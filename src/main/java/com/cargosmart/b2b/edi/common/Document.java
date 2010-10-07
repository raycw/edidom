/**
 * 
 */
package com.cargosmart.b2b.edi.common;

import java.util.ArrayList;
import java.util.List;

/**
 * An EDI document. It is the root element to access EDI message.
 * 
 * @author Raymond Chin
 * 
 */
public class Document {

	private InterchangeEnvelope interchange;
    private String segmentSeparator;
    private String elementSeparator;
    private String subElementSeparator;

	public Document() {

	}
	
	/**
	 * Sets the InterchangeEnvelope of the <code>Document</code>. 
	 * 
	 * Document only contains 1 <code>InterchangeEnvelope</code>. 
	 * @param interchange new content of the <code>Document</code>
	 * @return this document
	 */
	public Document setInterchangeEnvelope(InterchangeEnvelope interchange) {
		this.interchange = interchange;
		interchange.setDocument(this);
		return this;
	}

	/**
	 * Returns the <code>InterchangeEnvelope</code> of the EDI document.
	 * @return InterchangeEnvelope of this <code>Document</code>
	 */
	public InterchangeEnvelope getInterchangeEnvelope() {
		return interchange;
	}

	/**
	 * Sets the document element separator
	 * 
	 * @param elementSeparator
	 */
    public void setElementSeparator(String elementSeparator) {
        this.elementSeparator = elementSeparator;        
    }
    
    /**
     * Gets the document element separator
     * 
     * @return element separator
     */
    public String getElementSeparator() {
        return elementSeparator;
    }

    /**
     * Sets the document segment separator
     * 
     * @return segment separator
     */
    public String getSegmentSeparator() {
        return segmentSeparator;
    }

    /**
     * Sets the document segment separator
     * 
     * @param segmentSeparator
     */
    public void setSegmentSeparator(String segmentSeparator) {
        this.segmentSeparator = segmentSeparator;
    }

    /**
     * Gets the document sub-element separator
     * 
     * @return sub-element separator
     */
    public String getSubElementSeparator() {
        return subElementSeparator;
    }

    /**
     * Sets the document sub-element separator
     * 
     * @param subElementSeparator
     */
    public void setSubElementSeparator(String subElementSeparator) {
        this.subElementSeparator = subElementSeparator;
    }

	public List<Segment> getSegment(String tag) {
		List<Segment> segments = new ArrayList<Segment>();
		segments.addAll(interchange.getSegment(tag));
		return segments;
	}

}
