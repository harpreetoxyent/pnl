package com.oxymedical.hic.request;

import com.oxymedical.hic.application.NOLISRuntime;
import com.oxymedical.hic.application.eventmanagement.PublicationScope;

public class RenderRequest implements Runnable
{
//	IRendererComponent renderComponent;
//
//	public RenderRequest(IRendererComponent rc)
//	{
//		this.renderComponent = rc;
//	}
	
	@Override
	public void run()
	{
		try
		{
//			renderComponent.renderApplication(null);
			NOLISRuntime.FireEvent("renderApplication", new Object[]{null}, PublicationScope.Global);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
