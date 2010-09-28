package com.cargosmart.b2b.edi.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InterchangeEnvelopeTest {

	Document doc;
	private InterchangeEnvelope interchange;
	@Before
	public void setUp() throws Exception {
		doc = new Document();
		interchange = new InterchangeEnvelope(new Segment(new String[16]));
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
		GroupEnvelope group = new GroupEnvelope(new Segment(new String[8]));
		interchange.addGroupEnvelope(group);
		
		assertEquals(1, interchange.getGroups().size());
		assertEquals(interchange, group.getInterchangeEnvelope());
	}
	
	@Test
	public void testRemoveGroupEnvelope() {
		GroupEnvelope group = new GroupEnvelope(new Segment(new String[8]));
		interchange.addGroupEnvelope(group);
		
		interchange.removeGroupEnvelope(group);
		assertNull(group.getInterchangeEnvelope());
		assertEquals(0, interchange.getGroups().size());
	}

}
