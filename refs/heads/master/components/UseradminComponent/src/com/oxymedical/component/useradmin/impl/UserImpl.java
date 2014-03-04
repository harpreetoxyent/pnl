package com.oxymedical.component.useradmin.impl;

/**
 * @author Oxyent Medical Ltd, India
 * 
 * No part of this Source may be copied
 * without Oxyent's prior written permission.
 * Copyright 2007 Oxyent Medical Ltd, All Rights Reserved.
 * 
 *  Version 1.0.0
 */

import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.ldap.LDAPComponent;
import com.oxymedical.component.useradmin.IGroup;
import com.oxymedical.component.useradmin.IOrganization;
import com.oxymedical.component.useradmin.IUser;
import com.oxymedical.component.useradmin.UserAdminComponent;
import com.oxymedical.component.useradmin.constants.Constants;
import com.oxymedical.component.useradmin.constants.SQLCommands;
import com.oxymedical.component.useradmin.exception.DuplicateUserIdException;
import com.oxymedical.component.useradmin.exception.InValidPasswordException;
import com.oxymedical.component.useradmin.exception.NoSuchCompanyException;
import com.oxymedical.component.useradmin.exception.NoSuchGroupException;
import com.oxymedical.component.useradmin.exception.NoSuchOrganizationException;
import com.oxymedical.component.useradmin.exception.NoSuchRoleException;
import com.oxymedical.component.useradmin.exception.NoSuchUserException;
import com.oxymedical.component.useradmin.exception.OrganizationParentException;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.impl.ldap.LdapImpl;
import com.oxymedical.component.useradmin.model.Contact;
import com.oxymedical.component.useradmin.model.Group;
import com.oxymedical.component.useradmin.model.Grouplead;
import com.oxymedical.component.useradmin.model.GroupleadPK;
import com.oxymedical.component.useradmin.model.Logintracker;
import com.oxymedical.component.useradmin.model.Organization;
import com.oxymedical.component.useradmin.model.User;
import com.oxymedical.component.useradmin.model.UsersGroup;
import com.oxymedical.component.useradmin.model.UsersGroupPK;
import com.oxymedical.component.useradmin.model.UsersOrg;
import com.oxymedical.component.useradmin.model.UsersOrgPK;
import com.oxymedical.component.useradmin.model.UsersRole;
import com.oxymedical.component.useradmin.model.UsersRolePK;
import com.oxymedical.component.useradmin.model.Usersignature;
import com.oxymedical.component.useradmin.operations.ConnectionDatabase;
import com.oxymedical.component.useradmin.operations.UserDataBaseInfo;
import com.oxymedical.component.useradmin.util.CounterUtil;
import com.oxymedical.component.useradmin.util.GroupInfo;
import com.oxymedical.component.useradmin.util.GroupUserInfo;
import com.oxymedical.component.useradmin.util.OrgInfo;
import com.oxymedical.component.useradmin.util.UserInfo;

public class UserImpl implements IUser
{
	private LdapImpl ldapImpl = null;

	public void addGroupUsers(String groupId, String[] userIds) throws UAComponentException,
			NoSuchUserException, NoSuchGroupException, DBComponentException
	{
		UserImpl userImpl = new UserImpl();
		GroupImpl groupImpl = new GroupImpl();
		UsersGroup usersGroup = new UsersGroup();
		UsersGroupPK usersGroupPK = new UsersGroupPK();
		boolean validateUser = false;
		boolean validateGroup = false;
		for (int i = 0; i < userIds.length; i++)
		{
			validateGroup = groupImpl.validateGroup(groupId);
			validateUser = userImpl.validateUser(userIds[i]);
			if ((validateGroup == true) && (validateUser == true))
			{
				usersGroupPK.setGroupId(groupId);
				usersGroupPK.setUserId(userIds[i]);
				usersGroup.setComp_id(usersGroupPK);
				ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(usersGroup);
			}
		}
	}

	public void addGroupUsers(String groupId, String userId) throws UAComponentException,
			NoSuchUserException, NoSuchGroupException, DBComponentException
	{
		UserImpl userImpl = new UserImpl();
		GroupImpl groupImpl = new GroupImpl();
		UsersGroup usersGroup = new UsersGroup();
		UsersGroupPK usersGroupPK = new UsersGroupPK();
		boolean validateUser = false;
		boolean validateGroup = false;
		validateGroup = groupImpl.validateGroup(groupId);
		validateUser = userImpl.validateUser(userId);
		if ((validateGroup == true) && (validateUser == true))
		{
			usersGroupPK.setGroupId(groupId);
			usersGroupPK.setUserId(userId);
			usersGroup.setComp_id(usersGroupPK);
			ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(usersGroup);
		}
	}

	public void addRoleUsers(String roleId, String[] userIds) throws UAComponentException,
			NoSuchUserException, DBComponentException, NoSuchRoleException
	{
		UserImpl userImpl = new UserImpl();
		RoleImpl roleImpl = new RoleImpl();
		UsersRole usersRole = new UsersRole();
		UsersRolePK usersRolePK = new UsersRolePK();
		boolean validateRole = false;
		boolean validateUser = false;
		for (int i = 0; i < userIds.length; i++)
		{
			validateUser = userImpl.validateUser(userIds[i]);
			validateRole = roleImpl.validateRole(roleId);
			if ((validateUser == true) && (validateRole == true))
			{
				usersRolePK.setUserId(userIds[i]);
				usersRolePK.setRoleId(roleId);
				usersRole.setComp_id(usersRolePK);
				ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(usersRole);
			}
		}

	}

	public void addRoleUsers(Object[] roleIds, String userId) throws UAComponentException,
			NoSuchUserException, DBComponentException, NoSuchRoleException
	{
		UserImpl userImpl = new UserImpl();
		RoleImpl roleImpl = new RoleImpl();
		UsersRole usersRole = new UsersRole();
		UsersRolePK usersRolePK = new UsersRolePK();
		// UserAdminComponent.logger.log(0,"**********8 addRoleUsers");
		boolean validateRole = false;
		boolean validateUser = false;
		for (int i = 0; i < roleIds.length; i++)
		{
			validateUser = userImpl.validateUser(userId);
			validateRole = roleImpl.validateRole(roleIds[i].toString());
			if ((validateUser == true) && (validateRole == true))
			{
				usersRolePK.setUserId(userId);
				usersRolePK.setRoleId(roleIds[i].toString());
				usersRole.setComp_id(usersRolePK);
				ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(usersRole);
			}
		}

	}


	
	
