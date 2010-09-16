package com.cargosmart.b2b.edi.common;

import java.util.ArrayList;
import java.util.List;

public class InterchangeEnvelope {

	private Document document;
	private List<GroupEnvelope> groups = new ArrayList<GroupEnvelope>();

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

}
