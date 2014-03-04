package com.oxymedical.component.db.application.register;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.db.IDBComponent;
import com.oxymedical.component.db.application.register.pattern.DataPattern;
import com.oxymedical.component.db.application.register.pattern.FormPattern;
import com.oxymedical.component.db.application.register.pattern.SearchPattern;
import com.oxymedical.component.db.constants.DBConstants;
import com.oxymedical.component.db.constants.RegisterConstants;
import com.oxymedical.component.db.exception.DBComponentException;



/**
 * Register class that is responsible for registering all BaseWindows with the 
 * Database Component. 
 * This class implements IRegisterWindow.
 * @author      Oxyent Technologies
 * @version     1.0.0
*/
public class Register implements IRegisterWindow
{
	/**is used to invoke the setters methods of its class and set the values.*/
	private FormPattern frmPattern = null;
	/**is used to invoke the setters methods of its class and set the values.*/
	private DataPattern dataPattern = null;
	/**is used to invoke the setters methods of its class and set the values.*/
	private SearchPattern srchPattern = null;
	/**this hashtable is used map the searc id with the List of SearchPattern class object.*/
	private Hashtable<String,List<SearchPattern>> searchHash = null;
	 /**this hashtable is used map the formPattern id with the FormPattern class object.*/
	private Hashtable<String,FormPattern> formPatternHash = null;
	/**this hashtable map string element name with the hashtable formPatternHash.That hashtable maps
	element id with the object of the class FormPattern.*/
	private Hashtable<String,Hashtable> baseFormPatternHash = null;
	/**this hashtable map string element name with the hashtable dataPatternHash.That hashtable maps
	dataPattern id with the object of the class DataPattern.*/
	private Hashtable<String,Hashtable> baseDataPatternHash = null;
	/**this hashtable maps dataPattern id with the object of the class DataPattern.*/
	//This is changed as periviously it is taking only datapattern object,as implementation is changed now will take linkedlist
	private Hashtable<String,LinkedList<DataPattern>> dataPatternHash = null;
	/**this list contains the objects of the class SearchPattern.*/
	private List<SearchPattern> searchPatternList = null;
	/**this hashtable maps applicationName with the List of applicationPattern.*/
	private Hashtable<String,List<String>> applicationPatternHash = new Hashtable<String,List<String>>();
	/**this is used to persists the appliction pattern name for the applicationPatternHash hashtable.*/
	private List<String> applicationPatternList = null;
	/**this variable is used to call the methods of the class DBComponent*/
	IDBComponent dbComponent;
	
	/**
	 * Constructor for initializing several objects
	 * 
	*/	  	
  public Register()
  {
	  searchHash = new Hashtable<String,List<SearchPattern>>();
	  //applicationPatternHash = 
	  
  }
 
