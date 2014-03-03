package com.oxymedical.core.commonData;

import java.util.ArrayList;

public class CoreOrganizationInfo implements java.io.Serializable
{
	CoreOrganization organization = null;
	ArrayList<CoreOrganization> organizationList =null;
	/**
	 * @return the organization
	 */
	public CoreOrganization getOrganization() {
		return organization;
	}
	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(CoreOrganization organization) {
		this.organization = organization;
	}
	/**
	 * @return the organizationList
	 */
	public ArrayList<CoreOrganization> getOrganizationList() {
		return organizationList;
	}
	/**
	 * @param organizationList the organizationList to set
	 */
	public void setOrganizationList(ArrayList<CoreOrganization> organizationList) {
		this.organizationList = organizationList;
	}
}
