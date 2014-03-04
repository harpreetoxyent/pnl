package com.oxymedical.component.db.utilities;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class DBDateTime 
{
    /**The month, 1-12*/
    int month;
    /**The day of the month, 1-31*/
    int day;
    /**The year, all digits*/
    int year;
    /**The hour of the day, 0-23*/
    int hour;
    /**The minutes of the hour, 0-59*/
    int min;
    /**The seconds of the minute, 0-59*/
    int sec;
    
    /**Create a DateTime object with the current date time
     *return DateTime initialized to the current date and time
     */

    public DBDateTime() {

        TimeZone tz = TimeZone.getTimeZone("GMT");
        Calendar rightNow = Calendar.getInstance(tz);

        year = rightNow.get(Calendar.YEAR);
        month = rightNow.get(Calendar.MONTH) + 1;
        day = rightNow.get(Calendar.DATE);
        hour = rightNow.get(Calendar.HOUR_OF_DAY);
        min = rightNow.get(Calendar.MINUTE);
        sec = rightNow.get(Calendar.SECOND);


        /**replaced for y2k and gmt
         Date today = new Date();
         year  = today.getYear() + 1900;
         month = today.getMonth() + 1;
         day   = today.getDate();
         hour  = today.getHours();
         min   = today.getMinutes();
         sec   = today.getSeconds();
         */
    }

    /**Create a DateTime object with the requested values
     *@param yr year (full year, e.g., 1997)
     *@param mo month ( 1-12)
     *@param da date (1-31)
     *@param hr hour of day (0-23)
     *@param mn minute of hour (0-59)
     *@param sc second of minute (0-59)
     *@exception IllegalArgumentException if something wrong in date
     */

    public DBDateTime(int yr, int mo, int da, int hr, int mn, int sc) {

        year = yr;
        month = mo;
        day = da;
        hour = hr;
        min = mn;
        sec = sc;

        if (!isValid()) throw new IllegalArgumentException();
    }

    /**Create a DateTime object with the requested values, 0 for rest
     *@param yr year (full year, e.g., 1997)
     *@param mo month ( 1-12)
     *@param da date (1-31)
     *@exception IllegalArgumentException if something wrong in date
     */

    public DBDateTime(int yr, int mo, int da) {
        this(yr, mo, da, 0, 0, 0);
    }

    /**Create a DateTime object from a Calendar object
     *@param inCalendar is the incomming calendar object
     */
    public DBDateTime(Calendar inCalendar) {
        //initialize to gmt from incomming calendar time
        year = inCalendar.get(Calendar.YEAR);
        month = inCalendar.get(Calendar.MONTH) + 1;
        day = inCalendar.get(Calendar.DATE);
        hour = inCalendar.get(Calendar.HOUR_OF_DAY);
        min = inCalendar.get(Calendar.MINUTE);
        sec = inCalendar.get(Calendar.SECOND);
        //adjust to gmt
        TimeZone tz = inCalendar.getTimeZone();
        int off = tz.getRawOffset();
        advanceSecs(-off / 1000);
       
    }

    /**Create a DateTime object with the requested julian date
     * @param julian the date time in our julian expanded format, i.e. date/time
     * @exception IllegalArgumentException if something wrong
     */

    public DBDateTime(int julian) {

        fromJulian(julian);
        if (!isValid()) throw new IllegalArgumentException();
    }

    /**
     * Advances this DateTime by n days
     * @param n the number of days by which to change this day, + or -
     * @deprecated Replaced by advanceDays
     */

    public void advance(int n) {
        advanceDays(n);
    }


    /**
     * Advances this DateTime by n days
     * @param n the number of days by which to change this day, + or -
     */

    public void advanceDays(int n) {
        fromJulian(toJulian() + n);
    }


    /**
     * Advances this DateTime by hr hours
     * @param hr is the number of hours to add
     */

    public void advanceHours(int hr)
    {
        double addval = hr;
        //DBComponent.logger.log(0,"day = " + day);
        addval = addval / 24;
        fromJulian(toJulian() + addval);
       /* Calendar cal = Calendar.getInstance();
        cal.setTime(cal);
        cal.set(year, month, day);
        //DBComponent.logger.log(0," day = " + day);
       return hour;*/
    }


    /**
     * Advances this DateTime by min minutes
     * @param min is the number of minutes to add
     */

    public void advanceMins(int min) {
        double addval = min;
        addval = addval / 60 / 24;
        fromJulian(toJulian() + addval);
    }


    /**
     * Advances this DateTime by min minutes
     * @param sec is the number of minutes to add
     */

    public void advanceSecs(int sec) {
        double addval = sec;
        addval = addval / 60 / 60 / 24;
        fromJulian(toJulian() + addval);
    }


    /**@return day of the month as an int, 1-31*/

    public int getDay() {
        return day;
    }


    /**@return month as an int, 1-12*/

    public int getMonth() {
        return month;
    }


    /**@return year as an int, all digits*/

    public int getYear() {
        return year;
    }


    /**@return hour as an int*/

    public int getHour() {
        return hour;
    }


    /**@return minutes as an int*/

    public int getMin() {
        return min;
    }


    /**@return seconds as an int*/

    public int getSecs() {
        return sec;
    }


    /**@return the weekday (0-6, Sunday-Saturday)*/

    public int weekday() {
        //double dj = toJulian();
        //int ij = (int) dj;
        //int rj = (ij - 2440000) % 7;
        //DBComponent.logger.log(0,dj + " " + ij + " " + rj);


        return (((int) toJulian() + 1) % 7);
    }


    /**
     * positive if this is later than the parameter
     * @param other any date
     * @return the number of days between this and the parameter
     */

    public int daysBetween(DBDateTime other) {
        int ithis, iother;

        ithis = (int) toJulian();
        iother = (int) other.toJulian();
        return (ithis - iother);
    }

    /**@return a copy of this object*/

    public Object clone() {

        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }


    /**@return true if this is a valid DateTime*/

    public boolean isValid() {

        DBDateTime dt = new DBDateTime();

        dt.fromJulian(this.toJulian());

        return dt.day == day && dt.month == month && dt.year == year
                && dt.hour == hour && dt.min == min && dt.sec == sec;
    }


    /**
     *@return the julian day as the integer part and the 24hr time as fractional
     *<p><i>2440000 was 5/23/68 a Thursday</i>
     */

    public double toJulian() {

        int y = year;
        int m = month;
        double timeofday;
        int ijulian;
        int IGREG = 15 + 31 * (10 + 12 * 1582); // Greg.Calendar 10/15/1582
        int adj;

        if (y < 0) y = y + 1;
        if (m > 2)
            m = m + 1;
        else {
            y = y - 1;
            m = m + 13;
        }

        ijulian = (int) (365.25 * y) + (int) (30.6001 * m) + day + 1720995;

        if (day + 31 * (m + 12 * y) >= IGREG) { // change for Gregorian calendar
            adj = y / 100;
            ijulian = ijulian + 2 - adj + adj / 4;
        }

        timeofday = hour / 24.0 + min / 1440.0 + sec / 86400.0;
        return ijulian + timeofday;
    }


    /**Converts a TeleMed julian (both date & time) to a DateTime
     *The DataTime object exists and its values are changed
     *@param injulian the TeleMed julian daytime number
     */

    public void fromJulian(double injulian) {

        int ja,jalpha,jb,jc,jd,je;
        int JGREG = 2299161; //  julian date of Geg calendar
        double halfsecond = 0.5;

        double julian = injulian + halfsecond / 86400.0;

        ja = (int) julian;

        if (ja >= JGREG) {    //adjust
            jalpha = (int) (((ja - 1867216) - 0.25) / 36524.25);
            ja = ja + 1 + jalpha - jalpha / 4;
        }

        jb = ja + 1524;
        jc = (int) (6680.0 + ((jb - 2439870) - 122.1) / 365.25);
        jd = 365 * jc + jc / 4;
        je = (int) ((jb - jd) / 30.6001);
        day = jb - jd - (int) (30.6001 * je);
        month = je - 1;
        if (month > 12) month = month - 12;
        year = jc - 4715;
        if (month > 2) year = year - 1;
        if (year <= 0) year = year - 1;

        // now for the time

        double thetime = julian - (int) julian;
        thetime = thetime * 24;
        hour = (int) thetime;
        thetime = (thetime - hour) * 60;
        min = (int) thetime;
        thetime = (thetime - min) * 60;
        sec = (int) thetime;
        //DBComponent.logger.log(0,hour);

    }

    /**Converts from PID time to the DateTime class
     *@param thepidtime the pid time as a long, has both date and time
     */

    public void fromPIDTime(long thepidtime) {
        double jul;
        jul = (double) thepidtime;
        jul = jul / 10000000 / 60 / 60 / 24;
        fromJulian(jul);
    }


    /**Converts the DateTime to PID time
     *@return the PID time as a long, both date and time
     */

    public long toPIDTime() {
        double jul = toJulian();
        long pidtime = (long) (jul * 24 * 60 * 60 * 10000000);
        return pidtime;
    }


    /**Creates a calendar object using GMT timezone
     *@return a Calendar with GMT time zone set properly
     */
    public Calendar makeGMTCalendar() {
        Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
        gmtCal.set(year, month - 1, day, hour, min, sec);
        return gmtCal;
    }


    /**Creates a calendar object using Local timezone
     *@return a Calendar with Local time zone set properly
     */
    public Calendar makeLocalCalendar() {
        Calendar locCal = Calendar.getInstance();
        TimeZone tz = locCal.getTimeZone();
        int off = tz.getRawOffset() / 1000;
        advanceSecs(off);
        locCal.set(year, month - 1, day, hour, min, sec);
        advanceSecs(-off);
        return locCal;
    }
    
    public String createDateString()
    {
    	 String createdDate = null;
         
    	 String strDate = Integer.toString(day);
    	 String strMonth = Integer.toString(month);
    	  
    	 if(day < 10)
    		 strDate = "0" + strDate;
    	 if(month < 10)
    		 strMonth = "0" + strMonth;
    	 createdDate = strDate + "-" + strMonth + "-" + Integer.toString(year);
    	// DBComponent.logger.log(0,"date = " + createdDate);
    	 return createdDate;
         // return cal;
   }


}

