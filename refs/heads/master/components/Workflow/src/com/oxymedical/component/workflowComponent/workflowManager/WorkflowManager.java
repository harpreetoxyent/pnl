package com.oxymedical.component.workflowComponent.workflowManager;


import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.oxymedical.component.logging.LoggingComponent;
import com.oxymedical.component.workflowComponent.WorkflowComponent;
import com.oxymedical.component.workflowComponent.command.tool.IObserver;
import com.oxymedical.component.workflowComponent.data.WorkflowObject;
import com.oxymedical.component.workflowComponent.dataObjectManager.DataObjectManager;
import com.oxymedical.component.workflowComponent.dataObjectManager.IDataObjectManager;
import com.oxymedical.component.workflowComponent.dataobject.util.DataObjectListUtil;
import com.oxymedical.component.workflowComponent.db.impl.WorkFlowNodeInfoImpl;
import com.oxymedical.component.workflowComponent.erl.ERLConstants;
import com.oxymedical.component.workflowComponent.erl.ERLCreator;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.nodeConnectorBuilder.INodeConnector;
import com.oxymedical.component.workflowComponent.nodeInfo.NodeObject;
import com.oxymedical.component.workflowComponent.nodeInfo.WorkflowNodeCollection;
import com.oxymedical.component.workflowComponent.nodeInfo.WorkflowNodeCollectionHelper;
import com.oxymedical.component.workflowComponent.nodemanager.NodeEventInfo;
import com.oxymedical.component.workflowComponent.nodemanager.NodeEvents;
import com.oxymedical.component.workflowComponent.nodemanager.NodeManager;
import com.oxymedical.component.workflowComponent.operation.db.NodeCollectionOperation;
import com.oxymedical.component.workflowComponent.operation.db.NodeConnectorCollectionOperation;
import com.oxymedical.component.workflowComponent.parser.WorkflowParser;
import com.oxymedical.component.workflowComponent.rule.RulesManager;
import com.oxymedical.core.commonData.Application;
import com.oxymedical.core.commonData.DataPattern;
import com.oxymedical.core.commonData.FormPattern;
import com.oxymedical.core.commonData.IApplication;
import com.oxymedical.core.commonData.IDataPattern;
import com.oxymedical.core.commonData.IFormPattern;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.core.router.IRouter;
import com.oxymedical.core.userdata.IUserPattern;
import com.oxymedical.core.userdata.UserPattern;


public class WorkflowManager implements IWorkflowManager 
{
	LoggingComponent logger = WorkflowComponent.logger;
	
	public static String currentActiveWorkflowId = null;
	public static String currentActiveNodeId = null;
	private WorkflowNodeCollection _workflowCollection = null;
	private IDataObjectManager doManager = null;
	private IObserver _toolCompletionObserver = null;
	private IRouter _router = null;
	private NodeManager _nodeManager = null;
	
	private WorkflowParser workflowParser = null;
	
	private HashMap<String, WorkflowNodeCollection> workflowMapWithApplication = new HashMap<String, WorkflowNodeCollection>();
	
	public WorkflowManager() 
	{
		doManager = new DataObjectManager();
		workflowParser = new WorkflowParser();
		_nodeManager = new NodeManager();
	}
	
	
	public WorkflowObject getWorkflow(String workflowName)
	{
		return null;
	}
	
	
	public synchronized void initializeWorkflow(IApplication app) throws WorkflowComponentException
	{
		if (app == null)
			throw (new WorkflowComponentException("Application is null"));
		
		String applicationName = app.getApplicationName();
		System.out.println("------Inside initializeWorkflow & workflowMapWithApplication=----"+workflowMapWithApplication);
		if (workflowMapWithApplication == null || !workflowMapWithApplication.containsKey(applicationName))
		{
			System.out.println("------Inside if loop and workflow found---");

			/*if (app == null)
			{
				throw new WorkflowComponentException(
						"!!!! HICData does not contain application information !!!!");
			}

			this._workflowCollection = WorkflowNodeCollectionHelper.createWorkFlowCollection();
			if (_workflowCollection != null)
			{
				workflowMapWithApplication.put(app.getApplicationName(),
						_workflowCollection);
			}
			else
			{
				workflowMapWithApplication.put(app.getApplicationName(), null);
			}*/
			
			setWorkflowCollectionForApplication(app);
			
			ERLCreator c = new ERLCreator();
			c.createERL(_workflowCollection, app);

			RulesManager.buildRules(null);
			
			// Register new components for each Node in all Workflows
			registerToolsComponent();
		}
	}
	
	
	public void setWorkflowCollectionForApplication(IApplication app) throws WorkflowComponentException
	{
		if (app == null)
		{
			throw new WorkflowComponentException(
					"!!!! HICData does not contain application information !!!!");
		}
		
		this._workflowCollection = WorkflowNodeCollectionHelper.createWorkFlowCollection();
		if (_workflowCollection != null)
		{
			workflowMapWithApplication.put(app.getApplicationName(),
					_workflowCollection);
		}
		else
		{
			workflowMapWithApplication.put(app.getApplicationName(), null);
		}
	}
	
