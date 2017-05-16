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
package com.github.raycw.edidom.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.github.raycw.edidom.output.EdifactOutputter;
import com.github.raycw.edidom.output.Outputter;
import org.junit.*;

import com.github.raycw.edidom.common.Document;
import com.github.raycw.edidom.common.LoopGroup;
import com.github.raycw.edidom.common.Segment;
import com.github.raycw.edidom.common.Transaction;
import com.github.raycw.edidom.common.edifact.EdifactGroupEnvelope;
import com.github.raycw.edidom.common.edifact.EdifactInterchangeEnvelope;
import com.github.raycw.edidom.input.EdifactBuilder;

import javax.print.Doc;

public class EdifactBuilderTest {

	private static String edifactStr;
	private Document doc;
    private Document doc_w_una;
	
    public static final String SIMPLE_EDIFACT_W_UNA = 
            "UNA:+.? '" + 
            "UNB+UNOA:1+005435656:1+006415160:1+060515:1434+00000000000778'" + 
            "UNH+00000000000117+INVOIC:D:97B:UN'" + 
            "BGM+380+3?+4?:2?'4??5?:+9'" + 
            "DTM+3:20060515:102'" +
            "UNT+4+00000000000117'" + 
            "UNH+00000000000118+INVOIC:D:97B:UN'" + 
            "BGM+380+3?+4?:2?'4??5?:+9'" + 
            "DTM+3:20060515:102'" +
            "UNT+4+00000000000118'" + 
            "UNZ+2+00000000000778'";

	@BeforeClass
	public static void onlyOnce() throws Exception {
		edifactStr = readFileAsString("/invoice_d97b.txt");
	}

