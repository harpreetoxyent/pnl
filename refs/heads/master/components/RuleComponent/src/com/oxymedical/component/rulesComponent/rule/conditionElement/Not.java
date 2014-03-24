package com.oxymedical.component.rulesComponent.rule.conditionElement;

import com.oxymedical.component.rulesComponent.IConditionalElement;
import com.oxymedical.component.rulesComponent.constants.ConditionConstants;


/**
 * Class for NOT conditional element relational operator 
 * 
 * @author Oxyent Medical
 *
 */
public class Not implements IConditionalElement {

	String conditionType;
	
	public Not() {
		conditionType = ConditionConstants.NOTCONDITION;
	}
	
	public String getConditionType() {
		return conditionType;
	}

	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;	
	}

}
