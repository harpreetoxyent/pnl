package com.oxymedical.core.commonData;

import java.io.Serializable;

public class MetaData implements IMetaData,Serializable{
	private Object commonObject;

	/**
	 * @return the commonObject
	 */
	public Object getCommonObject() {
		return commonObject;
	}

	/**
	 * @param commonObject the commonObject to set
	 */
	public void setCommonObject(Object commonObject) {
		this.commonObject = commonObject;
	}

	
}
