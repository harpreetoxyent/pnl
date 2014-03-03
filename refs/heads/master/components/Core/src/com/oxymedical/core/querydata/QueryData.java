package com.oxymedical.core.querydata;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class QueryData implements Serializable
{
	private String dbName = null;
	private String parent = null;
	private String idField = null;
	private String condition = null;
	private Hashtable<String, List<String>> rowValues = new Hashtable<String, List<String>>();
	//private Hashtable<String, Hashtable<String, String>> colOrderValues = new Hashtable<String, Hashtable<String, String>>();
	private LinkedHashMap<String, LinkedHashMap<String, String>> colOrderValues = new LinkedHashMap<String, LinkedHashMap<String, String>>();
	private List<Object> listData = null;
	private QueryObject queryObj = null;

	/**
	 * @return the listData
	 */
	public List<Object> getListData() {
		return listData;
	}

	/**
	 * @param listData the listData to set
	 */
	public void setListData(List<Object> listData) {
		this.listData = listData;
	}

	/**
	 * @return the condition
	 */
	public String getCondition()
	{
		return condition;
	}

	/**
	 * @param condition
	 *            the condition to set
	 */
	public void setCondition(String condition)
	{
		this.condition = condition;
	}

	/**
	 * @return the parent
	 */
	public String getParent()
	{
		return parent;
	}

	public Hashtable<String, List<String>> getRowValues()
	{
		return rowValues;
	}

	public void setRowValues(Hashtable<String, List<String>> rowValues)
	{
		this.rowValues = rowValues;
	}

	public String getIdField()
	{
		return idField;
	}

	public void setIdField(String idField)
	{
		this.idField = idField;
	}

	public LinkedHashMap<String, LinkedHashMap<String, String>> getColOrderValues()
	{
		return colOrderValues;
	}

	public void setColOrderValues(
			LinkedHashMap<String, LinkedHashMap<String, String>> colOrderValues)
	{
		this.colOrderValues = colOrderValues;
	}
	
	public String getDBName() {
		return dbName;
	}
	public void setDBName(String dbName) {
		this.dbName = dbName;
	}
	
	// iterate list data in double Array
	
	
	
	
	public String[][] getListDataInArray()
	{
		return QueryData.iterateListData(this.listData);
	}
	 
	/**
	 * @param listValue
	 * @return
	 */
	public static String[][] iterateListData(List<Object> listValue)
	{
		if(listValue==null)
		{
			return null;
		}
		if(listValue.size()<=0)
		{
			return null;
		}
		Iterator<Object> itr = listValue.iterator();
		int counter=0;
		int objectCounter =0;
		Object retValue = null;
		String[][] allValues = new String[listValue.size()][];
		while(itr.hasNext())
		{
			Object obj = itr.next();
			if(obj!= null && !obj.getClass().isArray())
			{
				allValues[counter]= new String[1];
				if(obj!=null)
			    allValues[counter][0] = obj.toString();
			}
			else
			{
		    	Object[] objArr = (Object[])obj;
				if(null == obj)
				{
					continue;
				}
				allValues[counter]= new String[objArr.length];
				for(objectCounter = 0;objectCounter<objArr.length; objectCounter++)
				{
					retValue = objArr[objectCounter];
					if(retValue !=null)
						allValues[counter][objectCounter] = retValue.toString();
				}	
			}
			counter++;
		}
		return allValues;
	}
	
	/**
	 * @return
	 */
	public QueryObject getQueryObject() 
	{
		return this.queryObj;
	}

	/**
	 * @param QueryObject the QueryObject to set
	 */
	public void setQueryObject(QueryObject queryObject) 
	{
		this.queryObj = queryObject;
	}
	
}
