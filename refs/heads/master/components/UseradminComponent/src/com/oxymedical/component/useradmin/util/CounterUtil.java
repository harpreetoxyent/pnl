package com.oxymedical.component.useradmin.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.useradmin.constants.SQLCommands;
import com.oxymedical.component.useradmin.model.Counter;
import com.oxymedical.component.useradmin.operations.ConnectionDatabase;


public class CounterUtil 
{
	public int incrementGroupId(String name)
	{
		return increment(name, _MINIMUM_INCREMENT_SIZE);
	}
	
	
	public int incrementOrganizationId(String name)
	{
		return increment(name, _MINIMUM_INCREMENT_SIZE);
	}
	public int incrementUserId(String name)
	{
		return increment(name, _MINIMUM_INCREMENT_SIZE);
	}
	public int incrementRoleId(String name)
	{
		return increment(name, _MINIMUM_INCREMENT_SIZE);
	}
	public int incrementRightId(String name)
	{
		return increment(name, _MINIMUM_INCREMENT_SIZE);
	}

	
	private synchronized int increment(String name, int size)
	{
		Counter counter = null;
		int currentId = 0;
		try
		{
			counter = new Counter();
			counter.setName(name);
			String sqlQuery = SQLCommands.List_Counter;			
			ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
			NameQueryParameter Id = new NameQueryParameter("name", name);
			listParam.add(Id);
			List list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,listParam);
			if(null != list)
			{
				
				if (list.size() > 0 )
				{	
					
					for(Iterator counterRow=list.iterator();counterRow.hasNext();)
					{
						Object counterRowData= counterRow.next();
						
						if(null != counterRowData )
						{
							counter = (Counter)counterRowData;
							currentId = counter.getCurrentId() + size;
							counter.setCurrentId(currentId);
							ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(counter);
							break;
						}
					}	
				}
				else
				{
						counter = new Counter();
						counter.setName(name);
						counter.setCurrentId(_DEFAULT_CURRENT_ID);
						ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(counter);
					
				}
				
			}
		}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
		return currentId;
	}
	
	
	
	
	private static final int _DEFAULT_CURRENT_ID = 0;

	private static final int _MINIMUM_INCREMENT_SIZE = 1;
}
