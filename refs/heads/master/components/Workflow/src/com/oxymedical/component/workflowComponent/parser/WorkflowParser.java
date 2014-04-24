package com.oxymedical.component.workflowComponent.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.oxymedical.component.workflowComponent.IWorkflowParser;
import com.oxymedical.component.workflowComponent.WorkflowComponent;
import com.oxymedical.component.workflowComponent.constants.WorkflowConstant;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.nodeBuilder.DataObjectAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.DataPatternAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.EventAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.FormPatternAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.IDataObjectAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.IDataPatternAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.IEventAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.IFormPatternAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.IUserPatternAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.IWorkflowAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.IWorkflowNodeStatus;
import com.oxymedical.component.workflowComponent.nodeBuilder.NodeAttribute;
import com.oxymedical.component.workflowComponent.nodeBuilder.UserPatternAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.WorkflowAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.WorkflowNodeStatus;
import com.oxymedical.component.workflowComponent.nodeConnectorBuilder.INodeConnector;
import com.oxymedical.component.workflowComponent.nodeConnectorBuilder.NodeConnector;
import com.oxymedical.component.workflowComponent.nodeInfo.NodeObject;
import com.oxymedical.component.workflowComponent.nodeInfo.WorkflowNodeCollection;
import com.oxymedical.component.workflowComponent.processManager.WorkflowProcessManager;
import com.oxymedical.component.workflowComponent.workflowManager.WorkflowManager;
import com.oxymedical.core.commonData.Application;




public class WorkflowParser implements IWorkflowParser {
	private String applicationFileName;
	private String applicationFolderPath;
	private String baseDirectoryPath;
//	private String applicationName;
	SAXReader xmlReader = null;
	IEventAttributes eventAttribute = null;
	IFormPatternAttributes formPatternAttribute = null;
	NodeAttribute nodeAttribute = null;
	IUserPatternAttributes userPatternAttribute = null;
	IWorkflowAttributes workflowAttr = null;
	IDataPatternAttributes dataPatternAttribute = null;
	IDataObjectAttributes dataObjectAttribute = null;
	INodeConnector nodeConnectorObject =null;
	// String key is node id and node manager is the collection of node attribute
	Hashtable<String, NodeObject> nodeManagerCollection = null;
	//Hashtable<String, NodeConnector> nodeConnectorTable = null;
	
	List<INodeConnector> nodeConnectorList = null;
	NodeObject nodeManager = null;
	WorkflowNodeCollection workflowNodeCollection =null;
	NodeConnector nodeConnector =null;
	Document document = null;
	ParserUtility parserUtility = null;
	WorkflowProcessManager workflowProcess = null;
	private boolean isWorkFlowPresent = false; 
	
	public WorkflowParser() {
		
		workflowNodeCollection = new WorkflowNodeCollection();
		xmlReader = new SAXReader();
		parserUtility = new ParserUtility();
		workflowProcess = new WorkflowProcessManager();

	}

