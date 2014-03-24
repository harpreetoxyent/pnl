package com.oxymedical.component.rulesComponent.rete.productionMemory.builder;

import java.util.ArrayList;
import java.util.Hashtable;

import com.oxymedical.component.rulesComponent.IRule;
import com.oxymedical.component.rulesComponent.IRuleClass;
import com.oxymedical.component.rulesComponent.compiler.compilationUnit.RuleCompilationData;
import com.oxymedical.component.rulesComponent.exception.ReteException;
import com.oxymedical.component.rulesComponent.rete.productionMemory.builder.alphaBuilder.AlphaBuilder;
import com.oxymedical.component.rulesComponent.rete.productionMemory.builder.alphaBuilder.ConditionConstraints;
import com.oxymedical.component.rulesComponent.rete.productionMemory.builder.betaBuilder.BetaBuilder;
import com.oxymedical.component.rulesComponent.rete.productionMemory.builder.betaBuilder.BetaNodeManager;
import com.oxymedical.component.rulesComponent.rete.productionMemory.builder.objectTypeBuilder.ObjectTypeBuilder;
import com.oxymedical.component.rulesComponent.rete.productionMemory.node.ReteNode;
import com.oxymedical.component.rulesComponent.util.RuleComponentUtil;


/**
 * This class is the main class that builds the rete tree
 *  
 * @author Oxyent Medical
 *
 */
public class ReteBuilder 
{	
	ClassLoader classLoader;
	
	ReteNode reteNode;
	
	ObjectTypeBuilder objectTypeBuilder;
	
	AlphaBuilder alphaNodeBuilder;
	
	BetaBuilder betaBuilder;
	
	static BetaNodeManager betaManager;
	
	/// ama
	// Added to avoid adding the already added rules.
	// Saves the duplicacy in the nodes
	ArrayList<String> addedRules;
	
	public ReteBuilder() 
	{
		classLoader = RuleCompilationData.getClassLoader();
		objectTypeBuilder = new ObjectTypeBuilder();		
		objectTypeBuilder.setClassLoader(classLoader);
		alphaNodeBuilder = new AlphaBuilder(classLoader);
		betaBuilder = new BetaBuilder();
		reteNode = new ReteNode();
		betaManager = new BetaNodeManager();
		addedRules = new ArrayList<String>();
	}
	
	public ReteNode getReteNode() 
	{
		return this.reteNode;
	}
	
	/**
	 * Adds each rule to rete
	 * First object type nodes are built, then alpha nodes and then beta nodes
	 * To manage the beta nodes a beta node manager is created 
	 * Beta node manager has the list of all the nodes particpating in a join node condition
	 * 
	 * @param rule
	 * @throws ReteException
	 */
	public void addRuleToRete(IRule rule) throws ReteException
	{
		if(addedRules.contains(rule.getRuleClassName())) return;
		
		try 
		{
			IRuleClass ruleClass = (IRuleClass) classLoader.loadClass(rule.getRuleClassName()).newInstance();

			// build object type nodes
			objectTypeBuilder.buildObjectTypeNodes(ruleClass.getVariablesList(), reteNode);
			RuleComponentUtil.setTypes(objectTypeBuilder.getTypes());
			
			// evaluates a condition and gets the expression w.r.t precedence 
			String conditionString = ruleClass.getCondition().getConditionString();
			Hashtable<String, String> expressionHash = RuleComponentUtil.evaluateExpression("(" + conditionString + ")");
			
			// build alpha nodes
			Hashtable<String, ConditionConstraints> condHash = alphaNodeBuilder.buildAlphaNodes(expressionHash, reteNode);
			
			// build beta nodes
			betaBuilder.buildBetaNodes(expressionHash, condHash, ruleClass);
			
			this.betaManager = betaBuilder.getBetaManager();
			addedRules.add(rule.getRuleClassName());
		} 
		catch(InstantiationException ie)
		{
			ie.printStackTrace();
			throw new ReteException(ie.getMessage());
		}
		catch(IllegalAccessException iae) 
		{
			iae.printStackTrace();
			throw new ReteException(iae.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	public BetaNodeManager getBetaManager() {
		return betaManager;
	}

}
