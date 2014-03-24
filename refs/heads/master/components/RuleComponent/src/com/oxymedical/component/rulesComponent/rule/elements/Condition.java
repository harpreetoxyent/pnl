package com.oxymedical.component.rulesComponent.rule.elements;

import com.oxymedical.component.rulesComponent.ICondition;

/**
 * Condition class that has the name and condition string
 * 
 * @author Oxyent Medical
 *
 */
public class Condition implements ICondition {
	
	private String name;
	private String conditionString;
		
	public String getConditionString() {
		return conditionString;
	}
	public void setConditionString(String conditionString) {
		this.conditionString = conditionString;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
