package com.oxymedical.component.db.application.query;
/**
 * This class is used to define the set and get the query parameters i.e.
 * the query type select,insert,delete & tables ,conditions etc.
 * @author      Oxyent Medical
 * @version     1.0.0
*/
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.oxymedical.component.db.constants.DBConstants;
import com.oxymedical.core.stringutil.StringUtil;

public class ParserQueryParameters
{
	/**is used to set the list of Field class objects that has the field names in it.*/
	List<Field> fieldList;
	/**is used to set the list of tables that has the table name and the conditions.*/
	List tables;
    /**is used to set the conditions as the hashtable this requires to get conditions in user defined-query.*/
	Hashtable<Integer,Condition> conditions;
	/**is used to get the type of the query that can be select,insert or delete.*/
	String queryType;
	/** Is used to get database name for current query */
	String databaseName = DBConstants.DB_BLANK;
	
	 /**is used to set the conditions as the hashtable this requires to get joins in user defined-query.*/
	Hashtable<Integer,Join> joins;
	
	 /**is used to set the conditions as the hashtable this requires to get orderby  in user defined-query. added by pra on july 27*/
	Hashtable<Integer,OrderBy> orderBy;
	
	
	/**
	 * Constructor that call the constructor of 
	 * it's superclass i.e. Object class.
	 * 
	*/
	public ParserQueryParameters() {
		super();
	}
	/**
	 * this constructor calls the constructor of it's superclass i.e. Object
	 *  class and takes values into it's class variables.
	 * 
	*/
	public ParserQueryParameters(List<Field> fieldList, List tables, Hashtable<Integer, Condition> conditions, String queryType,Hashtable<Integer,Join> joins) {
		super();
		this.fieldList = fieldList;
		this.tables = tables;
		this.conditions = conditions;
		this.queryType = queryType;
		this.joins=joins;
	}
	/**
 	* This method is used to get conditions  in user defined-query.
 	* @param field
  	* @returns void
 	*/
	public Hashtable<Integer, Condition> getConditions() {
		return conditions;
	}
	/**
 	* This method is used to set the conitions takes as the hashtable this
 	* requires to get conditions  in user defined-query.
 	* @param field
  	* @returns void
 	*/
	public void setConditions(Hashtable<Integer, Condition> conditions) {
		this.conditions = conditions;
	}
	/**
 	* This method is used to get the list of Field class objects
 	* that has the field names in it.
 	* @param field
  	* @returns void
 	*/
	public List<Field> getFieldList() {
		return fieldList;
	}
	/**
 	* This method is used to set the list of Field class objects
 	* that has the field names in it.
 	* @param field
  	* @returns void
 	*/
	public void setFieldList(List<Field> fieldList) {
		this.fieldList = fieldList;
	}
	/**
 	* This method is used to set the list of tables that has
 	* the table name and the conditions.
 	* @param field
  	* @returns void
 	*/
	public List getTables() {
		// Since table names may contain database name also, 
		// remove database name and then send.
		return removeDotFromTables(tables);
	}
	
	/**
 	* This method is used to set the list of tables that has
 	* the table name and the conditions.
 	* @param field
  	* @returns void
 	*/
	public void setTables(List tables) {
		this.tables = tables;
	}
	/**
 	* This method is used to get the type of the query that can be
 	* select,insert or delete.
 	* @param field
  	* @returns void
 	*/
	public String getQueryType() {
		return queryType;
	}
	/**
 	* This method is used to set the type of the query that can be
 	* select,insert or delete.
 	* @param field
  	* @returns void
 	*/
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	
	private List removeDotFromTables(List tblList)
	{
		List<String> tableList = new ArrayList<String>();
		String[] tableArr = null;
		String table = null;
		for (int i = 0; i < tblList.size(); i++)
		{
			tableArr = StringUtil.splitString( tblList.get(i).toString(), ".");
			if (tableArr.length > 1) table = tableArr[1]; 
				else table = tableArr[0];
			tableList.add(i, table);
		}
		return tableList;
	}
	
	/**
	 * @return the databaseName
	 */
	public String getDatabaseName()
	{
		// Fetch database name from first table in tables list.
		// Assumption: Query fetches data from one db only and thus database of
		// all tables are same.
		if ((tables != null) && (!tables.isEmpty()) && (tables.get(0) != null))
		{
			String table = tables.get(0).toString();
			if (table.indexOf(DBConstants.DB_DOT) >= 0) 
				databaseName = table.substring(0, table.indexOf(DBConstants.DB_DOT));
		}
		return databaseName;
	}
	
	
	public Hashtable<Integer, Join> getJoins() 
	{
		return joins;
	}
	public void setJoins(Hashtable<Integer, Join> joins)
	{
		this.joins = joins;
	}
	
	
	public Hashtable<Integer, OrderBy> getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(Hashtable<Integer, OrderBy> orderBy) {
		this.orderBy = orderBy;
	}
	
}
