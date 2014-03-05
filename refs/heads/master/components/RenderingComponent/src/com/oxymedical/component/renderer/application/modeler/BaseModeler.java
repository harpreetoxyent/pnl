package com.oxymedical.component.renderer.application.modeler;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.Document;
import org.dom4j.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.db.IDBComponent;
import com.oxymedical.component.db.application.IDataHandler;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.renderer.Application;
import com.oxymedical.component.renderer.RendererComponent;
import com.oxymedical.component.renderer.constants.ZKConstants;
import com.oxymedical.component.renderer.data.HibernateQueryResultDataSource;
import com.oxymedical.component.renderer.exception.RendererComponentException;
import com.oxymedical.component.renderer.uiBuilder.zk.util.ZKBuilderUtility;
import com.oxymedical.component.renderer.util.RenderingUtility;
import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.core.renderdata.DataUnit;
import com.oxymedical.core.renderdata.ExternalData;
import com.oxymedical.core.renderdata.IDataUnit;
import com.oxymedical.core.xmlutil.XmlReader;
import com.oxymedical.hic.application.NOLISRuntime;

/**
 * This class is responsible for processing the application logic of the
 * Rendering component.It retrieves and saves data in the database and invokes
 * services of other HIC components
 * 
 * @author Oxyent Medical
 * @version 1.0.0
 * 
 */

public class BaseModeler implements IBaseModeler
{

	// initialise variables
	private static IDBComponent dbComponent = null;
	private IDataUnit renderingData = null;
	static Application application = null;
	private List dataList = null;
	private ArrayList<String> dataPatternArray = null;
	private String dataPatternName = null;
	private XmlReader xmlReader = new XmlReader();
	private String applicationFolderPath;
	private String applicationFileName;
	ZKBuilderUtility zkBuilderUtility = null;
	IDataHandler dataHandler;
	protected String baseWindowName;
	protected Hashtable requestData;
	protected LinkedHashMap colOrder;

	public void setRequestData(IDataUnit renderingData)
	{
		this.renderingData = renderingData;
	}

