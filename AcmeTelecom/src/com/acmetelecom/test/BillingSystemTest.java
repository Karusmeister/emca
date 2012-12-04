package com.acmetelecom.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.acmetelecom.BillingSystem;
import com.acmetelecom.Call;
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

	@Test
	public void testcheckCallLogEmpty() {
		
		HashMap<String, CallEvent> log = bs.callLog;
		assertEquals(0, log.size());
		
		List<Call> calls = bs.calls;
		assertEquals(0, calls.size());
	}
	
	@Test
	public void testAddStartToLog() {
		String caller = "Peter";
		String callee = "Adam";
		
		bs.callInitiated(caller, callee);
		
		HashMap<String, CallEvent> log = bs.callLog;
		assertEquals(1, log.size());
	}
	
	@Test
	public void testAddEndToLog() {
		String caller = "Peter";
		String callee = "Adam";
		
		bs.callInitiated(caller, callee);
		bs.callCompleted(caller, callee);
		
		HashMap<String, CallEvent> log = bs.callLog;
		assertEquals(0, log.size());
		
		List<Call> calls = bs.calls;
		assertEquals(1, calls.size());
	}
	
	@Test
	public void testCreateBillsClearsLog() {
		String caller = "Peter";
		String callee = "Adam";
		
		bs.callInitiated(caller, callee);
		bs.callCompleted(caller, callee);
		
		bs.createCustomerBills();
		
		HashMap<String, CallEvent> log = bs.callLog;
		assertEquals(0, log.size());
		
		List<Call> calls = bs.calls;
		assertEquals(0, calls.size());
	}

}
