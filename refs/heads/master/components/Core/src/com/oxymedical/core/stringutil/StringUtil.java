package com.oxymedical.core.stringutil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StringUtil
{

	public static String[] splitString(String str, String splitBy) 
	{
		if (str == null) return new String[] { null };
		Pattern p = Pattern.compile(splitBy, Pattern.LITERAL);
		String[] stringAfterSplit = p.split(str);
		return stringAfterSplit;
	}
	
	public static String createGetterSetterMethod(String propertyName, boolean isGetterMethod, boolean removeBrackets)
	{
		String methodType = (isGetterMethod == true ? "get" : "set"); 
		if (propertyName.indexOf(methodType)!=0)
		{
			propertyName = changeFirstUpper(propertyName);
			
			if (removeBrackets)
			{
				if (propertyName.indexOf('(') > 0 && propertyName.indexOf(')') > 0) 
				{
					propertyName = propertyName.replace("(", "");
					propertyName = propertyName.replace(")", "");
				}
			}
		}
		
		return methodType + propertyName;
	}
	
	public static String createGetterSetterMethod(String propertyName, boolean isGetterMethod)
	{
		return createGetterSetterMethod(propertyName, isGetterMethod, true);
	}
	
	public static String getFieldFromGetterSetterMethod(String propertyName)
	{
		propertyName = getField(propertyName, "get");
		propertyName = getField(propertyName, "set");
		return propertyName;
	}
	
	public static String getField(String propertyName, String methodType)
	{
		if (propertyName.indexOf(methodType)!=0)
		{
			propertyName = propertyName.substring(methodType.length());
			propertyName = changeFirstLower(propertyName);
			if (propertyName.indexOf('(') > 0 && propertyName.indexOf(')') > 0) 
			{
				propertyName = propertyName.replace("(", "");
				propertyName = propertyName.replace(")", "");
			}
		}
		return propertyName;
	}
	
	public static String changeFirstLower(String st) 
	{
		String subStr = st.substring(0, 1);
		String subStr1 = subStr.toLowerCase();
		String subStr2 = (st.substring(1, st.length()));
		return subStr1+subStr2;
	}

	public static String changeFirstUpper(String st) 
	{
		String subStr = st.substring(0, 1);
		String subStr1 = subStr.toUpperCase();
		String subStr2 = (st.substring(1, st.length()));
		return subStr1+subStr2;
	}
	public static String reformatQueryStatement(String query)
	{
		String retVal = query.replaceAll("_DATESTART_", "[");
		retVal = retVal.replaceAll("_DATEEND_", "]");
		retVal = retVal.replaceAll("_STRINGSTART_", "[");
		retVal = retVal.replaceAll("_STRINGEND_", "]");
		return retVal;
	}
	
	public static String getStringFromList(List<String> data, String separator)
	{
		String retVal = null;
		if (null != data)
		{
			if (data.size() > 0)
			{
				for (int i = 0; i<data.size(); i++)
				{
					if (i ==0) retVal = data.get(i);
					else retVal = retVal + separator + data.get(i);
				}
			}
		}
		return retVal;
	}
	
	public static String getString(Object obj)
	{
		if (obj == null) return null;
		return obj.toString();
	}
	
	public static boolean isStringValueEqual(String val1, String val2, boolean ignoreCase)
	{
		if ((val1 == null) && (val2 == null)) return true;
		else if (((val1 == null) && (val2 != null)) || ((val1 != null) && (val2 == null))) return false;
		else
		{
			if (ignoreCase) return val1.equalsIgnoreCase(val2);
			else return val1.equals(val2);
		}
	}
	
	public static boolean arrayContainsValue(String[] arr, String value, boolean ignoreCase)
	{
		boolean contains = false;
		if ((arr == null) && (value == null)) return true;
		else if ((arr == null) && (value != null)) return false;
		else if ((arr != null) && (value == null)) return false;
		else
		{
			for (int i = 0; i < arr.length; i++)
			{
				if (ignoreCase) contains = arr[i].equalsIgnoreCase(value);
				else contains = arr[i].equals(value);
				
				if (contains) return contains;
			}
		}
		
		return contains;
	}
	
	public static String createGetterSetterMethod(String propertyName, boolean isGetterMethod,Class clazz)
	{
		String methodName=createGetterSetterMethod(propertyName,isGetterMethod, true);
		Method[] classMethods = clazz.getMethods();
		for(int i=0;i<classMethods.length;i++)
		{
			Method method=classMethods[i];
			if(methodName.equalsIgnoreCase(method.getName()))
			{
				methodName=method.getName();
			}
		}
		return methodName;
	}
	
	private String[] smartSplit(String inputStr, String splitter)
	{
		ArrayList<String> localArrayList = new ArrayList<String>();
		String[] splitToIgnoreAfterChars = { "(", "[", "{" };
		String[] splitToContinueAfterChars = { ")", "]", "}" };
		int[] places = {0,0,0};
		String[] normalSplit = inputStr.split(",");
		
		String intermediateStr = "";
		for (int i=0; i<normalSplit.length; i++)
		{
			int specialCharsPresentAt = -1;
			intermediateStr = intermediateStr.equals("") ? normalSplit[i] : intermediateStr + "," + normalSplit[i];
			
			for (int j=0; j<splitToIgnoreAfterChars.length; j++)
			{
				if (intermediateStr.indexOf(splitToIgnoreAfterChars[j]) > 0)
				{
					specialCharsPresentAt = j;
					places[j] = 1;
				}
				if ((specialCharsPresentAt != -1) && (intermediateStr.indexOf(splitToContinueAfterChars[specialCharsPresentAt]) > 0) && (places[j]!=0))
				{
					specialCharsPresentAt = -1;
					places[j] = 0;
				}
			}
			if (specialCharsPresentAt == -1)
			{
				boolean addData = true;
				for (int k=0; k<places.length; k++)
				{
					if (places[k]!=0) addData = false;
				}
				if (addData)
				{
					localArrayList.add(intermediateStr);
					intermediateStr = "";
				}
			}
		}
		
	    java.lang.String[] arrayOfString = new java.lang.String[localArrayList.size()];
	    return ((java.lang.String[])(java.lang.String[])localArrayList.toArray(arrayOfString));

//		return (java.lang.String[])localArrayList.toArray();
	}
	

	
	public static void main(String[] args)
	{
		
	}
}
