package com.oxymedical.component.rulesComponent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.dom4j.Document;

import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.baseComponent.annotations.EventSubscriber;
import com.oxymedical.component.baseComponent.exception.ComponentException;
import com.oxymedical.component.logging.LoggingComponent;
import com.oxymedical.component.rulesComponent.exception.RuleComponentException;
import com.oxymedical.component.rulesComponent.parser.javacc.ParseException;
import com.oxymedical.component.rulesComponent.parser.javacc.RuleParser;
import com.oxymedical.component.rulesComponent.parser.javacc.SimpleNode;
import com.oxymedical.component.rulesComponent.rete.productionMemory.ReteRuleBase;
import com.oxymedical.component.rulesComponent.rule.elements.Consequence;
import com.oxymedical.component.rulesComponent.rule.elements.RuleSet;
import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.Event;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.maintenanceData.IMaintenanceData;
import com.oxymedical.core.propertyUtil.PropertyUtil;
import com.oxymedical.framework.objectbroker.annotations.InjectNew;


public class RuleComponent implements IRuleComponent,IComponent {
	
	String masterPagePath;
	IRuleBase ruleBase;
   	@InjectNew
   	static public LoggingComponent logger;
	public static LoggingComponent getInstanceOfLoggingComponent() {
		if (logger == null) {
			logger = new LoggingComponent();
		}
		return logger;
	}
	@EventSubscriber(topic = "buildReteRules")
	public HICData buildReteHIC(HICData hicData) throws ComponentException 
	{
		System.out.println("*********INSIDE RULE COMPONENT PNL BUILD RETE************");	
		String masterPageLocation = PropertyUtil.setUpProperties("RULEFILE_LOCATION");
		System.out.println("Master Page Location" + masterPageLocation);
		buildRete(masterPageLocation);
		return hicData;
	}
	
	public void buildRete(String masterPageLocation) throws ComponentException 
	{
		
		System.out.println("*********INSIDE RULE COMPONENT  BUILD RETE************");
		this.masterPagePath = masterPageLocation;
		ruleBase = new ReteRuleBase(); 
		//ruleBase.readRule(RulesConstants.MASTER_PAGE_PATH);
		try {
			ruleBase.readRule(masterPageLocation);
			ruleBase.buildRete();
		} catch (RuleComponentException rce) {
			rce.printStackTrace();
			throw new ComponentException(rce.getMessage());
		}
		/*IWorkingMemory reteWorkingMemory = ruleBase.newWorkingMemory();
		reteWorkingMemory.assertObject(ob);
		return reteWorkingMemory.executeRules();*/
	}

	/**
	 * 
	 * buildRete(String masterPageLocation) should be called before this.
	 * Difference from buildRete() is that it uses the stored value of masterPageLocation
	 * And *does not* initialize the ReteRuleBase()
	 * 
	 * Does not take care of edited erls
	 * Adds nodes for any new erl
	 * Removes the nodes of any deleted erls
	 * The node structure of the Rete is created from scratch. Only the compilation is avoided
	 * 
	 * USAGE: When an erl is to be added or removed from the rete (through the erls folder)
	 * 			If erls are edited use buildRete() instead. 
	 * 
	 * @author ama
	 * @throws ComponentException
	 */
	public void refreshErls() throws ComponentException {
	
		try {		
			ruleBase.readRule( masterPagePath);
			ruleBase.buildRete();
		}catch(Exception e){
			e.printStackTrace();
			throw new ComponentException(e.getMessage());
		}
		
	}
	public List<IRuleClass> executeRules(Object[] ob) throws ComponentException 
	{
		System.out.println("-------Inside Execute RULES---rulebase="+ruleBase);
		try {
			IWorkingMemory reteWorkingMemory = ruleBase.newWorkingMemory();
			reteWorkingMemory.assertObject(ob);
			return reteWorkingMemory.executeRules();
		} catch (RuleComponentException rce) {
			throw new ComponentException(rce.getMessage());
		}
	}
	
	@EventSubscriber(topic = "executeRuleHICData")
	public IHICData executeRuleHICData(IHICData dataObject) throws Exception
	{
		IHICData hicData = new HICData();
		List<String> resultList = new ArrayList<String>();
		try
		{
			String factInStrFormat = (String) dataObject.getData().getRawData();
			System.out.println("-------Inside Execute RULES---factInStrFormat"+factInStrFormat);
			Object[] facts = {dataObject};
			List<IRuleClass> ruleClassList = new ArrayList<IRuleClass>();
			ruleClassList = executeRules(facts);  // Should be called every time a fact arrives
			System.out.println("-------Inside Execute RULES- number of rules mathcing=--ruleClassList.size()="+ruleClassList.size());
			for (int i=0; i<ruleClassList.size();i++)
			{
				IRuleClass rule = ruleClassList.get(i);
				Consequence con = (Consequence)rule.getConsequenceList().get(0);
				resultList.add(con.getConsequenceString());
			}
			for (int i=0; i<resultList.size();i++)
			{
				System.out.println("Inside executeRuleHICData consequence list");
				System.out.println(resultList.get(i));
				//	hicData.getData().setRawData(resultLis
			}
		} 
		catch (ComponentException e) 
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return hicData;
		
	}
	public void actionHandler(List<IAction> actions) {
		// TODO Auto-generated method stub

	}

	public void createRuleSet(IRuleSet ruleSet) {
		// TODO Auto-generated method stub

	}

	public void deleteRuleSet(IRuleSet ruleSet) {
		// TODO Auto-generated method stub

	}

	public List<IAction> execute(HICData object) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IAction> getAgendaList() {
		// TODO Auto-generated method stub
		return null;
	}

	public void info(HICData object) {
		// TODO Auto-generated method stub

	}

	public void notifyAgendaList(HICData object, Event event) {
		// TODO Auto-generated method stub

	}

	

	public IRuleSet createRuleSetFromERL(File erlDocument) {
		if (null == erlDocument || erlDocument.equals("")) {
			return null;
		}
		RuleSet ruleSet = null;
		try {
			RuleParser parser = new RuleParser (new FileInputStream(erlDocument));
			SimpleNode root = parser.parse();
		    root.dump("");		    
		    ruleSet = parser.getRuleSet();
		} catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
			
		}catch(ParseException pe) {
			pe.printStackTrace();
		}
		return ruleSet;
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public IHICData getHicData() {
		// TODO Auto-generated method stub
		return null;
	}

	public void maintenance(IMaintenanceData arg0) {
		// TODO Auto-generated method stub
		
	}

	public void run() {
		// TODO Auto-generated method stub
		
	}

	public void setHicData(IHICData arg0) {
		// TODO Auto-generated method stub
		
	}

	public boolean modifyFactInRete(Object ob) throws ComponentException {
		boolean result;
		try {
			IWorkingMemory reteWorkingMemory = ruleBase.newWorkingMemory();
			reteWorkingMemory.modifyObject(ob);
			result = true;
		} catch (RuleComponentException rce) {
			throw new ComponentException(rce.getMessage());
		}
		return result;
	}
	public void start(Hashtable<String, Document> arg0) {
		// TODO Auto-generated method stub
		
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}

	public boolean retractFactFromRete(Object ob) throws ComponentException {
		boolean result;
		try {
			IWorkingMemory reteWorkingMemory = ruleBase.newWorkingMemory();
			reteWorkingMemory.retractObject(ob);
			result = true;
		} catch (RuleComponentException rce) {
			throw new ComponentException(rce.getMessage());
		}
		return result;
	}	
}
