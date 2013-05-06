package com.github.edi.common.x12;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.edi.common.Document;
import com.github.edi.common.Segment;
import com.github.edi.common.x12.X12Document;
import com.github.edi.common.x12.X12InterchangeEnvelope;

public class DocumentTest {

	@Test
	public void testSetInterchangeEnvelope() {
		Document doc = new X12Document();
		X12InterchangeEnvelope interchange = new X12InterchangeEnvelope(new Segment(new String[16]));
		doc.setInterchangeEnvelope(interchange);
		
		assertEquals(interchange, doc.getInterchangeEnvelope());
		assertEquals(doc, interchange.getDocument());
		
	}

	@Test
	public void testSubElementSeparator() {
        Document doc = new X12Document();
        X12InterchangeEnvelope interchange = new X12InterchangeEnvelope(new Segment(new String[17]));
        doc.setInterchangeEnvelope(interchange);
        
        doc.setSubElementSeparator("@");
        assertEquals("@", doc.getSubElementSeparator());
        assertEquals("@", interchange.getField(16).getValue());
	}
}
