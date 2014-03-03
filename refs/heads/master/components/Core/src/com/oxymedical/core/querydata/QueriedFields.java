package com.oxymedical.core.querydata;

public class QueriedFields
{
	private String field;
	/*
	 * this field added to store tablename. field would be related to this table
	 * changes by wasim 26-06-2009
	 */
	
	private String tableName;
	
	

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}
}
