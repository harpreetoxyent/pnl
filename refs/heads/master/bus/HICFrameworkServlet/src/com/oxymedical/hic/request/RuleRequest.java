package com.oxymedical.hic.request;

import com.oxymedical.core.commonData.IApplication;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.hic.application.NOLISRuntime;
import com.oxymedical.hic.application.eventmanagement.PublicationScope;

public class RuleRequest implements Runnable {

	private IHICData hicData;
	private IApplication application;
	
	public IHICData getHicData() {
		return hicData;
	}
	public void setHicData(IHICData hicData) {
		this.hicData = hicData;
	}
	public IApplication getApplication() {
		return application;
	}
	public void setApplication(IApplication application) {
		this.application = application;
	}
	public RuleRequest(IHICData hicData)
	{
		this.hicData= hicData;
	}
	public void intializeRules()
	{
		this.hicData.setApplication(this.application);
		System.out.println("----Inside intializeRules----");
		try
		{
			NOLISRuntime.FireEvent("buildReteRules", new Object[]{null}, PublicationScope.Global);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() 
	{
		try
		{
			System.out.println("----Inside Rules run method-----");
			intializeRules();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
