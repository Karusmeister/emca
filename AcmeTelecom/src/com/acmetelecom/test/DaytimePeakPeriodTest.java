package com.acmetelecom.test;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
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

}
