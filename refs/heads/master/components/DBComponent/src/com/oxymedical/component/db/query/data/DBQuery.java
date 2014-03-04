package com.oxymedical.component.db.query.data;

import java.util.ArrayList;
import java.util.List;

public class DBQuery
{
	private List<Select> fields = null;
	private List<From> tables = null;
	private List<Where> conditions = null;
	/**
	 * @return the fields
	 */
	public List<Select> getFields() {
		return fields;
	}
	/**
	 * @param fields the fields to set
	 */
	public void setFields(List<Select> fields) {
		this.fields = fields;
	}
	/**
	 * @return the tables
	 */
	public List<From> getTables() {
		return tables;
	}
	/**
	 * @param tables the tables to set
	 */
	public void setTables(List<From> tables) {
		this.tables = tables;
	}
	/**
	 * @return the conditions
	 */
	public List<Where> getConditions() {
		return conditions;
	}
	/**
	 * @param conditions the conditions to set
	 */
	public void setConditions(List<Where> conditions) {
		this.conditions = conditions;
	}
	
	
}
