package com.oxymedical.component.useradmin.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.useradmin.IUserPatternUsers;
import com.oxymedical.component.useradmin.UserAdminComponent;
import com.oxymedical.component.useradmin.constants.Constants;
import com.oxymedical.component.useradmin.constants.SQLCommands;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.model.User;
import com.oxymedical.component.useradmin.model.UserpatternUser;
import com.oxymedical.component.useradmin.model.UserpatternUserPK;
import com.oxymedical.component.useradmin.operations.ConnectionDatabase;

public class UserpatternUsersImpl implements IUserPatternUsers
{
	public void addUserpatternUsers(String userPatternId, String companyId,
			ArrayList<User> users) throws DBComponentException, UAComponentException
	{
		UserAdminComponent.logger.log(0,"-----Presen tin Add Role Rights impl--role id--" + userPatternId);
		UserAdminComponent.logger.log(0,"-----Presen tin Add Role Rights impl--company id--" + companyId);
		UserAdminComponent.logger.log(0,"-----Presen tin Add Role Rights impl--ArrayList id--"
				+ users.size());
		boolean isValidUserPattern = false;
		boolean isValidUser = false;

		User user = null;
		UserPatternImpl userpatternImpl = new UserPatternImpl();
		UserImpl userImpl = new UserImpl();
		UserpatternUser userpatternusers = new UserpatternUser();
		UserpatternUserPK userpatternuserPK = new UserpatternUserPK();

		isValidUserPattern = userpatternImpl.validateUserPattern(userPatternId);
		UserAdminComponent.logger.log(0,"-------Is isValidUserPattern Valid--" + isValidUserPattern);
		for (Iterator usersRow = users.iterator(); usersRow.hasNext();)
		{
			Object usersRowData = usersRow.next();
			user = (User) usersRowData;
			UserAdminComponent.logger.log(0,"---Now in For Loop to check users-------" + user.getUserId());
			isValidUser = userImpl.validateUser(user.getUserId());
			UserAdminComponent.logger.log(0,"-------Is isValidUser Valid--" + isValidUser);
			if ((isValidUserPattern) && (isValidUser))
			{
				userpatternuserPK.setUserId(user.getUserId());
				userpatternuserPK.setUserPatternId(userPatternId);
				userpatternusers.setComp_id(userpatternuserPK);
				userpatternusers.setCompanyId(companyId);
				UserAdminComponent.logger.log(0,"-Call save userpattern users --");
				ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(
						userpatternusers);
			}

		}
	}

	public List<String> getUserPatternsFromUserId(String userId) throws 
			UAComponentException
	{
		
		List<String> userPatternslist = null;
		try
		{
			
			String retrieveUPQuery = SQLCommands.Find_UserPat_ForUserId_From_UserPatUsers;
			UserAdminComponent.logger.log(0,"In UserpatternImpl getUserPatternsFromUserId--retrieveUPQuery"
							+ retrieveUPQuery);
			ArrayList<NameQueryParameter> userListParam = new ArrayList<NameQueryParameter>();
			NameQueryParameter userid = new NameQueryParameter(Constants.userId, userId);
			userListParam.add(userid);
			userPatternslist = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeHSQLQueryWithNameParameter(retrieveUPQuery, userListParam);
			}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return userPatternslist;
	}
	
	public List<String> getUserIdFromUserPatterns(String userPatternId) throws UAComponentException
	{
		List<String> userIdlist = null;
		try
		{
			
			String retrieveUPQuery = SQLCommands.Find_UserId_From_UserPatUsers;
			UserAdminComponent.logger.log(0,"In UserpatternImpl getUserIdFromUserPatterns--retrieveUPQuery"+ retrieveUPQuery);
			ArrayList<NameQueryParameter> userPatternListParam = new ArrayList<NameQueryParameter>();
			NameQueryParameter userPatternIdQueryParam = new NameQueryParameter(Constants.userPatternId, userPatternId);
			userPatternListParam.add(userPatternIdQueryParam);
			userIdlist = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(retrieveUPQuery, userPatternListParam);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return userIdlist;
	}
	
	public void deleteUserPatternForUser(String userPatternId, String userId)
			throws UAComponentException
	{
		UserAdminComponent.logger.log(0,"--Present in--deleteUserPatternForUser check userPatternId-"
				+ userPatternId);
		UserAdminComponent.logger.log(0,"-Present in--deleteUserPatternForUser check -userId-"
				+ userId);
		String sqlQuery = null;
		/*ArrayList<NameQueryParameter> listParam = null;
		NameQueryParameter Id = null;*/
		Integer result = null;
		try
		{
			sqlQuery = SQLCommands.Clear_User_UserPattern_basis_UserPat;
			StringBuilder sb = new StringBuilder(sqlQuery);
			sb.append("'").append(userPatternId.trim()).append("'").append(
					SQLCommands.Append_UserPattern_basis_User).append("'").append(
					userId.trim()).append("'");
			sqlQuery = sb.toString();
			UserAdminComponent.logger.log(0,"--Final Query in deleteUserPatternForUser- "+sqlQuery);
			/*listParam = new ArrayList<NameQueryParameter>();
			Id = new NameQueryParameter(Constants.userPatternId, userPatternId);
			listParam.add(Id);*/
			System.out.println("--deleteUserPatternForUser--"+sqlQuery);
			result = ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeDMLQuery(sqlQuery);
			UserAdminComponent.logger.log(0,"-Check result afet deletiong orgs from User_orgs-"
					+ result);
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
			throw new UAComponentException(e);
		}

	}
}
