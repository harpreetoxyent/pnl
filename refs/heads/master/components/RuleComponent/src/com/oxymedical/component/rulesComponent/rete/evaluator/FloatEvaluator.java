package com.oxymedical.component.rulesComponent.rete.evaluator;

import com.oxymedical.component.rulesComponent.IEvaluator;
import com.oxymedical.component.rulesComponent.constants.EvaluatorConstants;


/**
 * For handling float types.
 * 
 * @author Oxyent Medical
 */
public class FloatEvaluator {
	
	public static IEvaluator getFloatEvaluator(final String operator) {
		 
        if (operator.equals(EvaluatorConstants.EQUAL) || operator.equals(EvaluatorConstants.DOUBLE_EQUAL)) 
            return FloatEqualEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.NOT_EQUAL))
            return FloatNotEqualEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.LESS))
            return FloatLessEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.LESS_OR_EQUAL))
            return FloatLessOrEqualEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.GREATER))
            return FloatGreaterEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.GREATER_OR_EQUAL))
            return FloatGreaterOrEqualEvaluator.INSTANCE;	            
        
        return null;
 }
	
}


 class FloatEqualEvaluator extends BaseEvaluator {
   
    public final static IEvaluator INSTANCE = new FloatEqualEvaluator();

    private FloatEqualEvaluator() {
        super( EvaluatorConstants.FLOAT_TYPE,
        		EvaluatorConstants.EQUAL );
    }

    public boolean evaluate(final Object object1,
                            final Object object2) {
        if ( object1 == null ) {
            return object2 == null;
        }
        return ((Number) object1).equals( object2 );
    }

    public String toString() {
        return "Float ==";
    }
 }

 class FloatNotEqualEvaluator extends BaseEvaluator {
   
    public final static IEvaluator INSTANCE = new FloatNotEqualEvaluator();

    private FloatNotEqualEvaluator() {
        super( EvaluatorConstants.FLOAT_TYPE,
        		EvaluatorConstants.NOT_EQUAL );
    }

    public boolean evaluate(final Object object1,
                            final Object object2) {
        if ( object1 == null ) {
            return object2 != null;
        }
        return !((Number) object1).equals( object2 );
    }

    public String toString() {
        return "Float !=";
    }
 }

 class FloatLessEvaluator extends BaseEvaluator {
  
    public final static IEvaluator INSTANCE         = new FloatLessEvaluator();

    private FloatLessEvaluator() {
        super( EvaluatorConstants.FLOAT_TYPE,
        		EvaluatorConstants.LESS );
    }

    public boolean evaluate(final Object object1,
                            final Object object2) {
        return ((Number) object1).floatValue() < ((Number) object2).floatValue();
    }

    public String toString() {
        return "Float <";
    }
}

 class FloatLessOrEqualEvaluator extends BaseEvaluator {
   
    public final static IEvaluator INSTANCE         = new FloatLessOrEqualEvaluator();

    private FloatLessOrEqualEvaluator() {
        super( EvaluatorConstants.FLOAT_TYPE,
        		EvaluatorConstants.LESS_OR_EQUAL );
    }

    public boolean evaluate(final Object object1,
                            final Object object2) {
        return ((Number) object1).floatValue() <= ((Number) object2).floatValue();
    }

    public String toString() {
        return "Float <=";
    }
 }

 class FloatGreaterEvaluator extends BaseEvaluator {
  
    public final static IEvaluator INSTANCE         = new FloatGreaterEvaluator();

    private FloatGreaterEvaluator() {
        super( EvaluatorConstants.FLOAT_TYPE,
        		EvaluatorConstants.GREATER );
    }

    public boolean evaluate(final Object object1,
                            final Object object2) {
        return ((Number) object1).floatValue() > ((Number) object2).floatValue();
    }

    public String toString() {
        return "Float >";
    }
}

 class FloatGreaterOrEqualEvaluator extends BaseEvaluator {
  
    public final static IEvaluator INSTANCE         = new FloatGreaterOrEqualEvaluator();

    private FloatGreaterOrEqualEvaluator() {
        super( EvaluatorConstants.FLOAT_TYPE,
        		EvaluatorConstants.GREATER_OR_EQUAL );
    }

    public boolean evaluate(final Object object1,
                            final Object object2) {
        return ((Number) object1).floatValue() >= ((Number) object2).floatValue();
    }

    public String toString() {
        return "Float >=";
    }
 }
