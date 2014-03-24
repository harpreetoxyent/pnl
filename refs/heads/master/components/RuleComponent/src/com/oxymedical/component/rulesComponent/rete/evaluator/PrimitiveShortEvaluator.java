package com.oxymedical.component.rulesComponent.rete.evaluator;

import java.io.PrintStream;

import com.oxymedical.component.rulesComponent.IEvaluator;

// Referenced classes of package com.oxymedical.component.rulesComponent.rete.evaluator:
//            PrimitiveShortEqualEvaluator, PrimitiveShortNotEqualEvaluator, PrimitiveShortLessEvaluator, PrimitiveShortLessOrEqualEvaluator, 
//            PrimitiveShortGreaterEvaluator, PrimitiveShortGreaterOrEqualEvaluator

public class PrimitiveShortEvaluator
{

    public PrimitiveShortEvaluator()
    {
    }

    public static IEvaluator getPrimitiveShortEvaluator(String operator)
    {
        if(operator.equals("=") || operator.equals("=="))
        {
            return PrimitiveShortEqualEvaluator.INSTANCE;
        }
        if(operator.equals("!="))
        {
            return PrimitiveShortNotEqualEvaluator.INSTANCE;
        }
        if(operator.equals("<"))
        {
            return PrimitiveShortLessEvaluator.INSTANCE;
        }
        if(operator.equals("<="))
        {
            return PrimitiveShortLessOrEqualEvaluator.INSTANCE;
        }
        if(operator.equals(">"))
        {
            return PrimitiveShortGreaterEvaluator.INSTANCE;
        }
        if(operator.equals(">="))
        {
            return PrimitiveShortGreaterOrEqualEvaluator.INSTANCE;
        } else
        {
            return null;
        }
    }
}


class PrimitiveShortGreaterEvaluator extends BaseEvaluator
{

    private PrimitiveShortGreaterEvaluator()
    {
        super("short", ">");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        return Short.parseShort(object1.toString()) > Short.parseShort(object2.toString());
    }

    public String toString()
    {
        return "short >";
    }

    public static final IEvaluator INSTANCE = new PrimitiveShortGreaterEvaluator();

}


class PrimitiveShortGreaterOrEqualEvaluator extends BaseEvaluator
{

    private PrimitiveShortGreaterOrEqualEvaluator()
    {
        super("short", ">=");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        return Short.parseShort(object1.toString()) >= Short.parseShort(object2.toString());
    }

    public String toString()
    {
        return "short >=";
    }

    public static final IEvaluator INSTANCE = new PrimitiveShortGreaterOrEqualEvaluator();

}

class PrimitiveShortLessEvaluator extends BaseEvaluator
{

    private PrimitiveShortLessEvaluator()
    {
        super("short", "<");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        return Short.parseShort(object1.toString()) < Short.parseShort(object2.toString());
    }

    public String toString()
    {
        return "short <";
    }

    public static final IEvaluator INSTANCE = new PrimitiveShortLessEvaluator();

}


class PrimitiveShortLessOrEqualEvaluator extends BaseEvaluator
{

    private PrimitiveShortLessOrEqualEvaluator()
    {
        super("short", "<=");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        return Short.parseShort(object1.toString()) <= Short.parseShort(object2.toString());
    }

    public String toString()
    {
        return "short <=";
    }

    public static final IEvaluator INSTANCE = new PrimitiveShortLessOrEqualEvaluator();

}


class PrimitiveShortNotEqualEvaluator extends BaseEvaluator
{

    private PrimitiveShortNotEqualEvaluator()
    {
        super("short", "!=");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        if(object1 == null)
            return object2 != null;
        return Short.parseShort(object1.toString()) != Short.parseShort(object2.toString());
    }

    public String toString()
    {
        return "short !=";
    }

    public static final IEvaluator INSTANCE = new PrimitiveShortNotEqualEvaluator();

}

class PrimitiveShortEqualEvaluator extends BaseEvaluator
{

    private PrimitiveShortEqualEvaluator()
    {
        super("short", "=");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        if(object1 == null)
            return object2 != null;
        return Short.parseShort(object1.toString()) == Short.parseShort(object2.toString());
    }

    public String toString()
    {
        return "short ==";
    }

    public static final IEvaluator INSTANCE = new PrimitiveShortEqualEvaluator();

}