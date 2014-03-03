package com.oxymedical.component.logging;

import java.util.ArrayList;

import com.oxymedical.component.logging.settings.Field;

/**
 * IPattern Interface is userd by pattern
 * 
 **/
public interface IPattern 
{
	public ArrayList<Field> getPattern();
	public void setPattern(ArrayList<Field> pattern);
}