	/**
	 * Register new components for each Node in all Workflows
	 * @throws WorkflowComponentException
	 */
	private void registerToolsComponent() throws WorkflowComponentException
	{
		/*logger.log(0, "------ REGISTERING TOOLS COMPONENT ------ STARTS -------");
		List outputList = WorkFlowNodeInfoImpl.getWorkflowNodeWithActionAndPackage();
		
		logger.log(0, "[Received outputList]" 
				+ (outputList == null ? " NULL " : "\tsize" + outputList.size()));
		
		if (outputList != null)
		{
			logger.log(0, "[outputList.size()]" + outputList.size());
			String[][] outputArr = QueryData.iterateListData(outputList);
			
			if (outputArr == null) return;
			for (int j = 0; j < outputArr.length; j++)
			{
				String workflowName = outputArr[j][0];
				String actionName = outputArr[j][1];
				String packageClass = outputArr[j][2];
				String nodeName = outputArr[j][3]; // Not currently used but might be used in future

				if ((actionName == null) || (packageClass == null)) continue;

				logger.log(0, 
						"[workflowName]" + workflowName 
						+ "\t[actionName]" + actionName 
						+ "\t[packageClass]" + packageClass 
						+ "\t[nodeName]" + nodeName);

				String componentId 
					= new StringBuffer(workflowName)
						.append(".").append(actionName)
						.append(".").append(packageClass).toString();

				// Register component with HIC
				NOLISRuntimeHandler.registerComponent(componentId, packageClass);
			}
		}
		logger.log(0, "------ REGISTERING TOOLS COMPONENT ------ ENDS -------");*/
	}
	
	
	public void startWorkflow(IApplication application) throws WorkflowComponentException
	{
		WorkflowNodeCollection flowCollection = workflowParser.parseProcessDefnXML((Application)application);
		System.out.println("---workflowCollection count=--"+flowCollection);
		if (workflowParser.isWorkFlowPresent())
		{
			System.out.println("---Inside workflowParser.isWorkFlowPresent()--");

			this.setWorkflowCollection(flowCollection);
			addWorkFlow(_workflowCollection.getConnectorCollection(), _workflowCollection.getNodeCollection());		
		}
	}
	
	private void addWorkFlow(Hashtable<String, List<INodeConnector>> connectorCollection ,Hashtable<String, Hashtable<String, NodeObject>> nodeCollection)
	{
		NodeCollectionOperation nodeCollectionOperation = new NodeCollectionOperation();
		nodeCollectionOperation.addNodeCollectionObjectToDatabase(nodeCollection); 
		NodeConnectorCollectionOperation nodeConnectorCollectionOperation = new NodeConnectorCollectionOperation();
		nodeConnectorCollectionOperation.addNodeCollectionObject(connectorCollection);
	}
	
	
	public WorkflowNodeCollection getWorkflowCollection() 
	{
		return _workflowCollection;
	}


	public void setWorkflowCollection(WorkflowNodeCollection workflowCollection) 
	{
		this._workflowCollection = workflowCollection;
	}
	
	public void updateWorkflowMap(IApplication app) throws WorkflowComponentException
	{
		workflowMapWithApplication = new HashMap<String, WorkflowNodeCollection>();
		initializeWorkflow(app);
	}
	
	public WorkflowNodeCollection getWorkflowCollection(IApplication app)
	{
		return workflowMapWithApplication.get(app.getApplicationName());
	}
	
	
	public void invokeWorkflow(IHICData dataObject) throws WorkflowComponentException
	{
		invokeWorkflow(dataObject, true);
	}
	
