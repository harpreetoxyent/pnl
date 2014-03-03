package com.oxymedical.core.renderdata;

import java.util.LinkedList;

public class ExternalData
{
	private LinkedList<Object> keys;
	private LinkedList<Object> values;
	public LinkedList<Object> getKeys()
	{
		return keys;
	}
	public void setKeys(LinkedList<Object> keys)
	{
		this.keys = keys;
	}
	public LinkedList<Object> getValues()
	{
		return values;
	}
	public void setValues(LinkedList<Object> values)
	{
		this.values = values;
	}
}
