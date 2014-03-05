package com.oxymedical.component.renderer.data;

import java.util.Hashtable;
import java.util.List;

public class QueryData {
	
	private String dbName = null;
	private String parent = null;
	private String idField = null;
	private String condition = null;
	private Hashtable<String,List<String>> rowValues = new Hashtable<String,List<String>>();
	private Hashtable<String , Hashtable<String,String>> colOrderValues = new Hashtable<String , Hashtable<String,String>>();
	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}
	/**
	 * @param condition the condition to set
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	/**
	 * @return the parent
	 */
	public String getParent() {
		return parent;
	}
	public Hashtable<String, List<String>> getRowValues() {
		return rowValues;
	}
	public void setRowValues(Hashtable<String, List<String>> rowValues) {
		this.rowValues = rowValues;
	}
	public String getIdField() {
		return idField;
	}
	public void setIdField(String idField) {
		this.idField = idField;
	}
	public Hashtable<String, Hashtable<String, String>> getColOrderValues() {
		return colOrderValues;
	}
	public void setColOrderValues(
			Hashtable<String, Hashtable<String, String>> colOrderValues) {
		this.colOrderValues = colOrderValues;
	}
	public String getDBName() {
		return dbName;
	}
	public void setDBName(String dbName) {
		this.dbName = dbName;
	}
	
		
	

}
