package com.oxymedical.component.db.query.data;

public class Select
{
	public String field;
	public String fieldAlias;
	public String tableAlias;
	
	public Select(String fieldName, String fieldAlias, String tableName)
	{
		this.field = fieldName;
		this.fieldAlias = fieldAlias;
		this.tableAlias = tableName;
	}

	public Select(String fieldName, String tableName)
	{
		this(fieldName, null, tableName);
	}

	public Select(String fieldName)
	{
		this(fieldName, null);
	}
	
	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}
	
	/**
	 * @return the fieldAlias
	 */
	public String getFieldAlias() {
		return fieldAlias;
	}
	/**
	 * @return the tableAlias
	 */
	public String getTableAlias() {
		return tableAlias;
	}
	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @param fieldAlias the fieldAlias to set
	 */
	public void setFieldAlias(String fieldAlias) {
		this.fieldAlias = fieldAlias;
	}

	/**
	 * @param tableAlias the tableAlias to set
	 */
	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
	}
	
}
