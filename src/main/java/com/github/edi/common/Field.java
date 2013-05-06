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
