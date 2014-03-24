package com.oxymedical.component.rulesComponent.rule.builder;

import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.rulesComponent.ICondition;
import com.oxymedical.component.rulesComponent.IConsequence;
import com.oxymedical.component.rulesComponent.IRule;
import com.oxymedical.component.rulesComponent.constants.ConditionConstants;
import com.oxymedical.component.rulesComponent.constants.RulesConstants;
import com.oxymedical.component.rulesComponent.exception.RuleComponentException;
import com.oxymedical.component.rulesComponent.rule.elements.Condition;
import com.oxymedical.component.rulesComponent.rule.elements.Consequence;
import com.oxymedical.component.rulesComponent.rule.elements.RuleSet;
import com.oxymedical.component.rulesComponent.rule.elements.Variables;
import com.oxymedical.component.rulesComponent.util.RuleComponentUtil;


/**
 * This class builds a rule class from the rule set.
 * This is requred to check the validity, syntax and semantics of the rule
 * 
 * @author Oxyent Medical
 *
 */
public class RuleClassBuilder {
	
	final static String lineSeparator = System.getProperty( "line.separator" );
	
	public String buildRuleClass(IRule rule, RuleSet ruleSet) throws RuleComponentException {   
		// the rule name is Rule + the name of the rule  + name of the ruleset, 
		// the name of the rule set is the name of the rule set file
		String ruleClassName = RulesConstants.RULE + rule.getName().replaceAll( "[^\\w$]","_") + ruleSet.getName();
		rule.setRuleClassName(ruleClassName);
		
        final StringBuffer buffer = new StringBuffer();
        //buffer.append( "package com.oxymedical.component.rulesComponent.generatedRules;" + lineSeparator );        

        // the list of imports required to create the rule class
        buffer.append( "import com.oxymedical.component.rulesComponent.IRuleClass;" + lineSeparator );
        buffer.append( "import com.oxymedical.framework.objectbroker.annotations.InjectNew;" + lineSeparator );        
        buffer.append( "import java.util.List;" + lineSeparator );
        buffer.append( "import java.util.ArrayList;" + lineSeparator );        
        buffer.append( "import com.oxymedical.component.rulesComponent.ICondition;"+ lineSeparator);
        buffer.append( "import com.oxymedical.component.rulesComponent.IVariables;"+ lineSeparator);
        buffer.append( "import com.oxymedical.component.rulesComponent.IConsequence;"+ lineSeparator);
        buffer.append( "import com.oxymedical.component.rulesComponent.IConditionalElement;"+ lineSeparator); 
        buffer.append( "import com.oxymedical.component.rulesComponent.rule.elements.Condition;"+ lineSeparator);
        buffer.append( "import com.oxymedical.component.rulesComponent.rule.elements.Variables;"+ lineSeparator);
        buffer.append( "import com.oxymedical.component.rulesComponent.rule.elements.Consequence;"+ lineSeparator); 
        buffer.append( "import com.oxymedical.component.rulesComponent.rule.conditionElement.And;"+ lineSeparator);
        buffer.append( "import com.oxymedical.component.rulesComponent.rule.conditionElement.Or;"+ lineSeparator);
        
        buffer.append( "public class " + ruleClassName + " implements IRuleClass {" + lineSeparator );
        buffer.append(createVariableForRuleClass()+ lineSeparator);
        buffer.append(parseVariableList(ruleSet.getVariables())+ lineSeparator);
        
        // constructor
        buffer.append("\t public "+ruleClassName+"() {" + lineSeparator);
        buffer.append("\t\t evaluate();" + lineSeparator);
        buffer.append("\t }" + lineSeparator);
        
        // evaluate method that populates the ruleclass variables
        buffer.append("\t public void evaluate() {" + lineSeparator);
        
        // TODO move the below sections to different methods 
        
        // variables section
        buffer.append("\t\t IVariables var;" + lineSeparator);
        for(int i=0; i<ruleSet.getVariables().size(); i++) {
        	Variables var = (Variables)ruleSet.getVariables().get(i);
        	buffer.append("\t\t var = new Variables();" + lineSeparator);
        	buffer.append("\t\t var.setType(\""+var.getType()+"\");" + lineSeparator);
        	buffer.append("\t\t var.setId(\""+var.getId()+"\");" + lineSeparator);
        	buffer.append("\t\t this.variableList.add(var);" + lineSeparator + lineSeparator);
        }
        
        // condition section
        buffer.append("\t\t ICondition cond;" + lineSeparator);
    	ICondition cond = (Condition)rule.getCondition();
    	buffer.append("\t\t cond = new Condition();" + lineSeparator);    	
    	String condStr = cond.getConditionString();        	
    	condStr = condStr.replaceAll("\"", "\'"); 
    	condStr = replaceEncodedString(condStr);
    	buffer.append("\t\t cond.setConditionString(\""+condStr+"\");" + lineSeparator);
    	buffer.append("\t\t this.condition = cond;" + lineSeparator + lineSeparator);
        	
        // consequence section
        buffer.append("\t\t Consequence conseq;" + lineSeparator);
        for(int i=0; i<rule.getConsequenceList().size(); i++) {
        	Consequence conseq = (Consequence)rule.getConsequenceList().get(i);
        	buffer.append("\t\t conseq = new Consequence();" + lineSeparator);
        	String consqStr = conseq.getConsequenceString();        	
        	consqStr = consqStr.replaceAll("\"", "\'");        	
        	buffer.append("\t\t conseq.setConsequenceString(\""+consqStr+"\");" + lineSeparator);
        	buffer.append("\t\t this.consequenceList.add(conseq);" + lineSeparator + lineSeparator);
        }
        
        // salience section        
    	buffer.append("\t\t this.salience = "+rule.getSalience()+";" + lineSeparator + lineSeparator);
    	buffer.append("\t\t this.url = "+rule.getUrl()+";" + lineSeparator + lineSeparator);
        
        buffer.append( "\t }  " + lineSeparator ); // end of evaluate
        
        // condition method - this has the parsed condition string 
        // this checks the syntax & validity of the condition of rule
        buffer.append("\t public void condition() {" + lineSeparator);        
        buffer.append(parseCondition(rule.getCondition()));        
        buffer.append( "\t }  " + lineSeparator );   

        // consequence method - this has the parsed consequence string 
        // this checks the syntax & validity of the consequence of rule
        buffer.append("\t public void consequence() {" + lineSeparator);        
        buffer.append(parseConsequence(rule.getConsequenceList()));
        // RuleComponent.logger.log(0,"rule.getConsequenceList() = " +rule.getConsequenceList());
        buffer.append( "\t }  " + lineSeparator );        
        
        // getters & setters of all the variables of the rule class
        buffer.append("\t public ICondition getCondition() { "+lineSeparator);
        buffer.append("\t\t return this.condition;" + lineSeparator);
        buffer.append("\t }" + lineSeparator);
        
        buffer.append("\t public List getConsequenceList() { "+lineSeparator);
        buffer.append("\t\t return this.consequenceList;" + lineSeparator);
        buffer.append("\t }" + lineSeparator);
        
        buffer.append("\t public List getVariablesList() { "+lineSeparator);
        buffer.append("\t\t return this.variableList;"+ lineSeparator);
        buffer.append("\t }" + lineSeparator);
        
        buffer.append("\t public int getSalience() { "+lineSeparator);
        buffer.append("\t\t return this.salience;" + lineSeparator);
        buffer.append("\t }" + lineSeparator);
        
        buffer.append("\t public void setCondition(ICondition condition) {"+lineSeparator);     
        buffer.append("\t\t this.condition = condition;" + lineSeparator);
        buffer.append("\t }" + lineSeparator);
        
        buffer.append("\t public void setConsequenceList(List consequenceList) {"+lineSeparator);
        buffer.append("\t\t this.consequenceList = consequenceList;" + lineSeparator);
        buffer.append("\t }"+lineSeparator);
        
        buffer.append("\t public void setVariablesList(List variableList) {"+lineSeparator);
        buffer.append("\t\t this.variableList = variableList;" + lineSeparator);
        buffer.append("\t }"+lineSeparator);
        
        buffer.append("\t public void setSalience(int salience) {"+lineSeparator);
        buffer.append("\t\t this.salience = salience;" + lineSeparator);
        buffer.append("\t }"+lineSeparator);
        
        buffer.append("\t public String getUrl() { "+lineSeparator);
        buffer.append("\t\t return this.url;" + lineSeparator);
        buffer.append("\t }" + lineSeparator);
        
        buffer.append("\t public void setUrl(String url) { "+lineSeparator);
        buffer.append("\t\t this.url = url;" + lineSeparator);
        buffer.append("\t }" + lineSeparator);
        
        buffer.append("}" +lineSeparator); // end of class

        String ruleClass = buffer.toString();
        // RuleComponent.logger.log(0,"ruleClass = " +ruleClass);       
        return ruleClass;
    }
	
