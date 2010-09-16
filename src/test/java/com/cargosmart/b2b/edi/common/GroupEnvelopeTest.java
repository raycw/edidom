package com.cargosmart.b2b.edi.common;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GroupEnvelopeTest {
	
	Document document;
	InterchangeEnvelope interchange;
	GroupEnvelope group;

	@Before
	public void setUp() throws Exception {
		document = new Document();
		interchange = new InterchangeEnvelope();
		document.setInterchangeEnvelope(interchange);
		group = new GroupEnvelope();
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
		Transaction txn = new Transaction();
		group.addTransaction(txn);
		
		assertEquals(1, group.getTransactions().size());
		assertEquals(group, txn.getGroupEnvelope());
	}

}