	// This method instantiates the database component to be used for rendering
	/*public IDBComponent getInstance()
	{
		if (null == dbComponent)
		{
			try
			{

				dbComponent = (IDBComponent) NOLISRuntime.getComponent("dbCompnent");
				RendererComponent.logger.log(0,"dbcomponent instance  ==== " + dbComponent);

				// dbComponent=new DBComponent();
				if (null != dbComponent)
					getConnection();

			}
			catch (RendererComponentException exp)
			{
				RendererComponent.logger.log(0,exp.getMessage());
			}

		}
		return dbComponent;
	}

	public void getConnection() throws RendererComponentException
	{

		RendererComponent.logger.log(0,"*********this is getConnection***********");

		zkBuilderUtility = new ZKBuilderUtility();
		Document formPatternDoc = null;
		String defaultForm = "";
		Document document = null;
		// Document doc = null;
		// String defaultForm = "";
		String dataPatternName;
		// UIElement paternrootUI;
		String serverName = "", userName = "", password = "";
		boolean createDBResources = true;

		applicationFolderPath = application.getApplicationFolderPath();
		RendererComponent.logger.log(0,"folder path is 1111111111 " + applicationFolderPath);
		applicationFileName = application.getApplicationFileName();

		RendererComponent.logger.log(0,"application name-----------" + applicationFileName);
		try
		{
			if (applicationFolderPath == null || applicationFolderPath.trim().equals(""))
				throw (new RendererComponentException("Application Source not found"));
			else if (applicationFileName == null || applicationFileName.trim().equals(""))
				throw (new RendererComponentException("Application Source not found"));
			else
			{

				String applicationFile = applicationFolderPath + applicationFileName;

				// dbComponent = new DBComponent();
				dataPatternArray = new ArrayList<String>();
				document = xmlReader.getDocumentFromPath(applicationFile);
				org.dom4j.Element root = document.getRootElement();
				if (root.getName().trim().equalsIgnoreCase(ZKConstants.appRootTagName))
				{
					Element formPattern = root
							.element(ZKConstants.FORM_PATTERN_ROOT_TAG_NAME);
					List lst = root.elements(ZKConstants.formPatternsRootTagName);
					List dataList = root.elements("DataPattern");
					List formPatternList = formPattern.elements();
					String mainAppDocStr;
					mainAppDocStr = zkBuilderUtility.startApplication(application);
					if (dataList.size() > 0)
					{
						Element dataRoot = (org.dom4j.Element) dataList.get(0);
						RendererComponent.logger.log(0,"datalist--------------get(0)--"
								+ dataList.get(0));
						List dataSubLst = dataRoot.elements("datapattern");
						RendererComponent.logger.log(0,"datapattern------------size0--"
								+ dataSubLst.size());
						for (int i = 0; i < dataSubLst.size(); i++)
						{
							org.dom4j.Element page = (org.dom4j.Element) dataSubLst
									.get(i);
							
							 * if(page.attributeValue(UIConstants.defaultArg)!=null
							 * &&
							 * page.attributeValue(UIConstants.defaultArg).equals
							 * ("true")) defaultForm =
							 * page.attributeValue(UIConstants
							 * .elementName).trim();
							 
							dataPatternArray.add(page.attributeValue("name"));
							RendererComponent.logger.log(0,"page attribute value---------"
									+ page.attributeValue("name"));
						}
					}

					if (null != formPatternList && formPatternList.size() > 0)
					{
						// RendererComponent.logger.log(0,"formPatternList:size:"+formPatternList.size());
						for (int i = 0; i < formPatternList.size(); i++)
						{
							Element page = (Element) formPatternList.get(i);
							String formPatternXmlName = page
									.attributeValue(ZKConstants.ELEMENT_ID);
							// RendererComponent.logger.log(0,"formPatternXmlName1111111111111"+formPatternXmlName);
							if (null != page.attributeValue(ZKConstants.DEFAULT_ARG)
									&& page.attributeValue(ZKConstants.DEFAULT_ARG)
											.equals(ZKConstants.TRUE))
							{
								defaultForm = page.attributeValue(
										ZKConstants.ELEMENT_NAME).trim();
								mainAppDocStr = mainAppDocStr
										+ zkBuilderUtility
												.includeDefaultFormPattern(formPatternXmlName);
							}

							formPatternDoc = zkBuilderUtility.getFormPatternXmlDoc(
									application, page
											.attributeValue(ZKConstants.ELEMENT_ID));
							// ClientScriptBuilder.createDocList(formPatternDoc);

							// RendererComponent.logger.log(0,"The form pattern xml document, name = "
							// +formPatternXmlName);
							// formPatternDoc =
							// zkBuilderUtility.getFormPatternXmlDoc(application,
							// formPatternXmlName);
							if (null == formPatternDoc)
							{
								RendererComponent.logger.log(0,"The form pattern xml document, name = "
												+ formPatternXmlName + " not found");
								return;
							}

							if (application.getApplicationName().equalsIgnoreCase(
									"maintenance"))
							{

								// monitDBComponent.getRegisterWindow().registerBaseWindow(doc,application.getApplicationName());
							}
							else if (application.getApplicationName().equalsIgnoreCase(
									"BillingTracking"))
							{

								// bTDBComponent.getRegisterWindow().registerBaseWindow(doc,application.getApplicationName());
							}
							else
							{

								dbComponent.getRegisterWindow().registerBaseWindow(
										formPatternDoc, application.getApplicationName());
							}
						}
					}

					if (lst.size() > 0)
					{
						root = (org.dom4j.Element) lst.get(0);
						lst = root.elements(ZKConstants.FORM_PATTERN_TAG_NAME);

					}

					String connectionStr[] = RenderingUtility.getConnectionSettings(application.getApplicationFolderPath()
									+ ZKConstants.DATA_SETTINGS_PATH);
					if (connectionStr != null)
					{
						serverName = connectionStr[0];
						userName = connectionStr[2];
						password = connectionStr[3];
					}

					for (int count = 0; count < dataPatternArray.size(); count++)
					{
						// Creating Database Resources
						dataPatternName = dataPatternArray.get(count);
						// RendererComponent.logger.log(0,"datapatternArray-----------"+dataPatternArray.get(count));
						if (application.getApplicationName().equalsIgnoreCase(
								"maintenance"))
						{

							// monitDBComponent.createDatabaseResources(userName,
							// password, serverName,
							// application.getBaseDirectoryPath(),
							// dataPatternName, createDBResources,
							// "resources_maintenance.jar");
							// monitDBComponent
						}
						else if (application.getApplicationName().equalsIgnoreCase(
								"BillingTracking"))
						{

							// bTDBComponent.createDatabaseResources(userName,
							// password, serverName,
							// application.getBaseDirectoryPath(),
							// dataPatternName, createDBResources ,
							// "resources_bt.jar");

						}
						else
						{

							dbComponent.createDatabaseResources(userName, password,
									serverName, application.getBaseDirectoryPath(),
									dataPatternName, createDBResources);
							// dbComponent.getDataHandler().connectionSettings("root","1234",
							// "localhost","GIPTime","C:/apache-tomcat-5.5.20/common/lib/ext");
							dbComponent.getDataHandler().connectionSettings(userName,
									password, serverName,
									application.getApplicationName(),
									application.getBaseDirectoryPath());
						}

					}

				}

			}

		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw (new RendererComponentException(exp.getMessage()));
		}
		RendererComponent.logger.log(0,"***************end dataconnection*******************");

	}*/

