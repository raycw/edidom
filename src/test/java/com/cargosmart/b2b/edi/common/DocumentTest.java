package com.cargosmart.b2b.edi.common;

import static org.junit.Assert.*;

import org.junit.Test;

public class DocumentTest {

	@Test
	public void testSetInterchangeEnvelope() {
		Document doc = new Document();
		InterchangeEnvelope interchange = new InterchangeEnvelope();
		doc.setInterchangeEnvelope(interchange);
		
		assertEquals(interchange, doc.getInterchangeEnvelope());
		assertEquals(doc, interchange.getDocument());
		
	}

}