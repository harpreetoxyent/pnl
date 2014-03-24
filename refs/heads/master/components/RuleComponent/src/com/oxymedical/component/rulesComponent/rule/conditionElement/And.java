package com.oxymedical.component.rulesComponent.rule.conditionElement;

import com.oxymedical.component.rulesComponent.IConditionalElement;
import com.oxymedical.component.rulesComponent.constants.ConditionConstants;


/**
 * Class for AND conditional element relational operator 
 * 
 * @author Oxyent Medical
 *
 */
public class And implements IConditionalElement {
	
	String conditionType;
	
	public And() {
		conditionType = ConditionConstants.ANDCONDITION;
	}
	
	public String getConditionType() {
		return conditionType;
	}

	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;	
	}

	
}
