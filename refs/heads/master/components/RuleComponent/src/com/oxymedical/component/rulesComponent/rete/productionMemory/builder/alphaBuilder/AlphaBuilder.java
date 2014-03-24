package com.oxymedical.component.rulesComponent.rete.productionMemory.builder.alphaBuilder;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.rulesComponent.RuleComponent;
import com.oxymedical.component.rulesComponent.rete.productionMemory.node.AlphaNode;
import com.oxymedical.component.rulesComponent.rete.productionMemory.node.ObjectTypeNode;
import com.oxymedical.component.rulesComponent.rete.productionMemory.node.ReteNode;


/**
 * Builds the alpha nodes of rete
 * 
 * @author Oxyent Medical
 *
 */
public class AlphaBuilder {

	AlphaBuilderUtil alphaBuilderUtil;
	
	public AlphaBuilder(ClassLoader classLoader) {
		alphaBuilderUtil = new AlphaBuilderUtil(classLoader);
	}
	
	/**
	 * Creates alpha nodes for the rule from the given condition
	 * 
	 * @param condition
	 * @param reteNode
	 */
	public Hashtable<String, ConditionConstraints> buildAlphaNodes(Hashtable expressionHash, ReteNode reteNode) 
	{
		Hashtable<String, ConditionConstraints> conditionHash = new Hashtable<String, ConditionConstraints>();
		
		// get the list of condition from the hashtable
		List conditionList = alphaBuilderUtil.getConditionList(expressionHash);
		if (null != conditionList && conditionList.size() > 0) {
			//alphaBuilderUtil.setTypes(types);
			for(Iterator it = conditionList.iterator(); it.hasNext();) {
				String cond = (String)it.next();
				ConditionConstraints conditionConstraint = alphaBuilderUtil.parseConditionString(cond, reteNode);
				conditionHash.put(conditionConstraint.getConditionString(), conditionConstraint);
				int condId = addAlphaToObjectTypeNode(conditionConstraint, reteNode);
			}
		}
		// RuleComponent.logger.log(0,"alpha node built");
		return conditionHash;
	}
	
	/**
	 * Appends the alpha node to specific object type node
	 * 
	 * @param conditionConstraint
	 * @param reteNode
	 * @return
	 */
	private int addAlphaToObjectTypeNode(ConditionConstraints conditionConstraint, ReteNode reteNode) {
		String name = conditionConstraint.getObjectTypeName();
		ObjectTypeNode objectTypeNode = reteNode.getObjectTypeList().get(name);						
		AlphaNode alphaNode = objectTypeNode.getAlphaNode();
		Hashtable condConstraintTable = alphaNode.getConditionConstraintTable();
		if (!condConstraintTable.containsValue(conditionConstraint)) 
		{
			alphaNode.incrementId();
			alphaNode.addConditionConstraint(conditionConstraint);		
			objectTypeNode.setAlphaNode(alphaNode);			
			//reteNode.getObjectTypeList().remove(name);
			reteNode.getObjectTypeList().put(name, objectTypeNode);
		}
		return alphaNode.getId();
	}
	

}