	/*
	 * public DataUnit getGridData(DataUnit requestData) throws
	 * RendererComponentException {RendererComponent.logger.log(0,
	 * "******************this is getGridData class in modeler*****************"
	 * ); String retGridData = ""; List outputGrid=null; DataUnit data=null;
	 * String gridId = requestData.getElementId(); //String queryStr =
	 * (String)requestData.get("gridquery");
	 * 
	 * String currentBaseWindowName = requestData.getFormId();
	 * 
	 * if(dbComponent==null) { dbComponent=this.getInstance(); //
	 * dbComponent=new DBComponent(); getConnection(); dataHandler =
	 * dbComponent.getDataHandler();
	 * 
	 * } else { dataHandler = dbComponent.getDataHandler(); }
	 * 
	 * if(dataHandler!=null) { RendererComponent.logger.log(0,"dataHandler NOT NULL"+
	 * dataHandler.toString()); try {
	 * 
	 * 
	 * if (null != gridId && null != currentBaseWindowName) outputGrid =
	 * dataHandler.getGridList(gridId, currentBaseWindowName); data=new
	 * DataUnit(); if (null != outputGrid) data.setList(outputGrid); //
	 * System.out
	 * .println("value of output Grid size-------------"+outputGrid.size());
	 * //System
	 * .out.println("value of output Grid value-------------"+outputGrid.
	 * get(0));
	 * 
	 * 
	 * / Iterator resultList = outputGrid.iterator();
	 * 
	 * while(resultList.hasNext()) {
	 * RendererComponent.logger.log(0,"list value is "+resultList.next());
	 * 
	 * //Object obj = (Object)resultList.next(); //if (obj.getClass().isArray())
	 * // RendererComponent.logger.log(0,"****value of object---------------------"+obj);
	 * //System
	 * .out.println("****value of object------------666---------"+obj.getClass
	 * ().getName()); }
	 * 
	 * 
	 * 
	 * } catch(Exception exp) { throw newRendererComponentException(
	 * "!!!!!! Error occurred while getting the grid data********" +
	 * exp.getMessage()); } }
	 * 
	 * 
	 * return data; }
	 */
	/**
	 * This method is used to save the value of the form
	 * 
	 * @throws RendererComponentException
	 */
//	@EventSubscriber(topic = "moveForm")
//	public IHICData move(IHICData hicData)
//	{
//		RendererComponent.logger.log(0,"-------------in side move method in rendering component------------------");
//		IData data = hicData.getData();
//		IFormPattern formPattern = data.getFormPattern();
//		String formId = formPattern.getFormId();
//		RendererComponent.logger.log(0,"-----in side move---"+formId);
//		formId = formId+ ZKConstants.ZK_EXTN;
//		RendererComponent.logger.log(0,"-----in side move---"+formId);
//		Executions.sendRedirect(formId);
//		
//		return hicData;
//	}
//	
//	
	/*
	public IDataUnit saveForm(IDataUnit requestData) throws RendererComponentException
	{
		IDataUnit dataUnit = null;
		String windowFormID = requestData.getFormId();
		// RendererComponent.logger.log(0,"Window FormPattern form ID is   "+windowFormID);

		if (dbComponent == null)
		{
			dbComponent = this.getInstance();
			dataHandler = dbComponent.getDataHandler();
		}
		else
		{
			dataHandler = dbComponent.getDataHandler();
		}

		try
		{
			// RendererComponent.logger.log(0,"1111Inside the save method in the base modeler ");
			Object objId;
			dataHandler = dbComponent.getDataHandler();
			// RendererComponent.logger.log(0,"2222Inside the save method in the base modeler ");
			if (null != windowFormID && null != requestData.getFormValues())
				objId = dataHandler.save(windowFormID, requestData.getFormValues(), "");
			// RendererComponent.logger.log(0,"33333Inside the save method in the base modeler ");
		}
		catch (DBComponentException exp)
		{

			throw new RendererComponentException("Error Occurred while saving the form "
					+ exp.getMessage());

		}
		return dataUnit;
	}
*/
	public static void main(String[] argc) throws RendererComponentException
	{

		RendererComponent.logger.log(0,"Test Class");
		BaseModeler baseModeller = new BaseModeler();
		baseModeller.application = new Application();
		baseModeller.application.setApplicationFolderPath("c:\\comboitem\\");
		baseModeller.application.setApplicationName("comboitem");
		baseModeller.application.setApplicationFileName("comboitem.esp");
		baseModeller.application
				.setBaseDirectoryPath("C:\\apache-tomcat-5.5.20\\common\\lib\\ext\\");
		IDataUnit requestData = new DataUnit();
		// QueryData query = new QueryData();

		// query.setCondition("get htable.id,htable.name,htable.address from htable conditions htable.id:="+1+" and htable.name like '%m'");
		// query.setIdField("parent_id");
		// requestData.setQueryData("select state.stateid , state.statename , country.countryid  from State1 as state , Country1 as country where country.countryid = '2' ");
		requestData
				.setSqlQuery("select doctor.id,doctor.name,doctor.fees from Doctor as doctor");
		requestData.setParentId("/com/oxymedical/component/jasperReport/doctor.jasper");
		//Object jasper = new Jasperreport();
	//	requestData.setDataObject(jasper);
		// String
		// output="<records><record address="rrrrrrrr" name="rrr" id="33333333"/><record address="delhi" name="amit" id="33"/><record address="Lucknow" name="Rohan" id="30"/><record address="delhi" name="wasim" id="1"/></records>"
		// baseModeller.getListData(requestData);
		// requestData.setElementId("grid3");
		// requestData.setFormId("formpattern0");
	//	baseModeller.getListValue(requestData);

	}

	
	/*public IDataUnit getList(IDataUnit requestData) throws RendererComponentException
	{

		com.oxymedical.core.renderdata.IDataUnit dataUnit = new com.oxymedical.core.renderdata.DataUnit();
		QueryData queryResponseData = new QueryData();
		String queryStr = requestData.getQueryData().getCondition();
		// colOrder=requestData.getColumnOrder();
		Hashtable<String, List<String>> responseRowValues = new Hashtable<String, List<String>>();

		String responseXML = null;
		boolean exception = false;

		RendererComponent.logger.log(0,"***********************in side getTreeNodes of BM ***********************queryStr"
						+ queryStr);

		if (dbComponent == null)
		{
			// Commented by HS
			 dbComponent=this.getInstance();
			//dbComponent = new DBComponent();
			getConnection();
			dataHandler = dbComponent.getDataHandler();
			RendererComponent.logger.log(0,"dbComponent of getTreeNodes" + dbComponent);

		}
		else
		{
			dataHandler = dbComponent.getDataHandler();
		}

		if (dbComponent != null)
		{
			if (dataHandler != null)
			{
				try
				{
					try
					{
						responseXML = dataHandler.executeQuery(queryStr);
						RendererComponent.logger.log(0,"output-------" + responseXML);
					}
					catch (NullPointerException exp)
					{
						RendererComponent.logger.log(0,"No values exist for query string"
								+ exp.getMessage());

						exception = true;

					}
					if (!exception)
					{
						// retrieve tree XML data
						responseRowValues = retrieveRowValuesFromXML(responseXML,
								requestData);

						queryResponseData.setRowValues(responseRowValues);
						
						dataUnit.setQueryData(queryResponseData);
					}
				}
				catch (DBComponentException exp)
				{
					throw new RendererComponentException(
							"!!!!! Error occurred while executing the the action query "
									+ exp.getMessage());
				}
			}
			else
			{
				throw new RendererComponentException("Get List failed.");
			}
		}
		else
		{
			throw new RendererComponentException(
					"Get List failed. Database Component not found.");
		}
		return dataUnit;
	}

	public DataUnit getListValue(IDataUnit requestData2) throws RendererComponentException
	{
		List value = null;

		DataUnit dataUnit = new DataUnit();

		String queryStr = requestData2.getSqlQuery();
		Object dataObject = requestData2.getDataObject();
		String jasperFile = requestData2.getParentId();
		RendererComponent.logger.log(0,"data object value is   " + dataObject);

	//	Jasperreport jasperReport = (Jasperreport) dataObject;

		RendererComponent.logger.log(0,"***********************Inside base modeleter printmethod getListValue ***********************queryStr"
						+ queryStr);

		if (dbComponent == null)
		{

			dbComponent = new DBComponent();
			getConnection();
			dataHandler = dbComponent.getDataHandler();
			RendererComponent.logger.log(0,"dbComponent of getTreeNodes" + dbComponent);

		}
		else
		{
			dataHandler = dbComponent.getDataHandler();
		}

		if (dbComponent != null)
		{
			if (dataHandler != null)
			{
				try
				{
					try
					{
						// value = (List)
						// dataHandler.executeQueryList(queryStr);
						value = (List) dbComponent.getList(queryStr);
						RendererComponent.logger.log(0,"dbComponent of  getList " + value);
						Map parameters = new HashMap();
						parameters.put("Title", "Report");

						// InputStream reportStream =
						// this.getClass().getResourceAsStream("state1.xml");
						// RendererComponent.logger.log(0,"after input stream file "+reportStream);
						// JasperDesign jasperDesign =
						// JasperManager.loadXmlDesign(reportStream);
						// JasperReport jasperReport =
						// JasperManager.compileReport(jasperDesign);

						int getIndex = queryStr.indexOf("select");
						int fromIndex = queryStr.indexOf("from");
						// QueryData reques tData = new QueryData();
						String idValue = null;
						StringBuffer idField = new StringBuffer();
						int colKey = 0;

						String[] paramValues = queryStr
								.substring(getIndex + 6, fromIndex).trim().split(",");
						String[] fields = new String[paramValues.length];
						RendererComponent.logger.log(0,"index of get" + getIndex);
						RendererComponent.logger.log(0,"index of from" + fromIndex);

						for (int i = 0; i < paramValues.length; i++)
						{
							String[] eachParam = paramValues[i].split("\\.");
							for (int j = 0; j < eachParam.length; j++)
							{
								if (j == 0)
								{
									idValue = eachParam[1];
									fields[colKey] = idValue;
									RendererComponent.logger.log(0,"fields value is "
											+ fields[colKey]);
									colKey++;
								}
							}

						}

						// JasperDesign jasperDesign =
						// JRXmlLoader.load(reportStream);
						// JasperReport jasperReport =
						// JasperCompileManager.compileReport(jasperDesign);
						// String[] fields1 = new String[] { "id" , "name"
						// ,"fees"};
					//	HibernateQueryResultDataSource ds = new HibernateQueryResultDataSource(
								value, fields);
						jasperReport.setSrc(jasperFile);
						jasperReport.setParameters(parameters);
						jasperReport.setDatasource(ds);

						// JRBeanCollectionDataSource ds = new
						// JRBeanCollectionDataSource(value);
						// JasperPrint jasperPrint =
						// JasperManager.fillReport(jasperReport, parameters,
						// ds);
						// JasperPrint jasperPrint =
						// JasperFillManager.fillReport(jasperReport,
						// parameters, ds);
						RendererComponent.logger.log(0,"before printing the file");
						// JasperViewer.viewReport(jasperPrint);
						// RendererComponent.logger.log(0,"After printing the file");
						// JasperManager.printReportToPdfFile(jasperPrint,
						// "state-report.pdf");
						// byte[] buf =
						// JasperRunManager.runReportToPdf("state1.jasper",
						// parameters , ds);
						// report.setSrc("state1.jasper");
						// report.setParameters(parameters);
						// report.setDatasource((JRDataSource) jasperPrint);
						// JasperExportManager.exportReportToPdfFile(jasperPrint,
						// "state-report.pdf");
						// responseXML =dataHandler.executeQuery(queryStr);

					}
					catch (Exception ex)
					{
						ex.printStackTrace();
						RendererComponent.logger.log(0,"No values exist for query string"
								+ ex.getMessage());

						// exception = true;

					}
					// if(!exception){
					// retrieve tree XML data
					// responseRowValues =
					// retrieveRowValuesFromXML(responseXML,requestData);

					// queryResponseData.setRowValues(responseRowValues);
					// dataUnit.setQueryData(queryResponseData);
					// }
				}
				catch (Exception exp)
				{
					throw new RendererComponentException(
							"!!!!! Error occurred while executing the the action query "
									+ exp.getMessage());
				}
			}
			else
			{
				throw new RendererComponentException("Get List failed.");
			}
		}
		else
		{
			throw new RendererComponentException(
					"Get List failed. Database Component not found.");
		}
		return dataUnit;
	}

	public DataUnit getListData(DataUnit requestData) throws RendererComponentException
	{
		RendererComponent.logger.log(0,"******************in data list for delete**************************************");

		DataUnit dataUnit = new DataUnit();
		QueryData queryResponseData = new QueryData();
		String queryStr = requestData.getQueryData().getCondition();
		LinkedHashMap<String, LinkedHashMap<String, String>> colRecord = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		String responseXML = null;
		boolean exception = false;
		if (dbComponent == null)
		{
			// dbComponent=this.getInstance();
			dbComponent = new DBComponent();
			getConnection();
			dataHandler = dbComponent.getDataHandler();
			RendererComponent.logger.log(0,"dbComponent of getTreeNodes" + dbComponent);
		}
		else
		{
			dataHandler = dbComponent.getDataHandler();
		}
		if (dbComponent != null)
		{
			if (dataHandler != null)
			{
				try
				{
					try
					{
						responseXML = dataHandler.executeQuery(queryStr);
						RendererComponent.logger.log(0,"output-------" + responseXML);
					}
					catch (NullPointerException exp)
					{
						RendererComponent.logger.log(0,"No values exist for query string"
								+ exp.getMessage());

						exception = true;

					}
					if (!exception)
					{
						// retrieve tree XML data
						if (responseXML != null)
						{
							colRecord = retrieveValuesFromXML(responseXML, requestData);
							queryResponseData.setColOrderValues(colRecord);
							dataUnit.setQueryData(queryResponseData);
						}
					}
				}
				catch (DBComponentException exp)
				{
					throw new RendererComponentException(
							"!!!!! Error occurred while executing the the action query "
									+ exp.getMessage());
				}
			}
			else
			{
				throw new RendererComponentException("Get List Data failed.");
			}
		}
		else
		{
			throw new RendererComponentException(
					"Get List failed. Database Component not found.");
		}
		return dataUnit;
	}
*/
	/**
	 * Converts the XML tree-data string to retrieve tree-data
	 * 
	 * @param String
	 * @return Hashtable<String,String>
	 * 
	 */
	private Hashtable<String, List<String>> retrieveRowValuesFromXML(String responseXML,
			IDataUnit requestData2) throws RendererComponentException
	{

		Hashtable<String, List<String>> rowValues = new Hashtable<String, List<String>>();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		colOrder = requestData2.getColumnOrder();

		Hashtable<String, Hashtable<String, String>> colRecord = new Hashtable<String, Hashtable<String, String>>();

		DocumentBuilder parser = null;
		try
		{
			parser = factory.newDocumentBuilder();
		}
		catch (ParserConfigurationException e)
		{
			throw new RendererComponentException(
					"Unable to create DocumentBuilder object:" + e);
		}

		// Parse the file and build a Document tree to represent its content
		org.w3c.dom.Document document = null;
		try
		{
			document = parser.parse(new InputSource(new StringReader(responseXML)));
		}
		catch (SAXException e)
		{
			throw new RendererComponentException("Unable to parse document:"
					+ e.getMessage());
		}
		catch (IOException e)
		{
			throw new RendererComponentException("Unable to parse document:"
					+ e.getMessage());
		}

		org.w3c.dom.NodeList root = document.getElementsByTagName("records");
		org.w3c.dom.Node rootElem = root.item(0);
		org.w3c.dom.NodeList recordList = rootElem.getChildNodes();

		for (int i = 0; i < recordList.getLength(); i++)
		{
			org.w3c.dom.Node recordElem = recordList.item(i);
			org.w3c.dom.NamedNodeMap recordAttrList = recordElem.getAttributes();
			List<String> displayValues = new ArrayList<String>();
			String idValue = null;

			for (int j = 0; j < recordAttrList.getLength(); j++)
			{

				org.w3c.dom.Node attr = recordAttrList.item(j);

				if (attr.getNodeName().equals(requestData2.getQueryData().getIdField()))
				{
					RendererComponent.logger.log(0,"id-field name is:" + attr.getNodeValue());
					idValue = attr.getNodeValue();

				}
				else
				{
					RendererComponent.logger.log(0,"display-field :" + j + " is :"
							+ attr.getNodeValue());
					displayValues.add(attr.getNodeValue());
				}

			}
			// RendererComponent.logger.log(0,"idValue---------"+idValue);
			// RendererComponent.logger.log(0,"displayValues---------"+displayValues);

			rowValues.put(idValue, displayValues);
		}

		return rowValues;

	}

