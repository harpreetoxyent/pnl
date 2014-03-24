package com.oxymedical.component.rulesComponent.rete.productionMemory.builder.alphaBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

import com.oxymedical.component.rulesComponent.RuleComponent;
import com.oxymedical.component.rulesComponent.constants.ConditionConstants;
import com.oxymedical.component.rulesComponent.rete.productionMemory.node.AlphaNode;
import com.oxymedical.component.rulesComponent.rete.productionMemory.node.ObjectTypeNode;
import com.oxymedical.component.rulesComponent.rete.productionMemory.node.ReteNode;
import com.oxymedical.component.rulesComponent.util.RuleComponentUtil;


/**
 * Helper class that parses the condition string and creates a condition constraint object
 * 
 * @author Oxyent Medical
 *
 */
public class AlphaBuilderUtil {
	
	String delimitter = "";
	private static ClassLoader classLoader;
	
	public AlphaBuilderUtil(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}
	
	public String getDelimitter() {
		return delimitter;
	}

	/**
	 * Gets the list of conditions from the hashtable
	 * 
	 * @param expHash
	 * @return
	 */
	public List<String> getConditionList(Hashtable expHash) {
		//Regex regx = new Regex("shells");
		String regx = "[||, &&, \\S]";
		List<String> conditionList = null;
		String temp = "";
		if (null != expHash) {
			conditionList = new ArrayList<String>();
			for(int i=0, k=1; i < expHash.size(); i++, k++) {			
				String condition = (String)expHash.get("e"+k);
				condition = RuleComponentUtil.truncateExtraSpace(condition);
				condition = RuleComponentUtil.addExtraSpaceForOperator(condition);
				String ar[] = Pattern.compile(regx).split(condition);
				//String ar[] = pattern.split(condition);
				//String ar[] = condition.split(regx);
				int j=0;		
				while( j < ar.length)
				{				 
					temp = ar[j];
					if (temp.length() > 0 && !temp.equals(ConditionConstants.ANDCONDITION) && !checkContains(temp, expHash)) {
						conditionList.add(temp);
						// RuleComponent.logger.log(0,"temp = " + temp);
					}
					j++;
				}
			}
		}
		return conditionList;
	}
	
	/**
	 * Checks if the hashtable contains the condition string
	 * This is done to ensure the expression string is not added as part of condition list
	 * 
	 * @param condStr
	 * @param expHash
	 * @return
	 */
	private boolean checkContains(String condStr, Hashtable expHash) {
		boolean isPresent = false;
		if (null != expHash.get(condStr.trim()))
			isPresent = true;
		return isPresent;
	}
	
	/**
	 * This parses the condition string and creats a ConditionConstraint object
	 * 
	 * @param conditionString
	 * @return
	 */
	public ConditionConstraints parseConditionString(String conditionString, ReteNode reteNode) {
		// condition can have any of the evaluator
		ConditionConstraints conditionConstraint = null;
		 RuleComponent.logger.log(0,"conditionString = " +conditionString);
		try 
		{
			 RuleComponent.logger.log(0,"before split" + conditionString);
			String[] splitCondition = splitStringOnTokens(conditionString);
			 RuleComponent.logger.log(0,"after split" + splitCondition);
			if (splitCondition.length > 0) 
			{
				if(splitCondition.length > 1)
				{
					String condition = splitCondition[0];
					String conditionValue = splitCondition[1];
					String constraintType = null;
					Class dataType = null;
					if (condition.indexOf(".") != 0 ) 
					{
						String[] attribute = splitStringByDot(condition);
						if (attribute.length > 0 ) 
						{
							String className = RuleComponentUtil.getClassName(attribute[0]);
							 RuleComponent.logger.log(0,"conditionString:" + conditionString);
							conditionString = RuleComponentUtil.replaceStringForObjectName(conditionString, className);
							if (null != className) 
							{
								Class clazz = classLoader.loadClass(className);
								
								int i = attribute.length - 1;
								if (attribute[i].indexOf('(') > 0 && attribute[i].indexOf(')') > 0) 
								{
									// method 
									 RuleComponent.logger.log(0,"method name = " +attribute[i]);
									constraintType = ConditionConstants.METHOD;
									attribute[i] = attribute[i].replace("(", "");
									attribute[i] = attribute[i].replace(")", "");
			
									// TODO remove assumption that if it is nested methods, then last method would return String
									if (attribute.length > 2) dataType = Class.forName("java.lang.String");
									else
									{
										Method method = clazz.getMethod(attribute[i].trim(), null);
										dataType = method.getReturnType();
									}
									attribute[i] = attribute[i].substring(3);
									char startChar = attribute[i].charAt(0);
									char[] lowerStartChar = (startChar+"").toLowerCase().toCharArray();
									attribute[i] = attribute[i].replace(startChar, lowerStartChar[0]);									
								}
								else 
								{
									// property
									constraintType = ConditionConstants.FIELD;
									Field field = clazz.getField(attribute[1].trim());
									dataType = field.getType();
								}	
								conditionConstraint = getConditionAlreadyPresent(className, conditionString, reteNode); 
								if (null == conditionConstraint) { // attribute[1], constraintType, conditionValue, delimitter, dataType
									conditionConstraint =  new ConditionConstraints();								
									conditionConstraint.setNameOfCondition(attribute[i]);
									conditionConstraint.setConstraintType(constraintType);
									conditionConstraint.setDataType(dataType);
									conditionConstraint.setOperatorType(delimitter);
									conditionConstraint.setValue(conditionValue);
									conditionConstraint.setObjectTypeName(className);
									conditionConstraint.setConditionString(conditionString);
								} 
								else {
									 RuleComponent.logger.log(0,"condition already exist");								
								}
								
							} // end of className = null
							
						}	// end of if - attribute			
					} // end of if condition
				} else {
					String condition = splitCondition[0];
					 RuleComponent.logger.log(0,"condition = " +condition);
					String constraintType = null;
					Class dataType = null;
					if (condition.indexOf(".") != 0 ) {					
						String[] attribute = splitStringByDot(condition);
						if (attribute.length > 0 ) {
							String className = RuleComponentUtil.getClassName(attribute[0]);
							// RuleComponent.logger.log(0,"conditionString:" + conditionString);
							conditionString = RuleComponentUtil.replaceStringForObjectName(conditionString, className);
							if (null != className) {
								Class clazz = classLoader.loadClass(className);						
																
									// method 
									// RuleComponent.logger.log(0,"method name = " +attribute[1]);
									constraintType = ConditionConstants.METHOD;
									String methodName = attribute[1].substring(0, attribute[1].indexOf('('));
									
									
									int indexOfOpenBrac = attribute[1].indexOf('(');
									int indexOfCloseBrac = attribute[1].indexOf(')');
									String param = null;
									Class paramType = null;
									
									//for setter
									if(!(indexOfCloseBrac == indexOfOpenBrac+1)){
										param = attribute[1].substring(indexOfOpenBrac+1,indexOfCloseBrac-1);
										paramType = param.getClass();
										// RuleComponent.logger.log(0,"Parameter = " + paramType);
										Method method = clazz.getMethod(methodName, paramType);
										dataType = paramType;
									}else {//for getter
										Method method = clazz.getMethod(methodName, null);
										dataType = method.getReturnType();
									}
																											
								conditionConstraint = getConditionAlreadyPresent(className, conditionString, reteNode); 
								if (null == conditionConstraint) { // attribute[1], constraintType, conditionValue, delimitter, dataType
									conditionConstraint =  new ConditionConstraints();								
									conditionConstraint.setNameOfCondition(methodName.substring(3));
									conditionConstraint.setConstraintType(constraintType);
									conditionConstraint.setDataType(dataType);
									conditionConstraint.setOperatorType("=");
									conditionConstraint.setValue(param);
									conditionConstraint.setObjectTypeName(className);
									conditionConstraint.setConditionString(conditionString);
								} 
								else {
									 RuleComponent.logger.log(0,"condition already exist");								
								}
								
							} // end of className = null
							
						}	// end of if - attribute			
					} // end of if condition
				}
			}
		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}catch(NoSuchFieldException nsfe) {
			nsfe.printStackTrace();
		}catch(NoSuchMethodException nsme) {
			nsme.printStackTrace();
		}	
		return conditionConstraint;
	}
	
