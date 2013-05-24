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
package com.github.edidom.common.edifact;

import java.util.List;

import com.github.edidom.common.CompositeField;
import com.github.edidom.common.Segment;

public class EmptyGroupEnvelope extends EdifactGroupEnvelope {

	public EmptyGroupEnvelope() {
		super((Segment)null);
	}

	/* (non-Javadoc)
	 * @see com.github.edidom.common.edifact.EdifactGroupEnvelope#getControlNumber()
	 */
	@Override
	public String getControlNumber() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.edidom.common.edifact.EdifactGroupEnvelope#getField(int)
	 */
	@Override
	public CompositeField getField(int position) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.edidom.common.edifact.EdifactGroupEnvelope#getFunctionalCode()
	 */
	@Override
	public String getFunctionalCode() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.edidom.common.edifact.EdifactGroupEnvelope#getReceiverCode()
	 */
	@Override
	public String getReceiverCode() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.edidom.common.edifact.EdifactGroupEnvelope#getSenderCode()
	 */
	@Override
	public String getSenderCode() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.edidom.common.edifact.EdifactGroupEnvelope#getVersion()
	 */
	@Override
	public String getVersion() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.edidom.common.edifact.EdifactGroupEnvelope#setControlNumber(java.lang.String)
	 */
	@Override
	public void setControlNumber(String controlNum) {
	}

	/* (non-Javadoc)
	 * @see com.github.edidom.common.edifact.EdifactGroupEnvelope#setFunctionalCode(java.lang.String)
	 */
	@Override
	public void setFunctionalCode(String code) {
	}

	/* (non-Javadoc)
	 * @see com.github.edidom.common.edifact.EdifactGroupEnvelope#setReceiverCode(java.lang.String)
	 */
	@Override
	public void setReceiverCode(String receiver) {
	}

	/* (non-Javadoc)
	 * @see com.github.edidom.common.edifact.EdifactGroupEnvelope#setSenderCode(java.lang.String)
	 */
	@Override
	public void setSenderCode(String sender) {
	}

	/* (non-Javadoc)
	 * @see com.github.edidom.common.edifact.EdifactGroupEnvelope#setVersion(java.lang.String)
	 */
	@Override
	public void setVersion(String version) {
	}

	/* (non-Javadoc)
	 * @see com.github.edidom.common.Envelope#getFields()
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
