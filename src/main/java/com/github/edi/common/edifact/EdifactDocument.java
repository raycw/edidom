/**
 * 
 */
package com.github.edi.common.edifact;

import java.util.ArrayList;
import java.util.List;

import com.github.edi.common.Document;
import com.github.edi.common.InterchangeEnvelope;
import com.github.edi.common.Segment;

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
    private String releaseCharacter;

	public EdifactDocument() {

	}
	
	public EdifactDocument(EdifactDocument orig) {
		this.segmentSeparator = orig.segmentSeparator;
		this.elementSeparator = orig.elementSeparator;
		this.subElementSeparator = orig.subElementSeparator;
		this.releaseCharacter = orig.releaseCharacter;
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
     * @see com.github.edi.common.DocumentI#setInterchangeEnvelope(com.github.edi.common.InterchangeEnvelope)
     */
	public Document setInterchangeEnvelope(InterchangeEnvelope interchange) {
		this.interchange = interchange;
		interchange.setDocument(this);
		return this;
	}

	/* (non-Javadoc)
     * @see com.github.edi.common.DocumentI#getInterchangeEnvelope()
     */
	public InterchangeEnvelope getInterchangeEnvelope() {
		return interchange;
	}

	/* (non-Javadoc)
     * @see com.github.edi.common.DocumentI#setElementSeparator(java.lang.String)
     */
    public void setElementSeparator(String elementSeparator) {
        this.elementSeparator = elementSeparator;        
    }
    
    /* (non-Javadoc)
     * @see com.github.edi.common.DocumentI#getElementSeparator()
     */
    public String getElementSeparator() {
        return elementSeparator;
    }

    /* (non-Javadoc)
     * @see com.github.edi.common.DocumentI#getSegmentSeparator()
     */
    public String getSegmentSeparator() {
        return segmentSeparator;
    }

    /* (non-Javadoc)
     * @see com.github.edi.common.DocumentI#setSegmentSeparator(java.lang.String)
     */
    public void setSegmentSeparator(String segmentSeparator) {
        this.segmentSeparator = segmentSeparator;
    }

    /* (non-Javadoc)
     * @see com.github.edi.common.DocumentI#getSubElementSeparator()
     */
    public String getSubElementSeparator() {
        return subElementSeparator;
    }

    /* (non-Javadoc)
     * @see com.github.edi.common.DocumentI#setSubElementSeparator(java.lang.String)
     */
    public void setSubElementSeparator(String subElementSeparator) {
        this.subElementSeparator = subElementSeparator;
    }

	/* (non-Javadoc)
     * @see com.github.edi.common.DocumentI#getSegment(java.lang.String)
     */
	public List<Segment> getSegment(String tag) {
		return getSegments(tag);
	}

    public String getReleaseCharacter() {
        return releaseCharacter;
    }

    public void setReleaseCharacter(String releaseCharacter) {
        this.releaseCharacter = releaseCharacter;
    }

	public Document copy() {
		return new EdifactDocument(this);
	}

    public List<Segment> getSegments(String tag) {
        List<Segment> segments = new ArrayList<Segment>();
        segments.addAll(interchange.getSegment(tag));
        return segments;
    }

}
