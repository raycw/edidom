package com.cargosmart.b2b.edi.common;


public class Field extends CompositeField {

	private Segment segment;
	public Field() {
	}
	
	public Field(String value) {
		this.value = value;
	}
	
	public Field(Field orig) {
	    this.value = orig.value;
	}
	
	public CompositeField setSegment(Segment segment) {
		this.segment = segment;
		return this;
	}
	
	public Segment getSegment() {
		return segment;
	}
	
	@Override
	public boolean isComposite() {
		return false;
	}
	
	@Override
	public CompositeField addField(Field field) {
		return this;
	}
	
	@Override
	public Field copy() {
	    return new Field(this);
	}

}
