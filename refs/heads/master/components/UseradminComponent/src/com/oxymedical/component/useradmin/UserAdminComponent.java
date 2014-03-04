package com.oxymedical.component.useradmin;

/**
 * @author Oxyent Medical Ltd, India
 * 
 * No part of this Source may be copied
 * without Oxyent's prior written permission.
 * Copyright 2007 Oxyent Medical Ltd, All Rights Reserved.
 * 
 *  Version 1.0.0
 */

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.baseComponent.IComponentIdConstants;
import com.oxymedical.component.baseComponent.annotations.EventSubscriber;
import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.ua.UserAdminQueryHelper;
import com.oxymedical.component.ldap.LDAPComponent;
import com.oxymedical.component.ldap.constants.LDAPConstants;
import com.oxymedical.component.logging.LoggingComponent;
import com.oxymedical.component.useradmin.XMLParser.XMLParser;
import com.oxymedical.component.useradmin.XMLcreator.XMLCreator;
import com.oxymedical.component.useradmin.constants.Constants;
import com.oxymedical.component.useradmin.exception.DuplicateUserIdException;
import com.oxymedical.component.useradmin.exception.InValidPasswordException;
import com.oxymedical.component.useradmin.exception.NoSuchCompanyException;
import com.oxymedical.component.useradmin.exception.NoSuchContainerCompanyException;
import com.oxymedical.component.useradmin.exception.NoSuchContainerException;
import com.oxymedical.component.useradmin.exception.NoSuchGroupException;
import com.oxymedical.component.useradmin.exception.NoSuchOrganizationException;
import com.oxymedical.component.useradmin.exception.NoSuchRoleException;
import com.oxymedical.component.useradmin.exception.NoSuchUserException;
import com.oxymedical.component.useradmin.exception.OrganizationParentException;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.hicintegration.HICIntegration;
import com.oxymedical.component.useradmin.impl.CompanyContImpl;
import com.oxymedical.component.useradmin.impl.CompanyImpl;
import com.oxymedical.component.useradmin.impl.ContainerImpl;
import com.oxymedical.component.useradmin.impl.OrganizationImpl;
import com.oxymedical.component.useradmin.impl.RoleImpl;
import com.oxymedical.component.useradmin.impl.RoleRightsImpl;
import com.oxymedical.component.useradmin.impl.UserFieldsImpl;
import com.oxymedical.component.useradmin.impl.UserImpl;
import com.oxymedical.component.useradmin.impl.UserPatternImpl;
import com.oxymedical.component.useradmin.impl.UserpatternRolesImpl;
import com.oxymedical.component.useradmin.impl.UserpatternUsersImpl;
import com.oxymedical.component.useradmin.model.Company;
import com.oxymedical.component.useradmin.model.CompanyCont;
import com.oxymedical.component.useradmin.model.Group;
import com.oxymedical.component.useradmin.model.Logindetail;
import com.oxymedical.component.useradmin.model.Organization;
import com.oxymedical.component.useradmin.model.Role;
import com.oxymedical.component.useradmin.model.User;
import com.oxymedical.component.useradmin.model.UsersRole;
import com.oxymedical.component.useradmin.operations.ConnectionDatabase;
import com.oxymedical.component.useradmin.operations.UserDataBaseInfo;
import com.oxymedical.component.useradmin.util.LoginUtil;
import com.oxymedical.component.useradmin.util.UserInfo;
import com.oxymedical.component.useradmin.util.UserPatternUsersInfo;
import com.oxymedical.core.commonData.Application;
import com.oxymedical.core.commonData.DataBase;
import com.oxymedical.core.commonData.FormPattern;
import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.IApplication;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IDataBase;
import com.oxymedical.core.commonData.IFormPattern;
import com.oxymedical.core.commonData.IGroup;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.commonData.ISource;
import com.oxymedical.core.commonData.Source;
import com.oxymedical.core.constants.CoreConstants;
import com.oxymedical.core.dateutil.DateUtil;
import com.oxymedical.core.hicUtil.TransContainer;
import com.oxymedical.core.ioutil.FileIO;
import com.oxymedical.core.ldapData.ILdapData;
import com.oxymedical.core.ldapData.LdapData;
import com.oxymedical.core.maintenanceData.IMaintenanceData;
import com.oxymedical.core.propertyUtil.PropertyUtil;
import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.core.userdata.IRights;
import com.oxymedical.core.userdata.IUserPattern;
import com.oxymedical.core.userdata.UserPattern;
import com.oxymedical.core.xmlutil.XmlReader;
import com.oxymedical.framework.objectbroker.annotations.InjectNew;

/**
 * @author vka
 *
 */
public class UserAdminComponent implements IUserAdminComponent, IComponent
{
	@InjectNew
	public TransContainer containerSettings;
	@InjectNew
	public DBComponent userAdminDBComponent;

	@InjectNew
	public LDAPComponent ldapComp;
	@InjectNew 
	static public LoggingComponent logger;
	
		
	public IHICData hicData = null;

	public static UserDataBaseInfo dataBaseInfo = null;
	/*
	 * private Hashtable<String,
	 * com.oxymedical.component.useradmin.register.pattern.FormPattern>
	 * formPatternHash = null; private Hashtable<String, DataPattern>
	 * dataPatternHash = null; private Hashtable<String, List<Field>>
	 * tableFieldHash = null;
	 */
	private List applicationFormList = null;
	private String applicationName = null;
	// private List<Field> fieldList = null;

	private static boolean isApplicationRegistered = false;

	public ISingleSignonManager getSingleSignonManager()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public IUserAdminManager getUserAdminManager()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void setSingleSignonManager(ISingleSignonManager userAdminManager)
	{
		// TODO Auto-generated method stub

	}

