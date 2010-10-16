package com.cargosmart.b2b.edi.common;

import java.util.ArrayList;
import java.util.List;

public class GroupEnvelope extends Envelope{

	private InterchangeEnvelope interchangeEnvelope;
	private List<Transaction> transactions = new ArrayList<Transaction>();
	
	public GroupEnvelope(Segment segment) {
		super(segment);
	}

	public GroupEnvelope setInterchangeEnvelope(InterchangeEnvelope interchangeEnvelope) {
		this.interchangeEnvelope = interchangeEnvelope;
		return this;
	}
	
	public Envelope getInterchangeEnvelope() {
		return interchangeEnvelope;
	}
	
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
	
	public List<Transaction> getTransactions() {
		return transactions;
	}

	/**
	 * @return the functionalCode
	 */
	public String getFunctionalCode() {
		return getField(1).getValue().trim();
	}
	public void setFunctionalCode(String code) {
		getField(1).setValue(code);
	}

	/**
	 * @return the senderCode
	 */
	public String getSenderCode() {
		return getField(2).getValue().trim();
	}
	public void setSenderCode(String sender) {
		getField(2).setValue(sender);
	}

	/**
	 * @return the receiverCode
	 */
	public String getReceiverCode() {
		return getField(3).getValue().trim();
	}
	public void setReceiverCode(String receiver) {
		getField(3).setValue(receiver);
	}

	/**
	 * @return the controlNumber
	 */
	public String getControlNumber() {
		return getField(6).getValue().trim();
	}
	public void setControlNumber(String controlNum) {
		getField(6).setValue(controlNum);
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return getField(8).getValue().trim();
	}
	public void setVersion(String version) {
		getField(8).setValue(version);
	}

	/**
	 * A convenience method to find segment by tag name.
	 * 
	 * @param tag
	 * @return List of segment
	 */
	public List<Segment> getSegment(String tag) {
		List<Segment> segments = new ArrayList<Segment>();
		for (Transaction txn : transactions) {
			segments.addAll(txn.getSegments(tag));
		}
		return segments;
	}

}
