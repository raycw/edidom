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
package com.github.edidom.snippets;

import java.util.List;

import com.github.edidom.common.Document;
import com.github.edidom.common.Segment;
import com.github.edidom.common.Transaction;
import com.github.edidom.common.edifact.EdifactGroupEnvelope;
import com.github.edidom.input.EdifactBuilder;
import com.github.edidom.output.EdifactOutputter;

public class PartnerId {

    private static final String CT =
        "UNB+UNOC:2+IITTRR:ZZZ+999999:ZZZ+110419:0540+6771'" + 
        "UNH+1+IFTSTA:D:99B:UN'" + 
        "BGM+23+315+9'" + 
        "DTM+137:209904172300:203'" + 
        "NAD+CA+ABCD:160:87'" + 
        "RFF+BN:1234567890AB'" + 
        "RFF+BM:1234567890AB'" + 
        "CNI+1'" + 
        "STS+1+VD::22'" + 
        "DTM+334:209904172300:203'" + 
        "LOC+175+CNXGG:139:6:XINGANG'" + 
        "TDT+20+9996R+1++:172+++9999999:146:11:DUMP VESSEL'" + 
        "LOC+9+CNXGG:139:6'" + 
        "DTM+133:209904170000:203'" + 
        "LOC+11+USLGB:139:6'" + 
        "DTM+132:209905120000:203'" + 
        "LOC+7+USHOU:139:6'" + 
        "EQD+CN+ABCD1234567+42G0:102:5+++5'" + 
        "UNT+18+1'" + 
        "UNZ+1+6771'";
    
    public void moveToGroup() {
        EdifactBuilder builder = new EdifactBuilder();
        Document doc = builder.buildDocument(CT);
        String receiverId = doc.getInterchangeEnvelope().getReceiverId();
        //create dummy group that contains interchange receiver id
        String[][] cField = {{"UNG"}, {"IFTSTA"}, {"999999","ZZZ"}, {receiverId,"ZZZ"}, {doc.getInterchangeEnvelope().getField(4).getField(1).getValue(),doc.getInterchangeEnvelope().getField(4).getField(2).getValue()}, {"58909"}, {"UN"}, {"D","96B"}};
        EdifactGroupEnvelope dummyGroup = new EdifactGroupEnvelope(new Segment(cField));
        //change interchange id to INTTRA
        doc.getInterchangeEnvelope().setReceiverId("999999");
        doc.setSegmentSeparator(doc.getSegmentSeparator() + "\n"); // debug only
        //remove empty group and add dummy group to document
        List<Transaction> txns = doc.getInterchangeEnvelope().getGroups().get(0).getTransactions();
        doc.getInterchangeEnvelope().removeGroupEnvelope(doc.getInterchangeEnvelope().getGroups().get(0));
        doc.getInterchangeEnvelope().addGroupEnvelope(dummyGroup);
        for (Transaction transaction : txns) {
            dummyGroup.addTransaction(transaction);
        }
        //output the massaged document
        EdifactOutputter outputter = new EdifactOutputter();
        System.out.println(outputter.outputString(doc));
    }
    
    public void moveToRFF() {
        EdifactBuilder builder = new EdifactBuilder();
        Document doc = builder.buildDocument(CT);
        String receiverId = doc.getInterchangeEnvelope().getReceiverId();
        List<Transaction> txns = doc.getInterchangeEnvelope().getGroups().get(0).getTransactions();
        //change interchange id to INTTRA
        doc.getInterchangeEnvelope().setReceiverId("999999");
        doc.setSegmentSeparator(doc.getSegmentSeparator() + "\n"); // debug only
        next:for (Transaction transaction : txns) {
            List<Segment> segments = transaction.getSegments();
            for (Segment seg : segments) {
                if (seg.getSegmentTag().equals("RFF")) {
                    String[][] fields = {{"RFF"}, {"CS",receiverId}};
                    segments.add(segments.indexOf(seg), new Segment(fields));
                    continue next;
                }
            }
        }
        EdifactOutputter outputter = new EdifactOutputter();
        System.out.println(outputter.outputString(doc));
    }
    
    public static void main(String[] args) {
        PartnerId id = new PartnerId();
        id.moveToGroup();
        System.out.println();
        id.moveToRFF();
        
    }
}
