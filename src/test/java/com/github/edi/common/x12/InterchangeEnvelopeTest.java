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
package com.github.edi.common.x12;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.edi.common.Document;
import com.github.edi.common.GroupEnvelope;
import com.github.edi.common.Segment;
import com.github.edi.common.x12.X12Document;
import com.github.edi.common.x12.X12GroupEnvelope;
import com.github.edi.common.x12.X12InterchangeEnvelope;

public class InterchangeEnvelopeTest {

	Document doc;
	private X12InterchangeEnvelope interchange;
	@Before
	public void setUp() throws Exception {
		doc = new X12Document();
		interchange = new X12InterchangeEnvelope(new Segment(new String[16]));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetDocument() {
		interchange.setDocument(doc);
		
		assertEquals(doc, interchange.getDocument());
		assertEquals(interchange, doc.getInterchangeEnvelope());
	}

	@Test
	public void testGetDocument() {
		doc.setInterchangeEnvelope(interchange);
		
		assertEquals(doc, interchange.getDocument());
	}
	
	@Test
	public void testAddGroupEnvelope() {
		GroupEnvelope group = new X12GroupEnvelope(new Segment(new String[8]));
		interchange.addGroupEnvelope(group);
		
		assertEquals(1, interchange.getGroups().size());
		assertEquals(interchange, group.getInterchangeEnvelope());
	}
	
	@Test
	public void testRemoveGroupEnvelope() {
		GroupEnvelope group = new X12GroupEnvelope(new Segment(new String[8]));
		interchange.addGroupEnvelope(group);
		
		interchange.removeGroupEnvelope(group);
		assertNull(group.getInterchangeEnvelope());
		assertEquals(0, interchange.getGroups().size());
	}

}
