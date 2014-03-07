package com.oxymedical.component.renderer;
		
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.*;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;


import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.baseComponent.annotations.EventSubscriber;
import com.oxymedical.component.logging.LoggingComponent;
import com.oxymedical.component.renderer.Ant.HICAntUtility;
import com.oxymedical.component.renderer.application.installer.ZKAppUpdateHelper;
import com.oxymedical.component.renderer.application.modeler.BaseModeler;
import com.oxymedical.component.renderer.application.modeler.IBaseModeler;
import com.oxymedical.component.renderer.constants.ZKConstants;
import com.oxymedical.component.renderer.controller.HICDataController;
import com.oxymedical.component.renderer.exception.RendererComponentException;
import com.oxymedical.component.renderer.uiBuilder.zk.ZKBuilder;
import com.oxymedical.core.commonData.Application;
import com.oxymedical.core.commonData.Data;
import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.IApplication;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IFormPattern;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.deployApplicationInfo.ApplicationInfo;
import com.oxymedical.core.ioutil.FileIO;
import com.oxymedical.core.maintenanceData.IMaintenanceData;
import com.oxymedical.core.router.IRouter;
import com.oxymedical.core.xmlutil.XmlReader;
import com.oxymedical.framework.objectbroker.annotations.InjectNew;
import com.oxymedical.framework.objectbroker.annotations.InjectNewType;


/**
 * Rendering Component to render the UI and the standard functionality based 
 * on meta data (UI patern and Database Pattern)
 * 
 * @author      Oxyent Medical
 * @version     1.0.0
 **/

public class RendererComponent implements IComponent, IRendererComponent
{			
	private IApplication application = null;
	private com.oxymedical.component.renderer.Application renderApp = null;
	private IUIBuilder uiBuilder;

	private IBaseModeler baseModeler = null;
	private HICAntUtility hicAntUtility = null;
	
	public IHICData hicData = null;
	public boolean isAppExtension = false;
	
	/*@InjectNewType(implementationClass="com.oxymedical.servlet.HICServlet.HICRouter")
	public static IRouter router;*/
	
	//@InjectNewType(implementationClass="com.oxymedical.servlet.HICServlet.DataUnitRouter")
	public static IRouter dataUnitRouter = new com.oxymedical.servlet.HICServlet.DataUnitRouter();
	
	 @InjectNew
	 static public LoggingComponent logger;
	//TODO Need to change the implementation as a new Annotation type
	/*@InjectNew
	public DBComponent dbCompnent;
	
	@InjectNew
	public DBComponent billingDBComponent;
	
	@InjectNew
	public DBComponent maintDBComponent;

	@InjectNew
	public DBComponent mainDBcomponent; */
	
	/**
	 * Constructor of the Rendering Component
	 * */
	
	public RendererComponent()
	{
	
	}
	
	
	public static LoggingComponent GetInstanceOfLoggingComponent()
	{
		if(logger == null)
		  {
			logger = new LoggingComponent(); 
		  }
		  return  logger;
	}
	
	public static void setLoggingComponent(LoggingComponent logger) 
	{
		RendererComponent.logger = logger;
	}
	
	/**
	 * This function is used to provide the application specific information 
	 * 
	 * */
	
	@EventSubscriber(topic = "newApplication")
	public void newApplication(IHICData hicData) throws RendererComponentException
	{
		List<String> list = hicData.getData().getList();
		String applicationName = list.get(0);
		String applicationPath = list.get(1);
		String outputPath = list.get(2);
		String controllerPath = list.get(3); 
		String baseDirectoryPath = list.get(4);
		String serverDir = list.get(5);
		
		System.out.println("[RendererComponent][newApplication][Inside]"
				+ "[applicationName]" + applicationName
				+ "[applicationPath]" + applicationPath
				+ "[outputPath]" + outputPath
				+ "[baseDirectoryPath]" + baseDirectoryPath
				+ "[serverDir]" + serverDir
			);
		
		validateApplicationInfo(applicationName, applicationPath, outputPath, controllerPath, baseDirectoryPath, serverDir);
		this.application = createApplication(applicationName, applicationPath, outputPath, controllerPath, baseDirectoryPath, serverDir);
		renderApp.setAppExtension(false);
		
		// Creating the main folder which will contain all the application code
		this.createMainFolder();
	}