	/**
	 * Parses the variable list and appends to the rule class variable string
	 * 
	 * @param variableList
	 * @return
	 */
	private String parseVariableList(List variableList) {
		StringBuffer variableString = new StringBuffer("");
		//variableString.append("@InjectNew \n public Policy rulesClassificationPolicy;"+lineSeparator);
		for(Iterator it=variableList.iterator(); it.hasNext(); ) {
			Variables var = (Variables)it.next();
			/*if(var.getType().equalsIgnoreCase("com.oxymedical.rules.integrate.classification.Policy")){
				continue;
			}*/
			variableString.append("\t\t" + var.getType() + "  " + var.getId() +" = new "+ var.getType() +"();" + lineSeparator);
		}
		
		return variableString.toString();
	}
	
	/**
	 * Creates the class level variable for the generated rule class
	 * 
	 * @return
	 */
	private String createVariableForRuleClass() {
		StringBuffer variableString = new StringBuffer("");
		variableString.append( "\t private int salience = 0;" +lineSeparator);		
		variableString.append( "\t private String url = \"\";" +lineSeparator);
		variableString.append( "\t private List variableList = new ArrayList();" +lineSeparator);
		variableString.append( "\t private ICondition condition = new Condition();" +lineSeparator);
		variableString.append( "\t private List consequenceList = new ArrayList();" +lineSeparator);
		return variableString.toString();
	}

