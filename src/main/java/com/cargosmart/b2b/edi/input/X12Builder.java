package com.cargosmart.b2b.edi.input;

import java.util.regex.Pattern;

import com.cargosmart.b2b.edi.common.Document;
import com.cargosmart.b2b.edi.common.GroupEnvelope;
import com.cargosmart.b2b.edi.common.InterchangeEnvelope;
import com.cargosmart.b2b.edi.common.Segment;
import com.cargosmart.b2b.edi.common.Transaction;
import com.cargosmart.b2b.edi.common.x12.X12Document;
import com.cargosmart.b2b.edi.common.x12.X12GroupEnvelope;
import com.cargosmart.b2b.edi.common.x12.X12InterchangeEnvelope;
import com.cargosmart.b2b.edi.common.x12.X12Transaction;

/**
 * A X12 X12Document builder to build a EDI document from file or String. 
 *  
 * @author Raymond
 *
 */
public class X12Builder extends EdiBuilder {

	/**
	 * It will build a EDI document from string.
	 * 
	 * @param content String to read from
	 * @return X12Document
	 */
	@Override
	public Document buildDocument(String content) {
		if (!content.startsWith("ISA")) {
			throw new WrongDocuemntFormatException("Not a X12 Document");
		}
		X12Document document = new X12Document();
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
			} else if (segment.getSegmentTag().equals("SE")) {
				// TODO add to transaction
			} else if (segment.getSegmentTag().equals("GE")) {
				// TODO add to group
			} else if (segment.getSegmentTag().equals("IEA")) {
				// TODO add to interchange
			} else {
				st.addSegment(segment);
			}
		}

		return document;
	}

    private Transaction buildTransaction(Segment segment) {
		return new X12Transaction(segment);
	}

	private GroupEnvelope buildGroupEnvelope(Segment segment) {
		return new X12GroupEnvelope(segment);
	}

	private InterchangeEnvelope buildInterchangeEnvelope(Segment segment) {
        return new X12InterchangeEnvelope(segment);
    }
	
	private Segment buildSegment(String segmentStr, Document document) {
	    return new Segment(splitFields(segmentStr, document));
	}

	private String[] splitFields(String content, Document document) {
		return content.split(Pattern.quote(document.getElementSeparator()));
	}
}
