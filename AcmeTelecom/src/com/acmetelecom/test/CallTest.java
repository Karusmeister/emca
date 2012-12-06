package com.acmetelecom.test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.acmetelecom.model.Call;
import com.acmetelecom.model.CallEnd;
import com.acmetelecom.model.CallStart;
import com.acmetelecom.model.Callee;
import com.acmetelecom.model.Caller;

public class CallTest {

	Caller caller = new Caller("Peter");
	Callee callee = new Callee("Adam");
	
	Call call;
	CallStart start;
	CallEnd end;
	
	@Before
	public void setUp() throws Exception {
		
		start = new CallStart(caller, callee);
		end = new CallEnd(caller, callee);
		call = new Call(start, end);
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testCorrectCalle() {
		assertEquals(callee, call.callee());
	}
	
	@Test
	public void testCorrectDate() {
		String nowStr = SimpleDateFormat.getInstance().format(new Date());
		assertEquals(nowStr, call.date());
	}
	
	@Test
	public void testCorrectTimes() {
		assertEquals(new Date(start.time()), call.startTime());
		assertEquals(new Date(end.time()), call.endTime());
	}

}
