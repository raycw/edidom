package com.cargosmart.b2b.edi.common;

import java.util.ArrayList;
import java.util.List;

public class GroupEnvelope {

	private InterchangeEnvelope interchangeEnvelope;
	private List<Transaction> transactions = new ArrayList<Transaction>();
	
	public GroupEnvelope setInterchangeEnvelope(InterchangeEnvelope interchangeEnvelope) {
		this.interchangeEnvelope = interchangeEnvelope;
		return this;
	}
	
	public InterchangeEnvelope getInterchangeEnvelope() {
		return interchangeEnvelope;
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
	
	

}