	@Before
	public void setUp() throws Exception {
		EdifactBuilder builder = new EdifactBuilder();
		doc = builder.buildDocument(edifactStr);
		doc_w_una = builder.buildDocument(SIMPLE_EDIFACT_W_UNA);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBuildDocumentString() {
		assertEquals("+", doc.getElementSeparator());
		assertEquals(":", doc.getSubElementSeparator());
		assertEquals("'", doc.getSegmentSeparator());
		assertEquals("?", doc.getReleaseCharacter());
		assertNotNull(doc.getInterchangeEnvelope());
		assertEquals(1, doc.getSegments("BGM").size());
	}
	
	@Test
	public void testBuildInterchangeSegment() {
		EdifactInterchangeEnvelope interchange = (EdifactInterchangeEnvelope)doc.getInterchangeEnvelope();
		assertEquals("1", interchange.getVersion());
		assertEquals("UNOA", interchange.getField(1).getField(1).getValue());
		assertEquals("005435656", interchange.getSenderId());
		assertEquals("1", interchange.getSenderQualifier());
		assertEquals("006415160", interchange.getReceiverId());
		assertEquals("1", interchange.getReceiverQualifier());
		assertEquals("00000000000778", interchange.getControlNumber());
		assertNotNull(interchange.getLevelAInterchangeEnvelope());
	}
	
	@Test
	public void testBuildGroupSegment() {
		EdifactGroupEnvelope group = (EdifactGroupEnvelope) doc.getInterchangeEnvelope().getGroups().get(0);
		assertEquals(null, group.getFunctionalCode());
		assertEquals(null, group.getControlNumber());
		assertEquals(null, group.getVersion());
		assertEquals(null, group.getSenderCode());
		assertEquals(null, group.getReceiverCode());
	}
	
	@Test
	public void testBuildTransaction() {
		Transaction txn = doc.getInterchangeEnvelope().getGroups().get(0).getTransactions().get(0);
		assertNotNull(txn);
		assertEquals("00000000000117", txn.getControlNumber());
		assertEquals("INVOIC", txn.getType());
	}

	@Test
	public void testSegment() {
		Transaction txn = doc.getInterchangeEnvelope().getGroups().get(0).getTransactions().get(0);
		Segment seg = txn.getSegments("BGM").get(0);
		assertEquals("380", seg.getField(1).getField(1).getValue());
		
		List<Segment> segments = txn.getSegments("NAD");
		assertEquals("BY", segments.get(0).getField(1).getField(1).getValue());
		assertEquals("792820524", segments.get(0).getField(2).getField(1).getValue());
		assertEquals("", segments.get(0).getField(2).getField(2).getValue());
		assertEquals("CUMMINS MID-RANGE E'NGINE PLANT +M", segments.get(0).getField(4).getField(1).getValue());
		//test release character
		assertEquals("GENERAL WIDGET COMPANY' ATTN:Raymond:??+", segments.get(1).getField(4).getField(1).getValue());
	}
	
	@Test
	public void testNextSegment() {
	    Transaction txn = doc.getInterchangeEnvelope().getGroups().get(0).getTransactions().get(0);
	    Segment bgm = txn.getSegments("BGM").get(0);
	    Segment dtm = txn.getSegments("DTM").get(0);
	    assertEquals(bgm.nextSegment(), dtm);
	    
	    Segment moa = txn.getSegments("MOA").get(txn.getSegments("MOA").size()-1);
	    assertEquals(null, moa.nextSegment());
	}
	
    @Test
    public void testPreviousSegment() {
        Transaction txn = doc.getInterchangeEnvelope().getGroups().get(0).getTransactions().get(0);
        Segment bgm = txn.getSegments("BGM").get(0);
        Segment dtm = txn.getSegments("DTM").get(0);
        assertEquals(dtm.previousSegment(), bgm);
        assertEquals(null, bgm.previousSegment());
        
        Segment moa = txn.getSegments("MOA").get(txn.getSegments("MOA").size()-1);
        Segment alc = txn.getSegments("ALC").get(txn.getSegments("ALC").size()-1);
        assertEquals(alc, moa.previousSegment());
    }
    
    @Test
    public void testNextSegments() {
        Transaction txn = doc.getInterchangeEnvelope().getGroups().get(0).getTransactions().get(0);
        List<Segment> linList = txn.getSegments("LIN");
        List<Segment> firstMOAList = linList.get(0).nextSegments("MOA");
        assertEquals(4, firstMOAList.size());
        
        List<Segment> secondMOAList = linList.get(1).nextSegments("MOA");
        assertEquals(3, secondMOAList.size());
    }
	
    @Test
    public void testPreviousSegments() {
        Transaction txn = doc.getInterchangeEnvelope().getGroups().get(0).getTransactions().get(0);
        List<Segment> linList = txn.getSegments("LIN");
        List<Segment> firstMOAList = linList.get(0).previousSegments("MOA");
        assertEquals(0, firstMOAList.size());
        
        List<Segment> secondMOAList = linList.get(1).previousSegments("MOA");
        assertEquals(1, secondMOAList.size());
    }
    
    @Test
    public void testLoopGroup() {
        Transaction txn = doc.getInterchangeEnvelope().getGroups().get(0).getTransactions().get(0);
        List<LoopGroup> loops = txn.getLoopGroups("LIN", "UNS");
        assertEquals(2, loops.size());
        
        for (LoopGroup loopGroup : loops) {
            assertEquals(6, loopGroup.getSegments().size());
            assertEquals("LIN", loopGroup.getSegments().get(0).getSegmentTag());
            assertEquals("PRI", loopGroup.getSegments().get(5).getSegmentTag());
            List<LoopGroup> secondLoop = loopGroup.getLoopGroups("LIN", "QTY");
            assertEquals(1, secondLoop.size());
            assertEquals(2, secondLoop.get(0).getSegments().size());
        }
        loops = txn.getLoopGroups("ALC", "UNT");
        assertEquals(1, loops.size());
        assertEquals(2, loops.get(0).getSegments().size());
    }
    
    @Test
    public void testReleaseCharacter() {
        Transaction txn = doc_w_una.getInterchangeEnvelope().getGroups().get(0).getTransactions().get(0);
        Segment bgm = txn.getSegments("BGM").get(0);
        assertEquals("3+4:2'4?5:", bgm.getField(2).getValue());
    }

	@Test
	public void testHandleLinefeedByBuilderConfiguration() {
		BuilderConfiguration builderConfiguration = new BuilderConfiguration();
		builderConfiguration.segmentSeparator = "\n";
		EdifactBuilder builder = new EdifactBuilder();
		String origin = readFileAsString("/edifact_d96a_linefeed.txt");
		Document document = builder.buildDocument(origin, builderConfiguration);
		Assert.assertEquals("\n", document.getSegmentSeparator());
		Assert.assertEquals(5, document.getInterchangeEnvelope().getGroups().get(0).getTransactions().size());
		Outputter outputter = new EdifactOutputter();
		Assert.assertEquals(origin, outputter.outputString(document));
	}

	@Test
	public void testHandleQuoteWithLinefeedByBuilderConfiguration() {
		BuilderConfiguration builderConfiguration = new BuilderConfiguration();
		builderConfiguration.segmentSeparator = "'\r\n";
		String origin = readFileAsString("/edifact_d96a_quote_with_linefeed.txt");
		EdifactBuilder builder = new EdifactBuilder();
		Document document = builder.buildDocument(origin, builderConfiguration);
		Assert.assertEquals("'\r\n", document.getSegmentSeparator());
		Assert.assertEquals(5, document.getInterchangeEnvelope().getGroups().get(0).getTransactions().size());
		Outputter outputter = new EdifactOutputter();
		Assert.assertEquals(origin, outputter.outputString(document));
	}


	private static String readFileAsString(String resourceName){
		InputStream in = EdifactBuilderTest.class
				.getResourceAsStream(resourceName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		char[] buffer = new char[1024];
		int nRead;
		StringBuilder content = new StringBuilder();
		try {
			while ((nRead = reader.read(buffer, 0, 1024)) != -1) {
                content.append(buffer, 0, nRead);
            }
		} catch (IOException e) {
			assertFalse(true);
		}
		return content.toString();
	}

}
