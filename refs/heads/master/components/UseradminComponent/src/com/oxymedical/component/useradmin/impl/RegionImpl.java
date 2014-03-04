package com.oxymedical.component.useradmin.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.useradmin.IRegion;
import com.oxymedical.component.useradmin.constants.Constants;
import com.oxymedical.component.useradmin.constants.SQLCommands;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.model.Region;
import com.oxymedical.component.useradmin.operations.ConnectionDatabase;

public class RegionImpl implements IRegion
{

	public Region getRegion(String regionId)throws UAComponentException , DBComponentException
	{
		
		Region region = null;
		String sqlQuery = SQLCommands.Select_Region;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter Id = new NameQueryParameter(Constants.regionId, regionId);
		listParam.add(Id);
		List list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,listParam);			
		if (list == null)
		{
			throw (new UAComponentException("No Region with this id Found"));
		}	
		else 
		{
			for(Iterator regionRow=list.iterator();regionRow.hasNext();)
			{
				Object regionRowData= regionRow.next();
				region = (Region)regionRowData;
				
			}		
		}
		return region;
	}

	public List getRegions() throws DBComponentException ,UAComponentException
	{
		String sqlQuery = SQLCommands.List_Region;
		List list = null;
		list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().getList(sqlQuery);
		if ((list == null) || (list.size()==0))
		{	
			throw(new UAComponentException("No regions exist"));
		}	
		return list;
	}

	public List getRegions(String countryId) throws DBComponentException ,UAComponentException
	{
		String sqlQuery = SQLCommands.Select_Region_From_Country;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter Id = new NameQueryParameter(Constants.countryId, countryId);
		listParam.add(Id);
		List list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,listParam);			
		if (list == null && list.size()==0)
		{
			throw (new UAComponentException("No Regions with this country id Found"));
		}	
		return list;
	}
	public Region getRegionFromName(String name) throws UAComponentException , DBComponentException
	{
		
		Region region = null;
		String sqlQuery = SQLCommands.Select_Region_From_Name;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter Id = new NameQueryParameter(Constants.regionName, name);
		listParam.add(Id);
		List list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,listParam);			
		if ((list == null)&& (list.size() == 0))
		{
			throw (new UAComponentException("No Region with this name Found"));
		}	
		else 
		{
			for(Iterator regionRow=list.iterator();regionRow.hasNext();)
			{
				Object regionRowData= regionRow.next();
				region = (Region)regionRowData;
				
			}		
		}
		return region;
	}
	public List getRegions(boolean active) 
	{
		
		// TODO Auto-generated method stub
		return null;
	}

	public List getRegions(String countryId, boolean active) 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
