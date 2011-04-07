package com.cargosmart.b2b.edi.output;

import java.util.List;

import com.cargosmart.b2b.edi.common.CompositeField;
import com.cargosmart.b2b.edi.common.Document;
import com.cargosmart.b2b.edi.common.Field;
import com.cargosmart.b2b.edi.common.GroupEnvelope;
import com.cargosmart.b2b.edi.common.InterchangeEnvelope;
import com.cargosmart.b2b.edi.common.Segment;
import com.cargosmart.b2b.edi.common.Transaction;
import com.cargosmart.b2b.edi.common.edifact.EdifactInterchangeEnvelope;

/**
 * Output Edifact EdifactDocument to a string or writer. The {@link #outputString(EdifactDocument) outputString} 
 * method to print out Edifact format string.
 * 
 * 
 * @author Raymond
 *
 */
public class EdifactOutputter {
    public String outputString(Document document) {
        StringBuilder strBuilder = new StringBuilder();
        // Interchange
        EdifactInterchangeEnvelope interchange = (EdifactInterchangeEnvelope) document.getInterchangeEnvelope();
        // LevelA
        Segment levelAInterchange = interchange.getLevelAInterchangeEnvelope();
        if (levelAInterchange != null) {
            for (CompositeField field : levelAInterchange.getFields()) {
                strBuilder.append(field.getValue());
            }
        }
        // LevelB
        List<CompositeField> fields = interchange.getFields();
        strBuilder.append(fields.get(0).getField(0).getValue());
        for (int i = 1; i < fields.size(); i++) {
            List<Field> subFields = fields.get(i).getFields();
            strBuilder.append(document.getElementSeparator()).append(subFields.get(1).getValue());
            for (int j = 1; j < subFields.size(); j++) {
                strBuilder.append(document.getSubElementSeparator()).append(subFields.get(j).getValue());         
            }
        }
        strBuilder.append(document.getSegmentSeparator());
        
        /*
        List<GroupEnvelope> groups = interchange.getGroups();
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
        }*/
        // UNZ
        strBuilder.append("UNZ").append(document.getElementSeparator())
                .append(interchange.getGroups().size()).append(document.getElementSeparator())
                .append(interchange.getControlNumber()).append(document.getSegmentSeparator());
        return strBuilder.toString();
    }

}
