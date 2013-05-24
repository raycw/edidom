/*******************************************************************************
 * Copyright 2013 Raymond Chin
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.github.edidom.common;

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
        if (position-1 < 0 || position-1 >= fields.size()) {
            return null;
        }
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
