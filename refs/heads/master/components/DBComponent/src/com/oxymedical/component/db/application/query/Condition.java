package com.oxymedical.component.db.application.query;
/**
 * This class is used to define the structure for the 
 * condition part of the query. 
 * @author      Oxyent Medical
 * @version     1.0.0
*/
public class Condition
{
	/**this variable is used to set the field retrieved from the sql query*/
	String field;
	/**this variableis used to set the condition operator string.*/
	String operator;
	/**is used to set the table name.This table name is retrieved from query.*/
	String table;
	/**is used to set the join.The join can be 'and' or 'or'.*/
	String join;
	/**is used to set the variable of the class Object which is the field value like numeric,blank etc.*/
	Object value;
	/** */
	String consTable;
	/** */
	String consField;
	
	/**
	 * Constructor that call the constructor of 
	 * it's superclass i.e. Object class.
	 * 
	*/
	public Condition() {
		super();
	}
	/**
	 * this constructor calls the constructor of it's superclass i.e. Object
	 *  class and takes values into it's class variables.
	 * 
	*/
	public Condition(String field, String operator, String table, String join, Object value, String consTable, String consField) {
		super();
		this.field = field;
		this.operator = operator;
		this.table = table;
		this.join = join;
		this.value = value;
		this.consTable = consTable;
		this.consField = consField;
	}
	
	/**
 	* This method is used to get the field.
  	* @returns String
 	*/
	public String getField() 
	{
		return field;
	}
	/**
 	* This method is used to set the field.
 	* @param field
  	* @returns void
 	*/
	public void setField(String field) 
	{
		this.field = field;
	}
	/**
 	* This method is used to get the condition operator string.
 	* @returns String
 	*/
	public String getOperator() 
	{
		return operator;
	}
	/**
 	* This method is used to set the condition operator string.
 	* @param operator is the condition operator string.
 	* @return void
 	*/
	public void setOperator(String operator)
	{
		this.operator = operator;
	}
	/**
 	* This method is used to get the variable
 	* of the class Object i.e. value which is the field value like numeric,blank etc.
 	* @returns Object
 	*/
	public Object getValue() 
	{
		return value;
	}
	/**
 	* This method is used to set the variable of
 	* the class Object i.e. value which is the field value like numeric,blank etc.
 	* @param value
 	* @returns Object
 	*/
	public void setValue(Object value)
	{
		this.value = value;
	}
	/**
 	* This method is used to get the join.
 	* The join can be 'and' or 'or'.
 	* @return void
 	*/
	public String getJoin() {
		return join;
	}
	/**
 	* This method is used to set the join.
 	* The join can be 'and' or 'or'.
 	* @param join
 	* @return void
 	*/
	public void setJoin(String join) {
		this.join = join;
	}
	/**
 	* This method is used to get the table name.
 	* This table name is retrieved from query and then set into setTable method.
 	* @param applicationPatternList
 	* @return void
 	*/
	public String getTable() {
		return table;
	}
	/**
 	* This method is used to set the table name.
 	* This table name is retrieved from query.
 	* @param applicationPatternList
 	* @return void
 	*/
	public void setTable(String table) {
		this.table = table;
	}
	/**
 	* This method is used to set the applicationPatternList which 
 	* the application pattern name in it.
 	* @param applicationPatternList
 	* @return void
 	*/
	public String getConsField() {
		return consField;
	}
	/**
 	* This method is used to set the applicationPatternList which 
 	* the application pattern name in it.
 	* @param applicationPatternList
 	* @return void
 	*/
	public void setConsField(String consField) {
		this.consField = consField;
	}
	/**
 	* This method is used to set the applicationPatternList which 
 	* the application pattern name in it.
 	* @param applicationPatternList
 	* @return void
 	*/
	public String getConsTable() {
		return consTable;
	}
	/**
 	* This method is used to set the applicationPatternList which 
 	* the application pattern name in it.
 	* @param applicationPatternList
 	* @return void
 	*/
	public void setConsTable(String consTable) {
		this.consTable = consTable;
	}
	
}
