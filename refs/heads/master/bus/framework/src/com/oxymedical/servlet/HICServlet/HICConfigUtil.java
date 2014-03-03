package com.oxymedical.servlet.HICServlet;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class HICConfigUtil
{
	
	private Document xmlDocument;
	String src = null;
	String addUser = null;
	String addOrg = null;
	
	public HICConfigUtil()
	{
			
	}
	public String retrieveSource(String URL)
	{
		String value = null;
		String checkURL = URL;
		try
		{
			
			if(xmlDocument == null)
			{
				xmlDocument = parseUsingSAX();
			}
		  if(checkSource(checkURL))
		  {
			   value =	generateSourceURL(xmlDocument);
		  }
		  else
		  {
			  value =	generateAddUserURL(xmlDocument);
		  }
		  
		  return value;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public Document parseUsingSAX() throws DocumentException
	{
		SAXReader reader = new SAXReader();
		
		InputStream	modulefileStream =this.getClass().getResourceAsStream("/hic/conf/URLConfig.xml");
		 modulefileStream =this.getClass().getResourceAsStream("/de/stoneone/portal/hic/conf/URLConfig.xml");
		
		if(modulefileStream!=null)
		{
			Document document = reader.read(modulefileStream);
			return document;
		}
		return null;
	}
	public String generateSourceURL(Document document) throws DocumentException
	{
		try
		{
			List elementList = document.selectNodes("//startup-url");
			Iterator elementIterator  = elementList.iterator();
             while(elementIterator.hasNext())
             {
            	
            	 Element element = (Element)elementIterator.next();
            	 Iterator ElementIterator = (Iterator)element.elementIterator();
            	 while(ElementIterator.hasNext())
            	 {
            		Element elementSecond = (Element)ElementIterator.next();
            		           		
            		if((elementSecond.getName())=="URL")
            		 {
            			Iterator attIterator = (Iterator)elementSecond.attributeIterator();
            			while(attIterator.hasNext())
            			{
            				Attribute attribute = (Attribute)attIterator.next();
            				if((attribute.getName())== "value")
            				{
            					src = attribute.getText();
            					src = src.trim();
            				}
            			}
               		 }
            		
            	 }
            	 return src;
            	 
             }
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
             
		 return null;
	}
	public String generateAddUserURL(Document document) throws DocumentException
	{
		try
		{
			List elementList = document.selectNodes("//adduser-url");
			Iterator elementIterator  = elementList.iterator();
             while(elementIterator.hasNext())
             {
            	
            	 Element element = (Element)elementIterator.next();
            	 Iterator ElementIterator = (Iterator)element.elementIterator();
            	 while(ElementIterator.hasNext())
            	 {
            		Element elementSecond = (Element)ElementIterator.next();
            		           		
            		if((elementSecond.getName())=="URL")
            		 {
            			Iterator attIterator = (Iterator)elementSecond.attributeIterator();
            			while(attIterator.hasNext())
            			{
            				Attribute attribute = (Attribute)attIterator.next();
            				if((attribute.getName())== "value")
            				{
            					addUser = attribute.getText();
            					addUser = addUser.trim();
            				}
            			}
               		 }
            		
            	 }
            	 return addUser;
            	 
             }
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
             
		
		 return null;
	}
	public String generateAddOrgURL(Document document) throws DocumentException
	{
		try
		{
			List elementList = document.selectNodes("//addorg-url");
			Iterator elementIterator  = elementList.iterator();
             while(elementIterator.hasNext())
             {
            	
            	 Element element = (Element)elementIterator.next();
            	 Iterator ElementIterator = (Iterator)element.elementIterator();
            	 while(ElementIterator.hasNext())
            	 {
            		Element elementSecond = (Element)ElementIterator.next();
            		           		
            		if((elementSecond.getName())=="URL")
            		 {
            			Iterator attIterator = (Iterator)elementSecond.attributeIterator();
            			while(attIterator.hasNext())
            			{
            				Attribute attribute = (Attribute)attIterator.next();
            				if((attribute.getName())== "value")
            				{
            					addOrg = attribute.getText();
            					addOrg = addOrg.trim();
            				}
            			}
               		 }
            		
            	 }
            	 return addOrg;
            	 
             }
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
             
		
		 return null;
	}
	public boolean checkSource(String url)
	{
		if(url == (URLConstants.sourceURL))
		{
			return true;
		}
		else if(url ==URLConstants.addUserURL)
		{
			return false;
		}
		return false;
	}
	public static void main(String args[])
	{
		HICConfigUtil hicConfig = new HICConfigUtil();
		String result = hicConfig.retrieveSource("addUserURL");
		
	}
	}
