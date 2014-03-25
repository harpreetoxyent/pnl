package com.oxymedical.component.rulesComponent;

import java.io.File;
import java.util.List;

import com.oxymedical.component.baseComponent.annotations.EventSubscriber;
import com.oxymedical.component.baseComponent.exception.ComponentException;
import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.Event;
import com.oxymedical.core.commonData.IHICData;


public interface IRuleComponent   {

	/*List<Facts> factList;
		RuleComponent
		{
			BuildRete
			}*/
	
	public void createRuleSet(IRuleSet ruleSet);	
	public void deleteRuleSet(IRuleSet ruleSet);
	public IRuleSet createRuleSetFromERL(File erlDocument);


	public void info(HICData object);

	public HICData buildReteHIC(HICData hicData) throws ComponentException;
	
	public void buildRete(String masterPageLocation) throws ComponentException;
	
	public List executeRules(Object[] ob) throws ComponentException;
	
	public boolean retractFactFromRete(Object ob) throws ComponentException;
	
	public boolean modifyFactInRete(Object ob) throws ComponentException;
	
	public List<IAction> execute(HICData object);
	
	public IHICData executeRuleHICData(IHICData socialUsedataObjectrData) throws Exception;

	public List<IAction> getAgendaList();

	public void actionHandler(List<IAction> actions);

//	@Publish("notifyAgendaList", HIC);
	public void notifyAgendaList(HICData object, Event event);
	
	
}
