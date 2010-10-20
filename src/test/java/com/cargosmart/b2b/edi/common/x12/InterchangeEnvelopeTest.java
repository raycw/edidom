package com.cargosmart.b2b.edi.common.x12;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cargosmart.b2b.edi.common.Document;
import com.cargosmart.b2b.edi.common.GroupEnvelope;
import com.cargosmart.b2b.edi.common.Segment;
import com.cargosmart.b2b.edi.common.x12.X12Document;
import com.cargosmart.b2b.edi.common.x12.X12GroupEnvelope;
import com.cargosmart.b2b.edi.common.x12.X12InterchangeEnvelope;

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
