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
package com.github.edi.common;

import java.util.List;


public interface InterchangeEnvelope {

    public abstract InterchangeEnvelope setDocument(Document document);

    public abstract Document getDocument();

    public abstract InterchangeEnvelope addGroupEnvelope(
            GroupEnvelope groupEnvelope);

    public abstract InterchangeEnvelope removeGroupEnvelope(
            GroupEnvelope groupEnvelope);

    public abstract List<GroupEnvelope> getGroups();

    /**
     * Gets Interchange field by position. The position is starting from 1.
     * 
     * @param position start from 1
     * @return Field
     */
    public abstract CompositeField getField(int position);

    public abstract String getSenderQualifier();

    public abstract void setSenderQualifier(String qualifier);

    public abstract String getSenderId();

    public abstract void setSenderId(String id);

    public abstract String getReceiverQualifier();

    public abstract void setReceiverQualifier(String qualifier);

    public abstract String getReceiverId();

    public abstract void setReceiverId(String id);

    public abstract String getVersion();

    public abstract void setVersion(String version);

    public abstract String getControlNumber();

    public abstract void setControlNumber(String controlNum);

    public abstract List<Segment> getSegment(String tag);

    public abstract List<CompositeField> getFields();
    
    /**
     * Shallow copy without relationship
     * 
     * @return copy of current instance
     */
    public abstract InterchangeEnvelope copy();
}
