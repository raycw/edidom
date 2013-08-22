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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.raycw.edidom.common.GroupEnvelope;
import com.github.raycw.edidom.common.Segment;
import com.github.raycw.edidom.common.Transaction;

public class TransactionTest {

	Transaction txn;
	GroupEnvelope group;
	@Before
	public void setUp() throws Exception {
		group = new X12GroupEnvelope(new Segment(new String[8]));
		txn = new X12Transaction(new Segment(new String[2]));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDetach() {
		group.addTransaction(txn);
		
		assertEquals(group, txn.getGroupEnvelope());
		txn.detach();
		
		assertNull(txn.getGroupEnvelope());
		assertFalse(group.getTransactions().contains(txn));
	}
	
	@Test
	public void testGroupTxnRelationship() {
		group.addTransaction(txn);
		
		assertEquals(txn, group.getTransactions().get(0));
		assertEquals(group, txn.getGroupEnvelope());
	}
	
	@Test
	public void testAddSegement() {
		Segment segment = new Segment(new String[16]);
		txn.addSegment(segment);
		
		assertEquals(segment, txn.getSegments().get(0));
	}
	
	
	@Test
	public void testAddSegmentAtIndex() {
		Segment segment = new Segment(new String[16]);
		txn.addSegment(segment);
		
		assertEquals(segment, txn.getSegments().get(0));
		
		Segment segment1 = new Segment(new String[]{"N9","SE","123456"});
		txn.addSegment(segment1);
		Segment segment2 = new Segment(new String[]{"N9","BE","567891"});
		txn.addSegment(segment2);
		Segment segment3 = new Segment(new String[]{"R4","O","UNBCO"}); 
		txn.addSegment(1, segment3); // insert position 1 (before N9)
		assertEquals("SE", txn.getSegments().get(2).getFields().get(1).getValue());
		assertEquals("BE", txn.getSegments().get(3).getField(1).getValue());
		assertEquals("O", txn.getSegments().get(1).getField(1).getValue());
		
		
		assertEquals(segment, txn.getSegments().get(0));
		assertEquals(segment3, txn.getSegments().get(1)); 
		assertEquals(segment1, txn.getSegments().get(2)); 
		assertEquals(segment2, txn.getSegments().get(3)); 
	}

	@Test
	public void testAddSegmentsAtIndex() {
		Collection<Segment> c1 = new ArrayList<Segment>();
		c1.add(new Segment(new String[]{"BGM","S1","USLAX","20130825"}));
		c1.add(new Segment(new String[]{"BGM","S2","USLAX","20130825"}));
		c1.add(new Segment(new String[]{"LOC","S3","POR"}));
		c1.add(new Segment(new String[]{"LOC","S4","POD"}));
		
		Collection<Segment> c2 = new ArrayList<Segment>();
		c2.add(new Segment(new String[]{"DTM","N1","USLAX","20130825"}));
		c2.add(new Segment(new String[]{"DTM","N2","USLAX","20130825"}));
		c2.add(new Segment(new String[]{"DTM","N3","POR"}));
		c2.add(new Segment(new String[]{"DTM","N4","POD"}));
	
		Collection<Segment> c3 = new ArrayList<Segment>();
		c3.add(new Segment(new String[]{"FTX","E1","This is testing segment"}));
		
		// Add collection 1
		txn.addSegment(0, c1);
		// Size should be 4
		assertEquals(4, txn.getSegments().size());
		// Position 1 to 4
		assertEquals("S1", txn.getSegment(0).getField(1).getValue());
		assertEquals("S2", txn.getSegment(1).getField(1).getValue());
		assertEquals("S3", txn.getSegment(2).getField(1).getValue());
		assertEquals("S4", txn.getSegment(3).getField(1).getValue());
		
		// Add collection 2
		txn.addSegment(2, c2);
		// Position 1 to 8
		assertEquals("S1", txn.getSegment(0).getField(1).getValue());
		assertEquals("S2", txn.getSegment(1).getField(1).getValue());
		assertEquals("N1", txn.getSegment(2).getField(1).getValue());
		assertEquals("N2", txn.getSegment(3).getField(1).getValue());
		assertEquals("N3", txn.getSegment(4).getField(1).getValue());
		assertEquals("N4", txn.getSegment(5).getField(1).getValue());
		assertEquals("S3", txn.getSegment(6).getField(1).getValue());
		assertEquals("S4", txn.getSegment(7).getField(1).getValue());
		// Size should be 8
		assertEquals(8, txn.getSegments().size());
		
		// Add collection 3 at the end
		txn.addSegment(txn.getSegments().size(), c3);
		assertEquals("E1", txn.getSegment(8).getField(1).getValue());
		// Size should be 9
		assertEquals(9, txn.getSegments().size());
	}
	
}
