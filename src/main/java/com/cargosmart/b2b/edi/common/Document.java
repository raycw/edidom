/**
 * 
 */
package com.cargosmart.b2b.edi.common;

/**
 * An EDI document. It is the root element to access EDI message.
 * 
 * @author Raymond Chin
 * 
 */
public class Document {

	private InterchangeEnvelope interchange;

	public Document() {

	}
	
	/**
	 * Sets the InterchangeEnvelope of the <code>Document</code>. 
	 * 
	 * Document only contains 1 <code>InterchangeEnvelope</code>. 
	 * @param interchange new content of the <code>Document</code>
	 * @return this document
	 */
	public Document setInterchangeEnvelope(InterchangeEnvelope interchange) {
		this.interchange = interchange;
		interchange.setDocument(this);
		return this;
	}

	/**
	 * Returns the <code>InterchangeEnvelope</code> of the EDI document.
	 * @return InterchangeEnvelope of this <code>Document</code>
	 */
	public InterchangeEnvelope getInterchangeEnvelope() {
		return interchange;
	}

}