	//Implementation changed for this method according to the Newly Added fields in AddNewUser Form
	//Change done by pra on 22-May-2009
	// Perviosuly method throws DBComponentException , now it will throw
	// UAComponentException,respective changes done in method.Change done by pra
	// on 26-May-2009
	//Change done by netram sahu on 10-Apr-2012
	//Added one parameter groupId
	public User addUser(String creatorUserId, String companyId,
				boolean autoUserId, String userId, String password,
				String emailAddress, Locale locale, String firstName,
				String middleName, String lastName, String nickName,
				String prefixId, String suffixId, boolean male, int birthdayMonth,
				int birthdayDay, int birthdayYear, String jobTitle,
				String organizationId, String locationId, String employeeNumber,
				String address1, String address2,				
				String nationalProvidedIdentificationNumber, String universalProviderIdentificationNumber,String category,
			  long city, String state, long telephoneNumber, long zipCode,Byte active,Date inActiveDate,String groupId) throws  DuplicateUserIdException,
			NoSuchCompanyException, NoSuchUserException, NoSuchOrganizationException,
			OrganizationParentException, NoSuchGroupException, UAComponentException
	{
		UserAdminComponent.logger.log(0,"Reached in add user ");
		Timestamp birthdayTimestamp = null;
		Organization organization = null;
		Group userGroup = null;
		Timestamp dateTimestamp = null;
		Byte maleByte = 0;
		//Byte active = 1;
		String creatorUserName = Constants.BLANK;
		Date now = new Date();
		String greetingConstant = Constants.BLANK;
		int lengthGreetingConstant = 0;
		CounterUtil counterUtil = new CounterUtil();
		Contact contact = new Contact();
		IGroup groupImpl = new GroupImpl();
		IOrganization orgImpl = new OrganizationImpl();
		Hashtable<String, String> ldapUserInfoHash = null;
		boolean newUser = false;
		User user = null;
		try
		{
			if (userId != null)

			{
				userId = userId.trim().toLowerCase();
				user = UserImpl.findByPrimaryKey(userId);
				UserAdminComponent.logger.log(0,"User ID is  " + userId);
				System.out.println("-------------Reached in addUser and userId is  -----"+userId);

				if (null != emailAddress)
				{
					emailAddress = emailAddress.trim().toLowerCase();
				}
				CompanyImpl companyImpl = new CompanyImpl();
				companyImpl.validateCompany(companyId);

				// UserAdminComponent.logger.log(0,"First NAME" + firstName);
				// UserAdminComponent.logger.log(0,"Last NAME" + lastName);
				String fullName = firstName + Constants.SPACE + lastName;
				// UserAdminComponent.logger.log(0,"FULL NAME" + fullName);
				String greeting = Constants.GREETING + fullName;
				greetingConstant = Constants.GREETING;
				lengthGreetingConstant = greetingConstant.length();

				user.setCompanyId(companyId);
				user.setPassword(password);
				user.setEmailAddress(emailAddress);
				user.setActive(active);
				user.setGreeting(greeting);
				//added implementaion for inactive date.change done by pra on 16june2009
				if(inActiveDate!=null)
				{
					user.setInActiveDate(new java.sql.Date(inActiveDate.getTime()));
				}
				
				UserAdminComponent.logger.log(0,"Reached here in AddUser user saved");
				ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(user);

				// UserAdminComponent.logger.log(0,"**** *******************User ID************"
				// +user.getUserId() );
				creatorUserId = user.getUserId();
				// UserAdminComponent.logger.log(0,"**** *******************creatorUserId ************"
				// +creatorUserId);
				User creatorUser = UserImpl.findByPrimaryKey(creatorUserId);
				// UserAdminComponent.logger.log(0,"**** *******************creatorUser.getGreeting().substring(lengthGreetingConstatnt) ************"
				// +creatorUser.getGreeting().substring(lengthGreetingConstant));
				if (creatorUser != null)
				{
					creatorUserName = creatorUser.getGreeting().substring(
							lengthGreetingConstant);
				}
				// UserAdminComponent.logger.log(0,"**** *******************Reached Here ************"
				// );
				// UserAdminComponent.logger.log(0,"**** Creator User Name***" +
				// creatorUserName);
				// Computing Birthday Day Month Year
				dateTimestamp = new Timestamp(now.getTime());
				Calendar cal = Calendar.getInstance();
				cal.set(birthdayYear, birthdayMonth, birthdayDay);
				Date birthday = cal.getTime();
				birthdayTimestamp = new Timestamp(birthday.getTime());

				String contactId = userId;
				if (male)
				{
					maleByte = 1;
				}
				else
				{
					maleByte = 0;
				}
				contact.setContactId(contactId);
				contact.setCompanyId(user.getCompanyId());
				contact.setUserId(creatorUserId);
				contact.setUserName(creatorUserName);
				contact.setCreateDate(dateTimestamp);
				contact.setModifiedDate(dateTimestamp);
				contact.setAccountId(user.getCompanyId());
				contact.setFirstName(firstName);
				contact.setMiddleName(middleName);
				contact.setLastName(lastName);
				contact.setPrefixId(prefixId);
				contact.setSuffixId(suffixId);
				contact.setMale(maleByte);
				contact.setBirthday(birthdayTimestamp);
				contact.setJobTitle(jobTitle);			
				
				contact.setEmployeeNumber(employeeNumber);
				contact.setAddress1(address1);
				contact.setAddress2(address2);
				//contact.setCategory(category);
				if(city!=0)		
				{
					String stringCity = ""+city+"";
					//contact.setCity(city);
				}
				else
					contact.setCity(null);	
				contact.setState(state);
				contact.setNationalProvidedIdentificationNumber(nationalProvidedIdentificationNumber);
				contact.setUniversalProviderIdentificationNumber(universalProviderIdentificationNumber);
				//Added check for 0,so that value cant be save 0 in database.and 0 cant be displayed in userform.added by pra on 15june2009
				if(telephoneNumber!=0)			
				contact.setTelephoneNumber(telephoneNumber);		
				else					
				contact.setTelephoneNumber(null);	
				if(zipCode!=0)	
				contact.setZipCode(zipCode);
				else
					contact.setZipCode(null);
					

				ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(contact);
				System.out.println("-------------Reached in addUser and contact saved  -----");

				//Unused exeception not in user.Still need to look into that. Change done by pra on 3-june-2009
			/*	if (user != null)
				{
					throw new DuplicateUserIdException("User with this" + userId
							+ " already exists.");
				}*/
			}
		}
	
		catch (NoSuchUserException ex)
		{
			newUser = true;
			if (null != emailAddress)
			{
				emailAddress = emailAddress.trim().toLowerCase();
			}
			CompanyImpl companyImpl = new CompanyImpl();
			try {
				companyImpl.validateCompany(companyId);
			} catch (DBComponentException e) {
				
				throw new UAComponentException(e.getMessage(),e);
			}

			if (autoUserId)
			{
				userId = String.valueOf(counterUtil.incrementUserId(UserImpl.class.getName()
						+ Constants.DOT + companyId));
			}

			user = new User();
			String fullName = firstName + Constants.SPACE + lastName;
			String greeting = Constants.GREETING + fullName;
			greetingConstant = Constants.GREETING;
			lengthGreetingConstant = greetingConstant.length();

			// Save User
			user.setUserId(userId);
			user.setCompanyId(companyId);
			user.setPassword(password);
			user.setEmailAddress(emailAddress);
			user.setGreeting(greeting);
			user.setActive(active);
			if(inActiveDate!=null)
			{
			user.setInActiveDate(new java.sql.Date(inActiveDate.getTime()));
			}
			try {
				ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(user);
			} catch (DBComponentException e) {
				
				throw new UAComponentException(e.getMessage(),e);
			}
			//Start changes by netram sahu on 25 May 2012
			//This changes is used to track invalid password
			try {
				Logintracker logintracker =new Logintracker();

				int logincounter=1;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
				java.util.Date date = sdf.parse("2012-06-21 13:18:00"); 
				java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime()); 
				logintracker.setUserId(userId);
				logintracker.setCounter(logincounter);
				logintracker.setLogindate(timestamp);
				ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(logintracker);

			} catch (DBComponentException e) {

				throw new UAComponentException(e.getMessage(),e);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//End changes by netram sahu 
			// Contact
			creatorUserId = user.getUserId();
			User creatorUser;
			try {
				creatorUser = UserImpl.findByPrimaryKey(creatorUserId);
			} catch (DBComponentException e) {
				throw new UAComponentException(e.getMessage(),e);
			}
			creatorUserName = creatorUser.getGreeting().substring(lengthGreetingConstant);
			// UserAdminComponent.logger.log(0,"**** *******************Reached Here ************"
			// );
			// UserAdminComponent.logger.log(0,"**** Creator User Name***" +
			// creatorUserName);

			dateTimestamp = new Timestamp(now.getTime());
			Calendar cal = Calendar.getInstance();
			cal.set(birthdayYear, birthdayMonth, birthdayDay);
			Date birthday = cal.getTime();
			birthdayTimestamp = new Timestamp(birthday.getTime());
			String contactId = userId;
			if (male)
			{
				maleByte = 1;
			}
			else
			{
				maleByte = 0;
			}
			contact.setContactId(contactId);
			contact.setCompanyId(user.getCompanyId());
			contact.setUserId(creatorUserId);
			contact.setUserName(creatorUserName);
			contact.setCreateDate(dateTimestamp);
			contact.setModifiedDate(dateTimestamp);
			contact.setAccountId(user.getCompanyId());
			contact.setFirstName(firstName);
			contact.setMiddleName(middleName);
			contact.setLastName(lastName);
			contact.setPrefixId(prefixId);
			contact.setSuffixId(suffixId);
			contact.setMale(maleByte);
			contact.setBirthday(birthdayTimestamp);
			contact.setJobTitle(jobTitle);
			
			contact.setEmployeeNumber(employeeNumber);
			contact.setAddress1(address1);
			contact.setAddress2(address2);
			contact.setCategory(category);
			if(city!=0)
			{	
				String stringCity = ""+city+"";
				//contact.setCity(city);
			}
			else
					contact.setCity(null);	
			contact.setState(state);
			contact.setNationalProvidedIdentificationNumber(nationalProvidedIdentificationNumber);
			contact.setUniversalProviderIdentificationNumber(universalProviderIdentificationNumber);
			//added check for zero done by pra on 29-june-2009
			if (telephoneNumber != 0)
				contact.setTelephoneNumber(telephoneNumber);
			else
				contact.setTelephoneNumber(null);
			if (zipCode != 0)
				contact.setZipCode(zipCode);
			else
				contact.setZipCode(null);
			try {
				ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(contact);
				orgImpl.ClearOrganizations(userId);
				organization = OrganizationImpl.findByPrimaryKey(organizationId);
			} catch (DBComponentException e) {
				
				throw new UAComponentException(e.getMessage(),e);
			}
			
			if (organization != null)
			{
				if (null != organizationId)
				{
					try {
						addUserOrganization(userId, organizationId);
					} catch (DBComponentException e) {
						
						throw new UAComponentException(e.getMessage(),e);
					}
				}
				//TODO Dont know why both entries required in database because then how can we show 
				//users in an organization so for a time being comment this code.
				/*if ((null != organization.getParentOrganizationId())
						&& (!organization.getParentOrganizationId().trim().equals("-1")))
				{
					AddUserOrganization(userId, organization.getParentOrganizationId());
				}*/
			}
			
			
			// Groups
			try {
				addGroupUsers(groupId, userId);
				
				/*userGroup = groupImpl.addGroup(user.getUserId(), User.class.getName(), user
						.getCompanyId()
						+ Constants.DOT + user.getUserId(), user.getUserId(), null, null);*/
			//End Changes
			} catch (DBComponentException e) {
				throw new UAComponentException(e.getMessage(),e);
			}

		} catch (DBComponentException e) {
			throw new UAComponentException(e.getMessage(),e);
		}
		if (newUser)
		{
			try
			{
				// ldapImpl = ldapImpl.getSingletonObject();
				ldapImpl = new LdapImpl();
				ldapUserInfoHash = new Hashtable<String, String>();
				if (null == contact.getFirstName() || null == contact.getLastName()
						|| null == contact.getUserName() || null == user.getPassword())
					throw new UAComponentException(
							"Incomplete information of user. Fill in all the fields");
				ldapUserInfoHash.put("type", "user");
				ldapUserInfoHash.put("name", contact.getFirstName());
				ldapUserInfoHash.put("lastname", contact.getLastName());
				String email = user.getEmailAddress();
				if (null == email)
					email = "";
				ldapUserInfoHash.put("mail", email);
				ldapUserInfoHash.put("telephonenumber", "");
				ldapUserInfoHash.put("username", contact.getUserName());
				ldapUserInfoHash.put("password", user.getPassword());
				createLdapInfo(organizationId, organization.getName(), "organization",
						ldapUserInfoHash, "add");
			}
			catch (Exception e)
			{
				UserAdminComponent.logger.log(0,"Exception in UserImpl while adding user entry in ldap");
			}

		}
		return user;
	}

