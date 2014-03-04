package com.oxymedical.component.useradmin.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.useradmin.ICompany;
import com.oxymedical.component.useradmin.IGroup;
import com.oxymedical.component.useradmin.IOrganization;
import com.oxymedical.component.useradmin.UserAdminComponent;
import com.oxymedical.component.useradmin.constants.Constants;
import com.oxymedical.component.useradmin.constants.SQLCommands;
import com.oxymedical.component.useradmin.exception.NoSuchCompanyException;
import com.oxymedical.component.useradmin.exception.NoSuchGroupException;
import com.oxymedical.component.useradmin.exception.NoSuchGroupOrgException;
import com.oxymedical.component.useradmin.exception.NoSuchOrganizationException;
import com.oxymedical.component.useradmin.exception.NoSuchUserException;
import com.oxymedical.component.useradmin.exception.NoSuchUserOrgException;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.impl.ldap.LdapImpl;
import com.oxymedical.component.useradmin.model.Company;
import com.oxymedical.component.useradmin.model.GroupsOrg;
import com.oxymedical.component.useradmin.model.GroupsOrgPK;
import com.oxymedical.component.useradmin.model.Organization;
import com.oxymedical.component.useradmin.model.User;
import com.oxymedical.component.useradmin.model.UsersOrg;
import com.oxymedical.component.useradmin.operations.ConnectionDatabase;
import com.oxymedical.component.useradmin.util.CounterUtil;

public class OrganizationImpl implements IOrganization
{

	Organization organization = null;
	private LdapImpl ldapImpl = null;

	/**
	 * This function is used to assign groups to Organiztaions .Before assigning
	 * it validates the group,validates the organizcodation and save the Object.
	 * 
	 * @param groupId
	 *            The authentic group to be assigned to organizations.
	 * @param organizationIds
	 *            [] The authentic organizations to whom groups will be
	 *            assigned.
	 * @return addGroupOrganizations Returns void.
	 * @exception Throws
	 *                NoSuchGroupException if group is not validated and
	 *                NoSuchOrganizationException if organization is not
	 *                validated.
	 */

	public void addGroupOrganizations(String groupId, String[] organizationIds)
			throws NoSuchGroupException, NoSuchOrganizationException, DBComponentException
	{
		OrganizationImpl orgImpl = new OrganizationImpl();
		GroupImpl groupImpl = new GroupImpl();
		GroupsOrg groupsOrg = new GroupsOrg();
		GroupsOrgPK groupsOrgPK = new GroupsOrgPK();
		boolean validateOrganization = false;
		boolean validateGroup = false;
		for (int i = 0; i < organizationIds.length; i++)
		{
			validateGroup = groupImpl.validateGroup(groupId);
			validateOrganization = orgImpl.validateOrganization(organizationIds[i]);
			if ((validateGroup == true) && (validateOrganization == true))
			{
				groupsOrgPK.setGroupId(groupId);
				groupsOrgPK.setOrganizationId(organizationIds[i]);
				groupsOrg.setComp_id(groupsOrgPK);
				ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(groupsOrg);
			}
		}

	}

	public Organization addOrganization(String userId, String parentOrganizationId,
			String name, String regionId, String countryId, String statusId)
			throws NoSuchUserException, NoSuchGroupException, DBComponentException,
			UAComponentException
	{
		User user = null;
		user = UserImpl.findByPrimaryKey(userId);
		CounterUtil counterUtil = new CounterUtil();

		parentOrganizationId = getParentOrganizationId(user.getCompanyId(),
				parentOrganizationId);

		if (null == name)
		{
			throw (new UAComponentException("Name is required"));
		}
		organization = getOrganizationbyCompanyAndName(user.getCompanyId(), name);
		if (null == organization)
		{
			String organizationId = String.valueOf(counterUtil
					.incrementOrganizationId(OrganizationImpl.class.getName().toString()));
			organization = new Organization();
			organization.setOrganizationId(organizationId);
			organization.setCompanyId(user.getCompanyId());
			organization.setParentOrganizationId(parentOrganizationId);
			organization.setName(name);
			organization.setRegionId(regionId);
			organization.setCountryId(countryId);
			organization.setStatusId(statusId);
			ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(organization);
			//Start Changes by netram sahu ,no need to add group
			/*GroupImpl groupImpl = new GroupImpl();
			groupImpl.addGroup(userId, Organization.class.getName(), organizationId, null,
					null, null);*/
			//End Changes by netram sahu
		}

		else
		{
			throw (new UAComponentException("organization with " + name + "and company"
					+ user.getCompanyId() + "already exists"));
		}

		return organization;

	}