	public void newApplicationExt(String applicationName, String applicationPath, String outputPath,
			String controllerPath, String baseDirectoryPath, String serverDir)
			throws RendererComponentException
	{
		System.out.println("[RendererComponent][newApplicationExt][Inside]"
				+ "[applicationName]" + applicationName
				+ "[applicationPath]" + applicationPath
				+ "[outputPath]" + outputPath
				+ "[baseDirectoryPath]" + baseDirectoryPath
				+ "[serverDir]" + serverDir
			);
		
		validateApplicationInfo(applicationName, applicationPath, outputPath, controllerPath, baseDirectoryPath, serverDir);
		IApplication app = createApplication(applicationName, applicationPath, outputPath, controllerPath, baseDirectoryPath, serverDir);
		renderApp.setAppExtension(true);
		
		// Add application to Application Extension
		this.application.addExtension(app);
	}
	

	private void validateApplicationInfo(String applicationName, String applicationPath, String outputPath,
			String controllerPath, String baseDirectoryPath, String serverDir)
			throws RendererComponentException
	{
		if(applicationName==null || applicationPath==null || applicationName.trim()=="" || applicationPath.trim()=="")
			throw (new RendererComponentException("Application source not found"));
		else if(outputPath==null || outputPath.trim()=="")
			throw (new RendererComponentException("Application's output path not found"));
		else if(controllerPath==null || controllerPath.trim()=="")
			throw (new RendererComponentException("Application's controller path not found"));
		else if(baseDirectoryPath==null || baseDirectoryPath.trim()=="")
			throw (new RendererComponentException("Base Directory Path not found"));
		else if(serverDir==null || serverDir.trim()=="")
			throw (new RendererComponentException("Base Directory Path not found"));
	}
	
	private IApplication createApplication(String applicationName, String applicationPath, String outputPath,
			String controllerPath, String baseDirectoryPath, String serverDir)
			throws RendererComponentException
	{
		System.out.println("[RendererComponent][createApplication][Inside]"
				+ "[applicationName]" + applicationName
				+ "[applicationPath]" + applicationPath
				+ "[outputPath]" + outputPath
				+ "[baseDirectoryPath]" + baseDirectoryPath
				+ "[serverDir]" + serverDir
			);
		
		String applicationFileName="";
		outputPath = outputPath + "/";
		applicationPath = applicationPath + "/";
		applicationFileName = applicationName + ZKConstants.APPLICATION_EXTN;

		IApplication app = new Application();
		app.setApplicationName(applicationName);
		app.setApplicationFileName(applicationFileName);
		app.setApplicationFolderPath(applicationPath);
		app.setOutputPath(outputPath);
		app.setRenderApplicationType(ZKConstants.RENDER_ZK);
		app.setServerDirectory(serverDir);
		app.setBaseDirectoryPath(baseDirectoryPath);
		
		renderApp = new com.oxymedical.component.renderer.Application();
		renderApp.setApplicationName(applicationName);
		renderApp.setApplicationFileName(applicationFileName);
		renderApp.setApplicationFolderPath(applicationPath);
		renderApp.setOutputPath(outputPath);
		renderApp.setRenderApplicationType(ZKConstants.RENDER_ZK);
		renderApp.setServerDirectory(serverDir);
		renderApp.setBaseDirectoryPath(baseDirectoryPath);
		
		return app;
	}
	
	/**
	 * This function is used to start rendering the application
	 * @param HICData
	 * */

	@EventSubscriber(topic = "renderApplication")
	public void renderApplication(IHICData hicData) throws RendererComponentException	
	{
		System.out.println("inside renderApplication ===========================");
		hicAntUtility = new HICAntUtility();
		try
		{
			if(renderApp==null)
				throw (new RendererComponentException("Application source not found"));
			else
			{
				if (renderApp.getRenderApplicationType().equals(ZKConstants.RENDER_ZK))
				uiBuilder = new ZKBuilder();
				baseModeler = new BaseModeler();
				uiBuilder.setApplication(renderApp);
				baseModeler.setApplication(renderApp);

				//uiBuilder.renderUI(hicData,dbCompnent,maintDBComponent,billingDBComponent,createDatabase);
				uiBuilder.renderUI();
				
				System.out.println("[RendererComponent][renderApplication][renderApp.isAppExtension()]"
						+ renderApp.isAppExtension()
					);
				
				if (renderApp.isAppExtension())
				{
					System.out.println("[RendererComponent][renderApplication][Calling ZKAppUpdateHelper]");
					if (this.application == null)
						this.application = ApplicationInfo.getApplicationInfo("");
					
					// Copy only files to existing deployed application
					ZKAppUpdateHelper hlpr = new ZKAppUpdateHelper();
					hlpr.updateZKApplication(renderApp, application);
				}
				else
				{
					// Deploy the complete application
					hicAntUtility.createApplicationInfo(renderApp);
					hicAntUtility.integrateHIC(renderApp.getApplicationFolderPath(), 
							renderApp.getApplicationName(), 
							renderApp.getServerDirectory());
				}

				// Load zk jar file
				/*RenderingUtility.jarLoader(application);*/
			}
		}
		catch(RendererComponentException exp)
		{
			exp.printStackTrace();
			throw exp;
		}
	}


