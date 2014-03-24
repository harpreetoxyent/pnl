package com.oxymedical.component.rulesComponent.rete.evaluator;

import com.oxymedical.component.rulesComponent.IEvaluator;
import com.oxymedical.component.rulesComponent.constants.EvaluatorConstants;


/**
 * For handling double types.
 * 
 * @author Oxyent Medical
 */
public class DoubleEvaluator {

    public static IEvaluator getDoubleEvaluator(final String operator) {
        if (operator.equals(EvaluatorConstants.EQUAL) || operator.equals(EvaluatorConstants.DOUBLE_EQUAL))
                return DoubleEqualEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.NOT_EQUAL))
                return DoubleNotEqualEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.LESS))
                return DoubleLessEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.LESS_OR_EQUAL))
                return DoubleLessOrEqualEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.GREATER))
                return DoubleGreaterEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.GREATER_OR_EQUAL))
                return DoubleGreaterOrEqualEvaluator.INSTANCE;
            return null;
        }
    }

    class DoubleEqualEvaluator extends BaseEvaluator {
      
        public final static IEvaluator INSTANCE = new DoubleEqualEvaluator();

        private DoubleEqualEvaluator() {
            super( EvaluatorConstants.DOUBLE_TYPE,
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
            return "Double ==";
        }
    }

    class DoubleNotEqualEvaluator extends BaseEvaluator {
       
        public final static IEvaluator INSTANCE = new DoubleNotEqualEvaluator();

        private DoubleNotEqualEvaluator() {
            super( EvaluatorConstants.DOUBLE_TYPE,
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
            return "Double !=";
        }
    }

   class DoubleLessEvaluator extends BaseEvaluator {
      
        public final static IEvaluator INSTANCE = new DoubleLessEvaluator();

        private DoubleLessEvaluator() {
            super( EvaluatorConstants.DOUBLE_TYPE,
            		EvaluatorConstants.LESS );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            return ((Number) object1).doubleValue() < ((Number) object2).doubleValue();
        }

        public String toString() {
            return "Double <";
        }
    }

    class DoubleLessOrEqualEvaluator extends BaseEvaluator {
      
        public final static IEvaluator INSTANCE = new DoubleLessOrEqualEvaluator();

        private DoubleLessOrEqualEvaluator() {
            super( EvaluatorConstants.DOUBLE_TYPE,
            		EvaluatorConstants.LESS_OR_EQUAL );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            return ((Number) object1).doubleValue() <= ((Number) object2).doubleValue();
        }

        public String toString() {
            return "Double <=";
        }
    }

    class DoubleGreaterEvaluator extends BaseEvaluator {
       
        public final static IEvaluator INSTANCE         = new DoubleGreaterEvaluator();

        private DoubleGreaterEvaluator() {
            super( EvaluatorConstants.DOUBLE_TYPE,
            		EvaluatorConstants.GREATER );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            return ((Number) object1).doubleValue() > ((Number) object2).doubleValue();
        }

        public String toString() {
            return "Double >";
        }
    }

    class DoubleGreaterOrEqualEvaluator extends BaseEvaluator {
      
        public final static IEvaluator INSTANCE = new DoubleGreaterOrEqualEvaluator();

        private DoubleGreaterOrEqualEvaluator() {
            super( EvaluatorConstants.DOUBLE_TYPE,
            		EvaluatorConstants.GREATER_OR_EQUAL );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            return ((Number) object1).doubleValue() >= ((Number) object2).doubleValue();
        }

        public String toString() {
            return "Double >=";
        }
    }