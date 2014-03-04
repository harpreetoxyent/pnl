package com.oxymedical.component.db.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.util.XMLHelper;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.db.exception.DBComponentException;

/**
 *This class parses the given cfg document.
 * @author      Oxyent Medical
 * @version     1.0.0
*/
public class HibernateConfigMover 
{
	/**
     * This method parses the given cfg document.     
     * @param hbmDirectory  
     * @returns void
     * @throws DBComponentException
    */

	private Document getDocumentFromPath(File filesrc) throws IOException, DocumentException
	{
		org.dom4j.Document document = null; 	
		XMLHelper xmlHelper = new XMLHelper();
		EntityResolver entityResolver = XMLHelper.DEFAULT_DTD_RESOLVER;
		try	
		{	
			SAXReader reader = new SAXReader();
			FileInputStream fileInputStream = new FileInputStream(filesrc);
			List errors = new ArrayList();
			document = xmlHelper.createSAXReader("XML Input Stream", errors, entityResolver).read(new InputSource(fileInputStream));
			//document = (org.dom4j.Document)reader.read(filesrc);
		}		
		catch(DocumentException exp)
		{		
			throw exp;
		}			
	    return document;
	}   			

	private boolean writeToFile(File fl, String filePath, String fileContents)
	{
		FileOutputStream out; // declare a file output object
		PrintStream p; // declare a print stream object
        
		try
		{
			fl.delete();
			//DBComponent.logger.log(0,"filePath:" + filePath);
			out = new FileOutputStream(filePath);
			p = new PrintStream( out );
			p.println (fileContents);
			p.close();
			out.close();
		}
		catch(IOException e)
		{
			//e.printStackTrace();
			//DBComponent.logger.log(0,e.getMessage());
		}
		
		
        return true;
	}
	
	
	private void moveHibernateConfigFile(String basePath, String packageName) throws DBComponentException
	{
		File sourceLocation = new File(basePath);
		File srcFile;
		String filePath = "";
		Document xmlDoc;
		
		try
		{
		    if(sourceLocation.exists())
		    {
		    	if (sourceLocation.isDirectory()) 
		    	{
		           	srcFile = new File(sourceLocation, "hibernate.cfg.xml");
		           	filePath = basePath + "/" + packageName + "/hibernate.cfg.xml";
		           	xmlDoc = this.getDocumentFromPath(srcFile);
		           	String xmlStr = xmlDoc.asXML();
		           	
    				if(srcFile.canWrite())
    					this.writeToFile(srcFile, filePath, xmlStr);
		    	}
		    }
		}
		catch(IOException docExp)
		{
			//DBComponent.logger.log(0,docExp.getMessage());
			throw new DBComponentException(docExp.getMessage());
			//docExp.printStackTrace();
		}
		catch(DocumentException docExp)
		{
			//DBComponent.logger.log(0,docExp.getMessage());
			throw new DBComponentException(docExp.getMessage());
			//docExp.printStackTrace();
		}
	}
	
	public static void main(String args[])
	{
		//String hbmDirectory = "C:\\apache-tomcat-5.5.20\\common\\lib\\ext\\jar\\resources\\de\\stoneone\\component\\testtest"; 

		HibernateConfigMover me = new HibernateConfigMover(); 
		
		String REGEX = ".";
		try
		{
			Pattern p = Pattern.compile(REGEX,  Pattern.LITERAL);
			Matcher m = p.matcher(args[1]);
			String packageName = m.replaceAll("/");
			
			//DBComponent.logger.log(0,"received inptu = " + args[0]);
			//DBComponent.logger.log(0,"args[1]:" + args[1]);
			
			//args[1].replace('.','/' );
			DBComponent.logger.log(0,"BASEPATH =" + args[0]);
			DBComponent.logger.log(0,"Package Name=" + packageName);
			me.moveHibernateConfigFile(args[0], packageName);
			
			//me.parsseMapping("C:\\apache-tomcat-5.5.20\\common\\lib\\ext\\classes\\de\\stoneone\\component\\render");
		}
		catch(Exception e)
		{
			//throw DBComponentExcpetion(e.getMessage());
			e.printStackTrace();
			//DBComponent.logger.log(0,e.getMessage());
		}
		//DBComponent.logger.log(0,"accomplished");
	}

}
