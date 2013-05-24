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
package com.github.edidom.common.x12;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.edidom.common.GroupEnvelope;
import com.github.edidom.common.Segment;
import com.github.edidom.common.Transaction;
import com.github.edidom.common.x12.X12GroupEnvelope;
import com.github.edidom.common.x12.X12Transaction;

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

}
