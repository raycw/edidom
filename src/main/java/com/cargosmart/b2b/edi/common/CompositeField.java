package com.cargosmart.b2b.edi.common;

import java.util.ArrayList;
import java.util.List;


public class CompositeField {

	private Segment segment;
	private List<Field> fields = new ArrayList<Field>();
    public CompositeField setSegment(Segment segment) {
		this.segment = segment;
		for (Field field : fields) {
			field.setSegment(segment);
		}
		return this;
	}
	
	public CompositeField(CompositeField orig) {
	    for (Field field : orig.fields) {
            this.fields.add(field.copy());
        }
	}
	
	public CompositeField() {
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
	
	public Field getField(int position) {
		return fields.get(position-1);
	}
	
	public List<Field> getFields() {
	    return fields;
	}

    public String getValue() {
        Field field = fields.get(0);
        if (field != null) {
            return field.getValue();
        }
    	return null;
    }

	public void setValue(String value) {
        Field field = fields.get(0);
        if (field != null) {
            field.setValue(value);
        }
	}
	
	public String[] getValues() {
	    String[] values = new String[fields.size()];
	    for (int i = 0; i < fields.size(); i++) {
            values[i] = fields.get(i).getValue();
        }
	    return values;
	}
	
	public void setValues(String[] values) {
	    if (fields.size() == values.length) {
    	    for (int i = 0; i < values.length; i++) {
    	        fields.get(i).setValue(values[i]);
            }
	    }
	}

    public CompositeField copy() {
        return new CompositeField(this);
    }
	
}
