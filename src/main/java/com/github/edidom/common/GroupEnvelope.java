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
package com.github.edidom.common;

import java.util.List;

public interface GroupEnvelope {

    public abstract GroupEnvelope setInterchangeEnvelope(
            InterchangeEnvelope interchangeEnvelope);

    public abstract InterchangeEnvelope getInterchangeEnvelope();

    public abstract CompositeField getField(int position);

    public abstract GroupEnvelope detach();

    public abstract GroupEnvelope addTransaction(Transaction txn);

    public abstract GroupEnvelope removeTransaction(Transaction txn);

    public abstract List<Transaction> getTransactions();

    /**
     * @return the functionalCode
     */
    public abstract String getFunctionalCode();

    public abstract void setFunctionalCode(String code);

    /**
     * @return the senderCode
     */
    public abstract String getSenderCode();

    public abstract void setSenderCode(String sender);

    /**
     * @return the receiverCode
     */
    public abstract String getReceiverCode();

    public abstract void setReceiverCode(String receiver);

    /**
     * @return the controlNumber
     */
    public abstract String getControlNumber();

    public abstract void setControlNumber(String controlNum);

    /**
     * @return the version
     */
    public abstract String getVersion();

    public abstract void setVersion(String version);

    /**
     * A convenience method to find segment by tag name.
     * 
     * @param tag
     * @return List of segment
     */
    public abstract List<Segment> getSegment(String tag);

    public abstract List<CompositeField> getFields();
    
    /**
     * Shallow copy without relationship
     * 
     * @return copy of this instance
     */
    public abstract GroupEnvelope copy();
}