	/**
	 * This function is used to get the UIBuilder for the Rendering Component.
	 * 
	 * @return IUIBuilder
	 * */
	public IUIBuilder getUibuilder() {
		return uiBuilder;
	}

	/**
	 * This function is used to set the UI Builder for the Rendering Component.
	 * 
	 * @param IUIBuilder
	 * */
	public void setUibuilder(IUIBuilder uibuilder) {
	 	this.uiBuilder = uibuilder;
	}

	/**
	 * This function is used to stop the Rendering Component.
	 * 
	 * @return void
	 **/
	public void stop()
	{	
	 	
	}	
	 
	/**
	 * This function is used to destroy
	 * the Rendering Component.
	 * @return void
	 **/
	public void destroy()
	{
		
	}	
	
	/**
	 * This function is used to run
	 * the Rendering Component.
	 * @return void
	 * */
	public void run()
	{
		
	}
	
	/**
	 * This function is used to start
	 * the Rendering Component.
	 * @return void
	 * */
	public void start(Hashtable<String, Document> arg0) 
	{
		
	}
	
	
	/**
	 * This function is used to invoke 
	 * the Rendering Component.
	 * @return Object
	 * */
	public Object invoke(){
		
		Object object= null;
		return object;
	}
	
	/**
	 * The main method of Rendering Component
	 * the Rendering Component.
	 * @return void
	 * */
	public static void main(String args[])
	{	
		//logger.log(0,"RenderingComponent Called");
		RendererComponent.GetInstanceOfLoggingComponent();
		String sourceDir ="";// args[0];//C:/Gip
		String UserAdminURL;
		//String serverDirectory = args[1];//C:/apache-tomcat-5.5.20/apache-tomcat-5.5.20/webapps
		String applicationName;//args[2];//gip
		String  URL;// = args[3];// http://localhost:8080/
		RendererComponent rc = new RendererComponent();
		/*UserAdminURL = URL + applicationName + "/UserAdminController";
		URL = URL + applicationName + "/ControllerServlet";*/
		applicationName = "JAYDESIGN";
		
		sourceDir = sourceDir + "\\";
		
		logger.log(0,"applicationName ="+applicationName);
		//logger.log(0,"URL ="+URL);
		logger.log(0,"sourceDir ="+sourceDir);
	
		//RendererComponent.GetInstanceOfLoggingComponent();
		
		//String applicationName, sourceDir, URL/*, UserAdminURL*/;
		//RendererComponent rc = new RendererComponent();
		
		//sourceDir = "//172.30.10.9/public/AMS";
		sourceDir = "c:/JAYDESIGN";
		//URL = "http://172.30.10.9:8080/";
		URL="http://localhost:8080/";
		try
		{
			
			
			IHICData hicData = new HICData();
			IData data = new Data();
			List<String> parameters = new ArrayList<String>();
			parameters.add(applicationName);
			parameters.add(sourceDir);
			parameters.add(sourceDir);
			parameters.add(URL);
			//parameters.add("//172.30.10.9/public/glassfish/domains/domain1/lib/ext");
			//parameters.add("//172.30.10.9/public/glassfish/domains/domain1/autodeploy");
			parameters.add("c:/glassfish/domains/domain1/lib/ext");
			parameters.add("c:/glassfish/domains/domain1/autodeploy");
			data.setList(parameters);
			hicData.setData(data);
			rc.newApplication(hicData);
//			rc.newApplication(applicationName, sourceDir, sourceDir, URL,"c:/glassfish/domains/domain1/lib/ext","C:/glassfish/domains/domain1/autodeploy");
			
			//RUN FROM MAIN THE RENDERCOMPONENT BY COMMENTING BELOW LINE
			//rc.renderApplication(null);
			rc.renderToolXML(hicData);
			
			/*IHICData hicData = new HICData();
			IData data = new Data();
			IFormPattern formp = new FormPattern();
			data.setFormPattern(formp);
			hicData.setData(data);
			rc.move(hicData);*/
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
	}

	private boolean createMainFolder() throws RendererComponentException
	{
		FileIO fileWriter = new FileIO();
		return fileWriter.createFolder(application.getOutputPath() ,ZKConstants.MAIN_FOLDER_NAME);		
	}
	
	public void maintenance(IMaintenanceData maintenanceData)
	{
		
	}
	/**
	 * @return the hicData
	 */
	public IHICData getHicData() {
		return hicData;
	}
	/**
	 * @param hicData the hicData to set
	 */
	public void setHicData(IHICData hicData) {
		this.hicData = hicData;
	}
	
	@EventSubscriber(topic = "moveForm")
	public IHICData move(IHICData hicData)
	{
		logger.log(0,"Inside move method in rendering component");
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ hicData.getSessionId() inside rendering component = "+hicData.getSessionId());
		String sessionId = hicData.getSessionId();
		if(sessionId != null)
			HICDataController.addHICData(sessionId, hicData);
		IData data = hicData.getData();
		IFormPattern formPattern = data.getFormPattern();
		
		String formId = null;
		if ((formPattern != null) && (formPattern.getFormValues().containsKey("destinationForm")))
		formId = formPattern.getFormValues().get("destinationForm").toString();
	
		if (formId == null)
		{
			if (data.getRawData() != null) formId = data.getRawData().toString();
		}
		if(!(formId.lastIndexOf(".")>0))
			formId = formId+ ZKConstants.ZK_EXTN;
		
		System.out.println("formId-----"+formId);
		logger.log(0,"[In side move]-formId"+formId);
		try {
			Executions.sendRedirect(formId);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return hicData;
	}

	@EventSubscriber(topic = "renderToolXML")
	public IHICData renderToolXML(IHICData hicData)
	{
		Hashtable<String, Object> formValues = hicData.getData().getFormPattern().getFormValues();
		String formName = (String)formValues.get("formName");
		try 
		{
			XmlReader xmlReader = new XmlReader();
			//File file = new File("C:/glassfish/domains/domain1/config/tool-config/result.xml");
			File file = new File("C:/glassfish/domains/domain1/config/tool-config/"+formName+".xml");
			System.out.println("file.getAbsolutePath()----"+file.getAbsolutePath());
			Document formPatternDoc = xmlReader.getDocumentFromPath(/*"C:/glassfish/domains/domain1/config/tool-config/result.xml"*/file.getAbsolutePath());
			if(uiBuilder==null)
				uiBuilder = new ZKBuilder();
			System.out.println("localUIBuilder = "+uiBuilder);
			String applicationName = "LIMS";//hicData.getApplication().getApplicationName();
			System.out.println("applicationName--"+applicationName);
			applicationName = file.getAbsolutePath().substring(0, file.getAbsolutePath().indexOf("config"))+"applications\\j2ee-modules\\"+applicationName;
			System.out.println("applicationName after = "+applicationName);
			uiBuilder.renderSinglePage(formPatternDoc,/*"C:/glassfish/domains/domain1/config/tool-config/"*/file.getAbsolutePath().replaceFirst(formName+".xml", ""), applicationName);
		}catch (RendererComponentException e) {
			e.printStackTrace();
		}
		return hicData;
	}
	
	public static Session session;
	@EventSubscriber(topic = "saveSession")
	public IHICData saveSession(IHICData hicData)
	{
		Hashtable<String, Object> formValues = hicData.getData().getFormPattern().getFormValues();
		session = (Session)formValues.get("session");
		System.out.println("[========== session inside rendering component ========"+session);
		return hicData;
	}
	
	private Treeitem nodeToItem(Element element){
		Treeitem tr_node=new Treeitem();
		Treechildren tc = null;
		tr_node.setLabel(element.getName());
		tr_node.setAttribute("xmlnodename",element.getName());
		System.out.println("****tr_node label*****"+tr_node.getLabel());
		List<Attribute> attributes=element.attributes();
		Iterator<Attribute> itr_attr=attributes.iterator();
		Attribute att=null;
		while(itr_attr.hasNext()){
			att=itr_attr.next();
			if(att.getName().equalsIgnoreCase("_displayValue")){
				tr_node.setLabel(att.getValue());
			}
		
			tr_node.setAttribute(att.getName(), att.getValue());
			}
		Iterator<Element> itr_elem=element.elementIterator();
		if(itr_elem.hasNext())
		{
			 tc=new Treechildren();
		}
		while(itr_elem.hasNext()){
			Treeitem tr_child=new Treeitem();
			tr_child=nodeToItem(itr_elem.next());
			tc.appendChild(tr_child);
			
		}
		if(tc!=null){
		tr_node.appendChild(tc);
		}
		return tr_node;
		
	}
	
	@EventSubscriber(topic = "xmlToTreeItem")
	public IHICData xmlToTreeItem(IHICData hicData){
		Hashtable<String, Object> formValues = hicData.getData().getFormPattern().getFormValues();
		Document doc=(Document) formValues.get("TreeDoc");
	
		Element element = doc.getRootElement();
		Treeitem result_treeitem=nodeToItem(element);
		formValues.put("resultTreeitem", result_treeitem);
		return hicData;
	}
}