	public void invokeWorkflow(IHICData dataObject, boolean calledFromMainThread) throws WorkflowComponentException
	{
		List<String> ruleResultList = RulesManager.getRules(dataObject);
		System.out.println("==========ruleResultList size========="+ruleResultList.size());
		if (ruleResultList.size() <= 0) return;
		
		// Check the node on which this data object is present - DATA OBJECT MANAGER
		String appName = null;
		String workflowName = null;
		String nodeName = null;
		String prevNodeName = null;
		WorkflowNodeCollection currWfColl = null;
		NodeObject currNode = null;
		NodeObject prevNode = null;
		try
		{
			appName = dataObject.getApplication().getApplicationName();
			System.out.println("==========ruleResultList size========="+ruleResultList.size());
			currWfColl = workflowMapWithApplication.get(appName);
			System.out.println("==========currWfColl========="+currWfColl);
			
			workflowName = dataObject.getData().getWorkflowPattern().getWorkflowPattern();
			System.out.println("==========workflowName========="+workflowName);
			nodeName = dataObject.getData().getWorkflowPattern().getWorkflowNode();
			System.out.println("==========nodeName========="+nodeName);
			prevNodeName = dataObject.getData().getWorkflowPattern().getWorkflowPreviousNode();
			System.out.println("==========prevNodeName========="+prevNodeName);
			currNode = currWfColl.getNode(workflowName, nodeName);
			System.out.println("==========currNode========="+currNode);
			prevNode = currWfColl.getNode(workflowName, prevNodeName);
			System.out.println("==========prevNode========="+prevNode);
		}
		catch (NullPointerException npe) { }
		

		/*if (currNode != null)
		{
			_nodeManager.startNodeExecution(dataObject, currNode, null, this);
			if (_nodeManager.isStopExecution()) return;
		}*/
		
		boolean execContinue = true;
		
		try
		{
			if ((dataObject.getData().getWorkflowPattern() != null) 
					&& (dataObject.getData().getWorkflowPattern().getWorkflowNodeEventStatus() != null)
					&& (prevNode != null)
					&& (dataObject.getData().getWorkflowPattern().getWorkflowNodeEventStatus()
								.get(prevNode.getNodeAttributes().getNodeId() + "_" + NodeEvents.EXIT_NODE).equals(false))) // TODO EXIT_NODE should be replaced with Last node
				execContinue = false;
			System.out.println("==============inside try execContinue========00000==="+execContinue);
		}
		catch (NullPointerException npe) { }
		
		// Execute current node for only those DOs that do not contain Patient
		// and Schedule information since for such DOs, current node is executed
		// when they opt for updating their DO to next node.
		if (!DataObjectListUtil.isPatientSchedulePresentInFormValues(dataObject)) 
			execContinue = startNodeExecution(dataObject, ruleResultList);
		System.out.println("==============inside try execContinue======1111===="+execContinue);
		
		// Execute Next Node
		if (execContinue) execContinue = executeNextNode(dataObject, ruleResultList, calledFromMainThread);
		System.out.println("==============inside try execContinue========222222222==="+execContinue);
		
		//execContinue = true;
		// Continue execution further till next node. Only valid if node is an Action node.
		if (execContinue)
		{
			// One cycle is complete, start next cycle
			System.out.println("===================dataObject==========="+dataObject);
			System.out.println("===================before invokeWorkflowForNextNode===========");
			invokeWorkflowForNextNode(dataObject);
		}
		
		
		
		// Get all event types from NodeEvent with their execution order information. - NODE MANAGER
		
		// For current node, Loop each node event according to execution order - NODE MANAGER
		// {
			// Fire node event - NODE EVENT MANAGER
		
			// After completing execution of event, set event execution status appropriately. For thread commands, this status is set by ToolCompletionObserver. - NODE EVENT MANAGER / TOOL COMPLETION OBSERVER
		
			// Check event execution status of DO - NODE MANAGER / TOOL COMPLETION OBSERVER
		
			// If event execution status is set to "complete", check error - NODE MANAGER / TOOL COMPLETION OBSERVER
			
				// If event returns an error, send it to HangingObject List for that workflow - NODE MANAGER / TOOL COMPLETION OBSERVER, WORKFLOW MANAGER
				
				// If not - NODE MANAGER / TOOL COMPLETION OBSERVER
					// If it is LAST event in Execution List - NODE MANAGER / TOOL COMPLETION OBSERVER
						// Get out of the loop & Return to WorkflowManager for deciding next node and firing it. - NODE MANAGER / TOOL COMPLETION OBSERVER, WORKFLOW MANAGER
			
		// }

		
		// Calculate to which node should it go. - WORKFLOW MANAGER
		// This calculation is based on DataObject returned after exit event. - WORKFLOW MANAGER
		// This calculation should be based on Rules from Rule Component - WORKFLOW MANAGER
		// Ideally there should be only 1 destination node. - WORKFLOW MANAGER
		// Returns next Node - WORKFLOW MANAGER
		// If there is no next node according to rule component, - WORKFLOW MANAGER 
			// Get connected nodes to current node and take the first node. - WORKFLOW MANAGER
			// However, If there was no current node, set connected node to start node. - WORKFLOW MANAGER

		
		// Get all event types from NodeEvent with their execution order information.
		
		// For current node, Loop each node event according to execution order
		// {
			// Fire node event
		
			// After completing execution of event, set event execution status appropriately. For thread commands, this status is set by ToolCompletionObserver.
	
			// Check event execution status of DO
		
			// If event execution status is set to "complete", check error
			
				// If event returns an error, send it to HangingObject List for that workflow
				
				// If not 
					// If it is LAST event in Execution List
						// Get out of the loop & Return to WorkflowManager for deciding next node and firing it.
					// If it is FIRST event in Execution List
						// Update workflow / node information in DataObject - NODE MANAGER
					// If it is STOP_AT event in Execution List
						// If Node Type is Input
							// Get out of the loop - NODE MANAGER
			
		// }
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// Execute "exit" event of current node (if node present).
		
		// If "exit" event returns an error, send it to HangingObject List for that workflow
		
		// If "exit" DOES NOT event return an error, calculate to which node should it go.
		// This calculation is based on DataObject returned after exit event.
		
		// Ideally there should be only 1 destination node.
		
		// Fire the "enter" event of next node
		
		// If "enter" event returns an error, send it to HangingObject List for that workflow
		
		// If "enter" DOES NOT event return an error, make next node as current node.
		// set workflow information in DataObject pertaining to new current node 
		// fire "process" event of new current node.
		
		// If "process" event returns an error, send it to HangingObject List for that workflow
		
		// If "process" DOES NOT event return an error, calculate which type of node is new current node.
		// If current node is Action node, repeat the while process again.
		// If current node is Input node, stop execution here.
	}
	
	
	public void invokeWorkflowForNextNode(IHICData dataObject) throws WorkflowComponentException
	{
		invokeWorkflowForNextNode(dataObject, true);
	}
	
