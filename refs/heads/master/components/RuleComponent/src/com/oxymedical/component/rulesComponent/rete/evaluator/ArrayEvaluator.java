package com.oxymedical.component.rulesComponent.rete.evaluator;

import java.util.Arrays;

import com.oxymedical.component.rulesComponent.IEvaluator;
import com.oxymedical.component.rulesComponent.constants.EvaluatorConstants;


/**
 * For handling simple (non collection) array types.
 * @author Oxyent Medical
 */
public class ArrayEvaluator {

    public static IEvaluator getArrayEvaluator(final String operator) {
       if (operator.equals(EvaluatorConstants.EQUAL) || operator.equals(EvaluatorConstants.DOUBLE_EQUAL))
            return ArrayEqualEvaluator.INSTANCE;
       if (operator.equals(EvaluatorConstants.NOT_EQUAL))
            return ArrayNotEqualEvaluator.INSTANCE;
       if (operator.equals(EvaluatorConstants.CONTAINS))
            return ArrayContainsEvaluator.INSTANCE;
         return null;
      }
    }

    class ArrayEqualEvaluator extends BaseEvaluator {
       
        public final static IEvaluator INSTANCE = new ArrayEqualEvaluator();

        private ArrayEqualEvaluator() {
            super( EvaluatorConstants.ARRAY_TYPE,
            		EvaluatorConstants.EQUAL );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            if ( object1 == null ) {
                return object2 == null;
            }
            return object1.equals( object2 );
        }

        public String toString() {
            return "Array ==";
        }
    }

    class ArrayNotEqualEvaluator extends BaseEvaluator {
     
        public final static IEvaluator INSTANCE = new ArrayNotEqualEvaluator();

        private ArrayNotEqualEvaluator() {
            super( EvaluatorConstants.ARRAY_TYPE,
            		EvaluatorConstants.NOT_EQUAL );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            return !Arrays.equals( (Object[]) object1,
                                   (Object[]) object2 );
        }

        public String toString() {
            return "Array !=";
        }
    }

    class ArrayContainsEvaluator extends BaseEvaluator {
       
        public final static IEvaluator INSTANCE = new ArrayContainsEvaluator();

        private ArrayContainsEvaluator() {
            super( EvaluatorConstants.ARRAY_TYPE,
            		EvaluatorConstants.CONTAINS );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            if ( object2 == null ) {
                return false;
            }
            if ( Arrays.binarySearch( (Object[]) object1,
                                      object2 ) == -1 ) {
                return false;
            } else {
                return true;
            }
        }

        public String toString() {
            return "Array contains";
        }
    }