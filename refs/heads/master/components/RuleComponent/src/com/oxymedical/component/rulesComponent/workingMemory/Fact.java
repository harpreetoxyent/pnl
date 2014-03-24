package com.oxymedical.component.rulesComponent.workingMemory;

public class Fact {
	
	private String nameOfCondition;
	
	private Object value;
	
	private String conditionString;
	
	public String getConditionString() {
		return conditionString;
	}

	public void setConditionString(String conditionString) {
		this.conditionString = conditionString;
	}

	public Fact(String noc, Object val, String condString) {
		this.nameOfCondition = noc;
		this.value = val;
		this.conditionString = condString;
	}

	public String getNameOfCondition() {
		return nameOfCondition;
	}

	public void setNameOfCondition(String nameOfCondition) {
		this.nameOfCondition = nameOfCondition;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		Fact thisFact = this;
		Fact newFact = (Fact)obj;
		if (thisFact.getNameOfCondition().equals(newFact.getNameOfCondition()) && thisFact.getValue().equals(newFact.getValue())) 
			return true;
		return false;
	}
	
	

}
