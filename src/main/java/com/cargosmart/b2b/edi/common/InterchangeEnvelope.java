package com.cargosmart.b2b.edi.common;

import java.util.List;


public interface InterchangeEnvelope {

    public abstract InterchangeEnvelope setDocument(Document document);

    public abstract Document getDocument();

    public abstract InterchangeEnvelope addGroupEnvelope(
            GroupEnvelope groupEnvelope);

    public abstract InterchangeEnvelope removeGroupEnvelope(
            GroupEnvelope groupEnvelope);

    public abstract List<GroupEnvelope> getGroups();

    /**
     * Gets Interchange field by position. The position is starting from 1.
     * 
     * @param position start from 1
     * @return Field
     */
    public abstract CompositeField getField(int position);

    public abstract String getSenderQualifier();

    public abstract void setSenderQualifier(String qualifier);

    public abstract String getSenderId();

    public abstract void setSenderId(String id);

    public abstract String getReceiverQualifier();

    public abstract void setReceiverQualifier(String qualifier);

    public abstract String getReceiverId();

    public abstract void setReceiverId(String id);

    public abstract String getVersion();

    public abstract void setVersion(String version);

    public abstract String getControlNumber();

    public abstract void setControlNumber(String controlNum);

    public abstract List<Segment> getSegment(String tag);

    public abstract List<CompositeField> getFields();
}