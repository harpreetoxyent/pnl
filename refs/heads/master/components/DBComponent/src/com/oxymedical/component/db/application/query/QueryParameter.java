package com.oxymedical.component.db.application.query;
/**
 * This class is used to define the structure for the 
 * condition part of the query. 
 * @author      Oxyent Medical
 * @version     1.0.0
*/
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

public class QueryParameter 
{
	/**is used to set the fieldNameLabelHash hashtable which generate while creating the tree.*/
	LinkedHashMap<String,String> fieldNameLabelHash;
	Hashtable conditionHash;
	//Is used to set the join condition
	Hashtable joinHash;
	
	/**is used to set the list of the tableName in it.*/
	List tableName;
    /**is used to set the conditionString of the tree which is for the parent node.*/
	String  condition;
	
	Hashtable orderByHash;
	

	
	public QueryParameter(LinkedHashMap<String, String> fieldNameLabelHash, List tableName, String condition) {
		super();
		this.fieldNameLabelHash = fieldNameLabelHash;
		this.tableName = tableName;
		this.condition = condition;
	}
	/**
	 * Constructor that call the constructor of 
	 * it's superclass i.e. Object class.
	 * 
	*/
	public QueryParameter() {
		super();
	}
	/**
	 * this constructor calls the constructor of it's superclass i.e. Object
	 *  class and takes values into it's class variables.
	 * 
	*/
	public QueryParameter(LinkedHashMap<String, String> fieldNameLabelHash, Hashtable conditionHash, List tableName, String condition) {
		super();
		this.fieldNameLabelHash = fieldNameLabelHash;
		this.conditionHash = conditionHash;
		this.tableName = tableName;
		this.condition = condition;
	}
	
	/**
	 * this constructor calls the constructor of it's superclass i.e. Object
	 *  class and takes values into it's class variables.
	 * 
	*/
	public QueryParameter(LinkedHashMap<String, String> fieldNameLabelHash, Hashtable conditionHash, List tableName, String condition,Hashtable joinHash,Hashtable orderByHash) {
		super();
		this.fieldNameLabelHash = fieldNameLabelHash;
		this.conditionHash = conditionHash;
		this.tableName = tableName;
		this.condition = condition;
		this.joinHash=joinHash;
		this.orderByHash=orderByHash;
	}
	/**
 	* This method is used to get the conditionString of the tree which is for the parent node.
 	* @return String
 	*/
	public String getCondition() {
		return condition;
	}
	/**
 	* This method is used to set the conditionString of the tree which is for the parent node.
 	* @param condition
 	* @return void
 	*/
	public void setCondition(String condition) {
		this.condition = condition;
	}
	/**
 	* This method is used to set the fieldNameLabelHash LinkedHashMap that maps constant 
 	* i.e. displayField or valueField to the objects of its classes.
 	* @return LinkedHashMap<String, String>
 	*/
	public LinkedHashMap<String, String> getFieldNameLabelHash() {
		return fieldNameLabelHash;
	}
	/**
 	* This method is used to set the fieldNameLabelHash LinkedHashMap that maps constant 
 	* i.e. displayField or valueField to the objects of its classes.
 	* @param operator is the condition operator string.
 	* @return void
 	*/
	public void setFieldNameLabelHash(LinkedHashMap<String, String> fieldNameLabelHash) {
		this.fieldNameLabelHash = fieldNameLabelHash;
	}
	/**
 	* This method is used to get the tableName list.
 	* @return List
 	*/
	public List getTableName() {
		return tableName;
	}
	/**
 	* This method is used to set the list of the tableName.
 	* @param tableName
 	* @return void
 	*/
	public void setTableName(List tableName) {
		this.tableName = tableName;
	}
	/**
 	* 
 	* 
 	* @return Hashtable
 	*/
	public Hashtable getConditionHash() {
		return conditionHash;
	}
	/**
 	* .
 	* @param conditionHash
 	* @return void
 	*/
	public void setConditionHash(Hashtable conditionHash) {
		this.conditionHash = conditionHash;
	}
	
	public Hashtable getJoinHash() {
		return joinHash;
	}
	public void setJoinHash(Hashtable joinHash) {
		this.joinHash = joinHash;
	}	
	
	public Hashtable getOrderByHash() {
		return orderByHash;
	}
	public void setOrderByHash(Hashtable orderByHash) {
		this.orderByHash = orderByHash;
	}
}
