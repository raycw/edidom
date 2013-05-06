package com.github.edi.common.edifact;

import java.util.List;

import com.github.edi.common.CompositeField;
import com.github.edi.common.Segment;

public class EmptyGroupEnvelope extends EdifactGroupEnvelope {

	public EmptyGroupEnvelope() {
		super((Segment)null);
	}

	/* (non-Javadoc)
	 * @see com.github.edi.common.edifact.EdifactGroupEnvelope#getControlNumber()
	 */
	@Override
	public String getControlNumber() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.edi.common.edifact.EdifactGroupEnvelope#getField(int)
	 */
	@Override
	public CompositeField getField(int position) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.edi.common.edifact.EdifactGroupEnvelope#getFunctionalCode()
	 */
	@Override
	public String getFunctionalCode() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.edi.common.edifact.EdifactGroupEnvelope#getReceiverCode()
	 */
	@Override
	public String getReceiverCode() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.edi.common.edifact.EdifactGroupEnvelope#getSenderCode()
	 */
	@Override
	public String getSenderCode() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.edi.common.edifact.EdifactGroupEnvelope#getVersion()
	 */
	@Override
	public String getVersion() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.edi.common.edifact.EdifactGroupEnvelope#setControlNumber(java.lang.String)
	 */
	@Override
	public void setControlNumber(String controlNum) {
	}

	/* (non-Javadoc)
	 * @see com.github.edi.common.edifact.EdifactGroupEnvelope#setFunctionalCode(java.lang.String)
	 */
	@Override
	public void setFunctionalCode(String code) {
	}

	/* (non-Javadoc)
	 * @see com.github.edi.common.edifact.EdifactGroupEnvelope#setReceiverCode(java.lang.String)
	 */
	@Override
	public void setReceiverCode(String receiver) {
	}

	/* (non-Javadoc)
	 * @see com.github.edi.common.edifact.EdifactGroupEnvelope#setSenderCode(java.lang.String)
	 */
	@Override
	public void setSenderCode(String sender) {
	}

	/* (non-Javadoc)
	 * @see com.github.edi.common.edifact.EdifactGroupEnvelope#setVersion(java.lang.String)
	 */
	@Override
	public void setVersion(String version) {
	}

	/* (non-Javadoc)
	 * @see com.github.edi.common.Envelope#getFields()
	 */
	@Override
	public List<CompositeField> getFields() {
		return null;
	}
	
	@Override
	public EmptyGroupEnvelope copy() {
	    return new EmptyGroupEnvelope();
	}

}
