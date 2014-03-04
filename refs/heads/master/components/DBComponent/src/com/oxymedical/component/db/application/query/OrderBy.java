package com.oxymedical.component.db.application.query;

/**
 * @author pra
 * class to add orderby in query
 */
public class OrderBy {
	
	String tableName;
	String fieldName;
	String orderType;
	
	
	
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public OrderBy()
	{
		super();
	}
	
	public OrderBy(String tableName,String fieldName)
	{
		this.tableName=tableName;
		this.fieldName=fieldName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}	
	
	
	
