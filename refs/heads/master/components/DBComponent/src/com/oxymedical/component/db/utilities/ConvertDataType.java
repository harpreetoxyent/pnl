package com.oxymedical.component.db.utilities;


import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.oxymedical.component.db.constants.DBConstants;
import com.oxymedical.component.db.constants.DataTypeConstants;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.exception.DBExceptionConstants;

/**
 * This class is used to check the type of field and then 
 * set the object value in the class.
 * @author      Oxyent Medical
 * @version     1.0.0
*/
public class ConvertDataType
{
	/**
     * This method is used to check the type of field passed in the
     * method and then set the object value in it.     
     * @param objectValue  
     * @param fieldType
     * @returns Object
     * @throws DBComponentException
     * Store null default value 
     * 
    */
	public Object setObjectValue(Object objectValue, String fieldType) throws DBComponentException
	{
		Object resultObject = null;
		String[] field=fieldType.split("\\.");
		for(int i=0;i<field.length;i++)
		{
			System.out.println("Elements of fieldType[] "+field[i]);
		}
		String dataType=field[field.length-1];
		System.out.println("The data type is: "+dataType);
		String objectStringValue = null;
		try
		{
			
			if(dataType.toLowerCase().equalsIgnoreCase(DataTypeConstants.TYPE_INT))
			{
				
				if(null == objectValue || objectValue.equals(DataTypeConstants.TYPE_NULL)  || objectValue.equals(DBConstants.DB_BLANK))
				{
					// store nulll as default value
					//resultObject = 0;
				}
				else
				{
					objectStringValue = this.hasNumeral(objectValue , dataType);
					objectStringValue = objectStringValue.trim();
					if(objectStringValue.trim().length()>0)
					resultObject =  Integer.parseInt(objectStringValue);
				}
			}
			else
			if(dataType.toLowerCase().equalsIgnoreCase(DataTypeConstants.TYPE_SHORT))
			{
				
				if(null == objectValue || objectValue.equals(DataTypeConstants.TYPE_NULL)  || objectValue.equals(DBConstants.DB_BLANK))
				{
				//	store nulll as default value
					//Short ln = 0;
					//resultObject = ln;
				}
				else
				{
					objectStringValue = this.hasNumeral(objectValue , dataType);
					resultObject =  Short.parseShort(objectStringValue);
				}
			}
			else if(dataType.toLowerCase().equalsIgnoreCase(DataTypeConstants.TYPE_LONG))
			{
				
				if(null == objectValue || objectValue.equals(DataTypeConstants.TYPE_NULL)  || objectValue.equals(DBConstants.DB_BLANK))
				{
					//Long ln = 0l;
					//resultObject = ln;
				}
				else
				{
					objectStringValue = this.hasNumeral(objectValue , dataType);
					resultObject =  Long.parseLong(objectStringValue);
				}
			}
			else if(dataType.toLowerCase().equalsIgnoreCase(DataTypeConstants.TYPE_STRING))
			{
				if(null == objectValue || objectValue.equals(DataTypeConstants.TYPE_NULL)  || objectValue.equals(DBConstants.DB_BLANK))
				{
					resultObject =  DBConstants.DB_SPACE;
				}
				else
					resultObject =  (String) objectValue;
			}
			else if(dataType.toLowerCase().equalsIgnoreCase(DataTypeConstants.TYPE_FLOAT))
			{
				
				if(null == objectValue || objectValue.equals(DataTypeConstants.TYPE_NULL)  || objectValue.equals(DBConstants.DB_BLANK))
				{
					//Float fl = 0f;
					//resultObject = fl;
				}
				else
				{
					objectStringValue = this.hasNumeral(objectValue , dataType);
					resultObject = new Float(objectStringValue);
				}

			}
			else if(dataType.toLowerCase().equalsIgnoreCase(DataTypeConstants.TYPE_DOUBLE))
			{
				objectStringValue = this.hasNumeral(objectValue , dataType);
				if(null == objectValue || objectValue.equals(DataTypeConstants.TYPE_NULL)  || objectValue.equals(DBConstants.DB_BLANK))
				{
					/*Double d1 = 0.0;
					resultObject = d1;*/
				}
				else
				{	
					//String.valueOf(objectValue);
					objectStringValue = this.hasNumeral(objectValue , dataType);
					resultObject = Double.parseDouble(objectStringValue);
					//resultObject = new Double(objectValue);
				}
			}		

			else if(dataType.toLowerCase().equalsIgnoreCase(DataTypeConstants.TYPE_DATE))
			{
				if(null == objectValue || objectValue.equals(DataTypeConstants.TYPE_NULL)  || objectValue.equals(DBConstants.DB_BLANK))
				{
					// when objectvalue is null return null value chages by wasim, 07-July-2009
					/*Date d = new Date();
					resultObject = d;*/
				}
				else
				{	
					String resultObject1 =  (String)objectValue;
					SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date parsedDate = formater.parse(resultObject1);
					java.sql.Date result = new java.sql.Date(parsedDate.getTime()); 
					//SimpleDateFormat dateFormat = new SimpleDateFormat(DataTypeConstants.TYPE_DATEformat); 
					//java.sql.Date date1 = dateFormat.parse((String)objectValue);
					resultObject = result;
					
				//DateTime. st;
				}
			}
			else if(dataType.toLowerCase().equalsIgnoreCase(DataTypeConstants.TYPE_TIMESTAMP))
			{
				SimpleDateFormat sdf = null;
				String dateString =  (String)objectValue.toString();
				if(null == objectValue || objectValue.equals(DataTypeConstants.TYPE_NULL)  || objectValue.equals(DBConstants.DB_BLANK))
				{
					//DBComponent.logger.log(0,"Inside the object value is null " + objectValue.toString());
					sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
					resultObject = sdf.format(new Date());
				}
				else
				{	
					//DBComponent.logger.log(0,"Inside the object value is null " + objectValue.toString());
					if(dateString.contains(DBConstants.DB_SPACE))
						sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					else
						sdf = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date = sdf.parse(dateString);
					//java.util.Date date = new Date(objectValue.toString());
					resultObject = new java.sql.Timestamp(date.getTime());
				}
				
			}
			/*This else part added for time fieldtype
			 * Changes by wasim 08-07-09
			 */
			else if(dataType.toLowerCase().equalsIgnoreCase(DataTypeConstants.TYPE_TIMES))
			{
				if(null == objectValue || objectValue.equals(DataTypeConstants.TYPE_NULL)  || objectValue.equals(DBConstants.DB_BLANK))
				{
					
				}
				else
				{	
					if(!(objectValue instanceof Time))
					{
						Time time = Time.valueOf((String) objectValue);
						resultObject = time;
					}
					else
					{
						resultObject = objectValue;
					}
				}
			}
			else if(dataType.toLowerCase().equalsIgnoreCase(DataTypeConstants.TYPE_BYTE))
			{	
				if(objectValue.equals(DataTypeConstants.TYPE_TRUE))
				{
					objectStringValue = DataTypeConstants.TYPE_ONE;
				}
				else
				if(objectValue.equals(DataTypeConstants.TYPE_FALSE))
				{
					objectStringValue = DataTypeConstants.TYPE_ZERO;
				}
				else
				{
					objectStringValue = hasNumeral(objectValue , dataType);
				}
				resultObject =  Byte.valueOf(objectStringValue);
			}
			else
				resultObject = objectValue;
			
			System.out.println("Result Object is "+resultObject.toString());
			
			return resultObject;
		}
		catch(ParseException e)
		{
			throw new DBComponentException(DBExceptionConstants.PARSE_EXCEPTION + objectValue + DBConstants.DB_COMMA + fieldType + "in setObjectValue");
		}
		
		
	}

