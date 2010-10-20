package com.cargosmart.b2b.edi.common.x12;

import java.util.List;

import com.cargosmart.b2b.edi.common.CompositeField;
import com.cargosmart.b2b.edi.common.Segment;

abstract class Envelope {

	protected Segment segment;

	public Envelope(Segment segment) {
		this.segment = segment;
	}

	public List<CompositeField> getFields() {
		return segment.getFields();
	}

}