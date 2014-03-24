package com.oxymedical.component.rulesComponent.rete.productionMemory.node;

import com.oxymedical.component.rulesComponent.INode;
import com.oxymedical.component.rulesComponent.IRuleClass;

/**
 * Terminal node of rete that contains the consequence
 * 
 * @author Oxyent Medical
 *
 */
public class TerminalNode implements INode {
	
	IRuleClass ruleClass;
	
	public TerminalNode(IRuleClass ruleClass) {
		this.ruleClass = ruleClass;
	}

	public IRuleClass getRuleClass() {
		return ruleClass;
	}
	
	public int getId() {		
		return 0;
	}

	public void addNode(INode node) {
		
	}

	public void deleteNode(INode node) {
		
	}

}
