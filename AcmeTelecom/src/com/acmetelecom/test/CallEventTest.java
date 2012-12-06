package com.acmetelecom.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.acmetelecom.CallEvent;
import com.acmetelecom.Callee;
import com.acmetelecom.Caller;

public class CallEventTest {


	@Test
	public void testTimeZero() {

		Caller caller = new Caller("Peter");
		Callee callee = new Callee("Adam");
		
		long time = 0;
		
		CallEvent ce = new CallEvent(caller, callee, time) {
		};
		
		assertEquals(ce.getCaller(), caller);
		assertEquals(ce.getCallee(), callee);
		assertEquals(ce.time(), time);
	}

	@Test
	public void testTimeMaxLong() {

		Caller caller = new Caller("Peter");
		Callee callee = new Callee("Adam");
		
		long time = Long.MAX_VALUE;
		
		CallEvent ce = new CallEvent(caller, callee, time) {
		};
		
		assertEquals(ce.time(), time);
	}
	
	@Test
	public void testMinLong() {

		Caller caller = new Caller("Peter");
		Callee callee = new Callee("Adam");
		
		long time = Long.MIN_VALUE;
		
		CallEvent ce = new CallEvent(caller, callee, time) {
		};
		
		assertEquals(ce.time(), time);
	}
	
}
