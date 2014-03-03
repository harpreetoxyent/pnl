package com.oxymedical.core.dateutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;



/**
 * This class used for Date
 * @author pra
 *
 */
public class DateUtil {
 
	

	/**
	 * This method is used to compare two dates
	 * @param first
	 * @param second
	 * @return int
	 */
	public static int compareDates(Date first,Date second)
	{
		int result=first.compareTo(second);
		return result;
	}
	

	
	/**
	 * This method returns the date in the given format.
	 * @param date
	 * @param format
	 * @return Date
	 */
	public static Date stringToDate(String date,String format)
	{
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date convertedDate1 = null;
		try {
			convertedDate1 = dateFormat.parse(date);
		} 
		catch (ParseException e1) 
		{
			e1.printStackTrace();
		}
		return convertedDate1; 
	}
	

	/**
	 * This method returns the Date in String format.
	 * @param date
	 * @param format
	 * @return String
	 */
	public static String formatDate(Date date,String format)
	{
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String curDate = "";
		curDate = dateFormat.format(date);
		return curDate;
	}
	
	/**
	 * This method takes input date,increment day and date format,
	 * return incremented date in string format.
	 * @param date
	 * @param ofSet
	 * @return
	 */
	public static String getNextDate(String date,String ofSet,String formatType)
	{
		double nODays= 1;
		if(ofSet!=null && ofSet.trim().length()>1)
		{
			nODays = Double.parseDouble(ofSet.trim());
		}
		double MILLIS_IN_DAY = (1000 * 60 * 60 * 24)*nODays;
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatType);
		String nextDate = null;
		Date convertedDate1 = null;
		try {
			convertedDate1 = dateFormat.parse(date);
		} 
		catch (ParseException e1) 
		{
			e1.printStackTrace();
		}
	    nextDate = dateFormat.format(convertedDate1.getTime() + MILLIS_IN_DAY);
		return nextDate;
	}
	

	/**
	 * Returns current date in specified format
	 * @param format
	 * @return
	 */
	public static String getCurrentDateTime(String format)
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(cal.getTime());
	}
	

	/**
	 * This method used to check date is valid format or not.
	 * @param dateStr
	 * @param formatStr
	 * @return
	 */
	public static boolean validateDate(String dateStr,String formatStr)
	{
		if (formatStr == null) return false; // or throw some kinda exception, possibly a InvalidArgumentException
		SimpleDateFormat df = new SimpleDateFormat(formatStr);
		Date testDate = null;
		try
		{
			df.setLenient(false); 
			testDate = df.parse(dateStr);
		}
		catch (ParseException e)
		{
			// invalid date format
			return false;
		}
		return true;
	}
	
	
}