/*******************************************************************************
 * Copyright 2013 Raymond Chin
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.github.raycw.edidom.output;


import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.raycw.edidom.common.Document;
import com.github.raycw.edidom.common.GroupEnvelope;
import com.github.raycw.edidom.common.Segment;
import com.github.raycw.edidom.common.Transaction;
import com.github.raycw.edidom.input.EdiBuilder;
import com.github.raycw.edidom.input.X12Builder;
import com.github.raycw.edidom.input.X12BuilderTest;
import com.github.raycw.edidom.output.X12Outputter;

public class X12OutputterTest {
    public static final String X12DOC = 
        "ISA*00*          *00*          *01*GITHUB     *ZZ*ACSLTEST       *100716*1228*U*00401*000000004*0*P*:~\n" + 
        "GS*RO*GITHUB*ACSLTEST*20100716*1228*4*X*004010~\n" + 
        "ST*301*40001~\n"+
        "BEG*00*SA*95018017***950118~\n"+
        "SE*3*40001~\n" + 
        "GE*1*4~\n" + 
        "IEA*1*000000004~\n";
    public static final String X12DOC_6010 = 
        "ISA*00*          *00*          *01*GITHUB     *ZZ*ACSLTEST       *100716*1228*U*00601*000000004*0*P*:~\n" + 
        "GS*RO*GITHUB*ACSLTEST*20100716*1228*4*X*006010~\n" + 
        "ST*301*40001~\n"+
        "BEG*00*SA*95018017***950118~\n"+
        "SE*3*40001~\n" + 
        "GE*1*4~\n" + 
        "IEA*1*000000004~\n";
    private static String x12_301;
    private Document doc;
    private Document doc_301;
    private Document doc_x12_2_txn;
    private Document doc_x12_3_txn;
    
    private static EdiBuilder x12builder;
    private static X12Outputter outputter;

    @BeforeClass
    public static void onlyOnce() throws IOException {
    	x12builder = new X12Builder();
    	x12_301 = readFile("/X12.txt");
        outputter = new X12Outputter();
    }

	private static String readFile(String resource) throws IOException {
		InputStream in = X12BuilderTest.class.getResourceAsStream(resource);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		char[] buffer = new char[1024];
		int nRead;
		StringBuilder content = new StringBuilder();
		while ((nRead = reader.read(buffer, 0, 1024)) != -1) {
			content.append(buffer, 0, nRead);
		}
		return content.toString();
	}

    @Before
	public void setUp() throws Exception {
        doc = x12builder.buildDocument(X12DOC);
        doc_301 = x12builder.buildDocument(x12_301);
        doc_x12_2_txn = x12builder.buildDocument(readFile("/X12_2_txn.txt"));
        doc_x12_3_txn = x12builder.buildDocument(readFile("/X12_3_txn.txt"));
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testOutput() {
		String output = outputter.outputString(doc);
		assertEquals(X12DOC, output);
		output = outputter.outputString(doc_301);
		assertEquals(x12_301, output);
	}
	
	@Test
	public void testChangeN9_BN() throws IOException {
		Segment n9 = doc_301.getSegments("N9").get(0);
		n9.getField(2).setValue("0987654321");
		String output = outputter.outputString(doc_301);
		assertEquals(X12OutputterTest.readFile("/X12_2.txt"), output);
	}
	
	@Test
	public void testChangeVersion() {
		doc.getInterchangeEnvelope().setVersion("00601");
		for (GroupEnvelope gs : doc.getInterchangeEnvelope().getGroups()) {
			gs.setVersion("006010");
		}
		String output = outputter.outputString(doc);
		assertEquals(X12DOC_6010, output);
	}
	
	@Test
	public void testSplit() {
	    List<Transaction> txns = new ArrayList<Transaction>();
	    // 2 txn
	    for (GroupEnvelope gs : doc_x12_2_txn.getInterchangeEnvelope().getGroups()) {
            for (Transaction st : gs.getTransactions()) {
                txns.add(st);
            }
        }
	    for (Transaction transaction : txns) {
            transaction.detach();
        }
	    for (Transaction txn : txns) {
            doc_x12_2_txn.getInterchangeEnvelope().getGroups().get(0).addTransaction(txn);
            doc_301.getInterchangeEnvelope().getGroups().get(0).getTransactions().get(0).setControlNumber(txn.getControlNumber());
            assertEquals(outputter.outputString(doc_301), outputter.outputString(doc_x12_2_txn));
            txn.detach();
        }
	    
	    // 3 txn
	    txns.clear();
        for (GroupEnvelope gs : doc_x12_3_txn.getInterchangeEnvelope().getGroups()) {
            for (Transaction st : gs.getTransactions()) {
                txns.add(st);
            }
        }
        for (Transaction transaction : txns) {
            transaction.detach();
        }
        for (Transaction txn : txns) {
            doc_x12_3_txn.getInterchangeEnvelope().getGroups().get(0).addTransaction(txn);
            doc_301.getInterchangeEnvelope().getGroups().get(0).getTransactions().get(0).setControlNumber(txn.getControlNumber());
            assertEquals(outputter.outputString(doc_301), outputter.outputString(doc_x12_3_txn));
            txn.detach();
        }
	}

}
