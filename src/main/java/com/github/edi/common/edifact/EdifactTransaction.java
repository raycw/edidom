package com.github.edi.common.edifact;

import java.util.ArrayList;
import java.util.List;

import com.github.edi.common.CompositeField;
import com.github.edi.common.Envelope;
import com.github.edi.common.GroupEnvelope;
import com.github.edi.common.Segment;
import com.github.edi.common.Transaction;

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
     * @see com.github.edi.common.Transaction#getGroupEnvelope()
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
     * @see com.github.edi.common.Transaction#getSegements()
     */
	public List<Segment> getSegements() {
		return getSegments();
	}

	/* (non-Javadoc)
     * @see com.github.edi.common.Transaction#getField(int)
     */
	public CompositeField getField(int position) {
	    return segment.getField(position);
	}

	/* (non-Javadoc)
     * @see com.github.edi.common.Transaction#getType()
     */
	public String getType() {
		return getField(2).getField(1).getValue().trim();
	}
	/* (non-Javadoc)
     * @see com.github.edi.common.Transaction#setType(java.lang.String)
     */
	public void setType(String type) {
		getField(2).getField(1).setValue(type);
	}
	
	/* (non-Javadoc)
     * @see com.github.edi.common.Transaction#getControlNumber()
     */
	public String getControlNumber() {
		return getField(1).getField(1).getValue().trim();
	}
	/* (non-Javadoc)
     * @see com.github.edi.common.Transaction#setControlNumber(java.lang.String)
     */
	public void setControlNumber(String controlNum) {
		getField(1).getField(1).setValue(controlNum);
	}

	/* (non-Javadoc)
     * @see com.github.edi.common.Transaction#getSegments(java.lang.String)
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
}
