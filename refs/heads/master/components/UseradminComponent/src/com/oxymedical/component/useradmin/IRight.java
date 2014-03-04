package com.oxymedical.component.useradmin;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.useradmin.exception.NoSuchCompanyException;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.model.Right;



public interface IRight 
{
	public void addRight( String companyId , String name,String defaultUrl )throws DBComponentException,NoSuchCompanyException;
	public void addRight( String companyId , String name,String languageProperties,String defaultUrl )throws DBComponentException,NoSuchCompanyException;
	public Right getRightFromCompanyAndName(String name , String companyId)throws DBComponentException,UAComponentException;
 
}
