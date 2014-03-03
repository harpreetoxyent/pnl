package com.oxymedical.core.xmlutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlReader 
{
	public Document getDocumentAsResource(String filesrc)
	{
		InputStream inStream;
		org.dom4j.Document document = null; 	
		try	
		{	
			inStream = this.getClass().getResourceAsStream(filesrc);
			SAXReader reader = new SAXReader();
			InputStreamReader inR = new InputStreamReader(inStream);
			document = (org.dom4j.Document)reader.read(inR);
		}		
		catch(DocumentException exp)
		{		
			exp.printStackTrace(); 
		}			
		catch(Exception exp)
		{
			
			exp.printStackTrace(); 			
		}		
	    return document;
	}   		
	public Document getDocumentFromPath(String filesrc )
	{
		org.dom4j.Document document = null; 	
		try	
		{	
			System.out.println("entering into getdocuemntfrompath");
			File file = new File(filesrc);
			System.out.println("file======"+file.getAbsolutePath());
			//File file =new File("C:/ArgumentMapping.xml");
			System.out.println(" AFTER THE FILE CREATE entering into getdocuemntfrompath");
			if(file.exists())
			{
				System.out.println("entered file exists 123");
				FileInputStream fin =  new FileInputStream(file);
				System.out.println("entered file exists 2");
				InputStreamReader inR = new InputStreamReader(fin);
				System.out.println("entered file exists 3");
				SAXReader reader = new SAXReader();
				System.out.println("entered file exists 4");
				document = (org.dom4j.Document)reader.read(inR);
				System.out.println("entered file exists 5");
			}
			else
			{
				System.out.println("entered file not exists"); 
				document = null;
			}
		}		
		catch(DocumentException exp)
		{		
			exp.printStackTrace(); 
		}			
		catch(Exception exp)
		{
			
			exp.printStackTrace(); 			
		}		
	    return document;
	}   		
				
	public Element getElementById(Element parent, String nodeId)
	{			
		Element element = null;
		List elementsList = null;
		elementsList = parent.elements();
		for(int count=0;count<elementsList.size();count++)
		{		
			element = (Element) elementsList.get(count);
			if(element.attributeValue("id").toLowerCase().equals(nodeId.toLowerCase()))
				break;
			else
				element = null;
		}		
		return element;
	}			
	
	public Element getElementByAttribute(Element parent, String attrName, String nodeId)
	{			
		Element element = null, retElement = null;
		List elementsList = null;
		elementsList = parent.elements();
		for(int count=0;count<elementsList.size();count++)
		{		
			element = (Element) elementsList.get(count);
			if(element.attributeValue(attrName)!=null && element.attributeValue(attrName)!=""  )
			{
			if(element.attributeValue(attrName).toLowerCase().equals(nodeId.toLowerCase()))
			{
				 retElement = element;
				 return retElement;
			}
			 else
				retElement = null;
			}
			else
				retElement = null;
		}		
		
		if(retElement==null)
		{
			for(int count=0;count<elementsList.size();count++)
			{		
				element = (Element) elementsList.get(count);
				retElement = getElementByAttribute(element, attrName, nodeId);
				if(retElement!=null)
					return retElement;
			}
		}
		
		return retElement;
	}
}	