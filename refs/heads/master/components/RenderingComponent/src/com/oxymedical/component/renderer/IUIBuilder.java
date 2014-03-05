package com.oxymedical.component.renderer;

import org.dom4j.Document;

import com.oxymedical.component.renderer.application.modeler.IBaseModeler;
import com.oxymedical.component.renderer.exception.RendererComponentException;


public interface IUIBuilder 
{
	//public void renderUI(HICData hicData,IDBComponent dbComponent,IDBComponent maintDB , IDBComponent BtDB, Boolean createDatabase) throws RendererComponentException;
	public void renderUI() throws RendererComponentException;
	public void renderSinglePage(Document doc, String localFilePath, String applicationName) throws RendererComponentException;
	public void setApplication(Application application);
	public void setBaseModeler(IBaseModeler baseModeler);
}
