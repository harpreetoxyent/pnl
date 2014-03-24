package com.oxymedical.component.rulesComponent.rete.productionMemory;

import java.util.Iterator;

import com.oxymedical.component.rulesComponent.IRule;
import com.oxymedical.component.rulesComponent.IWorkingMemory;
import com.oxymedical.component.rulesComponent.exception.ReteException;
import com.oxymedical.component.rulesComponent.exception.RuleComponentException;
import com.oxymedical.component.rulesComponent.rete.productionMemory.builder.ReteBuilder;
import com.oxymedical.component.rulesComponent.rete.productionMemory.builder.betaBuilder.BetaNodeManager;
import com.oxymedical.component.rulesComponent.rete.productionMemory.node.ReteNode;
import com.oxymedical.component.rulesComponent.rule.RuleBase;
import com.oxymedical.component.rulesComponent.workingMemory.ReteWorkingMemory;


/**
 * This class has the rete implementation of the list of rules.
 * 
 * @author Oxyent Medical
 */
public class ReteRuleBase  extends RuleBase {
	
	Rete rete;
	
	ReteBuilder reteBuilder;
	
	ReteNode reteNode;
	
	BetaNodeManager betaNodeManager;
	
	public ReteRuleBase() {
		super();

		this.rete = new Rete();
	}	
	
	public Rete getRete() {
		return rete;
	}
	
    /**
     * Builds rete tree from list of rules
     * 
     * @param rulesList
     * @throws RuleComponentException
     */
    public void buildRete() throws RuleComponentException
    {
    	reteBuilder = new ReteBuilder();
    	try {
			for(Iterator<IRule> it = rulesList.iterator(); it.hasNext();) 
			{
				IRule rule = (IRule)it.next();
				reteBuilder.addRuleToRete(rule);
			}
			rete.setReteNode(reteBuilder.getReteNode());
			this.reteNode = rete.getReteNode();
			this.betaNodeManager = reteBuilder.getBetaManager();
			//printReteTree();
    	}catch(ReteException re) {
    		re.printStackTrace();
    		throw new RuleComponentException(re.getMessage());
    	}
	}
    
    
    public void deleteRuleFromRete() {
    	
    }

	/*private void printReteTree() {
		 RuleComponent.logger.log(0,"After building rete");
		 RuleComponent.logger.log(0,"No of object type nodes in rete node = " + this.reteNode.getObjectTypeList().size());
		HashMap map = this.reteNode.getObjectTypeList();
		
		Collection list = map.values();
		Object[] arr = list.toArray();
		for(int i=0; i < list.size(); i++ ) {
			
			ObjectTypeNode node = (ObjectTypeNode)arr[i];
			 RuleComponent.logger.log(0,"type = "+node.getClassName());
			AlphaNode alphaNode = node.getAlphaNode();
			 RuleComponent.logger.log(0,"No of condition = "+alphaNode.getConditionConstraintTable().size());
			Hashtable list1 = (Hashtable)alphaNode.getConditionConstraintTable();
			for(int j=0, k=1; j < list1.size(); j++,k++ ) {
				ConditionConstraints cons = (ConditionConstraints)list1.get(new Integer(k));
				 RuleComponent.logger.log(0,"condition name = "+cons.getNameOfCondition());
				 RuleComponent.logger.log(0,"condition type = "+cons.getConstraintType());
				 RuleComponent.logger.log(0,"condition value = "+cons.getValue());
			}
		}
		
	}*/
	

	public ReteBuilder getReteBuilder() {
		return reteBuilder;
	}
	
	public IWorkingMemory newWorkingMemory() {		
		return new ReteWorkingMemory(this);
	}

	public BetaNodeManager getBetaManager() {
		return betaNodeManager;
	}

}
