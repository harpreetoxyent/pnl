package com.oxymedical.component.rulesComponent.workingMemory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Pattern;

import com.oxymedical.component.rulesComponent.IAction;
import com.oxymedical.component.rulesComponent.IEvaluator;
import com.oxymedical.component.rulesComponent.IFactHandle;
import com.oxymedical.component.rulesComponent.INode;
import com.oxymedical.component.rulesComponent.IRuleBase;
import com.oxymedical.component.rulesComponent.IRuleClass;
import com.oxymedical.component.rulesComponent.IWorkingMemory;
import com.oxymedical.component.rulesComponent.RuleComponent;
import com.oxymedical.component.rulesComponent.constants.ConditionConstants;
import com.oxymedical.component.rulesComponent.constants.EvaluatorConstants;
import com.oxymedical.component.rulesComponent.constants.RulesConstants;
import com.oxymedical.component.rulesComponent.exception.RuleComponentException;
import com.oxymedical.component.rulesComponent.rete.evaluator.BooleanEvaluator;
import com.oxymedical.component.rulesComponent.rete.evaluator.ByteEvaluator;
import com.oxymedical.component.rulesComponent.rete.evaluator.DateEvaluator;
import com.oxymedical.component.rulesComponent.rete.evaluator.DoubleEvaluator;
import com.oxymedical.component.rulesComponent.rete.evaluator.FloatEvaluator;
import com.oxymedical.component.rulesComponent.rete.evaluator.IntegerEvaluator;
import com.oxymedical.component.rulesComponent.rete.evaluator.LongEvaluator;
import com.oxymedical.component.rulesComponent.rete.evaluator.ObjectEvaluator;
import com.oxymedical.component.rulesComponent.rete.evaluator.PrimitiveBooleanEvaluator;
import com.oxymedical.component.rulesComponent.rete.evaluator.PrimitiveByteEvaluator;
import com.oxymedical.component.rulesComponent.rete.evaluator.PrimitiveDoubleEvaluator;
import com.oxymedical.component.rulesComponent.rete.evaluator.PrimitiveFloatEvaluator;
import com.oxymedical.component.rulesComponent.rete.evaluator.PrimitiveIntEvaluator;
import com.oxymedical.component.rulesComponent.rete.evaluator.PrimitiveLongEvaluator;
import com.oxymedical.component.rulesComponent.rete.evaluator.PrimitiveShortEvaluator;
import com.oxymedical.component.rulesComponent.rete.evaluator.ShortEvaluator;
import com.oxymedical.component.rulesComponent.rete.evaluator.StringEvaluator;
import com.oxymedical.component.rulesComponent.rete.productionMemory.FactHandle;
import com.oxymedical.component.rulesComponent.rete.productionMemory.Rete;
import com.oxymedical.component.rulesComponent.rete.productionMemory.builder.alphaBuilder.ConditionConstraints;
import com.oxymedical.component.rulesComponent.rete.productionMemory.builder.alphaBuilder.ConditionIdentifier;
import com.oxymedical.component.rulesComponent.rete.productionMemory.builder.betaBuilder.BetaNodeManager;
import com.oxymedical.component.rulesComponent.rete.productionMemory.node.AlphaNode;
import com.oxymedical.component.rulesComponent.rete.productionMemory.node.JoinNode;
import com.oxymedical.component.rulesComponent.rete.productionMemory.node.ObjectTypeNode;
import com.oxymedical.component.rulesComponent.rete.productionMemory.node.TerminalNode;
import com.oxymedical.component.rulesComponent.rule.elements.Consequence;


/**
 * Working memory that matches the fact with the rete tree
 * 
 * @author Oxyent Medical
 *
 */
public class ReteWorkingMemory implements IWorkingMemory {
	
	Action action;
	
	BetaNodeManager betaNodeManager;
	
	IRuleBase ruleBase;
	
	Rete rete;	
	
