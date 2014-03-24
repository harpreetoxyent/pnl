package com.oxymedical.component.rulesComponent.rule.locator;

import java.io.Reader;

/**
 * This saves the rule set name and the input stream reader of the rule 
 * 
 * @author Oxyent Medical
 *
 */
public class RuleSetLocator {
	
	String ruleSetName;
	
	Reader ruleSetSourceReader;

	public String getRuleSetName() {
		return ruleSetName;
	}

	public void setRuleSetName(String ruleSetName) {
		this.ruleSetName = ruleSetName;
	}

	public Reader getRuleSetSourceReader() {
		return ruleSetSourceReader;
	}

	public void setRuleSetSourceReader(Reader ruleSetSourceReader) {
		this.ruleSetSourceReader = ruleSetSourceReader;
	}

}
