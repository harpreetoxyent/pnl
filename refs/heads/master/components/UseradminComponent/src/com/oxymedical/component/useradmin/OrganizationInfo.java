package com.oxymedical.component.useradmin;

import java.util.ArrayList;

import com.oxymedical.component.useradmin.model.Organization;

public class OrganizationInfo
{
	Organization organization = null;
	ArrayList<Organization> organizationList =null;
	/**
	 * @return the organization
	 */
	public Organization getOrganization() {
		return organization;
	}
	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	/**
	 * @return the organizationList
	 */
	public ArrayList<Organization> getOrganizationList() {
		return organizationList;
	}
	/**
	 * @param organizationList the organizationList to set
	 */
	public void setOrganizationList(ArrayList<Organization> organizationList) {
		this.organizationList = organizationList;
	}
}
