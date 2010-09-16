package com.cargosmart.b2b.edi.common;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SegmentTest {

	private Segment segment;
	private Field field;
	private CompositeField cField;
	
	@Before
	public void setUp() throws Exception {
		segment = new Segment();
		field = new Field();
		cField = new CompositeField();		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddField() {
		segment.addField(field);
		assertEquals(1, segment.getFields().size());
		
		segment.addCompositeField(cField);
		assertEquals(2, segment.getFields().size());
	}
}
