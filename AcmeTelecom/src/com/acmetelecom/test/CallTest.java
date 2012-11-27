package com.acmetelecom.test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.acmetelecom.Call;
import com.acmetelecom.CallEnd;
import com.acmetelecom.CallStart;

public class CallTest {

	String caller = "Peter";
	String callee = "Adam";
	
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
