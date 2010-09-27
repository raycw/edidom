package com.cargosmart.b2b.edi.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import com.cargosmart.b2b.edi.common.Document;
import com.cargosmart.b2b.edi.common.InterchangeEnvelope;

/**
 * A X12 Document builder to build a EDI document from file or String. 
 *  
 * @author Raymond
 *
 */
public class X12Builder {

	/**
	 * It will build a EDI document from file.
	 * 
	 * @param file File to read from
	 * @return Document 
	 * @throws IOException when an I/O error prevents a document from being fully parsed
	 */
	public Document buildDocument(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		char[] buffer = new char[1024];
		int nRead;
		StringBuilder content = new StringBuilder();
		while ((nRead = reader.read(buffer, 0, 1024)) != -1) {
			content.append(buffer, 0, nRead);
		}
		return buildDocument(content.toString());
	}

	/**
	 * It will build a EDI document from string.
	 * 
	 * @param content String to read from
	 * @return Document
	 */
	public Document buildDocument(String content) {
		if (!content.startsWith("ISA")) {
			throw new WrongDocuemntFormatException("Not a X12 Document");
		}
		Document document = new Document();
		String elementSeparator = content.substring(3, 4);
		document.setElementSeparator(elementSeparator);
		String[] isaSegment = content.split(Pattern.quote(elementSeparator), 18);
		// to get the segment separator, get the ISA16 value, it contains  
		// sub-element separator,  segment separator and GS. 
		// for example: :~\nGS
		String segmentSeparator = isaSegment[16].substring(1).replace("GS", "");
		String subElementSeparator = isaSegment[16].substring(0, 1);
		document.setSegmentSeparator(segmentSeparator);
		document.setSubElementSeparator(subElementSeparator);
		
		InterchangeEnvelope isa = buildInterchangeEnvelope(content, document);
		document.setInterchangeEnvelope(isa);
		
		return document;
	}

    public InterchangeEnvelope buildInterchangeEnvelope(String content, Document document) {
        String isaString = content.split(Pattern.quote(document.getSegmentSeparator()), 2)[0];
        String[] isaFields = isaString.split(Pattern.quote(document.getElementSeparator()));
        InterchangeEnvelope isa = new InterchangeEnvelope(isaFields);
        
        return isa;
    }
}
