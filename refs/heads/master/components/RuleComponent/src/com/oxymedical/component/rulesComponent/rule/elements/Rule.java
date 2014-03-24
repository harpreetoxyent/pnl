package com.oxymedical.component.rulesComponent.rule.elements;

import java.util.ArrayList;
import java.util.List;

import com.oxymedical.component.rulesComponent.ICondition;
import com.oxymedical.component.rulesComponent.IConsequence;
import com.oxymedical.component.rulesComponent.IRule;


/**
 * Logical view of a rule.
 * A rule consists of a condition and a consequence
 * It also saves the rule class name with the name of the rule and its salience
 * 
 * @author Oxyent Medical
 *
 */
public class Rule implements IRule {
	
	private int ruleId;
	
	private String name;
	
	private int salience;
	
	private String ruleClassName;	
	
	public ICondition condition = new Condition();
	
	public List<IConsequence> consequenceList = new ArrayList<IConsequence>();
	
	private String url;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ICondition getCondition() {
		return condition;
	}

	public void setCondition(ICondition condition) {
		this.condition = condition;
	}

	public List<IConsequence> getConsequenceList() {
		return consequenceList;
	}

	public void setConsequenceList(List<IConsequence> consequenceList) {
		this.consequenceList = consequenceList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSalience() {
		return salience;
	}

	public void setSalience(int salience) {
		this.salience = salience;
	}

	public int getRuleId() {
		return ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleClassName() {
		return ruleClassName;
	}

	public void setRuleClassName(String ruleClassName) {
		this.ruleClassName = ruleClassName;
	}

	
}
