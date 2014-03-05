package com.oxymedical.component.renderer.data;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;


public class HibernateQueryResultDataSource implements JRDataSource { 

	  private String[] fields; 
	  private Iterator iterator; 
	  private Object currentValue; 

	  public HibernateQueryResultDataSource(List list, String[] fields) { 
	    this.fields = fields; 
	    this.iterator = list.iterator(); 
	  } 

	  public Object getFieldValue(JRField field) throws JRException { 
	    Object value = null; 
	    int index = getFieldIndex(field.getName()); 
	    if (index > -1) { 
	      Object[] values = (Object[])currentValue; 
	      value = values[index]; 
	    } 
	    return value; 
	  } 

	  public boolean next() throws JRException { 
	    currentValue = iterator.hasNext() ? iterator.next() : null; 
	    return (currentValue != null); 
	  } 

	  private int getFieldIndex(String field) { 
	    int index = -1; 
	    for (int i = 0; i < fields.length; i++) { 
	      if (fields[i].equals(field)) { 
	        index = i; 
	        break; 
	      } 
	    } 
	    return index; 
	  } 

	public void iterateValue(List list)
	{
		Iterator itr = list.iterator();
		while(itr.hasNext())
		{
			Object obj = itr.next();
		}
	}

public void showValue(List listValue,String[] fields)
{
	
	HibernateQueryResultDataSource ds = new HibernateQueryResultDataSource(listValue,fields);
	Iterator itr = listValue.iterator();
	int counter=0;
	while(itr.hasNext())
	{
		Object obj = itr.next();
		Class clsObject = obj.getClass();
		String nn = obj.getClass().getName();
		Method[] methods = clsObject.getMethods();
		
		String method = changeFirstUpper(fields[counter++]);
		String getterMethod = "get"+method;
		Method methodName;
		
		
		
		try {
			methodName = clsObject.getDeclaredMethod(getterMethod, clsObject);
			Object obs =(Object)methodName.invoke(obj, null);
			System.out.println("obs-------------"+obs.toString());
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}

public static String changeFirstUpper(String st) 
{
	 String subStr = st.substring(0, 1);
	 String subStr1 = subStr.toUpperCase();
	 String subStr2 = (st.substring(1, st.length()));
	 return subStr1+subStr2;
}


}

