package com.oxymedical.core.querydata;

import java.util.List;

public class QueryObject
{
	private List<QueriedFields> fields;
	private List<QueryCondition> conditions;
	
	/* New field added for database information.
	 * changes by Wasim Khan 16-July-2009.
	 */
	private String dbName;
	
	/**
	 * @return the dbName
	 */
	public String getDbName() {
		return dbName;
	}
	/**
	 * @param dbName the dbName to set
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	/**
	 * @return the fields
	 */
	public List<QueriedFields> getFields() {
		return fields;
	}
	/**
	 * @param fields the fields to set
	 */
	public void setFields(List<QueriedFields> fields) {
		this.fields = fields;
	}
	/**
	 * @return the conditions
	 */
	public List<QueryCondition> getConditions() {
		return conditions;
	}
	/**
	 * @param conditions the conditions to set
	 */
	public void setConditions(List<QueryCondition> conditions) {
		this.conditions = conditions;
	}
	
	
}
