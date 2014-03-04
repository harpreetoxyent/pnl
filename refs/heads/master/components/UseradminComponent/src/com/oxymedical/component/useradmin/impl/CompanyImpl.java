package com.oxymedical.component.useradmin.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.useradmin.ICompany;
import com.oxymedical.component.useradmin.UserAdminComponent;
import com.oxymedical.component.useradmin.constants.Constants;
import com.oxymedical.component.useradmin.constants.SQLCommands;
import com.oxymedical.component.useradmin.exception.NoSuchCompanyException;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.impl.ldap.LdapImpl;
import com.oxymedical.component.useradmin.model.Company;
import com.oxymedical.component.useradmin.model.User;
import com.oxymedical.component.useradmin.operations.ConnectionDatabase;

public class CompanyImpl implements ICompany
{
	private LdapImpl ldapImpl = null;
	public List getCompanies() throws NoSuchCompanyException
	{
		Company company = null;
		String sqlQuery = SQLCommands.List_Company;
		List list = null;
		try 
		{
			list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().getList(sqlQuery);
			if ((list == null) || (list.size()==0))
			{	
				throw(new NoSuchCompanyException("No companies exist"));
			}	
			else 
			{
				for(Iterator companyRow=list.iterator();companyRow.hasNext();)
				{
					Object companyRowData= companyRow.next();
					company = (Company)companyRowData;
				//	UserAdminComponent.logger.log(0,company.getCompanyId());
				//	UserAdminComponent.logger.log(0,company.getHomeUrl());
					
				}				
			}
		} 
		catch (DBComponentException e) 
		{
			e.printStackTrace();
		}
		 
		
		
		return list;
	}

	public Company getCompany(String companyId) throws NoSuchCompanyException ,DBComponentException
	{
		Company company = null;
		String sqlQuery = SQLCommands.Select_Company;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter Id = new NameQueryParameter(Constants.companyId, companyId);
		listParam.add(Id);
		List list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,listParam);			
		if ((list == null) || (list.size()== 0))
		{
			throw (new NoSuchCompanyException(companyId + " does not exists"));
		}	
		else 
		{
			for(Iterator companyRow=list.iterator();companyRow.hasNext();)
			{
				Object companyRowData= companyRow.next();
				company = (Company)companyRowData;
				
			}		
		}
	
		
		
		return company;
	}

	public Company addCompany(String companyId,  String homeURL) throws DBComponentException , UAComponentException
	{
		UserAdminComponent.logger.log(0,"--------------For Company Company Impl------------------ " );
		Company company = null;
		boolean newCompany = false;
		Hashtable<String,String> ldapCompanyInfo = null;
		
		try
		{
			company = getCompany(companyId);
			
		}
		catch(NoSuchCompanyException ex)
		{
			newCompany = true;
			company = new Company();
			company.setCompanyId(companyId);
			company.setHomeUrl(homeURL);
			UserAdminComponent.logger.log(0,"-------------- Company-before save----------------- " );
			ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(company);
			UserAdminComponent.logger.log(0,"-------------- Company-after save----------------- " );

		}
//		if(newCompany)
//		{
//			ldapImpl = new LdapImpl();
//			ldapCompanyInfo = new Hashtable<String,String>();
//			ldapCompanyInfo.put("type", "company");
//			ldapCompanyInfo.put("name", companyId);
//			ldapCompanyInfo.put("parentname", "");
//			ldapImpl.addLDAPEntry(ldapCompanyInfo);
//			/*try 
//			{
//				ldapComp.addEntry(ldapCompanyInfo);
//			} catch (ComponentException e)
//			{
//				// TODO Auto-generated catch block
//				throw new UAComponentException(e);
//			}*/
//			
//		}
		return company;
	}
	
	public Company updateCompany(String companyId,  String homeURL) throws DBComponentException
	{
		Company company = null;
		company = new Company();
		company.setCompanyId(companyId);
		company.setHomeUrl(homeURL);
		ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(company);
	
		return company;
	}

	public void updateDisplay(String companyId, String languageId) throws NoSuchCompanyException,DBComponentException
	{
		User user = null;
		String sqlQuery = SQLCommands.Select_User;			
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter Id = new NameQueryParameter(Constants.companyId, companyId);
		listParam.add(Id);
		List list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,listParam);
		if(list != null)
		{
			
			if (list.size() > 0 )
			{	
				
				for(Iterator userRow=list.iterator();userRow.hasNext();)
				{
					Object userRowData= userRow.next();
					user = (User)userRowData;
					
				}	
				user.setLanguageId(languageId);
				ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(user);
			}
			else 
			{
				throw (new NoSuchCompanyException("No Company with this id Found"));
			}
		
		}
		
	}
	public  Boolean validateCompany(String companyId)throws  NoSuchCompanyException,DBComponentException
	{
		boolean resultCompany = false;
	
		String sqlQuery = SQLCommands.Validate_Company;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter Id = new NameQueryParameter(Constants.companyId, companyId);
		listParam.add(Id);
		List list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,listParam);			
		if (list != null)
		{
			if (list.size() > 0 )
			{	
//				UserAdminComponent.logger.log(0,"Company Exists");
				resultCompany =  true;
			}
			else 
			{
//				UserAdminComponent.logger.log(0,"Company does not Exists");
				resultCompany =  false;		
				throw (new NoSuchCompanyException("No Company with this id Found"));
			}
			
		}	
	return resultCompany;
	}
	
	
	/*public static void main(String args[])
	{
		Company company = new Company(); 
		ICompany impl = new CompanyImpl();
		try {
			impl.addCompany("Oxyent.com", "http://oxyent.com");
		} catch (NoSuchCompanyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (DBComponentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
}
