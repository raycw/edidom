package com.cargosmart.b2b.edi.common;

import java.util.ArrayList;
import java.util.List;

public class CompositeField {

	private Segment segment;
	private List<Field> fields = new ArrayList<Field>();
    protected String value;
	
	public CompositeField setSegment(Segment segment) {
		this.segment = segment;
		for (Field field : fields) {
			field.setSegment(segment);
		}
		return this;
	}
	
	public Segment getSegment() {
		return segment;
	}
	
	public boolean isComposite() {
		return true;
	}
	
	public CompositeField addField(Field field) {
		fields.add(field);
		return this;
	}

    public String getValue() {
    	return value;
    }
}
