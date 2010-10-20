package com.cargosmart.b2b.edi.output;

import java.util.List;

import com.cargosmart.b2b.edi.common.CompositeField;
import com.cargosmart.b2b.edi.common.Document;
import com.cargosmart.b2b.edi.common.GroupEnvelope;
import com.cargosmart.b2b.edi.common.Segment;
import com.cargosmart.b2b.edi.common.Transaction;
import com.cargosmart.b2b.edi.common.InterchangeEnvelope;

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
				List<Segment> segments = st.getSegements();
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
						.append(st.getSegements().size()+2).append(document.getElementSeparator())
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