	/**
	 * This method receive application object,which have all information about appliaton
	 * as appliation name , application path, This method read esp application file and 
	 * takes workflow pattern, if defined. Then calls readProcessDefnXML method.
	 *  
	 * @param Application application
	 * @return WorkflowNodeCollection object
	 * @throws WorkflowComponentException
	 */
	public WorkflowNodeCollection parseProcessDefnXML(Application application) throws WorkflowComponentException {
		//applicationFileName = application.getApplicationFileName();
		applicationFileName = application.getApplicationFileName();
		applicationFolderPath = application.getApplicationFolderPath();
		baseDirectoryPath = application.getBaseDirectoryPath();
		if(applicationFileName==null || applicationFolderPath==null || applicationFileName.trim()=="" || applicationFolderPath.trim()=="")
		{
			throw (new WorkflowComponentException("Application source not found"));
		}
		if(baseDirectoryPath==null || baseDirectoryPath.trim()=="")
		{
			throw (new WorkflowComponentException("Base Directory Path not found"));
		}

		else
		{
			try {
				String applicationFile = applicationFolderPath + applicationFileName;
				WorkflowComponent.logger.log(0,"--------Present in ParseProcessDefXml--check AppName---"+applicationFileName);
				//String file = applicationFolderPath + "/" +applicationName + ".xml";
				document = this.parseUsingSAXReader(applicationFile);
				Element root = document.getRootElement();
				//logger.debug("root"+root.getName());
				if(root.getName().trim().equalsIgnoreCase(WorkflowConstant.APPLICATION_ROOT_NAME))
				{
					Element workflowPattern = root.element(WorkflowConstant.WORKFLOWPATTERN);
					List<Element> workflowPatternList = workflowPattern.elements();
					WorkflowComponent.logger.log(0,"---Work Flow pattern are present or not check size----"+workflowPatternList.size());
					if ( null != workflowPatternList && workflowPatternList.size() > 0)
					{
							WorkflowComponent.logger.log(0,"-----Started with workflowList-to process------");
							WorkflowComponent.logger.log(0,"---set boolean of work flow true-- ");
						isWorkFlowPresent = true;
						int counterVariable =0;
						for(counterVariable=0; counterVariable<workflowPatternList.size(); counterVariable++)
						{
							nodeManagerCollection = new Hashtable<String, NodeObject>();
							nodeConnectorList = new ArrayList<INodeConnector>();							
							Element page = (Element) workflowPatternList.get(counterVariable);
							String workflowXmlName = page.attributeValue(WorkflowConstant.ID);
							String workflowPatternDoc = applicationFolderPath +WorkflowConstant.WORKFLOW_FOLDER_NAME+WorkflowConstant.FORWARDSLASH+ workflowXmlName + WorkflowConstant.WORKFLOW_EXTN;
							Document document = this.parseUsingSAXReader(workflowPatternDoc);
							this.readProcessDefnXML(document);
							workflowNodeCollection.setNodeCollection(workflowXmlName, nodeManagerCollection);
							workflowNodeCollection.setConnectorCollection(workflowXmlName, nodeConnectorList);
							WorkflowComponent.logger.log(0,"------------------i insideeeeeeeeee--------------"+counterVariable);
							WorkflowManager.currentActiveWorkflowId =workflowXmlName;
						}
						WorkflowComponent.logger.log(0,"------------------i counter--------------"+counterVariable);
						System.out.println("--- workflow are defined and their count=-- "+counterVariable);

						
					}
					else
					{
						WorkflowComponent.logger.log(0,"**************Workflow Pattern is not define* " +
								"just remove all node collection present***************");
						isWorkFlowPresent = false;
						WorkflowComponent.logger.log(0,"---set boolean of work flow false-- ");
						System.out.println("---No workflow defined set boolean of work flow false-- ");
					}
				}
				
			}catch (WorkflowComponentException doc)
			{
				throw new WorkflowComponentException(doc.getMessage());
			}
			catch (DocumentException doc)
			{
				throw new WorkflowComponentException(doc.getMessage());
			}


		}
		return workflowNodeCollection;
	}

	/**
	 * This method takes workflow xml file and retun xml document file;
	 *  
	 * @param String fileName
	 * @return Document
	 * @throws WorkflowComponentException
	 */
	
	
	
	public Document parseUsingSAXReader(String fileName) throws WorkflowComponentException
	{

		FileInputStream fin = null;
		Document document = null;
		try {
			fin = new FileInputStream(fileName);
			InputStreamReader inR = new InputStreamReader(fin);
			xmlReader = new SAXReader();
			document = (org.dom4j.Document)xmlReader.read(inR);
		} catch (FileNotFoundException exp) {
			exp.printStackTrace();
			throw new WorkflowComponentException(exp.getMessage());
		} catch (DocumentException exp) {
			throw new WorkflowComponentException(exp.getMessage());
		} 
		return document;
	}