	private String hasNumeral(Object objectValue , String typeString) throws DBComponentException
	{
		String objVal = null;
		objVal = String.valueOf(objectValue).trim();
		for(int i =0; i<objVal.length() ; i++)
		{
			if((objVal.charAt(i) < DataTypeConstants.TYPE_CHAR_ZERO ||  objVal.charAt(i) > DataTypeConstants.TYPE_CHAR_NINE) &&  objVal.charAt(i)!= DataTypeConstants.TYPE_CHAR_DOT)
			{
				if(typeString.toLowerCase().indexOf(DataTypeConstants.TYPE_INT)>=00)
				{
					if(objVal.charAt(i) != DataTypeConstants.TYPE_CHAR_MINUS)
					{
						throw new DBComponentException(DBExceptionConstants.EXCEPTION + objectValue + typeString + " in hasNumeral" );
					}
					
				}
				else
				{
					throw new DBComponentException(DBExceptionConstants.EXCEPTION + objectValue + typeString + " in hasNumeral" );
				}
				
				//return null;
			}
		}
		
		return objVal;
	}
	/**
     * This method is not in use currently.  
     * @param date  
     * @param hours
     * @returns String
     * @throws DBComponentException
    */
	public String advanceHours(String date,int hours)
	{
		DBDateTime dateTime = null;
		List<Integer> dashCount = new ArrayList<Integer>();
		String calString = null;
		int startCounter = 0;
		if(date.indexOf(DataTypeConstants.TYPE_CHAR_BLANK) > 0 )
		date = date.substring(0,date.indexOf(DataTypeConstants.TYPE_CHAR_BLANK));
		int strLength = date.length();
		for(int lenCounter = 0; lenCounter < strLength ; lenCounter++)
		{
			String extratcedDateTime = null;
			if(date.charAt(lenCounter) == DataTypeConstants.TYPE_CHAR_MINUS || lenCounter == strLength-1)
			{
				//if(lenCounter >)
					if(lenCounter == strLength-1)
						extratcedDateTime = date.substring(startCounter,lenCounter+1);
					else
					{
						extratcedDateTime = date.substring(startCounter,lenCounter);
					}
					dashCount.add(Integer.parseInt(extratcedDateTime.trim()));
					startCounter = lenCounter+1;
				
			}
		}
		if(dashCount.get(2).toString().length() > dashCount.get(0).toString().length())
			dateTime = new DBDateTime(dashCount.get(2),dashCount.get(1),dashCount.get(0),hours,0,0);
		else
			dateTime = new DBDateTime(dashCount.get(0),dashCount.get(1),dashCount.get(2),12,0,0);
		 dateTime.advanceHours(hours);
		 calString = dateTime.createDateString();
				
		return calString;
	}
	
}
