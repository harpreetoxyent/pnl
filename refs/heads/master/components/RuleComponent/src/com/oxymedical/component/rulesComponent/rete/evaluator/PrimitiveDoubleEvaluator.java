package com.oxymedical.component.rulesComponent.rete.evaluator;

import java.io.PrintStream;

import com.oxymedical.component.rulesComponent.IEvaluator;

// Referenced classes of package com.oxymedical.component.rulesComponent.rete.evaluator:
//            PrimitiveDoubleEqualEvaluator, PrimitiveDoubleNotEqualEvaluator, PrimitiveDoubleLessEvaluator, PrimitiveDoubleLessOrEqualEvaluator, 
//            PrimitiveDoubleGreaterEvaluator, PrimitiveDoubleGreaterOrEqualEvaluator

public class PrimitiveDoubleEvaluator
{

    public PrimitiveDoubleEvaluator()
    {
    }

    public static IEvaluator getPrimitiveDoubleEvaluator(String operator)
    {
        if(operator.equals("=") || operator.equals("=="))
        {
            return PrimitiveDoubleEqualEvaluator.INSTANCE;
        }
        if(operator.equals("!="))
        {
            return PrimitiveDoubleNotEqualEvaluator.INSTANCE;
        }
        if(operator.equals("<"))
        {
            return PrimitiveDoubleLessEvaluator.INSTANCE;
        }
        if(operator.equals("<="))
        {
            return PrimitiveDoubleLessOrEqualEvaluator.INSTANCE;
        }
        if(operator.equals(">"))
        {
            return PrimitiveDoubleGreaterEvaluator.INSTANCE;
        }
        if(operator.equals(">="))
        {
            return PrimitiveDoubleGreaterOrEqualEvaluator.INSTANCE;
        } else
        {
            return null;
        }
    }
}


class PrimitiveDoubleGreaterEvaluator extends BaseEvaluator
{

    private PrimitiveDoubleGreaterEvaluator()
    {
        super("double", ">");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        return Double.parseDouble(object1.toString()) > Double.parseDouble(object2.toString());
    }

    public String toString()
    {
        return "double >";
    }

    public static final IEvaluator INSTANCE = new PrimitiveDoubleGreaterEvaluator();

}


class PrimitiveDoubleGreaterOrEqualEvaluator extends BaseEvaluator
{

    private PrimitiveDoubleGreaterOrEqualEvaluator()
    {
        super("double", ">=");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        return Double.parseDouble(object1.toString()) >= Double.parseDouble(object2.toString());
    }

    public String toString()
    {
        return "double >=";
    }

    public static final IEvaluator INSTANCE = new PrimitiveDoubleGreaterOrEqualEvaluator();

}

class PrimitiveDoubleLessEvaluator extends BaseEvaluator
{

    private PrimitiveDoubleLessEvaluator()
    {
        super("double", "<");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        return Double.parseDouble(object1.toString()) < Double.parseDouble(object2.toString());
    }

    public String toString()
    {
        return "double <";
    }

    public static final IEvaluator INSTANCE = new PrimitiveDoubleLessEvaluator();

}


class PrimitiveDoubleLessOrEqualEvaluator extends BaseEvaluator
{

    private PrimitiveDoubleLessOrEqualEvaluator()
    {
        super("double", "<=");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        return Double.parseDouble(object1.toString()) <= Double.parseDouble(object2.toString());
    }

    public String toString()
    {
        return "double <=";
    }

    public static final IEvaluator INSTANCE = new PrimitiveDoubleLessOrEqualEvaluator();

}


class PrimitiveDoubleNotEqualEvaluator extends BaseEvaluator
{

    private PrimitiveDoubleNotEqualEvaluator()
    {
        super("double", "!=");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        if(object1 == null)
            return object2 != null;
        return Double.parseDouble(object1.toString()) != Double.parseDouble(object2.toString());
    }

    public String toString()
    {
        return "double !=";
    }

    public static final IEvaluator INSTANCE = new PrimitiveDoubleNotEqualEvaluator();

}


class PrimitiveDoubleEqualEvaluator extends BaseEvaluator
{

    private PrimitiveDoubleEqualEvaluator()
    {
        super("double", "=");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        if(object1 == null)
            return object2 != null;
        return Double.parseDouble(object1.toString()) == Double.parseDouble(object2.toString());
    }

    public String toString()
    {
        return "double ==";
    }

    public static final IEvaluator INSTANCE = new PrimitiveDoubleEqualEvaluator();

}