	public void parseProcessDefnXML(File processDefnDocument) {

	}

	/**
	 * This method takes workflow Document xml file and read complete
	 * process xml and store all information in object related to their class.
	 *  
	 * @param Document document
	 * @return void
	 * @throws DocumentException
	 */
	
	
	@SuppressWarnings("unchecked")
	public void readProcessDefnXML(Document document) throws DocumentException,WorkflowComponentException {

		String workflowWindowName = null;
		String workflowWindowId = null;
		Element root = document.getRootElement();
		//Root element is application name
		WorkflowComponent.logger.log(0,"===========root name----------"+ root.getName());

		for(Iterator<Attribute> rootCounter = root.attributeIterator(); rootCounter.hasNext(); ) 
		{
			Attribute attribute = (Attribute) rootCounter.next();
			if(attribute != null && attribute.getName().equals(WorkflowConstant.WORKFLOW_WINDOWNAME))
			{
				workflowWindowName = attribute.getValue();
			}
			if(attribute != null && attribute.getName().equals(WorkflowConstant.WORKFLOW_WINDOWID))
			{
				workflowWindowId = attribute.getValue();
			}

		}
		WorkflowComponent.logger.log(0,"-------workflowwindow name----------" + workflowWindowName);
		WorkflowComponent.logger.log(0,"-------workflowWindowId ----------" + workflowWindowId);

		List<Element> nodes = root.elements(WorkflowConstant.WORKFLOWNODE);
		WorkflowComponent.logger.log(0,"------------------------------node---------------------------------");
		for(Iterator i = nodes.iterator(); i.hasNext(); )
		{		
			WorkflowComponent.logger.log(0,"------------------------in side node---------------------------------------");
			String nodeName = null;
			String nodeId = null;
			String isStart = null;
			String scenario = null;
			nodeAttribute = new NodeAttribute();
			eventAttribute = new EventAttributes();
			nodeManager = new NodeObject();
			Element nodeElement = (Element) i.next();
			// iterate through attributes of module 
			nodeName = nodeElement.attributeValue(WorkflowConstant.WORKFLOW_NODENAME);
			nodeId = nodeElement.attributeValue(WorkflowConstant.WORKFLOW_NODEID);
			isStart = nodeElement.attributeValue(WorkflowConstant.ISSTART);
			scenario = nodeElement.attributeValue(WorkflowConstant.SCENARIO);

			if(nodeId != null && isStart != null && scenario !=null) {
				nodeAttribute.setNodeId(nodeId);
				nodeAttribute.setIsStart(isStart);
				nodeAttribute.setScenario(scenario);				
				nodeManager.setNodeAttributes(nodeAttribute);
			}
			if(isStart.equalsIgnoreCase("True"))
			{
				WorkflowManager.currentActiveNodeId = nodeId;
			}
			WorkflowComponent.logger.log(0,"nodeId *********= " + nodeId);
			WorkflowComponent.logger.log(0,"node name********** = " + nodeName);
			WorkflowComponent.logger.log(0,"isStart *********= " + isStart);
			WorkflowComponent.logger.log(0,"scenario********** = " + scenario);

			for (Iterator m = nodeElement.elementIterator(); m.hasNext(); )
			{
				Element nodeChildElement = (Element) m.next();
				if(nodeChildElement.getName().equals(WorkflowConstant.WORKFLOW_NODEEVENT))
				{
					for(Iterator eventIterate = nodeChildElement.elementIterator(); eventIterate.hasNext(); )
					{

						Element element = (Element) eventIterate.next();
						WorkflowComponent.logger.log(0,"-----------------this is event block-------------------");
						String eventName = null;
						eventName = element.attributeValue(WorkflowConstant.NAME);
						String eventValue = element.getStringValue();
						if(eventName.equalsIgnoreCase(WorkflowConstant.ONNODE)) {
							eventAttribute.setOnNode(eventValue);
						} 
						if(eventName.equalsIgnoreCase(WorkflowConstant.ENTERNODE)) {
							eventAttribute.setOnEnter(eventValue);
						} 
						if(eventName.equalsIgnoreCase(WorkflowConstant.EXITNODE)) {
							eventAttribute.setOnExit(eventValue);
						} 

						WorkflowComponent.logger.log(0,"---------------------eventValue------------- ");
						WorkflowComponent.logger.log(0,"eventValue1----getOnEnter----- = " + eventAttribute.getOnEnter());
						WorkflowComponent.logger.log(0,"eventValue---------getOnExit---- = " + eventAttribute.getOnExit());
						WorkflowComponent.logger.log(0,"3---------getOnNode--- = " + eventAttribute.getOnNode());


					}
					nodeManager.setEventAttributeObject(eventAttribute);
				}

				if(nodeChildElement.getName().equals(WorkflowConstant.WORKFLOW_FORMPATTERNS))
				{
					String formId = null;
					for(Iterator formIterate = nodeChildElement.elementIterator(); formIterate.hasNext(); )
					{
						formPatternAttribute = new FormPatternAttributes();
						Element element = (Element) formIterate.next();
						formId = element.attributeValue(WorkflowConstant.FORMPATTERNID);
						formPatternAttribute.setFormPatternId(formId);
						nodeManager.addFormPatternObject(formPatternAttribute);
					}
					WorkflowComponent.logger.log(0,"formId 33333333333= " + formId);
				}
				if(nodeChildElement.getName().equals(WorkflowConstant.WORKFLOW_DATAPATTERN))
				{
					String dataPatternId = null;
					for(Iterator formIterate = nodeChildElement.elementIterator(); formIterate.hasNext(); )
					{
						dataPatternAttribute = new DataPatternAttributes();
						Element element = (Element) formIterate.next();
						dataPatternId = element.attributeValue(WorkflowConstant.DATAPATTERNID);
						dataPatternAttribute.setDataPatternId(dataPatternId);
						nodeManager.addDataPatternObject(dataPatternAttribute);
					}
					WorkflowComponent.logger.log(0,"dataPatternId2222222222 = " + dataPatternId);
				}
				if(nodeChildElement.getName().equals(WorkflowConstant.WORKFLOW_DATAOBJECT))
				{
					String status = null;
					dataObjectAttribute = new DataObjectAttributes();
					for(Iterator dataObjectIterate = nodeChildElement.elementIterator(); dataObjectIterate.hasNext(); )
					{
						Element element = (Element) dataObjectIterate.next();
						if (element.getName().equals(WorkflowConstant.WORKFLOW_DATAOBJECT_STATUS))
						{
							status = element.getStringValue();
							dataObjectAttribute.setStatus(status);
							nodeManager.setDataObject(dataObjectAttribute);
						}
					}
					WorkflowComponent.logger.log(0,"dataObjectStatus2222222222 = " + status);
				}

				if(nodeChildElement.getName().equals(WorkflowConstant.WORKFLOW_USERPATTERN))
				{
					String userPatternId = null;
					for(Iterator formIterate = nodeChildElement.elementIterator(); formIterate.hasNext(); )
					{
						userPatternAttribute = new UserPatternAttributes();
						Element element = (Element) formIterate.next();
						userPatternId = element.attributeValue(WorkflowConstant.USERPATTERNID);
						userPatternAttribute.setUserPatternId(userPatternId);
						nodeManager.addUserPatternObject(userPatternAttribute); // .setUserPatternObject(userPatternAttribute);
					}
					WorkflowComponent.logger.log(0,"userPatternId111111111 = " + userPatternId);
				}
			}

			// For storing Workflow Node Status
			workflowAttr = new WorkflowAttributes();
			workflowAttr.setId(workflowWindowId);
			nodeManager.setWorkflowAttributes(workflowAttr);

			List nodeConnector = root.elements(WorkflowConstant.CONNECTOR);
			getMatchingNodesConnection(nodeConnector);
			
			nodeManagerCollection.put(nodeId, nodeManager);
		}

		List nodeConnector = root.elements(WorkflowConstant.CONNECTOR);
		this.readNodeConnectorXML(nodeConnector);
		
	}


