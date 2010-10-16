package com.cargosmart.b2b.edi.output;


import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cargosmart.b2b.edi.common.Document;
import com.cargosmart.b2b.edi.common.GroupEnvelope;
import com.cargosmart.b2b.edi.common.Segment;
import com.cargosmart.b2b.edi.input.X12Builder;
import com.cargosmart.b2b.edi.input.X12BuilderTest;

public class X12OutputterTest {
    public static final String X12DOC = 
        "ISA*00*          *00*          *01*CARGOSMART     *ZZ*ACSLTEST       *100716*1228*U*00401*000000004*0*P*:~\n" + 
        "GS*RO*CARGOSMART*ACSLTEST*20100716*1228*4*X*004010~\n" + 
        "ST*301*40001~\n"+
        "BEG*00*SA*95018017***950118~\n"+
        "SE*3*40001~\n" + 
        "GE*1*4~\n" + 
        "IEA*1*000000004~\n";
    public static final String X12DOC_6010 = 
        "ISA*00*          *00*          *01*CARGOSMART     *ZZ*ACSLTEST       *100716*1228*U*00601*000000004*0*P*:~\n" + 
        "GS*RO*CARGOSMART*ACSLTEST*20100716*1228*4*X*006010~\n" + 
        "ST*301*40001~\n"+
        "BEG*00*SA*95018017***950118~\n"+
        "SE*3*40001~\n" + 
        "GE*1*4~\n" + 
        "IEA*1*000000004~\n";
    private static String x12_301;
    private Document doc;
    private Document doc_301;
    
    private static X12Builder x12builder;
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
		Segment n9 = doc_301.getSegment("N9").get(0);
		n9.getField(2).setValue("0987654321");
		String output = outputter.outputString(doc_301);
		assertEquals(this.readFile("/X12_2.txt"), output);
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

}
