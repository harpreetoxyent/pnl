package com.oxymedical.component.db.XML;

import java.util.Enumeration;
import java.util.Hashtable;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;

import com.oxymedical.component.db.constants.TreeConstants;


/**
 * FieldValueXML class is used to create the field value XML 
 * @author      Oxyent Medical
 * @version     1.0.0
*/
public class FieldValueXML 
{
	/**
     * This method creates field value XML output thorugh hashtable RecordfieldeHash
     * that maps integer(record number) to a hashtable(fieldValueHash).This hashtable(fieldValueHash) maps the field name
     * to the field value i.e. the variable of the Object class.
     * 
     * @param RecordfieldeHash  
     * @returns String
    */
	public String createFieldVlaueXML(Hashtable<Integer,Hashtable> RecordfieldeHash)
	{
		 String xmlOutput = null;
	     Document document = DocumentFactory.getInstance().createDocument();
         Element root = document.addElement(TreeConstants.TREE_RECORDS);
         //for(int listCounter=0;listCounter<ruleLoc.size();listCounter++)
         Enumeration recordFieldEnum = RecordfieldeHash.keys();
         while(recordFieldEnum.hasMoreElements())
         { 
        	 Element fieldVlaueElement = root.addElement(TreeConstants.TREE_RECORD);
        	 Integer recordnumber = (Integer) recordFieldEnum.nextElement();
        	 Hashtable<String,Object> fieldValueHash = RecordfieldeHash.get(recordnumber);
        	 Enumeration<String> fieldValueEnum = fieldValueHash.keys();
        	 while(fieldValueEnum.hasMoreElements())
        	 {
        		 String fieldName =  fieldValueEnum.nextElement();
        		 Object fieldValue = fieldValueHash.get(fieldName);
        		 fieldVlaueElement.addAttribute(fieldName,fieldValue.toString());
        	 }
        }  
       xmlOutput = document.getRootElement().asXML();

		 return xmlOutput;
    }
}

