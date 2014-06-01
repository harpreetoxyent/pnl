package com.oxymedical.component.nicu;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Document;

import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.baseComponent.annotations.EventSubscriber;
import com.oxymedical.component.baseComponent.exception.ComponentException;
import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.nicu.request.UpdateRequest;
import com.oxymedical.component.render.resource_FORTIS.Nursingprogressreport;
import com.oxymedical.core.commonData.Application;
import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.IApplication;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.maintenanceData.IMaintenanceData;
import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.framework.objectbroker.annotations.InjectNew;

public class NICUComponent implements INICUComponent, IComponent
{
	@InjectNew
	public DBComponent dbComponent;

	UpdateRequest updateRequest = new UpdateRequest();
	@Override
	@EventSubscriber(topic = "StartNursingProgressThread")
	public void startUpdateRequest(IHICData hicData) 
	{
		updateRequest.setParentComponent(this);
		updateRequest.start();
	}
	
	public  void saveNursingProgressRowInDatabase(Object obj)
	{
		try
		{			
			dbComponent.saveObject(obj);
		} catch (DBComponentException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
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

	@Override
	public void createNursingNoteRecord(Nursingprogressreport nursingprogressreport) 
	{
		
		
		nursingprogressreport.setAaHr(0.0);
		nursingprogressreport.setAspirateVomit(0.0);
		nursingprogressreport.setBirthWt(0.0);
		nursingprogressreport.setBo("");
		nursingprogressreport.setBp("");
		nursingprogressreport.setCpap("");
		nursingprogressreport.setFeedType("");
		nursingprogressreport.setFio2("");
		nursingprogressreport.setHr("");
		nursingprogressreport.setIvfluids("");
		nursingprogressreport.setIvHr(0.0);
		nursingprogressreport.setIvtotal(0.0);
		nursingprogressreport.setLipidHr(0.0);
		nursingprogressreport.setName("");
		nursingprogressreport.setPresentWt(0.0);
		nursingprogressreport.setRr("");
		nursingprogressreport.setSao2("");
		nursingprogressreport.setTempAxilla("");
		nursingprogressreport.setTempIncBed("");
		nursingprogressreport.setTotal(0.0);
		nursingprogressreport.setUrine(0.0);
		nursingprogressreport.setVolume(0.0);
		
	}



}
