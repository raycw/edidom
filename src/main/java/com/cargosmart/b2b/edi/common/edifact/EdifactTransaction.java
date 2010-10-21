package com.cargosmart.b2b.edi.common.edifact;

import java.util.ArrayList;
import java.util.List;

import com.cargosmart.b2b.edi.common.CompositeField;
import com.cargosmart.b2b.edi.common.Envelope;
import com.cargosmart.b2b.edi.common.GroupEnvelope;
import com.cargosmart.b2b.edi.common.Segment;
import com.cargosmart.b2b.edi.common.Transaction;

public class EdifactTransaction extends Envelope implements Transaction {

	private GroupEnvelope group;
	private List<Segment> segments = new ArrayList<Segment>();
	
	public EdifactTransaction(Segment segment) {
		super(segment);
	}

	public Transaction setGroupEnvelope(GroupEnvelope group) {
		this.group = group;
		return this;		
	}

	/* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.Transaction#getGroupEnvelope()
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
     * @see com.cargosmart.b2b.edi.common.Transaction#getSegements()
     */
	public List<Segment> getSegements() {
		return segments;
	}

	/* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.Transaction#getField(int)
     */
	public CompositeField getField(int position) {
	    return segment.getField(position);
	}

	/* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.Transaction#getType()
     */
	public String getType() {
		return getField(1).getValue().trim();
	}
	/* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.Transaction#setType(java.lang.String)
     */
	public void setType(String type) {
		getField(1).setValue(type);
	}
	
	/* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.Transaction#getControlNumber()
     */
	public String getControlNumber() {
		return getField(2).getValue().trim();
	}
	/* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.Transaction#setControlNumber(java.lang.String)
     */
	public void setControlNumber(String controlNum) {
		getField(2).setValue(controlNum);
	}

	/* (non-Javadoc)
     * @see com.cargosmart.b2b.edi.common.Transaction#getSegments(java.lang.String)
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
}
