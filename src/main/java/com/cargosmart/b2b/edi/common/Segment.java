package com.cargosmart.b2b.edi.common;

import java.util.ArrayList;
import java.util.List;

public class Segment {

	private Transaction transaction;
	private List<CompositeField> fields = new ArrayList<CompositeField>();
	
	public Segment setTransaction(Transaction transaction) {
		this.transaction = transaction;
		return this;
	}
	
	public Transaction getTransaction() {
		return transaction;
	}
	
	public Segment addField(Field field) {
		return addCompositeField(field);
	}
	
	public List<CompositeField> getFields() {
		return fields;
	}
	
	public Segment detach() {
		transaction.removeSegment(this);
		transaction = null;
		return this;
	}
	
	public Segment addCompositeField(CompositeField field) {
		fields.add(field);
		field.setSegment(this);
		return this;
	}

}
