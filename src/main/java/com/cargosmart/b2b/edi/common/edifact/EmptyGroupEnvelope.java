package com.cargosmart.b2b.edi.common.edifact;

import java.util.List;

import com.cargosmart.b2b.edi.common.CompositeField;
import com.cargosmart.b2b.edi.common.GroupEnvelope;
import com.cargosmart.b2b.edi.common.InterchangeEnvelope;
import com.cargosmart.b2b.edi.common.Segment;
import com.cargosmart.b2b.edi.common.Transaction;

public class EmptyGroupEnvelope extends EdifactGroupEnvelope {

	public EmptyGroupEnvelope(Segment segment) {
		super(segment);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.cargosmart.b2b.edi.common.edifact.EdifactGroupEnvelope#getControlNumber()
	 */
	@Override
	public String getControlNumber() {
		// TODO Auto-generated method stub
		return super.getControlNumber();
	}

	/* (non-Javadoc)
	 * @see com.cargosmart.b2b.edi.common.edifact.EdifactGroupEnvelope#getField(int)
	 */
	@Override
	public CompositeField getField(int position) {
		// TODO Auto-generated method stub
		return super.getField(position);
	}

	/* (non-Javadoc)
	 * @see com.cargosmart.b2b.edi.common.edifact.EdifactGroupEnvelope#getFunctionalCode()
	 */
	@Override
	public String getFunctionalCode() {
		// TODO Auto-generated method stub
		return super.getFunctionalCode();
	}

	/* (non-Javadoc)
	 * @see com.cargosmart.b2b.edi.common.edifact.EdifactGroupEnvelope#getInterchangeEnvelope()
	 */
	@Override
	public InterchangeEnvelope getInterchangeEnvelope() {
		// TODO Auto-generated method stub
		return super.getInterchangeEnvelope();
	}

	/* (non-Javadoc)
	 * @see com.cargosmart.b2b.edi.common.edifact.EdifactGroupEnvelope#getReceiverCode()
	 */
	@Override
	public String getReceiverCode() {
		// TODO Auto-generated method stub
		return super.getReceiverCode();
	}

	/* (non-Javadoc)
	 * @see com.cargosmart.b2b.edi.common.edifact.EdifactGroupEnvelope#getSegment(java.lang.String)
	 */
	@Override
	public List<Segment> getSegment(String tag) {
		// TODO Auto-generated method stub
		return super.getSegment(tag);
	}

	/* (non-Javadoc)
	 * @see com.cargosmart.b2b.edi.common.edifact.EdifactGroupEnvelope#getSenderCode()
	 */
	@Override
	public String getSenderCode() {
		// TODO Auto-generated method stub
		return super.getSenderCode();
	}

	/* (non-Javadoc)
	 * @see com.cargosmart.b2b.edi.common.edifact.EdifactGroupEnvelope#getTransactions()
	 */
	@Override
	public List<Transaction> getTransactions() {
		// TODO Auto-generated method stub
		return super.getTransactions();
	}

	/* (non-Javadoc)
	 * @see com.cargosmart.b2b.edi.common.edifact.EdifactGroupEnvelope#getVersion()
	 */
	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return super.getVersion();
	}

	/* (non-Javadoc)
	 * @see com.cargosmart.b2b.edi.common.edifact.EdifactGroupEnvelope#removeTransaction(com.cargosmart.b2b.edi.common.Transaction)
	 */
	@Override
	public GroupEnvelope removeTransaction(Transaction txn) {
		// TODO Auto-generated method stub
		return super.removeTransaction(txn);
	}

	/* (non-Javadoc)
	 * @see com.cargosmart.b2b.edi.common.edifact.EdifactGroupEnvelope#setControlNumber(java.lang.String)
	 */
	@Override
	public void setControlNumber(String controlNum) {
		// TODO Auto-generated method stub
		super.setControlNumber(controlNum);
	}

	/* (non-Javadoc)
	 * @see com.cargosmart.b2b.edi.common.edifact.EdifactGroupEnvelope#setFunctionalCode(java.lang.String)
	 */
	@Override
	public void setFunctionalCode(String code) {
		// TODO Auto-generated method stub
		super.setFunctionalCode(code);
	}

	/* (non-Javadoc)
	 * @see com.cargosmart.b2b.edi.common.edifact.EdifactGroupEnvelope#setReceiverCode(java.lang.String)
	 */
	@Override
	public void setReceiverCode(String receiver) {
		// TODO Auto-generated method stub
		super.setReceiverCode(receiver);
	}

	/* (non-Javadoc)
	 * @see com.cargosmart.b2b.edi.common.edifact.EdifactGroupEnvelope#setSenderCode(java.lang.String)
	 */
	@Override
	public void setSenderCode(String sender) {
		// TODO Auto-generated method stub
		super.setSenderCode(sender);
	}

	/* (non-Javadoc)
	 * @see com.cargosmart.b2b.edi.common.edifact.EdifactGroupEnvelope#setVersion(java.lang.String)
	 */
	@Override
	public void setVersion(String version) {
		// TODO Auto-generated method stub
		super.setVersion(version);
	}

	/* (non-Javadoc)
	 * @see com.cargosmart.b2b.edi.common.Envelope#getFields()
	 */
	@Override
	public List<CompositeField> getFields() {
		// TODO Auto-generated method stub
		return super.getFields();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return super.equals(arg0);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

}
