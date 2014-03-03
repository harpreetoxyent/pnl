package com.oxymedical.core.commonData;

import java.util.List;

public interface IApplication extends Cloneable
{
	public String getApplicationFileName();
	public void setApplicationFileName(String applicationFileName);
	public String getApplicationFolderPath();
	public void setApplicationFolderPath(String applicationFolderPath);
	public String getOutputPath();
	public void setOutputPath(String outputPath);
	public String getApplicationController();
	public void setApplicationController(String applicationController);
	public String getUserAdminController();
	public void setUserAdminController(String userAdminController);
	public String getApplicationName();
	public void setApplicationName(String applicationName);
	public String getBaseDirectoryPath();
	public void setBaseDirectoryPath(String baseDirectoryPath);
	public String getRenderApplicationType();
	public void setRenderApplicationType(String renderApplicationType);
	public String getServerDirectory();
	public void setServerDirectory(String serverDirectory);
	
	public List<IApplication> getExtensions();
	public void setExtensions(List<IApplication> extensions);
	public void addExtension(IApplication app);
	public Object clone();
}
