package com.cargosmart.b2b.edi.common;

import java.util.List;

public interface GroupEnvelope {

    public abstract GroupEnvelope setInterchangeEnvelope(
            InterchangeEnvelope interchangeEnvelope);

    public abstract InterchangeEnvelope getInterchangeEnvelope();

    public abstract CompositeField getField(int position);

    public abstract GroupEnvelope detach();

    public abstract GroupEnvelope addTransaction(Transaction txn);

    public abstract GroupEnvelope removeTransaction(Transaction txn);

    public abstract List<Transaction> getTransactions();

    /**
     * @return the functionalCode
     */
    public abstract String getFunctionalCode();

    public abstract void setFunctionalCode(String code);

    /**
     * @return the senderCode
     */
    public abstract String getSenderCode();

    public abstract void setSenderCode(String sender);

    /**
     * @return the receiverCode
     */
    public abstract String getReceiverCode();

    public abstract void setReceiverCode(String receiver);

    /**
     * @return the controlNumber
     */
    public abstract String getControlNumber();

    public abstract void setControlNumber(String controlNum);

    /**
     * @return the version
     */
    public abstract String getVersion();

    public abstract void setVersion(String version);

    /**
     * A convenience method to find segment by tag name.
     * 
     * @param tag
     * @return List of segment
     */
    public abstract List<Segment> getSegment(String tag);

    public abstract List<CompositeField> getFields();
}