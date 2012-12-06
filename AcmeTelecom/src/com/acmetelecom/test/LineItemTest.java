package com.acmetelecom.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.acmetelecom.BillingSystem.LineItem;
import com.acmetelecom.Call;
import com.acmetelecom.CallEnd;
import com.acmetelecom.CallStart;
import com.acmetelecom.Callee;
import com.acmetelecom.Caller;

public class LineItemTest {
	
	Caller caller = new Caller("Peter");
	Callee callee = new Callee("Adam");
	BigDecimal callCost = new BigDecimal(12.50);
	Call call = new Call(new CallStart(caller, callee), new CallEnd(caller, callee));
	LineItem li;
	
	@Before
	public void setUp() throws Exception {
		li = new LineItem(call, callCost);
	}

	@After
	public void tearDown() throws Exception {

	}
	
	@Test
	public void testGetCall() {
		assertEquals(call, li.getCall());
	}
	
	@Test
	public void testSetCall() {
		
		Caller caller = new Caller("A");
		Callee callee = new Callee("B");
		
		Call call = new Call(new CallStart(caller, callee), new CallEnd(caller, callee));
		li.setCall(call);
		
		assertEquals(call, li.getCall());
	}
	
	@Test
	public void testGetCallCost() {
		assertEquals(callCost, li.getCallCost());
	}
	
	@Test
	public void testSetCallCost() {
		BigDecimal newCallCost = new BigDecimal(5.0);
		li.setCallCost(newCallCost);
		assertEquals(newCallCost, li.getCallCost());
	}
	
	@Test
	public void testDate() {
		assertEquals(call.date(), li.date());
	}
	
	@Test
	public void testGetCallee() {
		assertEquals(callee, li.callee());
	}
	
}