	private void createLdapInfo(String parentId, String parentName, String parentType,
			Hashtable<String, String> ldapUserInfoHash, String type)
			throws UAComponentException
	{

		// ldapUserInfoHash = new Hashtable<String,String>();
		// UserAdminComponent.logger.log(0,"*********** createLdapInfo called");
		ldapUserInfoHash.put("parentType", parentType);
		ldapUserInfoHash.put("parentname", parentId);
		ldapUserInfoHash.put("parentId", parentName);
		if (type.equalsIgnoreCase("add"))
			ldapImpl.addLDAPEntry(ldapUserInfoHash);
		else
			ldapImpl.deleteLDAPEntry(ldapUserInfoHash);

	}

	public void addUserGroupUsers(String userGroupId, String[] userIds)
	{
		// TODO Auto-generated method stub

	}

	public int authenticateByUserId(String companyId, String userId, String password,
			Map headerMap, Map parameterMap)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public void deleteRoleUser(String roleId, String userId)
	{
		// TODO Auto-generated method stub

	}

	public void delete(Document udocument, LDAPComponent ldapComp) throws UAComponentException
	{
		GroupInfo groupInfo = null;
		Group group = null;
		GroupUserInfo groupUserInfo = null;
		OrgInfo orgInfo = null;
		UserInfo userInfo = null;
		GroupImpl groupImpl = null;
		OrganizationImpl organizationImpl = null;
		Organization organization = null;

		// UserAdminComponent.logger.log(0,"strDocument XML  is " + strDocument);

		try
		{
			// Convert String to XML
			// ldapImpl.initLdapInfoList();
			// This section will be deleted .This section is just for testing
			// purposes
			String fileName = Constants.USER_ADMIN_USER1;
			SAXReader reader = new SAXReader();
			InputStream modulefileStream = this.getClass().getResourceAsStream(fileName);
			Document document = reader.read(modulefileStream);
			// Till here
			// document will come from Form Designer

			String strDocument = document.getRootElement().asXML();
			strDocument = convertLessThen(strDocument);
			strDocument = convertGreaterThen(strDocument);
			UserAdminComponent.logger.log(0,"strDocument XML  is " + strDocument);

			// Convert String to XML
			document = DocumentHelper.parseText(strDocument); // UserAdminComponent.logger.log(0,"Document XML  is "
			// +
			// document.asXML());
			Element rootElement = document.getRootElement();
			// UserAdminComponent.logger.log(0,"Root Element is " + rootElement.getName());
			Iterator rootIterator = rootElement.elementIterator();
			while (rootIterator.hasNext())
			{
				Element nextElement = (Element) rootIterator.next();
				if (null != nextElement && nextElement.getName().equals("Organization"))
				{
					orgInfo = new OrgInfo();
					Iterator organizationIterator = nextElement.elementIterator();
					while (organizationIterator.hasNext())
					{
						Element element = (Element) organizationIterator.next();
						if ((null != element) && (null != element.getText())
								&& !("".equals(element.getText()))
								&& (null != element.getName())
								&& !("".equals(element.getName())))
						{
							if (element.getName().equals("orgName"))
							{
								orgInfo.setName(element.getText().trim());

							}
							if (element.getName().equals("companyId"))
							{
								orgInfo.setCompanyId(element.getText().trim());
							}
							if (element.getName().equals("ParentOrgname"))
							{
								orgInfo.setParentOrganizationId(element.getText().trim());

							}
						}
					}
					// Add Organization
					organizationImpl = new OrganizationImpl();
					organizationImpl.deleteOrganization(orgInfo.getName(), orgInfo
							.getCompanyId());

				}
			}
			// Group
			rootElement = document.getRootElement();
			rootIterator = rootElement.elementIterator();
			while (rootIterator.hasNext())
			{
				Element nextElement = (Element) rootIterator.next();
				if (null != nextElement && nextElement.getName().equals("Group"))
				{
					groupInfo = new GroupInfo();
					Iterator groupIterator = nextElement.elementIterator();
					while (groupIterator.hasNext())
					{
						Element element = (Element) groupIterator.next();
						if ((null != element) && (null != element.getText())
								&& !("".equals(element.getText()))
								&& (null != element.getName())
								&& !("".equals(element.getName())))
						{
							if (element.getName().equals("groupId"))
							{
								groupInfo.setName(element.getText().trim());
							}
							if (element.getName().equals("companyId"))
							{
								groupInfo.setCompanyId(element.getText().trim());
							}
						}
					}
					// Add Group
					groupImpl = new GroupImpl();
					groupImpl.deleteGroup(groupInfo.getCompanyId(), groupInfo.getName(), true);

				}
			}
			rootElement = document.getRootElement();
			rootIterator = rootElement.elementIterator();
			while (rootIterator.hasNext())
			{
				Element nextElement = (Element) rootIterator.next();
				if (null != nextElement && nextElement.getName().equals("User"))
				{

					List roleIds = new ArrayList();
					List elementList = nextElement.elements();
					userInfo = new UserInfo();
					for (Iterator userIterator = elementList.iterator(); userIterator
							.hasNext();)
					{
						Element element = (Element) userIterator.next();
						// UserAdminComponent.logger.log(0,"Element Name is  " +
						// element.getName());
						// UserAdminComponent.logger.log(0,"Element Text  is  " +
						// element.getText());
						// UserAdminComponent.logger.log(0,"Element  Length is  " +
						// element.getText().length());
						if ((null != element) && (null != element.getText())
								&& (null != element.getName())
								&& !("".equals(element.getName())))
						{
							// UserAdminComponent.logger.log(0,"Element Name inside *********   "
							// + element.getName());
							if (element.getName().equals("userId")
									&& !("".equals(element.getText())))
							{
								userInfo.setUserId(element.getText().trim());
								// UserAdminComponent.logger.log(0,"User is " +
								// userInfo.getUserId());
							}
							if (null != userInfo.getUserId())
							{
								userInfo.setAutoUserId(false);
							}
							else
							{
								userInfo.setAutoUserId(true);
							}
							if (element.getName().equals("companyId")
									&& !("".equals(element.getText())))
							{
								userInfo.setCompanyId((element.getText().trim()));
								// UserAdminComponent.logger.log(0,"Company is " +
								// userInfo.getCompanyId());
							}

							if (element.getName().equals("orgName")
									&& !("".equals(element.getText())))
							{
								organizationImpl = new OrganizationImpl();
								organization = new Organization();
								organization = organizationImpl
										.getOrganizationbyCompanyAndName(userInfo
												.getCompanyId(), element.getText().trim());
								if (null != organization)
								{
									userInfo.setOrganizationId(organization
											.getOrganizationId());
								}
								// UserAdminComponent.logger.log(0,"Organization ID  is " +
								// userInfo.getOrganizationId());

							}
							if (element.getName().equals("groupId")
									&& !("".equals(element.getText())))
							{
								groupImpl = new GroupImpl();
								group = new Group();
								group = groupImpl.getGroupByCompanyAndName(userInfo
										.getCompanyId(), element.getText().trim());
								if (null != group)
								{
									groupUserInfo = new GroupUserInfo();
									groupUserInfo.setGroupId(group.getGroupId());
									groupUserInfo.setUserId(userInfo.getUserId());
								}
								// UserAdminComponent.logger.log(0,"GroupUser Info is  " +
								// groupUserInfo.getGroupId());
							}

							if (element.getName().equals("firstName")
									&& !("".equals(element.getText())))
							{
								userInfo.setFirstName((element.getText().trim()));
								// UserAdminComponent.logger.log(0,"FirstName is " +
								// userInfo.getFirstName());

							}

							if (element.getName().equals("lastName")
									&& !("".equals(element.getText())))
							{
								userInfo.setLastName(element.getText().trim());
								// UserAdminComponent.logger.log(0,"Last Name is " +
								// userInfo.getLastName());

							}

						}
					}
					deleteUser(userInfo.getUserId(), userInfo.getFirstName(), userInfo
							.getLastName(), userInfo.getCompanyId(), userInfo
							.getOrganizationId());

				}
				// deleteUser(userInfo.getUserId());

			}

		}
		catch (DocumentException e)
		{
			throw new UAComponentException(e);
		}
		catch (DBComponentException e)
		{
			throw new UAComponentException(e);
			// TODO: handle exception
		}
	}

