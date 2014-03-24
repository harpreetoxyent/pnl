package com.oxymedical.component.rulesComponent.rete.productionMemory.builder.betaBuilder;

import java.util.Hashtable;
import java.util.regex.Pattern;

import com.oxymedical.component.rulesComponent.ICondition;
import com.oxymedical.component.rulesComponent.INode;
import com.oxymedical.component.rulesComponent.IRuleClass;
import com.oxymedical.component.rulesComponent.constants.ConditionConstants;
import com.oxymedical.component.rulesComponent.rete.productionMemory.builder.alphaBuilder.ConditionConstraints;
import com.oxymedical.component.rulesComponent.rete.productionMemory.builder.alphaBuilder.ConditionIdentifier;
import com.oxymedical.component.rulesComponent.rete.productionMemory.builder.alphaBuilder.ResolutorKey;
import com.oxymedical.component.rulesComponent.rete.productionMemory.node.JoinNode;
import com.oxymedical.component.rulesComponent.rete.productionMemory.node.TerminalNode;
import com.oxymedical.component.rulesComponent.util.RuleComponentUtil;


/**
 * Builds the beta node of the rete tree
 * 
 * @author Oxyent Medical
 *
 */
public class BetaBuilder {
	
	static BetaNodeManager betaManager;
	
	public BetaBuilder(){
		betaManager = new BetaNodeManager();
	}
	
	/**
	 * Builds the beta nodes of the rete and adds the node to beta node manager that manages all beta nodes
	 * @param condition
	 * @param condHash
	 * @param ruleClass
	 */
	public void buildBetaNodes(Hashtable<String, String> expressionHash, Hashtable<String, ConditionConstraints> condHash, IRuleClass ruleClass) {
		if (null != expressionHash && expressionHash.size() > 0) {
			populateConditionList(expressionHash, condHash, ruleClass);
		}
	}
	
	/**
	 * populates the condition list for the beta nodes.
	 * This method creates join nodes and populates beta node manager
	 * 
	 * @param expressionHash
	 * @param condHash
	 * @param ruleClass
	 */
	private void populateConditionList(Hashtable<String, String> expressionHash, Hashtable<String, ConditionConstraints> condHash, IRuleClass ruleClass) {
		String regx = "[||, && ,\\S]";
		// RuleComponent.logger.log(0,"Expression hash size = " + expressionHash.size());
		JoinNode joinNode = null;
		INode node = null;
		ResolutorKey key;
		Hashtable<String, INode> expHash = new Hashtable<String, INode>();
		String condElement = null;
		boolean isJoinNodeCreated = false;
		// iterates on expression list
		for(int i=0, k=1; i < expressionHash.size(); i++, k++) {			
			JoinNode jn = null;	
			INode leftNode = null;
			String cond = (String)expressionHash.get("e"+k);
			String exp = "e"+k;
			
			// below code is to truncate extra space before and after relational operators 
			// and adds extra space before and after logical operators
			// This is required to split the condition string
			cond = cond.replaceAll("  ", " ");
			cond = RuleComponentUtil.truncateExtraSpace(cond);
			cond = RuleComponentUtil.addExtraSpaceForOperator(cond);
			
			// splits the expression on operators
			// RuleComponent.logger.log(0,"e"+k +" = " + cond);			
			String splittedStr[] = Pattern.compile(regx).split(cond);
			String ar[] = parseSplittedString(splittedStr);
			// RuleComponent.logger.log(0,"ar.length = " +ar.length);
			boolean isConditionalElement = false;
			
			// iterate on each condition of the expression
			for (int j=0; j<ar.length; j++) {
				// RuleComponent.logger.log(0,"ar = " +ar[j]);
				
				isJoinNodeCreated = false;
				isConditionalElement = false;
				
				ConditionConstraints conditionConstraint = getCondition(ar[j], condHash);
				// if condition is null then there can be three cases - AND / OR / Expression node
				if (null == conditionConstraint) {
					if (!ar[j].equals("") && ar[j].length() > 0) {
						if	(ar[j].indexOf(ConditionConstants.ANDCONDITION) >= 0 ) { 
							condElement = ConditionConstants.ANDCONDITION;
							isConditionalElement = true;
						}
						else  if (ar[j].indexOf(ConditionConstants.ORCONDITION) >= 0 ) {
							condElement = ConditionConstants.ORCONDITION;
							isConditionalElement = true;
						}
						else {
							// this can be an expression in itself
							node = (INode)expHash.get(ar[j].trim());
						}
					}
				}				
				else // when condition constraint is not null 
				{	
					node = new ConditionIdentifier(conditionConstraint.getObjectTypeName(), conditionConstraint);	
				} 
				
				//create a join node				
				if (null != node && null != leftNode && null == jn && !isConditionalElement) {
					joinNode = createJoinNode(leftNode, node, condElement);
					jn = joinNode;
					isJoinNodeCreated = true;
				}
				else {
					// a join node has already been created, add subsequent join nodes
					if (null != node && null != jn && !isConditionalElement) {
						joinNode = createJoinNode(jn, node, condElement);
						jn = joinNode;
						isJoinNodeCreated = true;
					} 
				}
				
				if (!isConditionalElement)
					leftNode = node;
				
			} // end of ar[] - split
			
			// saves the expression node in an expression
			if (!isJoinNodeCreated) {
				expHash.put(exp, leftNode);
			}
			else {
				expHash.put(exp, joinNode); // add the join node to expression hash					
			}
			
		} // end of for-expressionHash
		
		// when only one node is present in a rule 
		if (null == joinNode) {
			joinNode = createJoinNode(node, node, null);
		}
		
		// add the terminal node for the rule
		TerminalNode terminalNode = new TerminalNode(ruleClass);
		joinNode.addChildNode(terminalNode);
		key = joinNode.getKey();
		populateBetaManagerList(key.getJoin1(), key.getJoin2(), joinNode);
	}
	
