package com.oxymedical.component.useradmin.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.useradmin.IRight;
import com.oxymedical.component.useradmin.UserAdminComponent;
import com.oxymedical.component.useradmin.constants.Constants;
import com.oxymedical.component.useradmin.constants.SQLCommands;
import com.oxymedical.component.useradmin.exception.NoSuchCompanyException;
import com.oxymedical.component.useradmin.exception.NoSuchRightException;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.model.Right;
import com.oxymedical.component.useradmin.operations.ConnectionDatabase;
import com.oxymedical.component.useradmin.util.CounterUtil;

public class RightImpl implements IRight 
{
	public Right getRightFromCompanyAndName(String name , String companyId) throws DBComponentException,UAComponentException
	 {
		 	Right right = null;
		 	String retrieveRightQuery = SQLCommands.Find_Right_From_Company_Name;
			ArrayList<NameQueryParameter> rightListParam = new ArrayList<NameQueryParameter>();
			NameQueryParameter company = new NameQueryParameter(Constants.companyId, companyId);
			rightListParam.add(company);
			NameQueryParameter rights = new NameQueryParameter(Constants.name, name);
			rightListParam.add(rights);
			List rightlist = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(retrieveRightQuery,rightListParam);			
			if ((rightlist == null) || (rightlist.size()== 0))
			{
				throw (new UAComponentException("right with " + name + "and" + companyId + " does not exists"));
			}	
			else 
			{
				
				for(Iterator rightRow=rightlist.iterator();rightRow.hasNext();)
				{
					Object rightRowData= rightRow.next();
					right = (Right)rightRowData;
					
					
				}		
			}
			return right;
	 }
	
	public void addRight(String companyId , String name,String defaultUrl )throws DBComponentException, NoSuchCompanyException
	{
		UserAdminComponent.logger.log(0,"--------present to add right in rightimpl---------"+companyId+name);
		boolean isCompany = false;
		Right right = null;
		CompanyImpl companyImpl = new CompanyImpl();
		isCompany = companyImpl.validateCompany(companyId);
		try
		{
			right = getRightbyCompanyAndName(companyId,name);
			UserAdminComponent.logger.log(0,"--------present to add right in rightimpl----right-----"+right);
			
		}
		catch(NoSuchRightException ex)
		{
			if(isCompany)
			{
				right = new Right();
				CounterUtil counterUtil = new CounterUtil();
				String rightId = String.valueOf(counterUtil.incrementRightId(RightImpl.class.getName().toString()));
				right.setRightId(rightId);
				right.setCompanyId(companyId);
				right.setName(name);
				right.setDefaultUrl(defaultUrl);
				ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(right);
			}
		}
}
	
	public boolean validateRight(String rightId)throws DBComponentException , UAComponentException
	{
		boolean resultRight = false;
		String sqlQuery = SQLCommands.Find_Right;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter Id = new NameQueryParameter(Constants.rightId, rightId);
		listParam.add(Id);
		List list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,listParam);			
		if (list != null)
		{
			if (list.size() > 0 )
			{	
//				UserAdminComponent.logger.log(0,"Right Exists");
				resultRight =  true;
			}
			else 
			{
//				UserAdminComponent.logger.log(0,"Right does not Exists");
				resultRight =  false;		
				throw (new UAComponentException("No right with this name Found"));
			}
			
		}
		return resultRight;
			
	}
	
	public Right getRightbyCompanyAndName(String companyId , String name)throws NoSuchRightException,DBComponentException
	{
		Right right = null;
		String sqlQuery = SQLCommands.Find_Right_From_Company_Name;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter compId = new NameQueryParameter(Constants.companyId, companyId);
		listParam.add(compId);
		NameQueryParameter orgName = new NameQueryParameter(Constants.name, name);
		listParam.add(orgName);
		List list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,listParam);			
		if (list == null || (list.size()==0))
		{
			throw (new NoSuchRightException("No Right with this companyID " + companyId + " and " +name + " found"));
		}
		else
		{
			for(Iterator rightRow=list.iterator();rightRow.hasNext();)
			{
				Object rightRowData =rightRow.next();
				right = (Right)rightRowData;
			}
		}
			
		return right;
		
	}

	public void addRight(String companyId, String name, String languageProperties,String defaultUrl) throws DBComponentException, NoSuchCompanyException 
	{
		boolean isCompany = false;
		Right right = null;
		CompanyImpl companyImpl = new CompanyImpl();
		isCompany = companyImpl.validateCompany(companyId);
		if(isCompany)
		{
			right = new Right();
			CounterUtil counterUtil = new CounterUtil();
			String rightId = String.valueOf(counterUtil.incrementRightId(RightImpl.class.getName().toString()));
			right.setRightId(rightId);
			right.setCompanyId(companyId);
			right.setName(name);
			right.setLanguageproperties(languageProperties);
			right.setDefaultUrl(defaultUrl);
			ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(right);
		}	
		
	}
	/*public void getRightsBasedOnRoleID(String roleId)throws NoSuchRightException
	{
		
		Right right = null;
		String sqlQuery = SQLCommands.Find_Rights_Base_On_RoleId;
		sqlQuery="select distinct ceil(RoleRight) from Right as Right,RoleRight as RoleRight where RoleRight.comp_id.roleId ="+roleId+ " and  Right.rightId=RoleRight.comp_id.rightId ";;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter rightId = new NameQueryParameter("roleId",roleId);
		listParam.add(rightId);
		
		List list=null;
		try {
			list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().getList(sqlQuery);
		} catch (DBComponentException e) {
			// TODO Auto-generated catch blo
			e.printStackTrace();
		}			
		if (list == null || (list.size()==0))
		{
			throw (new NoSuchRightException("No Right with this roleId " + roleId +" found"));
		}
		System.out.println("list---------"+list.size());
			
		//return right;
		
		
	}*/
	
}
