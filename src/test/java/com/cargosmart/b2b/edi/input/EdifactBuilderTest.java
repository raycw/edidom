package com.cargosmart.b2b.edi.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cargosmart.b2b.edi.common.Document;

public class EdifactBuilderTest {

	private static String edifactStr;
	private Document doc;

	@BeforeClass
	public static void onlyOnce() throws Exception {
		InputStream in = EdifactBuilderTest.class
				.getResourceAsStream("/invoice_d97b.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		char[] buffer = new char[1024];
		int nRead;
		StringBuilder content = new StringBuilder();
		while ((nRead = reader.read(buffer, 0, 1024)) != -1) {
			content.append(buffer, 0, nRead);
		}
		edifactStr = content.toString();
	}

	@Before
	public void setUp() throws Exception {
		EdifactBuilder builder = new EdifactBuilder();
		doc = builder.buildDocument(edifactStr);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBuildDocumentString() {
		assertEquals("+", doc.getElementSeparator());
		assertEquals(":", doc.getSubElementSeparator());
		assertEquals("'\n", doc.getSegmentSeparator());
		assertEquals("?", doc.getReleaseCharacter());
		assertNotNull(doc.getInterchangeEnvelope());
		assertEquals(1, doc.getSegment("BGM").size());
	}

}
