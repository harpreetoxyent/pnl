package com.oxymedical.component.baseComponent;

import java.util.Hashtable;

import com.oxymedical.component.baseComponent.exception.ComponentException;
import com.oxymedical.component.baseComponent.maintenance.annotations.MaintenancePublisher;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.maintenanceData.IMaintenanceData;


public interface IComponent 
{
	public void start(Hashtable<String,org.dom4j.Document> configData);
	public void run() throws ComponentException;
	public void stop() throws ComponentException;
	public void destroy() throws ComponentException;
	public IHICData getHicData();
	public void setHicData(IHICData hicData);
	
	//This annotation allows the method to be automatically
	//maintained /tracked by HIC and other components. By default 
	//maintenance method of every component is annotated to provide 
	//maintenance facilities by HIC
	@MaintenancePublisher 
	public void maintenance(IMaintenanceData maintenanceData);
}
