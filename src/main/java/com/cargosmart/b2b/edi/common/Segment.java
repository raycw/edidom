package com.cargosmart.b2b.edi.common;

import java.util.ArrayList;
import java.util.List;


public final class Segment {

    private Transaction transaction;
	private List<CompositeField> fields = new ArrayList<CompositeField>();
	private String segmentTag;
	
	public Segment(String[] fields) {
	    segmentTag = fields[0];
	    for (int i = 0; i < fields.length; i++) {
            Field field = new Field(fields[i]);
            addField(field);
        }
	}
	
	public Segment(String[][] fields) {
		segmentTag = fields[0][0];
		for (int i = 0; i < fields.length; i++) {
			CompositeField cField = new CompositeField();
			for (int j = 0; j < fields[i].length; j++) {
				Field field = new Field(fields[i][j]);
				cField.addField(field);
			}
			addCompositeField(cField);
		}
	}
	
	/**
	 * Copy constructor
	 * 
	 * @param orig to copy
	 */
	public Segment(Segment orig) {
		this.segmentTag = orig.segmentTag;
		for (CompositeField cField : orig.fields) {
            this.fields.add(cField.copy());
        }
	}
	
	public Segment setTransaction(Transaction transaction) {
		this.transaction = transaction;
		return this;
	}
	
    public String getSegmentTag() {
        return segmentTag;
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
	
	public CompositeField getField(int position) {
	    return fields.get(position);
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
	
	/**
	 * Shallow copy without relationship
	 * 
	 * @return copy of this instance
	 */
	public Segment copy() {
		return new Segment(this);
	}

}
