package com.cargosmart.b2b.edi.common;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SegmentTest {

    private Segment segment;
    private Field field;
    private CompositeField cField;
    private static final String[] X12_FIELDS = { "ISA", "00", "          ", "00",
            "          ", "01", "CARGOSMART     ", "ZZ", "ACSLTEST       ",
            "100716", "1228", "U", "00401", "000000004", "0", "P", ":" };

    @Before
    public void setUp() throws Exception {
        segment = new Segment(X12_FIELDS);
        field = new Field();
        cField = new CompositeField();
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
    public void testGetField() {
        assertEquals("00", segment.getField(1).getValue());
        assertEquals("          ", segment.getField(2).getValue());
        assertEquals("00", segment.getField(3).getValue());
        assertEquals("          ", segment.getField(4).getValue());
        assertEquals("01", segment.getField(5).getValue());
        assertEquals("CARGOSMART     ", segment.getField(6).getValue());
        assertEquals("P", segment.getField(15).getValue());
        assertEquals(":", segment.getField(16).getValue());
    }
}
