package com.oxymedical.component.renderer.data;
import java.util.Hashtable;
import java.util.List;
/**
 *  This class defines the data-structure to be used while processing rendering data 
 *   
 * @author Oxyent Medical
 * @version 1.0.0
 */
public class DataUnit extends CommonRenderingData{
	private boolean isTrue=false;
	private String parentId = null;
	private List list=null;
	private String sqlQuery=null;
	private QueryData queryData = null;
	private Hashtable columnOrder=null;
	private Object dataObject = null;
	
	public Object getDataObject() {
		return dataObject;
	}
	public void setDataObject(Object dataObject) {
		this.dataObject = dataObject;
	}
	/**
	 * @return the isTrue
	 */
	public boolean isTrue() {
		return isTrue;
	}
	/**
	 * @param isTrue the isTrue to set
	 */
	public void setTrue(boolean isTrue) {
		this.isTrue = isTrue;
	}
	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		//this.list = new ArrayList();
		this.list = list;
	}
	public String getSqlQuery() {
		return sqlQuery;
	}
	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}
	public QueryData getQueryData() {
		return queryData;
	}
	public void setQueryData(QueryData queryData) {
		this.queryData = queryData;
	}
	public Hashtable getColumnOrder() {
		return columnOrder;
	}
	public void setColumnOrder(Hashtable columnOrder) {
		this.columnOrder = columnOrder;
	}
}
