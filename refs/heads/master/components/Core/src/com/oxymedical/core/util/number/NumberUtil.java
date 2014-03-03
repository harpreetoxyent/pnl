package com.oxymedical.core.util.number;

public class NumberUtil
{
	public static boolean isNumeric(String s) 
	{
        return s.matches ("[0-9]+");
    }
}
