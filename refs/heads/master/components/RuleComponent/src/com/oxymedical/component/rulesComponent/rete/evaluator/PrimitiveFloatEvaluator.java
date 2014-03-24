package com.oxymedical.component.rulesComponent.rete.evaluator;

import java.io.PrintStream;

import com.oxymedical.component.rulesComponent.IEvaluator;

// Referenced classes of package com.oxymedical.component.rulesComponent.rete.evaluator:
//            PrimitiveFloatEqualEvaluator, PrimitiveFloatNotEqualEvaluator, PrimitiveFloatLessEvaluator, PrimitiveFloatLessOrEqualEvaluator, 
//            PrimitiveFloatGreaterEvaluator, PrimitiveFloatGreaterOrEqualEvaluator

public class PrimitiveFloatEvaluator
{

    public PrimitiveFloatEvaluator()
    {
    }

    public static IEvaluator getPrimitiveFloatEvaluator(String operator)
    {
        if(operator.equals("=") || operator.equals("=="))
        {
            return PrimitiveFloatEqualEvaluator.INSTANCE;
        }
        if(operator.equals("!="))
        {
            return PrimitiveFloatNotEqualEvaluator.INSTANCE;
        }
        if(operator.equals("<"))
        {
            return PrimitiveFloatLessEvaluator.INSTANCE;
        }
        if(operator.equals("<="))
        {
            return PrimitiveFloatLessOrEqualEvaluator.INSTANCE;
        }
        if(operator.equals(">"))
        {
            return PrimitiveFloatGreaterEvaluator.INSTANCE;
        }
        if(operator.equals(">="))
        {
            return PrimitiveFloatGreaterOrEqualEvaluator.INSTANCE;
        } else
        {
            return null;
        }
    }
}


class PrimitiveFloatGreaterEvaluator extends BaseEvaluator
{

    private PrimitiveFloatGreaterEvaluator()
    {
        super("float", ">");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        return Float.parseFloat(object1.toString()) > Float.parseFloat(object2.toString());
    }

    public String toString()
    {
        return "float >";
    }

    public static final IEvaluator INSTANCE = new PrimitiveFloatGreaterEvaluator();

}


class PrimitiveFloatGreaterOrEqualEvaluator extends BaseEvaluator
{

    private PrimitiveFloatGreaterOrEqualEvaluator()
    {
        super("float", ">=");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        return Float.parseFloat(object1.toString()) >= Float.parseFloat(object2.toString());
    }

    public String toString()
    {
        return "float >=";
    }

    public static final IEvaluator INSTANCE = new PrimitiveFloatGreaterOrEqualEvaluator();

}


class PrimitiveFloatLessEvaluator extends BaseEvaluator
{

    private PrimitiveFloatLessEvaluator()
    {
        super("float", "<");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        return Float.parseFloat(object1.toString()) < Float.parseFloat(object2.toString());
    }

    public String toString()
    {
        return "float <";
    }

    public static final IEvaluator INSTANCE = new PrimitiveFloatLessEvaluator();

}


class PrimitiveFloatLessOrEqualEvaluator extends BaseEvaluator
{

    private PrimitiveFloatLessOrEqualEvaluator()
    {
        super("float", "<=");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        return Float.parseFloat(object1.toString()) <= Float.parseFloat(object2.toString());
    }

    public String toString()
    {
        return "float <=";
    }

    public static final IEvaluator INSTANCE = new PrimitiveFloatLessOrEqualEvaluator();

}

class PrimitiveFloatNotEqualEvaluator extends BaseEvaluator
{

    private PrimitiveFloatNotEqualEvaluator()
    {
        super("float", "!=");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        if(object1 == null)
            return object2 != null;
        return Float.parseFloat(object1.toString()) != Float.parseFloat(object2.toString());
    }

    public String toString()
    {
        return "float !=";
    }

    public static final IEvaluator INSTANCE = new PrimitiveFloatNotEqualEvaluator();

}

class PrimitiveFloatEqualEvaluator extends BaseEvaluator
{

    private PrimitiveFloatEqualEvaluator()
    {
        super("float", "=");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        if(object1 == null)
            return object2 != null;
        return Float.parseFloat(object1.toString()) == Float.parseFloat(object2.toString());
    }

    public String toString()
    {
        return "float ==";
    }

    public static final IEvaluator INSTANCE = new PrimitiveFloatEqualEvaluator();

}
