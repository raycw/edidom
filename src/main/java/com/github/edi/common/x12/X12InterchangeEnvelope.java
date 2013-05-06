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
import com.github.edi.common.Document;
import com.github.edi.common.Envelope;
import com.github.edi.common.GroupEnvelope;
import com.github.edi.common.InterchangeEnvelope;
import com.github.edi.common.Segment;

public class X12InterchangeEnvelope extends Envelope implements InterchangeEnvelope {

	private Document document;
	private List<GroupEnvelope> groups = new ArrayList<GroupEnvelope>();

	public X12InterchangeEnvelope(Segment segment) {
	    super(segment);
	}
	
	/**
	 * Copy constructor
	 * 
	 * @param orig
	 */
	public X12InterchangeEnvelope(X12InterchangeEnvelope orig) {
		super(new Segment(orig.segment));
	}

	/* (non-Javadoc)
     * @see com.github.edi.common.x12.InterchangeEnvelopeI#setDocument(com.github.edi.common.Document)
     */
	public InterchangeEnvelope setDocument(Document document) {
		if (this.document != document) {
			this.document = document;
			this.document.setInterchangeEnvelope(this);
		}
		return this;
	}
	
	/* (non-Javadoc)
     * @see com.github.edi.common.x12.InterchangeEnvelopeI#getDocument()
     */
	public Document getDocument() {
		return document;
	}

	/* (non-Javadoc)
     * @see com.github.edi.common.x12.InterchangeEnvelopeI#addGroupEnvelope(com.github.edi.common.x12.X12GroupEnvelope)
     */
	public InterchangeEnvelope addGroupEnvelope(GroupEnvelope groupEnvelope) {
		groups.add(groupEnvelope);
		groupEnvelope.setInterchangeEnvelope(this);
		return this;
	}
	
	/* (non-Javadoc)
     * @see com.github.edi.common.x12.InterchangeEnvelopeI#removeGroupEnvelope(com.github.edi.common.x12.X12GroupEnvelope)
     */
	public InterchangeEnvelope removeGroupEnvelope(GroupEnvelope groupEnvelope) {
		groups.remove(groupEnvelope);
		groupEnvelope.setInterchangeEnvelope(null);
		return this;
	}
	
	/* (non-Javadoc)
     * @see com.github.edi.common.x12.InterchangeEnvelopeI#getGroups()
     */
	public List<GroupEnvelope> getGroups() {
		return groups;
	}

	/* (non-Javadoc)
     * @see com.github.edi.common.x12.InterchangeEnvelopeI#getField(int)
     */
	public CompositeField getField(int position) {
	    return segment.getField(position);
	}

    /* (non-Javadoc)
     * @see com.github.edi.common.x12.InterchangeEnvelopeI#getSenderQualifier()
     */
    public String getSenderQualifier() {
        return getField(5).getValue().trim();
    }
    /* (non-Javadoc)
     * @see com.github.edi.common.x12.InterchangeEnvelopeI#setSenderQualifier(java.lang.String)
     */
    public void setSenderQualifier(String qualifier) {
        getField(5).setValue(qualifier);
    }

    /* (non-Javadoc)
     * @see com.github.edi.common.x12.InterchangeEnvelopeI#getSenderId()
     */
    public String getSenderId() {
        return getField(6).getValue().trim();
    }
    /* (non-Javadoc)
     * @see com.github.edi.common.x12.InterchangeEnvelopeI#setSenderId(java.lang.String)
     */
    public void setSenderId(String id) {
        getField(6).setValue(id);
    }

    /* (non-Javadoc)
     * @see com.github.edi.common.x12.InterchangeEnvelopeI#getReceiverQualifier()
     */
    public String getReceiverQualifier() {
        return getField(7).getValue().trim();
    }
    /* (non-Javadoc)
     * @see com.github.edi.common.x12.InterchangeEnvelopeI#setReceiverQualifier(java.lang.String)
     */
    public void setReceiverQualifier(String qualifier) {
        getField(7).setValue(qualifier);
    }

    /* (non-Javadoc)
     * @see com.github.edi.common.x12.InterchangeEnvelopeI#getReceiverId()
     */
    public String getReceiverId() {
        return getField(8).getValue().trim();
    }
    
    /* (non-Javadoc)
     * @see com.github.edi.common.x12.InterchangeEnvelopeI#setReceiverId(java.lang.String)
     */
    public void setReceiverId(String id) {
    	getField(8).setValue(id);
    }

    /* (non-Javadoc)
     * @see com.github.edi.common.x12.InterchangeEnvelopeI#getVersion()
     */
    public String getVersion() {
        return getField(12).getValue().trim();
    }
    
    /* (non-Javadoc)
     * @see com.github.edi.common.x12.InterchangeEnvelopeI#setVersion(java.lang.String)
     */
    public void setVersion(String version) {
    	getField(12).setValue(version);
    }

    /* (non-Javadoc)
     * @see com.github.edi.common.x12.InterchangeEnvelopeI#getControlNumber()
     */
    public String getControlNumber() {
        return getField(13).getValue().trim();
    }
    
    /* (non-Javadoc)
     * @see com.github.edi.common.x12.InterchangeEnvelopeI#setControlNumber(java.lang.String)
     */
    public void setControlNumber(String controlNum) {
    	getField(13).setValue(controlNum);
    }

	/* (non-Javadoc)
     * @see com.github.edi.common.x12.InterchangeEnvelopeI#getSegment(java.lang.String)
     */
	public List<Segment> getSegment(String tag) {
		List<Segment> segments = new ArrayList<Segment>();
		for (GroupEnvelope group : groups) {
			segments.addAll(group.getSegment(tag));
		}
		return segments;
	}

	public InterchangeEnvelope copy() {
		return new X12InterchangeEnvelope(this);
	}

}
