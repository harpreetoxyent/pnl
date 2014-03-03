package com.oxymedical.hic.servlet;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.oxymedical.component.baseComponent.IComponentIdConstants;
import com.oxymedical.hic.application.NOLISRuntime;

	/**
	 * @author Oxyent Medical
	 * 
	 * No part of this Source may be copied
	 * without Oxyent Medical’s prior written permission.
	 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
	 */

	public class NOLISStartupUtility 
	{
		public static Object singletonInstance = null;
		public static final String Module_XML_Key = "Module.xml";
		//static final String Hibernate_Key = "orm.cfg.xml";
		//static final String LDAP_Key = "ldapconf.xml";	
		//InputStream moduleStream = null;
		FileInputStream moduleStream = null;
		public static Hashtable<String,Document> ConfigData = new Hashtable<String,Document>();
		public NOLISStartupUtility(){
			
		}
		public NOLISStartupUtility(String appConfFile)
		{
			if(NOLISStartupUtility.singletonInstance == null)
			{
				NOLISStartupUtility.singletonInstance = new Object();
				try  
				{  
					System.out.println("-----Module XML path before replace----"+appConfFile);
					appConfFile = appConfFile.replaceAll("\\\\", "/");
					System.out.println("-----Module XML path after replace----"+appConfFile);

					//Read Module.xml
					moduleStream =
						new FileInputStream(appConfFile);
					if(moduleStream!=null)
					{	
					   SAXReader reader = new SAXReader();	
					   InputStreamReader inR = new InputStreamReader(moduleStream) ; 
					   Document document = reader.read(inR);
					   ConfigData.put(Module_XML_Key, document);
					}
					/*//Read orm.cfg.xml
					moduleStream =
						this.getClass().getResourceAsStream("orm.cfg.xml");
					if(moduleStream!=null)
					{	
					   SAXReader reader = new SAXReader();	
					   InputStreamReader inR = new InputStreamReader(moduleStream) ; 
					   Document document = reader.read(inR);
					   ConfigData.put(Hibernate_Key, document);
					}		*/
					/*moduleStream =
						this.getClass().getResourceAsStream("/com/ethalon/argos/conf/ldapconf.xml");
					if(moduleStream!=null)
					{	
					   SAXReader reader = new SAXReader();	
					   InputStreamReader inR = new InputStreamReader(moduleStream) ; 
					   Document document = reader.read(inR);
					   ConfigData.put(LDAP_Key, document);
					}		
					moduleStream.close();*/
				}  
				catch(Exception exp)
				{
					exp.printStackTrace(); 
				}			
			}
		}
		
		
		
	}


