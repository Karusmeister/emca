package com.acmetelecom;

import java.util.Calendar;
import java.util.Date;

public class PeakCalculator {
	public int onPeakTime(Date time, int duration)
	{
		int peakLowerLimitSeconds = 7*60*60;
		int peakUpperLimitSeconds = 19*60*60;
		
		 Calendar calendar = Calendar.getInstance();
	        calendar.setTime(time);
	        int startTimeHours = calendar.get(Calendar.HOUR_OF_DAY)*60*60;
	        int startTimeMinutes = calendar.get(Calendar.MINUTE)*60;
	        int startTimeSeconds = calendar.get(Calendar.SECOND);
	        
	        int startTime = startTimeHours+startTimeMinutes+startTimeSeconds;
	        int endTime = startTime+duration;
	       
	        if(endTime<=peakLowerLimitSeconds)
	        	return 0;
	        
	        if(startTime<peakLowerLimitSeconds && endTime>peakLowerLimitSeconds && endTime<=peakUpperLimitSeconds)
	        	return endTime-peakLowerLimitSeconds;
	        
	        if(startTime>=peakLowerLimitSeconds && startTime<peakUpperLimitSeconds && endTime>=peakUpperLimitSeconds)
	        	return peakUpperLimitSeconds-startTime;
	        
	        if(startTime>=peakLowerLimitSeconds && endTime<=peakUpperLimitSeconds)
	        	return duration;

       	return 0; //startSeconds>peakUpperLimitSeconds
	}

}
