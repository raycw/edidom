package com.github.edi.common;

import java.util.List;

public interface Document {

    /**
     * Sets the InterchangeEnvelope of the <code>X12Document</code>. 
     * 
     * X12Document only contains 1 <code>InterchangeEnvelope</code>. 
     * @param interchange new content of the <code>X12Document</code>
     * @return this document
     */
    public abstract Document setInterchangeEnvelope(
            InterchangeEnvelope interchange);

    /**
     * Returns the <code>InterchangeEnvelope</code> of the EDI document.
     * @return InterchangeEnvelope of this <code>X12Document</code>
     */
    public abstract InterchangeEnvelope getInterchangeEnvelope();

    /**
     * Sets the document element separator
     * 
     * @param elementSeparator
     */
    public abstract void setElementSeparator(String elementSeparator);

    /**
     * Gets the document element separator
     * 
     * @return element separator
     */
    public abstract String getElementSeparator();

    /**
     * Sets the document segment separator
     * 
     * @return segment separator
     */
    public abstract String getSegmentSeparator();

    /**
     * Sets the document segment separator
     * 
     * @param segmentSeparator
     */
    public abstract void setSegmentSeparator(String segmentSeparator);

    /**
     * Gets the document sub-element separator
     * 
     * @return sub-element separator
     */
    public abstract String getSubElementSeparator();

    /**
     * Sets the document sub-element separator
     * 
     * @param subElementSeparator
     */
    public abstract void setSubElementSeparator(String subElementSeparator);

    /**
     * Use getSegments instead
     * 
     * @deprecated
     * @param tag
     * @return
     */
    public abstract List<Segment> getSegment(String tag);
    
    /**
     * Returns List of Segments of provided tag name
     * 
     * @param tag 
     * @return List of segments
     */
    public abstract List<Segment> getSegments(String tag);

    /**
     * Gets the document release character
     * 
     * @return release character
     */
    public abstract String getReleaseCharacter();
    
    /**
     * Sets the document release character
     * 
     * @param releaseCharacter
     */
    public abstract void setReleaseCharacter(String releaseCharacter);
    
    /**
     * Shallow copy itself without any relationship
     * 
     * @return a copy of Document
     */
    public abstract Document copy();
    
}