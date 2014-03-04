package com.oxymedical.component.db.constants;
/**
 *These are the constants defined for the tree related classes.
 * @author      Oxyent Medical
 * @version     1.0.0
*/
public class TreeConstants 
{
	/**
	  * Declares the constant value for the TREE_QUERY as tree is used to
	  * checks that whether the query type is for tree.
	 */
	public static final String TREE_QUERY = "tree";
	/**
	  * Declares the constant value for the TREE_TEXT as 'text' is used
	  * while creating the tree xml as attribute under the element treeRecord.
	 */
	public static final String TREE_TEXT = "text";
	/**
	  * Declares the constant value for the TREE_VALUE as 'value' is used
	  * while creating the tree xml as attribute under the element treeRecord.
	 */
	public static final String TREE_VALUE = "value";
	/**
	  * Declares the constant value for the TREE_RECORDS as 'records' is used
	  * as element while creating the tree xml .
	 */
	public static final String TREE_RECORDS = "records";
	/**
	  * Declares the constant value for the TREE_RECORD as 'record' is used
	  * as element while creating the tree xml from the list.
	 */
	public static final String TREE_RECORD = "record";
	/**
	  * Declares the constant value for the TREE_ISLEAF_CHAR as 'L'
	  * is used to make parent string i.e.helpful for generating the xml.
	 */
	public static final String TREE_ISLEAF_CHAR = "L";
	/**
	  * This is the attribute of the element record in the tree xml. 
	 */
	public static final String TREE_ISLEAF = "isleaf";
	/**
	  * While adding attribute Tree_ISLEAF passes the TREE_ISLEAF_TRUE as true.
	 */
	public static final String TREE_ISLEAF_TRUE = "true";
}
