package com.oxymedical.component.rulesComponent.rete.evaluator;

import com.oxymedical.component.rulesComponent.IEvaluator;
import com.oxymedical.component.rulesComponent.constants.EvaluatorConstants;


/**
 * For handling Integer types.
 * 
 * @author Oxyent Medical
 */
public class IntegerEvaluator {

	 public static IEvaluator getIntegerEvaluator(final String operator) {
		 
	        if (operator.equals(EvaluatorConstants.EQUAL) || operator.equals(EvaluatorConstants.DOUBLE_EQUAL)) 
	            return IntegerEqualEvaluator.INSTANCE;
	        if (operator.equals(EvaluatorConstants.NOT_EQUAL))
	            return IntegerNotEqualEvaluator.INSTANCE;
	        if (operator.equals(EvaluatorConstants.LESS))
	            return IntegerLessEvaluator.INSTANCE;
	        if (operator.equals(EvaluatorConstants.LESS_OR_EQUAL))
	            return IntegerLessOrEqualEvaluator.INSTANCE;
	        if (operator.equals(EvaluatorConstants.GREATER))
	            return IntegerGreaterEvaluator.INSTANCE;
	        if (operator.equals(EvaluatorConstants.GREATER_OR_EQUAL))
	            return IntegerGreaterOrEqualEvaluator.INSTANCE;	            
	        
	        return null;
	 }
 }


	    class IntegerEqualEvaluator extends BaseEvaluator {
	       
	        public final static IEvaluator INSTANCE = new IntegerEqualEvaluator();

	        private IntegerEqualEvaluator() {
	            super( EvaluatorConstants.INTEGER_TYPE,
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
	            return "Integer ==";
	        }
	    }

	   class IntegerNotEqualEvaluator extends BaseEvaluator {
	       
	        public final static IEvaluator INSTANCE = new IntegerNotEqualEvaluator();

	        private IntegerNotEqualEvaluator() {
	            super( EvaluatorConstants.INTEGER_TYPE,
	            		EvaluatorConstants.NOT_EQUAL );
	        }

	        public boolean evaluate(final Object object1,
	                                final Object object2) {
	            if ( object1 == null ) {
	                return (object2 != null);
	            }
	            return !((Number) object1).equals( object2 );
	        }

	        public String toString() {
	            return "Integer !=";
	        }
	    }

	    class IntegerLessEvaluator extends BaseEvaluator {
	        
	        public final static IEvaluator INSTANCE = new IntegerLessEvaluator();

	        private IntegerLessEvaluator() {
	            super( EvaluatorConstants.INTEGER_TYPE,
	            		EvaluatorConstants.LESS );
	        }

	        public boolean evaluate(final Object object1,
	                                final Object object2) {
	            return ((Number) object1).intValue() < ((Number) object2).intValue();
	        }

	        public String toString() {
	            return "Integer <";
	        }
	    }

	    class IntegerLessOrEqualEvaluator extends BaseEvaluator {
	        
	        public final static IEvaluator INSTANCE = new IntegerLessOrEqualEvaluator();

	        private IntegerLessOrEqualEvaluator() {
	            super( EvaluatorConstants.INTEGER_TYPE,
	            		EvaluatorConstants.LESS_OR_EQUAL );
	        }

	        public boolean evaluate(final Object object1,
	                                final Object object2) {
	            return ((Number) object1).intValue() <= ((Number) object2).intValue();
	        }

	        public String toString() {
	            return "Integer <=";
	        }
	    }

	   class IntegerGreaterEvaluator extends BaseEvaluator {
	      
	        public final static IEvaluator INSTANCE = new IntegerGreaterEvaluator();

	        private IntegerGreaterEvaluator() {
	            super( EvaluatorConstants.INTEGER_TYPE,
	            		EvaluatorConstants.GREATER );
	        }

	        public boolean evaluate(final Object object1,
	                                final Object object2) {
	            return ((Number) object1).intValue() > ((Number) object2).intValue();
	        }

	        public String toString() {
	            return "Integer >";
	        }
	    }

	    class IntegerGreaterOrEqualEvaluator extends BaseEvaluator {
	      
	        public final static IEvaluator INSTANCE = new IntegerGreaterOrEqualEvaluator();

	        private IntegerGreaterOrEqualEvaluator() {
	            super( EvaluatorConstants.INTEGER_TYPE,
	            		EvaluatorConstants.GREATER_OR_EQUAL );
	        }

	        public boolean evaluate(final Object object1,
	                                final Object object2) {
	            return ((Number) object1).intValue() >= ((Number) object2).intValue();
	        }

	        public String toString() {
	            return "Integer >=";
	        }
	    }
