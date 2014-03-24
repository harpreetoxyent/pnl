package com.oxymedical.component.rulesComponent.rete.evaluator;

import com.oxymedical.component.rulesComponent.IEvaluator;
import com.oxymedical.component.rulesComponent.constants.EvaluatorConstants;

/**
 * For handling character types.
 * 
 * @author Oxyent Medical
 */

public class CharacterEvaluator {

    public static IEvaluator getCharacterEvaluator(final String operator) {
        if (operator.equals(EvaluatorConstants.EQUAL) || operator.equals(EvaluatorConstants.DOUBLE_EQUAL))
                return CharacterEqualEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.NOT_EQUAL))
                return CharacterNotEqualEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.LESS))
                return CharacterLessEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.LESS_OR_EQUAL))
                return CharacterLessOrEqualEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.GREATER))
                return CharacterGreaterEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.GREATER_OR_EQUAL))
                return CharacterGreaterOrEqualEvaluator.INSTANCE;
            return null;
        }
    }

    class CharacterEqualEvaluator extends BaseEvaluator {
      
        public final static IEvaluator INSTANCE         = new CharacterEqualEvaluator();

        private CharacterEqualEvaluator() {
            super( EvaluatorConstants.CHAR_TYPE,
            		EvaluatorConstants.EQUAL );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            if ( object1 == null ) {
                return object2 == null;
            }
            return ((Character) object1).equals( object2 );
        }

        public String toString() {
            return "Character ==";
        }
    }

    class CharacterNotEqualEvaluator extends BaseEvaluator {
       
        public final static IEvaluator INSTANCE         = new CharacterNotEqualEvaluator();

        private CharacterNotEqualEvaluator() {
            super( EvaluatorConstants.CHAR_TYPE,
            		EvaluatorConstants.NOT_EQUAL );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            if ( object1 == null ) {
                return object2 != null;
            }
            return !((Character) object1).equals( object2 );
        }

        public String toString() {
            return "Character !=";
        }
    }

    class CharacterLessEvaluator extends BaseEvaluator {
      
        public final static IEvaluator INSTANCE         = new CharacterLessEvaluator();

        private CharacterLessEvaluator() {
            super( EvaluatorConstants.CHAR_TYPE,
            		EvaluatorConstants.LESS );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            return ((Character) object1).charValue() < ((Character) object2).charValue();
        }

        public String toString() {
            return "Character <";
        }
    }

    class CharacterLessOrEqualEvaluator extends BaseEvaluator {
     
        public final static IEvaluator INSTANCE = new CharacterLessOrEqualEvaluator();

        private CharacterLessOrEqualEvaluator() {
            super( EvaluatorConstants.CHAR_TYPE,
            		EvaluatorConstants.LESS_OR_EQUAL );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            return ((Character) object1).charValue() <= ((Character) object2).charValue();
        }

        public String toString() {
            return "Character <=";
        }
    }

    class CharacterGreaterEvaluator extends BaseEvaluator {
      
        public final static IEvaluator INSTANCE = new CharacterGreaterEvaluator();

        private CharacterGreaterEvaluator() {
            super( EvaluatorConstants.CHAR_TYPE,
            		EvaluatorConstants.GREATER );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            return ((Character) object1).charValue() > ((Character) object2).charValue();
        }

        public String toString() {
            return "Character >";
        }
    }

    class CharacterGreaterOrEqualEvaluator extends BaseEvaluator {
       
        public final static IEvaluator INSTANCE = new CharacterGreaterOrEqualEvaluator();

        private CharacterGreaterOrEqualEvaluator() {
            super( EvaluatorConstants.CHAR_TYPE,
            		EvaluatorConstants.GREATER_OR_EQUAL );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            return ((Character) object1).charValue() >= ((Character) object2).charValue();
        }

        public String toString() {
            return "Character >=";
        }
    }
