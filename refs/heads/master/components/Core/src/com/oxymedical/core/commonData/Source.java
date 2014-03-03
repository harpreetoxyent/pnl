package com.oxymedical.core.commonData;

public class Source implements ISource
{
	String component_id;
	String methodName;

	
	public Source()
	{
		
	}
	public Source(String component_id) {
		super();
		this.component_id = component_id;
	}

	public String getComponent_id() {
		return component_id;
	}

	public void setComponent_id(String component_id) {
		this.component_id = component_id;
	}
	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
}
