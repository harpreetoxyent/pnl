package com.oxymedical.core.renderdata;

import java.util.Hashtable;

public interface ICommonRenderData
{
	/**
	 * @return the elementId
	 */
	public String getElementId();


	/**
	 * @param elementId
	 *            the elementId to set
	 */
	public void setElementId(String elementId);


	/**
	 * @return the elementValue
	 */
	public String getElementValue();


	/**
	 * @param elementValue
	 *            the elementValue to set
	 */
	public void setElementValue(String elementValue);


	/**
	 * @return the formId
	 */
	public String getFormId();


	/**
	 * @param formId
	 *            the formId to set
	 */
	public void setFormId(String formId);

	/**
	 * @return
	 */
	public String getMethodName();

	/**
	 * @param methodName
	 */
	public void setMethodName(String methodName);

	public Hashtable<String, Object> getFormValues();

	public void setFormValues(Hashtable<String, Object> formValues);

}