	@EventSubscriber(topic="groupdetalis")
	public IHICData groupdetalis(IHICData hicData)
	{
		IData data=hicData.getData();
		String userId=data.getUserId();
		try
		{
			Group groupInfo=UserImpl.getGroupbyUserId(userId);
			String groupId=groupInfo.getGroupId();
			String groupName=groupInfo.getName();
			data.setGroupId(groupId);
			data.setGroupName(groupName);
			hicData.setData(data);
			//hicData=buildHICData(hicData, true,"authenticateUserEx", userId,"",groupId,groupName);
		}
		catch (NoSuchUserException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (DBComponentException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hicData;
	}
	
	
	
	
	@EventSubscriber(topic="pddUsersGroup")
	public IHICData pddUsersGroup(IHICData hicData)
	{
		IData data=hicData.getData();
		IFormPattern formpattern=data.getFormPattern();
		Hashtable formvalues=new Hashtable();
		formvalues=data.getFormPattern().getFormValues();
		String groupName=data.getGroupName();
		System.out.println("groupName====inside the pddUsersGroup Name======="+groupName);
		formvalues.put("emailCondition","emailCondition");
		formpattern.setFormValues(formvalues);
		try
		{   List pddusers=new ArrayList();
			pddusers=(List) UserImpl.getpddGroupUsers(groupName);
			QueryData queryResponseData = data.getQueryData();
			if(null==data.getQueryData())
			{
				queryResponseData=new QueryData();	
			}
			queryResponseData.setListData(pddusers);
			data.setQueryData(queryResponseData);
			hicData.setData(data);
			
			//hicData=buildHICData(hicData, true,"authenticateUserEx", userId,"",groupId,groupName);
		}
		catch (NoSuchUserException e) 
		{
			e.printStackTrace();
		}
		catch (DBComponentException e) 
		{
			e.printStackTrace();
		}
		return hicData;
	}
	
	
	/*@EventSubscriber(topic="userEmailId")
	public IHICData userEmailId(IHICData hicData)
	{
		IData data=hicData.getData();
		String userId=data.getUserId();
		System.out.println("===inside the userEmailId function userId====="+userId);
		try
		{
			List emailId=new ArrayList();
			emailId=(List) UserImpl.getpddGroupUsers(userId);
			QueryData queryResponseData = data.getQueryData();
			if(null==data.getQueryData())
			{
				queryResponseData=new QueryData();	
			}
			queryResponseData.setListData(emailId);
			data.setQueryData(queryResponseData);
			hicData.setData(data);
			//hicData=buildHICData(hicData, true,"authenticateUserEx", userId,"",groupId,groupName);
		}
		catch (NoSuchUserException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (DBComponentException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hicData;
	}*/
	
	
	
	@EventSubscriber(topic = "authenticateUserEx")
	public IHICData authenticateUserExernally(Object hicInputData)
	{
		
		System.out.println("hicInputData inside =====================authenticateUserEx = "+((IHICData)hicInputData).getSrcHistoryList());
		
		boolean userAthenticated = false;
		String userId = "";
		String password = "";
		String loguserId ="";
		IHICData hicObjectData = (IHICData) hicInputData;
		
		try
		{
			// This call is required to set necessary credentials to connect
			// with database
			checkConnectionSettings(hicObjectData);
			System.out.println("===========after checkConnectionSettings=========");
			/*
			 * ExternalData externaldata =
			 * (ExternalData)hicObjectData.getMetaData().getCommonObject();
			 * logger.log(0,"-----external data is not null-------");
			 * LinkedList<Object> valuesList = externaldata.getValues();
			 * String userId = valuesList.getFirst().toString(); String password
			 * = valuesList.getLast().toString();
			 */
			/*
			 * String userId =
			 * (String)hicObjectData.getData().getFormPattern().getFormValues
			 * ().get("userName"); String password =
			 * (String)hicObjectData.getData
			 * ().getFormPattern().getFormValues().get("password");
			 */
			// TODO need to get these formavalues as parameters not hard coded
			IData data=null;
			if ((hicObjectData != null && hicObjectData.getData() != null)
					&& (hicObjectData.getData().getFormPattern() != null && hicObjectData
							.getData().getFormPattern().getFormValues() != null))
			{
				System.out.println("=====getting the value from the form data=====");
				data =hicObjectData.getData();
				userId = (String) data.getFormPattern().getFormValues()
						.get(Constants.userId);
				password = (String) data.getFormPattern().getFormValues()
						.get(Constants.password);
			}
			String authenticateUserInLDAP = (String)data.getFormPattern().getFormValues().get(Constants.AuthenticateUserInLDAP);
			if(authenticateUserInLDAP != null && authenticateUserInLDAP.equalsIgnoreCase("true")){
				LDAPComponent ldapComp = new LDAPComponent();
				
				ILdapData ldapData = new LdapData();
				ldapData.setUserName(userId);
				ldapData.setPassword(password);
				hicObjectData.setLdapData(ldapData);
				
				hicObjectData = ldapComp.authenticateUserInLDAP(hicObjectData);
				
				String userAuthenticatedInLDAP = (String)hicObjectData.getLdapData().getAttributes().get(CoreConstants.UserAuthenticatedInLDAP_KEY);
				if(userAuthenticatedInLDAP != null && userAuthenticatedInLDAP.equalsIgnoreCase("true")){
					userAthenticated = true;
				}
				
				//checking if user is present in the user admin db
				try{
					data.getFormPattern().getFormValues().put(Constants.UserPresentInUserAdmin_KEY, "false");
					User userInfo = UserImpl.findByPrimaryKey(userId);
					if(userInfo != null){
						data.getFormPattern().getFormValues().put(Constants.UserPresentInUserAdmin_KEY, "true");
					}
				}catch(NoSuchUserException noUsrEx){}
			}else{
				userAthenticated = authenticateUser(userId, password, "", "");
			}
			if(userAthenticated)
			{
				System.out.println("======================user authenticated 00000000000===================");
				String hostAddress = data.getUserInfo().getHostAddress();
				Logindetail loginDetails = LoginUtil.saveLoginDetails(userId,hostAddress);
				loguserId = userId+"-"+loginDetails.getId();
			}
			hicObjectData = buildHICData(hicObjectData, userAthenticated,
					"authenticateUserEx", userId,loguserId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			userAthenticated = false;
			hicObjectData = buildHICData(hicObjectData, userAthenticated,
					"authenticateUserEx", userId,loguserId);
		}
		return hicObjectData;
	}

	
	@EventSubscriber(topic = "logoutUser")
	public IHICData logOut(Object hicInputData)
	{
		boolean userAthenticated = false;
		String userId = null;
		IHICData hicObjectData = (IHICData) hicInputData;
		try
		{
			// This call is required to set necessary credentials to connect
			// with database
			checkConnectionSettings(hicObjectData);

			if ((hicObjectData != null && hicObjectData.getData() != null))
			{
				userId = hicObjectData.getData().getUserId();
				/*userId = (String) hicObjectData.getData().getFormPattern().getFormValues()
						.get(Constants.userId);*/
			}
			if(userId != null)
			{
				LoginUtil.saveLogOutDetail(userId);
			}
		}
		catch (Exception e)
		{
			
			userAthenticated = false;
			hicObjectData = buildHICData(hicObjectData, userAthenticated,
					"authenticateUserEx", userId,null,null,null);
		}
		return hicObjectData;
	}
	
	//

	private IHICData buildHICData(IHICData hicObjectData, boolean userAthenticated,
			String method, String userId,String logUserId,String groupId,String groupName)
	{
		
		if(groupId == null && groupName == null)
		{
			try
			{
				throw new Exception("group  or group name is null Id is null");
			}
			catch (Exception e) 
			{
			
				e.printStackTrace();
			}
		}
		hicObjectData.getData().setGroupId(groupId);
		hicObjectData.getData().setGroupName(groupName);
		System.out.println("hicObjectData.getSrcHistoryList() ====="+hicObjectData.getSrcHistoryList());
		IHICData hicReturnData = (IHICData) hicObjectData.clone();
		System.out.println("hicReturnData ====="+hicReturnData);
		System.out.println("hicReturnData.getSrcHistoryList() ====="+hicReturnData.getSrcHistoryList());
		ISource uasource = new Source();
		uasource.setComponent_id(IComponentIdConstants.USERADMIN_COMP_ID);
		uasource.setMethodName(method);
		hicReturnData.getSrcHistoryList().add(uasource);
		// add userPatterns while building HICData
		hicReturnData.getData().setUserPatterns(
				setRelatedUserPatternsInData(userId, hicReturnData));
		
		//set the group inside the hicData here===============
		//IData data=hicReturnData.getData();
		
		hicReturnData.getData().setGroupId(groupId);
		hicReturnData.getData().setGroupName(groupName);
		
		//hicReturnData.getData().setDataPattern(set);
		
		if (userAthenticated)
		{
			if(logUserId !=null)
			{
				hicReturnData.getData().setUserId(logUserId);
				//hicReturnData.getData().;
			}
			else
			{
				hicReturnData.getData().setUserId(userId);
			}
			
			hicReturnData.getData().setStatus(Constants.USER_AUTH_VALID_STATUS);
			
		}
		else
		{
			
			hicReturnData.getData().setStatus(Constants.USER_AUTH_INVALID_STATUS);
		}
		
		return hicReturnData;

	}
	
	
	
	private IHICData buildHICData(IHICData hicObjectData, boolean userAthenticated,
			String method, String userId,String logUserId)
	{
		
		System.out.println("hicObjectData.getSrcHistoryList() ====="+hicObjectData.getSrcHistoryList());
		IHICData hicReturnData = (IHICData) hicObjectData.clone();
		ISource uasource = new Source();
		uasource.setComponent_id(IComponentIdConstants.USERADMIN_COMP_ID);
		uasource.setMethodName(method);
		hicReturnData.getSrcHistoryList().add(uasource);
		// add userPatterns while building HICData
		hicReturnData.getData().setUserPatterns(
				setRelatedUserPatternsInData(userId, hicReturnData));
		
		if (userAthenticated)
		{
			if(logUserId !=null)
			{
				hicReturnData.getData().setUserId(logUserId);
				
			}
			else
			{
				hicReturnData.getData().setUserId(userId);
			}
			
			hicReturnData.getData().setStatus(Constants.USER_AUTH_VALID_STATUS);
			
		}
		else
		{
			
			hicReturnData.getData().setStatus(Constants.USER_AUTH_INVALID_STATUS);
		}
		
		return hicReturnData;

	}

	/*
	 * This is commnted method
	 * 
	 * changes by wasim,
	 */
	/*@EventSubscriber(topic = "UAList")
	public IHICData getListData(IHICData requestData)
			throws UAComponentException {
		QueryData queryResponseData = null;
		hicData = requestData;
		IData data = requestData.getData();
		queryResponseData = data.getQueryData();
		String queryStr = data.getQueryData().getCondition();
		List<Object> fetchValue = null;
		boolean exception = false;
		try {
			checkConnectionSettings(requestData);
			
			fetchValue = ConnectionDatabase.GetInstanceOfDatabaseComponent().stringQueryList(queryStr);
			System.out.println("-------list-------"+fetchValue.size());
			if (fetchValue != null) {
				queryResponseData.setListData(fetchValue);
				data.setQueryData(queryResponseData);
			}
		} catch (Exception exp) {

			exp.printStackTrace();
			
		}

		return requestData;
	}
	
	
	
	*/
	
	
	
	
	private Set<IUserPattern> setRelatedUserPatternsInData(String userId,
			IHICData hicReturnData)
	{
		Set<IUserPattern> upSet = null;
	
		IUserPatternUsers upusersimpl = new UserpatternUsersImpl();
		try
		{
			List<String> uplist = upusersimpl.getUserPatternsFromUserId(userId);
			
			
		
			if (uplist != null && uplist.size() > 0)
			{
				// Add defaultFormPattern of first UserPattern in IData of
				// HICData
				String formPattern = getDefaultFormPatForUserPat(uplist.get(0));
				if (hicReturnData.getData() != null
						&& hicReturnData.getData().getFormPattern() != null)
				{
					hicReturnData.getData().getFormPattern().setFormId(formPattern);
				}
				else
				{
					IFormPattern formpat = new FormPattern();
					formpat.setFormId(formPattern);
					hicReturnData.getData().getFormPattern().setFormId(formPattern);
				}

				upSet = new HashSet<IUserPattern>();
				for (String id : uplist)
				{
					logger.log(0,"-present in user pattterns for users get up-"
							+ id);
					IUserPattern up = new UserPattern();
					up.setUserPatternId(id);
					//Added RoleRights based on the UserPattern id ,change done by pra on 29-May-2009
					UserpatternRolesImpl patternImpl=new UserpatternRolesImpl();
					com.oxymedical.core.userdata.IRoleRights roleRights=patternImpl.getUserPatternRolesRights(id);
					com.oxymedical.core.userdata.IUser us = new com.oxymedical.core.userdata.User();
					us.setUserId(userId);
					up.setRoles(roleRights);
					up.setUser(us);
					upSet.add(up);
				}
			}
			return upSet;
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
		}
		catch (UAComponentException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private String getDefaultFormPatForUserPat(String userPatternId)
	{
		String defaultFormPattern = null;
		com.oxymedical.component.useradmin.IUserPattern upimpl = new UserPatternImpl();
		try
		{
			defaultFormPattern = upimpl.getDefaultFormPatFromUserPatternsId(userPatternId);
			logger.log(0," default for Pattern from db is-" + defaultFormPattern
					+ "-for " + "userpattern id--" + userPatternId);
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
		}
		catch (UAComponentException e)
		{
			e.printStackTrace();
		}
		logger.log(0,"-returned formpattern is--" + defaultFormPattern);
		return defaultFormPattern;
	}

	/**
	 * This function is used to authenticate Users
	 * 
	 * @param userName
	 *            The user Name to be authenticated.
	 * @param password
	 *            The password to be authenticated.
	 * @param ldapServer
	 *            The server against which users will be authenticated
	 * @return authenticateUser Returns boolean.
	 * @throws NoSuchContainerCompanyException
	 * @throws DBComponentException
	 * @throws NoSuchContainerException
	 * @throws NoSuchCompanyException
	 * @exception Throws
	 *                UAComponentException if user is not validated .
	 */
	public boolean authenticateUser(String userId, String password, String containerId,
			String ldapServer) throws NoSuchUserException, NoSuchCompanyException,
			NoSuchContainerException, DBComponentException, NoSuchContainerCompanyException,
			InValidPasswordException
	{
		boolean isuserAuthenticated = false;
		boolean isPasswordAuthenticated = false;
		boolean isCompContAuthenticated = false;
		boolean isUserCompContAuthenticated = false;

		User userInfo = UserImpl.findByPrimaryKey(userId);
		//Group groupInfo=UserImpl.getGroupbyUserId(userId);
		if (userInfo != null)
		{
			//Case sensitive user id checked
			String dbUserId = userInfo.getUserId();
			String dbPassword = userInfo.getPassword();
			Byte active = userInfo.getActive();
			// if user is active. then can login. active value is 1.
			Byte trueValue = 1;
			if(dbUserId.equals(userId)&& dbPassword.equals(password)&& active.equals(trueValue))
			{
				isuserAuthenticated = true;
				isPasswordAuthenticated=true;
			}
			//code commented because this os duplicate.done by pra on 10-aug-2009
			/*if(isuserAuthenticated)
			isPasswordAuthenticated = UserImpl.authenticatePassword(userInfo.getUserId(),
					password);*/

		}
		
		
		if ((isuserAuthenticated) && (isPasswordAuthenticated) )
			{
			isUserCompContAuthenticated = true;
		}
		
		return isUserCompContAuthenticated;
	}

	public boolean changePassword(String userId, String oldPassword, String newPassword)
			throws NoSuchUserException, NoSuchCompanyException, DBComponentException
	{
		boolean isPasswordAuthenticated = false;

		User userInfo = UserImpl.findByPrimaryKey(userId);
		if (userInfo != null)
		{
			if (userInfo.getPassword().trim().equals(oldPassword))
			{
				isPasswordAuthenticated = true;
				userInfo.setPassword(newPassword);
				ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(userInfo);
			}

		}
		return isPasswordAuthenticated;

	}

	public boolean authenticateContainer(String containerId, String companyId)
			throws NoSuchCompanyException, NoSuchContainerException, DBComponentException,
			NoSuchContainerCompanyException
	{
		boolean isContainerCompanyExists = false;
		CompanyCont companyCont = CompanyContImpl.findContainerByCompany(containerId,
				companyId);
		if (null != companyCont)
		{
			// logger.log(0,"Container exists");
			isContainerCompanyExists = true;
		}
		else
		{
			// logger.log(0,"Container does not exists");

		}
		// logger.log(0,"Container exists" +isContainerCompanyExists);
		return isContainerCompanyExists;
	}

	/**
	 * This function is used to retrieve User Object information if user is
	 * validated
	 * 
	 * @param userName
	 *            The user Name to be authenticated.
	 * @param password
	 *            The password to be authenticated.
	 * @param ldapServer
	 *            The server against which users will be authenticated
	 * @return retrieveUserInfo Returns User Object.
	 * @throws NoSuchContainerCompanyException
	 * @throws DBComponentException
	 * @throws NoSuchContainerException
	 * @exception Throws
	 *                UAComponentException if user is not validated .
	 */
	public User retrieveUserInfo(String userName, String password, String container,
			String ldapServer) throws UAComponentException, NoSuchContainerException,
			DBComponentException, NoSuchContainerCompanyException
	{
		User userInfoObject = null;
		if (authenticateUser(userName, password, container, ldapServer))
		{
			// Set company , organization, component in User Info and return the
			// object
			userInfoObject = UserImpl.findByPrimaryKey(userName);

		}
		return userInfoObject;
	}
	public Group retrieveGroupInfo(String userName, String password, String container,
			String ldapServer) throws UAComponentException, NoSuchContainerException,
			DBComponentException, NoSuchContainerCompanyException
	{
		Group groupInfoObject = null;
		if (authenticateUser(userName, password, container, ldapServer))
		{
			// Set company , organization, component in User Info and return the
			// object
			groupInfoObject = UserImpl.getGroupbyUserId(userName);

		}
		return groupInfoObject;
	}


	/**
	 * This function is used to retrieve Organiztaion Structure for a given
	 * Company Id.It gives the parent child relationship of company and their
	 * children Organizations.
	 * 
	 * @param companyId
	 *            The organization Structure of the given company.
	 * @return retrieveOrganizationStructure Returns OrganizationStructure
	 *         Object.
	 * @exception Throws
	 *                UAComponentException if company is not validated ,throws
	 *                DBComponentException.
	 */

	public OrganizationStructure retrieveOrganizationStructureFromContainer(String containerId)
			throws UAComponentException, DBComponentException
	{
		OrganizationStructure organizationStructure = new OrganizationStructure();
		ICompany companyImpl = new CompanyImpl();
		OrganizationImpl orgImpl = new OrganizationImpl();
		CompanyCont companyCont = new CompanyCont();

		companyCont = CompanyContImpl.getCompanyByContainer(containerId);
		if (null != companyCont)
		{
			Company company = companyImpl.getCompany(companyCont.getCompany().getCompanyId());
			if (null != company)
			{
				organizationStructure.setCompany(company);
			}
		}

		ArrayList<Organization> organizationList = orgImpl
				.getOrganizationByCompanyId(companyCont.getCompany().getCompanyId());

		Iterator orgIterator = organizationList.iterator();
		while (orgIterator.hasNext())
		{
			Organization organization = (Organization) orgIterator.next();
			ArrayList<Organization> childOrgList = orgImpl
					.getOrganizationByParentOrganizationId(organization.getName());
			OrganizationInfo orgInfo = new OrganizationInfo();
			orgInfo.setOrganization(organization);
			orgInfo.setOrganizationList(childOrgList);
			organizationStructure.getOrgInfoList().add(orgInfo);
		}

		return organizationStructure;
	}

	public OrganizationStructure retrieveOrganizationStructureFromCompany(String companyId)
			throws UAComponentException, DBComponentException
	{
		OrganizationStructure organizationStructure = new OrganizationStructure();
		ICompany companyImpl = new CompanyImpl();
		OrganizationImpl orgImpl = new OrganizationImpl();
		Company company = companyImpl.getCompany(companyId);
		if (null != company)
		{
			organizationStructure.setCompany(company);
		}
		ArrayList<Organization> organizationList = orgImpl
				.getOrganizationByCompanyId(companyId);
		Iterator orgIterator = organizationList.iterator();
		while (orgIterator.hasNext())
		{
			Organization organization = (Organization) orgIterator.next();
			ArrayList<Organization> childOrgList = orgImpl
					.getOrganizationByParentOrganizationId(organization.getName());
			OrganizationInfo orgInfo = new OrganizationInfo();
			orgInfo.setOrganization(organization);
			orgInfo.setOrganizationList(childOrgList);
			organizationStructure.getOrgInfoList().add(orgInfo);
		}

		return organizationStructure;
	}

	/**
	 * This function is used to encapsulate Organiztaion Structure in HIC Data
	 * for a given Company Id.The encapsulation is required to pass Org
	 * Structure to other components
	 * 
	 * @param companyId
	 *            The organization Structure of the given company.
	 * @return encapsulateOrgStructure Returns IHICData Object.
	 * @exception Throws
	 *                UAComponentException if company is not validated ,throws
	 *                DBComponentException.
	 */
	public IHICData encapsulateOrgStructureForCompany(String companyId)
			throws UAComponentException, DBComponentException
	{
		HICIntegration hicIntegration = new HICIntegration();
		IHICData hicData = new HICData();
		OrganizationStructure orgStructure = new OrganizationStructure();
		orgStructure = retrieveOrganizationStructureFromCompany(companyId);
		hicData = hicIntegration.encapsulate(companyId, orgStructure);
		return hicData;

	}

	public IHICData encapsulateOrgStructureForContainer(String containerId)
			throws UAComponentException, DBComponentException
	{
		HICIntegration hicIntegration = new HICIntegration();
		IHICData hicData = new HICData();
		OrganizationStructure orgStructure = new OrganizationStructure();
		orgStructure = retrieveOrganizationStructureFromContainer(containerId);
		hicData = hicIntegration
				.encapsulate(orgStructure.company.getCompanyId(), orgStructure);
		return hicData;

	}

	public void setUserAdminManager(IUserAdminManager userAdminManager)
	{
		// TODO Auto-generated method stub

	}

	public void destroy()
	{
		// TODO Auto-generated method stub

	}

	public void maintenance(IMaintenanceData arg0)
	{
		// TODO Auto-generated method stub

	}

	public void run()
	{
		// TODO Auto-generated method stub

	}

	public void start(Hashtable<String, Document> arg0)
	{
		// TODO Auto-generated method stub
		// logger.log(0,"-----------START OF UA CALLED--------------");
		// ConnectionDatabase.setDatabaseComponent(userAdminDBComponent);
		// logger.log(0,"-----------START OF UA ENDED--------------");

	}


	public DBComponent GetInstanceOfDatabaseComponent()
	{
		if (userAdminDBComponent == null)
		{
			// Major Exception
		}
		return userAdminDBComponent;
	}

	public void stop()
	{
		// TODO Auto-generated method stub

	}
	public static LoggingComponent GetInstanceOfLoggingComponent()
	{
		if(logger == null)
		  {
			logger = new LoggingComponent(); 
		  }
		  return  logger;
	}

	public static void main(String args[])
	{
		 UserAdminComponent.GetInstanceOfLoggingComponent();

		/*
		 * UserAdminComponent ua = new UserAdminComponent(); Hashtable<String,
		 * Object> formValues = new Hashtable(); List<String> textId = new
		 * ArrayList<String>();
		 * 
		 * 
		 * formValues.put("combobox38", "3"); formValues.put("combobox36", "2");
		 * formValues.put("datepicker21", "2009/02/15");
		 * 
		 * formValues.put("textbox70", "harmeet"); formValues.put("textbox6",
		 * "1234"); formValues.put("textbox62", "harmeet");
		 * 
		 * // datepicker21=2009/02/12, textbox53=1/1/1954, textbox51=Mod, //
		 * textbox48=4, textbox50=Pil, textbox46=5 am, combobox38=4, //
		 * combobox36=7, textbox44=2 am
		 * 
		 * 
		 * formValues.put("textbox0", "4444"); formValues.put("textbox1",
		 * "Harpreet4444");
		 * 
		 * IHICData hicData = new HICData();
		 * com.oxymedical.core.commonData.IData data = new
		 * com.oxymedical.core.commonData.Data();
		 * 
		 * com.oxymedical.core.commonData.IFormPattern formPattern = new
		 * com.oxymedical.core.commonData.FormPattern();
		 * formPattern.setFormId("User"); formPattern.setFormValues(formValues);
		 * Object[] array = data.getClass().getMethods();
		 * data.setMethodName("addUserFromApplication");
		 * com.oxymedical.core.commonData.IDataPattern dataPattern = new
		 * com.oxymedical.core.commonData.DataPattern();
		 * dataPattern.setDataPatternId("useradmin");
		 * data.setDataPattern(dataPattern); data.setFormPattern(formPattern);
		 * 
		 * QueryData qd = new QueryData();qd.setCondition(
		 * "get patient.patientid,patient.firstname,patient.lastname,patient.phone,patient.dob from patient"
		 * ); qd.setIdField("id"); Hashtable columnOrder =new Hashtable();
		 * columnOrder.put("0", "patientid"); columnOrder.put("1", "firstname");
		 * columnOrder.put("2", "lastname"); columnOrder.put("3", "phone");
		 * columnOrder.put("4", "dob"); data.setColumnOrder(columnOrder);
		 * data.setQueryData(qd);
		 * 
		 * IApplication application = new Application();
		 * application.setApplicationName("UserAdminApp"); application
		 * .setBaseDirectoryPath
		 * ("D:/Harmeet_WorkData/nolis/Nolis-Docs/trunk/UserAdminApp");
		 * application.setApplicationFileName("userAdminApp.esp"); application
		 * .setApplicationFolderPath
		 * ("D:/Harmeet_WorkData/nolis/Nolis-Docs/trunk/UserAdminApp/");
		 * application
		 * .setServerDirectory("D:/glassfish/domains/domain1/autodeploy");
		 * application
		 * .setBaseDirectoryPath("D:/glassfish/domains/domain1/lib/ext");
		 * hicData.setApplication(application); hicData.setData(data);
		 * ua.createUserFromApplication(hicData);
		 */
		
		/**
		 * 
		 */
		Hashtable<String, Object> formValues = new Hashtable();
		List<String> textId = new ArrayList<String>();
//
		formValues.put("userId", "testuserone");
		formValues.put("password", "1234");
		
//		formValues.put("textbox44", "1700");
//		formValues.put("textbox46", "1900");
		
		/*formValues.put("textbox70", "testuserDbcomponent");
		formValues.put("textbox6", "12345");
		formValues.put("textbox62", "test");
		formValues.put("textbox66", "user");
		formValues.put("textbox68", "dbcomponent");
		formValues.put("textbox89", "abc@gmail.com");
		formValues.put("combobox54", "0");
		formValues.put("combobox56", "testuserpatternone");*/
		

		// datepicker21=2009/02/12, textbox53=1/1/1954, textbox51=Mod,
		// textbox48=4, textbox50=Pil, textbox46=5 am, combobox38=4,
		// combobox36=7, textbox44=2 am

		/*
		 * formValues.put("textbox0", "4444"); formValues.put("textbox1",
		 * "Harpreet4444");
		 */
		IHICData hicData = new HICData();
		com.oxymedical.core.commonData.IData data = new com.oxymedical.core.commonData.Data();

		com.oxymedical.core.commonData.IFormPattern formPattern = new com.oxymedical.core.commonData.FormPattern();
		formPattern.setFormId("login");
		formPattern.setFormValues(formValues);
		Object[] array = data.getClass().getMethods();
		data.setMethodName("authenticateUserEx");
		com.oxymedical.core.commonData.IDataPattern dataPattern = new com.oxymedical.core.commonData.DataPattern();
		dataPattern.setDataPatternId("useradmin");
		QueryData qd = new QueryData();
		String sql = "get Logindetails.userId,Logindetails.LoginDate from Logindetails conditions Logindetails.userId:=[testuserone]";
		qd.setCondition(sql);
		data.setQueryData(qd);
		
		data.setDataPattern(dataPattern);
		data.setFormPattern(formPattern);
	/*	QueryData qd = new QueryData();
		qd.setCondition("get patient.patientid,patient.firstname,patient.lastname,patient.phone,patient.dob from patient");
		qd.setIdField("patientid");
		Hashtable columnOrder = new Hashtable();
		columnOrder.put("0", "patientid");
		columnOrder.put("1", "firstname");
		columnOrder.put("2", "lastname");
		columnOrder.put("3", "phone");
		columnOrder.put("4", "dob");
		
		
		data.setColumnOrder(columnOrder);
		data.setQueryData(qd);*/
		IApplication application = new Application();
		application.setApplicationName("NOLISUI");
		application
				.setBaseDirectoryPath("D:\\CDrive\\NOLIS_SVN\\NOLIS\\trunk\\dev\\src\\main\\NOLISApps\\NOLIS_Wisconsin\\NOLISUI");
		application.setApplicationFileName("NOLISUI.esp");
		application
				.setApplicationFolderPath("D:\\CDrive\\NOLIS_SVN\\NOLIS\\trunk\\dev\\src\\main\\NOLISApps\\NOLIS_Wisconsin\\NOLISUI/");
		application.setServerDirectory("c:/glassfish/domains/domain1/autodeploy");
		application.setBaseDirectoryPath("c:/glassfish/domains/domain1/lib/ext");
		hicData.setApplication(application);
		hicData.setData(data);
		UserAdminComponent userAdmin= new UserAdminComponent();
		
		String xmlPathName ="D:/CDrive/NOLISback/trunk/dev/src/main/NOLISApps/Win1/NOLISUI/users/UserPatterns.xml";
		
		String applicationName ="NOLISUI";
		
		UserImpl userimpl = new UserImpl();
		try
		{
			/*UserAdminComponent.dataBaseInfo = new UserDataBaseInfo();
			UserAdminComponent.dataBaseInfo.setUserName("root");
			UserAdminComponent.dataBaseInfo.setPassword("1234");
			UserAdminComponent.dataBaseInfo.setDbServerName("jdbc:mysql://localhost:3306/");*/
			//logger.log(0,"---Call delete user from main----");
			//userimpl.deleteUser("testuserthree", "", "", "", false)application;
			//userAdmin.createUserFromApplication(hicData);
			userAdmin.checkConnectionSettings(hicData);
			
			List<String> parameters = new ArrayList<String>();
			parameters.add(applicationName);
			parameters.add(xmlPathName);
			data.setList(parameters);
			userAdmin.registerNewApplication(hicData);
		//	userAdmin.logOut(hicData);
			
			//userAdmin.registerNewApplication(xmlPathName, applicationName);
		//userAdmin.getListData(hicData);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This function is userd to read users xml from a given path and pass the
	 * argument to registerUsersFromXML() method
	 * 
	 * @param xmlPathName
	 *            The path from where users xml will be picked.
	 * @return registerNewApplication Returns void.
	 * 
	 */
	
	@EventSubscriber(topic = "registerNewApplication")
	public void registerNewApplication(IHICData hicData)
			throws DBComponentException, NoSuchGroupException, NoSuchCompanyException,
			DuplicateUserIdException, NoSuchUserException, NoSuchRoleException,
			NoSuchContainerException, UAComponentException
	{
		String xmlPathName = (String)hicData.getData().getList().get(1);
		String applicationName = (String)hicData.getData().getList().get(0);
		System.out.println("------------------Inside UA register new application----------"+xmlPathName);

		XMLParser xmlparser = new XMLParser();
		XmlReader xmlReader = new XmlReader();
		Document userXmlDoc = null;

		userXmlDoc = xmlReader.getDocumentFromPath(xmlPathName);
		xmlparser.registerUsersFromXML(userXmlDoc, applicationName, ldapComp);
	}

	/**
	 * Subscriber for persisting values of module.xml
	 * 
	 * @throws DBComponentException
	 * @throws DocumentException
	 * 
	 */
	public void invokeContImpl() throws DBComponentException, DocumentException
	{
		// logger.log(0,"!!!!!!!!!!!!!!UA Component-inside invokeContImpl");
		ContainerImpl containerImpl = new ContainerImpl();
		containerImpl.addContainerData(userAdminDBComponent, containerSettings);
	}

	public OrganizationStructure retrieveOrganizationStructureFromUser(String userName,
			String companyId) throws UAComponentException, DBComponentException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the hicData
	 */
	public IHICData getHicData()
	{
		return hicData;
	}

	/**
	 * @param hicData
	 *            the hicData to set
	 */
	public void setHicData(IHICData hicData)
	{
		this.hicData = hicData;
	}

	public Role[] retrieveUserRoles(String userId, String password)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This function is used to generate Roles in xml format for the specicfed
	 * user
	 * 
	 * @param userName
	 *            User whose roles are requested to be retrieved
	 * @return String
	 * @throws DBComponentException
	 * @throws UAComponentException
	 * 
	 */
	public String generateRolesXML(String userId) throws DBComponentException,
			UAComponentException
	{
		User user = null;
		ArrayList<UsersRole> usersRole = null;
		Role role = null;

		IUser userImpl = new UserImpl();
		IRole roleImpl = new RoleImpl();

		// logger.log(0,"User Name is" +userName);

		user = UserImpl.findByPrimaryKey(userId);
		usersRole = userImpl.getUserRoles(user.getUserId());
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("rolelist");

		for (Iterator usersRoleRow = usersRole.iterator(); usersRoleRow.hasNext();)
		{
			UsersRole usersRoleRowData = (UsersRole) usersRoleRow.next();
			role = roleImpl.getRole(usersRoleRowData.getComp_id().getRoleId());
			Element roleElement = root.addElement("role");
			Element roleId = roleElement.addElement("id").addText(role.getRoleId());
			Element roleName = roleElement.addElement("name").addText(role.getName());
			Element defaultURL = roleElement.addElement("defaultURL").addText(
					role.getDefaultUrl());

		}
		// logger.log(0,"role XML is" +document.getRootElement().asXML());
		return document.getRootElement().asXML();
	}


	
	
	public void deleteItem(Document doc, LDAPComponent ldapComp)
	{
		IUser userImpl = new UserImpl();
		// userImpl.deleteUser(userId)
		/*
		 * if(itemType.equalsIgnoreCase(Constants.USER_ITEM)) { //UserImpl. }
		 * else if(itemType.equalsIgnoreCase(Constants.GROUP_ITEM)) {
		 * 
		 * } else if(itemType.equalsIgnoreCase(Constants.ORG_ITEM)) {
		 * 
		 * }
		 */

	}

	public void retrieveRoleRights(String roleName) throws DBComponentException,
			UAComponentException
	{
		IRoleRights roleRightImpl = new RoleRightsImpl();
		ArrayList<String> rightList = roleRightImpl.getRoleRights(roleName);

	}

	// Copies src file to dst file.
	// If the dst file does not exist, it is created
	void copy(File src, File dst) throws IOException {
	    InputStream in = new FileInputStream(src);
	    OutputStream out = new FileOutputStream(dst);

	    // Transfer bytes from in to out
	    byte[] buf = new byte[1024];
	    int len;
	    while ((len = in.read(buf)) > 0) {
	        out.write(buf, 0, len);
	    }
	    in.close();
	    out.close();
	}

	@EventSubscriber(topic = "setUADataBaseSetting")
	public void setDataBaseSetting(IHICData hicData)
	{
		if (dataBaseInfo == null)
		{
			IDataBase database = hicData.getDatabase();
			if (database == null)
			{
				String dataSettingPath = null;
				File dataSettings = new File("datasettings.xml");
				System.out.println("[================ dataSettings full file path =============]"+dataSettings.getAbsolutePath());
				if(dataSettings.exists()){
					dataSettingPath = dataSettings.getAbsolutePath();
					System.out.println("==dataSettings.exists()=="+dataSettingPath);
				}else{
					dataSettingPath = hicData.getApplication().getApplicationFolderPath()
					+ Constants.DATABASESETTING;
					
					File src = new File(dataSettingPath);
					File dst = new File("datasettings.xml");
					System.out.println("[====== dst full path =====]"+dst.getAbsolutePath());
					try {
						copy(src, dst);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				database = new DataBase();
				database = setDataBaseSettingFromFile(dataSettingPath, database);
			}
			dataBaseInfo = new UserDataBaseInfo();
			String dbName = database.getDataBaseName();
			if (dbName == null)
				dbName = Constants.DBNAME;
			dataBaseInfo.setUserName(database.getUserName());
			dataBaseInfo.setPassword(database.getPassword());
			dataBaseInfo.setDbServerName(database.getServerName());
			dataBaseInfo.setDbType(database.getDbType());
			dataBaseInfo.setDBPort(database.getDataBasePort());
			dataBaseInfo.setDbName(dbName);
		}
		
		ConnectionDatabase.setDatabaseComponent(userAdminDBComponent);
	}

	private void checkConnectionSettings(IHICData hicData)
	{
		setDataBaseSetting(hicData);
		if (dataBaseInfo == null)
		{
			String dataSettingPath = hicData.getApplication().getApplicationFolderPath()
					+ Constants.DATABASESETTING;
			//setDataBaseSettingFromFile(dataSettingPath, hicData);
			setDataBaseSetting(hicData);
		}
	}

	/**
	 * This method is used to set the database setting like username , password
	 * 
	 * Parameter and return type changed from IHicdata to Idatabase.
	 * Changes by wasim, 21-May-2009.
	 * portnumber and dbtype hardcoded removed
	 * changes by wasim, 8-june-2009
	 * 
	 */
	private IDataBase setDataBaseSettingFromFile(String dataSettingPath, IDataBase dataBase)
	{
		
		FileInputStream inputFile = null;
		Document document = null;
		String userName;
		String password;
		String serverName;
		String portNumber;
		String dbType;
		try
		{
			inputFile = new FileInputStream(dataSettingPath);
			InputStreamReader dataInputStream = new InputStreamReader(inputFile);
			SAXReader reader = new SAXReader();
			document = reader.read(dataInputStream);
			Element root = document.getRootElement();
			userName = root.element("user").getTextTrim();
			password = root.element("password").getTextTrim();
			logger.log(0,"database password  " + password);
			serverName = root.element("servername").getTextTrim();
			dbType = root.element("databasetype").getTextTrim();
			portNumber = root.element("portno").getTextTrim();
			if (null != userName && null != password && null != serverName)
			{
				dataBase.setUserName(userName);
				dataBase.setPassword(password);
				dataBase.setServerName(serverName);
				dataBase.setDbType(dbType);
				dataBase.setDataBasePort(portNumber);
				dataBase.setDataBaseName(Constants.DBNAME);
				

			}

		}
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
		}
		
		return dataBase;
	}

	public Object invoke()
	{
		return null;
	}


	
	/**
	 * This method creates UserPattern From Application
	 * added by pra
	 * @param hicInputData
	 * @return IHICData
	 * @throws UAComponentException
	 * @throws DBComponentException
	 */
	// Method implementation changed.Now directly XMlParser is called and
	// Document is passed to it.Done by pra on 26-May-2009
	@EventSubscriber(topic = "AddUserPattern")
	 public IHICData createUserPatternFromApplication(Object hicInputData)
			throws UAComponentException, DBComponentException {
		IHICData hicObjectData = (IHICData) hicInputData;
		Hashtable formValues = hicObjectData.getData().getFormPattern()
				.getFormValues();
		String userPatternId = (String) formValues.get("userPatternId");
		List<String> privileges = (List<String>) formValues.get("privileges");
		List<String> fields = (List<String>) formValues.get("fields");
		String defaultFormPattern = (String) formValues
				.get("defaultFormPattern");
		String companyId = (String) formValues.get("companyId");
		String applicationName = hicObjectData.getApplication()
				.getApplicationName();
		XMLCreator xmlCreator = new XMLCreator();
		/*Document rightXmlPath = xmlCreator.createXmlForRight(userPatternId,
				applicationName,companyId);*/
		Document roleXmlPath = xmlCreator.createXmlForRole(userPatternId,
				privileges, applicationName,companyId);
		Document userPatternXmlPath = xmlCreator.createXmlForUserPattern(
				userPatternId,privileges, applicationName, defaultFormPattern,companyId);
		XMLParser xmlParser= new XMLParser();
	//	xmlParser.registerUsersFromXML(rightXmlPath, applicationName, ldapComp);
		xmlParser.registerUsersFromXML(roleXmlPath, applicationName, ldapComp);
		xmlParser.registerUsersFromXML(userPatternXmlPath, applicationName, ldapComp);
		IUserFields userfields = new UserFieldsImpl();
		userfields.addUserDefinedFields(userPatternId, fields);
		return hicObjectData;

	}

	
	@SuppressWarnings("unchecked")
	@EventSubscriber(topic = "addUserFromApplication")
	public IHICData createUserFromApplication(Object hicInputData)
	{
	
		IHICData hicObjectData = (IHICData) hicInputData;
		String userxml = null;
		String windowFormID = null;
		Hashtable<String, Object> formValues = null;
		/*
		 * if(hicObjectData.getMetaData()!=null &&
		 * hicObjectData.getMetaData().getCommonObject().toString();
		 * logger.log(0,"-----------userxml is ------"+userxml); }
		 */
		try
		{
			if (!isApplicationRegistered)
			{
				registerWindowWithApplication(hicObjectData);
			}
			if (hicObjectData != null)
			{
				IData data = hicObjectData.getData();
				IFormPattern formPatternData = data.getFormPattern();
				windowFormID = formPatternData.getFormId();
				// windowFormID="formpattern0";
				formValues = formPatternData.getFormValues();
				IApplication application = hicObjectData.getApplication();
				String applicationName = application.getApplicationName();
				
				Hashtable databaseTableHash = createHashTablesForXml(applicationName,
						windowFormID, formValues);
				if (databaseTableHash != null)
				{
					createXmlAndSave(databaseTableHash);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return hicObjectData;

	}

	@SuppressWarnings("unchecked")
	private void createXmlAndSave(Hashtable databaseTableHash)
			throws NoSuchOrganizationException, DBComponentException
	{
		Hashtable fieldsData = new Hashtable();
		/*
		 * while (requestTableEnum.hasMoreElements()) { String requestFieldName
		 * = (String) requestTableEnum.nextElement();
		 */
		
		if (databaseTableHash != null
				&& databaseTableHash.get(Constants.USERADMIN_DB_NAME) != null)
		{
			fieldsData = (Hashtable) databaseTableHash.get(Constants.USERADMIN_DB_NAME);
			fieldsData = ConnectionDatabase.GetInstanceOfDatabaseComponent().getDataHandler()
					.getFieldsFromHashData(fieldsData);
			if (fieldsData != null)
			{
				String organizationId = fieldsData.get(Constants.organizationId_in_db) != null ? fieldsData
						.get(Constants.organizationId_in_db).toString()
						: "";
				String userPatternId = fieldsData.get(Constants.userPatternId) != null ? fieldsData
						.get(Constants.userPatternId).toString()
						: "";

				String userId = fieldsData.get(Constants.userId) != null ? fieldsData.get(
						Constants.userId).toString() : "";
				String password = fieldsData.get(Constants.password_in_db) != null ? fieldsData
						.get(Constants.password_in_db).toString()
						: "";

				String firstName = fieldsData.get(Constants.firstName) != null ? fieldsData
						.get(Constants.firstName).toString() : "";
				String middleName = fieldsData.get(Constants.middleName) != null ? fieldsData
						.get(Constants.middleName).toString() : "";
				String lastName = fieldsData.get(Constants.lastName) != null ? fieldsData.get(
						Constants.lastName).toString() : "";
				String birthday = fieldsData.get(Constants.birthday) != null ? fieldsData.get(
						Constants.birthday).toString() : "";
				String emailAddress = fieldsData.get(Constants.emailId) != null ? fieldsData
						.get(Constants.emailId).toString() : "";
				String male = fieldsData.get(Constants.male) != null ? fieldsData.get(
						Constants.male).toString() : "";
			
						/*These are the new fields added for creating the New User Account 
						 * and impmentation changed accordingly.
						 * Added by pra on 22-May-2009 
						 */					
				String employeeNumber = fieldsData
						.get(Constants.employeeNumber) != null ? fieldsData
						.get(Constants.employeeNumber).toString() : "";
				String category = fieldsData.get(Constants.category) != null ? fieldsData
						.get(Constants.category).toString()
						: "";
				String address1 = fieldsData.get(Constants.address1) != null ? fieldsData
						.get(Constants.address1).toString()
						: "";
				String address2 = fieldsData.get(Constants.address2) != null ? fieldsData
						.get(Constants.address2).toString()
						: "";
				String universalProviderIdentificationNumber = fieldsData
						.get(Constants.universalProviderIdentificationNumber) != null ? fieldsData
						.get(Constants.universalProviderIdentificationNumber)
						.toString()
						: "";
				String nationalProvidedIdentificationNumber = fieldsData
						.get(Constants.nationalProvidedIdentificationNumber) != null ? fieldsData
						.get(Constants.nationalProvidedIdentificationNumber)
						.toString()
						: "";
				String city = fieldsData.get(Constants.city) != null ? fieldsData
						.get(Constants.city).toString()
						: "";
				String zipCode = fieldsData.get(Constants.zipCode) != null ? fieldsData
						.get(Constants.zipCode).toString()
						: "";
				String state = fieldsData.get(Constants.state) != null ? fieldsData
						.get(Constants.state).toString()
						: "";
				String telephoneNumber = fieldsData
						.get(Constants.telephoneNumber) != null ? fieldsData
						.get(Constants.telephoneNumber).toString() : "";
				String prefixId = fieldsData.get(Constants.prefixId) != null ? fieldsData
						.get(Constants.prefixId).toString()
						: "";
				String suffixId = fieldsData.get(Constants.suffixId) != null ? fieldsData
						.get(Constants.suffixId).toString()
						: "";	
				String active_ = fieldsData.get(Constants.active_) != null ? fieldsData
								.get(Constants.active_).toString()
								: "";	
								
				//added implementation for inactive date.change done by pra on 16-june-2009
				String deActiveDate=fieldsData.get(Constants.inActiveDate) != null ? fieldsData
						.get(Constants.inActiveDate).toString()
						: "";	
				Date inActive=null;
				 if(deActiveDate!=null && deActiveDate!="")
				 {
					inActive=DateUtil.stringToDate(deActiveDate,"yyyy-MM-dd"); 
				 }

				if (organizationId != null && organizationId != "")
				{
					IOrganization organizationImpl = new OrganizationImpl();
					Organization organization = new Organization();
					organization = organizationImpl.getOrganization(organizationId);
					if (null != organization)
					{
						String companyId = organization.getCompanyId();
						UserInfo userInfo = new UserInfo();
						userInfo.setOrganizationId(organizationId);
						userInfo.setCompanyId(companyId);
						userInfo.setFirstName(firstName);
						userInfo.setMiddleName(middleName);
						userInfo.setLastName(lastName);
						userInfo.setEmailAddress(emailAddress);
						userInfo.setUserId(userId);
						userInfo.setPassword(password);
						
						


						userInfo.setEmployeeNumber(employeeNumber);
						userInfo.setAddress1(address1);
						userInfo.setAddress2(address2);

						userInfo
								.setNationalProvidedIdentificationNumber(nationalProvidedIdentificationNumber);

						userInfo
								.setUniversalProviderIdentificationNumber(universalProviderIdentificationNumber);
						userInfo.setCategory(category);
						if(city!=null && city!="")
						{
							userInfo.setCity(Integer.valueOf(city).intValue());
						}
						
						userInfo.setState(state);
						if(telephoneNumber!=null && telephoneNumber!="")
						{
						userInfo.setTelephoneNumber(Integer.valueOf(
								telephoneNumber).intValue());
						}
						if(zipCode!=null && zipCode!="")
						{
							userInfo
							.setZipCode(Integer.valueOf(zipCode).intValue());	
						}						
						userInfo.setSuffixId(suffixId);
						userInfo.setPrefixId(prefixId);
						if(active_!=null && active_!="")
						userInfo.setActive_(Integer.valueOf(active_).byteValue());
						if ((male != null && !male.equals(""))
								&& male.equalsIgnoreCase("male"))
							userInfo.setMale(true);
						if(inActive!=null)
						{
							userInfo.setInActiveDate(inActive);
						}
						IUser userImpl = new UserImpl();
						try
						{
							userImpl.addUser(userInfo.getCreatorUserId(), userInfo
									.getCompanyId(), userInfo.isAutoUserId(), userInfo
									.getUserId(), userInfo.getPassword(), userInfo
									.getEmailAddress(), userInfo.getLocale(), userInfo
									.getFirstName(), userInfo.getMiddleName(), userInfo
									.getLastName(), userInfo.getNickName(), userInfo
									.getPrefixId(), userInfo.getSuffixId(), userInfo.isMale(),
									userInfo.getBirthdayMonth(), userInfo.getBirthdayDay(),
									userInfo.getBirthdayYear(), userInfo.getJobTitle(),
									userInfo.getOrganizationId(), userInfo.getLocationId(),userInfo.getEmployeeNumber(),userInfo.getAddress1(),userInfo.getAddress2()
									,userInfo.getNationalProvidedIdentificationNumber(),userInfo.getUniversalProviderIdentificationNumber(),userInfo.getCategory()
									,userInfo.getCity(),userInfo.getState(),userInfo.getTelephoneNumber(),userInfo.getZipCode(),userInfo.getActive_(),userInfo.getInActiveDate(),userInfo.getGroupId());

				
							addOrgsAndUserPats(userId, organizationId, userPatternId,
									companyId);

						}
						catch (NoSuchUserException e)
						{
							e.printStackTrace();
						}
						catch (DuplicateUserIdException e)
						{
							e.printStackTrace();
							// Duplicate user but update all other entries also
							logger.log(0,"---Duplicate user but update all other entries also---");
							addOrgsAndUserPats(userId, organizationId, userPatternId,
									companyId);
						}
						catch (OrganizationParentException e)
						{
							e.printStackTrace();
						}
						catch (NoSuchCompanyException e)
						{
							e.printStackTrace();
						}
						catch (NoSuchGroupException e)
						{
							e.printStackTrace();
						}
						catch (UAComponentException e)
						{
							e.printStackTrace();
						}

					}
				}

			}

		}
	}

	private void addOrgsAndUserPats(String userId, String organizationId,
			String userPatternId, String companyId)
	{
		

		try
		{
			// TODO This condition must be present while
			// updating user we
			// can change organization and userPatten so need to
			// set organization again in User Orgs.
			IOrganization organizationImpl = new OrganizationImpl();
			IUser userImpl = new UserImpl();
			// Get Orgs related to Users------
			List userOrgs = organizationImpl.getUserOrganizationsFromUserOrgs(userId);
			boolean orgNotExist = false;
			if (userOrgs != null && userOrgs.size() > 0)
			{
				for (Object orgId : userOrgs)
				{
					String temporgid = (String) orgId;
					
					if (!temporgid.equalsIgnoreCase(organizationId))
					{
						orgNotExist = true;
						organizationImpl.deleteOrganizationFromUserOrgs(temporgid, userId);
					}
					else
					{
						orgNotExist = false;
					}

				}
			}
			else
			{
				orgNotExist = true;
			}
			if (orgNotExist)
			{
				userImpl.addUserOrganization(userId, organizationId);
			}

			// Add update user patterns

			if (userPatternId != null && userPatternId != "")
			{
				IUserPatternUsers userPatternUserImpl = new UserpatternUsersImpl();
				List<String> userpats = userPatternUserImpl.getUserPatternsFromUserId(userId);

				boolean upatNotExist = false;
				if (userpats != null && userpats.size() > 0)
				{
					for (String userpatid : userpats)
					{
						
						if (!userpatid.equalsIgnoreCase(userPatternId))
						{
							upatNotExist = true;
							userPatternUserImpl.deleteUserPatternForUser(userpatid, userId);
						}
						else
						{
							upatNotExist = false;
						}

					}
				}
				else
				{
					upatNotExist = true;
				}
				if (upatNotExist)
				{
					ArrayList<User> userList = new ArrayList<User>();
					UserPatternUsersInfo userpatternUserInfo = new UserPatternUsersInfo();
					userpatternUserInfo.setUserPatternId(userPatternId);
					userpatternUserInfo.setUserId(userId);
					userpatternUserInfo.setCompanyId(companyId);
					User user = new User();
					user.setUserId(userpatternUserInfo.getUserId());
					userList.add(user);


					userPatternUserImpl.addUserpatternUsers(userpatternUserInfo
							.getUserPatternId(), userpatternUserInfo.getCompanyId(), userList);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to save data in database.
	 * 
	 * @param String
	 *            maintenanceID
	 * @return void
	 * @throws DBComponentException
	 */
	@SuppressWarnings("unchecked")
	private Hashtable createHashTablesForXml(String applicationName, String baseWindow,
			Hashtable requestTable)
	{
		Hashtable databaseTableHash = new Hashtable();

		try
		{
			databaseTableHash = ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.getDataHandler().getHashDataForApplication(applicationName, baseWindow,
							requestTable, "");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return databaseTableHash;
	}

	private void registerWindowWithApplication(IHICData hicData)
	{
		ConnectionDatabase.GetInstanceOfDatabaseComponent().registerWindowForApplication(
				hicData);
		isApplicationRegistered = true;
	}

	@EventSubscriber(topic = "executeListUserAdmin")
	public IHICData getListInGridUserAdmin(Object hicInputData)
	{
		IHICData hicObjectData = (IHICData) hicInputData;
		try
		{
			checkConnectionSettings(hicObjectData);
			ConnectionDatabase.GetInstanceOfDatabaseComponent().setConnectionAvailable(true);
			hicObjectData = ConnectionDatabase.GetInstanceOfDatabaseComponent().getListData(
					hicObjectData);
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return hicObjectData;

	}
//
	/*
	 * This code commented. This is useless now.
	 * 
	 * Changes by Wasim, 21-May-2009.
	 * 
	 * 
	 */
	
	
	
	/*@EventSubscriber(topic = "executeQueryUserAdmin"
	public IHICData getQueryFromUserAdmin(Object hicInputData)
	{
		logger.log(0,"----hicInputData------in executeListUserAdmin--");
		IHICData hicObjectData = (IHICData) hicInputData;
		try
		{
			ConnectionDatabase.GetInstanceOfDatabaseComponent().setConnectionAvailable(true);
			hicObjectData = ConnectionDatabase.GetInstanceOfDatabaseComponent().getListData(
					hicObjectData);
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return hicObjectData;

	}*/

	/* (non-Javadoc)
	 * @see com.oxymedical.component.useradmin.IUserAdminComponent#removeUserFromApplication(java.lang.Object)
	 */
	@EventSubscriber(topic = "deleteUserFromApplication")
	public IHICData removeUserFromApplication(Object hicInputData)
	{
		
		IHICData hicObjectData = (IHICData) hicInputData;

		try
		{
			if (hicObjectData != null)
			{
				IData data = hicObjectData.getData();
				QueryData queryResponseData = null;
				queryResponseData = data.getQueryData();
				String queryStr = data.getQueryData().getCondition();

				String userId = "";
				if (queryStr.indexOf("=") > 0)
				{
					String[] queryParams = queryStr.split("=");
					if (queryParams.length == 2)
					{
						userId = queryParams[1];
						UserImpl userimpl = new UserImpl();

						boolean ldapPresent = false;
						if (ldapComp != null)
						{
						
							ldapPresent = true;
						}

						// TODO Need to add condition here for deleting user
						// from
						// ldap also, now not required

						/*
						 * if(ldapPresent) { //fatch data on the basis of user
						 * using userimpl functions and pass these parameters //
						 * to delete a user, ldappresent boolean will set as
						 * true so it will delete //user from ldap also
						 * userimpl.deleteUser(userId, firstName, lastName,
						 * organizationId, ldapPresent); } else { Do the things
						 * here }
						 */

						userimpl.deleteUser(userId, "", "", "", false);
					}
				}
				else
				{
					logger.log(0,"Userid not foound for delete");
				}
				/*
				 * ConnectionDatabase.GetInstanceOfDatabaseComponent().setConnectionAvailable
				 * (true);logger.log(0,
				 * "---Check in other way just call the same function of Db for deletion-updatedata--"
				 * );
				 * ConnectionDatabase.GetInstanceOfDatabaseComponent().updateData
				 * (hicObjectData);
				 */
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return hicObjectData;

	}

	/**
	 * This method is used to Soft Delete the user from application.
	 * Added by pra on 26-may-2009
	 * @param hicInputData
	 * @return IHICData
	 */
	@EventSubscriber(topic = "SoftDeleteUser")
	public IHICData deleteSoftUserFromApplication(Object hicInputData)
	{
		
		IHICData hicObjectData = (IHICData) hicInputData;
		try
		{
			if (hicObjectData != null)
			{
				IData data = hicObjectData.getData();
				String queryStr = data.getQueryData().getCondition();
				String userId = "";
				if (queryStr.indexOf("=") > 0)
				{
					String[] queryParams = queryStr.split("=");
					
					if (queryParams.length == 2)
					{
						userId = queryParams[1];
						UserImpl userimpl = new UserImpl();

						boolean ldapPresent = false;
						if (ldapComp != null)
						{
						
							ldapPresent = true;
						}

						// TODO Need to add condition here for deleting user
						// from
						// ldap also, now not required

						/*
						 * if(ldapPresent) { //fatch data on the basis of user
						 * using userimpl functions and pass these parameters //
						 * to delete a user, ldappresent boolean will set as
						 * true so it will delete //user from ldap also
						 * userimpl.deleteUser(userId, firstName, lastName,
						 * organizationId, ldapPresent); } else { Do the things
						 * here }
						 */

						userimpl.softDeleteUser(userId, "", "", "", false);
					}
				}
				else
				{
					logger.log(0,"Userid not foound for delete");
				}
				
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return hicObjectData;

	}
	
	
	// This is used when userinfo is edited .This method was previously
	// commented now it is in use. Also method implementation changed as now we send the data in the list.Required changes done.
	//change done by pra on 28-may-2009
	/**
	 * @param hicInputData
	 * @return
	 * 
	 * This is commented method. no use of this method.
	 * Changes by Wasim Khan, 21-May-2009.
	 */
	
	@EventSubscriber(topic = "getComboDataFromUserAdmin")
	public IHICData getComboDataFromUserAdmin(Object hicInputData)
	{
		logger.log(0,"hicInputData   in getComboDataFromUserAdmin"
				+ hicInputData);
		IHICData hicObjectData = (IHICData) hicInputData;
		LinkedHashMap<String, LinkedHashMap<String, String>> returnMap = null;		
		LinkedHashMap<String, String> tempMap = null;
		try
		{
			if (hicObjectData != null)
			{
				IData data = hicObjectData.getData();
				QueryData queryResponseData = null;
				queryResponseData = data.getQueryData();
				String queryStr = data.getQueryData().getCondition();

				String userId = "";
				if (queryStr.indexOf("=") > 0)
				{
					String[] queryParams = queryStr.split("=");
					if (queryParams.length == 2)
					{
						userId = queryParams[1];
						
						/*returnMap = new LinkedHashMap<String, LinkedHashMap<String, String>>();
						tempMap = new LinkedHashMap<String, String>();*/

						OrganizationImpl orgimpl = new OrganizationImpl();
						List userOrgs = orgimpl.getUserOrganizationsFromUserOrgs(userId);
						Object[] obj=new Object[2];
						if (userOrgs.size() > 0)
						{
							
							/*
							 * String orgId =
							 * ((Organization)userOrgs.get(0)).getOrganizationId
							 * (); String orgName =
							 * ((Organization)userOrgs.get(0)).getName();
							 * System.
							 * out.println("-- organizationId----"+orgId);
							 * System
							 * .out.println("-- organizationName----"+orgName);
							 * tempMap.put(String.valueOf(0), orgName);
							 */
							//tempMap.put(String.valueOf(0), userOrgs.get(0).toString());
							//Commented code for shoing orgainization in useradmin
						//	obj[1]=userOrgs.get(0).toString();
							
						}

						UserpatternUsersImpl uupimpl = new UserpatternUsersImpl();
						List userUserpat = uupimpl.getUserPatternsFromUserId(userId);
					

					    if (userUserpat.size() > 0)
						{
							//tempMap.put(String.valueOf(1), userUserpat.get(0).toString());
					    	obj[0]=userUserpat.get(0).toString();					    	
						}
						//returnMap.put(String.valueOf(0), tempMap);
						List resultList= new LinkedList();
						resultList.add(obj);
						// returnMap.put(String.valueOf(1), tempMap);
						QueryData querydata = new QueryData();
						querydata.setListData(resultList);
						data.setQueryData(queryResponseData);
						hicObjectData.getData().setQueryData(querydata);

					}
				}
				else
				{
					logger.log(0,"-Userid not foound for delete");
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return hicObjectData;

	}
	
	
	/**
	 * This methods returns the list of Rights based on the userpatternid
	 * @param hicInputData
	 * @return
	 */
	@EventSubscriber(topic = "GetRights")
	public IHICData getRightsBasedOnUserPatternId(Object hicInputData)
	{
		IHICData hicObjectData = (IHICData) hicInputData;
		QueryData queryResponseData =hicObjectData.getData().getQueryData();
		try
		{
			if (hicObjectData != null)
			{
				IData data = hicObjectData.getData();
				String queryStr = data.getQueryData().getCondition();
				String userPatternId = "";
				List<IRights> rights=null;
				if (queryStr.indexOf("=") > 0)
				{
					String[] queryParams = queryStr.split("=");
					
					if (queryParams.length == 2)
					{
						userPatternId = queryParams[1];
						UserpatternRolesImpl userPatternImpl= new UserpatternRolesImpl();
						com.oxymedical.core.userdata.IRoleRights roleRights=userPatternImpl.getUserPatternRolesRights(userPatternId);
						if(roleRights!=null)
						{
					     rights=roleRights.getRights();	
					    
						}
						if(rights!=null)
						{   						
							List results=new ArrayList();
							for(int i=0;i<rights.size();i++)
							{
								Object[] objects=new Object[2];
								IRights right=rights.get(i);
								objects[0]=right.getRightName();
								objects[1]=right.getRightName();
								results.add(objects);
							}						
							queryResponseData.setListData(results);
							hicObjectData.getData().setQueryData(queryResponseData);
						}
						
					}
				
				else
				{
					logger.log(0,"-Rights  not foound for given UserPatternid");
				}
				
			}
		}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
		return hicObjectData;
	}
	/**
	 * 
	 * This method returns the list of fields based on the userpatternid.
	 * Method added  by pra on 11 june 2009
	 * @param hicInputData
	 * @return
	 */
	@EventSubscriber(topic = "GetFields")
	public IHICData getFieldsBasedOnUserPatternId(Object hicInputData)
	{
		IHICData hicObjectData = (IHICData) hicInputData;
		QueryData queryResponseData =hicObjectData.getData().getQueryData();
		try
		{
			if (hicObjectData != null)
			{
				IData data = hicObjectData.getData();
				String queryStr = data.getQueryData().getCondition();
				String userPatternId = "";
				if (queryStr.indexOf("=") > 0)
				{
					String[] queryParams = queryStr.split("=");
					
					if (queryParams.length == 2)
					{
						userPatternId = queryParams[1];
						UserFieldsImpl fieldsImpl= new UserFieldsImpl();
						List<Object> resultList=fieldsImpl.getUserDefinedFieldsFromUserPattern(userPatternId);
						if(resultList!=null)
						{
						
							queryResponseData.setListData(resultList);
							hicObjectData.getData().setQueryData(queryResponseData);
						}
						
					}
				}
				else
				{
					logger.log(0,"Fields not foound for userPatternId");
				}
				
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
		return hicObjectData;
	}
	
	/**
	 * 
	 * This method returns the list of based based on the query.
	 * Method added  by pra on 21 june 2009
	 * @param hicInputData
	 * @return
	 */
	@EventSubscriber(topic = "GetListUserAdmin")
	public IHICData getListUserAdmin(IHICData hicInputData)
	{
		/*IHICData hicObjectData = (IHICData) hicInputData;
		QueryData queryResponseData =hicObjectData.getData().getQueryData();
		try
		{
			if (hicObjectData != null)
			{
				IData data = hicObjectData.getData();
				String queryStr = data.getQueryData().getCondition();
				DBComponent dbComponent=ConnectionDatabase.GetInstanceOfDatabaseComponent();
				Properties userAdminProperty=PropertyUtil.getPropertiesObject(this.openTempFile(Constants.UA_PROPERITIES_FILE));
			    UserAdminQueryHelper userQuery = new UserAdminQueryHelper(userAdminProperty);
				List resultList= userQuery.getUserAdminQueryResult(queryStr, dbComponent);
			    if(resultList!=null)
				{
			    queryResponseData.setListData(resultList);
				hicObjectData.getData().setQueryData(queryResponseData);
				}
						
					
			}
				else
				{
					logger.log(0,"Result not found");
				}
				
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return hicObjectData;*/
		
		try{
			DBComponent dbComponent = ConnectionDatabase.GetInstanceOfDatabaseComponent();
			String conditionQuery = (String)hicInputData.getData().getFormPattern().getFormValues().get("conditionQuery");
			QueryData queryData = new QueryData();
			hicInputData.getData().setQueryData(queryData);
			
			List fetchValue = dbComponent.getDataHandler().executeQueryList(conditionQuery);
			if (fetchValue != null) {
				queryData.setListData(fetchValue);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception occured in GetListUserAdmin, message = "+e.getMessage());
		}
		return hicInputData;
	}
	
	/**
	 * 
	 * This method execute the query.
	 * Method added  by pra on 21 june 2009
	 * @param hicInputData
	 * @return
	 */
	@EventSubscriber(topic = "updateUserAdminRecord")
	public IHICData updateUserAdminRecord(IHICData hicInputData)
	{
		try{
			DBComponent dbComponent = ConnectionDatabase.GetInstanceOfDatabaseComponent();
			String conditionQuery = (String)hicInputData.getData().getFormPattern().getFormValues().get("conditionQuery");
			List fetchValue = dbComponent.getDataHandler().executeQueryList(conditionQuery);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception occured in updateUserAdminRecord, message = "+e.getMessage());
		}
		return hicInputData;
		
	}
	
	public String  openTempFile(String filename) 
	{
		File template=null;
		
		try {
		    template = File.createTempFile("useradminDb", ".properties");
			FileOutputStream out = new FileOutputStream(template);
			String fileContents="";
			FileIO fileWriter = new FileIO();
			fileContents = fileContents + fileWriter.readFromFile(filename);
			try {
				out.write(fileContents.getBytes());
			} finally {
				out.close();
			}
			template.deleteOnExit();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return template.getPath();
		
	}
	
	public void addSignature(Hashtable formValues) throws DBComponentException
	{
		if (formValues != null)
		{
			// FETCH ALL DATA FROM formValues AND CALL FUNCTION addUserSignature OF UserImpl class
			int id = Integer.parseInt(formValues.get(Constants.SIGN_ID).toString());
			String userId = formValues.get(Constants.SIGN_USERID).toString();
			String imageType = formValues.get(Constants.SIGN_IMAGETYPE).toString();
			String imageData = formValues.get(Constants.SIGN_IMAGEDATA).toString();
			int imageTagNo = Integer.parseInt(formValues.get(Constants.SIGN_IMAGETAGNO).toString());
			String imageUnid = formValues.get(Constants.SIGN_IMAGEUNID).toString();
			UserImpl uimpl = new UserImpl();
			uimpl.addUserSignature(id, userId, imageType, imageData, imageTagNo, imageUnid);
			
		}
	}
	
	
}
