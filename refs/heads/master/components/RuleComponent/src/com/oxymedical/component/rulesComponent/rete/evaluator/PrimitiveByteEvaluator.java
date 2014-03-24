package com.oxymedical.component.rulesComponent.rete.evaluator;

import java.io.PrintStream;

import com.oxymedical.component.rulesComponent.IEvaluator;

// Referenced classes of package com.oxymedical.component.rulesComponent.rete.evaluator:
//            PrimitiveByteEqualEvaluator, PrimitiveByteNotEqualEvaluator, PrimitiveByteLessEvaluator, PrimitiveByteLessOrEqualEvaluator, 
//            PrimitiveByteGreaterEvaluator, PrimitiveByteGreaterOrEqualEvaluator

public class PrimitiveByteEvaluator
{

    public PrimitiveByteEvaluator()
    {
    }

    public static IEvaluator getPrimitiveByteEvaluator(String operator)
    {
        if(operator.equals("=") || operator.equals("=="))
        {
            return PrimitiveByteEqualEvaluator.INSTANCE;
        }
        if(operator.equals("!="))
        {
            return PrimitiveByteNotEqualEvaluator.INSTANCE;
        }
        if(operator.equals("<"))
        {
            return PrimitiveByteLessEvaluator.INSTANCE;
        }
        if(operator.equals("<="))
        {
            return PrimitiveByteLessOrEqualEvaluator.INSTANCE;
        }
        if(operator.equals(">"))
        {
            return PrimitiveByteGreaterEvaluator.INSTANCE;
        }
        if(operator.equals(">="))
        {
            return PrimitiveByteGreaterOrEqualEvaluator.INSTANCE;
        } else
        {
            return null;
        }
    }
}


class PrimitiveByteGreaterEvaluator extends BaseEvaluator
{

    private PrimitiveByteGreaterEvaluator()
    {
        super("byte", ">");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        return Byte.parseByte(object1.toString()) > Byte.parseByte(object2.toString());
    }

    public String toString()
    {
        return "byte >";
    }

    public static final IEvaluator INSTANCE = new PrimitiveByteGreaterEvaluator();

}

class PrimitiveByteGreaterOrEqualEvaluator extends BaseEvaluator
{

    private PrimitiveByteGreaterOrEqualEvaluator()
    {
        super("byte", ">=");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        return Byte.parseByte(object1.toString()) >= Byte.parseByte(object2.toString());
    }

    public String toString()
    {
        return "byte >=";
    }

    public static final IEvaluator INSTANCE = new PrimitiveByteGreaterOrEqualEvaluator();

}


class PrimitiveByteLessEvaluator extends BaseEvaluator
{

    private PrimitiveByteLessEvaluator()
    {
        super("byte", "<");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        return Byte.parseByte(object1.toString()) < Byte.parseByte(object2.toString());
    }

    public String toString()
    {
        return "byte <";
    }

    public static final IEvaluator INSTANCE = new PrimitiveByteLessEvaluator();

}


class PrimitiveByteLessOrEqualEvaluator extends BaseEvaluator
{

    private PrimitiveByteLessOrEqualEvaluator()
    {
        super("byte", "<=");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        return Byte.parseByte(object1.toString()) <= Byte.parseByte(object2.toString());
    }

    public String toString()
    {
        return "byte <=";
    }

    public static final IEvaluator INSTANCE = new PrimitiveByteLessOrEqualEvaluator();

}

class PrimitiveByteNotEqualEvaluator extends BaseEvaluator
{

    private PrimitiveByteNotEqualEvaluator()
    {
        super("byte", "!=");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        if(object1 == null)
            return object2 != null;
        return Byte.parseByte(object1.toString()) != Byte.parseByte(object2.toString());
    }

    public String toString()
    {
        return "byte !=";
    }

    public static final IEvaluator INSTANCE = new PrimitiveByteNotEqualEvaluator();

}


class PrimitiveByteEqualEvaluator extends BaseEvaluator
{

    private PrimitiveByteEqualEvaluator()
    {
        super("byte", "=");
    }

    public boolean evaluate(Object object1, Object object2)
    {
        if(object1 == null)
            return object2 != null;
        return Byte.parseByte(object1.toString()) == Byte.parseByte(object2.toString());
    }

    public String toString()
    {
        return "byte ==";
    }

    public static final IEvaluator INSTANCE = new PrimitiveByteEqualEvaluator();

}
