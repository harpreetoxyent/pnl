package com.oxymedical.component.renderer.application.installer;

import java.io.IOException;

import com.oxymedical.component.renderer.Application;
import com.oxymedical.component.renderer.constants.ZKConstants;
import com.oxymedical.core.commonData.IApplication;
import com.oxymedical.core.ioutil.FileIO;

public class ZKAppUpdateHelper
{
	public void updateZKApplication(Application renderApp, IApplication app)
	{
		// Copy only files to existing deployed application
		if ((renderApp == null) || (app == null))
		{
			System.out.println("[ZKAppUpdateHelper][updateZKApplication][EITHER RENDERAPP OR APP IS NULL]");
		}
		
		System.out.println("[ZKAppUpdateHelper][updateZKApplication][renderApp.getServerDirectory()]" 
				+ renderApp.getServerDirectory());
		System.out.println("[ZKAppUpdateHelper][updateZKApplication][app.getApplicationName()]" 
				+ app.getApplicationName());
		
		String baseDestPath = null;
		if (renderApp.getServerDirectory().indexOf(ZKConstants.GLASSFISH_SERVER_IDENTIFIER) > 0)
		{
			baseDestPath = renderApp.getServerDirectory() 
					+ "/../applications/j2ee-modules/" + app.getApplicationName() + "/";
		}
		String baseSourcePath = renderApp.getApplicationFolderPath() 
				+ "/" + ZKConstants.MAIN_FOLDER_NAME;
		
		System.out.println("[ZKAppUpdateHelper][updateZKApplication][baseDestPath]" + baseDestPath);
		System.out.println("[ZKAppUpdateHelper][updateZKApplication][baseSourcePath]"+ baseSourcePath);
		try
		{
			FileIO fileIO = new FileIO();
			
			String folderToCopy = "/css";
			fileIO.copyDirectory(baseSourcePath + folderToCopy, baseDestPath + folderToCopy);

			folderToCopy = "/zul";
			fileIO.copyDirectory(baseSourcePath + folderToCopy, baseDestPath + folderToCopy);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
