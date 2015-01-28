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

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;


public class Field extends CompositeField {

    protected String value;
    private final static Field DUMMY_FIELD = new Field();
    private final static WeakHashMap<String, WeakReference<Field>> FLYWEIGHT = new WeakHashMap<String, WeakReference<Field>>();
        
    final public static Field create(String value) {
        WeakReference<Field> ref = FLYWEIGHT.get(value);
        Field field = null;
        if (ref != null) {
            field = ref.get();
        }
        if (field == null) {
            field = new Field(value);
            FLYWEIGHT.put(value, new WeakReference<Field>(field));
        }
        return field;
    }
    
	private Field() {
	    super(DUMMY_FIELD);//avoid to initialize useless 'fields'
	}
	
	private Field(String value) {
        super(DUMMY_FIELD);//avoid to initialize useless 'fields'
		this.value = value;
	}
	
	public Field(Field orig) {
        super(DUMMY_FIELD);//avoid to initialize useless 'fields'
	    this.value = orig.value;
	}
	
	@Override
	public String getValue() {
	    return value;
	}
	
	@Override
	public void setFieldValue(int position, String value) {
	    throw new UnsupportedOperationException();
	}
	
	@Override
	public String[] getValues() {
	    String[] values = {value};
	    return values;
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
	    return Field.create(this.value);
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        Field other = (Field) obj;
        if (value == null) {
            if (other.value != null) return false;
        } else if (!value.equals(other.value)) return false;
        return true;
    }

	
}
