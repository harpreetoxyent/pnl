package com.oxymedical.component.rulesComponent.rule.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import com.oxymedical.component.rulesComponent.RuleComponent;
import com.oxymedical.component.rulesComponent.constants.RulesConstants;
import com.oxymedical.component.rulesComponent.rule.locator.LocatorObject;
import com.oxymedical.component.rulesComponent.rule.locator.RuleSetLocator;
import com.oxymedical.core.ioutil.FileIO;
import com.oxymedical.core.xmlutil.XmlReader;


/**
 * Reads the masterpage
 * Master page has the location of ruleset.xml
 * Ruleset xml is then parsed to get the location of ERL and jar file and popultes the list  
 * 
 * @author Oxyent Medical
 *
 */
public class RuleReader {	
	
	List<RuleSetLocator> ruleSetLocatorList;
	List<File> jarList;
		
	 
	public RuleReader() {
		jarList = new ArrayList<File>();
		ruleSetLocatorList = new ArrayList<RuleSetLocator>();		 
	}
	
	/**
	 * Reads all the jar present in a jar locator directory
	 * @param locatorOb
	 * @param jarFileList
	 */
	public void populateJarList(String jarLocationDir) {
		if (null != jarLocationDir) {
			List jarList = FileIO.readDirectory(jarLocationDir, RulesConstants.JAR_EXTN);
			if (null != jarList) {
				for(Iterator it = jarList.iterator(); it.hasNext(); ) {
					String jarName = (String)it.next();					
					// RuleComponent.logger.log(0,"jarName = " +jarName);
					String jarPath = jarLocationDir + "/" + jarName;
					// RuleComponent.logger.log(0,"jarPath = " +jarPath);
					this.jarList.add(new File(jarPath));
				}				
			}				
		}
	}

	/**
	 * Reads all the ruleset from the erl location directory 
	 * @param builder
	 * @param locatorOb
	 */
	public void populateErlList(String erlLocationDir) throws FileNotFoundException{
		try {
			if (null != erlLocationDir) {
				RuleSetLocator ruleSetLocator = null;				
				List erlList = FileIO.readDirectory(erlLocationDir, RulesConstants.ERL_EXTN);
				if (null != erlList) {
					for(Iterator it = erlList.iterator(); it.hasNext(); ) {
						ruleSetLocator = new RuleSetLocator();
						String erlName = (String)it.next();					
						// RuleComponent.logger.log(0,"erlname = " +erlName);
						final Reader source = readFile(erlLocationDir + "/" +erlName);
						String ruleSetName = erlName.substring(0, erlName.indexOf(RulesConstants.ERL_EXTN));
						
						ruleSetLocator.setRuleSetName(ruleSetName);
						ruleSetLocator.setRuleSetSourceReader(source);
						this.ruleSetLocatorList.add(ruleSetLocator);
					}				
				}
			}
		}
		catch (FileNotFoundException fnfe) {
			throw new FileNotFoundException(fnfe.getMessage());
		}
		
	}
	
	/**
	 * This method reads an erl file from input stream.
	 * 
	 * @param erlFileName
	 * @return
	 * @throws FileNotFoundException
	 */
	private Reader readFile(String erlFileName) throws FileNotFoundException {	
		try {
			final Reader reader = new InputStreamReader( new FileInputStream(erlFileName) ); 
			// this.getClass().getResourceAsStream( erlFileName ) - for absolute path
			return reader;
		}catch (FileNotFoundException fnfe) {
			//fnfe.printStackTrace();
			throw fnfe;
		}
	}
	
	
	
	/**
	 * Reads the master page xml and gets the ruleset xml path from it
	 * 
	 * @param masterPagePath
	 * @return
	 */
	public List<String> readMasterPageXML(String masterPagePath) {
		XmlReader xmlReader = new XmlReader();
		List<String> ruleSetLocationList = new ArrayList<String>();
		Document doc = xmlReader.getDocumentFromPath(masterPagePath);
		Element root = doc.getRootElement();
		List<Element> ruleSetList = root.elements();
		for(Iterator it = ruleSetList.iterator(); it.hasNext();){
			Element ruleSet = (Element)it.next();
			String ruleSetLocation = ruleSet.attributeValue("location");
			
			// Condition added so as to allow relative path
			if (ruleSetLocation.indexOf(":") < 0)
			{
				File f = new File(masterPagePath);
				ruleSetLocation = f.getParent() + "/" + ruleSetLocation;
				 RuleComponent.logger.log(0,"ruleSetLocation="+ruleSetLocation);
			}
			ruleSetLocationList.add(ruleSetLocation);
		}
		return ruleSetLocationList;
	}

	/**
	 * Gets the rule set locator path and jar locator path from rule set xml
	 * 
	 * @param ruleSetXmlPath
	 * @return
	 */
	public LocatorObject readRuleSetXML(String ruleSetXmlPath) {
		XmlReader xmlReader = new XmlReader();
		LocatorObject locationOb = null;
		// RuleComponent.logger.log(0,"ruleSetXmlPath = " +ruleSetXmlPath);
		Document doc = xmlReader.getDocumentFromPath(ruleSetXmlPath);
		if (null != doc) {
			Element root = doc.getRootElement();
			if (null != root) {
				String erlLocation = null, jarLocation = null;
				Element erl = root.element("erl");
				if (null != erl) {
					erlLocation = erl.attributeValue("location");	
					// RuleComponent.logger.log(0,"erlLocation = " +erlLocation);
				}
				Element jar = root.element("jar");
				if (null != jar) {
					jarLocation = jar.attributeValue("location");
					// RuleComponent.logger.log(0,"jarLocation = " +jarLocation);
				}
				locationOb = new LocatorObject(erlLocation, jarLocation);
			}
		}
		return locationOb;
	}

	public List<File> getJarList() {
		return jarList;
	}

	public List<RuleSetLocator> getRuleSetLocatorList() {
		return ruleSetLocatorList;
	}


}