	private void deleteUser(String userId, String firstName, String lastName, String compId,
			String organizationId) throws UAComponentException
	{
		NameQueryParameter Id = null;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		Hashtable<String, String> ldapUserInfoHash = null;
		Id = new NameQueryParameter(Constants.userId, userId);
		listParam.add(Id);
		executeDeleteQuery(SQLCommands.Clear_User, listParam);
		executeDeleteQuery(SQLCommands.Clear_User_Organization, listParam);
		executeDeleteQuery(SQLCommands.Clear_User_Group, listParam);
		executeDeleteQuery(SQLCommands.Clear_User_Contact, listParam);
		executeDeleteQuery(SQLCommands.Clear_User_Role, listParam);
		executeDeleteQuery(SQLCommands.Clear_User_UserPattern, listParam);
		ldapImpl = new LdapImpl();
		ldapUserInfoHash = new Hashtable<String, String>();
		Organization organization = null;
		try
		{
			organization = OrganizationImpl.findByPrimaryKey(organizationId);
		}
		catch (DBComponentException e)
		{
			// TODO Auto-generated catch block
			throw new UAComponentException(e);
		}

		if (null == firstName || null == lastName || null == userId)
		{
			throw new UAComponentException(
					"Incomplete information of user. Fill in all the fields");
		}

		ldapUserInfoHash.put("type", "user");
		ldapUserInfoHash.put("name", firstName);
		ldapUserInfoHash.put("lastname", lastName);
		createLdapInfo(organizationId, organization.getName(), "organization",
				ldapUserInfoHash, "delete");

	}

