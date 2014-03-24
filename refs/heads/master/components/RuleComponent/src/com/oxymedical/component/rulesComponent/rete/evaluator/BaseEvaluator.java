package com.oxymedical.component.rulesComponent.rete.evaluator;

import com.oxymedical.component.rulesComponent.IEvaluator;

/**
 * BaseEvaluator is an Object Comparator
 * 
 * @author Oxyent Medical
 * 
 */
public abstract class BaseEvaluator
    implements
    IEvaluator {

    private final String operator;

    private final String type;

    public BaseEvaluator(final String type,
                         final String operator) {
        this.type = type;
        this.operator = operator;
    }

    public String getOperator() {
        return this.operator;
    }

    public String getType() {
        return this.type;
    }

    public abstract boolean evaluate(Object object1,
                                     Object object2);

    public boolean equals(final Object other) {
        if ( this == other ) {
            return true;
        }
        if ( !this.getClass().equals( other.getClass() ) ) {
            return false;
        }
        return (this.getOperator() == ((IEvaluator) other).getOperator()) && (this.getType() == ((IEvaluator) other).getType());
    }

    public int hashCode() {
        return 0;
    }

}