	/**
	 * Creates a join node between 2 conditions and a conditional element
	 * 
	 * @param leftInput
	 * @param rightInput
	 * @param condElement
	 * @return
	 */
	private JoinNode createJoinNode(INode leftInput, INode rightInput, String condElement) {
		JoinNode joinNode = new JoinNode(leftInput, rightInput, condElement);
		String condString1, condString2;
		ResolutorKey tempKey;
		if (leftInput instanceof JoinNode) {
			tempKey = ((JoinNode)leftInput).getKey();
			condString1 = tempKey.getJoin1() + "$" + tempKey.getJoin2();
			((JoinNode)leftInput).addChildNode(joinNode);
		}
		else {
			condString1 = ((ConditionIdentifier)leftInput).getCondition().getConditionString();
		}
		if (rightInput instanceof JoinNode) {
			tempKey = ((JoinNode)rightInput).getKey();
			condString2 = tempKey.getJoin1() + "$" + tempKey.getJoin2();
			((JoinNode)rightInput).addChildNode(joinNode);
		}
		else {
			condString2 = ((ConditionIdentifier)rightInput).getCondition().getConditionString();
		}		
		ResolutorKey key = new ResolutorKey(condString1, condString2);
		// RuleComponent.logger.log(0,"condString1 = " +condString1);
		// RuleComponent.logger.log(0,"condString2 = " +condString2);
		// RuleComponent.logger.log(0,"condElement = " +condElement);
		populateBetaManagerList(condString1, condString2, joinNode);
		joinNode.setKey(key);
		return joinNode;
	}
	
	/**
	 * This parses the already splitted string in case of blank it replaces it by OR
	 * 
	 * @param splittedStr
	 * @return
	 */
	private static String[] parseSplittedString(String[] splittedStr) {
		String[] str = new String[splittedStr.length];
		for(int i=0, j=0; i<splittedStr.length; i++,j++) {
			if (splittedStr[i].equals("")) {
				str[j] = "||";
			}
			else {
				str[j] = splittedStr[i];
			}
		}
		return str;
	}
	

	/**
	 * Gets the condition constraint of the already present condition
	 * 
	 * @param str
	 * @param condHash
	 * @return
	 */
	private ConditionConstraints getCondition(String str, Hashtable<String, ConditionConstraints> condHash) {
		// RuleComponent.logger.log(0,"str = " + str);		
		int index = str.indexOf(".");
		if (!(index < 0)) {
			String className = RuleComponentUtil.getClassName(str.substring(0, str.indexOf(".")));
			// RuleComponent.logger.log(0,"className = " + className);
			String condStr = RuleComponentUtil.replaceStringForObjectName(str, className);
			// RuleComponent.logger.log(0,"condStr = " + condStr);
			ConditionConstraints cons = (ConditionConstraints)condHash.get(condStr);
			return cons;
		}
		return null;
		
	}
	
	/**
	 * Populates beta node manager with the given condition string
	 * 
	 * @param condString1
	 * @param condString2
	 * @param joinNode
	 */
	private void populateBetaManagerList(String condString1, String condString2, JoinNode joinNode) {
		BetaIdentifier betaIdentifier = null;	
		
		if (null != joinNode.getBinder() && joinNode.getBinder().equals(ConditionConstants.ORCONDITION)) {
			if (null != condString1 && null != condString2) {
				if (null != betaManager.getBetaIdentifierList()) {
					betaIdentifier = new BetaIdentifier(condString1, joinNode);
					betaManager.addBetaIdentifierList(condString1, betaIdentifier);
				
					betaIdentifier = new BetaIdentifier(condString2, joinNode);
					betaManager.addBetaIdentifierList(condString2, betaIdentifier);
				}
			}
		}
		if (null == joinNode.getBinder() || null != joinNode.getBinder() && joinNode.getBinder().equals(ConditionConstants.ANDCONDITION)) {
			if (null != condString1 && null != condString2) {
				if (null != betaManager.getBetaIdentifierList()) {
					betaIdentifier = new BetaIdentifier(condString2, joinNode);
					betaManager.addBetaIdentifierList(condString1, betaIdentifier);
				
					betaIdentifier = new BetaIdentifier(condString1, joinNode);
					betaManager.addBetaIdentifierList(condString2, betaIdentifier);
				}
			}
		}
	}

	public static BetaNodeManager getBetaManager() {
		return betaManager;
	}


}
