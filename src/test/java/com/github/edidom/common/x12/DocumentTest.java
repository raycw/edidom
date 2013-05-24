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

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.edidom.common.Document;
import com.github.edidom.common.Segment;
import com.github.edidom.common.x12.X12Document;
import com.github.edidom.common.x12.X12InterchangeEnvelope;

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
