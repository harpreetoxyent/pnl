package com.oxymedical.hic.request;

import com.oxymedical.hic.application.NOLISRuntime;
import com.oxymedical.hic.application.eventmanagement.PublicationScope;

public class DatabaseRequest implements Runnable
{


	@Override
	public void run()
	{
		try
		{
			NOLISRuntime.FireEvent("createDBResourcesAndMappings", new Object[]{null}, PublicationScope.Global);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
