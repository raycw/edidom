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
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.edidom.common.Document;
import com.github.edidom.common.GroupEnvelope;
import com.github.edidom.common.Segment;
import com.github.edidom.common.Transaction;
import com.github.edidom.common.x12.X12Document;
import com.github.edidom.common.x12.X12GroupEnvelope;
import com.github.edidom.common.x12.X12InterchangeEnvelope;
import com.github.edidom.common.x12.X12Transaction;

public class GroupEnvelopeTest {
	
	Document document;
	X12InterchangeEnvelope interchange;
	GroupEnvelope group;

	@Before
	public void setUp() throws Exception {
		document = new X12Document();
		interchange = new X12InterchangeEnvelope(new Segment(new String[16]));
		document.setInterchangeEnvelope(interchange);
		group = new X12GroupEnvelope(new Segment(new String[8]));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetInterchangeEnvelope() {
		group.setInterchangeEnvelope(interchange);
		
		assertEquals(interchange, group.getInterchangeEnvelope());
	}

	@Test
	public void testDetach() {
		interchange.addGroupEnvelope(group);
		group.detach();
		
		assertNull(group.getInterchangeEnvelope());
		assertEquals(0, interchange.getGroups().size());
	}
	
	@Test
	public void testAddTransaction() {
		Transaction txn = new X12Transaction(new Segment(new String[2]));
		group.addTransaction(txn);
		
		assertEquals(1, group.getTransactions().size());
		assertEquals(group, txn.getGroupEnvelope());
	}

}
