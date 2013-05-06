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
	
	public Segment nextSegment() {
	    int position = transaction.getSegmentPosition(this);
	    return transaction.getSegment(position+1);
	}
	
	public Segment previousSegment() {
        int position = transaction.getSegmentPosition(this);
        return transaction.getSegment(position-1);
	}

	public List<Segment> nextSegments(String tagName) {
	    List<Segment> allSegments = transaction.getSegements();
	    List<Segment> subList = allSegments.subList(transaction.getSegmentPosition(this), allSegments.size());
	    List<Segment> result = new ArrayList<Segment>();
        for (Segment segment : subList) {
            if (segment.getSegmentTag().equals(tagName)) {
                result.add(segment);
            }
        }
        return result;
	}
	
    public List<Segment> previousSegments(String tagName) {
        List<Segment> allSegments = transaction.getSegements();
        List<Segment> subList = allSegments.subList(0, transaction.getSegmentPosition(this));
        List<Segment> result = new ArrayList<Segment>();
        for (Segment segment : subList) {
            if (segment.getSegmentTag().equals(tagName)) {
                result.add(segment);
            }
        }
        return result;
    }
}
