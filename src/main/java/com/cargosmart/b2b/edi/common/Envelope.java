package com.cargosmart.b2b.edi.common;

import java.util.List;


public abstract class Envelope {

	protected Segment segment;

	public Envelope(Segment segment) {
		this.segment = segment;
	}

	public List<CompositeField> getFields() {
		return segment.getFields();
	}

}