	public void invokeWorkflowForNextNode(IHICData dataObject, boolean calledFromMainThread) throws WorkflowComponentException
	{
		completeCurrentAndUpdateDOForNextNode(dataObject);
		invokeWorkflow(dataObject, calledFromMainThread);
	}
	
	public boolean startNodeExecution(IHICData dataObject, List<String> ruleResultList, boolean checkEqualityWithNextNode) throws WorkflowComponentException
	{
		// Check the node on which this data object is present - DATA OBJECT MANAGER
		String appName = null;
		String workflowName = null;
		String nodeName = null;
		WorkflowNodeCollection currWfColl = null;
		NodeObject currNode = null;
		
		try
		{
			appName = dataObject.getApplication().getApplicationName();
			currWfColl = workflowMapWithApplication.get(appName);
			
			workflowName = dataObject.getData().getWorkflowPattern().getWorkflowPattern();
			nodeName = dataObject.getData().getWorkflowPattern().getWorkflowNode();
			currNode = currWfColl.getNode(workflowName, nodeName);
		}
		catch (NullPointerException npe) { }
		
		// Get Next node
		NodeObject nextNode = this.getNextNode(dataObject, ruleResultList);
		
		// Start working on existing DOs present on Node and prepare for their exit from current node.
		Hashtable<String, Hashtable<String, List<IHICData>>> storedDOInfo = doManager.getMatchingDataObject(dataObject);
		if (storedDOInfo.size()>0)
		{
			Iterator<String> storedDOIterator = storedDOInfo.keySet().iterator();
			while (storedDOIterator.hasNext())
			{
				String storedWorkflowId = storedDOIterator.next();
				Hashtable<String, List<IHICData>> storedData = storedDOInfo.get(storedWorkflowId);
				Iterator<String> storedDOIteratorForNode = storedData.keySet().iterator();
				while (storedDOIteratorForNode.hasNext())
				{
					String storedNodeId = storedDOIteratorForNode.next();
					if (storedData.get(storedNodeId) != null)
					{
						List<IHICData> storedDOs = (List<IHICData>) storedData.get(storedNodeId);
						for (int j=0; j<storedDOs.size(); j++)
						{
							IHICData storedDO = storedDOs.get(j);
							currNode = currWfColl.getFirstNode(storedWorkflowId, storedNodeId);
							
//							WorkflowComponent.log(0, "[workflowName]" + workflowName);
//							WorkflowComponent.log(0, "[nodeName]" + nodeName);
//							WorkflowComponent.log(0, "[currWfColl.isLastNode(workflowName, nodeName)]" + currWfColl.isLastNode(workflowName, nodeName));
							
							if (currNode != null)
							{
								if (checkEqualityWithNextNode)
								{
//									if ((!currNode.equals(nextNode)))
									{
										boolean continueExec = executeCurrentNode(dataObject, storedDO, currNode);
										if (!continueExec) return continueExec;
										
										/*storedDO.getData().getFormPattern().setFormValues(dataObject.getData().getFormPattern().getFormValues());
										boolean continueExec = performNodeEventExecution(currNode, storedDO, null);
										dataObject.getData().getWorkflowPattern().setWorkflowNodeEventStatus(storedDO.getData().getWorkflowPattern().getWorkflowNodeEventStatus());
										if (!continueExec) return continueExec;*/
									}
								}
								else
								{
									boolean continueExec = executeCurrentNode(dataObject, storedDO, currNode);
									if (!continueExec) return continueExec;
									
									/*storedDO.getData().getFormPattern().setFormValues(dataObject.getData().getFormPattern().getFormValues());
									boolean continueExec = performNodeEventExecution(currNode, storedDO, null);
									dataObject.getData().getWorkflowPattern().setWorkflowNodeEventStatus(storedDO.getData().getWorkflowPattern().getWorkflowNodeEventStatus());
									if (!continueExec) return continueExec;*/
									
									/*_nodeManager.startNodeExecution(storedDO, currNode, null, this);
									if (_nodeManager.isStopExecution()) return;*/
								}
							}
						}
					}
				}
			}
		}
		return true;
	}

	
	private boolean executeCurrentNode(IHICData dataObject, IHICData storedDO, NodeObject currNode) throws WorkflowComponentException
	{
		storedDO.getData().getFormPattern().setFormValues(dataObject.getData().getFormPattern().getFormValues());
		boolean continueExec = performNodeEventExecution(currNode, storedDO, null);

		if ( (dataObject.getData() != null) && (dataObject.getData().getWorkflowPattern() != null)
				&& (storedDO.getData() != null) && (storedDO.getData().getWorkflowPattern() != null))
		dataObject.getData().getWorkflowPattern().setWorkflowNodeEventStatus(storedDO.getData().getWorkflowPattern().getWorkflowNodeEventStatus());
		
		return continueExec;
	}
	
	
	public boolean startNodeExecution(IHICData dataObject, List<String> ruleResultList) throws WorkflowComponentException
	{
		// If current node is last node, then also fire Exit method.
		/*NodeObject nextNode = null;
		try
		{
			nextNode = getWorkflowCollection(dataObject.getApplication())
							.getNextNodes(
									dataObject.getData().getWorkflowPattern().getWorkflowPattern(), 
									dataObject.getData().getWorkflowPattern().getWorkflowNode())
										.get(0);
		}
		catch (NullPointerException e1) { }
		return startNodeExecution(dataObject, ruleResultList, (nextNode != null));*/
		return startNodeExecution(dataObject, ruleResultList, true);
	}
	