  /**
	 * This method will be called by all base windows to
	 * register with DB component.Receives an XML document as input
	 * and will parse this information using parsePattern() method.
	 * 
	 * @param patternXML This XML document that contains all the elements in form pattern and its mappings.
	 * @param applicationName This is the name  of the registering application.
	 * @throws DBComponentException 
	 * @returns void
	*/
  public void registerBaseWindow(Document patternXML,String applicationName) throws DBComponentException
  {
	  formPatternHash = new Hashtable<String,FormPattern>();
	  dataPatternHash = new Hashtable<String,LinkedList<DataPattern>>();
	 // DBComponent.logger.log(0,"registering started");
	 
	  applicationName = applicationName.trim();
	  Element patternRoot = patternXML.getRootElement();
	 DBComponent.logger.log(0,"this doc information-----"+patternRoot.getName());
	   if(patternRoot.getName().trim().equalsIgnoreCase(RegisterConstants.REG_BASE_WINDOW))
	  {
		  if(null != applicationPatternHash.get(applicationName))
		  {
			  applicationPatternList = applicationPatternHash.get(applicationName);
			  applicationPatternHash.remove(applicationName);
			  applicationPatternList.add(patternRoot.attributeValue(RegisterConstants.REG_NAME_ATTR));
			
			  applicationPatternHash.put(applicationName, applicationPatternList);
		  }
		  else
		  {
			  applicationPatternList = new ArrayList<String>();
			  applicationPatternList.add(patternRoot.attributeValue(RegisterConstants.REG_NAME_ATTR));
			  applicationPatternHash.put(applicationName, applicationPatternList);
			  //DBComponent.logger.log(0,"applicationPatternHash = " + applicationPatternHash);
		  }
		parsePattern(patternRoot,true);
		
		if(null == baseFormPatternHash)
		{
			baseFormPatternHash = new Hashtable();
		}
		if(null == baseDataPatternHash)
		{
			baseDataPatternHash = new Hashtable();
		}
		
		baseFormPatternHash.put(patternRoot.attributeValue(RegisterConstants.REG_NAME_ATTR), formPatternHash);
		baseDataPatternHash.put(patternRoot.attributeValue(RegisterConstants.REG_NAME_ATTR), dataPatternHash);
	 }
	  //DBComponent.logger.log(0,"applicationPatternHash = " + applicationPatternHash);
	 //DBComponent.logger.log(0,"#########Registering completed##################");
	 
  }
  
