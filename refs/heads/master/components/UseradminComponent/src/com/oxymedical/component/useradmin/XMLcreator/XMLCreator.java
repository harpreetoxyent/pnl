package com.oxymedical.component.useradmin.XMLcreator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import com.oxymedical.component.useradmin.constants.Constants;

/**
 * This class creates and write the XMLs for Role,Rights
 * and UserPattern
 * @author pra
 *
 */


public class XMLCreator {	 
	
	
	
	// Change done all methods.Previously all methods returns the String ,now
	// methods will return the Document.And method is commented which writes the
	// xml.change done by pra on 26-may-2009
	
	/** 
	 * This method returns the path of  Rights XMLFile
	 * @param userPatternId
	 * @param applicationName
	 * @return Document
	 */
	public Document createXmlForRight(String userPatternId,String applicationName,String companyId)
	 {
		 Document xmlDoc  = DocumentHelper.createDocument();
		 Element rights= xmlDoc.addElement(Constants.XML_RIGHTS);
		 Element right=rights.addElement(Constants.XML_RIGHT);
		 right.addAttribute(Constants.XML_RIGHTID, userPatternId);
		 right.addAttribute(Constants.XML_COMPANYID, companyId);
		 return xmlDoc;
	 }
   
/*	
    *//**
     * This method returns the path of  created XMLFile
     * @param document
     * @param documentName
     * @return String
     *//*
    public String writeXml(Document document,String documentName)  
    {
    	String xmlPath="";
    	  File xmlFile=null;
		try {
			xmlFile = File.createTempFile(documentName,Constants.XML_FILE);
			 XMLWriter writer = new XMLWriter(
			            new FileWriter( xmlFile )
			        );
			        writer.write( document );
			        writer.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	       
    	xmlPath=xmlFile.getPath();
    	xmlFile.deleteOnExit();
    	return xmlPath;
    }
    */
    
    /**
     * This method returns the path of Role XMLFile
     * @param roleId
     * @param privileges
     * @param applicationName
     * @return Document
     */
    public Document createXmlForRole(String roleId,List privileges,String applicationName,String companyId)
    {
    
    	Document xmlDoc  = DocumentHelper.createDocument();
		 Element roles= xmlDoc.addElement(Constants.XML_ROLES);
		 Element  role=roles.addElement(Constants.XML_ROLE);
		 role.addAttribute(Constants.XML_ROLEID, roleId);
		 role.addAttribute(Constants.XML_COMPANYID, companyId);
		 Element rights= role.addElement(Constants.XML_RIGHTS);
		 if(privileges!=null && privileges.size()>0)
		 {
			 for(int i=0;i<privileges.size();i++)
			 {
				 Element right=rights.addElement(Constants.XML_RIGHT);
				 right.addAttribute(Constants.XML_RIGHTID, (String)privileges.get(i));
				 right.addAttribute(Constants.XML_COMPANYID,companyId);
			 }
         }
		 //roleXmlPath=this.writeXml(xmlDoc,Constants.XML_ROLE_FILE);
    	return xmlDoc;
    }

    /**
     * This method returns the path of  UserPattern XMLFile
     * @param userPatternId
     * @param privileges
     * @param applicationName
     * @return Document
     */
    public Document createXmlForUserPattern(String userPatternId,List privileges,String applicationName,String defaultFormPattern,String companyId)
    {
    	
    	Document xmlDoc  = DocumentHelper.createDocument();
    	 Element userPatterns= xmlDoc.addElement(Constants.XML_USERPATTERNS);
		 Element userPattern=userPatterns.addElement(Constants.XML_USERPATTERN);
		 userPattern.addAttribute(Constants.XML_USERPATTERNID,userPatternId);
		 userPattern.addAttribute(Constants.XML_COMPANYID, companyId);
		 userPattern.addAttribute(Constants.XML_DEFAULTFORMPATTERN,defaultFormPattern);
		 Element role=userPattern.addElement(Constants.XML_ROLE);
		 role.addAttribute(Constants.XML_ROLEID,userPatternId);
		 role.addAttribute(Constants.XML_COMPANYID, companyId);
		 Element rights=role.addElement(Constants.XML_RIGHTS);	
		 if(privileges!=null && privileges.size()>0)
		 {
			 for(int i=0;i<privileges.size();i++)
			 {
				 Element right=rights.addElement(Constants.XML_RIGHT);
				 right.addAttribute(Constants.XML_RIGHTID,(String)privileges.get(i));
			 }
		 }
	/*	 Element right=rights.addElement(Constants.XML_RIGHT);
		 right.addAttribute(Constants.XML_RIGHTID,userPatternId);*/
		 Element users=userPattern.addElement(Constants.XML_USERS);		
		// userPatternXmlPath=this.writeXml(xmlDoc, Constants.XML_USERPATTERN_FILE);
    	 return xmlDoc;
    }
}
