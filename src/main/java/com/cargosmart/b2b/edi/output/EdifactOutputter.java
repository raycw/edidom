package com.cargosmart.b2b.edi.output;

import java.util.List;
import java.util.regex.Pattern;

import com.cargosmart.b2b.edi.common.CompositeField;
import com.cargosmart.b2b.edi.common.Document;
import com.cargosmart.b2b.edi.common.Field;
import com.cargosmart.b2b.edi.common.GroupEnvelope;
import com.cargosmart.b2b.edi.common.Segment;
import com.cargosmart.b2b.edi.common.Transaction;
import com.cargosmart.b2b.edi.common.edifact.EdifactDocument;
import com.cargosmart.b2b.edi.common.edifact.EdifactInterchangeEnvelope;
import com.cargosmart.b2b.edi.common.edifact.EmptyGroupEnvelope;

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
        // Create regex for release character
        releasePattern = Pattern.compile("([" + document.getElementSeparator() + document.getSegmentSeparator() + document.getSubElementSeparator() + "])");
        
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
        writeSegment(strBuilder, document, fields);
        
        boolean isEmptyGroup = true;
        
        List<GroupEnvelope> groups = interchange.getGroups();
        for (GroupEnvelope gs : groups) {
            // GS
            if (!(gs instanceof EmptyGroupEnvelope)) {
                isEmptyGroup = false;
                fields = gs.getFields();
                writeSegment(strBuilder, document, fields);
            }
            List<Transaction> transactions = gs.getTransactions();
            for (Transaction st : transactions) {
                // ST
                fields = st.getFields();
                writeSegment(strBuilder, document, fields);
                List<Segment> segments = st.getSegements();
                for (Segment segment : segments) {
                    // Segment
                    fields = segment.getFields();
                    writeSegment(strBuilder, document, fields);
                }
                // SE
                strBuilder.append("UNT").append(document.getElementSeparator())
                        .append(st.getSegements().size()+2).append(document.getElementSeparator())
                        .append(st.getControlNumber()).append(document.getSegmentSeparator());
            }
            // GE
            if (!(gs instanceof EmptyGroupEnvelope)) {
                strBuilder.append("UNE").append(document.getElementSeparator())
                        .append(gs.getTransactions().size()).append(document.getElementSeparator())
                        .append(gs.getControlNumber()).append(document.getSegmentSeparator());
            }
        }
        // UNZ
        strBuilder.append("UNZ").append(document.getElementSeparator())
                //if emptyGroup, UNZ_01 should be count of transactions, otherwise, count of groups
                .append(isEmptyGroup?interchange.getGroups().get(0).getTransactions().size():interchange.getGroups().size()) 
                .append(document.getElementSeparator())
                .append(interchange.getControlNumber()).append(document.getSegmentSeparator());
        return strBuilder.toString();
    }

    private void writeSegment(StringBuilder strBuilder, Document document,
            List<CompositeField> fields) {
        strBuilder.append(releasePattern.matcher(fields.get(0).getField(1).getValue()).replaceAll(document.getReleaseCharacter()+"$0"));
        for (int i = 1; i < fields.size(); i++) {
            List<Field> subFields = fields.get(i).getFields();
            strBuilder.append(document.getElementSeparator()).append(releasePattern.matcher(subFields.get(0).getValue()).replaceAll(document.getReleaseCharacter()+"$0"));
            for (int j = 1; j < subFields.size(); j++) {
                strBuilder.append(document.getSubElementSeparator()).append(releasePattern.matcher(subFields.get(j).getValue()).replaceAll(document.getReleaseCharacter()+"$0"));         
            }
        }
        strBuilder.append(document.getSegmentSeparator());
    }
    
    private Pattern releasePattern;

}
