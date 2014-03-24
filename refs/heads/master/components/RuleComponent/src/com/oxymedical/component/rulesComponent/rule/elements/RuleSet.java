package com.oxymedical.component.rulesComponent.rule.elements;

import java.util.ArrayList;
import java.util.List;

import com.oxymedical.component.rulesComponent.IRule;
import com.oxymedical.component.rulesComponent.IRuleSet;
import com.oxymedical.component.rulesComponent.IVariables;


/**
 * Logical view of a ruleset.
 * A ruleset consists of a list of variables and rules
 * Variable list has the list of all the variables used in rules.
 * Rules list has the rules present in a rule set
 * 
 * @author Oxyent Medical
 *
 */
public class RuleSet implements IRuleSet {
	
	public int ruleSetId;
	
	private String name;
	
	public List<IVariables> variables = new ArrayList<IVariables>();
		
	public List<IRule> rules = new ArrayList<IRule>();

	public void createRule(IRule rule) {
		
	}

	public void deleteRule(IRule rule) {
			
	}

	public int getRuleSetId() {
		return ruleSetId;
	}

	public void setRuleSetId(int ruleSetId) {
		this.ruleSetId = ruleSetId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<IRule> getRules() {
		return rules;
	}

	public void setRules(List<IRule> rules) {
		this.rules = rules;
	}

	public List<IVariables> getVariables() {
		return variables;
	}

	public void setVariables(List<IVariables> variables) {
		this.variables = variables;
	}
}
