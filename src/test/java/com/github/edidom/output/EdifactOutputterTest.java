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
package com.github.edidom.output;


import static org.junit.Assert.assertEquals;

import java.io.InputStreamReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.edidom.common.Document;
import com.github.edidom.input.EdifactBuilder;
import com.github.edidom.output.EdifactOutputter;

public class EdifactOutputterTest {

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

    public static final String SIMPLE_EDI_WO_UNA_UNG = 
        "UNB+UNOA:1+005435656:1+006415160:1+060515:1434+00000000000778'" + 
        "UNH+00000000000117+INVOIC:D:97B:UN'" + 
        "BGM+380+?????+?+?:?:?'?'+9'" + 
        "DTM+3:20060515:102'" +
        "UNT+4+00000000000117'" +
        "UNH+00000000000118+INVOIC:D:97B:UN'" + 
        "BGM+380+?????+?+?:?:?'?'+9'" + 
        "DTM+3:20060515:102'" +
        "UNT+4+00000000000118'" +
        "UNZ+2+00000000000778'";
    
    public static final String SIMPLE_EDIFACT_WO_UNA =
        "UNB+UNOA:1+005435656:1+006415160:1+060515:1434+00000000000778'" + 
        "UNG+INVOIC+GITHUB:01+EXAMPLE:ZZ+110414:2138+268877+UN+D:97B'" +
        "UNH+00000000000117+INVOIC:D:97B:UN'" + 
        "BGM+380+?????+?+?:?:?'?++9?:'" + 
        "DTM+3:20060515:102'" +
        "UNT+4+00000000000117'" +
        "UNH+00000000000118+INVOIC:D:97B:UN'" + 
        "BGM+380+?????+?+?:?:?'?++9?:'" + 
        "DTM+3:20060515:102'" +
        "UNT+4+00000000000118'" +
        "UNE+2+268877'" +
        "UNZ+1+00000000000778'";
    
    public static final String REAL_EDI =
            "UNA:+.? '" + 
            "UNB+UNOC:3+HIGHGOAL:ZZZ+INTTRANG2:ZZZ+120920:1309+23749779'" + 
            "UNH+23749779+IFTMBF:D:99B:UN:2.0'" + 
            "BGM+335+HGZW12090337+9'" + 
            "CTA+IC+:FANNYWEN'" + 
            "COM+020-38390141:TE'" + 
            "COM+fanny@highgoalgz.com:EM'" + 
            "COM+020-38105788:FX'" + 
            "DTM+137:201209201309:203'" + 
            "TSR+30'" + 
            "FTX+AAI+++DOC FANNYWEN,WUCHONG LAOD;CNTR STUFF ON SEP,24;EQC?:UG'" + 
            "LOC+197+CNCAN:181:6:GUANGZHOU'" + 
            "LOC+7+GHTEM:181:6:TEMA'" + 
            "LOC+88+CNHUA:181:6:HUANGPU'" + 
            "DTM+196:20120930:102'" + 
            "RFF+SI:HGZW12090337'" + 
            "RFF+CT:QGGZ006720'" + 
            "TDT+20+FX267W+1+++++:::VILLE D?' ORION '" + 
            "LOC+9+CNHUA:139:6:HUANGPU'" + 
            "LOC+11+GHTEM:139:6:TEMA'" + 
            "NAD+CA+CMDU:160:86+CMA CGM'" + 
            "NAD+ZZZ+HIGHGOALCNCAN:160:86+HIGH GOAL LTD'" + 
            "CTA+NT+:FANNYWEN'" + 
            "COM+fanny@highgoalgz.com:EM'" + 
            "CTA+IC+:FANNYWEN'" + 
            "COM+020-38390141:TE'" + 
            "COM+fanny@highgoalgz.com:EM'" + 
            "COM+020-38105788:FX'" + 
            "NAD+FW+HIGHGOALCNCAN:160:86+HIGH GOAL LTD'" + 
            "GID+1'" + 
            "FTX+AAA+++FURNITURE'" + 
            "MEA+AAE+G+KGM:28000'" + 
            "EQD+CN++45G1:102:5+2'" + 
            "EQN+1:2'" + 
            "TMD+3++MM'" + 
            "UNT+34+23749779'" + 
            "UNZ+1+23749779'";
    
    private Document doc;
    private Document doc_w_una;
    private Document doc_wo_una;
    private Document doc_wo_una_ung;
    private Document doc_real_edi;
    
    @Before
    public void setUp() throws Exception {
        EdifactBuilder builder = new EdifactBuilder();
        doc = builder.buildDocument(new InputStreamReader(EdifactOutputterTest.class.getResourceAsStream("/invoice_d97b.txt")));
        doc_w_una = builder.buildDocument(SIMPLE_EDIFACT_W_UNA);
        doc_wo_una = builder.buildDocument(SIMPLE_EDIFACT_WO_UNA);
        doc_wo_una_ung = builder.buildDocument(SIMPLE_EDI_WO_UNA_UNG);
        doc_real_edi = builder.buildDocument(REAL_EDI);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testOutput() {
        EdifactOutputter outputter = new EdifactOutputter();
        String output = outputter.outputString(doc_w_una);
        assertEquals(SIMPLE_EDIFACT_W_UNA, output);
        
        output = outputter.outputString(doc_wo_una);
        assertEquals(SIMPLE_EDIFACT_WO_UNA, output);

        output = outputter.outputString(doc_wo_una_ung);
        assertEquals(SIMPLE_EDI_WO_UNA_UNG, output);
        
        output = outputter.outputString(doc_real_edi);
        assertEquals(REAL_EDI, output);
    }
    
    @Test
    public void testAddGroupEnvelope() {
    	
    }
}
