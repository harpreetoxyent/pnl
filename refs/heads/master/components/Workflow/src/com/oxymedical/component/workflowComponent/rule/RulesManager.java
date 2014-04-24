package com.oxymedical.component.workflowComponent.rule;

import java.util.ArrayList;
import java.util.List;

import com.oxymedical.component.baseComponent.exception.ComponentException;
import com.oxymedical.component.rulesComponent.IRuleClass;
import com.oxymedical.component.rulesComponent.RuleComponent;
import com.oxymedical.component.rulesComponent.rule.elements.Consequence;
import com.oxymedical.component.workflowComponent.WorkflowComponent;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.propertyUtil.PropertyUtil;

public class RulesManager
{
	static RuleComponent _rule = WorkflowComponent.ruleComponent;
	
	public static List<String> getRules(IHICData dataObject) throws WorkflowComponentException
	{
		Object[] facts = {dataObject};
		List<IRuleClass> ruleClassList = new ArrayList<IRuleClass>();
		List<String> resultList = new ArrayList<String>();;

		getRulesInstance();
		
		try
		{
			ruleClassList = _rule.executeRules(facts);  // Should be called every time a fact arrives
		}
		catch (ComponentException e)
		{
			e.printStackTrace();
			throw new WorkflowComponentException(e);
		}
		
		
		for (int i=0; i<ruleClassList.size();i++)
		{
			IRuleClass rule = ruleClassList.get(i);
			Consequence con = (Consequence)rule.getConsequenceList().get(0);
			WorkflowComponent.log(0, "[con.getConsequenceString())]" + con.getConsequenceString());
			resultList.add(con.getConsequenceString());
		}
		
		return resultList;
	}
	
	public static void buildRules(String masterPageLocation) throws WorkflowComponentException
	{
		if (masterPageLocation == null)
		{
			masterPageLocation = PropertyUtil.setUpProperties("GLASSFISH_DOMAIN_HOME")
					+ "/config/rules/masterpage.xml";
		}
		
		getRulesInstance();
		try
		{
			 // Should be called only once
			_rule.buildRete(masterPageLocation);
		}
		catch (ComponentException e)
		{
			throw new WorkflowComponentException(e.getMessage());
		}
	}
	
	private static void getRulesInstance()
	{
		if (_rule == null) _rule = new RuleComponent();
	}
}
