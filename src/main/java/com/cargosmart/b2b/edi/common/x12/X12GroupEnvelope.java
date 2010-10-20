package com.cargosmart.b2b.edi.common.x12;

import java.util.ArrayList;
import java.util.List;

import com.cargosmart.b2b.edi.common.CompositeField;
import com.cargosmart.b2b.edi.common.Envelope;
import com.cargosmart.b2b.edi.common.GroupEnvelope;
import com.cargosmart.b2b.edi.common.InterchangeEnvelope;
import com.cargosmart.b2b.edi.common.Segment;
import com.cargosmart.b2b.edi.common.Transaction;

public class X12GroupEnvelope extends Envelope implements GroupEnvelope{

	private InterchangeEnvelope interchangeEnvelope;
	private List<Transaction> transactions = new ArrayList<Transaction>();
	
	public X12GroupEnvelope(Segment segment) {
		super(segment);
	}

	public GroupEnvelope setInterchangeEnvelope(InterchangeEnvelope interchangeEnvelope) {
		this.interchangeEnvelope = interchangeEnvelope;
		return this;
	}
	
	/* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.x12.GroupEnvelope#getInterchangeEnvelope()
     */
	public InterchangeEnvelope getInterchangeEnvelope() {
		return interchangeEnvelope;
	}
	
	/* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.x12.GroupEnvelope#getField(int)
     */
	public CompositeField getField(int position) {
		return segment.getField(position);
	}
	
	public GroupEnvelope detach() {
		if (interchangeEnvelope != null) {
			interchangeEnvelope.removeGroupEnvelope(this);
		}
		return this;
	}
	
	public GroupEnvelope addTransaction(Transaction txn) {
		transactions.add(txn);
		txn.setGroupEnvelope(this);
		return this;
	}
	
	public GroupEnvelope removeTransaction(Transaction txn) {
		transactions.remove(txn);
		txn.setGroupEnvelope(null);
		return this;
	}
	
	/* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.x12.GroupEnvelope#getTransactions()
     */
	public List<Transaction> getTransactions() {
		return transactions;
	}

	/* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.x12.GroupEnvelope#getFunctionalCode()
     */
	public String getFunctionalCode() {
		return getField(1).getValue().trim();
	}
	/* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.x12.GroupEnvelope#setFunctionalCode(java.lang.String)
     */
	public void setFunctionalCode(String code) {
		getField(1).setValue(code);
	}

	/* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.x12.GroupEnvelope#getSenderCode()
     */
	public String getSenderCode() {
		return getField(2).getValue().trim();
	}
	/* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.x12.GroupEnvelope#setSenderCode(java.lang.String)
     */
	public void setSenderCode(String sender) {
		getField(2).setValue(sender);
	}

	/* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.x12.GroupEnvelope#getReceiverCode()
     */
	public String getReceiverCode() {
		return getField(3).getValue().trim();
	}
	/* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.x12.GroupEnvelope#setReceiverCode(java.lang.String)
     */
	public void setReceiverCode(String receiver) {
		getField(3).setValue(receiver);
	}

	/* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.x12.GroupEnvelope#getControlNumber()
     */
	public String getControlNumber() {
		return getField(6).getValue().trim();
	}
	/* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.x12.GroupEnvelope#setControlNumber(java.lang.String)
     */
	public void setControlNumber(String controlNum) {
		getField(6).setValue(controlNum);
	}

	/* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.x12.GroupEnvelope#getVersion()
     */
	public String getVersion() {
		return getField(8).getValue().trim();
	}
	/* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.x12.GroupEnvelope#setVersion(java.lang.String)
     */
	public void setVersion(String version) {
		getField(8).setValue(version);
	}

	/* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.x12.GroupEnvelope#getSegment(java.lang.String)
     */
	public List<Segment> getSegment(String tag) {
		List<Segment> segments = new ArrayList<Segment>();
		for (Transaction txn : transactions) {
			segments.addAll(txn.getSegments(tag));
		}
		return segments;
	}

}
