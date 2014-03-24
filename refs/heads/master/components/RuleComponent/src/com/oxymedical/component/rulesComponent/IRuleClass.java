package com.oxymedical.component.rulesComponent;

import java.util.ArrayList;
import java.util.List;

import com.oxymedical.component.rulesComponent.rule.elements.Condition;


public interface IRuleClass {
	
	int salience =0;
	String url = "";
	List variablesList = new ArrayList();
	ICondition condition = new Condition();
	List consequenceList = new ArrayList();
	
	public int getSalience();
	
	public void setSalience(int salience);
	
	public String getUrl();
	
	public void setUrl(String url);
	
	public ICondition getCondition();
	
	public void setCondition(ICondition conditionList);
	
	public List getConsequenceList();
	
	public void setConsequenceList(List consequenceList);
	
	public List getVariablesList();
	
	public void setVariablesList(List variablesList);
	
	public void condition();
	
	public void consequence();

}
