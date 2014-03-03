package com.oxymedical.core.commonData;

import java.util.Hashtable;

public class FormPattern implements IFormPattern, java.io.Serializable
{
	private String formId = null;
	private String elementId = null;
	private String elementValue = null;
	private Hashtable<String, Object> formValues = null;
	
	public String getFormId()
	{
		return formId;
	}
	public void setFormId(String formId)
	{
		this.formId = formId;
	}
	public String getElementId()
	{
		return elementId;
	}
	public void setElementId(String elementId)
	{
		this.elementId = elementId;
	}
	public String getElementValue()
	{
		return elementValue;
	}
	public void setElementValue(String elementValue)
	{
		this.elementValue = elementValue;
	}
	public Hashtable<String, Object> getFormValues()
	{
		return formValues;
	}
	public void setFormValues(Hashtable<String, Object> formValues)
	{
		this.formValues = formValues;
	}
	public void addFormValue(String key, Object value)
	{
		if (this.formValues == null) formValues = new Hashtable<String, Object>();
		this.formValues.put(key, value);
	}
	public void addFormValue(String key, String value)
	{
		addFormValue(key, (Object)value);
	}
	
	public IFormPattern clone()
	{
		IFormPattern fp = new FormPattern();
		fp.setFormId(formId);
		fp.setElementId(this.elementId);
		fp.setElementValue(elementValue);
		fp.setFormValues((Hashtable<String, Object>) formValues.clone());
		return fp;
	}
}
