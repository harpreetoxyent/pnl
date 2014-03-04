package com.oxymedical.component.db.application.query;

/**
 * This class is used to define field values 
 * i.e. pattern fieldname,tableName,and value.
 * @author      Oxyent Medical
 * @version     1.0.0
*/
public class Field 
{
	/** this variable is used to set the pattern field name.*/
	String name;
	/** */
	String tableName;
	/** this variable is used to set the field value in it.*/
	Object value;

	/**
	 * Constructor that call the constructor of 
	 * it's superclass i.e. Object class.
	 * 
	*/
	public Field() {
		super();
	}
	/**
	 * this constructor calls the constructor of it's superclass i.e. Object
	 *  class and takes values into it's class variables.
	 * 
	*/
	public Field(String name, String tableName, Object value) {
		super();
		this.name = name;
		this.tableName = tableName;
		this.value = value;
	}
	/**
 	* This method is used to get the pattern field name.
 	* @returns String
 	*/
	public String getName() {
		return name;
	}
	/**
 	* This method is used to set the pattern field name.
 	* @param name
  	* @returns void
 	*/
	public void setName(String name) {
		this.name = name;
	}
	/**
 	* This method is used to get the field value in it .
 	* @param field
  	* @returns void
 	*/
	public Object getValue() {
		return value;
	}
	/**
 	* This method is used to set the field value in it.
 	* @param field
  	* @returns void
 	*/
	public void setValue(Object value) {
		this.value = value;
	}
	/**
 	* .
 	* @param field
  	* @returns void
 	*/
	public String getTableName() {
		return tableName;
	}
	/**
 	* .
 	* @param field
  	* @returns void
 	*/
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
