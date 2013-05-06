package com.github.edi.common;


public class Field extends CompositeField {

	private Segment segment;
    protected String value;
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
	public String getValue() {
	    return value;
	}
	
	@Override
	public void setValue(String value) {
	    this.value = value;
	}
	
	@Override
	public String[] getValues() {
	    String[] values = {value};
	    return values;
	}
	
	@Override
	public void setValues(String[] values) {
	    this.value = values[0];
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
