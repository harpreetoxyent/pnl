package com.oxymedical.component.logging.settings;
// check the comments - put version control - RK
import java.util.Date;
/**
* This function is used to specify the timestamp,classname,
* methodName and date of a field.
*/
public class Field 
{
	// all fields private 
	String timeStamp;
	String className;
	String methodName;
	Date date;
	/**
	* This function is used to get the classname of a field.
	* @return string
	*/
	public String getClassName() {
		return className;
	}
	/**
	* This function is used to set the classname of a field.
	* @param className 
	* @return void
	*/
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	* This function is used to get the date of a field.
	* @return date
	*/
	public Date getDate() {
		return date;
	}
	/**
	* This function is used to set the date of a field.
	* @param date it is used to give the date 
	* at which log file was created
	* @return void
	*/
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	* This function is used to get the methodName of a field.
	* @return string
	*/
	public String getMethodName() {
		return methodName;
	}
	/**
	* This function is used to set the MethodName of a field.
	* @param MethodName it is used to name the method of
	* logging component
	* @return void
	*/
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	* This function is used to get the TimeStamp of a field.
	* @return string
	*/
	public String getTimeStamp() {
		return timeStamp;
	}
	/**
	* This function is used to set the TimeStamp of a field.
	* @param timestamp used to give the time needed for logging
	* @return void
	*/
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
}
