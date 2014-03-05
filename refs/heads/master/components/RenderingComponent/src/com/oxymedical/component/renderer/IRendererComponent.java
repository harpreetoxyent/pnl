package com.oxymedical.component.renderer;
import com.oxymedical.component.baseComponent.annotations.EventSubscriber;
import com.oxymedical.component.renderer.exception.RendererComponentException;
import com.oxymedical.core.commonData.IHICData;

public interface IRendererComponent 
{
	//public void execute(Event evt, HICData hicData);
	//public void renderApplication(HICData hicData, Boolean createDatabase) throws RendererComponentException;
	
	
	public void renderApplication(IHICData hicData) throws RendererComponentException;


	public void newApplication(IHICData hicData) throws RendererComponentException;


	public void newApplicationExt(String applicationName, String applicationPath, String outputPath,
			String controllerPath, String baseDirectoryPath, String serverDir)
			throws RendererComponentException;
	
	@EventSubscriber(topic = "moveForm")
	public IHICData move(IHICData hicData);
}
