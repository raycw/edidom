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
package com.github.edi.common.edifact;

import java.util.ArrayList;
import java.util.List;

import com.github.edi.common.CompositeField;
import com.github.edi.common.Document;
import com.github.edi.common.Envelope;
import com.github.edi.common.GroupEnvelope;
import com.github.edi.common.InterchangeEnvelope;
import com.github.edi.common.Segment;

public class EdifactInterchangeEnvelope extends Envelope implements InterchangeEnvelope {

	private Document document;
	private List<GroupEnvelope> groups = new ArrayList<GroupEnvelope>();
	private Segment levelAInterchangeEnvelope;

	public EdifactInterchangeEnvelope(Segment segment) {
	    super(segment);
	}
	
	public EdifactInterchangeEnvelope(Segment levelA, Segment levelB) {
		super(levelB);
		this.levelAInterchangeEnvelope = levelA;
	}
	
	/**
	 * Copy constructor
	 * 
	 * @param orig
	 */
	public EdifactInterchangeEnvelope(EdifactInterchangeEnvelope orig) {
		super(new Segment(orig.segment));
		this.levelAInterchangeEnvelope = new Segment(orig.levelAInterchangeEnvelope);
	}

	/**
	 * @return the levelAInterchangeEnvelope
	 */
	public Segment getLevelAInterchangeEnvelope() {
		return levelAInterchangeEnvelope;
	}

	/**
	 * @param levelAInterchangeEnvelope the levelAInterchangeEnvelope to set
	 */
	public void setLevelAInterchangeEnvelope(Segment levelAInterchangeEnvelope) {
		this.levelAInterchangeEnvelope = levelAInterchangeEnvelope;
	}

	/* (non-Javadoc)
     * @see com.github.edi.common.InterchangeEnvelopeI#setDocument(com.github.edi.common.Document)
     */
	public InterchangeEnvelope setDocument(Document document) {
		if (this.document != document) {
			this.document = document;
			this.document.setInterchangeEnvelope(this);
		}
		return this;
	}
	
	/* (non-Javadoc)
     * @see com.github.edi.common.InterchangeEnvelopeI#getDocument()
     */
	public Document getDocument() {
		return document;
	}

	/* (non-Javadoc)
     * @see com.github.edi.common.InterchangeEnvelopeI#addGroupEnvelope(com.github.edi.common.GroupEnvelope)
     */
	public InterchangeEnvelope addGroupEnvelope(GroupEnvelope groupEnvelope) {
		groups.add(groupEnvelope);
		groupEnvelope.setInterchangeEnvelope(this);
		return this;
	}
	
	/* (non-Javadoc)
     * @see com.github.edi.common.InterchangeEnvelopeI#removeGroupEnvelope(com.github.edi.common.GroupEnvelope)
     */
	public InterchangeEnvelope removeGroupEnvelope(GroupEnvelope groupEnvelope) {
		groups.remove(groupEnvelope);
		groupEnvelope.setInterchangeEnvelope(null);
		return this;
	}
	
	/* (non-Javadoc)
     * @see com.github.edi.common.InterchangeEnvelopeI#getGroups()
     */
	public List<GroupEnvelope> getGroups() {
		return groups;
	}

	/* (non-Javadoc)
     * @see com.github.edi.common.InterchangeEnvelopeI#getField(int)
     */
	public CompositeField getField(int position) {
	    return segment.getField(position);
	}

    /* (non-Javadoc)
     * @see com.github.edi.common.InterchangeEnvelopeI#getSenderQualifier()
     */
    public String getSenderQualifier() {
        return getField(2).getField(2).getValue().trim();
    }
    /* (non-Javadoc)
     * @see com.github.edi.common.InterchangeEnvelopeI#setSenderQualifier(java.lang.String)
     */
    public void setSenderQualifier(String qualifier) {
        getField(2).getField(2).setValue(qualifier);
    }

    /* (non-Javadoc)
     * @see com.github.edi.common.InterchangeEnvelopeI#getSenderId()
     */
    public String getSenderId() {
        return getField(2).getField(1).getValue().trim();
    }
    /* (non-Javadoc)
     * @see com.github.edi.common.InterchangeEnvelopeI#setSenderId(java.lang.String)
     */
    public void setSenderId(String id) {
        getField(2).getField(1).setValue(id);
    }

    /* (non-Javadoc)
     * @see com.github.edi.common.InterchangeEnvelopeI#getReceiverQualifier()
     */
    public String getReceiverQualifier() {
        return getField(3).getField(2).getValue().trim();
    }
    /* (non-Javadoc)
     * @see com.github.edi.common.InterchangeEnvelopeI#setReceiverQualifier(java.lang.String)
     */
    public void setReceiverQualifier(String qualifier) {
        getField(3).getField(2).setValue(qualifier);
    }

    /* (non-Javadoc)
     * @see com.github.edi.common.InterchangeEnvelopeI#getReceiverId()
     */
    public String getReceiverId() {
        return getField(3).getField(1).getValue().trim();
    }
    
    /* (non-Javadoc)
     * @see com.github.edi.common.InterchangeEnvelopeI#setReceiverId(java.lang.String)
     */
    public void setReceiverId(String id) {
    	getField(3).getField(1).setValue(id);
    }

    /* (non-Javadoc)
     * @see com.github.edi.common.InterchangeEnvelopeI#getVersion()
     */
    public String getVersion() {
        return getField(1).getField(2).getValue().trim();
    }
    
    /* (non-Javadoc)
     * @see com.github.edi.common.InterchangeEnvelopeI#setVersion(java.lang.String)
     */
    public void setVersion(String version) {
    	getField(1).getField(2).setValue(version);
    }

    /* (non-Javadoc)
     * @see com.github.edi.common.InterchangeEnvelopeI#getControlNumber()
     */
    public String getControlNumber() {
        return getField(5).getField(1).getValue().trim();
    }
    
    /* (non-Javadoc)
     * @see com.github.edi.common.InterchangeEnvelopeI#setControlNumber(java.lang.String)
     */
    public void setControlNumber(String controlNum) {
    	getField(5).getField(1).setValue(controlNum);
    }

	/* (non-Javadoc)
     * @see com.github.edi.common.InterchangeEnvelopeI#getSegment(java.lang.String)
     */
	public List<Segment> getSegment(String tag) {
		List<Segment> segments = new ArrayList<Segment>();
		for (GroupEnvelope group : groups) {
			segments.addAll(group.getSegment(tag));
		}
		return segments;
	}

	public InterchangeEnvelope copy() {
		return new EdifactInterchangeEnvelope(this);
	}

}
