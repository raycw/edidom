package com.cargosmart.b2b.edi.common;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TransactionTest {

	Transaction txn;
	GroupEnvelope group;
	@Before
	public void setUp() throws Exception {
		group = new GroupEnvelope();
		txn = new Transaction();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDetach() {
		group.addTransaction(txn);
		
		assertEquals(group, txn.getGroupEnvelope());
		txn.detach();
		
		assertNull(txn.getGroupEnvelope());
		assertFalse(group.getTransactions().contains(txn));
	}
	
	@Test
	public void testGroupTxnRelationship() {
		group.addTransaction(txn);
		
		assertEquals(txn, group.getTransactions().get(0));
		assertEquals(group, txn.getGroupEnvelope());
	}
	
	@Test
	public void testAddSegement() {
		Segment segment = new Segment();
		txn.addSegment(segment);
		
		assertEquals(segment, txn.getSegements().get(0));
	}

}
