package com.oxymedical.component.useradmin;

import java.util.ArrayList;

import com.oxymedical.component.useradmin.model.Company;

public class OrganizationStructure 
{
	public Company company = null;
	public ArrayList<OrganizationInfo> orgInfoList = null;
	
	
	public OrganizationStructure()
	{
		orgInfoList = new ArrayList<OrganizationInfo>();
	}
	
	
	
	/**
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}
	/**
	 * @param company the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}



	/**
	 * @return the orgInfoList
	 */
	public ArrayList<OrganizationInfo> getOrgInfoList() {
		return orgInfoList;
	}



	/**
	 * @param orgInfoList the orgInfoList to set
	 */
	public void setOrgInfoList(ArrayList<OrganizationInfo> orgInfoList) {
		this.orgInfoList = orgInfoList;
	}

	
	
}
