package com.pnl.component.knowledgeGraph;

import java.util.Hashtable;

import org.dom4j.Document;

import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.baseComponent.exception.ComponentException;
import com.oxymedical.component.baseComponent.maintenance.annotations.MaintenancePublisher;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.maintenanceData.IMaintenanceData;

public class KnowledgeGraphComponent implements IKnowledgeGraphComponent, IComponent{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
	@MaintenancePublisher
	public void maintenance(IMaintenanceData maintenanceData) {
		// TODO Auto-generated method stub
		
	}

}