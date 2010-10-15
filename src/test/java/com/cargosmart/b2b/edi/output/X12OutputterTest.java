package com.cargosmart.b2b.edi.output;


import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cargosmart.b2b.edi.common.Document;
import com.cargosmart.b2b.edi.input.X12Builder;

public class X12OutputterTest {
    public static final String X12DOC = 
        "ISA*00*          *00*          *01*CARGOSMART     *ZZ*ACSLTEST       *100716*1228*U*00401*000000004*0*P*:~\n" + 
        "GS*RO*CARGOSMART*ACSLTEST*20100716*1228*4*X*004010~\n" + 
        "ST*301*40001~\n"+
        "BEG*00*SA*95018017***950118~\n"+
        "SE*3*40001~\n" + 
        "GE*1*4~\n" + 
        "IEA*1*000000004~\n";
    private Document doc;
    private static X12Builder x12builder;
    private static X12Outputter outputter;

    @Before
	public void setUp() throws Exception {
    	x12builder = new X12Builder();
        doc = x12builder.buildDocument(X12DOC);
        outputter = new X12Outputter();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testOutput() {
		String output = outputter.outputString(doc);
		assertEquals(X12DOC, output);
	}

}