	/**
	 * This method takes nodeConnector attribute list and 
	 * store all infomation in object class
	 *  
	 * @param List nodeConnector
	 * @return void
	 * @throws none
	 */

	@SuppressWarnings("unchecked")
	public void readNodeConnectorXML(List<Element> nodeConnector)
	{
		WorkflowComponent.logger.log(0,"------------------------connecotr--------------------------------------");
		for(Iterator<Element> connectorIterator = nodeConnector.iterator(); connectorIterator.hasNext(); )
		{		
			WorkflowComponent.logger.log(0,"------------------------in side nodeconnector---------------------------------------");
			nodeConnectorObject = new NodeConnector();
			Element nodeElement = (Element) connectorIterator.next();
			// iterate through attributes of module 
			for (Iterator<Element> m = nodeElement.elementIterator(); m.hasNext(); )
			{
				String fromnodeId = null;
				String toNodeId = null;
				Element element = (Element) m.next();
				if(element.getName().equals(WorkflowConstant.FROM))
				{
					fromnodeId = element.attributeValue(WorkflowConstant.ID);
					nodeConnectorObject.setFromNodeId(fromnodeId);
				}

				if(element.getName().equals(WorkflowConstant.TO))
				{
					toNodeId = element.attributeValue(WorkflowConstant.ID);
					nodeConnectorObject.setToNodeId(toNodeId);
				}

				WorkflowComponent.logger.log(0,"formnodeId= " + fromnodeId);
				WorkflowComponent.logger.log(0,"toNodeId= " + toNodeId);
			}
			//nodeConnectorTable.put(nodeConnectorId, nodeConnectorObject);
			nodeConnectorList.add(nodeConnectorObject);
		}
		
	}


