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
package com.github.edidom.output;

import java.util.List;

import com.github.edidom.common.CompositeField;
import com.github.edidom.common.Document;
import com.github.edidom.common.GroupEnvelope;
import com.github.edidom.common.InterchangeEnvelope;
import com.github.edidom.common.Segment;
import com.github.edidom.common.Transaction;

/**
 * Output X12 X12Document to a string or writer. The {@link #outputString(X12Document) outputString} 
 * method to print out X12 format string.
 * 
 * 
 * @author Raymond
 *
 */
public class X12Outputter {

	public String outputString(Document document) {
		StringBuilder strBuilder = new StringBuilder();
		// ISA
		InterchangeEnvelope isa = document.getInterchangeEnvelope();
		List<CompositeField> fields = isa.getFields();
		strBuilder.append(fields.get(0).getValue());
		for (int i = 1; i < fields.size(); i++) {
			strBuilder.append(document.getElementSeparator()).append(fields.get(i).getValue());			
		}
		strBuilder.append(document.getSegmentSeparator());
		List<GroupEnvelope> groups = isa.getGroups();
		for (GroupEnvelope gs : groups) {
			fields = gs.getFields();
			// GS
			strBuilder.append(fields.get(0).getValue());
			for (int i = 1; i < fields.size(); i++) {
				strBuilder.append(document.getElementSeparator()).append(fields.get(i).getValue());			
			}
			strBuilder.append(document.getSegmentSeparator());
			List<Transaction> transactions = gs.getTransactions();
			for (Transaction st : transactions) {
				fields = st.getFields();
				// ST
				strBuilder.append(fields.get(0).getValue());
				for (int i = 1; i < fields.size(); i++) {
					strBuilder.append(document.getElementSeparator()).append(fields.get(i).getValue());			
				}
				strBuilder.append(document.getSegmentSeparator());
				List<Segment> segments = st.getSegments();
				for (Segment segment : segments) {
					fields = segment.getFields();
					// Segment
					strBuilder.append(fields.get(0).getValue());
					for (int i = 1; i < fields.size(); i++) {
						strBuilder.append(document.getElementSeparator()).append(fields.get(i).getValue());			
					}
					strBuilder.append(document.getSegmentSeparator());
				}
				// SE
				strBuilder.append("SE").append(document.getElementSeparator())
						.append(st.getSegments().size()+2).append(document.getElementSeparator())
						.append(st.getControlNumber()).append(document.getSegmentSeparator());
			}
			// GE
			strBuilder.append("GE").append(document.getElementSeparator())
					.append(gs.getTransactions().size()).append(document.getElementSeparator())
					.append(gs.getControlNumber()).append(document.getSegmentSeparator());
		}
		// IEA
		strBuilder.append("IEA").append(document.getElementSeparator())
				.append(isa.getGroups().size()).append(document.getElementSeparator())
				.append(isa.getControlNumber()).append(document.getSegmentSeparator());
		return strBuilder.toString();
	}
}
