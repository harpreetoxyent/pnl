package com.oxymedical.component.rulesComponent.rete.evaluator;

import com.oxymedical.component.rulesComponent.IEvaluator;
import com.oxymedical.component.rulesComponent.constants.EvaluatorConstants;


/**
 * For handling byte types.
 * 
 * @author Oxyent Medical
 */
public class ByteEvaluator {

    public static IEvaluator getByteEvaluator(final String operator) {
        if (operator.equals(EvaluatorConstants.EQUAL) || operator.equals(EvaluatorConstants.DOUBLE_EQUAL))
                return ByteEqualEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.NOT_EQUAL))
                return ByteNotEqualEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.LESS))
                return ByteLessEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.LESS_OR_EQUAL))
                return ByteLessOrEqualEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.GREATER))
                return ByteGreaterEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.GREATER_OR_EQUAL))
                return ByteGreaterOrEqualEvaluator.INSTANCE;
        return null;
        }
    }

    class ByteEqualEvaluator extends BaseEvaluator {
        
        public final static IEvaluator INSTANCE = new ByteEqualEvaluator();

        private ByteEqualEvaluator() {
            super( EvaluatorConstants.BYTE_TYPE,
            		EvaluatorConstants.EQUAL );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            if ( object1 == null ) {
                return object2 == null;
            }
            return ((Byte) object1).equals( object2 );
        }

        public String toString() {
            return "Byte ==";
        }
    }

    class ByteNotEqualEvaluator extends BaseEvaluator {
      
        public final static IEvaluator INSTANCE = new ByteNotEqualEvaluator();

        private ByteNotEqualEvaluator() {
            super( EvaluatorConstants.BYTE_TYPE,
            		EvaluatorConstants.NOT_EQUAL );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            if ( object1 == null ) {
                return object2 != null;
            }
            return !((Byte) object1).equals( object2 );
        }

        public String toString() {
            return "Byte !=";
        }
    }

    class ByteLessEvaluator extends BaseEvaluator {
       
        public final static IEvaluator INSTANCE = new ByteLessEvaluator();

        private ByteLessEvaluator() {
            super( EvaluatorConstants.BYTE_TYPE,
            		EvaluatorConstants.LESS );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            return ((Byte) object1).byteValue() < ((Byte) object2).byteValue();
        }

        public String toString() {
            return "Byte <";
        }
    }

    class ByteLessOrEqualEvaluator extends BaseEvaluator {
     
        public final static IEvaluator INSTANCE = new ByteLessOrEqualEvaluator();

        private ByteLessOrEqualEvaluator() {
            super( EvaluatorConstants.BYTE_TYPE,
            		EvaluatorConstants.LESS_OR_EQUAL );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            return ((Byte) object1).byteValue() <= ((Byte) object2).byteValue();
        }

        public String toString() {
            return "Byte <=";
        }
    }

    class ByteGreaterEvaluator extends BaseEvaluator {
      
        public final static IEvaluator INSTANCE = new ByteGreaterEvaluator();

        private ByteGreaterEvaluator() {
            super( EvaluatorConstants.BYTE_TYPE,
            		EvaluatorConstants.GREATER );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            return ((Byte) object1).byteValue() > ((Byte) object2).byteValue();
        }

        public String toString() {
            return "Byte >";
        }
    }

    class ByteGreaterOrEqualEvaluator extends BaseEvaluator {
     
        public final static IEvaluator INSTANCE = new ByteGreaterOrEqualEvaluator();

        private ByteGreaterOrEqualEvaluator() {
            super( EvaluatorConstants.BYTE_TYPE,
            		EvaluatorConstants.GREATER_OR_EQUAL );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            return ((Byte) object1).byteValue() >= ((Byte) object2).byteValue();
        }

        public String toString() {
            return "Byte >=";
        }
    }