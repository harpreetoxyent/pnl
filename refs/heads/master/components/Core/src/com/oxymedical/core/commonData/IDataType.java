package com.oxymedical.core.commonData;

import java.util.Hashtable;
import java.util.List;

public interface IDataType {
	public Hashtable<String, String> getDataSet();
	public void setDataSet(Hashtable<String, String> dataSet);
	public List<String> getDataList();
	public void setDataList(List<String> dataList);
}
