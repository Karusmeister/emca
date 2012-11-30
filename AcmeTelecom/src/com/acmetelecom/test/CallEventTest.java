package com.acmetelecom.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.acmetelecom.CallEvent;

public class CallEventTest {


	@Test
	public void testTimeZero() {

		String caller = "Peter";
		String callee = "Adam";
		long time = 0;
		
		CallEvent ce = new CallEvent(caller, callee, time) {
		};
		
		assertEquals(ce.getCaller(), caller);
		assertEquals(ce.getCallee(), callee);
		assertEquals(ce.time(), time);
	}

	@Test
	public void testTimeMaxLong() {

		String caller = "Peter";
		String callee = "Adam";
		long time = Long.MAX_VALUE;
		
		CallEvent ce = new CallEvent(caller, callee, time) {
		};
		
		assertEquals(ce.time(), time);
	}
	
	@Test
	public void testMinLong() {

		String caller = "Peter";
		String callee = "Adam";
		long time = Long.MIN_VALUE;
		
		CallEvent ce = new CallEvent(caller, callee, time) {
		};
		
		assertEquals(ce.time(), time);
	}
	
}
