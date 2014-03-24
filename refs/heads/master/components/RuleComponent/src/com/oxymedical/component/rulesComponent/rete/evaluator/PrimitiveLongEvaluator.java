package com.oxymedical.component.rulesComponent.rete.evaluator;

import java.io.PrintStream;

import com.oxymedical.component.rulesComponent.IEvaluator;

// Referenced classes of package com.oxymedical.component.rulesComponent.rete.evaluator:
//            PrimitiveLongEqualEvaluator, PrimitiveLongNotEqualEvaluator, PrimitiveLongLessEvaluator, PrimitiveLongLessOrEqualEvaluator, 
//            PrimitiveLongGreaterEvaluator, PrimitiveLongGreaterOrEqualEvaluator

public class PrimitiveLongEvaluator
{

    public PrimitiveLongEvaluator()
    {
    }

    public static IEvaluator getPrimitiveLongEvaluator(String operator)
    {
        if(operator.equals("=") || operator.equals("=="))
        {
            return PrimitiveLongEqualEvaluator.INSTANCE;
        }
        if(operator.equals("!="))
        {
            return PrimitiveLongNotEqualEvaluator.INSTANCE;
        }
        if(operator.equals("<"))
        {
            return PrimitiveLongLessEvaluator.INSTANCE;
        }
        if(operator.equals("<="))
        {
            return PrimitiveLongLessOrEqualEvaluator.INSTANCE;
        }
        if(operator.equals(">"))
        {
            return PrimitiveLongGreaterEvaluator.INSTANCE;
        }
        if(operator.equals(">="))
        {
            return PrimitiveLongGreaterOrEqualEvaluator.INSTANCE;
        } else
        {
            return null;
        }
    }
}


class PrimitiveLongGreaterEvaluator extends BaseEvaluator
{

    private PrimitiveLongGreaterEvaluator()
    {
        super("long", ">");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        return Long.parseLong(object1.toString()) > Long.parseLong(object2.toString());
    }

    public String toString()
    {
        return "Long >";
    }

    public static final IEvaluator INSTANCE = new PrimitiveLongGreaterEvaluator();

}


class PrimitiveLongGreaterOrEqualEvaluator extends BaseEvaluator
{

    private PrimitiveLongGreaterOrEqualEvaluator()
    {
        super("long", ">=");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        return Long.parseLong(object1.toString()) >= Long.parseLong(object2.toString());
    }

    public String toString()
    {
        return "Long >=";
    }

    public static final IEvaluator INSTANCE = new PrimitiveLongGreaterOrEqualEvaluator();

}


class PrimitiveLongLessEvaluator extends BaseEvaluator
{

    private PrimitiveLongLessEvaluator()
    {
        super("long", "<");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        return Long.parseLong(object1.toString()) < Long.parseLong(object2.toString());
    }

    public String toString()
    {
        return "Long <";
    }

    public static final IEvaluator INSTANCE = new PrimitiveLongLessEvaluator();

}

class PrimitiveLongLessOrEqualEvaluator extends BaseEvaluator
{

    private PrimitiveLongLessOrEqualEvaluator()
    {
        super("long", "<=");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        return Long.parseLong(object1.toString()) <= Long.parseLong(object2.toString());
    }

    public String toString()
    {
        return "Long <=";
    }

    public static final IEvaluator INSTANCE = new PrimitiveLongLessOrEqualEvaluator();

}


class PrimitiveLongNotEqualEvaluator extends BaseEvaluator
{

    private PrimitiveLongNotEqualEvaluator()
    {
        super("long", "!=");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        if(object1 == null)
            return object2 != null;
        return Long.parseLong(object1.toString()) != Long.parseLong(object2.toString());
    }

    public String toString()
    {
        return "Long !=";
    }

    public static final IEvaluator INSTANCE = new PrimitiveLongNotEqualEvaluator();

}

class PrimitiveLongEqualEvaluator extends BaseEvaluator
{

    private PrimitiveLongEqualEvaluator()
    {
        super("long", "=");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        if(object1 == null)
            return object2 != null;
        return Long.parseLong(object1.toString()) == Long.parseLong(object2.toString());
    }

    public String toString()
    {
        return "Long ==";
    }

    public static final IEvaluator INSTANCE = new PrimitiveLongEqualEvaluator();

}