  /*
   * It parses each element received from registerWindow Method.
   * It will persist the information regarding each elemnt of 
   * base window in memory
   */
  private void parsePattern(Element patternRoot, boolean parseSubElements) throws DBComponentException
  {   	
	  	/*try
	    {*/	    	
	  		Element childElement = null;
			//req
			String elName = null;
			String parentId = null;
			String rootId = null;
			String xmlTagName = null;
			String dataPatternId  = null;
			String fieldNameType = null;
			String fieldName = null;
			String fieldType = null;
			String tableName  = null;
			String databaseName = null;
			String elementToSearch = null;
			String joinOperator = null;
			String conditionOperator = null;
			String mappingValue = null;
			String elementName = null;
			String elementId = null;	
			String searchId = "";
            frmPattern = new FormPattern();            
            if( null == patternRoot.attribute(RegisterConstants.REG_ID_ATTR))
            {	
            	elementId = "";
            	
            }
            else
            {
            	elementId = patternRoot.attributeValue(RegisterConstants.REG_ID_ATTR);
            }
	        if( null == patternRoot.attribute(RegisterConstants.REG_NAME_ATTR))
	        {	
	        	elementName = "";
	        }
	        else
	        {
	        	elementName = patternRoot.attributeValue(RegisterConstants.REG_NAME_ATTR);
	        }
	        
		    if(patternRoot.isRootElement())
		    {
		    	rootId = patternRoot.getName();
		    	frmPattern.setRootWindowId(rootId);
		    	frmPattern.setType(rootId);
		    	frmPattern.setElementId(elementId);
		    	frmPattern.setElementName(elementName.trim());
		    	formPatternHash.put(elementId, frmPattern);
		    }
		    
		    else
		    {
		    	//root elementName
		    	parentId = patternRoot.getParent().getName();
		    	xmlTagName = patternRoot.getName();
		    	if(null != patternRoot.attribute(RegisterConstants.REG_SEARCH_TAG))
	    		{
	    			searchId = patternRoot.attributeValue(RegisterConstants.REG_SEARCH_TAG);
	    		}
		    	
		    	if((null != patternRoot.elements()) && (patternRoot.elements().size()>0))
		    	{

		    	 dataPatternId = parentId + DBConstants.DB_SPACE + xmlTagName + DBConstants.DB_SPACE + elementId;
		    	 List sublelemts= patternRoot.elements();	
		    	 //Implementation changed for saving data in the multiple tables.
		    	 LinkedList<DataPattern> data = new LinkedList<DataPattern>();
		    	 for (int i=0; i<sublelemts.size();i++)
		    	 {
		    		 Element subChild = (Element) sublelemts.get(i);
		    		 if(null!=subChild.elements() && subChild.getName().equalsIgnoreCase(RegisterConstants.REG_ID_FIELDMAP))
		    		 {
		    			List childs = subChild.elements();
		    			for(int j=0;j<childs.size();j++)
		    			{  
		    				Element subChildElement = (Element)childs.get(j);
		    				if(null != subChildElement.attributeValue(RegisterConstants.REG_DATA_PATTERN))
		    		    	{
		    		    		databaseName = subChildElement.attributeValue(RegisterConstants.REG_DATA_PATTERN);
		    		    		fieldNameType = subChildElement.attributeValue(RegisterConstants.REG_DATA_FIELD);
		    		    		if(null != fieldNameType)
		    		    		{
		    		    			int indexOfColon = fieldNameType.indexOf(DBConstants.DB_COLON);
		    		    			fieldName = fieldNameType.substring(0, indexOfColon);
		    		    			fieldType = fieldNameType.substring(indexOfColon+1);
		    		    			fieldType = fieldType.trim();
		    		    		}
		    		    		
		    		    		if(null != subChildElement.attribute(RegisterConstants.REG_DATA_TABLE))
		    		    		{
		    		    			tableName = subChildElement.attributeValue(RegisterConstants.REG_DATA_TABLE);
		    		    		}
		    		    		dataPattern = new DataPattern();
		    		    		dataPattern.setDataPatternId(dataPatternId);
		    		    		dataPattern.setFieldName(fieldName);
		    		    		dataPattern.setType(fieldType);
		    		    		dataPattern.setTableName(tableName.toLowerCase());
		    		    		dataPattern.setDatabaseName(databaseName);
		    		    		data.add(dataPattern);
		    		    		
		    		    	}
		    			}
		    			dataPatternHash.put(dataPatternId, data);
		    		 }
		    	 }
		    	 //code commented as the implementation is changed.
		    	// checks if the elemnt has associate data pattern
//		    	if(null != patternRoot.attributeValue(RegisterConstants.REG_DATA_PATTERN))
//		    	{
//		    		databaseName = patternRoot.attributeValue(RegisterConstants.REG_DATA_PATTERN);
//		    		dataPatternId = parentId + DBConstants.DB_SPACE + xmlTagName + DBConstants.DB_SPACE + elementId;
//		    		fieldNameType = patternRoot.attributeValue(RegisterConstants.REG_DATA_FIELD);
//		    		if(null != fieldNameType)
//		    		{
//		    			int indexOfColon = fieldNameType.indexOf(DBConstants.DB_COLON);
//		    			fieldName = fieldNameType.substring(0, indexOfColon);
//		    			fieldType = fieldNameType.substring(indexOfColon+1);
//		    			fieldType = fieldType.trim();
//		    		}
//		    		
//		    		if(null != patternRoot.attribute(RegisterConstants.REG_DATA_TABLE))
//		    		{
//		    			tableName = patternRoot.attributeValue(RegisterConstants.REG_DATA_TABLE);
//		    		}
//		    		DBComponent.logger.log(0,"dataPatternId---------------"+dataPatternId);
//		    		dataPattern = new DataPattern();
//		    	
//		    		dataPattern.setDataPatternId(dataPatternId);
//		    		dataPattern.setFieldName(fieldName);
//		    		dataPattern.setType(fieldType);
//		    		dataPattern.setTableName(tableName.toLowerCase());
//		    		dataPattern.setDatabaseName(databaseName);
//		    		frmPattern.setAssocDataPatternId(dataPatternId);
//		    		dataPatternHash.put(dataPatternId, dataPattern);
//		    		
//		    	}
  		    	frmPattern.setAssocDataPatternId(dataPatternId);
		    	frmPattern.setElementName(elementName.trim());
		    	frmPattern.setParentWindowId(parentId);
		    	frmPattern.setRootWindowId(rootId);
		    	frmPattern.setType(xmlTagName);
		    	frmPattern.setElementId(elementId);
		    	frmPattern.setSearchId(searchId);
		    	formPatternHash.put(elementId, frmPattern);	
		    	}
		    	
		    	
		    	//for search tag
		    	if(xmlTagName.equalsIgnoreCase(RegisterConstants.REG_SEARCH_TAG))
		    	{
		    		searchPatternList = new ArrayList<SearchPattern>();
		    		List searchElementList = patternRoot.elements();
		    		for(int searchCount = 0; searchCount < searchElementList.size(); searchCount++)
		    		{
		    			Element searchElement = (Element) searchElementList.get(searchCount);
		    			if(null !=  searchElement.attribute(RegisterConstants.REG_NAME_ATTR))
		    			{	
		    				searchId = searchElement.attributeValue(RegisterConstants.REG_NAME_ATTR);
		    				List queryElementList = searchElement.elements(RegisterConstants.REG_QUERY_ATTR);
		    				for(int queryCount = 0; queryCount < queryElementList.size(); queryCount++)
		    				{
		    					srchPattern = new SearchPattern();
		    					Element queryElement = (Element) queryElementList.get(queryCount);
		    					if(null != queryElement.attribute(RegisterConstants.REG_FIELD_NAME_ATTR))
		    					{
		    						elementToSearch =  queryElement.attributeValue(RegisterConstants.REG_FIELD_NAME_ATTR);
		    					}
		    					if(null != queryElement.attribute(RegisterConstants.REG_OPER_ATTR))
		    					{
		    						joinOperator = queryElement.attributeValue(RegisterConstants.REG_OPER_ATTR);
		    					}
		    					if(null != queryElement.attribute(RegisterConstants.REG_COND_ATTR))
		    					{
		    						conditionOperator = queryElement.attributeValue(RegisterConstants.REG_COND_ATTR);
		    					}
		    					if(null != queryElement.attribute(RegisterConstants.REG_FIELD_VALUE_ATTR))
		    					{
		    						mappingValue = queryElement.attributeValue(RegisterConstants.REG_FIELD_VALUE_ATTR);
		    					}
		    					srchPattern.setSearchId(searchId);
		    					srchPattern.setConditionOperator(conditionOperator);
		    					srchPattern.setElementName(elementToSearch);
		    					srchPattern.setJoinOperator(joinOperator);
		    					srchPattern.setMappingValue(mappingValue);
		    					searchPatternList.add(srchPattern);
		    					
		    				}
		    			}
		    		}
		    		searchHash.put(searchId, searchPatternList);
		    	}
			 	
		    }
	    	if(parseSubElements)
			{
	    		
				List lst = patternRoot.elements();
				for(int count=0;count<lst.size();count++)
				{
					childElement = (Element)lst.get(count);
					elName = childElement.getName();
					if(elName.equals("search") || elName.equalsIgnoreCase("query"))
					{
						continue;
					}
					if(!(elName.toLowerCase().equals("event")) && !(elName.equalsIgnoreCase(RegisterConstants.REG_ID_FIELDMAP)))
					{
						parsePattern(childElement, true);
					}
				}
			}
	/*}catch(Exception e)
	{
	 	e.printStackTrace();
	  	throw (new DBComponentException(e.getMessage()));
	}*/
  }
	    
  
  /**
	 * This method is used to get the hashtable applicationPatternHash which 
	 * maps application name to application formpattern list.
	 * 
	 * @returns Hashtable<String, List<String>> which maps application name to application pattern list.
	 */
	public Hashtable<String, List<String>> getApplicationPatternHash() {
		return applicationPatternHash;
	}
	/**
	 * This method is used to set the hashtable applicationPatternHash which 
	 * maps application name to application formpattern list.
	 * 
	 * @param applicationPatternHash
	 * @returns void
	 */
	public void setApplicationPatternHash(
			Hashtable<String, List<String>> applicationPatternHash) {
		this.applicationPatternHash = applicationPatternHash;
	}
	/**
	 * This method is used to get the hashtable baseFormPatternHash which 
	 * maps string to hashtable formPatternHash, this formPatternHash hashtable maps elementid  
	 * to the FormPattern class object.
	 * 
	 * @returns Hashtable<String, Hashtable> 
	 */
	public Hashtable<String, Hashtable> getBaseFormPatternHash() {
		return baseFormPatternHash;
	}
	/**
	 * This method is used to set the hashtable baseFormPatternHash which 
	 * maps string to hashtable formPatternHash, this formPatternHash hashtable maps elementid  
	 * to the FormPattern class object.
	 * 
	 * @param baseFormPatternHash
	 * @returns Hashtable<String, List<String>> 
	 */
	public void setBaseFormPatternHash(
		Hashtable<String, Hashtable> baseFormPatternHash) {
		this.baseFormPatternHash = baseFormPatternHash;
	}
	/**
	* This method is used to get the hashtable dataPatternHash which maps 
	* id of DataPattern to to the object of the DataPattern class.
	* DataPattern class object has all information related to datapattern.
	* 
	* @returns void
	*/
	public Hashtable<String, LinkedList<DataPattern>> getDataPatternHash() {
		return dataPatternHash;
	}
	/**
	* This method is used to set the hashtable dataPatternHash which maps 
	* id of DataPattern to the object of the DataPattern class.
	* DataPattern class object has all information related to datapattern.
	* 
	* @param dataPatternHash
	* @returns void
	*/
	public void setDataPatternHash(Hashtable<String, LinkedList<DataPattern>> dataPatternHash) {
		this.dataPatternHash = dataPatternHash;
	}
	/**
	* This method is used to get the hashtable formPatternHash which maps 
	* id of FormPattern to the object of the FormPattern class.
	* FormPattern class object has all information related to FormPattern.
	* 
	* @returns Hashtable<String, FormPattern>
	*/
	public Hashtable<String, FormPattern> getFormPatternHash() {
		return formPatternHash;
	}
	/**
	* This method is used to set the hashtable formPatternHash which maps 
	* id of FormPattern to the object of the FormPattern class.
	* FormPattern class object has all information related to FormPattern.
	* 
	* @param dataPatternHash
	* @returns void
	*/
	public void setFormPatternHash(Hashtable<String, FormPattern> formPatternHash) {
		this.formPatternHash = formPatternHash;
	}
	/**
	* This method is used to get the hashtable searchHash which maps
	* search id to the search pattern list.This list has the objects of the 
	* class SearchPattern in it.
	* 
	* @returns Hashtable<String, List<SearchPattern>>
	*/
	public Hashtable<String, List<SearchPattern>> getSearchHash() {
		return searchHash;
	}
	/**
	* This method is used to set the hashtable searchHash which maps
	* search id to the search pattern list.This list has the objects of the 
	* class SearchPattern in it.
	* 
	* @param searchHash
	* @returns Hashtable<String, List<SearchPattern>>
	*/
	public void setSearchHash(Hashtable<String, List<SearchPattern>> searchHash) {
		this.searchHash = searchHash;
	}
	/**
	* This method is used to get the applicationPatternList which 
	* has the application pattern name in it.
	*
	* @returns List<String>
	*/
	public List<String> getApplicationPatternList() {
		return applicationPatternList;
	}
	/**
	* This method is used to set the applicationPatternList which 
	* the application pattern name in it.
	* @param applicationPatternList
	* @return void
	*/
	public void setApplicationPatternList(List<String> applicationPatternList) {
	this.applicationPatternList = applicationPatternList;
	}
	/**
	 * This method is used to get the hashtable which maps string element name to hashtable dataPatternHash.The dataPatternHash maps
	 * dataPattern id with the object of the class DataPattern.
	 * 
	 * @returns Hashtable<String, Hashtable>
	 */
	public Hashtable<String, Hashtable> getBaseDataPatternHash() {
	return baseDataPatternHash;
	}

}
