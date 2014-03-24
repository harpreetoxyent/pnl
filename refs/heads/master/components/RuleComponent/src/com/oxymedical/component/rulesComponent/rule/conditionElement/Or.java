package com.oxymedical.component.rulesComponent.rule.conditionElement;

import com.oxymedical.component.rulesComponent.IConditionalElement;
import com.oxymedical.component.rulesComponent.constants.ConditionConstants;


/**
 * Class for OR conditional element relational operator 
 * 
 * @author Oxyent Medical
 *
 */
public class Or implements IConditionalElement {

	String conditionType;
	
	public Or() {
		conditionType = ConditionConstants.ORCONDITION;
	}

	public String getConditionType() {		
		return conditionType;
	}

	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;		
	}
}
