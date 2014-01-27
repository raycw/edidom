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
package com.github.raycw.edidom.common.edifact;

import java.util.ArrayList;
import java.util.List;

import com.github.raycw.edidom.common.CompositeField;
import com.github.raycw.edidom.common.Envelope;
import com.github.raycw.edidom.common.GroupEnvelope;
import com.github.raycw.edidom.common.LoopGroup;
import com.github.raycw.edidom.common.Segment;
import com.github.raycw.edidom.common.Transaction;

public class EdifactTransaction extends Envelope implements Transaction {

	private GroupEnvelope group;
	private List<Segment> segments = new ArrayList<Segment>();
	
	public EdifactTransaction(Segment segment) {
		super(segment);
	}
	
	/**
	 * Copy constructor
	 * 
	 * @param orig
	 */
	public EdifactTransaction(EdifactTransaction orig) {
		super(new Segment(orig.segment));
	}

	public Transaction setGroupEnvelope(GroupEnvelope group) {
		this.group = group;
		return this;		
	}

	/* (non-Javadoc)
     * @see com.github.raycw.edidom.common.Transaction#getGroupEnvelope()
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
     * @see com.github.raycw.edidom.common.Transaction#getSegements()
     */
	public List<Segment> getSegements() {
		return getSegments();
	}

	/* (non-Javadoc)
     * @see com.github.raycw.edidom.common.Transaction#getField(int)
     */
	public CompositeField getField(int position) {
	    return segment.getField(position);
	}

	/* (non-Javadoc)
     * @see com.github.raycw.edidom.common.Transaction#getType()
     */
	public String getType() {
		return getField(2).getField(1).getValue().trim();
	}
	/* (non-Javadoc)
     * @see com.github.raycw.edidom.common.Transaction#setType(java.lang.String)
     */
	public void setType(String type) {
		getField(2).getField(1).setValue(type);
	}
	
	/* (non-Javadoc)
     * @see com.github.raycw.edidom.common.Transaction#getControlNumber()
     */
	public String getControlNumber() {
		return getField(1).getField(1).getValue().trim();
	}
	/* (non-Javadoc)
     * @see com.github.raycw.edidom.common.Transaction#setControlNumber(java.lang.String)
     */
	public void setControlNumber(String controlNum) {
		getField(1).getField(1).setValue(controlNum);
	}

	/* (non-Javadoc)
     * @see com.github.raycw.edidom.common.Transaction#getSegments(java.lang.String)
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
		return new EdifactTransaction(this);
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

    public List<LoopGroup> getLoopGroups(String startTag, String endTag) {
        //Get all start segments
        //If more than one, find the end tag
        //Grep all the segments from startTag to endTag
        List<LoopGroup> loopGroups = new ArrayList<LoopGroup>();
        List<Segment> segments = this.getSegments(startTag);
        if (segments.size() == 0) {
            return loopGroups;
        }
        for (int i = 0; i < segments.size() -1; i++) {
            Segment segment = segments.get(i);
            LoopGroup loop = new LoopGroup(this);
            loop.addSegment(segment);
            segment = segment.nextSegment();
            while (!segment.getSegmentTag().equals(startTag)) {
                loop.addSegment(segment);
                segment = segment.nextSegment();
            }
            loopGroups.add(loop);
        }
        Segment segment = segments.get(segments.size()-1);
        LoopGroup loop = new LoopGroup(this);
        loop.addSegment(segment);
        segment = segment.nextSegment();
        while (!segment.getSegmentTag().equals(endTag)) {
            loop.addSegment(segment);
            segment = segment.nextSegment();
        }
        loopGroups.add(loop);
        return loopGroups;
    }
}
