package com.oxymedical.core.commonData;

import java.util.Hashtable;

public interface IFormPattern extends Cloneable
{
	public Hashtable<String, Object> getFormValues();

	public void setFormValues(Hashtable<String, Object> formValues);
	
	public String getFormId();

	public void setFormId(String formId);
	
	public String getElementId();

	public void setElementId(String elementId);

	public String getElementValue();

	public void setElementValue(String elementValue);
	
	public IFormPattern clone();
}