	/**
	 * Parses the consequence list and appends to the rule class consequence string
	 * 
	 * @param consequenceList
	 * @return
	 */
	private String parseConsequence(List<IConsequence> consequenceList) {		
		StringBuffer consequenceString = new StringBuffer("");
		for(Iterator it=consequenceList.iterator(); it.hasNext(); ) {
			Consequence conseq = (Consequence)it.next();
			String conseqStr = conseq.getConsequenceString();
			if (conseqStr.indexOf("(") > 0 && conseqStr.indexOf(")") > 0)
				consequenceString.append("\t\t\t" + conseqStr + ";" + lineSeparator);
			else
				consequenceString.append("\t\t if (" + conseqStr + ") { }" + lineSeparator);
		}
		return consequenceString.toString();
	}

	/**
	 * Parses the condition string and appends to the rule class condition string
	 * 
	 * @param condition
	 * @return
	 */
	private String parseCondition(ICondition condition){	
		String condStr = condition.getConditionString();
		condStr = replaceEncodedString(condStr);
		StringBuffer conditionString = new StringBuffer("");
		conditionString.append("\t\t if (" + condStr + ") { }" + lineSeparator);
		
		/*String cond[] = splitConditions(condStr);		
		for(int i=0; i<cond.length; i++) {	
			if (null != cond[i]){
				 RuleComponent.logger.log(0,"cond[i] = " +cond[i]);
				//if (cond[i].contains(ConditionConstants.LESS) || cond[i].contains(ConditionConstants.GREATER) || cond[i].contains(ConditionConstants.NOT_EQUAL)){
					conditionString.append("\t\t if (" + cond[i] + ") { }" + lineSeparator);
				//}
				else {
					 RuleComponent.logger.log(0,"else  ");
					conditionString.append("\t\t" + cond[i] + ";" + lineSeparator);
					 RuleComponent.logger.log(0,"*************  ");
				}
			}
		}*/	
		
		return conditionString.toString();
	}
	
	/**
	 * Helper function to split the condtion string to check the syntax.
	 * 
	 * @param condition
	 * @return
	 *//*
	private String[] splitConditions(String condition) {
		condition = RuleComponentUtil.truncateExtraSpace(condition);
		String regex = "[\\( , \\)]";
		//condition = condition.replaceAll(regex, "");
		condition = condition.replaceAll(" ", "");
		condition = condition.replaceAll("&&", " && ");
		String regx = "[||, && ,\\S]";
		String ar[] = Pattern.compile(regx).split(condition);
		String temp = "";
		String[] cond = new String[ar.length];
		for(int i=0, j=0; i < ar.length; i++) {
			temp = ar[i];
			if (temp.length() > 0 && !temp.equals(ConditionConstants.ANDCONDITION)) {
				cond[j] = temp;
				j++;
			}
		}		
	  return cond;
	}*/
	
	/**
	 * Replaces all the enceoded string
	 * 
	 * @param str
	 * @return
	 */
	private String replaceEncodedString(String str) {
		String encodedStr = str;
		encodedStr = encodedStr.replaceAll(ConditionConstants.GREATERENCODED, ConditionConstants.GREATER);
		encodedStr = encodedStr.replaceAll(ConditionConstants.LESSENCODED, ConditionConstants.LESS);
		encodedStr = RuleComponentUtil.truncateExtraSpace(encodedStr);
		return encodedStr;
	}
	
}
