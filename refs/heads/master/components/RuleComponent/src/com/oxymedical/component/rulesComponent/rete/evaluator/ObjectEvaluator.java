package com.oxymedical.component.rulesComponent.rete.evaluator;

import com.oxymedical.component.rulesComponent.RuleComponent;
import com.oxymedical.component.rulesComponent.constants.EvaluatorConstants;

/**
 * For handling Object types.
 * 
 * @author Oxyent Medical
 */
public class ObjectEvaluator  extends BaseEvaluator {

	String operator;
	
	public ObjectEvaluator(String operator) {
		super( EvaluatorConstants.OBJECT_TYPE,
        		EvaluatorConstants.COMPARE );
		// RuleComponent.logger.log(0,"operator = *** " + operator);
		this.operator = operator.trim();		
	}
	
	public String getOperator() {
		return operator;
	}

	public boolean compare(Object object1, Object object2){
		if (operator.equals("=") || operator.equals("=="))
			return object1.equals(object2);
		else if (operator.equals(">"))
			return ((Number) object1).intValue() > ((Number) object2).intValue();
		else if (operator.equals("<"))
				return ((Number) object1).intValue() < ((Number) object2).intValue();
		
		return false;
	}

	@Override
	public boolean evaluate(Object object1, Object object2) {
		if (operator.equals("=") || operator.equals("=="))
			return object1.equals(object2);
		else if (operator.equals(">"))
			return ((Number) object1).intValue() > ((Number) object2).intValue();
		else if (operator.equals("<"))
				return ((Number) object1).intValue() < ((Number) object2).intValue();
		
		return false;
	}
	
}
