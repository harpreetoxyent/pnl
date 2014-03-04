package com.oxymedical.component.db.application.tree;
/**
 * This class is use to set the information for the
 * TreeNode.
 * @author      Oxyent Medical
 * @version     1.0.0
*/
public class TreeNode 
{
	/**is used to set the level number of the tree for the parent and child node.*/ 
	String levelNo;
	/**is used to set the name of the table list.*/
	String tableList;
	/**is used to set the displayfield of the tree  for the parent and child node.*/
	String displayfield;
	/**is used to set the field value in it.*/
	String valueField; // left operand of assignment
	/**is used to set the parent node level. */
	String parentNodeLevel; //parent node
	 /** field with which to be compared denotes parents field*/
	String conditionField;
	/** right side of assignment the constant value*/
	String conditionValue;  
   /**condition string in case of parent*/
	String conditionString;  
	
	
	/**
	 * this calls the constructor of it's superclass i.e. Object
	 *  class and takes values into it's class variables.
	 * 
	*/
	public TreeNode(String levelNo, String tableList, String displayfield, String valueField, String parentNodeLevel, String conditionField, String conditionValue, String conditionString) {
		super();
		this.levelNo = levelNo;
		this.tableList = tableList;
		this.displayfield = displayfield;
		this.valueField = valueField;
		this.parentNodeLevel = parentNodeLevel;
		this.conditionField = conditionField;
		this.conditionValue = conditionValue;
		this.conditionString = conditionString;
	}
	/**
	 * Constructor that call the constructor of 
	 * it's superclass i.e. Object class.
	 * 
	*/
	public TreeNode() {
		super();
	}
	/**
	 * This method is used to get the conditionField of the tree 
	 * which is for the child node.  
	 * @return String
	 */
	public String getConditionField() {
		return conditionField;
	}
	/**
	 *This method is used to set the conditionField of the tree 
	 * which is for the child node.  
	 * @param conditionField
	 * @return void
	 */
	public void setConditionField(String conditionField) {
		this.conditionField = conditionField;
	}
	/**
	 * This method is used to get the conditionString of the tree 
	 * which is for the parent node.
	 * @return String
	 */
	public String getConditionString() {
		return conditionString;
	}
	
	/**
	 * This method is used to set the conditionString of the tree 
	 * which is for the parent node.
	 * @param conditionString
	 * @return void
	 */
	public void setConditionString(String conditionString) {
		this.conditionString = conditionString;
	}
	
	/**
	 * This method is used to get the conditionValue of the tree 
	 * which is for the child node.  
	 * @return String
	 */
	public String getConditionValue() {
		return conditionValue;
	}
	
	/**
	 * This method is used to set the conditionValue of the tree 
	 * which is for the child node only. 
	 * @param conditionValue
	 * @return void
	 */
	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}
		
	/**
	 * This method is used to get the displayfield of the tree 
	 * for the parent and child node.
	 * @return String
	 */
	public String getDisplayfield() {
		return displayfield;
	}
	
	/**
	 * This method is used to set the displayfield of the tree 
	 * for the parent and child node. 
	 * @param displayfield
	 * @returns void
	 */
	public void setDisplayfield(String displayfield) {
		this.displayfield = displayfield;
	}
	
	/**
	 * This method is used to get the level number of the tree 
	 * for the parent and child node. 
	 * 
	 * @return String
	 */
	public String getLevelNo() {
		return levelNo;
	}
	
	/**
	 * This method is used to set the level number of the tree 
	 * for the parent and child node. 
	 * @param levelNo
	 * @return void
	 */
	public void setLevelNo(String levelNo) {
		this.levelNo = levelNo;
	}
	
	/**
	 * This method is used to get the parent node level.   
	 * @return String
	 */
	public String getParentNodeLevel() {
		return parentNodeLevel;
	}
	
	/**	 
	 * This method is used to set the parent node level.  
	 * @param parentNodeLevel
	 * @return void
	 */
	public void setParentNodeLevel(String parentNodeLevel) {
		this.parentNodeLevel = parentNodeLevel;
	}
	
	/**
	 * This method is used to get the field value.  
	 * @return String
	 */
	public String getValueField() {
		return valueField;
	}
	
	/**
	 * This method is used to set the field value.  
	 * @param valueField
	 * @return void
	 */
	public void setValueField(String valueField) {
		this.valueField = valueField;
	}
	
	/**
	 * This method is used to get the tableList. 
	 * @return String
	 */
	public String getTableList() {
		return tableList;
	}
	
	/**
	 * This method is used to set the table list in it.
	 * @param tableList
	 * @return void
	 */
	public void setTableList(String tableList) {
		this.tableList = tableList;
	}
	
	
	
	
}
