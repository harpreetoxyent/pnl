package com.oxymedical.component.db.application.tree;

/**
 * This class is use to set the information for the
 * TreeResultObject.
 * @author      Oxyent Medical
 * @version     1.0.0
*/
public class TreeResultObject 
{
	/**is used to set the parent node for the tree xml. */
	String parentNode;
	/**this variable has the field name and field value in it. */
	ValueField valueField;
	/**this variable has the field name as displayField and field value i.e. the
	object of the Object class. */
	DisplayField displayField;
	/**is used as level number of the tree for the parent and child node. */
	String ownNode;
	
	/**
	  *This method is used to get the displayField that has the field name
	 * as displayField and field value i.e. the object of the Object class. 
	 * @returns DisplayField
	 */
	public DisplayField getDisplayField() {
		return displayField;
	}
	
	/**
	 *This method is used to set the displayField that has the field name
	 * as displayField and field value i.e. the object of the Object class.
	 * @param displayField
	 * @returns void
	 */
	public void setDisplayField(DisplayField displayField) {
		this.displayField = displayField;
	}
	
	/**
	 **This method is used to get the parent node for the 
	 * tree xml.
	 * @returns String
	 */
	public String getParentNode() {
		return parentNode;
	}
	
	/**
	 *This method is used to set the parent node for the 
	 * tree xml.
	 * @param parentNode
	 * @returns void
	 */
	public void setParentNode(String parentNode) {
		this.parentNode = parentNode;
	}
	
	/**
	 * This method is used to get the valueField which has the
	 * field name and field value in it.
	 * @returns ValueField
	 */
	public ValueField getValueField() {
		return valueField;
	}
	
	/**
	 * This method is used to set the valueField which has the
	 * field name and field value i.e.the object of the Object class.
	 * @param valueField
	 * @returns void
	 */
	public void setValueField(ValueField valueField) {
		this.valueField = valueField;
	}
	
	/**
	 * This method is used to get the level number of the tree 
	 * for the parent and child node. 
	 * @returns String
	 */
	public String getOwnNode() {
		return ownNode;
	}
	
	/**
	 * This method is used to set the level number of the tree 
	 * for the parent and child node.  
	 * @param ownNode
	 * @returns void
	 */
	public void setOwnNode(String ownNode) {
		this.ownNode = ownNode;
	}
	
}
