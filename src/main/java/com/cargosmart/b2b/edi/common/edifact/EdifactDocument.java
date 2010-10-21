/**
 * 
 */
package com.cargosmart.b2b.edi.common.edifact;

import java.util.ArrayList;
import java.util.List;

import com.cargosmart.b2b.edi.common.Document;
import com.cargosmart.b2b.edi.common.InterchangeEnvelope;
import com.cargosmart.b2b.edi.common.Segment;

/**
 * An EDI document. It is the root element to access EDI message.
 * 
 * @author Raymond Chin
 * 
 */
public class EdifactDocument implements Document {

	private Segment level;
	private InterchangeEnvelope interchange;
    private String segmentSeparator;
    private String elementSeparator;
    private String subElementSeparator;

	public EdifactDocument() {

	}
	
	/**
	 * @return the level
	 */
	public Segment getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(Segment level) {
		this.level = level;
	}

	/* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.DocumentI#setInterchangeEnvelope(com.cargosmart.b2b.edi.common.InterchangeEnvelope)
     */
	public Document setInterchangeEnvelope(InterchangeEnvelope interchange) {
		this.interchange = interchange;
		interchange.setDocument(this);
		return this;
	}

	/* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.DocumentI#getInterchangeEnvelope()
     */
	public InterchangeEnvelope getInterchangeEnvelope() {
		return interchange;
	}

	/* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.DocumentI#setElementSeparator(java.lang.String)
     */
    public void setElementSeparator(String elementSeparator) {
        this.elementSeparator = elementSeparator;        
    }
    
    /* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.DocumentI#getElementSeparator()
     */
    public String getElementSeparator() {
        return elementSeparator;
    }

    /* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.DocumentI#getSegmentSeparator()
     */
    public String getSegmentSeparator() {
        return segmentSeparator;
    }

    /* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.DocumentI#setSegmentSeparator(java.lang.String)
     */
    public void setSegmentSeparator(String segmentSeparator) {
        this.segmentSeparator = segmentSeparator;
    }

    /* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.DocumentI#getSubElementSeparator()
     */
    public String getSubElementSeparator() {
        return subElementSeparator;
    }

    /* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.DocumentI#setSubElementSeparator(java.lang.String)
     */
    public void setSubElementSeparator(String subElementSeparator) {
        this.subElementSeparator = subElementSeparator;
    }

	/* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.DocumentI#getSegment(java.lang.String)
     */
	public List<Segment> getSegment(String tag) {
		List<Segment> segments = new ArrayList<Segment>();
		segments.addAll(interchange.getSegment(tag));
		return segments;
	}

}