	/**
	 * Splits the string on the list of tokens specified 
	 * 
	 * returns an array of splitted string and the delimitter
	 * 
	 * @param conditionString
	 * @param delimitter
	 * @return
	 */
	private String[] splitStringOnTokens(String conditionString) {
		if (null == conditionString) 
			return null;
		
		//String REGEX = "[=,==,<,>,<=,>=,!,!=]";
		
		
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
		/* RuleComponent.logger.log(0,"string[2]" + idenf[2]);
		 RuleComponent.logger.log(0,"conditionString" + conditionString);
		 RuleComponent.logger.log(0,"delimiter " + delimitter);*/
		//try
		/*Tokenizer tokenizer = new Tokenizer(conditionString , RulesConstants.REGEX, true);
		
		int i = 0;
	    for (; tokenizer.hasNext(); ) 
	    {	        
            if(!(tokenizer.isNextToken()))
            {
            	idenf[i] = (String)tokenizer.next();
            	 RuleComponent.logger.log(0,"identifier = "+i+" " + idenf[i]);
    	        i++;
            	
            }
            else
            {
            	delimitter = (String)tokenizer.next();
            	 RuleComponent.logger.log(0,"token = " + delimitter);            	
            }
	    }*/	
		return idenf;
	}
	
	/**
	 * Splits the string by dot
	 * 
	 * @param st
	 * @return
	 */
	private String[] splitStringByDot(String st) {
		Pattern p = Pattern.compile(".", Pattern.LITERAL);
		String[] stringAfterSplit = p.split(st);
		return stringAfterSplit;
	}
	
	/**
	 * The method gets the conditionconstraint if present from the list of conditions
	 * 
	 * @param className
	 * @param conditionString
	 * @return
	 */
	private ConditionConstraints getConditionAlreadyPresent(String className, String conditionString, ReteNode reteNode) {
		//String className, String attributeName, String constraintType, String value, String operator, String dataType
		ObjectTypeNode objectTypeNode = reteNode.getObjectTypeList().get(className);
		AlphaNode alphaNode = objectTypeNode.getAlphaNode();		
		Hashtable conditionConstraintTable = alphaNode.getConditionConstraintTable();
		
		for(int i=0, j=1; i <  conditionConstraintTable.size(); i++, j++) {
			ConditionConstraints cond = (ConditionConstraints)conditionConstraintTable.get(new Integer(j));				
			if (null != cond) {
				// RuleComponent.logger.log(0,"cond = " + cond.getConditionString());
				if (cond.getConditionString().equalsIgnoreCase(conditionString)) {
					return cond;
				}
			}
		}
		return null;
	}
	
}
