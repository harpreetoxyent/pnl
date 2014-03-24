package com.oxymedical.component.rulesComponent;

public interface IEvaluator {
	
	public String getType();
	
	public String getOperator();
	
	public boolean evaluate(Object object1, Object object2);

}
