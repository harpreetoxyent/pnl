package com.oxymedical.component.rulesComponent.rete.evaluator;

import com.oxymedical.component.rulesComponent.IEvaluator;
import com.oxymedical.component.rulesComponent.constants.EvaluatorConstants;


/**
 * For handling short types.
 * 
 * @author Oxyent Medical
 */
public class ShortEvaluator {
	
    public static IEvaluator getShortEvaluator(final String operator) {
        if (operator.equals(EvaluatorConstants.EQUAL) || operator.equals(EvaluatorConstants.DOUBLE_EQUAL))
                return ShortEqualEvaluator.getInstance();
        if (operator.equals(EvaluatorConstants.NOT_EQUAL))
                return ShortNotEqualEvaluator.getInstance();
        if (operator.equals(EvaluatorConstants.LESS))
                return ShortLessEvaluator.getInstance();
        if (operator.equals(EvaluatorConstants.LESS_OR_EQUAL))
                return ShortLessOrEqualEvaluator.getInstance();
        if (operator.equals(EvaluatorConstants.GREATER))
                return ShortGreaterEvaluator.getInstance();
        if (operator.equals(EvaluatorConstants.GREATER_OR_EQUAL))
                return ShortGreaterOrEqualEvaluator.getInstance();
            return null;
        }
    }

    class ShortEqualEvaluator extends BaseEvaluator {
      
        private static IEvaluator  INSTANCE;

        public static IEvaluator getInstance() {
            if ( ShortEqualEvaluator.INSTANCE == null ) {
                ShortEqualEvaluator.INSTANCE = new ShortEqualEvaluator();
            }
            return ShortEqualEvaluator.INSTANCE;
        }

        private ShortEqualEvaluator() {
            super( EvaluatorConstants.SHORT_TYPE,
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
            return "Short ==";
        }
    }

    class ShortNotEqualEvaluator extends BaseEvaluator {
      
        private static IEvaluator  INSTANCE;

        public static IEvaluator getInstance() {
            if ( ShortNotEqualEvaluator.INSTANCE == null ) {
                ShortNotEqualEvaluator.INSTANCE = new ShortNotEqualEvaluator();
            }
            return ShortNotEqualEvaluator.INSTANCE;
        }

        private ShortNotEqualEvaluator() {
            super( EvaluatorConstants.SHORT_TYPE,
            		EvaluatorConstants.NOT_EQUAL );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            if ( object1 == null ) {
                return !(object2 == null);
            }
            return !((Number) object1).equals( object2 );
        }

        public String toString() {
            return "Short !=";
        }
    }

    class ShortLessEvaluator extends BaseEvaluator {
      
        private static IEvaluator  INSTANCE;

        public static IEvaluator getInstance() {
            if ( ShortLessEvaluator.INSTANCE == null ) {
                ShortLessEvaluator.INSTANCE = new ShortLessEvaluator();
            }
            return ShortLessEvaluator.INSTANCE;
        }

        private ShortLessEvaluator() {
            super( EvaluatorConstants.SHORT_TYPE,
            		EvaluatorConstants.LESS );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            return ((Number) object1).shortValue() < ((Number) object2).shortValue();
        }

        public String toString() {
            return "Short <";
        }
    }

    class ShortLessOrEqualEvaluator extends BaseEvaluator {
      
        private static IEvaluator  INSTANCE;

        public static IEvaluator getInstance() {
            if ( ShortLessOrEqualEvaluator.INSTANCE == null ) {
                ShortLessOrEqualEvaluator.INSTANCE = new ShortLessOrEqualEvaluator();
            }
            return ShortLessOrEqualEvaluator.INSTANCE;
        }

        private ShortLessOrEqualEvaluator() {
            super( EvaluatorConstants.SHORT_TYPE,
            		EvaluatorConstants.LESS_OR_EQUAL );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            return ((Number) object1).shortValue() <= ((Number) object2).shortValue();
        }

        public String toString() {
            return "Boolean <=";
        }
    }

    class ShortGreaterEvaluator extends BaseEvaluator {
      
        private static IEvaluator  INSTANCE;

        public static IEvaluator getInstance() {
            if ( ShortGreaterEvaluator.INSTANCE == null ) {
                ShortGreaterEvaluator.INSTANCE = new ShortGreaterEvaluator();
            }
            return ShortGreaterEvaluator.INSTANCE;
        }

        private ShortGreaterEvaluator() {
            super( EvaluatorConstants.SHORT_TYPE,
            		EvaluatorConstants.GREATER );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            return ((Number) object1).shortValue() > ((Number) object2).shortValue();
        }

        public String toString() {
            return "Short >";
        }
    }

    class ShortGreaterOrEqualEvaluator extends BaseEvaluator {
      
        private static IEvaluator  INSTANCE;

        public static IEvaluator getInstance() {
            if ( ShortGreaterOrEqualEvaluator.INSTANCE == null ) {
                ShortGreaterOrEqualEvaluator.INSTANCE = new ShortGreaterOrEqualEvaluator();
            }
            return ShortGreaterOrEqualEvaluator.INSTANCE;
        }

        private ShortGreaterOrEqualEvaluator() {
            super( EvaluatorConstants.SHORT_TYPE,
            		EvaluatorConstants.GREATER_OR_EQUAL );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            return ((Number) object1).shortValue() >= ((Number) object2).shortValue();
        }

        public String toString() {
            return "Short >=";
        }
    }