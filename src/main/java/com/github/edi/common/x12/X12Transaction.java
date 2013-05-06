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
package com.github.edi.common.x12;

import java.util.ArrayList;
import java.util.List;

import com.github.edi.common.CompositeField;
import com.github.edi.common.Envelope;
import com.github.edi.common.GroupEnvelope;
import com.github.edi.common.Segment;
import com.github.edi.common.Transaction;

public class X12Transaction extends Envelope implements Transaction {

	private GroupEnvelope group;
	private List<Segment> segments = new ArrayList<Segment>();
	
	public X12Transaction(Segment segment) {
		super(segment);
	}
	
	/**
	 * Copy constructor
	 * 
	 * @param orig
	 */
	public X12Transaction(X12Transaction orig) {
		super(new Segment(orig.segment));
	}

	public Transaction setGroupEnvelope(GroupEnvelope group) {
		this.group = group;
		return this;		
	}

	/* (non-Javadoc)
     * @see com.github.edi.common.x12.Transaction#getGroupEnvelope()
     */
	public GroupEnvelope getGroupEnvelope() {
		return group;
	}
	
	public Transaction detach() {
		group.removeTransaction(this);
		group = null;
		return this;
	}
	
	public Transaction addSegment(Segment segment) {
		segments.add(segment);
		segment.setTransaction(this);
		return this;
	}
	
	public Transaction removeSegment(Segment segment) {
		segments.remove(segment);
		segment.setTransaction(null);
		return this;
	}
	
	/* (non-Javadoc)
     * @see com.github.edi.common.x12.Transaction#getSegements()
     */
	public List<Segment> getSegements() {
		return getSegments();
	}

	/* (non-Javadoc)
     * @see com.github.edi.common.x12.Transaction#getField(int)
     */
	public CompositeField getField(int position) {
	    return segment.getField(position);
	}

	/* (non-Javadoc)
     * @see com.github.edi.common.x12.Transaction#getType()
     */
	public String getType() {
		return getField(1).getValue().trim();
	}
	/* (non-Javadoc)
     * @see com.github.edi.common.x12.Transaction#setType(java.lang.String)
     */
	public void setType(String type) {
		getField(1).setValue(type);
	}
	
	/* (non-Javadoc)
     * @see com.github.edi.common.x12.Transaction#getControlNumber()
     */
	public String getControlNumber() {
		return getField(2).getValue().trim();
	}
	/* (non-Javadoc)
     * @see com.github.edi.common.x12.Transaction#setControlNumber(java.lang.String)
     */
	public void setControlNumber(String controlNum) {
		getField(2).setValue(controlNum);
	}

	/* (non-Javadoc)
     * @see com.github.edi.common.x12.Transaction#getSegments(java.lang.String)
     */
	public List<Segment> getSegments(String tag) {
		List<Segment> segments = new ArrayList<Segment>();
		for (Segment segment : this.segments) {
			if (segment.getSegmentTag().equals(tag)) {
				segments.add(segment);
			}
		}
		return segments;
	}

	public Transaction copy() {
		return new X12Transaction(this);
	}

    public int getSegmentPosition(Segment segment) {
        return segments.indexOf(segment);
    }

    public Segment getSegment(int position) {
        if (position < 0 || position >= segments.size()) {
            return null;
        }
        return segments.get(position);
    }

    public List<Segment> getSegments() {
        return segments;
    }
}
