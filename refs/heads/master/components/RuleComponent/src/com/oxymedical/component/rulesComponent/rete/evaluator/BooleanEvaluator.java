package com.oxymedical.component.rulesComponent.rete.evaluator;

import com.oxymedical.component.rulesComponent.IEvaluator;
import com.oxymedical.component.rulesComponent.constants.EvaluatorConstants;


/**
 * For handling boolean types.
 * 
 * @author Oxyent Medical
 */
public class BooleanEvaluator {

    public static IEvaluator getBooleanEvaluator(final String operator) {
        if (operator.equals(EvaluatorConstants.EQUAL) || operator.equals(EvaluatorConstants.DOUBLE_EQUAL))
                return BooleanEqualEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.NOT_EQUAL))
                return BooleanNotEqualEvaluator.INSTANCE;
            return null;
        }
    }

   class BooleanEqualEvaluator extends BaseEvaluator {
      
       public final static IEvaluator INSTANCE = new BooleanEqualEvaluator();

        private BooleanEqualEvaluator() {
            super( EvaluatorConstants.BOOLEAN_TYPE,
            		EvaluatorConstants.EQUAL );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            if ( object1 == null ) {
                return object2 == null;
            }
            String s1 = object1.toString();
            String s2 = object2.toString();
            if(s2.indexOf("\'") != -1){
            	s2 = s2.replaceAll("\'", "");
            }
            if(s1.trim().equals(s2.trim()))
            	return true;
            return false;
        }

        public String toString() {
            return "Boolean ==";
        }
    }

    class BooleanNotEqualEvaluator extends BaseEvaluator {
      
        public final static IEvaluator INSTANCE = new BooleanNotEqualEvaluator();

        private BooleanNotEqualEvaluator() {
            super( EvaluatorConstants.BOOLEAN_TYPE,
            		EvaluatorConstants.NOT_EQUAL );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            if ( object1 == null ) {
                return object2 != null;
            }
            String s1 = object1.toString();
            String s2 = object2.toString();
            if(s2.indexOf("\'") != -1){
            	s2 = s2.replaceAll("\'", "");
            }
            if(s1.trim().equals(s2.trim()))
            	return false;
            return true;
        }

        public String toString() {
            return "Boolean !=";
        }
    }
