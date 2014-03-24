package com.oxymedical.component.rulesComponent;

import java.util.List;

import com.oxymedical.component.rulesComponent.exception.RuleComponentException;


public interface IWorkingMemory{
	
	public IAction getAgenda();
	
	public void assertObject(Object[] ob) throws RuleComponentException;
	
	public void assertObject(Object ob) throws RuleComponentException;
	
	public IFactHandle retractObject(Object ob) throws RuleComponentException;
	
	public IFactHandle modifyObject(Object ob) throws RuleComponentException;
	
	public IFactHandle fireAllRules();
	
	public List<IRuleClass> executeRules();

}
