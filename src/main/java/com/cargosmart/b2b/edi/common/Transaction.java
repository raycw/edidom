package com.cargosmart.b2b.edi.common;

import java.util.ArrayList;
import java.util.List;

public class Transaction extends Envelope {

	private GroupEnvelope group;
	private List<Segment> segments = new ArrayList<Segment>();
	
	public Transaction(Segment segment) {
		super(segment);
	}

	public Transaction setGroupEnvelope(GroupEnvelope group) {
		this.group = group;
		return this;		
	}

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
	
	public List<Segment> getSegements() {
		return segments;
	}

	/**
	 * Gets Transaction field by position. The position is starting from 1.
	 * 
	 * @param position start from 1
	 * @return Field
	 */
	public CompositeField getField(int position) {
	    return segment.getField(position);
	}

	public String getType() {
		return getField(1).getValue().trim();
	}
	public void setType(String type) {
		getField(1).setValue(type);
	}
	
	public String getControlNumber() {
		return getField(2).getValue().trim();
	}
	public void setControlNumber(String controlNum) {
		getField(2).setValue(controlNum);
	}

	public List<Segment> getSegments(String tag) {
		List<Segment> segments = new ArrayList<Segment>();
		for (Segment segment : this.segments) {
			if (segment.getSegmentTag().equals(tag)) {
				segments.add(segment);
			}
		}
		return segments;
	}
}
