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
package com.github.raycw.edidom.input;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.github.raycw.edidom.common.Document;
import com.github.raycw.edidom.common.Segment;
import com.github.raycw.edidom.common.edifact.EdifactDocument;
import com.github.raycw.edidom.common.edifact.EdifactGroupEnvelope;
import com.github.raycw.edidom.common.edifact.EdifactInterchangeEnvelope;
import com.github.raycw.edidom.common.edifact.EdifactTransaction;
import com.github.raycw.edidom.common.edifact.EmptyGroupEnvelope;

/**
 * A X12 X12Document builder to build a EDI document from file or String.
 * 
 * @author Raymond
 * 
 */
public class EdifactBuilder extends EdiBuilder {

    /*
     * For performance boosting
     */
    private Pattern segmentSeparator;
    private Pattern elementSeparator;
    private Pattern subElementSeparator;
    
	/**
	 * It will build a EDI document from string.
	 * 
	 * @param content
	 *            String to read from
	 * @return EdifactDocument
	 */
	@Override
	public Document buildDocument(String content) {
		if (!content.startsWith("UNA") && !content.startsWith("UNB")) {
			throw new WrongDocuemntFormatException("Not a Edifact Document");
		}
		EdifactDocument document = new EdifactDocument();
		Segment levelA = null;
		if (content.startsWith("UNA")) {
			String[] levelAStrArray = { content.substring(0, 3),
					content.substring(3, 4), content.substring(4, 5),
					content.substring(5, 6), content.substring(6, 7),
					content.substring(7, 8), content.substring(8, 10) };
			if (levelAStrArray[6].endsWith("U")) {
				levelAStrArray[6] = content.substring(8, 9);
				content = content.substring(9, content.length());
			} else {
				content = content.substring(10, content.length());
			}
			levelA = new Segment(levelAStrArray);
			document.setSegmentSeparator(levelAStrArray[6]);
			document.setElementSeparator(levelAStrArray[2]);
			document.setSubElementSeparator(levelAStrArray[1]);
			document.setReleaseCharacter(levelAStrArray[4]);
		} else {
			//document.setElementSeparator(content.substring(3, 4));
			//Assign defaults character. TODO - read content to recognize the delimiter 
		    document.setSubElementSeparator(":");
		    document.setElementSeparator("+");
		    document.setReleaseCharacter("?");
		    document.setSegmentSeparator("'");
		}
		segmentSeparator = Pattern.compile(document.getSegmentSeparator(), Pattern.LITERAL);
		elementSeparator = Pattern.compile(document.getElementSeparator(), Pattern.LITERAL);
		subElementSeparator = Pattern.compile(document.getSubElementSeparator(), Pattern.LITERAL);
		
		String segments[] = splitSegment(content, document);
		//assume 1st segment is interchange
		EdifactInterchangeEnvelope interchange = new EdifactInterchangeEnvelope(levelA, buildSegment(segments[0], document));
		document.setInterchangeEnvelope(interchange);
		//check group
		int txnIdex = 2;
		EdifactGroupEnvelope group;
		if (segments[1].startsWith("UNG")) {
		    group = new EdifactGroupEnvelope(buildSegment(segments[1], document));
		} else {
		    group = new EmptyGroupEnvelope();
		    txnIdex = 1;
		}
		interchange.addGroupEnvelope(group);
		EdifactTransaction txn = new EdifactTransaction(buildSegment(segments[txnIdex], document));
		group.addTransaction(txn);
		for (int i = txnIdex+1; i < segments.length; i++) {
			 Segment segment = buildSegment(segments[i], document);
			 String segmentTag = segment.getSegmentTag();
			 if (segmentTag.equals("UNG")) {
				 group = new EdifactGroupEnvelope(segment);
				 interchange.addGroupEnvelope(group);
			 } else if (segmentTag.equals("UNH")) {
				 txn = new EdifactTransaction(segment);
				 group.addTransaction(txn);
			 } else if (segmentTag.equals("UNT")) {
				 // TODO add to transaction
			 } else if (segmentTag.equals("UNE")) {
				 // TODO add to group
			 } else if (segmentTag.equals("UNZ")) {
				 // TODO add to interchange
			 } else {
				 txn.addSegment(segment);
			 }
		}
		return document;
	}

    private Segment buildSegment(String segmentStr, EdifactDocument document) {
        return new Segment(splitFields(segmentStr, document));
    }
    
    private String[] splitSegment(String content, EdifactDocument document) {
        return splitStringWithReleaseChar(content, document.getReleaseCharacter(), segmentSeparator, false);
    }
    
    private String[][] splitFields(String segmentStr, EdifactDocument document) {
        String releaseChar = document.getReleaseCharacter();
        Pattern p = Pattern.compile(Pattern.quote(releaseChar+releaseChar));
    	String[] fields = splitStringWithReleaseChar(segmentStr, releaseChar, elementSeparator, true);
    	String[][] compositeFields = new String[fields.length][];
    	for (int i = 0; i < fields.length; i++) {
			String field = fields[i];
			String[] cFields = splitStringWithReleaseChar(field, releaseChar, subElementSeparator, true);
			compositeFields[i] = new String[cFields.length];
			for (int j = 0; j < cFields.length; j++) {
			    if (cFields[j].contains(releaseChar+releaseChar)) {
			        compositeFields[i][j] = p.matcher(cFields[j]).replaceAll(releaseChar);
			    } else {
			        compositeFields[i][j] = cFields[j];
			    }
			}
		}
        return compositeFields;
    }

    private String[] splitStringWithReleaseChar(String content,
            String releaseChar, Pattern delimiter, boolean reserveEmpty) {
        String[] segmentWithoutReleaseChar = delimiter.split(content, reserveEmpty?-1:0);
        List<String> segmentWithReleaseChar = new ArrayList<String>();
        for (int i = 0; i < segmentWithoutReleaseChar.length; i++) {
            String segmentStr = segmentWithoutReleaseChar[i];
            while (segmentStr.endsWith(releaseChar)) {
                segmentStr = segmentStr.substring(0, segmentStr.length()-1) + delimiter + segmentWithoutReleaseChar[++i];
            }
            segmentWithReleaseChar.add(segmentStr);
        }
        return segmentWithReleaseChar.toArray(new String[segmentWithReleaseChar.size()]);
    }
}
