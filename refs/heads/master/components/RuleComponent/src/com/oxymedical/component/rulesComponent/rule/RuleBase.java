package com.oxymedical.component.rulesComponent.rule;

import java.util.ArrayList;
import java.util.List;

import com.oxymedical.component.rulesComponent.IRule;
import com.oxymedical.component.rulesComponent.IRuleBase;
import com.oxymedical.component.rulesComponent.IRuleSet;
import com.oxymedical.component.rulesComponent.exception.RuleComponentException;
import com.oxymedical.component.rulesComponent.rule.builder.RuleBuilder;


/**
 * RuleBase is the logical view of all the rules 
 * Rules are from differnt rule sets.
 * 
 * @author Oxyent Medical
 *
 */
public abstract class RuleBase implements IRuleBase {
	
	public List<IRule> rulesList = new ArrayList<IRule>();
	
	RuleBuilder ruleBuilder;
	
	public RuleBase() {
		ruleBuilder = new RuleBuilder();
	}	

	/**
	 * Reads the masterpage and erl and build rule.
	 * Rules created are then compiled
	 * 
	 * @param masterPagePath
	 * @throws Exception
	 */
	public void readRule(String masterPagePath) throws RuleComponentException 
	{
		try {
			ruleBuilder.reset();
			ruleBuilder.readMasterPage(masterPagePath);	
			ruleBuilder.readErl();
			this.rulesList = ruleBuilder.getRulesList();
			ruleBuilder.compile();
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuleComponentException(e.getMessage());
		}
	}
	
	
	public IRuleSet getRuleSet(String ruleSetId) {
		return null;
	}

	public void setRuleSet(IRuleSet ruleSet) {
		
	}

	public List<IRule> getRulesList() {
		return rulesList;
	}

	public void setRulesList(List<IRule> rulesList) {
		this.rulesList = rulesList;
	}

	public void addToRulesList(IRule rule) {
		this.rulesList.add(rule);
	}


	public RuleBuilder getRuleBuilder() {
		return ruleBuilder;
	}


	public void setRuleBuilder(RuleBuilder ruleBuilder) {
		this.ruleBuilder = ruleBuilder;
	}
	
}
