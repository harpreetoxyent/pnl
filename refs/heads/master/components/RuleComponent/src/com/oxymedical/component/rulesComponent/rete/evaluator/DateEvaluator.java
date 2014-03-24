package com.oxymedical.component.rulesComponent.rete.evaluator;

import java.util.Date;

import com.oxymedical.component.rulesComponent.IEvaluator;
import com.oxymedical.component.rulesComponent.constants.EvaluatorConstants;
import com.oxymedical.component.rulesComponent.util.DateUtil;


/**
 * This will generate evaluators that handle dates.
 * This will also parse strings into dates, according to 
 * DEFAULT_FORMAT_MASK, unless it is overridden by the drools.dateformat system property.
 * 
 * When parsing dates from a string, no time is included.
 * 
 * So you can do expressions like 
 * <code>Person(birthday <= "10-Jul-1974")</code> etc.
 * 
 * @author Oxyent Medical
 */
public class DateEvaluator {
   
    public static IEvaluator getDateEvaluator(final String operator) {
       if (operator.equals(EvaluatorConstants.EQUAL) || operator.equals(EvaluatorConstants.DOUBLE_EQUAL))
                return DateEqualEvaluator.INSTANCE;
       if (operator.equals(EvaluatorConstants.NOT_EQUAL))
                return DateNotEqualEvaluator.INSTANCE;
       if (operator.equals(EvaluatorConstants.LESS))
                return DateLessEvaluator.INSTANCE;
       if (operator.equals(EvaluatorConstants.LESS_OR_EQUAL))
                return DateLessOrEqualEvaluator.INSTANCE;
       if (operator.equals(EvaluatorConstants.GREATER))
                return DateGreaterEvaluator.INSTANCE;
       if (operator.equals(EvaluatorConstants.GREATER_OR_EQUAL))
                return DateGreaterOrEqualEvaluator.INSTANCE;
           return null;
        }
    
   }

   class DateEqualEvaluator extends BaseEvaluator {
       
        public final static IEvaluator INSTANCE = new DateEqualEvaluator();

        private DateEqualEvaluator() {
            super( EvaluatorConstants.DATE_TYPE,
            		EvaluatorConstants.EQUAL );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            if ( object1 == null ) {
                return object2 == null;
            }
            if ( object2 == null ) {
                return false;
            }
            final Date left = (Date) object1;

            if ( left.compareTo( DateUtil.getRightDate( object2 ) ) == 0 ) {
                return true;
            } else {
                return false;
            }
        }

        public String toString() {
            return "Date ==";
        }
    }

    class DateNotEqualEvaluator extends BaseEvaluator {
      
        public final static IEvaluator INSTANCE = new DateNotEqualEvaluator();

        private DateNotEqualEvaluator() {
            super( EvaluatorConstants.DATE_TYPE,
            		EvaluatorConstants.NOT_EQUAL );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            if ( object1 == null ) {
                return object2 != null;
            }
            if ( object2 == null ) {
                return true;
            }
            final Date left = (Date) object1;
            if ( left.compareTo( DateUtil.getRightDate( object2 ) ) != 0 ) {
                return true;
            } else {
                return false;
            }
        }

        public String toString() {
            return "Date !=";
        }
    }

    class DateLessEvaluator extends BaseEvaluator {
      
        public final static IEvaluator INSTANCE         = new DateLessEvaluator();

        private DateLessEvaluator() {
            super( EvaluatorConstants.DATE_TYPE,
            		EvaluatorConstants.LESS );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            final Date left = (Date) object1;
            if ( left.compareTo( DateUtil.getRightDate( object2 ) ) < 0 ) {
                return true;
            } else {
                return false;
            }
        }

        public String toString() {
            return "Date <";
        }
    }

    class DateLessOrEqualEvaluator extends BaseEvaluator {
      
        public final static IEvaluator INSTANCE         = new DateLessOrEqualEvaluator();

        private DateLessOrEqualEvaluator() {
            super( EvaluatorConstants.DATE_TYPE,
            		EvaluatorConstants.LESS_OR_EQUAL );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            final Date left = (Date) object1;
            if ( left.compareTo( DateUtil.getRightDate( object2 ) ) <= 0 ) {
                return true;
            } else {
                return false;
            }
        }

        public String toString() {
            return "Date <=";
        }
    }

    class DateGreaterEvaluator extends BaseEvaluator {
        
        public final static IEvaluator INSTANCE = new DateGreaterEvaluator();

        private DateGreaterEvaluator() {
            super( EvaluatorConstants.DATE_TYPE,
            		EvaluatorConstants.GREATER );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            final Date left = (Date) object1;
            if ( left.compareTo( DateUtil.getRightDate( object2 ) ) > 0 ) {
                return true;
            } else {
                return false;
            }
        }

        public String toString() {
            return "Date >";
        }
    }

   class DateGreaterOrEqualEvaluator extends BaseEvaluator {
       
        public final static IEvaluator INSTANCE = new DateGreaterOrEqualEvaluator();

        private DateGreaterOrEqualEvaluator() {
            super( EvaluatorConstants.DATE_TYPE,
            		EvaluatorConstants.GREATER_OR_EQUAL );
        }

        public boolean evaluate(final Object object1,
                                final Object object2) {
            final Date left = (Date) object1;
            if ( left.compareTo( DateUtil.getRightDate( object2 ) ) >= 0 ) {
                return true;
            } else {
                return false;
            }
        }

        public String toString() {
            return "Date >=";
        }
 
 }