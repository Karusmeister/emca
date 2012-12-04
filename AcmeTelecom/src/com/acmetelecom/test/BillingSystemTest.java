package com.acmetelecom.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.acmetelecom.BillingSystem;
import com.acmetelecom.CallEnd;
import com.acmetelecom.CallEvent;
import com.acmetelecom.CallStart;

public class BillingSystemTest {

	BillingSystem bs;
	
	@Before
	public void setUp() throws Exception {
		bs = new BillingSystem();
	}

	@After
	public void tearDown() throws Exception {
	}

	/*@Test
	public void testcheckCallLogEmpty() {
		List<CallEvent> log = bs.callLog;
		assertEquals(0, log.size());
	}*/
	
	/*@Test
	public void testAddStartToLog() {
		String caller = "Peter";
		String callee = "Adam";
		
		bs.callInitiated(caller, callee);
		
		List<CallEvent> log = bs.callLog;
		assertEquals(1, log.size());
		
		Object call = log.get(0);
		assertEquals(CallStart.class, call.getClass());
		
		CallStart start = (CallStart) call;
		assertEquals(caller, start.getCaller());
		assertEquals(callee, start.getCallee());
	}*/
	
	/*@Test
	public void testAddEndToLog() {
		String caller = "Peter";
		String callee = "Adam";
		
		bs.callInitiated(caller, callee);
		bs.callCompleted(caller, callee);
		
		List<CallEvent> log = bs.callLog;
		assertEquals(2, log.size());
		
		Object call = log.get(1);
		assertEquals(CallEnd.class, call.getClass());
		
		CallEnd start = (CallEnd) call;
		assertEquals(caller, start.getCaller());
		assertEquals(callee, start.getCallee());
	}*/
	
	/*@Test
	public void testCreateBillsClearsLog() {
		String caller = "Peter";
		String callee = "Adam";
		
		bs.callInitiated(caller, callee);
		bs.callCompleted(caller, callee);
		
		List<CallEvent> log = bs.callLog;
		assertEquals(2, log.size());
		
		bs.createCustomerBills();
		
		log = bs.callLog;
		assertEquals(0, log.size());
		
	}*/

}
