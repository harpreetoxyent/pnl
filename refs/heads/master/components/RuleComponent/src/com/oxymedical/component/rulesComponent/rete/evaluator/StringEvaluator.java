package com.oxymedical.component.rulesComponent.rete.evaluator;

import com.oxymedical.component.rulesComponent.IEvaluator;
import com.oxymedical.component.rulesComponent.constants.EvaluatorConstants;


/**
 * For handling string types.
 * 
 * @author Oxyent Medical
 */
public class StringEvaluator {
	
	public static IEvaluator getStringEvaluator(final String operator) {
		if (operator.equals(EvaluatorConstants.EQUAL) || operator.equals(EvaluatorConstants.DOUBLE_EQUAL))
                return StringEqualEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.NOT_EQUAL))
                return StringNotEqualEvaluator.INSTANCE;
        if (operator.equals(EvaluatorConstants.MATCHES))
                return StringMatchesEvaluator.INSTANCE;
            return null;
        }
    }

    class StringEqualEvaluator extends BaseEvaluator {
       
        public final static IEvaluator INSTANCE = new StringEqualEvaluator();

        private StringEqualEvaluator() {
            super( EvaluatorConstants.STRING_TYPE,
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
            return "String ==";
        }
    }

    class StringNotEqualEvaluator extends BaseEvaluator {
       
        public final static IEvaluator INSTANCE = new StringNotEqualEvaluator();

        private StringNotEqualEvaluator() {
            super( EvaluatorConstants.STRING_TYPE,
                   EvaluatorConstants.NOT_EQUAL );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            if ( object1 == null ) {
                return !(object2 == null);
            }

            return !object1.equals( object2 );
        }

        public String toString() {
            return "String !=";
        }
    }

    class StringMatchesEvaluator extends BaseEvaluator {
       
        public final static IEvaluator INSTANCE = new StringMatchesEvaluator();

        private StringMatchesEvaluator() {
            super( EvaluatorConstants.STRING_TYPE,
            		EvaluatorConstants.MATCHES );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            final String pattern = (String) object2;
            final String target = (String) object1;

            if ( object1 == null ) {
                return false;
            }

            //TODO: possibly use a WeakHashMap cache of regex expressions
            //downside is could cause a lot of hashing if the patterns are dynamic
            //if the patterns are static, then it will not be a problem. Perhaps compiler can recognise patterns
            //in the input string using /pattern/ etc.. and precompile it, in which case object2 will be a Pattern.
            return target.matches( pattern );
        }

        public String toString() {
            return "String !=";
        }
    }