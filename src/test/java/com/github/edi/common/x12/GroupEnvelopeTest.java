package com.github.edi.common.x12;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.edi.common.Document;
import com.github.edi.common.GroupEnvelope;
import com.github.edi.common.Segment;
import com.github.edi.common.Transaction;
import com.github.edi.common.x12.X12Document;
import com.github.edi.common.x12.X12GroupEnvelope;
import com.github.edi.common.x12.X12InterchangeEnvelope;
import com.github.edi.common.x12.X12Transaction;

public class GroupEnvelopeTest {
	
	Document document;
	X12InterchangeEnvelope interchange;
	GroupEnvelope group;

	@Before
	public void setUp() throws Exception {
		document = new X12Document();
		interchange = new X12InterchangeEnvelope(new Segment(new String[16]));
		document.setInterchangeEnvelope(interchange);
		group = new X12GroupEnvelope(new Segment(new String[8]));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetInterchangeEnvelope() {
		group.setInterchangeEnvelope(interchange);
		
		assertEquals(interchange, group.getInterchangeEnvelope());
	}

	@Test
	public void testDetach() {
		interchange.addGroupEnvelope(group);
		group.detach();
		
		assertNull(group.getInterchangeEnvelope());
		assertEquals(0, interchange.getGroups().size());
	}
	
	@Test
	public void testAddTransaction() {
		Transaction txn = new X12Transaction(new Segment(new String[2]));
		group.addTransaction(txn);
		
		assertEquals(1, group.getTransactions().size());
		assertEquals(group, txn.getGroupEnvelope());
	}

}
