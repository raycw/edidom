package com.cargosmart.b2b.edi.common;

import java.util.List;

public interface Transaction {

    public abstract Transaction setGroupEnvelope(GroupEnvelope group);

    public abstract GroupEnvelope getGroupEnvelope();

    public abstract Transaction detach();

    public abstract Transaction addSegment(Segment segment);

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
}