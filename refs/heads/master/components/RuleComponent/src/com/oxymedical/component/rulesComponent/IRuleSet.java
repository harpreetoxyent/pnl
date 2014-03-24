package com.oxymedical.component.rulesComponent;

import java.util.List;

public interface IRuleSet {
	
	public List<IRule> getRules();

	public void setRules(List<IRule> rules);

	public List<IVariables> getVariables();

	public void setVariables(List<IVariables> variables);
	
	public void createRule(IRule rule);
	
	public void deleteRule(IRule rule);	

}
