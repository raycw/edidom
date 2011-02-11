package com.cargosmart.b2b.edi.input;

import com.cargosmart.b2b.edi.common.Document;
import com.cargosmart.b2b.edi.common.Segment;
import com.cargosmart.b2b.edi.common.edifact.EdifactDocument;
import com.cargosmart.b2b.edi.common.edifact.EdifactInterchangeEnvelope;

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
		Segment segments[] = splitSegment(content, document); 
		EdifactInterchangeEnvelope interchange;
		return document;
	}

    private Segment[] splitSegment(String content, EdifactDocument document) {
        // TODO Auto-generated method stub
        return null;
    }

}
