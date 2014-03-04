package com.oxymedical.component.useradmin.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.useradmin.ICountry;
import com.oxymedical.component.useradmin.constants.Constants;
import com.oxymedical.component.useradmin.constants.SQLCommands;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.model.Country;
import com.oxymedical.component.useradmin.operations.ConnectionDatabase;

public class CountryImpl implements ICountry
{

	public List getCountries() throws DBComponentException , UAComponentException
	{
		String sqlQuery = SQLCommands.List_Country;
		List list = null;
		list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().getList(sqlQuery);
		if ((list == null) || (list.size()==0))
		{	
			throw(new UAComponentException("No countries exist"));
		}	
		return list;
	}

	public List getCountries(boolean active) 
	{
		
		return null;
	}

	public Country getCountry(String countryId) throws UAComponentException , DBComponentException
	{
		
		Country country = null;
		String sqlQuery = SQLCommands.Select_Country;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter Id = new NameQueryParameter(Constants.countryId, countryId);
		listParam.add(Id);
		List list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,listParam);			
		if (list == null)
		{
			throw (new UAComponentException("No Country with this id Found"));
		}	
		else 
		{
			for(Iterator countryRow=list.iterator();countryRow.hasNext();)
			{
				Object countryRowData= countryRow.next();
				country = (Country)countryRowData;
				
			}		
		}
		return country;
	}
	
	public Country getCountryFromName(String name) throws UAComponentException , DBComponentException
	{
		
		Country country = null;
		String sqlQuery = SQLCommands.Select_Country_From_Name;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter Id = new NameQueryParameter(Constants.countryName, name);
		listParam.add(Id);
		List list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,listParam);			
		if (list == null)
		{
			throw (new UAComponentException("No Country with this name Found"));
		}	
		else 
		{
			for(Iterator countryRow=list.iterator();countryRow.hasNext();)
			{
				Object countryRowData= countryRow.next();
				country = (Country)countryRowData;
				
			}		
		}
		return country;
	}

}
