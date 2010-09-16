package com.cargosmart.b2b.edi.common;

import java.util.ArrayList;
import java.util.List;

public class Transaction {

	private GroupEnvelope group;
	private List<Segment> segments = new ArrayList<Segment>();
	
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

}
