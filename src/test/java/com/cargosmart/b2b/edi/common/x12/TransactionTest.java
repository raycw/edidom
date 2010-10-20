package com.cargosmart.b2b.edi.common.x12;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cargosmart.b2b.edi.common.GroupEnvelope;
import com.cargosmart.b2b.edi.common.Segment;
import com.cargosmart.b2b.edi.common.Transaction;
import com.cargosmart.b2b.edi.common.x12.X12GroupEnvelope;

public class TransactionTest {

	Transaction txn;
	GroupEnvelope group;
	@Before
	public void setUp() throws Exception {
		group = new X12GroupEnvelope(new Segment(new String[8]));
		txn = new X12Transaction(new Segment(new String[2]));
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
		Segment segment = new Segment(new String[16]);
		txn.addSegment(segment);
		
		assertEquals(segment, txn.getSegements().get(0));
	}

}
