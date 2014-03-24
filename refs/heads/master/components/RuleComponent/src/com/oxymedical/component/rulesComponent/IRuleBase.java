package com.oxymedical.component.rulesComponent;

import java.util.List;

import com.oxymedical.component.rulesComponent.exception.RuleComponentException;
import com.oxymedical.component.rulesComponent.rete.productionMemory.Rete;
import com.oxymedical.component.rulesComponent.rete.productionMemory.builder.betaBuilder.BetaNodeManager;


public interface IRuleBase {
	
	public void readRule(String masterPagePath) throws RuleComponentException;
	
	public IRuleSet getRuleSet(String ruleSetId);

	public void setRuleSet(IRuleSet ruleSet);

	public List<IRule> getRulesList();

	public void setRulesList(List<IRule> rulesList);
	
	public void addToRulesList(IRule rule);
	
	public IWorkingMemory newWorkingMemory();
	
	public void buildRete() throws RuleComponentException;
	
	public Rete getRete();
	
	public BetaNodeManager getBetaManager();
		
}
