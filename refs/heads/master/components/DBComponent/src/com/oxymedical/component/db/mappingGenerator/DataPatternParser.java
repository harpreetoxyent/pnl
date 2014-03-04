/**
 * 
 */
package com.oxymedical.component.db.mappingGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.oxymedical.component.db.DBComponent;
import com.oxymedical.core.commonData.Application;
import com.oxymedical.core.commonData.IApplication;


/**
 * @author vka
 *
 */
public class DataPatternParser 
{
	private static List<MappingTable> listObject = null;
	
	static public void parseDataPattern(IApplication app,String dataPatternName)
	{
		String applicationPath = app.getApplicationFolderPath();
		String dataFilePath = applicationPath+"data/"+dataPatternName+ ".xml";
		DBComponent.logger.log(0,"-----------datafile name-------"+dataFilePath);
		listObject = readDataPattern(dataFilePath);
	}
	
	static List<MappingTable> getMappingInfo()
	{
		return listObject;
	}
	
	private static List<MappingTable> readDataPattern(String srcFile)
	{
		List<MappingTable> tableListObject = new ArrayList<MappingTable>();
		File file = new File(srcFile);
		
		Document document;
		SAXReader reader = new SAXReader();

		try
		{
			document = reader.read(file);
		} 
		catch (DocumentException e)
		{
			e.printStackTrace();
			document = null;
		}
		List jointCollection= new ArrayList();
		Hashtable<String,String> jointTable = new Hashtable<String,String>();
		Element rootElement = document.getRootElement();
		Element schemaElement = rootElement.element("schema");
		Element elementElement = schemaElement.element("element");
		Element complexTypeElement = elementElement.element("complexType");
		Element choiceElement = complexTypeElement.element("choice");
		List<Element> tableElement = choiceElement.elements("element");
		Iterator<Element> itr = tableElement.iterator();
		for(Element element;itr.hasNext();)
		{
			element = (Element) itr.next();
			String tableName= element.attributeValue("name");
			String manyTo= element.attributeValue("M2M");
			if(manyTo !=null)
			{
				if(manyTo.equalsIgnoreCase("yes"))
				{
					jointCollection.add(tableName);
				}
			}
		}
		
		/* unique name */
		
		List<Element> uniqueElement  = elementElement.elements("unique");
		
		
		for (Iterator<Element> eleIter = uniqueElement.iterator(); eleIter.hasNext(); )
		{
			Element keyUniqueElem = eleIter.next();
			String constraintName = keyUniqueElem.attributeValue("name");
			Element child = keyUniqueElem.element("selector");
			String tableName =child.attributeValue("xpath");
			String[] tabArray = tableName.trim().split(".//");
			if(tabArray.length>1)
			{
				tableName = tabArray[1];
			}
			jointTable.put(constraintName, tableName);
		}
		
		
		
		/* KEYREF */
		List<Element> keyRefElements = elementElement.elements("keyref");
		int counter=0;
		MappingTable jointTableObj = new MappingTable();
		for (Iterator<Element> eleIter = keyRefElements.iterator(); eleIter.hasNext(); )
		{
			String m2mTableName=null;
			Element keyRefElem = eleIter.next();
			String referName = keyRefElem.attributeValue("refer");
			Element child = keyRefElem.element("selector");
			String tableName =child.attributeValue("xpath");
			String[] tabArray = tableName.trim().split(".//");
			if(tabArray.length>1)
			{
				tableName = tabArray[1];
			}
			boolean hasTable = jointCollection.contains(tableName);			
			if(hasTable && counter<2)
			{
				
				jointTableObj.setJointTable(tableName);
				m2mTableName= (String) jointTable.get(referName);
				if(counter==0)
				{
					jointTableObj.setFirstTable(m2mTableName);
				}
				else
				{
					jointTableObj.setSecondTable(m2mTableName);
				}
				counter++;
			}
			if(counter==2)
			{
				counter=0;
				tableListObject.add(jointTableObj);
				jointTableObj = new MappingTable();
			}
			
		}
		
		return tableListObject;
	}
}