	@SuppressWarnings("unchecked")
	private NodeObject getMatchingNodesConnection(List<Element> nodeConnector)
	{
		checkConnector:
		for(Iterator<Element> connectorIterator = nodeConnector.iterator(); connectorIterator.hasNext(); )
		{		
			WorkflowComponent.logger.log(0,"------------------------in side nodeconnector---------------------------------------");
			
			Element nodeElement = (Element) connectorIterator.next();
			String fromnodeId = null;
			String toNodeId = null;

			// iterate through attributes of module 
			for (Iterator<Element> m = nodeElement.elementIterator(); m.hasNext(); )
			{
				Element element = (Element) m.next();
				if(element.getName().equals(WorkflowConstant.FROM))
				{
					fromnodeId = element.attributeValue(WorkflowConstant.ID);
				}
				if (!fromnodeId.equals(nodeManager.getNodeAttributes().getNodeId())) continue checkConnector;
				
				if(element.getName().equals(WorkflowConstant.TO))
				{
					toNodeId = element.attributeValue(WorkflowConstant.ID);
				}

				WorkflowComponent.logger.log(0,"formnodeId= " + fromnodeId);
				WorkflowComponent.logger.log(0,"toNodeId= " + toNodeId);
			}

			if (fromnodeId.equals(nodeManager.getNodeAttributes().getNodeId()))
			{
				nodeConnectorObject = new NodeConnector();
				nodeConnectorObject.setFromNodeId(fromnodeId);
				nodeConnectorObject.setToNodeId(toNodeId);
				
				nodeManager.addNodeConnector(nodeConnectorObject);
			}
		}

		return nodeManager;
	}

	public boolean isWorkFlowPresent()
	{
		return isWorkFlowPresent;
	}

}
