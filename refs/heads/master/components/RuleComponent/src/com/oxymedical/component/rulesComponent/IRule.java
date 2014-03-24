package com.oxymedical.component.rulesComponent;

import java.util.List;

public interface IRule {
	
	public ICondition getCondition() ;

	public void setCondition(ICondition condition);

	public List<IConsequence> getConsequenceList();

	public void setConsequenceList(List<IConsequence> consequenceList);

	public String getName();

	public void setName(String name);

	public int getSalience();

	public void setSalience(int salience);

	public int getRuleId() ;

	public void setRuleId(int ruleId);

	public String getRuleClassName();

	public void setRuleClassName(String ruleClassName);
	
	public String getUrl();
	
	public void setUrl(String url);
	
}
