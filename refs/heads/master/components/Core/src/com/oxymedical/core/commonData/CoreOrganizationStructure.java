package com.oxymedical.core.commonData;

import java.util.ArrayList;


public class CoreOrganizationStructure implements java.io.Serializable
{
	public CoreCompany company = null;
	public ArrayList<CoreOrganizationInfo> orgInfoList = null;
	
	
	public CoreOrganizationStructure()
	{
		orgInfoList = new ArrayList<CoreOrganizationInfo>();
	}
	
	
	
	/**
	 * @return the company
	 */
	public CoreCompany getCompany() {
		return company;
	}
	/**
	 * @param company the company to set
	 */
	public void setCompany(CoreCompany company) {
		this.company = company;
	}



	/**
	 * @return the orgInfoList
	 */
	public ArrayList<CoreOrganizationInfo> getOrgInfoList() {
		return orgInfoList;
	}



	/**
	 * @param orgInfoList the orgInfoList to set
	 */
	public void setOrgInfoList(ArrayList<CoreOrganizationInfo> orgInfoList) {
		this.orgInfoList = orgInfoList;
	}

	
	
}
