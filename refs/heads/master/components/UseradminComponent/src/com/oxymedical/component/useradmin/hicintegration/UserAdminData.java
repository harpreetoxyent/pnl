package com.oxymedical.component.useradmin.hicintegration;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.oxymedical.component.useradmin.OrganizationStructure;
import com.oxymedical.core.commonData.Group;
import com.oxymedical.core.commonData.IDataPattern;
import com.oxymedical.core.commonData.IFormPattern;
import com.oxymedical.core.commonData.IGroup;
import com.oxymedical.core.commonData.IUserInfo;
import com.oxymedical.core.commonData.IWorkflowPattern;
import com.oxymedical.core.communication.ICommunicationData;
import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.core.userdata.IUserPattern;

public class UserAdminData implements IUserAdmindata 
{
	String companyName = null;
	OrganizationStructure orgStructure = null;
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @return the orgStructure
	 */
	public OrganizationStructure getOrgStructure() {
		return orgStructure;
	}
	/**
	 * @param orgStructure the orgStructure to set
	 */
	public void setOrgStructure(OrganizationStructure orgStructure) {
		this.orgStructure = orgStructure;
	}
	
	
	public IDataPattern getDataPattern(){return null;}
	public IFormPattern getFormPattern(){return null;}
	public String getMethodName(){return null;}
	public String getStatus(){return null;}
	public String getUserId(){return null;}
	public void setDataPattern(IDataPattern arg0){}
	public void setFormPattern(IFormPattern arg0){}
	public void setMethodName(String arg0){}
	public void setStatus(String arg0){}
	public void setUserId(String arg0){}
	public void addUserPatterns(IUserPattern arg0){}
	public Set<IUserPattern> getUserPatterns(){return null;}
	public void setUserPatterns(Set<IUserPattern> arg0){}
	public String getInvokeComponentClass(){return null;}
	public String getInvokeComponentId(){	return null;}
	public void setInvokeComponentClass(String invokeComponentClass){}
	public void setInvokeComponentId(String invokeComponentId){}
	public LinkedHashMap getColumnOrder(){return null;}
	public List getList(){return null;}
	public QueryData getQueryData(){return null;}
	public Object getReturnedData(){return null;}
	public String getSqlQuery(){return null;}
	public void setColumnOrder(LinkedHashMap columnOrder){}
	public void setList(List list){}
	public void setQueryData(QueryData queryData){}
	public void setReturnedData(Object returnedData){}
	public void setSqlQuery(String sqlQuery){}
	public String getUserPatternString(){return null;}
	public Object getRawData(){return null;}
	public IUserInfo getUserCreds(){return null;}
	public void setRawData(Object rawData){}
	
	@Override public IUserInfo getUserInfo() {	return null;	}
	@Override public void setUserInfo(IUserInfo userInfo) {	}
	@Override public ICommunicationData getCommunicationInfo()	{	return null;	}
	@Override public void setCommunicationInfo(ICommunicationData commInfo)	{	}
	@Override public String getReturnMessage() { return null; }
	@Override public void setReturnMessage(String returnMessage) { }

	@Override public IWorkflowPattern getWorkflowPattern() { return null; }
	@Override
	public void setWorkflowPattern(IWorkflowPattern workflowPattern) {
	}
	@Override
	public String getGroupId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getGroupName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setGroupId(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setGroupName(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public IGroup getGroupInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setGroupInfo(IGroup arg0) {
		// TODO Auto-generated method stub
		
	}

}
