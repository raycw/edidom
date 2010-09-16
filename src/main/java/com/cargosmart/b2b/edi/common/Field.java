package com.cargosmart.b2b.edi.common;

public class Field extends CompositeField {

	private Segment segment;
	private String value;
	
	
	public Field() {
		
	}
	
	public Field(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public Field setSegment(Segment segment) {
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
	public Field addField(Field field) {
		return this;
	}

}
