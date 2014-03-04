package com.oxymedical.component.useradmin;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.useradmin.model.Company;
import com.oxymedical.component.useradmin.model.CompanyCont;
import com.oxymedical.component.useradmin.model.Container;

public interface ICompanyCont 
{
	
	public CompanyCont addCompanyContainer(Company company , Container container )throws DBComponentException;
	public void deleteCompanyConatiner(String comapnyId , String containerId);
	
}