	public boolean executeNextNode(IHICData dataObject, List<String> ruleResultList) throws WorkflowComponentException
	{
		return executeNextNode(dataObject, ruleResultList, true);
	}
	
	public boolean executeNextNode(IHICData dataObject, List<String> ruleResultList, boolean calledFromMainThread) throws WorkflowComponentException
	{
		NodeObject newNode = getNextNode(dataObject, ruleResultList);
		return performNodeEventExecution(newNode, dataObject, NodeEventInfo.getStartEvent(), !calledFromMainThread);
	}
	
	
	
	public NodeObject getNextNode(IHICData dataObject, List<String> ruleResultList) throws WorkflowComponentException
	{
		NodeObject newNode = null, nextNode = null;
		
		if (ruleResultList == null)
			ruleResultList = RulesManager.getRules(dataObject);
		
		if (ruleResultList.size() <= 0) return newNode;
		
		String appName = null;
		String workflowName = null;
		String nodeName = null;
		WorkflowNodeCollection currWfColl = null;
		NodeObject currNode = null;
		
		try
		{
			appName = dataObject.getApplication().getApplicationName();
			currWfColl = workflowMapWithApplication.get(appName);
			
			workflowName = dataObject.getData().getWorkflowPattern().getWorkflowPattern();
			nodeName = dataObject.getData().getWorkflowPattern().getWorkflowNode();
			currNode = currWfColl.getNode(workflowName, nodeName);
		}
		catch (NullPointerException npe) { }
		
		
		// Calculate to which node should it go. - WORKFLOW MANAGER
		// This calculation is based on DataObject returned after exit event. - WORKFLOW MANAGER
		
		
		try
		{
			newNode 
				= currWfColl.getNode(
					dataObject.getData().getWorkflowPattern().getWorkflowPattern(), 
					dataObject.getData().getWorkflowPattern().getWorkflowNode());
		}
		catch (NullPointerException npe)
		{
//			npe.printStackTrace();
			// throw new WorkflowComponentException("Either application or workflow information not present in received DataObject");
		}
		
		WorkflowComponent.log(0, "[currNode]" + currNode);
		
		if ((newNode == null) || ((newNode != null) && (currNode != null) && (newNode.equals(currNode))))
		{
			newNode = getNextNodeUsingRule(ruleResultList, currWfColl);
			
			/*// If the resultant node is same as that for the current data object
			if ((newNode != null) && (currNode != null) && (newNode.equals(currNode)))
			{
				// First update data object for next node
				updateDOForNextNode(dataObject);
				// Fetch next node from Rules based on updated data object
				ruleResultList = RulesManager.getRules(dataObject);
				// Get Next Node based on information from Rule
				newNode = getNextNodeUsingRule(ruleResultList, currWfColl);
			}

			if ((newNode != null) && (currNode != null) && (newNode.equals(currNode)))
			{
				throw new WorkflowComponentException("Not able to fetch next node based on data object - " + dataObject + "\n\n Node Object is - " + currNode);
			}*/
			
//			WorkflowComponent.log(0, "[Inside RuleManager If condition]");
//			// This calculation should be based on Rules from Rule Component - WORKFLOW MANAGER
//			/*List<String> ruleResultList = RulesManager.getRules(dataObject);
//			WorkflowComponent.log(0, ruleResultList == null ? "[ruleResultList is NULL]" : "[ruleResultList.size()]" + ruleResultList.size());*/
//			
//			for (int i=0; i<ruleResultList.size();i++)
//			{
//				String conseq = ruleResultList.get(i);
//				WorkflowComponent.log(0, "[conseq]" + conseq);
//
//				Pattern p = Pattern.compile(".", Pattern.LITERAL);
//				String workflowInfo = p.split(conseq)[0];
//				String workflowId = workflowInfo.substring(0, workflowInfo.indexOf(ERLConstants.workflowNodeSep));
//				String nodeId = workflowInfo.substring(workflowInfo.indexOf(ERLConstants.workflowNodeSep)+ERLConstants.workflowNodeSep.length());
//				
//				WorkflowComponent.log(0, "[workflowId]" + workflowId + "\n[nodeId]" + nodeId);
//				
//				// Returns next Node - WORKFLOW MANAGER
//				newNode = currWfColl.getNode(workflowId, nodeId);
//				
//				// Ideally there should be only 1 destination node. - WORKFLOW MANAGER
//				if (newNode != null) break;
//			}
		}
		
		
		// If there is no next node according to rule component, - WORKFLOW MANAGER 
		if ((newNode == null) && (currNode != null))
		{
			// Get connected nodes to current node and take the first node. - WORKFLOW MANAGER
			if (currNode.getNodeConnectors() != null)
			{
				Iterator<INodeConnector> currNodeConnectorIterator = currNode.getNodeConnectors().iterator();
				while (currNodeConnectorIterator.hasNext())
				{
					INodeConnector currNodeConnector = currNodeConnectorIterator.next();
					if (currNodeConnector.getToNodeId() != null)
					{
						newNode = currWfColl.getNode(workflowName, currNodeConnector.getToNodeId());
					}
				}
			}
		}
		
		WorkflowComponent.log(0, "[newNode]" + newNode);
		
		// However, If there was no current node, set connected node to start node. - WORKFLOW MANAGER
		/*if ((newNode == null) && (workflowName != null))
		{
			newNode = currWfColl.getDefaultNode(workflowName);
		}

		WorkflowComponent.log(0, "4. [newNode]" + newNode);

		if (newNode == null)
		{
			newNode = currWfColl.getDefaultNode();
		}
		
		WorkflowComponent.log(0, "5. [newNode]" + newNode);*/
		
		return newNode;
	}
	
	
	private NodeObject getNextNodeUsingRule(List<String> ruleResultList, WorkflowNodeCollection currWfColl)
	{
		NodeObject newNode = null;
		
//		WorkflowComponent.log(0, "[Inside RuleManager If condition]");
		// This calculation should be based on Rules from Rule Component - WORKFLOW MANAGER
		/*List<String> ruleResultList = RulesManager.getRules(dataObject);
			WorkflowComponent.log(0, ruleResultList == null ? "[ruleResultList is NULL]" : "[ruleResultList.size()]" + ruleResultList.size());*/

		for (int i=0; i<ruleResultList.size();i++)
		{
			String conseq = ruleResultList.get(i);
//			WorkflowComponent.log(0, "[conseq]" + conseq);

			Pattern p = Pattern.compile(".", Pattern.LITERAL);
			String workflowInfo = p.split(conseq)[0];
			String workflowId = workflowInfo.substring(0, workflowInfo.indexOf(ERLConstants.workflowNodeSep));
			String nodeId = workflowInfo.substring(workflowInfo.indexOf(ERLConstants.workflowNodeSep)+ERLConstants.workflowNodeSep.length());

//			WorkflowComponent.log(0, "[workflowId]" + workflowId + "\n[nodeId]" + nodeId);

			// Returns next Node - WORKFLOW MANAGER
			newNode = currWfColl.getNode(workflowId, nodeId);

			// Ideally there should be only 1 destination node. - WORKFLOW MANAGER
			if (newNode != null) break;
		}
		return newNode;
	}
	
	
	public boolean performNodeEventExecution(NodeObject node, IHICData dataObject, NodeEvents event) throws WorkflowComponentException
	{
		return performNodeEventExecution(node, dataObject, event, false);
	}
	
