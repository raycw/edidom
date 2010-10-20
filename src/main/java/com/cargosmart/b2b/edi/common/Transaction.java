package com.cargosmart.b2b.edi.common;

import java.util.List;

public interface Transaction {

    public abstract Transaction setGroupEnvelope(GroupEnvelope group);

    public abstract GroupEnvelope getGroupEnvelope();

    public abstract Transaction detach();

    public abstract Transaction addSegment(Segment segment);

    public abstract Transaction removeSegment(Segment segment);

    public abstract List<Segment> getSegements();

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
}