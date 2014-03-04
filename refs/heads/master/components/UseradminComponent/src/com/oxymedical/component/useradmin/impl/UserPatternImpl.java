package com.oxymedical.component.useradmin.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.useradmin.IUserPattern;
import com.oxymedical.component.useradmin.UserAdminComponent;
import com.oxymedical.component.useradmin.constants.Constants;
import com.oxymedical.component.useradmin.constants.SQLCommands;
import com.oxymedical.component.useradmin.exception.NoSuchCompanyException;
import com.oxymedical.component.useradmin.exception.NoSuchRoleException;
import com.oxymedical.component.useradmin.exception.NoSuchUserException;
import com.oxymedical.component.useradmin.exception.NoSuchUserPatternException;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.model.Right;
import com.oxymedical.component.useradmin.model.Role;
import com.oxymedical.component.useradmin.model.Userpattern;
import com.oxymedical.component.useradmin.operations.ConnectionDatabase;
import com.oxymedical.component.useradmin.util.CounterUtil;

public class UserPatternImpl implements IUserPattern
{

	public Userpattern addUserpattern(String companyId, String userPatternId, String comments,
			String defaultFormPattern) throws DBComponentException, NoSuchCompanyException
	{
		boolean isCompany = false;
		UserAdminComponent.logger.log(0,"------Presentin Add UserPattern-companyId--" + companyId);
		UserAdminComponent.logger.log(0,"------Presentin Add UserPattern-userPatternId--" + userPatternId);
		UserAdminComponent.logger.log(0,"------Presentin Add UserPattern-comments--" + comments);
		UserAdminComponent.logger.log(0,"------Presentin Add UserPattern-defaultFormPat--" + defaultFormPattern);
		Userpattern userpattern = null;
		CompanyImpl companyImpl = new CompanyImpl();
		isCompany = companyImpl.validateCompany(companyId);
		UserAdminComponent.logger.log(0,"------Presentin Add UserPattern-isCompany--" + isCompany);
		try
		{
			userpattern = getUserPatternbyCompanyAndId(companyId, userPatternId);
			UserAdminComponent.logger.log(0,"----userPattern is present in try------"
					+ userpattern.getUserPatternId());
			userpattern.setComments(comments);
			userpattern.setDefaultFormPattern(defaultFormPattern);
			userpattern.setCompanyId(companyId);
			ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(userpattern);
			if(userpattern!=null)
			{
				UserAdminComponent.logger.log(0,"-------UserPattern is already present just update it-------");
			}

		}
		catch (NoSuchUserPatternException ex)
		{
			UserAdminComponent.logger.log(0,"----NoSuchUserPatternException-----");
			if (isCompany)
			{
				userpattern = new Userpattern();
				userpattern.setUserPatternId(userPatternId);
				userpattern.setCompanyId(companyId);
				userpattern.setComments(comments);
				userpattern.setDefaultFormPattern(defaultFormPattern);
				ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(userpattern);
			}
		}
		UserAdminComponent.logger.log(0," Chekc return userpattern -------" + userpattern.getUserPatternId());
		return userpattern;
	}

	public Userpattern getUserPatternbyCompanyAndId(String companyId, String userpatternId)
			throws NoSuchUserPatternException, DBComponentException
	{
		UserAdminComponent.logger.log(0,"------Presentin Add getUserPatternbyCompanyAndId--");
		Userpattern userpattern = null;
		String sqlQuery = SQLCommands.Find_UserPattern_From_Company_Name;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter compId = new NameQueryParameter(Constants.companyId, companyId);
		listParam.add(compId);
		NameQueryParameter orgName = new NameQueryParameter(Constants.userPatternId, userpatternId);
		listParam.add(orgName);
		List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
				.executeHSQLQueryWithNameParameter(sqlQuery, listParam);
		UserAdminComponent.logger.log(0," Chekc Listsize -------" + list.size());
		if (list == null || (list.size() == 0))
		{
			throw (new NoSuchUserPatternException("No UserPattern with this companyID " + companyId
					+ " and " + userpatternId + " found"));
		}
		else
		{
			for (Iterator upRow = list.iterator(); upRow.hasNext();)
			{
				Object upRowData = upRow.next();
				userpattern = (Userpattern) upRowData;
			}
		}
		UserAdminComponent.logger.log(0," Chekc return userpattern -------" + userpattern.getUserPatternId());
		return userpattern;

	}

	public boolean validateUserPattern(String userPatternId) throws NoSuchUserPatternException,
			UAComponentException
	{
		boolean resultUserPattern = false;
		try
		{
			String sqlQuery = SQLCommands.Find_UserPattern;
			ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
			NameQueryParameter Id = new NameQueryParameter(Constants.userPatternId, userPatternId);
			listParam.add(Id);
			List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeHSQLQueryWithNameParameter(sqlQuery, listParam);
			if (list != null)
			{
				if (list.size() > 0)
				{
					UserAdminComponent.logger.log(0,"USERPATTERN Exists");
					resultUserPattern = true;
				}
				else
				{
					UserAdminComponent.logger.log(0,"USERPATTERN does not Exists");
					resultUserPattern = false;
					throw (new NoSuchUserPatternException("No userpattern with this id Found"));
				}

			}

		}
		catch (DBComponentException e)
		{
			throw (new UAComponentException(e.getMessage()));
		}
		return resultUserPattern;
	}

	public String getDefaultFormPatFromUserPatternsId(String userPatternId) throws DBComponentException,
			UAComponentException
	{
		UserAdminComponent.logger.log(0,"----In UserpatternImpl -getDefaultFormPatFromUserPatternsId---" + userPatternId);
		String defaultFormPat = null;
		try
		{
			String retrieveUPQuery = SQLCommands.Find_defFormPat_ForUserPatId_From_UserPat;
			UserAdminComponent.logger.log(0,"----In UserpatternImpl getDefaultFormPatFromUserPatternsId--retrieveUPQuery---"
							+ retrieveUPQuery);
			ArrayList<NameQueryParameter> userListParam = new ArrayList<NameQueryParameter>();
			NameQueryParameter userpatternid = new NameQueryParameter(Constants.userPatternId, userPatternId);
			userListParam.add(userpatternid);
			List formpatlist = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeHSQLQueryWithNameParameter(retrieveUPQuery, userListParam);

			UserAdminComponent.logger.log(0,"---present in userpatternImpl check userpattern lis size--------"
					+ formpatlist.size());
			defaultFormPat = formpatlist.get(0).toString();
			UserAdminComponent.logger.log(0,"-defaultform pat is-----"+defaultFormPat);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return defaultFormPat;
	}
}
