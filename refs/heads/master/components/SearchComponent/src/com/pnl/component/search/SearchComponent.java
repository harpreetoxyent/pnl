package com.pnl.component.search;

import java.util.Hashtable;

import org.dom4j.Document;

import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.baseComponent.annotations.EventSubscriber;
import com.oxymedical.component.baseComponent.exception.ComponentException;
import com.oxymedical.core.commonData.HICData;
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
	public IHICData executeSearch(IHICData searchDataObject)
	{
		System.out.println("Executing Search");
		try
		{
			IHICData nlpData = NOLISRuntime.FireEvent("executeNLP", new Object[]{searchDataObject}, PublicationScope.Global);
			nlpData.getData().getFormPattern().setFormId("nlpData");
			nlpData.getData().getDataPattern().setDataPatternId("");
			nlpData.getData().getFormPattern().getFormValues().put("nlpdata", nlpData);
			
			
			IHICData socialRuleData = NOLISRuntime.FireEvent("checkRuleForSocialData", new Object[]{searchDataObject}, PublicationScope.Global);
			socialRuleData.getData().getFormPattern().setFormId("socialRuleData");
			socialRuleData.getData().getDataPattern().setDataPatternId("");
			socialRuleData.getData().getFormPattern().getFormValues().put("socialRuleData", socialRuleData);
			
			IHICData solrData = NOLISRuntime.FireEvent("processQuery", new Object[]{searchDataObject}, PublicationScope.Global);
			socialRuleData.getData().getFormPattern().setFormId("solrData");
			socialRuleData.getData().getDataPattern().setDataPatternId("");
			socialRuleData.getData().getFormPattern().getFormValues().put("socialRuleData", solrData);
			
			
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}	
	

}
