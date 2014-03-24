package com.oxymedical.component.rulesComponent.workingMemory;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.oxymedical.component.rulesComponent.IAction;
import com.oxymedical.component.rulesComponent.IRuleClass;
import com.oxymedical.component.rulesComponent.rete.productionMemory.node.TerminalNode;
import com.oxymedical.component.rulesComponent.rule.elements.Consequence;


/**
 * List of action items that need to be executed 
 * on satisfying the condition of the rule
 * 
 * @author Oxyent Medical
 *
 */
public class Action implements IAction {
	
	List<TerminalNode> terminalNodeList;
	
	Hashtable<Integer, List<IRuleClass>> consequenceTable = new Hashtable<Integer, List<IRuleClass>>();
	
	public List consequences = new ArrayList();
	
	public Action() {
		terminalNodeList = new ArrayList<TerminalNode>();
	}
	
	public void addConsequence(Consequence conseq) {
		
	}
	
	public List getConsequenceList() {
		return null;
	}

	public List<TerminalNode> getTerminalNodeList() {
		return terminalNodeList;
	}

	public void addTerminalNode(TerminalNode terminalNode) {
		this.terminalNodeList.add(terminalNode);
		addConsequenceTable(terminalNode.getRuleClass());
	}

	public Hashtable<Integer, List<IRuleClass>> getConsequenceTable() {
		return consequenceTable;
	}

	/**
	 * Adds the ruleclass with the salience value to the hashtable.
	 * This is required as rules with higher salience needs to be 
	 * executed before the rule having less salience value 
	 * 
	 * @param ruleClass
	 */
	private void addConsequenceTable(IRuleClass ruleClass) {
		Integer sal = ruleClass.getSalience();
		List ruleClassList = this.consequenceTable.get(sal);
		if (null == ruleClassList) {
			ruleClassList = new ArrayList();
		}
		if (! ruleClassList.contains(ruleClass))
			ruleClassList.add(ruleClass);
		this.consequenceTable.put(sal, ruleClassList);
	}

	
}
