package com.acmetelecom.test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.acmetelecom.*;

public class DaytimePeakPeriodTest {

	@Test
	public void testOneOnPeak() {
		
		Calendar onPeakDate = Calendar.getInstance();
		
		// set it to 3 pm.
		onPeakDate.set(2005, 3, 4, 3, 0, 0);
		onPeakDate.set(Calendar.AM_PM, Calendar.PM);
	
		DaytimePeakPeriod peakPeriod = new DaytimePeakPeriod();
		
		assertEquals(false, peakPeriod.offPeak(onPeakDate.getTime()));
	}
	
	@Test
	public void testPeakBottomBoundary() {
		
		// the peak time is between 7 am and 19 pm
		Calendar onPeakCalendar = Calendar.getInstance();
		
		// set it to 6.59 am, make sure this is off peak
		onPeakCalendar.set(Calendar.HOUR, 6);
		onPeakCalendar.set(Calendar.MINUTE, 59);
		onPeakCalendar.set(Calendar.AM_PM, Calendar.AM);
	
		DaytimePeakPeriod peakPeriod = new DaytimePeakPeriod();
		Date onPeakDate = onPeakCalendar.getTime();
		assertEquals(true, peakPeriod.offPeak(onPeakDate));
	}
	
	@Test
	public void testPeakTopBoundary() {
		
		// the peak time is between 7 am and 19 pm
		Calendar onPeakCalendar = Calendar.getInstance();

		// set it to 19.01 pm, make sure this is off peak
		onPeakCalendar.set(Calendar.HOUR, 7);
		onPeakCalendar.set(Calendar.MINUTE, 01);
		onPeakCalendar.set(Calendar.AM_PM, Calendar.PM);

		DaytimePeakPeriod peakPeriod = new DaytimePeakPeriod();
		Date onPeakDate = onPeakCalendar.getTime();
		assertEquals(true, peakPeriod.offPeak(onPeakDate));
	}
	
	@Test
	public void testMiddleOfNight() {
		
		// the peak time is between 7 am and 19 pm
		Calendar onPeakCalendar = Calendar.getInstance();

		onPeakCalendar.set(Calendar.HOUR, 3);
		onPeakCalendar.set(Calendar.MINUTE, 30);
		onPeakCalendar.set(Calendar.AM_PM, Calendar.AM);

		DaytimePeakPeriod peakPeriod = new DaytimePeakPeriod();
		Date onPeakDate = onPeakCalendar.getTime();
		assertEquals(true, peakPeriod.offPeak(onPeakDate));
	}

}
