package com.oxymedical.component.rulesComponent.rule.builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.oxymedical.component.rulesComponent.ICompiler;
import com.oxymedical.component.rulesComponent.IRule;
import com.oxymedical.component.rulesComponent.compiler.janino.JaninoCompiler;
import com.oxymedical.component.rulesComponent.exception.RuleComponentException;
import com.oxymedical.component.rulesComponent.parser.javacc.ParseException;
import com.oxymedical.component.rulesComponent.parser.javacc.RuleParser;
import com.oxymedical.component.rulesComponent.rule.elements.Rule;
import com.oxymedical.component.rulesComponent.rule.elements.RuleSet;
import com.oxymedical.component.rulesComponent.rule.locator.LocatorObject;
import com.oxymedical.component.rulesComponent.rule.locator.RuleSetLocator;
import com.oxymedical.component.rulesComponent.rule.reader.RuleReader;


/**
 * Reads the erl, parses the erl 
 * and builds the rule 
 * and compiles the rule to chk the syntax, semantics and validity
 * 
 * @author Oxyent Medical
 *
 */
public class RuleBuilder {
	 	 
	 private List<IRule> rulesList;
	 private Map<String, byte[]> map;
	 private RuleReader ruleReader;
	 
	 private RuleParser parser;
	 
	 public RuleBuilder() {		 
		 this.ruleReader = new RuleReader();
		 this.rulesList = new ArrayList<IRule>();
		 this.map = new HashMap<String, byte[]>();
	 }
		
	 public void reset(){
		 this.ruleReader = new RuleReader();
		 this.rulesList = new ArrayList<IRule>();
	 }
	/**
	 * Reads the erl rule file
	 * Parses the erl
	 * Builds the rule and add rule to rulebase.
	 *  
	 * @param reader
	 * @param ruleSetName
	 * @throws RuleComponentException
	 */
	public void parseErl(Reader reader) throws RuleComponentException {
		try {
			parser = new RuleParser (reader);
			parser.parse();
			RuleSet ruleSet = parser.getRuleSet();
			String ruleSetName = ruleSet.getName();
			ruleSetName = ruleSet.getName();
			ruleSetName = ruleSetName.replaceAll("\"", "");
			ruleSetName = ruleSetName.replaceAll(" ", "_");
			ruleSet.setName(ruleSetName);
			buildRuleClass(ruleSet);
		}catch(ParseException pe) {
			//pe.printStackTrace();
			throw new RuleComponentException(pe.getMessage());
		}
	}
		
	/**
	 * Creates a rule class of the rule
	 * Adds the rule to rule list
	 * Creates a map of rules with the rule name and byte array of the rule class
	 * This is required to compile the rule class created
	 * 
	 * @param ruleSet
	 */
	private void buildRuleClass(RuleSet ruleSet) throws RuleComponentException {	
		try{
			RuleClassBuilder ruleClassBuilder = null;
			String ruleClass;
			byte[] ba;
			List rules = ruleSet.getRules();
			for(Iterator it = rules.iterator(); it.hasNext();) {
				IRule rule = (Rule)it.next();
				ruleClassBuilder = new RuleClassBuilder();
				ruleClass = ruleClassBuilder.buildRuleClass(rule, ruleSet);
				rulesList.add(rule);
				ba = ruleClass.getBytes();		    
			    map.put(rule.getRuleClassName() , ba);		    
			}
		}catch (RuleComponentException e) {
			throw new RuleComponentException(e.getMessage());
		}
	}
	
	/**
	 * Compiles all the generated rule classes
	 * 
	 * @param jarList
	 * @throws RuleComponentException
	 */
	public void compile() throws RuleComponentException {
		try {
			if (null != this.map && this.map.size() > 0) {
				ICompiler compiler = new JaninoCompiler();
				compiler.compile(this.map, this.ruleReader.getJarList());
			}
		}catch(Exception rce) {
			//rce.printStackTrace();
        	throw new RuleComponentException(rce.getMessage());
		}
	}

	
	/**
	 * Reads the ruleset location from the masterpage file path 
	 * Populates the ruleset locator list and jar list
	 * 
	 * @param masterPagePath
	 * @param jarList
	 */
	public void readMasterPage(String masterPagePath) throws FileNotFoundException 
	{
		LocatorObject locatorOb = null;		
		List<String> ruleSetLocationList = ruleReader.readMasterPageXML(masterPagePath);		
		for(Iterator it = ruleSetLocationList.iterator(); it.hasNext();) {
			String ruleSetLocation = (String)it.next();
			locatorOb = ruleReader.readRuleSetXML(ruleSetLocation);
			
			File f = new File(masterPagePath);
			String pathPrefix = f.getParent();
			
			String erlLocationDir = locatorOb.getErlLocationDir();
			if (erlLocationDir.indexOf(":") < 0)
			{
				erlLocationDir = pathPrefix + "/" + erlLocationDir;
			}
			ruleReader.populateErlList(erlLocationDir);
			
			String jarLocationDir = locatorOb.getJarLocationDir();
			if (jarLocationDir.indexOf(":") < 0)
			{
				jarLocationDir = pathPrefix + "/" + jarLocationDir;
			}
			ruleReader.populateJarList(jarLocationDir);
		}			
	}
	
	/**
	 * Parses the erl rule file and populates rule list
	 * 
	 * @throws RuleComponentException
	 */
	public void readErl() throws RuleComponentException 
	{
		try{
			RuleSetLocator ruleSetLocator = null;
			for(Iterator it = ruleReader.getRuleSetLocatorList().iterator(); it.hasNext();) {
				ruleSetLocator = (RuleSetLocator)it.next();
				this.parseErl(ruleSetLocator.getRuleSetSourceReader());
			}
		}catch (RuleComponentException e) {
			throw new RuleComponentException(e.getMessage());
		}
	}
	
	public RuleReader getRuleReader() {
		return ruleReader;
	}

	public List<IRule> getRulesList()
	{
		return rulesList;		
	}
	
	
}
