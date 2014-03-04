package com.oxymedical.component.db.mappingGenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.InputSource;

import com.oxymedical.component.db.DBComponent;


public class BuildXMLEditor {
	
	static public File updateForMany2ManyMapping(File buildFile) throws IOException, DocumentException
	{
		Document xmlDoc = getDocumentFromPath(buildFile);
		addEntries(xmlDoc, DataPatternParser.getMappingInfo());
		buildFile = writeToFile(buildFile, xmlDoc);
		return buildFile;
	}
    
	static private Document getDocumentFromPath(File filesrc) throws IOException, DocumentException
	{
		org.dom4j.Document document = null; 	
		try	
		{	
			SAXReader reader = new SAXReader();
			FileInputStream fileInputStream = new FileInputStream(filesrc);
			document = reader.read(new InputSource(fileInputStream));
		}
		catch(DocumentException exp)
		{		
			throw exp;
		}			
	    return document;
	}
	
	static private void addEntries(Document xmlDoc,List extEle)
	{
		Element rootElem = xmlDoc.getRootElement();
		List<Element> targetElems = rootElem.elements("target");
		for (Iterator<Element> tgtIter = targetElems.iterator(); tgtIter.hasNext(); )
		{
			Element tgtElem = tgtIter.next();
			String targetName = tgtElem.attributeValue("name");
			if (targetName.equalsIgnoreCase("middlegen"))
			{
				Element middlegenElem = tgtElem.element("middlegen");
				
				
				Iterator itr = extEle.iterator();
				while(itr.hasNext())
				{
					MappingTable obj = (MappingTable) itr.next();
					
					Element m2mElem = middlegenElem.addElement("many2many");
					Element tableAElem = m2mElem.addElement("tablea");
					tableAElem.addAttribute("generate", "true");
					tableAElem.addAttribute("name", obj.getFirstTable().toLowerCase());
					Element joinTab = m2mElem.addElement("jointable");
					joinTab.addAttribute("name", obj.getJointTable().toLowerCase());
					joinTab.addAttribute("generate", "false");
					Element secondTab = m2mElem.addElement("tableb");
					secondTab.addAttribute("generate", "true");
					secondTab.addAttribute("name", obj.getSecondTable().toLowerCase());
						
					
				}
			}
			
		}
	}
	
		
	static private File writeToFile(File fl, Document document)
	{
		try
		{
			OutputFormat format = OutputFormat.createPrettyPrint();
			XMLWriter writer = new XMLWriter(new FileWriter( fl ), format);
	        writer.write( document );
	        writer.close();

		}
		catch(IOException e)
		{
			e.printStackTrace();
			DBComponent.logger.log(0,e.getMessage());
		}
		
        return fl;
	}
	
	
	
	
	
		
}
