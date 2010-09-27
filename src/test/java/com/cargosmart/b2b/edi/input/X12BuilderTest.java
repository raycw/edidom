package com.cargosmart.b2b.edi.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cargosmart.b2b.edi.common.Document;
import com.cargosmart.b2b.edi.common.GroupEnvelope;
import com.cargosmart.b2b.edi.common.InterchangeEnvelope;
import com.cargosmart.b2b.edi.common.Transaction;

public class X12BuilderTest {
    
    public static final String X12DOC = 
        "ISA*00*          *00*          *01*CARGOSMART     *ZZ*ACSLTEST       *100716*1228*U*00401*000000004*0*P*:~\n" + 
        "GS*RO*CARGOSMART*ACSLTEST*20100716*1228*4*X*004010~\n" + 
        "ST*301*40001~\n"+
        "SE*1*40001~\n" + 
        "GE*1*4~\n" + 
        "IEA*1*000000004~\n";
    private Document doc;

    X12Builder x12builder = new X12Builder();
    
    @Before
    public void setUp() throws Exception {
        doc = x12builder.buildDocument(X12DOC);
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
    	assertEquals("CARGOSMART", gs.getSendCode());
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
}
