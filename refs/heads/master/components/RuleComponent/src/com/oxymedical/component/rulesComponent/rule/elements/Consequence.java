package com.oxymedical.component.rulesComponent.rule.elements;

import com.oxymedical.component.rulesComponent.IConsequence;

/**
 * Consequence class that has the name and consequence string
 * 
 * @author Oxyent Medical
 *
 */
public class Consequence implements IConsequence {

	private String name;
	private String consequenceString;
	
	public String getConsequenceString() {
		return consequenceString;
	}
	public void setConsequenceString(String consequenceString) {
		this.consequenceString = consequenceString;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
