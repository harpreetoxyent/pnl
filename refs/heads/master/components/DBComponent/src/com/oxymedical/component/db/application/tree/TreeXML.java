package com.oxymedical.component.db.application.tree;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.oxymedical.component.db.constants.DBConstants;
import com.oxymedical.component.db.constants.TreeConstants;
import com.oxymedical.component.db.exception.DBComponentException;

/**
 * This class is used to create the Tree xml from
 * hashtable for the list of the objects of the class TreeResultObject.
 * @author      Oxyent Medical
 * @version     1.0.0
*/
public class TreeXML 
{
	/**
	 * This method is used to create XML from the hashtable
	 * which maps level of the tree to the list of objects of 
	 * TreeResultObject class.
	 * 
	 * @param treeHash
	 * @returns String
	 * @throws DBComponentException
	 */
    public String createXML(Hashtable<String,List<TreeResultObject>> treeHash) throws DBComponentException
    {
		String xmlOutput = null;
		Element root = null;
		
		Document document = DocumentHelper.createDocument();
		root = document.addElement( TreeConstants.TREE_RECORDS );
		Vector treeVector = new Vector(treeHash.keySet());
		Collections.sort(treeVector);
		Iterator treeIterator  = treeVector.iterator();
		Element xmlNodeElement = null;
		xmlNodeElement = root;
		while (treeIterator.hasNext())
		{
			String element =  (String)treeIterator.next();
			generateXMLFromList(root, element, treeHash,DBConstants.DB_BLANK);
			break;
		}
    	
    	xmlOutput = root.asXML();
    	return xmlOutput; 
    }
    
	private void generateXMLFromList(Element root, String element, Hashtable<String,List<TreeResultObject>> treeHash, String parent) 
	{
		List treeList = (List)treeHash.get(element);   		      
		int level = Integer.parseInt(element);
		
		Element xmlNodeElement;
	    Iterator listIterator = treeList.iterator();
	    while(listIterator.hasNext())
	    {
		   int nextLevel = level + 1;
	       TreeResultObject treeResult = (TreeResultObject)listIterator.next();	       
	       if(treeResult.getParentNode().equalsIgnoreCase(parent))
	       {
			   xmlNodeElement = root.addElement(TreeConstants.TREE_RECORD);
			   xmlNodeElement.addAttribute(TreeConstants.TREE_TEXT , (String)treeResult.getDisplayField().getText());
			   xmlNodeElement.addAttribute(TreeConstants.TREE_VALUE , treeResult.getValueField().getText().toString());
			   if(nextLevel < treeHash.size())
			   {
				   String parentStr =  treeResult.getValueField().getText().toString() + TreeConstants.TREE_ISLEAF_CHAR +String.valueOf(level);
				   generateXMLFromList( xmlNodeElement, String.valueOf(nextLevel), treeHash, parentStr);
			   }
			   else
			   {
			    	xmlNodeElement.addAttribute(TreeConstants.TREE_ISLEAF , TreeConstants.TREE_ISLEAF_TRUE);
			   }
	       }
		   nextLevel = level;
	    }

	 }
   
}
