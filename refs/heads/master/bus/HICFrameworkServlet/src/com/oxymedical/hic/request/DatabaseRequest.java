package com.oxymedical.hic.request;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.hic.application.NOLISRuntime;
import com.oxymedical.hic.application.eventmanagement.PublicationScope;

public class DatabaseRequest implements Runnable
{

//	IDBComponent dbComponent;
//
//	public DatabaseRequest(IDBComponent dbComponent)
//	{
//		this.dbComponent = dbComponent;
//	}
//
//	public void setDbComponent(IDBComponent dbComponent)
//	{
//		this.dbComponent = dbComponent;
//	}

	@Override
	public void run()
	{
		try
		{
			NOLISRuntime.FireEvent("createDBResourcesAndMappings", new Object[]{null}, PublicationScope.Global);
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
