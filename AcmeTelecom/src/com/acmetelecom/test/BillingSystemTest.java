package com.acmetelecom.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.acmetelecom.BillingSystem;
import com.acmetelecom.model.Call;
import com.acmetelecom.model.CallEvent;
import com.acmetelecom.model.CallStart;
import com.acmetelecom.model.Callee;
import com.acmetelecom.model.Caller;

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
		List<Call> log = bs.calls;
		assertEquals(0, log.size());
		
		HashMap<String, CallEvent> callLog = bs.callLog;
		assertEquals(0, callLog.size());
	}

	@Test
	public void testAddStartToLog() {
		Caller caller = new Caller("225899");
		Callee callee = new Callee("112233");

		bs.callInitiated(caller, callee);

		HashMap<String, CallEvent> callLog = bs.callLog;
		assertEquals(1, callLog.size());

		Object call = callLog.get(caller.getPhoneNumber());
		assertEquals(CallStart.class, call.getClass());

		CallStart call1 = (CallStart)call;
		assertEquals(caller, call1.getCaller());
		assertEquals(callee, call1.getCallee());
	}

	@Test
	public void testAddEndToLog() {
		Caller caller = new Caller("00525");
		Callee callee = new Callee("99525");

		bs.callInitiated(caller, callee);
		bs.callCompleted(caller, callee);

		HashMap<String, CallEvent> callLog = bs.callLog;
		assertEquals(0, callLog.size());

		List<Call> log = bs.calls;
		
		Object call = log.get(0);
		assertEquals(Call.class, call.getClass());

		Call aCall = (Call) call;
		assertEquals(caller, aCall.caller());
		assertEquals(callee, aCall.callee());
	}

	@Test
	public void testCreateBillsClearsLog() {
		Caller caller = new Caller("Peter");
		Callee callee = new Callee("Adam");

		bs.callInitiated(caller, callee);
		bs.callCompleted(caller, callee);

		List<Call> log = bs.calls;
		assertEquals(1, log.size());

		bs.createCustomerBills();

		log = bs.calls;
		assertEquals(0, log.size());

	}

}
