package com.oxymedical.component.rulesComponent.rete.evaluator;

import com.oxymedical.component.rulesComponent.IEvaluator;

// Referenced classes of package com.oxymedical.component.rulesComponent.rete.evaluator:
//            PrimitiveBooleanEqualEvaluator, PrimitiveBooleanNotEqualEvaluator

public class PrimitiveBooleanEvaluator
{

    public PrimitiveBooleanEvaluator()
    {
    }

    public static IEvaluator getPrimitiveBooleanEvaluator(String operator)
    {
        if(operator.equals("=") || operator.equals("=="))
            return PrimitiveBooleanEqualEvaluator.INSTANCE;
        if(operator.equals("!="))
            return PrimitiveBooleanNotEqualEvaluator.INSTANCE;
        else
            return null;
    }
}

class PrimitiveBooleanNotEqualEvaluator extends BaseEvaluator
{

 private PrimitiveBooleanNotEqualEvaluator()
 {
     super("boolean", "!=");
 }

 public boolean evaluate(Object object1, Object object2)
 {
     if(object1 == null)
         return object2 != null;
     else
         return Boolean.parseBoolean(object1.toString()) != Boolean.parseBoolean(object2.toString());
 }

 public String toString()
 {
     return "Boolean !=";
 }

 public static final IEvaluator INSTANCE = new PrimitiveBooleanNotEqualEvaluator();

}

class PrimitiveBooleanEqualEvaluator extends BaseEvaluator
{

    private PrimitiveBooleanEqualEvaluator()
    {
        super("boolean", "=");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        if(object1 == null)
            return object2 == null;
        return Boolean.parseBoolean(object1.toString()) == Boolean.parseBoolean(object2.toString());
    }

    public String toString()
    {
        return "Boolean ==";
    }

    public static final IEvaluator INSTANCE = new PrimitiveBooleanEqualEvaluator();

}

