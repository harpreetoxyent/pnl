package com.oxymedical.component.useradmin.impl;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.useradmin.ICompany;
import com.oxymedical.component.useradmin.ICompanyCont;
import com.oxymedical.component.useradmin.constants.Constants;
import com.oxymedical.component.useradmin.constants.SQLCommands;
import com.oxymedical.component.useradmin.exception.NoSuchCompanyException;
import com.oxymedical.component.useradmin.exception.NoSuchContainerCompanyException;
import com.oxymedical.component.useradmin.exception.NoSuchContainerException;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.model.Company;
import com.oxymedical.component.useradmin.model.CompanyCont;
import com.oxymedical.component.useradmin.model.Container;
import com.oxymedical.component.useradmin.operations.ConnectionDatabase;


public class CompanyContImpl implements ICompanyCont
{
	
	
	public CompanyCont addCompanyContainer(Company company , Container container)throws DBComponentException
	{
		CompanyCont companyCont = new CompanyCont();
		companyCont.setCompany(company);
		companyCont.setContainer(container);
		ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(companyCont);
		return companyCont;
			
	}
	public static CompanyCont findContainerByCompany(String containerId , String companyId) throws NoSuchContainerException, DBComponentException, NoSuchCompanyException,NoSuchContainerCompanyException
	{
//		UserAdminComponent.logger.log(0,"companycontimpl CompanyCont findContainerByCompany");
		Container container = null;
		Company company = null;
		CompanyCont companyCont = null;
		ICompany companyImpl = new CompanyImpl();
		
		container = ContainerImpl.findByContainerId(containerId);
		company = companyImpl.getCompany(companyId);
		
		if((null != container) && (null != company))
		{
			String sqlQuery = SQLCommands.Select_Container_From_Company;
			ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
			NameQueryParameter compId = new NameQueryParameter(Constants.companyId, companyId);
			listParam.add(compId);
			NameQueryParameter contId = new NameQueryParameter(Constants.containerId, containerId);
			listParam.add(contId);
			List list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,listParam);			
			if ((list == null) || (list.size()== 0))
			{
				throw (new NoSuchContainerCompanyException("ContainerId "+ containerId + "with " + companyId + " does not exists"));
			}	
			else 
			{
//				UserAdminComponent.logger.log(0,"companycontimpl CompanyCont findContainerByCompany list not null");
				for(Iterator companyContRow=list.iterator();companyContRow.hasNext();)
				{
					Object companyContRowData= companyContRow.next();
					companyCont = (CompanyCont)companyContRowData;
					
				}		
			}
			
		}
		return companyCont;
		
	}
	public void deleteCompanyConatiner(String companyid , String conatinerId)
	{
		
	}
	public static CompanyCont getCompanyByContainer(String containerId) throws NoSuchContainerException, DBComponentException,UAComponentException
	{
		Container container = null;
		container = ContainerImpl.findByContainerId(containerId);
		
		CompanyCont companyCont = null;
		if(null != container)
		{
			String sqlQuery = SQLCommands.Select_Company_From_Container;
			ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
			NameQueryParameter contId = new NameQueryParameter(Constants.containerId, containerId);
			listParam.add(contId);
			List list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,listParam);			
			if ((list == null) || (list.size()== 0))
			{
				throw (new UAComponentException("No company with containerId" + containerId + " exists"));
			}	
			else 
			{
				for(Iterator companyContRow=list.iterator();companyContRow.hasNext();)
				{
					Object companyContRowData= companyContRow.next();
					companyCont = (CompanyCont)companyContRowData;
					
				}		
			}
		}
		return companyCont;
		
	}
}
