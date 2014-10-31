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
package com.github.raycw.edidom.common.x12;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.raycw.edidom.common.CompositeField;
import com.github.raycw.edidom.common.Document;
import com.github.raycw.edidom.common.Field;
import com.github.raycw.edidom.common.GroupEnvelope;
import com.github.raycw.edidom.common.Segment;
import com.github.raycw.edidom.common.Transaction;
import com.github.raycw.edidom.common.x12.X12Document;
import com.github.raycw.edidom.common.x12.X12GroupEnvelope;
import com.github.raycw.edidom.common.x12.X12InterchangeEnvelope;
import com.github.raycw.edidom.common.x12.X12Transaction;

public class SegmentTest {

	private Document document;
	private X12InterchangeEnvelope interchange;
	private GroupEnvelope group;
    private Transaction txn;
    private Segment segment;
    private Segment segmentR4;
    private Field field;
    private CompositeField cField;
    private static final String[] X12_FIELDS = { 
    	    "ISA", "00", "          ", "00",
            "          ", "01", "GITHUB     ", "ZZ", "ACSLTEST       ",
            "100716", "1228", "U", "00401", "000000004", "0", "P", ":" };
    private static final String[] X12_R4_FIELDS = { 
	    "R4","R","UN","USCHI","Chicago","US" };
    
    private static final String[] TXN_FIELDS = {"ST","301","40001"};
    private static final String[] GRP_FIELDS = {"GS","RO","GITHUB","ACSLTEST","20100716","1228","4","X","004010"};
    
    @Before
    public void setUp() throws Exception {
    	document = new X12Document();
    	interchange = new X12InterchangeEnvelope(new Segment(X12_FIELDS));
        group = new X12GroupEnvelope(new Segment(GRP_FIELDS));
    	txn = new X12Transaction(new Segment(TXN_FIELDS));
        segment = new Segment(X12_FIELDS);
        segmentR4 = new Segment(X12_R4_FIELDS);
        field = Field.create("");
        cField = new CompositeField();
        txn.addSegment(segment);
        group.addTransaction(txn);
        interchange.addGroupEnvelope(group);
        document.setInterchangeEnvelope(interchange);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAddField() {
        segment.addField(field);
        assertEquals(18, segment.getFields().size());

        segment.addCompositeField(cField);
        assertEquals(19, segment.getFields().size());
    }

    @Test
    public void testAddFieldInPosition() {
    	//Before
        assertEquals(6, segmentR4.getFields().size());
        Field field = Field.create("IL");
        segmentR4.addField(8, field);
        
        assertEquals(9, segmentR4.getFields().size());
        assertEquals("", segmentR4.getField(6).getValue());
		assertEquals("", segmentR4.getField(7).getValue());
        assertEquals("IL", segmentR4.getField(8).getValue());
		assertEquals("US", segmentR4.getField(5).getValue());
        
    }
    
    @Test
    public void testGetField() {
        assertEquals("00", segment.getField(1).getValue());
        assertEquals("          ", segment.getField(2).getValue());
        assertEquals("00", segment.getField(3).getValue());
        assertEquals("          ", segment.getField(4).getValue());
        assertEquals("01", segment.getField(5).getValue());
        assertEquals("GITHUB     ", segment.getField(6).getValue());
        assertEquals("P", segment.getField(15).getValue());
        assertEquals(":", segment.getField(16).getValue());
    }
    
    @Test
    public void testGetSegmentFromTransaction() {
    	List<Segment> segments = txn.getSegments("ISA");
    	assertEquals(1, segments.size());
    }
    
    @Test
    public void testGetSegmentFromGroupEnvelop() {
    	List<Segment> segments = group.getSegment("ISA");
    	assertEquals(1, segments.size());
    }
    
    @Test
    public void testGetSegmentFromInterchangeEnvelop() {
    	List<Segment> segments = interchange.getSegment("ISA");
    	assertEquals(1, segments.size());
    }

    @Test
    public void testGetSegmentFromDocument() {
    	List<Segment> segments = document.getSegments("ISA");
    	assertEquals(1, segments.size());
    }
    
    @Test
    public void testCopyConstructor() {
    	Segment copy = new Segment(segment);
    	assertNotSame(copy, segment);
    	assertEquals(segment.getSegmentTag(), copy.getSegmentTag());
    	assertEquals(segment.getField(1).getValue(), copy.getField(1).getValue());
    	assertNull(copy.getTransaction());
    	assertNotSame(segment.getFields(), copy.getFields());
    }

}
