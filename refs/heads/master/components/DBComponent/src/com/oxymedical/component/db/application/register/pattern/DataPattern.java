package com.oxymedical.component.db.application.register.pattern;
/**
 * This class is use to set the information for the
 * dataPattern.
 * @author      Oxyent Medical
 * @version     1.0.0
*/
public class DataPattern
{
	/**is used to persist the id of the datapattern in it.*/
	String dataPatternId;
	/**is used to set the element name for the datapattern in it.*/
	String elementName;
	/**is used to set the field name for the datapattern.*/
	String fieldName;
	/**is used to set the table name.*/
	String tableName;
	/**is used to set the database name.*/
	String databaseName;
	/**is used to set the parameter type that is the field type.*/
	String type;
	
	/**
	 * Constructor that call the constructor of 
	 * it's superclass i.e. Object class.
	 * 
	*/
	public DataPattern()
	{
		super();
	}
	/**
	 * this calls the constructor of it's superclass i.e. Object
	 *  class and takes values into it's class variables.
	 * 
	*/
	public DataPattern(String dataPatternId, String elementName, String fieldName, String tableName, String databaseName, String type) 
	{
		super();
		this.dataPatternId = dataPatternId;
		this.elementName = elementName;
		this.fieldName = fieldName;
		this.tableName = tableName;
		this.databaseName = databaseName;
		this.type = type;
	}
	/**
	 *This method is used to get the database name.
	 *@returns String
	 */
	public String getDatabaseName() 
	{
		return databaseName;
	}
	/**
	 *This method is used to set the database name.
	 * @param databaseName
	 * @returns void
	 */
	public void setDatabaseName(String databaseName) 
	{
		this.databaseName = databaseName;
	}
	/**
	 * This method is used to get the pattern id
	 * i.e. data pattern id when the elemnt associated to data pattern.
	 * @returns String
	 */
	public String getDataPatternId() 
	{
		return dataPatternId;
	}
	/**
	 * This method is used to set the pattern id
	 * i.e. data pattern id when the elemnt associated to data pattern.
	 * @param dataPatternId
	 * @returns void
	 */
	public void setDataPatternId(String dataPatternId) 
	{
		this.dataPatternId = dataPatternId;
	}
	/**
	 *This method is used to get the table name.
	 * @returns String
	 */
	public String getTableName() 
	{
		return tableName;
	}
	/**
	 *This method is used to set the table name.
	 * @param tableName
	 * @returns String
	 */
	public void setTableName(String tableName) 
	{
		this.tableName = tableName;
	}
	/**
	 * This method is used to get the parameter type
	 * that is the field type.
	 * @returns String
	 */
	public String getType() 
	{
		return type;
	}
	/**
	 *This method is used to set the parameter type
	 *that is the field type.
	 * @param type
	 * @returns void
	 */
	public void setType(String type) 
	{
		this.type = type;
	}
	/**
	 *This method is used to get the field name 
	 * for the datapattern.
	 * @returns String
	 */
	public String getFieldName() {
		return fieldName;
	}
	/**
	 *This method is used to set the field name 
	 * for the datapattern.
	 * @param fieldName
	 * @returns void
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	/**
	 *This method is used to get the element name 
	 * for the datapattern.
	 * @returns void
	 */
	public String getElementName() {
		return elementName;
	}
	/**
	 *This method is used to set the element name 
	 * for the datapattern.
	 * @param elementName
	 * @returns void
	 */
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}
	
}