	static List<String> participantConditionsList;
	 
	
	/**
	 * @param ruleBase
	 */
	public ReteWorkingMemory(IRuleBase ruleBase) {
		this.ruleBase = ruleBase;
		this.betaNodeManager = ruleBase.getBetaManager();
		rete = ruleBase.getRete();
		action = new Action();
		participantConditionsList = new ArrayList<String>();
	}
	
	public Action getAction() {
		return this.action;
	}
	
	/**
	 * Asserts the number of facts in working memory
	 * 
	 * @param ob[]
	 */
	public void assertObject(Object[] ob) throws RuleComponentException {
		for(int i=0; i <ob.length; i++) {
			assertObject(ob[i]);
		}
		List list = validateBetaCondition();
		// RuleComponent.logger.log(0,"list.size = " +list.size());
		populateActionList(list);
	}

	/**
	 * Asserts an object in working memory, finds the matching conditions and 
	 * check if the rule corresponding to the condition is present
	 * 
	 * @param ob
	 */
	public void assertObject(Object ob) throws RuleComponentException 
	{
		FactHandle factHandle = new FactHandle();
		factHandle.setObject(ob);
		ObjectTypeNode node = getObjectTypeNode(ob);
		if (null != node)
		{
			checkCondition(node.getAlphaNode(), ob);
		}
		else
		{
			 RuleComponent.logger.log(0,ob + " type object type node doen't exist");
		}
	}

	/**
	 * Get the object type node of the object passed
	 * 
	 * @param ob
	 * @return
	 * @throws RuleComponentException
	 */
	private ObjectTypeNode getObjectTypeNode(Object ob) throws RuleComponentException {		
		HashMap<String, ObjectTypeNode> objectTypeNodeList = rete.getReteNode().getObjectTypeList();
		String className = ob.getClass().getName();
		return objectTypeNodeList.get(className);
	}

	/**
	 * populates the action list for the rules to be executed
	 * @param list
	 */
	private void populateActionList(List list) {
		INode node = null;
		for(Iterator it = list.iterator(); it.hasNext();) {
			JoinNode joinNode = (JoinNode)it.next();
			node = joinNode.getChildNode();
			if (node instanceof TerminalNode) {
				TerminalNode terminalNode = (TerminalNode)node;
				if (!action.getTerminalNodeList().contains(terminalNode))
					action.addTerminalNode(terminalNode);
			}	
			else {
				JoinNode jn;
				boolean isRuleTrue = false;
				while (node instanceof JoinNode) {
					jn = (JoinNode)node;
					if (jn.getBinder().equals(ConditionConstants.ANDCONDITION)) {
						isRuleTrue = validate(jn.getRightInput(), node) && validate(jn.getLeftInput(), node);
						if (isRuleTrue) 
							node = jn.getChildNode();
						else 
							break;						
					}
					else {
						node = jn.getChildNode();
						isRuleTrue = true;						
					}
					
				}
				if (isRuleTrue) {
					if (node instanceof TerminalNode) {
						TerminalNode terminalNode = (TerminalNode)node;
						if (!action.getTerminalNodeList().contains(terminalNode))
							action.addTerminalNode(terminalNode);
					}
				}
			}
		
		}
		
	}
	
	/**
	 * Method validates the 
	 * @param inputNode
	 * @param node
	 * @return
	 */
	private boolean validate(INode inputNode, INode node) {
		ConditionIdentifier condIdef;
		boolean isRuleTrue = false;
		if (inputNode instanceof ConditionIdentifier) {
			condIdef = (ConditionIdentifier)inputNode;
			if (this.participantConditionsList.contains(condIdef.getCondition().getConditionString())) {
				isRuleTrue = true;
			}
			else {
				isRuleTrue = false;
			}
		}
		else {							
			isRuleTrue = validateJoinNodeCondition(inputNode);
		}
		return isRuleTrue;
	}
	
