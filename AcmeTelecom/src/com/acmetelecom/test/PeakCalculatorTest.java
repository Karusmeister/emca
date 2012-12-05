package com.acmetelecom.test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.acmetelecom.*;

public class PeakCalculatorTest {

	@Test
	public void withinPeakTime() {
		
		// the peak time is between 7 am and 19 pm
		Calendar testCalendar = Calendar.getInstance();
		
		// set start time to 07.00.20 am, duration to 20, 20 sec within the peak period
		testCalendar.set(Calendar.HOUR, 7);
		testCalendar.set(Calendar.MINUTE, 0);
		testCalendar.set(Calendar.SECOND, 20);
		testCalendar.set(Calendar.AM_PM, Calendar.AM);
	
		PeakCalculator peakCalculator = new PeakCalculator();
		Date testTime = testCalendar.getTime();
		assertEquals(20, peakCalculator.onPeakTime(testTime, 20));
	}
	
	@Test
	public void beforePeakTime() {
		
		Calendar testCalendar = Calendar.getInstance();
		
		// set start time to 6.00.20 am, duration to 20, 0 sec on peak period
		testCalendar.set(Calendar.HOUR, 6);
		testCalendar.set(Calendar.MINUTE, 0);
		testCalendar.set(Calendar.SECOND, 20);
		testCalendar.set(Calendar.AM_PM, Calendar.AM);
	
		PeakCalculator peakCalculator = new PeakCalculator();
		Date testTime = testCalendar.getTime();
		assertEquals(0, peakCalculator.onPeakTime(testTime, 20));
	}
	
	@Test
	public void afterPeakTime() {
		
		Calendar testCalendar = Calendar.getInstance();
		
		// set start time to 7.00.20 pm, duration to 20, 0 sec on peak period
		testCalendar.set(Calendar.HOUR, 7);
		testCalendar.set(Calendar.MINUTE, 0);
		testCalendar.set(Calendar.SECOND, 20);
		testCalendar.set(Calendar.AM_PM, Calendar.PM);
	
		PeakCalculator peakCalculator = new PeakCalculator();
		Date testTime = testCalendar.getTime();
		assertEquals(0, peakCalculator.onPeakTime(testTime, 20));
	}
	
	@Test
	public void extendToPeakTime() {
		
		Calendar testCalendar = Calendar.getInstance();
		
		// set start time to 06.59.20 pm, duration to 90, 40 sec on peak period
		testCalendar.set(Calendar.HOUR, 6);
		testCalendar.set(Calendar.MINUTE, 59);
		testCalendar.set(Calendar.SECOND, 20);
		testCalendar.set(Calendar.AM_PM, Calendar.PM);
	
		PeakCalculator peakCalculator = new PeakCalculator();
		Date testTime = testCalendar.getTime();
		assertEquals(40, peakCalculator.onPeakTime(testTime, 90));
	}
	
	@Test
	public void extendFromPeakTimeToAfter() {
		
		Calendar testCalendar = Calendar.getInstance();
		
		// set start time to 06.59.40 am, duration to 40, 20 sec on peak period
		testCalendar.set(Calendar.HOUR, 6);
		testCalendar.set(Calendar.MINUTE, 59);
		testCalendar.set(Calendar.SECOND, 40);
		testCalendar.set(Calendar.AM_PM, Calendar.PM);
	
		PeakCalculator peakCalculator = new PeakCalculator();
		Date testTime = testCalendar.getTime();
		assertEquals(20, peakCalculator.onPeakTime(testTime, 40));
	}
	
}
