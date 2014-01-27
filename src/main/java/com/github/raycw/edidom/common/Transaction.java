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
package com.github.raycw.edidom.common;

import java.util.Collection;
import java.util.List;

public interface Transaction {

    public abstract Transaction setGroupEnvelope(GroupEnvelope group);

    public abstract GroupEnvelope getGroupEnvelope();

    public abstract Transaction detach();

    public abstract Transaction addSegment(Segment segment);
    
    public abstract Transaction addSegment(int position, Segment segment);
    
    public abstract Transaction addSegment(int position, Collection<Segment> segments);

    public abstract Transaction removeSegment(Segment segment);

    /**
     * Use getSegments instead
     * @deprecated
     * @return
     */
    public abstract List<Segment> getSegements();
    
    /**
     * 
     * @return
     */
    public abstract List<Segment> getSegments();

    /**
     * Gets Transaction field by position. The position is starting from 1.
     * 
     * @param position start from 1
     * @return Field
     */
    public abstract CompositeField getField(int position);

    public abstract String getType();

    public abstract void setType(String type);

    public abstract String getControlNumber();

    public abstract void setControlNumber(String controlNum);

    public abstract List<Segment> getSegments(String tag);

    public abstract List<CompositeField> getFields();
    
    /**
     * Shallow copy without relationship.
     * 
     * @return copy of this instance
     */
    public abstract Transaction copy();

    public abstract int getSegmentPosition(Segment segment);

    public abstract Segment getSegment(int position);
    
    public abstract List<LoopGroup> getLoopGroups(String startTag, String endTag);
}
