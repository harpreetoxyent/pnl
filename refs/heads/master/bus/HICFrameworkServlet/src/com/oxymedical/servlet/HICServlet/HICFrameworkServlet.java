package com.oxymedical.servlet.HICServlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.oxymedical.component.baseComponent.IComponentIdConstants;
import com.oxymedical.component.baseComponent.exception.ComponentException;
import com.oxymedical.core.commonData.Application;
import com.oxymedical.core.commonData.Data;
import com.oxymedical.core.commonData.DataBase;
import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.IApplication;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IDataBase;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.deployApplicationInfo.ApplicationInfo;
import com.oxymedical.core.propertyUtil.PropertyUtil;
import com.oxymedical.core.router.IDataUnitRouter;
import com.oxymedical.core.router.IRouter;
import com.oxymedical.hic.application.NOLISRuntime;
import com.oxymedical.hic.application.eventmanagement.PublicationScope;
import com.oxymedical.hic.request.DatabaseRequest;
import com.oxymedical.hic.request.RenderRequest;
import com.oxymedical.hic.request.RuleRequest;
import com.oxymedical.hic.request.UserRequest;
import com.oxymedical.hic.request.WebServiceRequest;
import com.oxymedical.hic.request.WorkflowRequest;

public class HICFrameworkServlet extends HttpServlet 
{
	private static Logger log = Logger.getLogger(HICFrameworkServlet.class);

	public HICFrameworkServlet()
	{
		hashEntries = null; 
		hicServiceUtil = null;
		applicationName = "";
		sourceDir = "";
		hicAntUtility = new HICAntUtility();
		renderingPerformed = false;
		firstNotNullRendering = false;
		exceptionOccured = false;
		renderingApplicationSettings = null;
		hicData = new HICData();
		dataBase = new DataBase();
		hicRouter = null;
	}

