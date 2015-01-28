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
package com.github.raycw.edidom.common;

import java.util.ArrayList;
import java.util.List;


public class CompositeField {

//	private Segment segment;
	private List<Field> fields;
//    public CompositeField setSegment(Segment segment) {
//		this.segment = segment;
//		for (Field field : fields) {
//			field.setSegment(segment);
//		}
//		return this;
//	}
	
	public CompositeField(CompositeField orig) {
        this(orig.fields.size());
	    for (Field field : orig.fields) {
            this.fields.add(field.copy());
        }
	}
	
	public CompositeField() {
	    fields = new ArrayList<Field>(5);
    }
	
    public CompositeField(int initCapacity) {
        fields = new ArrayList<Field>(initCapacity);
    }

    /*
	 * It's for Field object to avoid fields initialization 
	 */
	protected CompositeField(Field field) {
	}

//    public Segment getSegment() {
//		return segment;
//	  }
	
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
	
	public void setFieldValue(int position, String field) {
        if (position-1 < 0 || position-1 >= fields.size()) {
            fields.set(position-1, Field.create(field));
        }	    
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

//	public void setValue(String value) {
//        Field field = fields.get(0);
//        if (field != null) {
//            fields.set(0, Field.create(value));
//        }
//	}
	
	public String[] getValues() {
	    String[] values = new String[fields.size()];
	    for (int i = 0; i < fields.size(); i++) {
            values[i] = fields.get(i).getValue();
        }
	    return values;
	}
	
//	public void setValues(String[] values) {
//	    if (fields.size() == values.length) {
//    	    for (int i = 0; i < values.length; i++) {
//    	        fields.set(i, Field.create((values[i])));
//            }
//	    }
//	}

    public CompositeField copy() {
        return new CompositeField(this);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fields == null) ? 0 : fields.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        CompositeField other = (CompositeField) obj;
        if (fields == null) {
            if (other.fields != null) return false;
        } else if (!fields.equals(other.fields)) return false;
        return true;
    }
	
}
