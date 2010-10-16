package com.cargosmart.b2b.edi.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cargosmart.b2b.edi.common.Document;
import com.cargosmart.b2b.edi.common.GroupEnvelope;
import com.cargosmart.b2b.edi.common.InterchangeEnvelope;
import com.cargosmart.b2b.edi.common.Segment;
import com.cargosmart.b2b.edi.common.Transaction;

public class X12BuilderTest {
    
    public static final String X12DOC = 
        "ISA*00*          *00*          *01*CARGOSMART     *ZZ*ACSLTEST       *100716*1228*U*00401*000000004*0*P*:~\n" + 
        "GS*RO*CARGOSMART*ACSLTEST*20100716*1228*4*X*004010~\n" + 
        "ST*301*40001~\n"+
        "BEG*00*SA*95018017***950118~\n"+
        "SE*3*40001~\n" + 
        "GE*1*4~\n" + 
        "IEA*1*000000004~\n";
    private static String x12_301;
    private Document doc;
    private Document doc_301;

    private static X12Builder x12builder;
    
    @BeforeClass
    public static void onlyOnce() throws IOException {
    	x12builder = new X12Builder();
    	InputStream in = X12BuilderTest.class.getResourceAsStream("/X12.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		char[] buffer = new char[1024];
		int nRead;
		StringBuilder content = new StringBuilder();
		while ((nRead = reader.read(buffer, 0, 1024)) != -1) {
			content.append(buffer, 0, nRead);
		}
		x12_301 = content.toString();
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
    public void testBuildDocumentString() {
        assertEquals("*", doc.getElementSeparator());
        assertEquals("~\n", doc.getSegmentSeparator());
        assertEquals(":", doc.getSubElementSeparator());
        assertNotNull(doc.getInterchangeEnvelope());
        assertNotNull(doc.getInterchangeEnvelope().getGroups());
    }

    @Test
    public void testBuildISASegment() {
        InterchangeEnvelope isa = doc.getInterchangeEnvelope();
        assertEquals("01", isa.getSenderQualifier());
        assertEquals("CARGOSMART", isa.getSenderId());
        assertEquals("ZZ", isa.getReceiverQualifier());
        assertEquals("ACSLTEST", isa.getReceiverId());
        assertEquals("00401", isa.getVersion());
        assertEquals("000000004", isa.getControlNumber());
        
    }
    
    @Test
    public void testBuildGSSegment() {
    	GroupEnvelope gs = doc.getInterchangeEnvelope().getGroups().get(0);
    	assertEquals(1, doc.getInterchangeEnvelope().getGroups().size());
    	assertEquals("RO", gs.getFunctionalCode());
    	assertEquals("CARGOSMART", gs.getSenderCode());
    	assertEquals("ACSLTEST", gs.getReceiverCode());
    	assertEquals("4", gs.getControlNumber());
    	assertEquals("004010", gs.getVersion());
    }

    @Test
    public void testBuildSTSegment() {
    	Transaction st = doc.getInterchangeEnvelope().getGroups().get(0).getTransactions().get(0);
    	assertEquals(1, doc.getInterchangeEnvelope().getGroups().get(0).getTransactions().size());
    	assertEquals("301", st.getType());
    	assertEquals("40001", st.getControlNumber());
    }
    
    @Test
    public void testSegment() {
    	Segment segment = doc.getInterchangeEnvelope().getGroups().get(0).getTransactions().get(0).getSegements().get(0);
    	assertEquals("BEG", segment.getSegmentTag());
    	assertEquals("950118", segment.getField(6).getValue());
    	
    	List<Segment> segments = doc_301.getInterchangeEnvelope().getGroups().get(0).getTransactions().get(0).getSegements();
    	assertEquals(27, segments.size());
    	
    }
}
