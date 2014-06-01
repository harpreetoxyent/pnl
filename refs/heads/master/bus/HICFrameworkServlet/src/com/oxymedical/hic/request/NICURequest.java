package com.oxymedical.hic.request;

import com.oxymedical.hic.application.NOLISRuntime;
import com.oxymedical.hic.application.eventmanagement.PublicationScope;

public class NICURequest implements Runnable
{


	@Override
	public void run()
	{
		try
		{
			NOLISRuntime.FireEvent("StartNursingProgressThread", new Object[]{null}, PublicationScope.Global);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}

