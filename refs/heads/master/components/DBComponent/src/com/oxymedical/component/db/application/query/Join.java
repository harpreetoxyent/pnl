package com.oxymedical.component.db.application.query;





/**
 * This class is for the implementation of the joins.
 * For the left outer join and right outer join.
 * @author pra
 *
 */
public class Join {

	
	/**
	 * This field is the name of parent table.
	 */
	String parentTableName;
	/**
	 * This field is the name of parent table field name.
	 */
	String fieldName;
	/**
	 * This field is the name of join type.
	 */
	String joinType;
	/**
	 * This field is the name of foreginTable.
	 */
	String foreginTable;
	/**
	 * This field is the name of foreginTable field name.
	 */
	String foreginTableFieldName;
	
	
	

	/**
	 * Constructor that call the constructor of 
	 * it's superclass i.e. Object class.
	 * 
	*/
	public Join() {
		super();
	}
	/**
	 * this constructor calls the constructor of it's superclass i.e. Object
	 *  class and takes values into it's class variables.
	 * 
	*/
	public Join(String parentTableName, String fieldName, String joinType,String foreginTable,String foreginTableFieldName) {
		super();
		this.parentTableName = parentTableName;
		this.fieldName = fieldName;
		this.joinType = joinType;
		this.foreginTable=foreginTable;
		this.foreginTableFieldName=foreginTableFieldName;
	}
	
	public String getParentTableName() {
		return parentTableName;
	}
	public void setParentTableName(String parentTableName) {
		this.parentTableName = parentTableName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getJoinType() {
		return joinType;
	}
	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}
	
	public String getForeginTable() {
		return foreginTable;
	}
	public void setForeginTable(String foreginTable) {
		this.foreginTable = foreginTable;
	}

	public String getForeginTableFieldName() {
		return foreginTableFieldName;
	}
	public void setForeginTableFieldName(String foreginTableFieldName) {
		this.foreginTableFieldName = foreginTableFieldName;
	}
}
