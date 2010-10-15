package com.cargosmart.b2b.edi.common;

import java.util.ArrayList;
import java.util.List;

public class InterchangeEnvelope {

	private Document document;
	private List<GroupEnvelope> groups = new ArrayList<GroupEnvelope>();
	private Segment segment;
	
	public InterchangeEnvelope(Segment segment) {
	    this.segment = segment;
	}

	public InterchangeEnvelope setDocument(Document document) {
		if (this.document != document) {
			this.document = document;
			this.document.setInterchangeEnvelope(this);
		}
		return this;
	}
	
	public Document getDocument() {
		return document;
	}

	public InterchangeEnvelope addGroupEnvelope(GroupEnvelope groupEnvelope) {
		groups.add(groupEnvelope);
		groupEnvelope.setInterchangeEnvelope(this);
		return this;
	}
	
	public InterchangeEnvelope removeGroupEnvelope(GroupEnvelope groupEnvelope) {
		groups.remove(groupEnvelope);
		groupEnvelope.setInterchangeEnvelope(null);
		return this;
	}
	
	public List<GroupEnvelope> getGroups() {
		return groups;
	}

	/**
	 * Gets Interchange field by position. The position is starting from 1.
	 * 
	 * @param position start from 1
	 * @return Field
	 */
	public CompositeField getField(int position) {
	    return segment.getField(position);
	}

    public String getSenderQualifier() {
        return getField(5).getValue().trim();
    }

    public String getSenderId() {
        return getField(6).getValue().trim();
    }

    public String getReceiverQualifier() {
        return getField(7).getValue().trim();
    }

    public String getReceiverId() {
        return getField(8).getValue().trim();
    }

    public String getVersion() {
        return getField(12).getValue().trim();
    }

    public String getControlNumber() {
        return getField(13).getValue().trim();
    }

	public List<Segment> getSegment(String tag) {
		List<Segment> segments = new ArrayList<Segment>();
		for (GroupEnvelope group : groups) {
			segments.addAll(group.getSegment(tag));
		}
		return segments;
	}
	
	public List<CompositeField> getFields() {
		return segment.getFields();
	}

}
