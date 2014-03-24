package com.oxymedical.component.rulesComponent.rete.evaluator;

import com.oxymedical.component.rulesComponent.IEvaluator;
import com.oxymedical.component.rulesComponent.constants.EvaluatorConstants;


/**
 * For handling long types.
 * 
 * @author Oxyent Medical
 */
public class LongEvaluator {
    public static IEvaluator getLongEvaluator(final String operator) {
        if (operator.equals(EvaluatorConstants.EQUAL) || operator.equals(EvaluatorConstants.DOUBLE_EQUAL))
                return LongEqualEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.NOT_EQUAL))
                return LongNotEqualEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.LESS))
                return LongLessEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.LESS_OR_EQUAL))
                return LongLessOrEqualEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.GREATER))
                return LongGreaterEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.GREATER_OR_EQUAL))
                return LongGreaterOrEqualEvaluator.INSTANCE;
            return null;
        }
    }

    class LongEqualEvaluator extends BaseEvaluator {
     
        public final static IEvaluator INSTANCE = new LongEqualEvaluator();

        private LongEqualEvaluator() {
            super( EvaluatorConstants.LONG_TYPE,
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
            return "Long ==";
        }
    }

    class LongNotEqualEvaluator extends BaseEvaluator {
       
        public final static IEvaluator INSTANCE = new LongNotEqualEvaluator();

        private LongNotEqualEvaluator() {
            super( EvaluatorConstants.LONG_TYPE,
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
            return "Long !=";
        }
    }

    class LongLessEvaluator extends BaseEvaluator {
       
        public final static IEvaluator INSTANCE = new LongLessEvaluator();

        private LongLessEvaluator() {
            super( EvaluatorConstants.LONG_TYPE,
            		EvaluatorConstants.LESS );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            return ((Number) object1).longValue() < ((Number) object2).longValue();
        }

        public String toString() {
            return "Long <";
        }
    }

    class LongLessOrEqualEvaluator extends BaseEvaluator {
       
        public final static IEvaluator INSTANCE = new LongLessOrEqualEvaluator();

        private LongLessOrEqualEvaluator() {
            super( EvaluatorConstants.LONG_TYPE,
            		EvaluatorConstants.LESS_OR_EQUAL );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            return ((Number) object1).longValue() <= ((Number) object2).longValue();
        }

        public String toString() {
            return "Long <=";
        }
    }

    class LongGreaterEvaluator extends BaseEvaluator {
       
        public final static IEvaluator INSTANCE = new LongGreaterEvaluator();

        private LongGreaterEvaluator() {
            super( EvaluatorConstants.LONG_TYPE,
            		EvaluatorConstants.GREATER );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            return ((Number) object1).longValue() > ((Number) object2).longValue();
        }

        public String toString() {
            return "Long >";
        }
    }

    class LongGreaterOrEqualEvaluator extends BaseEvaluator {
      
        public final static IEvaluator INSTANCE = new LongGreaterOrEqualEvaluator();

        private LongGreaterOrEqualEvaluator() {
            super( EvaluatorConstants.LONG_TYPE,
            		EvaluatorConstants.GREATER_OR_EQUAL );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            return ((Number) object1).longValue() >= ((Number) object2).longValue();
        }

        public String toString() {
            return "Long >=";
        }
    }