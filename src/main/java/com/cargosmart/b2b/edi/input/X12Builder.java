package com.cargosmart.b2b.edi.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import com.cargosmart.b2b.edi.common.Document;
import com.cargosmart.b2b.edi.common.GroupEnvelope;
import com.cargosmart.b2b.edi.common.InterchangeEnvelope;
import com.cargosmart.b2b.edi.common.Segment;
import com.cargosmart.b2b.edi.common.Transaction;

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
		
		String[] segments = content.split(Pattern.quote(segmentSeparator));
		
		// assume 1st segment is ISA
		InterchangeEnvelope isa = buildInterchangeEnvelope(buildSegment(segments[0], document));
		document.setInterchangeEnvelope(isa);
		// 2nd segment is GS
		GroupEnvelope gs = buildGroupEnvelope(buildSegment(segments[1], document));
		isa.addGroupEnvelope(gs);
		// 3rd segment is ST
		Transaction st = buildTransaction(buildSegment(segments[2], document));
		gs.addTransaction(st);

		for (int i = 3; i < segments.length; i++) {
			Segment segment = buildSegment(segments[i], document);
			if (segment.getSegmentTag().equals("GS")) {
				gs = buildGroupEnvelope(segment);
				isa.addGroupEnvelope(gs);
			} else if (segment.getSegmentTag().equals("ST")) {
				st = buildTransaction(segment);
				gs.addTransaction(st);
			} else {
				st.addSegment(segment);
			}
		}

		return document;
	}

    private Transaction buildTransaction(Segment segment) {
		return new Transaction(segment);
	}

	private GroupEnvelope buildGroupEnvelope(Segment segment) {
		return new GroupEnvelope(segment);
	}

	private InterchangeEnvelope buildInterchangeEnvelope(Segment segment) {
        return new InterchangeEnvelope(segment);
    }
	
	private Segment buildSegment(String segmentStr, Document document) {
	    return new Segment(splitFields(segmentStr, document));
	}

	private String[] splitFields(String content, Document document) {
		return content.split(Pattern.quote(document.getElementSeparator()));
	}
}
