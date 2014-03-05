package com.oxymedical.component.renderer.data;

import java.util.Hashtable;

/**
 *  This class defines the common data to be used for rendering
 * 
 * @author Oxyent Medical
 * @version 1.0.0
 */
public class CommonRenderingData {
	private String formId = null;
	private String elementId = null;
	private String elementValue = null;
	private String methodName = null;
	private Hashtable<String, Object> formValues= null;
	/**
	 * @return the elementId
	 */
	public String getElementId() {
		return elementId;
	}
	/**
	 * @param elementId the elementId to set
	 */
	public void setElementId(String elementId) {
		this.elementId = elementId;
	}
	/**
	 * @return the elementValue
	 */
	public String getElementValue() {
		return elementValue;
	}
	/**
	 * @param elementValue the elementValue to set
	 */
	public void setElementValue(String elementValue) {
		this.elementValue = elementValue;
	}
	/**
	 * @return the formId
	 */
	public String getFormId() {
		return formId;
	}
	/**
	 * @param formId the formId to set
	 */
	public void setFormId(String formId) {
		this.formId = formId;
	}
	/**
	 * @return
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * @param methodName
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Hashtable<String, Object> getFormValues() {
		return formValues;
	}
	public void setFormValues(Hashtable<String, Object> formValues) {
		this.formValues = new Hashtable<String, Object>();
		this.formValues = formValues;
	}
	

	

}