	public void deleteUser(String userId, String firstName, String lastName,
			String organizationId, boolean ldapPresent) throws UAComponentException
	{
		UserAdminComponent.logger.log(0,"Presen tin Delete User userImpl" + userId);
		UserAdminComponent.logger.log(0,"Presen tin Delete User ldappresent" + ldapPresent);
		NameQueryParameter Id = null;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		Hashtable<String, String> ldapUserInfoHash = null;
		Id = new NameQueryParameter(Constants.userId, userId);
		listParam.add(Id);
		try
		{
			
			executeDeleteQuery(SQLCommands.Clear_User_hql, userId);
			executeDeleteQuery(SQLCommands.Clear_User_Organization_hql, userId);
			executeDeleteQuery(SQLCommands.Clear_User_Group_hql, userId);
			executeDeleteQuery(SQLCommands.Clear_User_Contact_hql, userId);
			executeDeleteQuery(SQLCommands.Clear_User_UserPattern_hql, userId);
			executeDeleteQuery(SQLCommands.Clear_User_Role_hql, userId);
			if (ldapPresent)
			{
				ldapImpl = new LdapImpl();
				ldapUserInfoHash = new Hashtable<String, String>();
				Organization organization = null;
				try
				{
					organization = OrganizationImpl.findByPrimaryKey(organizationId);
				}
				catch (DBComponentException e)
				{
					throw new UAComponentException(e.getMessage(),e);
				}

				if (null == firstName || null == lastName || null == userId)
				{
					throw new UAComponentException(
							"Incomplete information of user. Fill in all the fields");
				}

				ldapUserInfoHash.put("type", "user");
				ldapUserInfoHash.put("name", firstName);
				ldapUserInfoHash.put("lastname", lastName);
				createLdapInfo(organizationId, organization.getName(), "organization",
						ldapUserInfoHash, "delete");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private void executeDeleteQuery(String sqlQuery, ArrayList<NameQueryParameter> listParam)
			throws UAComponentException
	{
		try
		{
			UserAdminComponent.logger.log(0,":executeDeleteQuery Chekc query-" + sqlQuery);
			ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeDMLQueryWithNameParameter(sqlQuery, listParam);
		}
		catch (DBComponentException e)
		{
			throw new UAComponentException(e);
		}
	}

	private void executeDeleteQuery(String hqlQuery, String userId) throws UAComponentException
	{
		try
		{
			
			hqlQuery = hqlQuery+"'"+userId.trim()+"'";
			ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeDMLQuery(hqlQuery);
		}
		catch (DBComponentException e)
		{
			throw new UAComponentException(e);
		}
	}

	public List getGroupUsers(String groupId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public List getRoleUsers(String roleId)
	{

		return null;
	}

	public ArrayList<UsersRole> getUserRoles(String userId) throws NoSuchUserException,
			DBComponentException
	{
		ArrayList<UsersRole> usersRole = null;
		String sqlQuery = SQLCommands.Select_Role_From_User;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter Id = new NameQueryParameter(Constants.userId, userId);
		listParam.add(Id);
		List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
				.executeHSQLQueryWithNameParameter(sqlQuery, listParam);
		if ((list == null) || (list.size() == 0))
		{
			throw (new NoSuchUserException(userId + " does not exists"));
		}

		else
		{
			usersRole = new ArrayList<UsersRole>();
			for (Iterator usersRoleRow = list.iterator(); usersRoleRow.hasNext();)
			{
				Object usersRoleRowData = usersRoleRow.next();
				usersRole.add((UsersRole) usersRoleRowData);

			}
		}
		return usersRole;

	}

	public boolean hasGroupUser(String groupId, String userId)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasRoleUser(String roleId, String userId)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public User updatePassword(String userId, String password1, String password2,
			boolean passwordReset)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public User updateUser(String userId, String password, String emailAddress,
			String languageId, String timeZoneId, String greeting, String resolution,
			String comments, String firstName, String middleName, String lastName,
			String nickName, String prefixId, String suffixId, boolean male,
			int birthdayMonth, int birthdayDay, int birthdayYear, String smsSn, String aimSn,
			String icqSn, String jabberSn, String msnSn, String skypeSn, String ymSn,
			String jobTitle, String organizationId, String locationId)
	{
		// TODO Auto-generated method stub
		return null;
	}
	public static Group getGroupbyUserId(String userId) throws DBComponentException, NoSuchUserException
	{
		System.out.println("===inside the getGroupbyUserId======");
		UserAdminComponent.logger.log(0,"Reached in UserImpl getGroupbyUserId");
		Group existingGroup = null;
		String sqlQuery = SQLCommands.Find_Group;
		System.out.println("===sqlQuery====");
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter userNameId = new NameQueryParameter(Constants.userId, userId);
		System.out.println("==checking the value of userNameId===="+userNameId);
		UserAdminComponent.logger.log(0,"userId in findByPrimarykey is" + userId);
		listParam.add(userNameId);
		List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery, listParam);
		System.out.println("=====check the value of list in group userImpl ======="+list.size());
		if ((list == null) || (list.size() == 0))
		{
			System.out.println("==inside the if statement when list is null===");
			throw (new NoSuchUserException("No Group with this User Id Found"));
		}
		else
		{/*
			for (Iterator userRow = list.iterator(); userRow.hasNext();)
			{
				String userRowData = userRow.next().toString();
				System.out.println("class of the object is ---->"+userRow.next().getClass());
				System.out.println("class of the object is ---->"+userRowData);
				Group group= new Group();
				group.setGroupId("3");
				group.setName("PDD");
				existingGroup = (Group) group;

			}*/
			
			HashSet<Group> set = new HashSet<Group>(list);
			System.out.println("set size is ---->"+set.size());
			System.out.println("list of element value========>"+set.getClass());
			//set.iterator()
			for (Iterator userRow = set.iterator(); userRow.hasNext();)
			{
				Group group= (Group) userRow.next();
				System.out.println("sop for group data::"+group.getGroupId() +"::"+group.getName());
				existingGroup = (Group) group;
			}
		}

		return existingGroup;
	}
	
	
	public static List getpddGroupUsers(String groupName) throws DBComponentException, NoSuchUserException
	{
		System.out.println("===inside the getpddGroupUsers inside the UserImpl method======"+groupName);
		UserAdminComponent.logger.log(0,"Reached in UserImpl getpddGroupUsers");
		Group existingGroup = null;
		String sqlQuery = SQLCommands.Find_UsersGroup;
		
		System.out.println("===sqlQuery====");
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter groupNameId = new NameQueryParameter(Constants.groupName, groupName);
		System.out.println("==checking the value of groupName===="+groupNameId);
		UserAdminComponent.logger.log(0,"userId in findByPrimarykey is" + groupName);
		listParam.add(groupNameId);
		List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery, listParam);
		System.out.println("=====check the value of list in  getpddGroupUsers ======="+list.size());
		if ((list == null) || (list.size() == 0))
		{
			System.out.println("==inside the if statement when list is null===");
			throw (new NoSuchUserException("No user find in the pddgroup with this group Id Found"));
		}
		else
		{
			HashSet set= new HashSet(list);
			list=  new ArrayList(set);
		}
		return list;
	}
	
	
	
	public static String getEmailId(String userId) throws DBComponentException, NoSuchUserException
	{
		System.out.println("===inside the getEmailId======"+userId);
		User existingUser = null;
		UserAdminComponent.logger.log(0,"Reached in UserImpl getEmailId");
		String sqlQuery = SQLCommands.Find_User;
		System.out.println("===sqlQuery===="+sqlQuery);
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter userNameId = new NameQueryParameter(Constants.userId, userId);
		UserAdminComponent.logger.log(0,"userId in findByPrimarykey is" + userId);
		listParam.add(userNameId);
		List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
		.executeHSQLQueryWithNameParameter(sqlQuery, listParam);
		String emailId="";
		if ((list == null) || (list.size() == 0))
		{
			throw (new NoSuchUserException("No user with this User Id Found"));
		}
		else
		{
			for (Iterator userRow = list.iterator(); userRow.hasNext();)
			{
				Object userRowData = userRow.next();
				existingUser = (User) userRowData;
				emailId=existingUser.getEmailAddress();

			}
			/*HashSet set= new HashSet(list);
			list=  new ArrayList(set);*/
		}
		return emailId;
	}
	
	
	
	
	public static User findByPrimaryKey(String userId) throws NoSuchUserException,
			DBComponentException
	{
		UserAdminComponent.logger.log(0,"Reached in UserImpl findByPrimaryKey");
		User existingUser = null;
		String sqlQuery = SQLCommands.Find_User;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter userNameId = new NameQueryParameter(Constants.userId, userId);
		UserAdminComponent.logger.log(0,"userId in findByPrimarykey is" + userId);
		listParam.add(userNameId);
		List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
				.executeHSQLQueryWithNameParameter(sqlQuery, listParam);
		if ((list == null) || (list.size() == 0))
		{
			throw (new NoSuchUserException("No user with this User Id Found"));
		}
		else
		{
			for (Iterator userRow = list.iterator(); userRow.hasNext();)
			{
				Object userRowData = userRow.next();
				existingUser = (User) userRowData;

			}
		}

		return existingUser;
	}

	public static User findByUserName(String userName) throws NoSuchUserException,
			DBComponentException
	{
		User existingUser = null;
		Contact contact = null;
		String sqlQuery = SQLCommands.Find_User_From_Contact;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter userNameId = new NameQueryParameter(Constants.userName, userName);
		listParam.add(userNameId);
		List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
				.executeHSQLQueryWithNameParameter(sqlQuery, listParam);
		if ((list == null) || (list.size() == 0))
		{
			// UserAdminComponent.logger.log(0,"*********Inside User Impl**Contact List null*******");
			throw (new NoSuchUserException("No user with this User Name Found"));
		}
		else
		{
			// UserAdminComponent.logger.log(0,"*********Inside User Impl**Contact List not  null*******");
			for (Iterator contactRow = list.iterator(); contactRow.hasNext();)
			{
				Object contactRowData = contactRow.next();
				contact = (Contact) contactRowData;

			}
		}
		if (null != contact)
		{
			String query = SQLCommands.Find_User;
			ArrayList<NameQueryParameter> userListParam = new ArrayList<NameQueryParameter>();
			NameQueryParameter userId = new NameQueryParameter(Constants.userId, contact
					.getUserId());
			userListParam.add(userId);
			List userList = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeHSQLQueryWithNameParameter(query, userListParam);
			if ((userList == null) || (userList.size() == 0))
			{
				// UserAdminComponent.logger.log(0,"*********Inside User Impl**User List null*******");
				throw (new NoSuchUserException("No user with this id Found"));

			}
			else
			{
				// UserAdminComponent.logger.log(0,"*********Inside User Impl**User List not  null*******");
				for (Iterator userRow = userList.iterator(); userRow.hasNext();)
				{
					Object userRowData = userRow.next();
					existingUser = (User) userRowData;

				}
			}

		}

		return existingUser;
	}

	public static boolean authenticatePassword(String userId, String password)
			throws InValidPasswordException, DBComponentException
	{
		boolean isValidPassword = false;
		String sqlQuery = SQLCommands.Find_User_Id_Password;

		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter userNameId = new NameQueryParameter(Constants.userId, userId);
		listParam.add(userNameId);
		NameQueryParameter passwordId = new NameQueryParameter(Constants.password, password);
		listParam.add(passwordId);
		List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
				.executeHSQLQueryWithNameParameter(sqlQuery, listParam);
		if ((list == null) || (list.size() == 0))
		{
			// UserAdminComponent.logger.log(0,"*********Inside User Impl**authenticate Password User List null*******");
			throw (new InValidPasswordException("No user with this User Id and Password Found"));
		}
		else
		{
			// UserAdminComponent.logger.log(0,"*********Password Valid*******");
			isValidPassword = true;
		}
		return isValidPassword;
	}

	public static String escape(String string)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0, len = string.length(); i < len; i++)
		{
			char c = string.charAt(i);
			switch (c)
			{
				case '&':
					sb.append("&amp;");
					break;
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '"':
					sb.append("&quot;");
					break;
				default:
					sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * This function is used to convert &lt operator to less than HTML tag
	 * 
	 * @param wrongString
	 *            The XML document
	 * @return modified String .
	 */
	public String convertLessThen(String wrongString)
	{
		String modifiedString = "";
		modifiedString = wrongString;
		int startIndex = modifiedString.indexOf("&lt;");
		if (startIndex > 0)
		{
			modifiedString = wrongString.substring(0, startIndex) + "<"
					+ wrongString.substring(startIndex + 4);
			modifiedString = convertLessThen(modifiedString);
		}
		else
		{
			return modifiedString;
		}
		return modifiedString;
	}

	/**
	 * This function is used to convert &gt operator to greater than HTML tag
	 * 
	 * @param wrongString
	 *            The XML document
	 * @return modified String .
	 */
	public String convertGreaterThen(String wrongString)
	{
		String modifiedString = "";
		modifiedString = wrongString;
		int startIndex = modifiedString.indexOf("&gt;");
		if (startIndex > 0)
		{
			modifiedString = wrongString.substring(0, startIndex) + ">"
					+ wrongString.substring(startIndex + 4);
			modifiedString = convertGreaterThen(modifiedString);
		}
		else
		{
			return modifiedString;
		}
		return modifiedString;
	}

	// public Document getDocumentFromString(String stringXML)
	// {
	//		
	//		
	// }

	public boolean validateUser(String userId) throws NoSuchUserException,
			UAComponentException
	{
		boolean resultUser = false;
		try
		{
			String sqlQuery = SQLCommands.Find_User;
			ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
			NameQueryParameter Id = new NameQueryParameter(Constants.userId, userId);
			listParam.add(Id);
			List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeHSQLQueryWithNameParameter(sqlQuery, listParam);
			if (list != null)
			{
				if (list.size() > 0)
				{
					// UserAdminComponent.logger.log(0,"Group Exists");
					resultUser = true;
				}
				else
				{
					// UserAdminComponent.logger.log(0,"Group does not Exists");
					resultUser = false;
					throw (new NoSuchUserException("No user with this id Found"));
				}

			}

		}
		catch (DBComponentException e)
		{
			throw (new UAComponentException(e.getMessage()));
		}
		return resultUser;
	}

	public void addUserOrganization(String userId, String organizationId)
			throws DBComponentException
	{
		UsersOrg usersOrg = new UsersOrg();
		UsersOrgPK usersOrgPK = new UsersOrgPK();

		usersOrgPK.setUserId(userId);
		usersOrgPK.setOrganizationId(organizationId);

		usersOrg.setComp_id(usersOrgPK);

		ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(usersOrg);

	}

	public void setGroups(String userId, String groupId) throws DBComponentException
	{
		UsersGroup usersGroup = new UsersGroup();
		UsersGroupPK usersGroupPK = new UsersGroupPK();

		usersGroupPK.setUserId(userId);
		usersGroupPK.setGroupId(groupId);

		usersGroup.setComp_id(usersGroupPK);
		ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(usersGroup);

	}

	public static void main(String args[])
	{
		User user = new User();
		UserImpl userImpl = new UserImpl();
		Boolean isUser = false;
		String[] userIds = { "1" };
		try
		{
			// ConnectionDatabase.setDatabaseComponent(new DBComponent());
			LDAPComponent ldapCompo = null;
			UserAdminComponent.dataBaseInfo = new UserDataBaseInfo();
			UserAdminComponent.dataBaseInfo.setUserName("root");
			UserAdminComponent.dataBaseInfo.setPassword("1234");
			// userImpl.registerUsersFromXML(null, "NewScenario", ldapCompo);
			// userImpl.delete(null, null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		// userImpl.delete(null, null);
		// User user1 = UserImpl.findByPrimary;Key("ar");
		/*
		 * if(null != user1) { isUser = true; UserAdminComponent.logger.log(0,isUser); }
		 */
		// organizationImpl.addOrganization("1","-1", "Stoneone", "1", "1",
		// "12");
		// org = organizationImpl.getOrganization("2");
		// userImpl.addGroupUsers("1", userIds);
		// userImpl.addUser(null, "Oxyent.com", true, null, "roohi123",
		// "roohi@oxyent.com", null, "roohi", "kaur", "arora", "roohi", "Mrs",
		// "SE", true, 05, 9, 1982, "SE", "0", null);
		// }
		/*
		 * catch (DBComponentException e) { // TODO Auto-generated catch block
		 * 
		 * }
		 */
		/*
		 * catch (NoSuchUserException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (NoSuchOrganizationException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } catch
		 * (OrganizationParentException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (NoSuchGroupException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } catch
		 * (DuplicateUserIdException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (DocumentException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } catch
		 * (UAComponentException e) { // TODO Auto-generated catch block
		 * e.printStackTrace();
		 * 
		 * }
		 */
		// }
	}

	/**This method is used to Soft Delete the user.
	 * Added by pra on 26-may-2009.
	 * @param userId
	 * @param firstName
	 * @param lastName
	 * @param organizationId
	 * @param ldapPresent
	 * @throws UAComponentException
	 */
	public void softDeleteUser(String userId, String firstName, String lastName,
			String organizationId, boolean ldapPresent) throws UAComponentException
			{
				
				NameQueryParameter Id = null;
				ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
				Hashtable<String, String> ldapUserInfoHash = null;
				Id = new NameQueryParameter(Constants.userId, userId);
				listParam.add(Id);
				try
				{
					executeDeleteQuery(SQLCommands.Clear_Soft_User_hql, userId);
					
					
					if (ldapPresent)
					{
						ldapImpl = new LdapImpl();
						ldapUserInfoHash = new Hashtable<String, String>();
						Organization organization = null;
						try
						{
							organization = OrganizationImpl.findByPrimaryKey(organizationId);
						}
						catch (DBComponentException e)
						{
							// TODO Auto-generated catch block
							throw new UAComponentException(e);
						}

						if (null == firstName || null == lastName || null == userId)
						{
							throw new UAComponentException(
									"Incomplete information of user. Fill in all the fields");
						}

						ldapUserInfoHash.put("type", "user");
						ldapUserInfoHash.put("name", firstName);
						ldapUserInfoHash.put("lastname", lastName);
						createLdapInfo(organizationId, organization.getName(), "organization",
								ldapUserInfoHash, "delete");
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

	}
	
	
	public void addUserSignature(int id, String userid, String imageType, String imageData, int imageTag, String imageUnid) throws DBComponentException
	{
		// Create Usersignature object based on data recieved.
		
		// Call function to store data into database.
		Usersignature sign = new Usersignature();
		
		if (id > 0) sign.setId(id);
		sign.setImagedata(imageData);
		sign.setImagetagno(imageTag);
		sign.setImageunid(imageUnid);
		sign.setImagetype(imageType);
		sign.setUserId(userid);
		ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(sign);
	}

	//start by netram sahu on 25 Jun 2012 to map groupId with userId
	//This method is used to map lead with group

	public static String addGroupLeads(String groupId, String userId) throws NoSuchUserException, UAComponentException, DBComponentException
	{
		Grouplead groupLead = null;
		GroupleadPK groupleadPK = null;
		String leadId ="";
		try
		{
			leadId = UserImpl.getLeadName(groupId);
			System.out.println("=leadId=="+leadId);
				groupleadPK = new GroupleadPK();
				groupLead = new Grouplead();
				groupleadPK.setGroupId(groupId);
				groupleadPK.setUserId(leadId);
				groupLead.setComp_id(groupleadPK);
				ConnectionDatabase.GetInstanceOfDatabaseComponent().deleteObject(groupLead);
				groupleadPK = new GroupleadPK();
				groupLead = new Grouplead();
				groupleadPK.setGroupId(groupId);
				groupleadPK.setUserId(userId);
				groupLead.setComp_id(groupleadPK);
				ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(groupLead);
				userId=leadId;
			}
			catch(NoSuchGroupException ex)
			{
			System.out.println("==NoSuchGroupException=");
		    }
			catch(NoSuchUserException ex)
			{
				System.out.println("==NoSuchUserException=");
				groupleadPK = new GroupleadPK();
				groupLead = new Grouplead();
				groupleadPK.setGroupId(groupId);
				groupleadPK.setUserId(userId);
				groupLead.setComp_id(groupleadPK);
				ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(groupLead);
				
		}
		return userId;
	}

	//This method is used to get Group Lead Name

	public static String getLeadName(String groupId) throws DBComponentException, NoSuchUserException, NoSuchGroupException
	{
		System.out.println("===inside the getLeadName======"+groupId);
		String userId="";
		GroupImpl groupImpl = new GroupImpl();
		boolean validateGroup = false;
		validateGroup = groupImpl.validateGroup(groupId);
		if(validateGroup == true){
			Grouplead existingUser = null;
			UserAdminComponent.logger.log(0,"Reached in UserImpl getLeadName");
			String sqlQuery = SQLCommands.Find_Lead_User;
			System.out.println("===sqlQuery===="+sqlQuery);
			ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
			NameQueryParameter groupName= new NameQueryParameter(Constants.groupId, groupId);
			UserAdminComponent.logger.log(0,"groupId in findByPrimarykey is" + groupId);
			listParam.add(groupName);
			List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
			.executeHSQLQueryWithNameParameter(sqlQuery, listParam);

			if ((list == null) || (list.size() == 0))
			{
				throw (new NoSuchUserException("No user with this group Id Found"));
			}
			else
			{
				for (Iterator userRow = list.iterator(); userRow.hasNext();)
				{
					Object userRowData = userRow.next();
					existingUser = (Grouplead) userRowData;
					userId=existingUser.getComp_id().getUserId();
					System.out.println("=========userId========"+userId);
				}
			}
		}
		return userId;
	}

	public static Logintracker findLoginTrackByPrimaryKey(String userId)throws NoSuchUserException, DBComponentException {
		System.out.println("Inside findLoginTrackByPrimaryKey ");
		UserAdminComponent.logger.log(0, "Reached in UserImpl findByPrimaryKey");
		Logintracker existingUser = null;
		String sqlQuery = SQLCommands.Find_Login_cntr ;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter userNameId = new NameQueryParameter(
				Constants.userId, userId);
		System.out.println("userId in  findLoginTrackByPrimaryKey "+userId);
		UserAdminComponent.logger.log(0, "userId in findByPrimarykey is"
				+ userId);
		listParam.add(userNameId);
		List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
		.executeHSQLQueryWithNameParameter(sqlQuery, listParam);
		if ((list == null) || (list.size() == 0)) {
			System.out.println("======No Login Tracker with this User Id Found =======");
			throw (new NoSuchUserException("No Login Tracker with this User Id Found"));
		} else {
			System.out.println("== Login Tracker with this User Id Found==");
			for (Iterator userRow = list.iterator(); userRow.hasNext();) {
				Object userRowData = userRow.next();
				existingUser = (Logintracker) userRowData;

			}
		}

		return existingUser;
	}


	//end by netram sahu

}
