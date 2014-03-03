package com.oxymedical.core.commonData;

import java.util.Hashtable;
import java.util.List;

public class DataType implements IDataType, java.io.Serializable{
	private Hashtable<String,String> dataSet = null;
	private List<String> dataList = null;
	

	/**
	 * @return the dataSet
	 */
	public Hashtable<String, String> getDataSet() {
		return dataSet;
	}

	/**
	 * @param dataSet the dataSet to set
	 */
	public void setDataSet(Hashtable<String, String> dataSet) {
		this.dataSet = dataSet;
	}

	/**
	 * @return the dataList
	 */
	public List<String> getDataList() {
		return dataList;
	}

	/**
	 * @param dataList the dataList to set
	 */
	public void setDataList(List<String> dataList) {
		this.dataList = dataList;
	}

	
}