	/**
	 * Validates join node condition
	 * 
	 * @param node
	 * @return
	 */
	private boolean validateJoinNodeCondition(INode node) {
		boolean isRuleTrue = false;
		ConditionIdentifier condIdef;
		JoinNode jn;
		
		if (node instanceof JoinNode){
			jn = (JoinNode)node;
			if (jn.getBinder().equals(ConditionConstants.ANDCONDITION))
				isRuleTrue =  validateJoinNodeCondition(jn.getLeftInput()) && validateJoinNodeCondition(jn.getRightInput());
			else
				isRuleTrue = validateJoinNodeCondition(jn.getLeftInput()) || validateJoinNodeCondition(jn.getRightInput());
		}
		else {
			if (node instanceof ConditionIdentifier) {
				condIdef = (ConditionIdentifier)node;
				if (this.participantConditionsList.contains(condIdef.getCondition().getConditionString())) {
					isRuleTrue = true;
				}
				else {
					isRuleTrue = false;					
				}
			}
		}
		return isRuleTrue;
	}
	
	/**
	 * Checks the facts againts the rete
	 * 
	 * @param alphaNode
	 * @param ob
	 */
	private void checkCondition(AlphaNode alphaNode, Object ob) throws RuleComponentException
	{
		Class clazz = ob.getClass();
		Field[] fields = clazz.getFields();
		Method[] methods = clazz.getMethods();
		String methodName, nameOfCondition;
		Object value;
		boolean result;
		try 
		{
			// For methods
			Iterator <ConditionConstraints> constraintsIter = alphaNode.getConditionConstraintTable().values().iterator();
			while (constraintsIter.hasNext())
			{
				ConditionConstraints constraints = constraintsIter.next();
				String condition = constraints.getConditionString();
				condition = splitStringOnTokens(condition)[0];
				condition = condition.replaceAll(clazz.getName(), "");
				
				String[] condArr = splitStringByDot(condition);
				nameOfCondition = condArr[condArr.length - 1];
				if (nameOfCondition.indexOf(RulesConstants.GET)==0)
				{
					nameOfCondition = nameOfCondition.substring(RulesConstants.GET.length());
					nameOfCondition = changeFirstLower(nameOfCondition);
					if (nameOfCondition.indexOf('(') > 0 && nameOfCondition.indexOf(')') > 0) 
					{
						nameOfCondition = nameOfCondition.replace("(", "");
						nameOfCondition = nameOfCondition.replace(")", "");
					}
					//  RuleComponent.logger.log(0,"[nameOfCondition]".concat(nameOfCondition));
				}
				
				condArr[0] = "";
				value = evaluateMethod(condArr, clazz, ob);
				
				// check if fact already present
				result = checkIfFactAlreadyPresent(nameOfCondition, value, alphaNode);
				
				if (!result)
					validateAlphaCondition(nameOfCondition, value, alphaNode);
			}

			
			// For fields
			for(int i = 0; i<fields.length; i++) 
			{
				nameOfCondition = fields[i].getName().trim();
				Class dataType = fields[i].getType();
				if(dataType.toString().equals(EvaluatorConstants.PRIMITIVE_BOOLEAN_TYPE)){
					methodName = RulesConstants.IS + changeFirstUpper(fields[i].getName());
					try{
						value = (Object)clazz.getDeclaredMethod(methodName, null).invoke(ob, null);
					}catch (Exception e) {
						methodName = RulesConstants.GET + changeFirstUpper(fields[i].getName());
						value = (Object)clazz.getDeclaredMethod(methodName, null).invoke(ob, null);
					}
//					 RuleComponent.logger.log(0,"value = " +value);
				}
				else{
					methodName = RulesConstants.GET + changeFirstUpper(fields[i].getName());
					value = (Object)clazz.getDeclaredMethod(methodName, null).invoke(ob, null);	
				}				
				
				// check if fact already present
				result = checkIfFactAlreadyPresent(nameOfCondition, value, alphaNode);
				
				if (!result)
					validateAlphaCondition(nameOfCondition, value, alphaNode);				
				
			}
		}catch(NoSuchMethodException nsme) {
			throw new RuleComponentException(nsme.getMessage());
		}catch(IllegalAccessException iae) {
			throw new RuleComponentException(iae.getMessage());
		}catch(InvocationTargetException ite) {
			throw new RuleComponentException(ite.getMessage());
		}catch(RuleComponentException rce) {
			throw rce;
		}		
	}

	
	private Object evaluateMethod(String[] methods, Class clazz, Object ob) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		try
		{
			Object value = ob;
			for (int i = 0; i < methods.length; i++)
			{
				if (!methods[i].equals(""))
				{
					String methodName = methods[i];
					methods[i] = "";
					if (methodName.indexOf('(') > 0 && methodName.indexOf(')') > 0) 
					{
						methodName = methodName.replace("(", "");
						methodName = methodName.replace(")", "");
					}
					value = (Object)clazz.getDeclaredMethod(methodName, null).invoke(ob, null);
					if (value == null) return null; 
					value = evaluateMethod(methods, value.getClass(), value);
				}
			}
			return value;
		}
		catch(NoSuchMethodException nsme) 
		{
			return null;
		}
	}
	
	private String[] splitStringByDot(String st) 
	{
		Pattern p = Pattern.compile(".", Pattern.LITERAL);
		String[] stringAfterSplit = p.split(st);
		return stringAfterSplit;
	}
	
	private String[] splitStringOnTokens(String conditionString) 
	{
		if (null == conditionString) 
			return null;
		
		//String REGEX = "[=,==,<,>,<=,>=,!,!=]";
		String delimitter = "";;
		
		String[] idenf = null;
		boolean condIsMethod = true;
		//TODO implement using Pattern
		
		for(int i=0; i<conditionString.length() ; i++){
			if(conditionString.charAt(i)== '=' || conditionString.charAt(i)=='<' || conditionString.charAt(i)== '>' || conditionString.charAt(i)=='!'){
				condIsMethod = false;
				idenf = new String[2];
				int nextDelim = i + 1;
				if(conditionString.charAt(nextDelim) == '='){
					idenf[0] = conditionString.substring(0, i);
					idenf[1]= conditionString.substring(nextDelim+1);
					delimitter = "" + conditionString.charAt(i) + conditionString.charAt(nextDelim);
					break;
				}else {
					idenf[0] = conditionString.substring(0, i);
					idenf[1]= conditionString.substring(i+1);
					delimitter = "" + conditionString.charAt(i);
					break;
				}
			} 
		}
		if(condIsMethod){
			idenf = new String[1];
			idenf[0] = conditionString;
		}
		return idenf;
	}
	
	
	/**
	 * Validates the beta condition
	 * 
	 * @return
	 */
	private List validateBetaCondition() {
		// RuleComponent.logger.log(0,"participantConditionsList size = " +participantConditionsList.size());		
		return betaNodeManager.getMatchingRules(this.participantConditionsList);
	}

	/**
	 * Validates the condition with the facts
	 * 
	 * @param ob
	 * @param condConstraint
	 */
	private void validateAlphaCondition(String noc, Object value, AlphaNode alphaNode) throws RuleComponentException {
		String operator, type, condValue;		
		Class dataType;
		boolean result;
		Hashtable conditionConstraintList = alphaNode.getConditionConstraintTable();
		try {
			for(int i=0, j=1; i < conditionConstraintList.size(); i++, j++) 
			{
				ConditionConstraints condConstraint = (ConditionConstraints)conditionConstraintList.get(new Integer(j));
				if ((condConstraint.getNameOfCondition().trim()).equalsIgnoreCase(noc)) 
				{
					operator = condConstraint.getOperatorType();
					type = condConstraint.getConstraintType();
					dataType = condConstraint.getDataType();
					condValue = condConstraint.getValue();
					// RuleComponent.logger.log(0,"inside validate alpha condition for noc = " +noc + " value = " +condValue);	
					
//					 RuleComponent.logger.log(0,"[ReteWorkingMemory][checkCondition][value]".concat(value == null ? "NULL" : value.toString()));
//					 RuleComponent.logger.log(0,"[ReteWorkingMemory][checkCondition][condValue]".concat(condValue == null ? "NULL" : condValue.toString()));
//					 RuleComponent.logger.log(0,"[ReteWorkingMemory][checkCondition][dataType]".concat(dataType == null ? "NULL" : dataType.getName()));
//					 RuleComponent.logger.log(0,"[ReteWorkingMemory][checkCondition][operator]".concat(operator == null ? "NULL" : operator.toString()));
					result = isConditionSatisfied(dataType, value, condValue, operator);
					
					if (result) {
						participantConditionsList.add(condConstraint.getConditionString());
						// RuleComponent.logger.log(0,"name of condition = " + condConstraint.getNameOfCondition() + " value = " + condValue);
						alphaNode.addToFactsList(new Fact(condConstraint.getNameOfCondition().trim(), condValue, condConstraint.getConditionString()));
					}				
				}
			}
		} catch (Exception e) {			
			e.printStackTrace();
			throw new RuleComponentException(e.getMessage());
		}
	}
	

	/**
	 * Verifies if the fact
	 * @param noc
	 * @param value
	 * @param alphaNode
	 * @return
	 */
	private boolean checkIfFactAlreadyPresent(String noc, Object value, AlphaNode alphaNode) {
		Fact inComingfact = new Fact(noc, value, "");
		List<Fact> factsList = alphaNode.getFactsList();
		boolean result = false;
		for(Iterator it = factsList.iterator(); it.hasNext();) {
			Fact fact = (Fact)it.next();
			if (fact.equals(inComingfact)) {
				this.participantConditionsList.add(fact.getConditionString());
				result = true;
			}
		}
		return result;
	}

	/**
	 * Executes all the rules matched.
	 * the execution of rules happen based on salience value
	 * 
	 */
	public List<IRuleClass> executeRules() 
	{
		List<IRuleClass> ruleList = new ArrayList<IRuleClass>();
		for(Iterator it = this.action.getTerminalNodeList().iterator(); it.hasNext(); ) 
		{
			TerminalNode terminalNode = (TerminalNode)it.next();			
			// RuleComponent.logger.log(0,terminalNode.getRuleClass());
			// RuleComponent.logger.log(0,"**** url ****  =  "+terminalNode.getRuleClass().getUrl());			
			ruleList.add((IRuleClass)terminalNode.getRuleClass());
		}
		List<String> conseqResultList = new ArrayList<String>();
		Hashtable consequenceRulesTable = this.action.getConsequenceTable();
		//Set keySet = consequenceRulesTable.keySet();		
		TreeSet sortedKeys = new TreeSet(new ReverseComparator());
		sortedKeys.addAll(consequenceRulesTable.keySet());
		
		List ruleClassList = null, conseqList = null;
		Consequence conseq = null;
		String conseqStr = null;
		for(Iterator it = sortedKeys.iterator(); it.hasNext(); ) 
		{
			Integer key = (Integer)it.next();
			// RuleComponent.logger.log(0,"key = " +key);
			ruleClassList = (ArrayList)consequenceRulesTable.get(key);
			// execute all the rule class for the current salience
			for(Iterator it1 = ruleClassList.iterator(); it1.hasNext();){
				IRuleClass ruleClass = (IRuleClass)it1.next();
				ruleClass.consequence();
				conseqList = ruleClass.getConsequenceList();				
				for(Iterator conseqItr = conseqList.iterator(); conseqItr.hasNext();) {
					conseq = (Consequence)conseqItr.next();
					conseqStr = conseq.getConsequenceString();
					conseqStr = conseqStr.replaceAll("\'", "\""); 
					//conseqResultList.add(new String(conseqStr));
				}
			}
		}
		return ruleList;
	}
	
	/**
	 * Changes the first letter of the alphabet to caps
	 * 
	 * @param st
	 * @return
	 */
	private String changeFirstUpper(String st) {
		String subStr = st.substring(0, 1);
		String subStr1 = subStr.toUpperCase();
		String subStr2 = (st.substring(1, st.length()));
		return subStr1+subStr2;
	}

	private String changeFirstLower(String st) {
		String subStr = st.substring(0, 1);
		String subStr1 = subStr.toLowerCase();
		String subStr2 = (st.substring(1, st.length()));
		return subStr1+subStr2;
	}

	public IFactHandle fireAllRules() {
		// TODO Auto-generated method stub
		return null;
	}

	public IAction getAgenda() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * modifies the fact present in rete tree
	 * Modify happens in 2 step : retract and assert
	 * 
	 * @param noc
	 * @param value
	 * @param alphaNode
	 * @return
	 */
	public IFactHandle modifyObject(Object ob) throws RuleComponentException {
		ObjectTypeNode node = getObjectTypeNode(ob);
		if (null != node) 
		{
			AlphaNode alphaNode = node.getAlphaNode();
			List l1 = alphaNode.getFactsList();
			for(Iterator it = l1.iterator(); it.hasNext(); ) {
				Fact fact = (Fact)it.next();
				// RuleComponent.logger.log(0,"fact, noc = " +fact.getNameOfCondition() + " value = " +fact.getValue());
			}
			retractFactsFromList(alphaNode, ob);
			assertFactInFactList(alphaNode, ob);
			// RuleComponent.logger.log(0,"After modify ********** ");
			List l2 = alphaNode.getFactsList();
			for(Iterator it = l2.iterator(); it.hasNext(); ) {
				Fact fact = (Fact)it.next();
				// RuleComponent.logger.log(0,"fact, noc = " +fact.getNameOfCondition() + " value = " +fact.getValue());
			}
		} 
		else 
		{
			 RuleComponent.logger.log(0,ob + " type object type node doen't exist");
		}		
		return null;
	}
	
	/**
	 * Checks the facts againts the rete
	 * 
	 * @param alphaNode
	 * @param ob
	 */
	private void retractFactsFromList(AlphaNode alphaNode, Object ob) throws RuleComponentException
	{
		Class clazz = ob.getClass();
		Field[] fields = clazz.getFields();
		String methodName, nameOfCondition;
		Object value;
		boolean result;		
		try {
			for(int i = 0; i<fields.length; i++) 
			{
				nameOfCondition = fields[i].getName().trim();
				Class dataType = fields[i].getType();
				if(dataType.toString().equals(EvaluatorConstants.PRIMITIVE_BOOLEAN_TYPE)){
					methodName = RulesConstants.IS + changeFirstUpper(fields[i].getName());
				}
				else{
					methodName = RulesConstants.GET + changeFirstUpper(fields[i].getName());
				}
				value = (Object)clazz.getDeclaredMethod(methodName, null).invoke(ob, null);	
				
				// check if fact already present
				result = retractFact(nameOfCondition, value, alphaNode);
				
				if (result)
					 RuleComponent.logger.log(0,"Removed fact from Rete");				
				
			}			
		}catch(NoSuchMethodException nsme) {
			throw new RuleComponentException(nsme.getMessage());
		}catch(IllegalAccessException iae) {
			throw new RuleComponentException(iae.getMessage());
		}catch(InvocationTargetException ite) {
			throw new RuleComponentException(ite.getMessage());
		}		
	}
	
	/**
	 * Asserts fact in rete
	 * 
	 * TODO the fact added has the condtion string as null, need to check that
	 * 
	 * @param alphaNode
	 * @param ob
	 */
	private void assertFactInFactList(AlphaNode alphaNode, Object ob) throws RuleComponentException
	{
		Class clazz = ob.getClass();
		Field[] fields = clazz.getFields();
		String methodName, nameOfCondition;
		Object value;
		boolean result;		
		try {
			for(int i = 0; i<fields.length; i++) 
			{
				nameOfCondition = fields[i].getName().trim();
				Class dataType = fields[i].getType();
				if(dataType.toString().equals(EvaluatorConstants.PRIMITIVE_BOOLEAN_TYPE)){
					methodName = RulesConstants.IS + changeFirstUpper(fields[i].getName());
				}
				else{
					methodName = RulesConstants.GET + changeFirstUpper(fields[i].getName());
				}
				value = (Object)clazz.getDeclaredMethod(methodName, null).invoke(ob, null);	
				
				alphaNode.addToFactsList(new Fact(nameOfCondition, value, null));
				
			}			
		}catch(NoSuchMethodException nsme) {
			throw new RuleComponentException(nsme.getMessage());
		}catch(IllegalAccessException iae) {
			throw new RuleComponentException(iae.getMessage());
		}catch(InvocationTargetException ite) {
			throw new RuleComponentException(ite.getMessage());
		}		
	}
	
	/**
	 * Removes the fact from rete tree
	 * 
	 * @param noc
	 * @param value
	 * @param alphaNode
	 * @return
	 */
	private boolean retractFact(String noc, Object value, AlphaNode alphaNode) {
		Fact inComingfact = new Fact(noc, value, "");
		List<Fact> factsList = alphaNode.getFactsList();
		for(Iterator it = factsList.iterator(); it.hasNext();) {
			Fact fact = (Fact)it.next();
			if (fact.equals(inComingfact)) {
				factsList.remove(fact);
				return true;
			}
		}	
		return false;
	}

	public IFactHandle retractObject(Object ob) throws RuleComponentException {
		ObjectTypeNode node = getObjectTypeNode(ob);
		if (null != node) 
		{			
			retractFactsFromList(node.getAlphaNode(), ob);			
		} 
		else 
		{
			 RuleComponent.logger.log(0,ob + " type object type node doen't exist");
		}		
		return null;
	}

	/**
	 * Validates if the condiiton matches the facts
	 * 
	 * @param dataType
	 * @param value
	 * @param condValue
	 * @param operator
	 * @return
	 */
	private boolean isConditionSatisfied(Class dataType, Object value, String condValue, String operator) throws Exception{
		boolean result = false;
		IEvaluator evaluator = null;
		if (null == value)
			return false;
		if (null == condValue)
			return false;
		
		if(dataType.getSimpleName().equals(EvaluatorConstants.PRIMITIVE_FLOAT_TYPE))
	    {
	        evaluator = PrimitiveFloatEvaluator.getPrimitiveFloatEvaluator(operator);
	        if(evaluator != null)
	            result = evaluator.evaluate(value, condValue);
	    } 
		else if(dataType.getSimpleName().equals(EvaluatorConstants.PRIMITIVE_INT_TYPE))
	    {
	        evaluator = PrimitiveIntEvaluator.getPrimitiveIntEvaluator(operator);
	        if(evaluator != null)
	            result = evaluator.evaluate(value, condValue);
	    } 
	    else if(dataType.getSimpleName().equals(EvaluatorConstants.PRIMITIVE_LONG_TYPE))
	    {
	        evaluator = PrimitiveLongEvaluator.getPrimitiveLongEvaluator(operator);
	        if(evaluator != null)
	            result = evaluator.evaluate(value, condValue);
	    } 
	    else if(dataType.getSimpleName().equals(EvaluatorConstants.PRIMITIVE_SHORT_TYPE))
	    {
	        evaluator = PrimitiveShortEvaluator.getPrimitiveShortEvaluator(operator);
	        if(evaluator != null)
	            result = evaluator.evaluate(value, condValue);
	    } 
	    else if(dataType.getSimpleName().equals(EvaluatorConstants.PRIMITIVE_BYTE_TYPE))
	    {
	        evaluator = PrimitiveByteEvaluator.getPrimitiveByteEvaluator(operator);
	        if(evaluator != null)
	            result = evaluator.evaluate(value, condValue);
	    } 
	    else if(dataType.getSimpleName().equals(EvaluatorConstants.PRIMITIVE_DOUBLE_TYPE))
	    {
	        evaluator = PrimitiveDoubleEvaluator.getPrimitiveDoubleEvaluator(operator);
	        if(evaluator != null)
	            result = evaluator.evaluate(value, condValue);
	    } 
	    else if(dataType.getSimpleName().equals(EvaluatorConstants.PRIMITIVE_BOOLEAN_TYPE))
	    {
	        evaluator = PrimitiveBooleanEvaluator.getPrimitiveBooleanEvaluator(operator);
	        if(evaluator != null)
	            result = evaluator.evaluate(value, condValue);
	    } 
	    else if (dataType.getSimpleName().equals(EvaluatorConstants.STRING_TYPE)) 
		{
			String tempStr = condValue;
			tempStr = tempStr.replaceAll("'", " ");	
			condValue = tempStr.trim();
			evaluator = StringEvaluator.getStringEvaluator(operator);
			if (null != evaluator)
				result = evaluator.evaluate(value, condValue);
		}
		else if (dataType.getSimpleName().equals(EvaluatorConstants.INTEGER_TYPE)) {	
			evaluator = IntegerEvaluator.getIntegerEvaluator(operator);
			if (null != evaluator)
				result = evaluator.evaluate(value, new Integer(condValue));
		}
		else if (dataType.getSimpleName().equals(EvaluatorConstants.FLOAT_TYPE)) {	
			evaluator = FloatEvaluator.getFloatEvaluator(operator);
			if (null != evaluator)
				result = evaluator.evaluate(value, new Float(condValue));
		}
		else if (dataType.getSimpleName().equals(EvaluatorConstants.BYTE_TYPE)) {
			evaluator = ByteEvaluator.getByteEvaluator(operator);
			if (null != evaluator)
				result = evaluator.evaluate(value, new Byte(condValue));
		}
		else if (dataType.getSimpleName().equals(EvaluatorConstants.LONG_TYPE)) {	
			evaluator = LongEvaluator.getLongEvaluator(operator);
			if (null != evaluator)
				result = evaluator.evaluate(value, new Long(condValue));
		}
		else if (dataType.getSimpleName().equals(EvaluatorConstants.SHORT_TYPE)) {	
			evaluator = ShortEvaluator.getShortEvaluator(operator);
			if (null != evaluator)
				result = evaluator.evaluate(value, new Short(condValue));
		}
		else if (dataType.getSimpleName().equals(EvaluatorConstants.DATE_TYPE)) {	
			evaluator = DateEvaluator.getDateEvaluator(operator);
			condValue = condValue.replaceAll("'","");			
			if (null != evaluator)
				result = evaluator.evaluate(value, EvaluatorConstants.sdf.parse(condValue));
			
		}
		else if (dataType.getSimpleName().equals(EvaluatorConstants.FLOAT_TYPE)) {	
			evaluator = FloatEvaluator.getFloatEvaluator(operator);
			if (null != evaluator)
				result = evaluator.evaluate(value, new Float(condValue));
		}
		else if (dataType.getSimpleName().equals(EvaluatorConstants.DOUBLE_TYPE)) {	
			evaluator = DoubleEvaluator.getDoubleEvaluator(operator);
			if (null != evaluator)
				result = evaluator.evaluate(value, new Double(condValue));
		}
		/*else if (dataType.getSimpleName().equals(EvaluatorConstants.CHAR_TYPE)) {	
			evaluator = CharacterEvaluator.getCharacterEvaluator(operator);
			if (null != evaluator)
				result = evaluator.evaluate(value, new Character(condValue));
		}*/
		else if (dataType.getSimpleName().equals(EvaluatorConstants.OBJECT_TYPE)) {	
			evaluator = new ObjectEvaluator(operator);
			if (null != evaluator)
				result = evaluator.evaluate(value, condValue);
		}
		else if (dataType.getSimpleName().equals(EvaluatorConstants.BOOLEAN_TYPE)) {	
			evaluator = BooleanEvaluator.getBooleanEvaluator(operator);
			if (null != evaluator)
				result = evaluator.evaluate(value, condValue);
		}
		return result;
	}

}


 class ReverseComparator implements Comparator
{
    // "reverse" the value of o1.compareTo(o2)
    public int compare(Object o1, Object o2)
    {
        Comparable c = (Comparable) o1;
        return -1 * c.compareTo(o2);
     }
}

