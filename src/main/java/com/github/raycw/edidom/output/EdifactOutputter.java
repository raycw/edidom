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
package com.github.raycw.edidom.output;

import java.util.List;
import java.util.regex.Pattern;

import com.github.raycw.edidom.common.CompositeField;
import com.github.raycw.edidom.common.Document;
import com.github.raycw.edidom.common.Field;
import com.github.raycw.edidom.common.GroupEnvelope;
import com.github.raycw.edidom.common.Segment;
import com.github.raycw.edidom.common.Transaction;
import com.github.raycw.edidom.common.edifact.EdifactDocument;
import com.github.raycw.edidom.common.edifact.EdifactInterchangeEnvelope;
import com.github.raycw.edidom.common.edifact.EmptyGroupEnvelope;

/**
 * Output Edifact EdifactDocument to a string or writer. The {@link #outputString(EdifactDocument) outputString} 
 * method to print out Edifact format string.
 * 
 * 
 * @author Raymond
 *
 */
public class EdifactOutputter implements Outputter{
    public String outputString(Document document) {
        // Create regex for release character
        releasePattern = Pattern.compile("([" + document.getElementSeparator() + document.getSegmentSeparator() + document.getSubElementSeparator() + "])");
        releaseCharPattner = Pattern.compile(Pattern.quote(document.getReleaseCharacter()));
        
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
                List<Segment> segments = st.getSegments();
                for (Segment segment : segments) {
                    // Segment
                    fields = segment.getFields();
                    writeSegment(strBuilder, document, fields);
                }
                // SE
                strBuilder.append("UNT").append(document.getElementSeparator())
                        .append(st.getSegments().size()+2).append(document.getElementSeparator())
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
        String fieldValue = fields.get(0).getField(1).getValue();
        if (fieldValue.contains(document.getReleaseCharacter())) {
            fieldValue = releaseCharPattner.matcher(fieldValue).replaceAll(document.getReleaseCharacter()+document.getReleaseCharacter());
        }
        strBuilder.append(releasePattern.matcher(fieldValue).replaceAll(document.getReleaseCharacter()+"$0"));
        for (int i = 1; i < fields.size(); i++) {
            List<Field> subFields = fields.get(i).getFields();
            fieldValue = subFields.get(0).getValue();
            if (fieldValue.contains(document.getReleaseCharacter())) {
                fieldValue = releaseCharPattner.matcher(fieldValue).replaceAll(document.getReleaseCharacter()+document.getReleaseCharacter());
            }
            strBuilder.append(document.getElementSeparator()).append(releasePattern.matcher(fieldValue).replaceAll(document.getReleaseCharacter()+"$0"));
            for (int j = 1; j < subFields.size(); j++) {
                fieldValue = subFields.get(j).getValue();
                if (fieldValue.contains(document.getReleaseCharacter())) {
                    fieldValue = releaseCharPattner.matcher(fieldValue).replaceAll(document.getReleaseCharacter()+document.getReleaseCharacter());
                }
                fieldValue = releasePattern.matcher(fieldValue).replaceAll(document.getReleaseCharacter()+"$0");
                strBuilder.append(document.getSubElementSeparator()).append(fieldValue);
            }
        }
        strBuilder.append(document.getSegmentSeparator());
    }
    
    private Pattern releasePattern;
    private Pattern releaseCharPattner;

}