	private LinkedHashMap<String, LinkedHashMap<String, String>> retrieveValuesFromXML(
			String responseXML, DataUnit requestData) throws RendererComponentException
	{
		int colIndex = 0;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		colOrder = requestData.getColumnOrder();
		LinkedHashMap<String, LinkedHashMap<String, String>> colRecord = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		DocumentBuilder parser = null;
		try
		{
			parser = factory.newDocumentBuilder();
		}
		catch (ParserConfigurationException e)
		{
			throw new RendererComponentException(
					"Unable to create DocumentBuilder object:" + e);
		}

		// Parse the file and build a Document tree to represent its content
		org.w3c.dom.Document document = null;
		try
		{
			document = parser.parse(new InputSource(new StringReader(responseXML)));
		}
		catch (SAXException e)
		{
			throw new RendererComponentException("Unable to parse document:"
					+ e.getMessage());
		}
		catch (IOException e)
		{
			throw new RendererComponentException("Unable to parse document:"
					+ e.getMessage());
		}

		org.w3c.dom.NodeList root = document.getElementsByTagName("records");
		org.w3c.dom.Node rootElem = root.item(0);
		org.w3c.dom.NodeList recordList = rootElem.getChildNodes();

		for (int i = 0; i < recordList.getLength(); i++)
		{
			org.w3c.dom.Node recordElem = recordList.item(i);
			org.w3c.dom.NamedNodeMap recordAttrList = recordElem.getAttributes();
			LinkedHashMap<String, String> rowData = new LinkedHashMap<String, String>();
			colIndex = 0;
			for (int j = 0; j < recordAttrList.getLength(); j++)
			{
				colIndex = 0;
				org.w3c.dom.Node attr = recordAttrList.item(j);
				for (int k = 0; k < colOrder.size(); k++)
				{
					if (attr.getNodeName().equals(colOrder.get(colIndex)))
					{
						rowData.put(String.valueOf(colIndex), attr.getNodeValue());
						colIndex++;
					}
					else
					{
						colIndex++;
					}
				}
			}
			colRecord.put(String.valueOf(i), rowData);
		}
		return colRecord;
	}

