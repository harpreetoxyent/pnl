package com.oxymedical.core.commonData;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to get and set the values of the application 
 *  for the Renderer
 *  
 */
public class Application implements IApplication, java.io.Serializable
{
	private String outputPath;
	private String applicationName;
	private String applicationFileName;
	private String applicationFolderPath;
	private String applicationController;
	private String renderApplicationType;
	private String userAdminController;
	private String baseDirectoryPath;
	private String serverDirectory;
	private List<IApplication> extensions;

	/**
	 * this method is used to retrieve the application file name 
	 * @return applicationFileName
	 */
	public String getApplicationFileName() {
		return applicationFileName;
	}

	/**
	 * this method is used to set the application file name
	 * @param String applicationFileName
	 */
	public void setApplicationFileName(String applicationFileName) {
		this.applicationFileName = applicationFileName;
	}

	/**
	 * this method is used to retrieve the application folder path
	 * @return
	 */
	public String getApplicationFolderPath() {
		return applicationFolderPath;
	}

	/**
	 * this method is used to set the application folder path
	 * @param String applicationFolderPath
	 */
	public void setApplicationFolderPath(String applicationFolderPath) {
		this.applicationFolderPath = applicationFolderPath;
	}

	/**
	 * this method is used to retrieve the application output path
	 * @return
	 */
	public String getOutputPath() {
		return outputPath;
	}

	/**
	 * this method is used to set the application output path
	 * @param String outputPath
	 */
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	/**
	 * this method is used to retrieve the controller of the application 
	 * @return
	 */
	public String getApplicationController() {
		return applicationController;
	}

	/**
	 * this method is used to set the controller of the application 
	 * @param String applicationController
	 */
	public void setApplicationController(String applicationController) {
		this.applicationController = applicationController;
	}

	/**
	 * this method is used to retrieve the useradmin controller 
	 * @return
	 */
	public String getUserAdminController() {
		return userAdminController;
	}

	/**
	 * this method is used to set the useAdmin controller
	 * @param String userAdminController
	 */
	public void setUserAdminController(String userAdminController) {
		this.userAdminController = userAdminController;
	}

	/**
	 * this method is used to retrieve the name of the application
	 * @return
	 */
	public String getApplicationName() {
		return applicationName;
	}

	/**
	 * this method is used to set the name of the application
	 * @param String applicationName
	 */
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	/**
	 * this method is used to retrieve the base directory path
	 * @return
	 */
	public String getBaseDirectoryPath() {
		return baseDirectoryPath;
	}

	/**
	 * this method is used set the base directory path
	 * @param String baseDirectoryPath
	 */
	public void setBaseDirectoryPath(String baseDirectoryPath) {
		this.baseDirectoryPath = baseDirectoryPath;
	}

	public String getRenderApplicationType() {
		return renderApplicationType;
	}

	public void setRenderApplicationType(String renderApplicationType) {
		this.renderApplicationType = renderApplicationType;
	}

	public String getServerDirectory() {
		return serverDirectory;
	}

	public void setServerDirectory(String serverDirectory) {
		this.serverDirectory = serverDirectory;
	}
	
	public List<IApplication> getExtensions()
	{
		return extensions;
	}
	public void setExtensions(List<IApplication> extensions)
	{
		this.extensions = extensions;
	}
	public void addExtension(IApplication app)
	{
		if (this.extensions == null) this.extensions = new ArrayList<IApplication>();
		this.extensions.add(app);
	}

	public Object clone()
	{
		IApplication app = new Application();
		app.setApplicationController(applicationController);
		app.setApplicationFileName(applicationFileName);
		app.setApplicationFolderPath(applicationFolderPath);
		app.setApplicationName(applicationName);
		app.setBaseDirectoryPath(baseDirectoryPath);
		app.setOutputPath(outputPath);
		app.setRenderApplicationType(renderApplicationType);
		app.setServerDirectory(serverDirectory);
		app.setUserAdminController(userAdminController);
		
		if (this.extensions!=null && this.extensions.size() > 0)
        {
			ArrayList<IApplication> ext = new ArrayList<IApplication>();
			for (int i = 0; i < this.extensions.size(); i++)
			{
				ext.add((IApplication)this.extensions.get(i).clone());
			}
			app.setExtensions(ext);
        }

		return app;
	}
	
}