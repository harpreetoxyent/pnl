package com.oxymedical.component.rulesComponent.rete.evaluator;

import java.io.PrintStream;

import com.oxymedical.component.rulesComponent.IEvaluator;

// Referenced classes of package com.oxymedical.component.rulesComponent.rete.evaluator:
//            PrimitiveIntEqualEvaluator, PrimitiveIntNotEqualEvaluator, PrimitiveIntLessEvaluator, PrimitiveIntLessOrEqualEvaluator, 
//            PrimitiveIntGreaterEvaluator, PrimitiveIntGreaterOrEqualEvaluator

public class PrimitiveIntEvaluator
{

    public PrimitiveIntEvaluator()
    {
    }

    public static IEvaluator getPrimitiveIntEvaluator(String operator)
    {
        if(operator.equals("=") || operator.equals("=="))
        {
            return PrimitiveIntEqualEvaluator.INSTANCE;
        }
        if(operator.equals("!="))
        {
            return PrimitiveIntNotEqualEvaluator.INSTANCE;
        }
        if(operator.equals("<"))
        {
            return PrimitiveIntLessEvaluator.INSTANCE;
        }
        if(operator.equals("<="))
        {
            return PrimitiveIntLessOrEqualEvaluator.INSTANCE;
        }
        if(operator.equals(">"))
        {
            return PrimitiveIntGreaterEvaluator.INSTANCE;
        }
        if(operator.equals(">="))
        {
            return PrimitiveIntGreaterOrEqualEvaluator.INSTANCE;
        } else
        {
            return null;
        }
    }
}


class PrimitiveIntGreaterEvaluator extends BaseEvaluator
{

    private PrimitiveIntGreaterEvaluator()
    {
        super("int", ">");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        return Integer.parseInt(object1.toString()) > Integer.parseInt(object2.toString());
    }

    public String toString()
    {
        return "int >";
    }

    public static final IEvaluator INSTANCE = new PrimitiveIntGreaterEvaluator();

}


class PrimitiveIntGreaterOrEqualEvaluator extends BaseEvaluator
{

    private PrimitiveIntGreaterOrEqualEvaluator()
    {
        super("int", ">=");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        return Integer.parseInt(object1.toString()) >= Integer.parseInt(object2.toString());
    }

    public String toString()
    {
        return "int >=";
    }

    public static final IEvaluator INSTANCE = new PrimitiveIntGreaterOrEqualEvaluator();

}


class PrimitiveIntLessEvaluator extends BaseEvaluator
{

    private PrimitiveIntLessEvaluator()
    {
        super("int", "<");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        return Integer.parseInt(object1.toString()) < Integer.parseInt(object2.toString());
    }

    public String toString()
    {
        return "int <";
    }

    public static final IEvaluator INSTANCE = new PrimitiveIntLessEvaluator();

}

class PrimitiveIntLessOrEqualEvaluator extends BaseEvaluator
{

    private PrimitiveIntLessOrEqualEvaluator()
    {
        super("int", "<=");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        return Integer.parseInt(object1.toString()) <= Integer.parseInt(object2.toString());
    }

    public String toString()
    {
        return "int <=";
    }

    public static final IEvaluator INSTANCE = new PrimitiveIntLessOrEqualEvaluator();

}


class PrimitiveIntNotEqualEvaluator extends BaseEvaluator
{

    private PrimitiveIntNotEqualEvaluator()
    {
        super("int", "!=");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        if(object1 == null)
            return object2 != null;
        return Integer.parseInt(object1.toString()) != Integer.parseInt(object2.toString());
    }

    public String toString()
    {
        return "int !=";
    }

    public static final IEvaluator INSTANCE = new PrimitiveIntNotEqualEvaluator();

}

class PrimitiveIntEqualEvaluator extends BaseEvaluator
{

    private PrimitiveIntEqualEvaluator()
    {
        super("int", "=");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        if(object1 == null)
            return object2 != null;
        return Integer.parseInt(object1.toString()) == Integer.parseInt(object2.toString());
    }

    public String toString()
    {
        return "int ==";
    }

    public static final IEvaluator INSTANCE = new PrimitiveIntEqualEvaluator();

}