	public Object invokecomponent(DataUnit requestData) throws RendererComponentException
	{

		String compId = null;
		String methodName = null;
		String className = null;
		ExternalData externaldata = null;
		RendererComponent.logger.log(0,"--------Present in Base Modular invoke Component method--------");
		RendererComponent.logger.log(0,"--------Present in Base Modular Check request data--------"
				+ requestData.getFormValues());
		Hashtable<String, Object> reqData = requestData.getFormValues();
		
		if (reqData != null)
		{
			compId = (String) reqData.get("componentid");
			methodName = (String) reqData.get("methodname");
			className = (String) reqData.get("classname");
			externaldata = (ExternalData) reqData.get("externaldata");
		}
		RendererComponent.logger.log(0,"------component id-------" + compId);
		RendererComponent.logger.log(0,"------methodName id-------" + methodName);
		RendererComponent.logger.log(0,"------className id-------" + className);
		RendererComponent.logger.log(0,"------externaldata Map-------" + externaldata);
		Class[] argClass = null;
		Object[] argValue = null;
		int paramCount = 0;
		try
		{
			com.oxymedical.component.baseComponent.IComponent comp = (com.oxymedical.component.baseComponent.IComponent) NOLISRuntime
					.getComponent(compId);
			Class retClass = Class.forName(className);
			if (null == retClass)
			{
				throw new RendererComponentException("No class found");
			}

			retClass.getClass().cast(comp.getClass());
RendererComponent.logger.log(0,"--------class from reflection---------"+retClass.getClass().getName());


			// paramXML =
			// "<param-list> <param> <type> string </type> <value> datapattern0 </value> </param> <param> <type> string </type> <value> task </value> </param> </param-list>";
			
			//paramXML = paramXML.substring(1, paramXML.length() - 1);

			/*Document document = DocumentHelper.parseText(paramXML);
			Element root = document.getRootElement();
			int paramSize = root.elements().size();*/
			LinkedList<Object> keysList = externaldata.getKeys();
			LinkedList<Object> valuesList = externaldata.getValues();
			int paramSize = keysList.size();
			argClass = new Class[paramSize];
			argValue = new Object[paramSize];
			
			
			Object[] keysArray =  keysList.toArray();
			Object[] valuesArray =  valuesList.toArray();
    		if((keysArray.length>0 & valuesArray.length>0)& (keysArray.length == valuesArray.length))
    		{
    			RendererComponent.logger.log(0,"------Present in if both have equal and somthing----keysArray--"+keysArray.length);
    			RendererComponent.logger.log(0,"------Present in if both have equal and somthing----valuesArray--"+valuesArray.length);
    		    
    			for (int i = 0;i<paramSize;i++)
    			{
    				RendererComponent.logger.log(0,"------Present in for Loop----KeysArray--"+keysArray[i]);
    				RendererComponent.logger.log(0,"-----Present in for Loop----valuesArray--"+valuesArray[i]);
    				if (keysArray[i].toString().equalsIgnoreCase("string"))
    				{
    					RendererComponent.logger.log(0,"--------Class is String------");
    					argClass[i] = String.class;
    				}
    				else if (keysArray[i].toString().equalsIgnoreCase("int"))
    				{
    					RendererComponent.logger.log(0,"--------Class is int------");
    					argClass[i] = int.class;
    				}
    				else if (keysArray[i].toString().equalsIgnoreCase("float"))
    				{
    					RendererComponent.logger.log(0,"--------Class is float------");
    					argClass[i] = float.class;
    				}
    				argValue[i] = valuesArray[i].toString();
    				RendererComponent.logger.log(0,"------getting values------"+argValue[i]);
    			}
    			Method m = comp.getClass().getMethod(methodName, argClass);
    			RendererComponent.logger.log(0,"------getting method from needed component------" + m);
    			
    			m.invoke(comp, argValue);
    		}
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
			throw new RendererComponentException(
					"No such method found in Invoke component");
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
			throw new RendererComponentException(
					"IllegalAccessException in Invoke Component");
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
			throw new RendererComponentException(
					"InvocationTargetException in Invoke Component");
		}
		/*catch (org.dom4j.DocumentException e)
		{
			throw new RendererComponentException("DocumentException in Invoke Component");
		}*/
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
			throw new RendererComponentException(
					"ClassNotFoundException in Invoke Component");
		}
		return null;
	}

	public void setApplication(Application application)
	{
		this.application = application;
	}

	public void setDataList(List dataList)
	{
		this.dataList = new ArrayList();
		this.dataList = dataList;
	}

	@Override
	public IDBComponent getInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}