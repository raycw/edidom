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
package com.github.edidom.common;

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
