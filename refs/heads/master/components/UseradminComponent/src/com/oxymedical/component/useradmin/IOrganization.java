/**
	 * @author Oxyent Medical Ltd, India
	 * 
	 * No part of this Source may be copied
	 * without Oxyent's prior written permission.
	 * Copyright 2007 Oxyent Medical Ltd, All Rights Reserved.
	 * 
	 *  Version 1.0.0
	 */

package com.oxymedical.component.useradmin;

import java.util.ArrayList;
import java.util.List;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.useradmin.exception.NoSuchCompanyException;
import com.oxymedical.component.useradmin.exception.NoSuchGroupException;
import com.oxymedical.component.useradmin.exception.NoSuchGroupOrgException;
import com.oxymedical.component.useradmin.exception.NoSuchOrganizationException;
import com.oxymedical.component.useradmin.exception.NoSuchUserException;
import com.oxymedical.component.useradmin.exception.NoSuchUserOrgException;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.model.Organization;

public interface IOrganization 
{

	public void addGroupOrganizations(String groupId,
			String[] organizationIds)throws NoSuchGroupException , NoSuchOrganizationException,DBComponentException;
	

	public Organization addOrganization(
		String userId, String parentOrganizationId,
		String name, String regionId,
		String countryId, String statusId)throws NoSuchUserException,NoSuchGroupException,DBComponentException,UAComponentException;
	
	public Organization addOrganizationByCompany(String companyId, String parentOrganizationId, String name, String regionId, String countryId, String statusId )throws NoSuchUserException,NoSuchGroupException,DBComponentException,UAComponentException;
	
	public void deleteOrganization(String organizationName , String companyId) throws UAComponentException;

	public void deleteOrganization(	Organization organization);
	
	public void ClearOrganizations(String userId)throws DBComponentException;

	public Organization getOrganization(String organizationId)throws NoSuchOrganizationException,DBComponentException;

	public Organization getOrganizationbyCompanyAndName(String companyId , String name)throws NoSuchOrganizationException,DBComponentException,NoSuchCompanyException;
	
	public ArrayList<Organization> getOrganizationByCompanyId(String companyId)throws NoSuchCompanyException,NoSuchOrganizationException,DBComponentException;
	
	public List getGroupOrganizations(String groupId) throws NoSuchGroupOrgException,DBComponentException;

	public List getUserOrganizations(String userId)throws DBComponentException,NoSuchUserOrgException;

	public boolean hasGroupOrganization(String groupId,String organizationId)throws DBComponentException,NoSuchGroupException,NoSuchOrganizationException;
	
	public Organization updateOrganization(
			String companyId, String organizationId,
			String parentOrganizationId, String name,
			String regionId, String countryId,
			String statusId, boolean location) throws DBComponentException ,NoSuchOrganizationException;
	
	public Organization updateOrganization(
			String organizationId, String comments)throws DBComponentException, NoSuchOrganizationException ;
	
	public List getUserOrganizationsFromUserOrgs(String userId) throws NoSuchUserOrgException, UAComponentException;
	public void deleteOrganizationFromUserOrgs(String organizationId, String userId)
	throws UAComponentException;
	
}