	public boolean performNodeEventExecution(NodeObject node, IHICData dataObject, NodeEvents event, boolean dummyExecution) throws WorkflowComponentException
	{
		if (node != null)
		{
			return _nodeManager.startNodeExecution(dataObject, node, event, this, dummyExecution);
//			if (_nodeManager.isStopExecution()) return false;
		}
		/*else
		{
			return false;
		}*/
		return true;
	}
	
	
	
	public IHICData completeCurrentAndUpdateDOForNextNode(IHICData dataObject) throws WorkflowComponentException
	{
		dataObject.getData().getWorkflowPattern().setWorkflowPreviousNode(dataObject.getData().getWorkflowPattern().getWorkflowNode());
		boolean execContinue = startNodeExecution(dataObject, null);
		if (execContinue) updateDOForNextNode(dataObject);
		return dataObject;
	}
	
	public IHICData updateDOForNextNode(IHICData inputHicData)
	{
		WorkflowComponent.log(0, inputHicData.getUniqueID());

		String workflowName = null;
		String status = null;

		// Get Next Workflownodeinfo object pertaining to WorkflowID, NodeID, Status
		try
		{
			workflowName = inputHicData.getData().getWorkflowPattern().getWorkflowPattern();
			status = inputHicData.getData().getStatus();
		}
		catch (NullPointerException npe)
		{
			npe.printStackTrace();
			inputHicData.getData().setReturnMessage("Workflow and/or Current status information not available.");
			inputHicData.getData().setStatus("error");
			return inputHicData;
		}

//		WorkflowComponent.log(0, "[workflowName]" + workflowName + "[status]" + status);

		if (workflowName == null)
		{
			inputHicData.getData().setReturnMessage("Error: WorkflowName is null.");
			WorkflowComponent.log(3, "[Error: WorkflowName is null.]");
			return inputHicData;
		}

		boolean entryFound = false;
		try
		{
			List output = WorkFlowNodeInfoImpl.getWorkflowNodesInfoListBasedOnWorkflowId(workflowName);
			String[][] dataOutput = QueryData.iterateListData(output);

			if (dataOutput == null)
			{
				inputHicData.getData().setReturnMessage("Error: There is no status: [" + status 
						+ "] present in workflow: [" + workflowName + "]");
				WorkflowComponent.log(3, "Error: There is no status: [" + status 
						+ "] present in workflow: [" + workflowName + "]");
				return inputHicData;
			}

			/*
			 * This loop would fail if required status is at 0 position. However
			 * this would never be the case for this implementation. It would
			 * always be at position 1 or greater since we are fetching "NEXT"
			 * node.
			 */
			for (int i = 0; i < dataOutput.length; i++)
			{
				if (entryFound)
				{
					// Set status of current HICData to new status
					inputHicData.getData().setStatus(dataOutput[i][0]);

					// Set Form Pattern
					if (inputHicData.getData().getFormPattern() == null)
					{
						IFormPattern fp = new FormPattern();
						inputHicData.getData().setFormPattern(fp);
					}
					inputHicData.getData().getFormPattern().setFormId(dataOutput[i][1]);

					// Set Data Pattern
					if (inputHicData.getData().getDataPattern() == null)
					{
						IDataPattern dp = new DataPattern();
						inputHicData.getData().setDataPattern(dp);
					}
					inputHicData.getData().getDataPattern().setDataPatternId(dataOutput[i][2]);

					// Set User Pattern
					if (inputHicData.getData().getUserPatterns() == null)
					{
						Set<IUserPattern> up = null;
						inputHicData.getData().setUserPatterns(up);
					}
					if (dataOutput[i][3] != null)
					{
						String[] upArr = dataOutput[i][3].split(",");

						for (int ctr = 0; ctr < upArr.length; ctr++)
						{
							if (upArr[ctr] != null)
							{
								IUserPattern up = new UserPattern();
								up.setUserPatternId(upArr[ctr]);
								inputHicData.getData().addUserPatterns(up);
							}
						}
					}
					
					
					// Set Node Name
					inputHicData.getData().getWorkflowPattern().setWorkflowNode(dataOutput[i][4]);
					break;
				}

//				WorkflowComponent.log(0, "[Status = dataOutput[i][0] = ]" + dataOutput[i][0]);
				if (dataOutput[i][0].equals(status)) entryFound = true;
			}
		}
		catch (WorkflowComponentException e)
		{
			e.printStackTrace();
			inputHicData.getData().setReturnMessage(e.getMessage());
			WorkflowComponent.log(3, "[ERROR - " + e.getMessage() + "]");
			return inputHicData;
		}

		return inputHicData;
	}

	public IObserver getToolCompletionObserver()
	{
		return this._toolCompletionObserver;
	}

	public void setToolCompletionObserver(IObserver toolCompletionObserver)
	{
		this._toolCompletionObserver = toolCompletionObserver;
	}


	public IRouter getRouter()
	{
		return _router;
	}

	public void setRouter(IRouter router)
	{
		_router = router;
	}


	public IDataObjectManager getDoManager()
	{
		return doManager;
	}


	public NodeManager getNodeManager()
	{
		return _nodeManager;
	}

	public IHICData performActionGetInfoFromWorkflow(IHICData dataObject, List<String> functions) throws WorkflowComponentException
	{
		NodeObject currNode = this.getWorkflowCollection(dataObject.getApplication()).getNode(dataObject.getData().getWorkflowPattern().getWorkflowPattern(), dataObject.getData().getWorkflowPattern().getWorkflowNode());
		return _nodeManager.performActionGetInfoFromNode(currNode, dataObject, functions, this, null);
	}
	
}