	/**
	 * This function is used to add the organiztaion to the user admin database
	 * .Before adding it validates the user,retrieves the companyId for the
	 * vaildated user , increments the organizationId and saves the object.
	 * 
	 * @param userId
	 *            The authentic user
	 * @param parentOrganizationId
	 * @param name
	 * @param regionId
	 *            The Id of the region
	 * @param countryId
	 * @param statusId
	 * @return addOrganization Returns Organization Object.
	 * @exception Throws
	 *                NoSuchUserException if UserId is not validated.
	 */

	public Organization addOrganizationByCompany(String companyId,
			String parentOrganizationId, String name, String regionId, String countryId,
			String statusId) throws NoSuchUserException, NoSuchGroupException,
			DBComponentException, UAComponentException
	{
		UserAdminComponent.logger.log(0,"---------Inside addOrganiztaionByCompany--------");
		CounterUtil counterUtil = new CounterUtil();
		Organization organization = null;
		String organizationId = null;
		boolean newOrg = false;
		Hashtable<String, String> ldapOrgInfo = null;

		if (null == name)
		{
			throw (new UAComponentException("Name is required"));
		}
		try
		{
			UserAdminComponent.logger.log(0,"------Inside try Organization------");
			organization = getOrganizationbyCompanyAndName(companyId, name);
			UserAdminComponent.logger.log(0,"SQL Query organization" + organization.getOrganizationId());
			UserAdminComponent.logger.log(0,"SQL Query organization" + organization.getCompanyId());
			organization.setRegionId(regionId);
			organization.setCountryId(countryId);
			organization.setStatusId(statusId);
			ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(organization);

		}
		catch (NoSuchOrganizationException ex)
		{
			UserAdminComponent.logger.log(0,"Inside catch NoSuchOrganizationException");
			newOrg = true;
			UserAdminComponent.logger.log(0,"OrganizationImpl.class.getName().toString())"
					+ OrganizationImpl.class.getName().toString());
			organizationId = String.valueOf(counterUtil
					.incrementOrganizationId(OrganizationImpl.class.getName().toString()));
			UserAdminComponent.logger.log(0,"Organization Id " + organizationId);
			ICompany companyImpl = new CompanyImpl();
			Company company = null;
			try
			{
				// company = companyImpl.getCompany(parentOrganizationId);
				company = companyImpl.getCompany(companyId);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			UserAdminComponent.logger.log(0,"-----Company is present----company---"
					+ company.getCompanyId());
			if (null != company)
			{
				organization = new Organization();
				organization.setOrganizationId(organizationId);
				organization.setCompanyId(companyId);
				organization.setParentOrganizationId(parentOrganizationId);
				organization.setName(name);
				organization.setRegionId(regionId);
				organization.setCountryId(countryId);
				organization.setStatusId(statusId);
				ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(organization);
				GroupImpl groupImpl = new GroupImpl();
				groupImpl.addGroupByCompany(companyId, Organization.class.getName(),
						organizationId, name, null, null, false);
			}
			UserAdminComponent.logger.log(0,"-----After Adding org and group in catch ---" + newOrg);

		}
		if (newOrg)
		{

			try
			{
				UserAdminComponent.logger.log(0,"-----After Adding org and group newOrg--" + newOrg);
				ldapOrgInfo = new Hashtable<String, String>();
				ldapImpl = new LdapImpl();
				ldapOrgInfo.put("type", "organization");
				ldapOrgInfo.put("name", organization.getName());
				ldapOrgInfo.put("parentname", organization.getCompanyId());
				ldapImpl.addLDAPEntry(ldapOrgInfo);
				UserAdminComponent.logger.log(0,"-----After Adding org and group newOrg-after lDap entry-");
			}
			catch (Exception e)
			{
				UserAdminComponent.logger.log(0,"-----After Adding org and group newOrg-after lDap entryin catch exp-");
				UserAdminComponent.logger.log(0," exception catched just start next");
			}
		}

		UserAdminComponent.logger.log(0,"----return organization--" + organization.getName());

		return organization;

	}

	public void ClearOrganizations(String userId) throws DBComponentException
	{

		String sqlQuery = SQLCommands.Clear_User_Organization;

		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter Id = new NameQueryParameter(Constants.userId, userId);
		listParam.add(Id);
		ConnectionDatabase.GetInstanceOfDatabaseComponent().executeDMLQueryWithNameParameter(
				sqlQuery, listParam);
	}

	public void deleteOrganization(String orgName, String compId) throws UAComponentException
	{
		// TODO Auto-generated method stub
		String orgId = null;
		String sqlQuery = null;
		UsersOrg usersOrg = null;
		ArrayList<NameQueryParameter> listParam = null;
		NameQueryParameter Id = null;
		List<UsersOrg> listUsers = null;
		Hashtable<String, String> ldapOrgInfo = null;
		IGroup grpImpl = new GroupImpl();
		organization = new Organization();

		try
		{
			organization = getOrganizationbyCompanyAndName(compId, orgName);
			orgId = organization.getOrganizationId();
			sqlQuery = SQLCommands.FIND_User_Org;
			listParam = new ArrayList<NameQueryParameter>();
			Id = new NameQueryParameter(Constants.organizationId, orgId);
			listParam.add(Id);
			listUsers = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeHSQLQueryWithNameParameter(sqlQuery, listParam);
			for (int listCounter = 0; listCounter < listUsers.size(); listCounter++)
			{
				usersOrg = listUsers.get(listCounter);
				ClearOrganizations(usersOrg.getComp_id().getUserId());
			}
			grpImpl.deleteGroup(compId, orgName, false);
			ldapOrgInfo = new Hashtable<String, String>();
			ldapImpl = new LdapImpl();
			ldapOrgInfo.put("type", "organization");
			ldapOrgInfo.put("name", organization.getName());
			ldapOrgInfo.put("parentname", organization.getCompanyId());
			ldapImpl.addLDAPEntry(ldapOrgInfo);

		}
		catch (DBComponentException e)
		{
			throw new UAComponentException(e);
		}

	}

	public void deleteOrganizationFromUserOrgs(String organizationId, String userId)
			throws UAComponentException
	{
		UserAdminComponent.logger.log(0,"----Present in--deleteOrganizationFromUserOrgs check orgId----"
				+ organizationId);
		String orgId = null;
		String sqlQuery = null;
		ArrayList<NameQueryParameter> listParam = null;
		NameQueryParameter Id = null;
		Integer result = null;
		try
		{
			sqlQuery = SQLCommands.Clear_User_Organization_basis_Org;
			StringBuilder sb = new StringBuilder(sqlQuery);
			sb.append("'").append(organizationId.trim()).append("'").append(
					SQLCommands.Append_Organization_basis_User).append("'").append(
					userId.trim()).append("'");
			sqlQuery = sb.toString();
			UserAdminComponent.logger.log(0,"----Final Query is-----"+sqlQuery);
			result = ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeDMLQuery(sqlQuery);
			UserAdminComponent.logger.log(0,"------Check result afet deletiong orgs from User_orgs----"
					+ result);
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
			throw new UAComponentException(e);
		}

	}

	public void deleteOrganization(Organization organization)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * This function is used to retrieve OrganiztaionIds with the given groupId
	 * .
	 * 
	 * @param groupId
	 *            The authentic groupId
	 * @return getGroupOrganizations Returns List.
	 * @exception Throws
	 *                NoSuchGroupOrgException if group and organization is not
	 *                validated .
	 * @exception Throws
	 *                DBComponentException
	 */
	public List getGroupOrganizations(String groupId) throws NoSuchGroupOrgException,
			DBComponentException
	{

		String sqlQuery = SQLCommands.Select_Group_org;

		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter Id = new NameQueryParameter(Constants.groupId, groupId);
		listParam.add(Id);
		List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
				.executeHSQLQueryWithNameParameter(sqlQuery, listParam);
		if (list == null || (list.size() == 0))
		{
			throw (new NoSuchGroupOrgException("No Group organization with this id Found"));
		}

		return list;

	}

	/**
	 * This function is used to retrieve Organiztaion data with the given
	 * organizationId .
	 * 
	 * @param groupId
	 *            The authentic groupId
	 * @return getOrganization Returns Organization object.
	 * @exception Throws
	 *                NoSuchOrganizationException if organizationId is not
	 *                validated .
	 */
	public Organization getOrganization(String organizationId)
			throws NoSuchOrganizationException, DBComponentException
	{
		organization = findByPrimaryKey(organizationId);
		return organization;
	}

	public List getUserOrganizations(String userId) throws DBComponentException,
			NoSuchUserOrgException
	{
		String sqlQuery = SQLCommands.Select_User_Organizations;
		UserAdminComponent.logger.log(0,"------present in getUserOrganization check sql query----"
				+ sqlQuery);
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter Id = new NameQueryParameter(Constants.userId, userId);
		listParam.add(Id);
		List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
				.executeHSQLQueryWithNameParameter(sqlQuery, listParam);
		if (list == null || (list.size() == 0))
		{
			throw (new NoSuchUserOrgException("No User organization with this id Found"));
		}
		UserAdminComponent.logger.log(0,"----Check return list size-----" + list);
		return list;
	}

	public List getUserOrganizationsFromUserOrgs(String userId) throws UAComponentException,
			NoSuchUserOrgException
	{
		String sqlQuery = SQLCommands.Find_Orgs_ForUserId_From_UserOrgs;
		UserAdminComponent.logger.log(0,"-present in getUserOrganization check sql query-"
				+ sqlQuery);
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter Id = new NameQueryParameter(Constants.userId, userId);
		listParam.add(Id);
		List list;
		try {
			list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeHSQLQueryWithNameParameter(sqlQuery, listParam);
		} catch (DBComponentException e) {
			throw new UAComponentException(e.getMessage(),e);
		}
		if (list == null || (list.size() == 0))
		{
			throw (new NoSuchUserOrgException("No User organization with this id Found"));
		}
		UserAdminComponent.logger.log(0,"-Check return list size-" + list.size());
		return list;
	}

	public boolean hasGroupOrganization(String groupId, String organizationId)
			throws DBComponentException, NoSuchGroupException, NoSuchOrganizationException
	{
		GroupImpl groupImpl = new GroupImpl();
		OrganizationImpl organizationImpl = new OrganizationImpl();
		int count = 0;
		Boolean hasGroupOrganization = false;

		Boolean validateGroup = groupImpl.validateGroup(groupId);
		Boolean validateOrg = organizationImpl.validateOrganization(organizationId);
		if ((validateGroup) && (validateOrg))
		{
			String sqlQuery = SQLCommands.Has_Group_Orgs;
			ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
			NameQueryParameter grId = new NameQueryParameter(Constants.groupId, groupId);
			listParam.add(grId);
			NameQueryParameter orgId = new NameQueryParameter(Constants.organizationId,
					organizationId);
			listParam.add(orgId);
			List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeHSQLQueryWithNameParameter(sqlQuery, listParam);
			if (list == null || (list.size() == 0))
			{

			}
			else
			{
				Iterator itr = list.iterator();

				if (itr.hasNext())
				{
					count++;
					;
				}
			}
		}
		if (count > 0)
		{
			hasGroupOrganization = true;

		}
		else
		{
			hasGroupOrganization = false;
		}
		return hasGroupOrganization;
	}

	public Organization updateOrganization(String companyId, String organizationId,
			String parentOrganizationId, String name, String regionId, String countryId,
			String statusId, boolean location) throws DBComponentException,
			NoSuchOrganizationException
	{
		parentOrganizationId = getParentOrganizationId(companyId, parentOrganizationId);
		Organization organization = OrganizationImpl.findByPrimaryKey(organizationId);

		organization.setParentOrganizationId(parentOrganizationId);
		organization.setName(name);
		organization.setRegionId(regionId);
		organization.setCountryId(countryId);
		organization.setStatusId(statusId);
		ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(organization);

		return organization;
	}

	public Organization updateOrganization(String organizationId, String comments)
			throws DBComponentException, NoSuchOrganizationException
	{

		Organization organization = OrganizationImpl.findByPrimaryKey(organizationId);
		organization.setComments(comments);
		ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(organization);

		return organization;
	}

	protected String getParentOrganizationId(String companyId, String parentOrganizationId)
			throws DBComponentException
	{

		if (!parentOrganizationId.equals(Constants.DEFAULT_PARENT_ORGANIZATION_ID))
		{

			// Ensure parent organization exists and belongs to the proper
			// company

			try
			{
				Organization parentOrganization = OrganizationImpl
						.findByPrimaryKey(parentOrganizationId);
				if (!companyId.equals(parentOrganization.getCompanyId()))
				{
					parentOrganizationId = Constants.DEFAULT_PARENT_ORGANIZATION_ID;
				}
			}
			catch (NoSuchOrganizationException nsoe)
			{
				parentOrganizationId = Constants.DEFAULT_PARENT_ORGANIZATION_ID;
			}
		}

		return parentOrganizationId;
	}

	public static Organization findByPrimaryKey(String organizationId)
			throws NoSuchOrganizationException, DBComponentException
	{
		Organization existingOrganization = null;
		String sqlQuery = SQLCommands.Find_Organization;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter Id = new NameQueryParameter(Constants.organizationId,
				organizationId);
		listParam.add(Id);
		List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
				.executeHSQLQueryWithNameParameter(sqlQuery, listParam);
		if ((list == null) || (list.size() == 0))
		{
			throw (new NoSuchOrganizationException("No organization with this id Found"));
		}
		else
		{
			for (Iterator organizationRow = list.iterator(); organizationRow.hasNext();)
			{
				Object organizationRowData = organizationRow.next();
				existingOrganization = (Organization) organizationRowData;

			}
		}
		return existingOrganization;

	}

	public boolean validateOrganization(String organizationId)
			throws NoSuchOrganizationException, DBComponentException
	{
		boolean resultOrganization = false;
		String sqlQuery = SQLCommands.Find_Organization;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter Id = new NameQueryParameter(Constants.organizationId,
				organizationId);
		listParam.add(Id);
		List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
				.executeHSQLQueryWithNameParameter(sqlQuery, listParam);
		if (list != null)
		{
			if (list.size() > 0)
			{
				// UserAdminComponent.logger.log(0,"Group Exists");
				resultOrganization = true;
			}
			else
			{
				// UserAdminComponent.logger.log(0,"Group does not Exists");
				resultOrganization = false;
				throw (new NoSuchOrganizationException("No organization with this id Found"));
			}

		}

		return resultOrganization;
	}

	public ArrayList<Organization> getOrganizationByCompanyId(String companyId)
			throws NoSuchCompanyException, NoSuchOrganizationException, DBComponentException
	{
		Organization organization = null;
		CompanyImpl companyImpl = new CompanyImpl();
		ArrayList<Organization> organizationList = new ArrayList<Organization>();
		Boolean isCompany = companyImpl.validateCompany(companyId);
		if (isCompany)
		{
			String sqlQuery = SQLCommands.Get_Org_By_Company;
			ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
			NameQueryParameter Id = new NameQueryParameter(Constants.companyId, companyId);
			listParam.add(Id);
			List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeHSQLQueryWithNameParameter(sqlQuery, listParam);
			if (list == null || (list.size() == 0))
			{
				throw (new NoSuchOrganizationException(
						"No  organization with this company Id  Found"));
			}
			else
			{
				for (Iterator organizationRow = list.iterator(); organizationRow.hasNext();)
				{
					Object organizationRowData = organizationRow.next();
					organization = (Organization) organizationRowData;
					organizationList.add(organization);
				}
			}
		}
		return organizationList;
	}

	public ArrayList<Organization> getOrganizationByParentOrganizationId(
			String parentOrganizationId) throws NoSuchCompanyException,
			NoSuchOrganizationException, DBComponentException
	{
		Organization organization = null;
		ArrayList<Organization> orgStructureList = new ArrayList<Organization>();
		String sqlQuery = SQLCommands.Get_Org_Structure;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter Id = new NameQueryParameter(Constants.name, parentOrganizationId);
		listParam.add(Id);
		List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
				.executeHSQLQueryWithNameParameter(sqlQuery, listParam);
		if (list == null || (list.size() == 0))
		{
			// UserAdminComponent.logger.log(0,"No child organization with " +
			// parentOrganizationId +" id found");
		}
		else
		{
			for (Iterator organizationRow = list.iterator(); organizationRow.hasNext();)
			{
				Object organizationRowData = organizationRow.next();
				organization = (Organization) organizationRowData;
				orgStructureList.add(organization);
			}
		}

		return orgStructureList;
	}

	public Organization getOrganizationbyCompanyAndName(String companyId, String name)
			throws NoSuchOrganizationException, DBComponentException, NoSuchCompanyException
	{
		Organization organization = null;
		UserAdminComponent.logger.log(0,"Inside getOrganizationByCompanyAndName ");
		String sqlQuery = SQLCommands.Find_Organization_From_Company_Name;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter compId = new NameQueryParameter(Constants.companyId, companyId);
		listParam.add(compId);
		NameQueryParameter orgName = new NameQueryParameter(Constants.name, name);
		listParam.add(orgName);
		List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
				.executeHSQLQueryWithNameParameter(sqlQuery, listParam);
		if (list == null || (list.size() == 0))
		{
			CompanyImpl companyImpl = new CompanyImpl();
			Boolean isCompany = companyImpl.validateCompany(companyId);
			UserAdminComponent.logger.log(0," Company Exists " + isCompany);
			if (isCompany)
			{
				throw (new NoSuchOrganizationException("No Organization with this  "
						+ companyId + " and " + name + " found"));
			}
			// if it does not
			else
			{
				throw (new NoSuchCompanyException(companyId + "does not exits"));
			}
		}
		else
		{
			for (Iterator organizationRow = list.iterator(); organizationRow.hasNext();)
			{
				Object organizationRowData = organizationRow.next();
				organization = (Organization) organizationRowData;
			}
		}

		return organization;

	}

	public static void main(String args[])
	{
		Organization org = new Organization();
		OrganizationImpl organizationImpl = new OrganizationImpl();

		String[] organizationIds = { "0" };
		try
		{
			// organizationImpl.addOrganization("0","2", "", "2", "6", "12");
			// org = organizationImpl.getOrganization("3");
			// organizationImpl.addGroupOrganizations("0", organizationIds);
			// organizationImpl.addOrganization("1", "-1",
			// "Organization Top Level", "3", "110", "1");
			// organizationImpl.getOrganizationByCompanyId("Oxyent.com");
			organizationImpl.getOrganizationByParentOrganizationId("2");
			// organizationImpl.updateOrganization("Oxyent.com", "0", "",
			// "Top Level Organization","2","10","1",true);
			// List list = organizationImpl.getGroupOrganizations("2");
			// UserAdminComponent.logger.log(0,"" +list.size());
			// organizationImpl.addGroupOrganizations("2", organizationIds);
			// UserAdminComponent.logger.log(0,"" +org.getCompanyId());
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
		} /*
		   * catch (NoSuchGroupException e) { e.printStackTrace(); }
		   */
		catch (NoSuchOrganizationException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchCompanyException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}/*
		  * catch (NoSuchUserException e) { // TODO Auto-generated catch block
		  * e.printStackTrace(); } catch (NoSuchGroupException e) { // TODO
		  * Auto-generated catch block e.printStackTrace(); }
		  */
	}

}
