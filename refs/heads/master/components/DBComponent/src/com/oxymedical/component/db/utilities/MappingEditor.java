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
public class MappingEditor 
{
	/**
     * This method parses the given cfg document.     
     * @param hbmDirectory  
     * @returns void
     * @throws DBComponentException
    */
    public void parsseMapping(String hbmDirectory) throws DBComponentException
	{
		File sourceLocation = new File(hbmDirectory);
		File srcFile;
		String filePath = "";
		Document xmlDoc;
		
		try
		{
		    if(sourceLocation.exists())
		    {
		    	if (sourceLocation.isDirectory()) 
		    	{
		    		//Copy the dtd into this location
		    		//Get Resource as Stream and paste into this folder
		    		/*InputStream inptStr = this.getClass().getResourceAsStream("/de/stoneone/component/db/mappingGenerator/hibernateMySQL.dtd");
		    		//DBComponent.logger.log(0,"input stream = " +inptStr);
		    		//InputStream in = new FileInputStream(sourceLocation);
		    		File file = new File(sourceLocation,"hibernateMySQL.dtd");
		            OutputStream out = new FileOutputStream(file);		            
		            // Copy the bits from instream to outstream
		            byte[] buf = new byte[1024];
		            int len;
		            while ((len = inptStr.read(buf)) > 0) 
		            {
		                out.write(buf, 0, len);
		            }
		            inptStr.close();
		            out.close();
		    */
		            String[] children = sourceLocation.list();
		    		for (int i=0; i<children.length; i++) 
			        {
			           	srcFile = new File(sourceLocation, children[i]);
			           //	DBComponent.logger.log(0,"srcFile = " + srcFile);
			           	//if(srcFile.)
			           	if(srcFile.getName().indexOf(".hbm.xml")>0)
			           	{
			           		filePath = srcFile.getAbsolutePath();
			           		//DBComponent.logger.log(0,"filepath = " + filePath);
			           		
			           		xmlDoc = this.getDocumentFromPath(srcFile);
			           		//DBComponent.logger.log(0,"xmldoc = " + xmlDoc);
			           		if(xmlDoc == null)
			           		{
			           			
			           		}
			            	//DBComponent.logger.log(0,"--------------HBM FOUND---------");
			           		
			           		if(changeIdentities(xmlDoc))
			    			{
			    				//DBComponent.logger.log(0,"xml doc given for prsing = " );
			           			String xmlStr = xmlDoc.asXML();
			    				if(srcFile.canWrite())
			    					this.writeToFile(srcFile, filePath, xmlStr);
			    			}
			           	}
			        }   	
		    	}
		    	//xmlReader.getDocumentFromPath(arg0)
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
    
	private Document getDocumentFromPath(File filesrc) throws IOException, DocumentException
	{
		org.dom4j.Document document = null; 	
		XMLHelper xmlHelper = new XMLHelper();
		EntityResolver entityResolver = XMLHelper.DEFAULT_DTD_RESOLVER;
		try	
		{	
			SAXReader reader = new SAXReader();
			//DBComponent.logger.log(0,"filesrc = " + filesrc);
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
	private Boolean changeIdentities(Document xmlDoc) 
	{

		//DBComponent.logger.log(0,"@@@@@@@@@@@@@@  change Identities called");
		Element rootEl = xmlDoc.getRootElement();
		Element classEl = rootEl.element("class");
		Element idE2 = classEl.element("set");
		DBComponent.logger.log(0,"-----------------testing"+idE2);
		Element idEl = classEl.element("id");
		boolean retBool = false;
		if(idEl!=null)
		{
			Attribute attr = idEl.attribute("type");
			String attrVal = attr.getText();
//			Element metaEl = idEl.element("meta");
//			if(metaEl!=null)
//			{
//			Attribute attr = metaEl.attribute("attribute");
//			
//			String attrVal = attr.getText();
//			if(attrVal.indexOf("field-description")>=0)
//			{
//				String elVal = metaEl.getText();
//				if(elVal.indexOf("auto_increment")>=0)
//				{
				
					Element generatorEl = idEl.element("generator");
					if((generatorEl!=null))
					{
						if(attrVal.equals("java.lang.Integer") || attrVal.equals("java.lang.Long"))
						{
						Attribute classAttr =generatorEl.attribute("class");
						classAttr.setText("native");
						retBool = true;
						}
/*						else
						{
							Attribute classAttr =generatorEl.attribute("class");
							classAttr.setText("assigned");
							retBool = true;
							
						}*/
					}
//				}
//			}
//		}
	}
		return retBool;
		//return xmlDoc;
		
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
	
	public static void main(String args[])
	{
		//String hbmDirectory = "C:\\apache-tomcat-5.5.20\\common\\lib\\ext\\jar\\resources\\de\\stoneone\\component\\testtest"; 

		MappingEditor me = new MappingEditor(); 
		//me.parsseMapping(hbmDirectory);
		
		String REGEX = ".";
		try
		{
			Pattern p = Pattern.compile(REGEX,  Pattern.LITERAL);
			Matcher m = p.matcher(args[1]);
			String packageName = m.replaceAll("/");
			
			//DBComponent.logger.log(0,"received inptu = " + args[0]);
			//DBComponent.logger.log(0,"args[1]:" + args[1]);
			
			args[1].replace('.','/' );
			
			me.parsseMapping(args[0]+"/" + packageName);
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