	public void init() throws ServletException
	{
		super.init();
		System.out.println("---HIC Framework Servlet Initialized---");
		boolean isValidLicense = true;
		//HS
		/*
		NEISEntitlementEngine entitlement = new NEISEntitlementEngine();
		try
		{
			entitlement.run();
		}
		catch(ComponentException exp)
		{
			isValidLicense = false;
			exp.printStackTrace();
		}
		 */
		if(isValidLicense)
		{
			try
			{
				System.out.println("inside the init of hic framework---");
				super.init();
				renderingApplicationSettings = RenderingApplicationSettings.getInstanceUserAdminSystemSettings();
				startHIC();
				System.out.println("----HIC Framework started as module xml found. Now initilizing housekeeping components------");
				initializeComponents();
				initializeHICObjects();
				initializeWebServiceProvider();
				webserviceThread.join();
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("NO VALID LICENSE FILE FOUND contact support@oxyent-medical.com");
			throw new ServletException("NO VALID LICENSE FILE FOUND contact support@oxyent-medical.com");

		}
		System.out.println("-------HIC Framework successfully initialized---");
	}

	public static void startHIC()
	{
		HICServiceUtil.startHIC(PropertyUtil.setUpProperties("MODULE_LOC"));
	}

	private void initializeComponents()
	{
		String Module_XML_Key = HICServiceUtil.NOLISStartupUtility.Module_XML_Key;
		System.out.println("------------Module_XML_Key----------"+Module_XML_Key);
		Document doc = HICServiceUtil.NOLISStartupUtility.ConfigData.get(Module_XML_Key);
		List<Node> list = doc.selectNodes( "//container/modulelist/module" );
		for (int i=0; i<list.size(); i++)
		{
			Node module = list.get(i);
			String moduleId = module.valueOf( "@id" );
			System.out.println("[Loading module]: " + moduleId);
			NOLISRuntime.getComponent(moduleId);
		}
	}

	public void initializeHICObjects()
	{
		hicRouter = (IRouter) NOLISRuntime.getClassInstance(HICRouter.class,IComponentIdConstants.ROUTER_ID);
		duRouter = (IDataUnitRouter) NOLISRuntime.getClassInstance(DataUnitRouter.class,IComponentIdConstants.DUROUTER_ID);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		System.out.println("-------------Inside HICFrameworkServlet Post Method----------------");		
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		System.out.println("-------------Inside HICFrameworkServlet Get Method----------------");
		Boolean createDatabaseResources = null;
		try
		{
			
			System.out.println("--------------doGetForRules-----------");		//By default setting up a default rule component
			
			RuleRequest ruleReq= new RuleRequest(hicData);
			ruleThread = new Thread(ruleReq);
			System.out.println("--------------startRules-----------");
			ruleThread.start();
			
			hashEntries = readRequest(request);
			if (hashEntries!=null 
					&& hashEntries.get(URLConstants.applicationName)!=null 
					&& hashEntries.get(URLConstants.BASE_APPLICATION_NAME) != null)
			{
				currentApplicationName = hashEntries.get(URLConstants.applicationName).toString();
			}

			if (hashEntries.get(URLConstants.BASE_APPLICATION_NAME) != null)
			{
				// Only update the zul files for "Extension Application"
				run(hashEntries, true);
			}
			else if (hashEntries.get("UserAdmin") != null && hashEntries.get("authenticate") != null)
			{
				NOLISRuntime.FireEvent("setUADataBaseSetting", new Object[]{hicData}, PublicationScope.Global);
			}
			else if (hashEntries.get("UserAdmin") != null
					&& hashEntries.get("deploy") != null)
			{
				NOLISRuntime.FireEvent("setUADataBaseSetting", new Object[]{hicData}, PublicationScope.Global);
				NOLISRuntime.FireEvent("setWorkflowDataBaseSetting", new Object[]{hicData}, PublicationScope.Global);
				String redirectURL = handleDeployRequest(request, response);
				renderingPerformed = true;
				System.out.println("== Redirecting to : " + redirectURL);
				response.sendRedirect(redirectURL);
			}
		}
		catch (ComponentException e)
		{
			e.printStackTrace();
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
		}
	}

	private String handleDeployRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, Exception
	{
		System.out.println("--------------Inside handleDeployRequest-----------");
		Boolean createDBResources = null;
		try
		{
			if(((String)hashEntries.get("UserAdmin")).trim().equalsIgnoreCase("true"))
			{
				System.out.println("--------------Inside if UserAdmin true-----------");

				createDBResources = Boolean.valueOf(true);
				initializeDBComponent(createDBResources.booleanValue());
				renderNewApplication(createDBResources);
				UserRequest userReq = new UserRequest();
				userReq.setApplicationName((String)hashEntries.get("ApplicationName"));
				userReq.setAuthenticate((String)hashEntries.get("authenticate"));
				userReq.setSourceDir((String)hashEntries.get("sourcedir"));

				/*userReq.handleUserRequest();*/
				useradminThread = new Thread(userReq);
				useradminThread.start();
				System.out.println("--------------Inside if useradminThread started-----------");

				/*for workFlow*/
				WorkflowRequest workReq= new WorkflowRequest(hicData,getApplicationInfo());
				workflowThread = new Thread(workReq);
				workflowThread.start();
				// Lets resume only after all processes are complete.
				renderThread.join();
				useradminThread.join();
				dbThread.join();
				workflowThread.join();

				String redirectURL = null;
				if(hashEntries.get("sourcedir") != null)
				{
					redirectURL = (new StringBuilder(URLConstants.serverAddress)).append((String)hashEntries.get("ApplicationName")+"/").append((String)hashEntries.get("ApplicationName")).append(".zul").toString();
					IHICData hicData = new HICData();
					IData data = new Data();
					List parameters = new ArrayList();
					parameters.add(request);
					parameters.add(hashEntries);
					parameters.add(redirectURL);
					data.setList(parameters);
					hicData.setData(data);
					NOLISRuntime.FireEvent("deployPortlet", new Object[]{hicData}, PublicationScope.Global);
					System.out.println("[===== after updatePortletPreference 1111======");
					NOLISRuntime.FireEvent("changeLiferayLogo", new Object[]{hicData}, PublicationScope.Global);
					//redirectURL = URLConstants.serverAddress+"c";
					//Thread.sleep(20000);
					System.out.println("========redirectURL inside hic framework servlet========"+redirectURL);
					return redirectURL;
				}
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return null;
	}

	private synchronized void initializeWebServiceProvider()
	{
		try
		{
			webserviceThread = new Thread(new WebServiceRequest());
			webserviceThread.start();
		}
		catch (Exception e) 
		{
		}
	}

	private synchronized void initializeDBComponent(boolean createDBResources)
	{
		if (createDBResources)
		{
			IApplication application = getApplicationInfo();
			try {
				IHICData hicData = new HICData();
				IData data = new Data();
				List parameters = new ArrayList();
				parameters.add(application);
				data.setList(parameters);
				hicData.setData(data);
				NOLISRuntime.FireEvent("setApplicationInDBComponent", new Object[]{hicData}, PublicationScope.Global);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// DB thread for creating resources and mappings
			dbThread = new Thread(new DatabaseRequest());
			dbThread.start();
		}
	}

	private IApplication getApplicationInfo()
	{
		System.out.println("*******Inside getApplication Info *******");
		IApplication application = new Application();
		System.out.println("*******Inside getApplication Info 2 *******");
		String sourceDir = null;
		String serverLibDir = null;
		String appName = null;
		String serverDir = null;
		appName = (String)hashEntries.get("ApplicationName");
		System.out.println("Application Info = " + appName);
		sourceDir = (String)hashEntries.get("sourcedir");
		System.out.println("Source Directory = " + sourceDir);
		serverLibDir = (String)hashEntries.get("serverLibDirectory");
		System.out.println("Server Lib directory = " + serverLibDir);
		serverDir = (String)hashEntries.get("serverDirectory");
		System.out.println("Server  directory = " + serverDir);
		String applicationFolderPath=sourceDir+"/";
		application.setApplicationName(appName);
		application.setServerDirectory(serverDir);
		application.setBaseDirectoryPath(serverLibDir);
		application.setApplicationFileName(appName+".esp");
		application.setApplicationFolderPath(applicationFolderPath);
		return application;
	}

	private synchronized void run(Hashtable hashEntries, Boolean createDatabase)
	throws Exception
	{
		try
		{
			String sourceDir = null;
			String serverLibDir = null;
			String appName = null;
			String retRenderingOpt = null;
			appName = (String)hashEntries.get("ApplicationName");
			sourceDir = (String)hashEntries.get("sourcedir");
			serverLibDir = (String)hashEntries.get("serverLibDirectory");
			retRenderingOpt = (String)hashEntries.get("renderOption");

			if(retRenderingOpt == null)
				retRenderingOpt = (String)ApplicationInfo.retreiveAppInfo(appName, "renderOption",currentApplicationName);

			if(sourceDir == null)
				sourceDir = (String)ApplicationInfo.retreiveAppInfo(appName, "sourcedir",currentApplicationName);

			if(serverLibDir == null)
				serverLibDir = (String)ApplicationInfo.retreiveAppInfo(appName, "serverLibDirectory",currentApplicationName);

			IHICData hicData = new HICData();
			IData data = new Data();
			List<String> parameters = new ArrayList<String>();
			parameters.add((String) hashEntries.get("ApplicationName"));
			parameters.add(sourceDir);
			parameters.add(sourceDir);
			parameters.add((String) hashEntries.get("serverIPAddress"));
			parameters.add(serverLibDir);
			parameters.add((String) hashEntries.get("serverDirectory"));
			data.setList(parameters);
			hicData.setData(data);
			if (hashEntries.get(URLConstants.BASE_APPLICATION_NAME) != null)
			{
				// This is an extension
				NOLISRuntime.FireEvent("newApplicationExt", new Object[]{hicData}, PublicationScope.Global);
			}
			else
			{
				NOLISRuntime.FireEvent("newApplication", new Object[]{hicData}, PublicationScope.Global);				
			}
			renderThread = new Thread(new RenderRequest());
			renderThread.start();
		}
		catch(Exception e)
		{
			throw e;
		}
	}

	private synchronized String renderNewApplication(Boolean createDatabase)
	throws Exception
	{
		String redirectURL = null;
		try
		{
			run(hashEntries, createDatabase);			
		}
		catch(ComponentException e)
		{
			throw e;
		}
		catch(Exception ex)
		{
			throw ex;
		}
		if(hashEntries.get("sourcedir") != null)
		{
			redirectURL = (new StringBuilder(URLConstants.serverAddress)).append((String)hashEntries.get("ApplicationName")+"/").append((String)hashEntries.get("ApplicationName")).append(".zul").toString();
		} 
		else
		{
			String lastAppName = null;
			String lastAppName1 = null;
			Hashtable allAppHash = (Hashtable) ApplicationInfo.retreiveAppInfo("", "",currentApplicationName);
			Hashtable appHash = null;
			Enumeration<String> allAppEnum = allAppHash.keys();
			while(allAppEnum.hasMoreElements())
			{
				lastAppName = allAppEnum.nextElement();
				if(lastAppName.equalsIgnoreCase("Maintenance") || lastAppName.equalsIgnoreCase("BillingTracking"))
					continue;
				lastAppName1 = lastAppName;
				appHash = new Hashtable();
				appHash = (Hashtable) allAppHash.get(lastAppName);
				sourceDir = (String) appHash.get("sourcedir");
			}
			redirectURL = (new StringBuilder(URLConstants.serverAddress + "laszlo-web/lps/")).append(lastAppName1).append("/lzx/").append(lastAppName1).append(".lzx").toString();
		}
		return redirectURL;
	}

	public synchronized Hashtable readRequest(HttpServletRequest request)
	{
		Hashtable hashSettings = new Hashtable();
		Enumeration parameters = request.getParameterNames();

		String appName = null;
		hashSettings.put("serverIPAddress", URLConstants.serverAddressAppended);
		while(parameters.hasMoreElements())
		{
			String parameterName = (String)parameters.nextElement();
			if(parameterName.trim().equalsIgnoreCase("ApplicationName") 
					&& request.getParameter(parameterName.trim()) != null)
			{
				hashSettings.put("ApplicationName", request.getParameter(parameterName.trim()));
				appName = request.getParameter(parameterName.trim());
				currentApplicationName = appName;
			}
			if(parameterName.trim().equalsIgnoreCase("TitleName") 
					&& request.getParameter(parameterName.trim()) != null)
			{
				hashSettings.put("TitleName", request.getParameter(parameterName.trim()));
				titleName = request.getParameter(parameterName.trim());
			}
			if(parameterName.trim().equalsIgnoreCase("sourcedir") 
					&& request.getParameter(parameterName.trim()) != null)
			{
				//Determines whether or not a datasettings.xml file exists
				hashSettings.put("sourcedir", request.getParameter(parameterName.trim()));
				File file=new File(request.getParameter(parameterName.trim()) + URLConstants.DATABASESETTING);
				boolean dbExists = file.exists();
				if(dbExists)
				{  
					boolean value = setDataBaseSetting(request.getParameter(parameterName.trim()) + URLConstants.DATABASESETTING);
				}
			}
			if(parameterName.trim().equalsIgnoreCase("serverDirectory") 
					&& request.getParameter(parameterName.trim()) != null)
			{
				hashSettings.put("serverDirectory", request.getParameter(parameterName.trim()));
			}
			if(parameterName.trim().equalsIgnoreCase("renderOption") 
					&& request.getParameter(parameterName.trim()) != null)
			{
				hashSettings.put("renderOption", request.getParameter(parameterName.trim()));
			}
			if(parameterName.trim().equalsIgnoreCase("serverLibDirectory") 
					&& request.getParameter(parameterName.trim()) != null)
			{
				hashSettings.put("serverLibDirectory", request.getParameter(parameterName.trim()));
			}
			if(parameterName.trim().equalsIgnoreCase("UserAdmin") 
					&& request.getParameter(parameterName.trim()) != null)
			{
				hashSettings.put("UserAdmin", request.getParameter(parameterName.trim()));
			}
			if(parameterName.trim().equalsIgnoreCase("deploy") 
					&& request.getParameter(parameterName.trim()) != null)
			{
				hashSettings.put("deploy", request.getParameter(parameterName.trim()));
			}
			if(parameterName.trim().equalsIgnoreCase("liferayDirectory") 
					&& request.getParameter(parameterName.trim()) != null)
			{
				hashSettings.put("liferayDirectory", request.getParameter(parameterName.trim()));
			}

			/*
			 * Base Application Name - Required so that Extended applications
			 * can be installed above Base Applications
			 */
			if(parameterName.trim().equalsIgnoreCase(URLConstants.BASE_APPLICATION_NAME) 
					&& request.getParameter(parameterName.trim()) != null)
			{
				hashSettings.put(URLConstants.BASE_APPLICATION_NAME, request.getParameter(parameterName.trim()));
			}
		}
		if(appName != null && hashSettings.get("sourcedir") != null 
				&& hashSettings.get("serverLibDirectory") != null)
		{
			Hashtable appInfoHash = null;
			Hashtable applicationHash = null;
			if(applicationHash == null || applicationHash.isEmpty())
				applicationHash = new Hashtable();
			else
				if(applicationHash.get(appName) != null)
					applicationHash.remove(appName);
			applicationHash.put(appName, hashSettings);
			System.out.println("------------applicationHash-------------"+applicationHash);
			renderingApplicationSettings.createApplicationInfo(applicationHash);
		}
		return hashSettings;
	}

	/**
	 * This method is used to set the database setting like username , password changes by Harmeet
	 * 
	 * changes by Wasim , 19-May-2009
	 * new attribute added in database object as port number,dbtype.
	 * this value is now hard coded. it would be removed when these value will get in databasesetting xml file.
	 * 
	 * portnumber and dbtype hardcoded removed
	 * changes by wasim 8-June-2009
	 * 
	 */
	public boolean setDataBaseSetting(String dataSettingPath)
	{
		FileInputStream inputFile = null;
		Document document = null;
		String userName ;
		String password;
		String serverName;
		String portNumber;
		String dbType;
		boolean value = false;
		try 
		{
			inputFile = new FileInputStream(dataSettingPath);
			InputStreamReader dataInputStream = new InputStreamReader(inputFile);
			SAXReader reader = new SAXReader();
			document = reader.read(dataInputStream);
			Element root = document.getRootElement();
			userName = root.element("user").getTextTrim();
			password = root.element("password").getTextTrim();
			serverName = root.element("servername").getTextTrim();
			dbType = root.element("databasetype").getTextTrim();
			portNumber = root.element("portno").getTextTrim();
			
			if(null != userName && null != password && null != serverName)
			{
				dataBase.setUserName(userName);
				dataBase.setPassword(password);
				dataBase.setServerName(serverName);
				dataBase.setDataBasePort(portNumber);
				dataBase.setDbType(dbType);
				hicData.setDatabase(dataBase);
				value = true;
			}
		}
		catch (FileNotFoundException e1) 
		{
			e1.printStackTrace();
		}
		catch (DocumentException e) 
		{
			e.printStackTrace();
		}
		return value;
	}

	Hashtable hashEntries;
	private static final long serialVersionUID = 1L;
	public HICServiceUtil hicServiceUtil;
	String applicationName;
	String sourceDir;
	HICAntUtility hicAntUtility;
	public static int count = 1;
	public boolean renderingPerformed;
	public boolean firstNotNullRendering;
	boolean exceptionOccured ;
	RenderingApplicationSettings renderingApplicationSettings;

	private IHICData hicData = null;
	private IDataBase dataBase = null;
	boolean sessionIsNew = true;
	IRouter hicRouter;
	IDataUnitRouter duRouter;
	String currentApplicationName = null;
	String titleName = null;

	// For thread implementation. Threads have saved around 11 seconds of deployment time.
	Thread webserviceThread;
	Thread renderThread;
	Thread useradminThread;
	Thread dbThread;
	Thread workflowThread;
	Thread ruleThread;
	
}