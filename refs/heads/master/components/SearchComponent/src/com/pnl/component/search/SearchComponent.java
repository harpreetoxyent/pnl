package com.pnl.component.search;

import java.util.Hashtable;

import org.dom4j.Document;

import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.baseComponent.annotations.EventSubscriber;
import com.oxymedical.component.baseComponent.exception.ComponentException;
import com.oxymedical.core.commonData.Data;
import com.oxymedical.core.commonData.FormPattern;
import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IFormPattern;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.maintenanceData.IMaintenanceData;
import com.oxymedical.hic.application.NOLISRuntime;
import com.oxymedical.hic.application.eventmanagement.PublicationScope;

public class SearchComponent implements ISearchComponent, IComponent
{

	
	
	@Override
	public void start(Hashtable<String, Document> configData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() throws ComponentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() throws ComponentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() throws ComponentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IHICData getHicData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHicData(IHICData hicData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void maintenance(IMaintenanceData maintenanceData) {
		// TODO Auto-generated method stub
		
	}

	@EventSubscriber(topic = "executeSearch")	
	public IHICData executeSearch(IHICData searchData)
	{
		System.out.println("Executing Search");
		try
		{
//			IData data = new Data();
//			IFormPattern formPattern = new FormPattern();
//			searchData.setData(data);
//			searchData.getData().setFormPattern(formPattern);
//			searchData.getData().getFormPattern().setFormValues(new Hashtable<String,Object>());
//
//			System.out.println("************EXECUTING SEARCH ANNSWER*******");
//			System.out.println("************ SEARCH ANNSWER*******" + searchData.getData());
//			searchData.getData().getFormPattern().setFormId("search");
			//searchData.getData().getDataPattern().setDataPatternId("");			
			searchData = NOLISRuntime.FireEvent("executeNLP", new Object[]{searchData}, PublicationScope.Global);				
			searchData= NOLISRuntime.FireEvent("checkRuleForSocialData", new Object[]{searchData}, PublicationScope.Global);
			
		//	searchData = NOLISRuntime.FireEvent("processQuery", new Object[]{searchDataObject}, PublicationScope.Global);
			
			
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return searchData;
	}	
	

}
