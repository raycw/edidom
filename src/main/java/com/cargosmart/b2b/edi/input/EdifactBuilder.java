package com.cargosmart.b2b.edi.input;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.cargosmart.b2b.edi.common.Document;
import com.cargosmart.b2b.edi.common.Segment;
import com.cargosmart.b2b.edi.common.edifact.EdifactDocument;
import com.cargosmart.b2b.edi.common.edifact.EdifactGroupEnvelope;
import com.cargosmart.b2b.edi.common.edifact.EdifactInterchangeEnvelope;
import com.cargosmart.b2b.edi.common.edifact.EdifactTransaction;
import com.cargosmart.b2b.edi.common.edifact.EmptyGroupEnvelope;

/**
 * A X12 X12Document builder to build a EDI document from file or String.
 * 
 * @author Raymond
 * 
 */
public class EdifactBuilder extends EdiBuilder {

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
			String[] levelAStrArray = { content.substring(3),
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
		
		return document;
	}

    private Segment buildSegment(String segmentStr, EdifactDocument document) {
        return new Segment(splitFields(segmentStr, document));
    }
    
    private String[] splitSegment(String content, EdifactDocument document) {
        return splitStringWithReleaseChar(content, document.getReleaseCharacter(), document.getSegmentSeparator());
    }
    
    private String[] splitFields(String segmentStr, EdifactDocument document) {
        return splitStringWithReleaseChar(segmentStr, document.getReleaseCharacter(), document.getElementSeparator());
    }

    private String[] splitStringWithReleaseChar(String content,
            String releaseChar, String delimiter) {
        String[] segmentWithoutReleaseChar = content.split(Pattern.quote(delimiter));
        List<String> segmentWithReleaseChar = new ArrayList<String>();
        for (int i = 0; i < segmentWithoutReleaseChar.length; i++) {
            String segmentStr = segmentWithoutReleaseChar[i];
            while (segmentStr.endsWith(releaseChar)) {
                segmentStr += segmentWithoutReleaseChar[++i];
            }
            segmentWithReleaseChar.add(segmentStr);
        }
        return segmentWithReleaseChar.toArray(new String[segmentWithReleaseChar.size()]);
    }
}
