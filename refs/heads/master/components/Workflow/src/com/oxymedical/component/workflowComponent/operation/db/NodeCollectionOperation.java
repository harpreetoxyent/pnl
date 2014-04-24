package com.oxymedical.component.workflowComponent.operation.db;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


import com.oxymedical.component.appadmin.model.resources_app.Nodedetail;
import com.oxymedical.component.appadmin.model.resources_app.Nodeevent;
import com.oxymedical.component.appadmin.model.resources_app.Nodeinfo;
import com.oxymedical.component.appadmin.model.resources_app.Workflowinfo;
import com.oxymedical.component.appadmin.model.resources_app.Workflownodeinfo;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.rulesComponent.parser.javacc.Node;
import com.oxymedical.component.workflowComponent.WorkflowComponent;
import com.oxymedical.component.workflowComponent.constants.WorkflowConstant;
import com.oxymedical.component.workflowComponent.constants.WorkflowSqlCommands;
import com.oxymedical.component.workflowComponent.db.impl.INodeConnectorInfo;
import com.oxymedical.component.workflowComponent.db.impl.INodeDetails;
import com.oxymedical.component.workflowComponent.db.impl.INodeEvent;
import com.oxymedical.component.workflowComponent.db.impl.INodeInfo;
import com.oxymedical.component.workflowComponent.db.impl.IWorkFlow;
import com.oxymedical.component.workflowComponent.db.impl.IWorkFlowNodeInfo;
import com.oxymedical.component.workflowComponent.db.impl.NodeConnectorImpl;
import com.oxymedical.component.workflowComponent.db.impl.NodeDetailsImpl;
import com.oxymedical.component.workflowComponent.db.impl.NodeEventImpl;
import com.oxymedical.component.workflowComponent.db.impl.NodeInfoImpl;
import com.oxymedical.component.workflowComponent.db.impl.WorkFlowImpl;
import com.oxymedical.component.workflowComponent.db.impl.WorkFlowNodeInfoImpl;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.nodeBuilder.DataObjectAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.DataPatternAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.EventAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.FormPatternAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.IDataObjectAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.IDataPatternAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.IEventAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.IFormPatternAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.INodeAttribute;
import com.oxymedical.component.workflowComponent.nodeBuilder.IUserPatternAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.IWorkflowAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.IWorkflowNodeStatus;
import com.oxymedical.component.workflowComponent.nodeBuilder.NodeAttribute;
import com.oxymedical.component.workflowComponent.nodeBuilder.UserPatternAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.WorkflowAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.WorkflowNodeStatus;
import com.oxymedical.component.workflowComponent.nodeInfo.NodeObject;
import com.oxymedical.component.workflowComponent.nodeInfo.NodeType;
import com.oxymedical.component.workflowComponent.util.db.NodeConnectorInfo;
import com.oxymedical.component.workflowComponent.util.db.NodeEventsInfo;
import com.oxymedical.component.workflowComponent.util.db.NodeInfo;
import com.oxymedical.component.workflowComponent.util.db.WorkFlowInfo;
import com.oxymedical.component.workflowComponent.util.db.WorkflowNodeInfo;

public class NodeCollectionOperation {

	public void addNodeCollectionObjectToDatabase(
			Hashtable<String, Hashtable<String, NodeObject>> nodeCollection) {
		Enumeration workflowsEnum = nodeCollection.keys();
		while (workflowsEnum.hasMoreElements()) {
			Workflowinfo flowObject = null;
			String workFlowId = (String) workflowsEnum.nextElement();
			IWorkFlow workFlowImpl= new WorkFlowImpl();
			try {
				Workflowinfo workflowdb=workFlowImpl.getWorkFlowBasedOnName(workFlowId);
				if(workflowdb!=null)
				{
					//continue;
					
					int workflowId = workflowdb.getId();
					NodeConnectorImpl nci = new NodeConnectorImpl();
					nci.deleteWorkflowFromNodeConnector(workflowId);
					WorkFlowNodeInfoImpl wfnii = new WorkFlowNodeInfoImpl();
					wfnii.deleteXMLWorkflowFromWorkflowNodeInfo(workflowId); 					
					ConnectionDatabase.GetInstanceOfDatabaseComponent().deleteObject(workflowdb);
				}
			} catch (Exception e) {
				 e.printStackTrace();
				
			}
			
			
			WorkFlowInfo workInfo = new WorkFlowInfo();
			workInfo.setWorkFlowName(workFlowId);
			workInfo.setCreatedFromUI(false);
			IWorkFlow wFlow = new WorkFlowImpl();
			try {
				flowObject = wFlow.addWorkFlow(workInfo.getWorkFlowName(), workInfo
						.getDeleted(),workInfo.getId(),workInfo.isVisual(),workInfo.isCreatedFromUI(),workInfo.getVisualizerid());
			} catch (WorkflowComponentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
          
			if (flowObject != null) {
				Hashtable<String, NodeObject> nodeObjectCollection = nodeCollection
						.get(workFlowId);
				if (nodeObjectCollection != null
						&& nodeObjectCollection.size() > 0) {
					Enumeration nodesEnum = nodeObjectCollection.keys();
					while (nodesEnum.hasMoreElements()) {
						String nodeName = (String) nodesEnum.nextElement();
						NodeObject nodeObject = nodeObjectCollection
								.get(nodeName);
						String formPatternId = nodeObject
								.getFormPatternString();
						String dataPatternId = null;
						Set<IDataPatternAttributes> dataPatternObject = nodeObject
								.getDataPatternObject();
						if (dataPatternObject != null) {
							Iterator itr = dataPatternObject.iterator();
							while (itr.hasNext()) {
								IDataPatternAttributes dataPatternAttribute = (DataPatternAttributes) itr
										.next();
								dataPatternId = dataPatternAttribute
										.getDataPatternId();
							}
						}
						IDataObjectAttributes dataObject = nodeObject
								.getDataObject();
						String status = dataObject.getStatus();
						String info = dataObject.getInfo();
						String data = dataObject.getData();
						String isStart = nodeObject.getNodeAttributes()
								.getIsStart();
						String scenario = nodeObject.getNodeAttributes()
								.getScenario();
						NodeInfo nInfo = new NodeInfo();
						nInfo.setData(data);
						nInfo.setInfo(info);
						nInfo.setDatapattern(dataPatternId);
						nInfo.setFormpattern(formPatternId);
						nInfo.setNodeName(nodeName);
						nInfo.setScenario(scenario);
						INodeInfo nodeInfoImpl = new NodeInfoImpl();
						Nodeinfo nodeInfoObject = null;
						try {
							nodeInfoObject = nodeInfoImpl.addNodeInfo(nInfo
									.getFormpattern(), nInfo.getDatapattern(),
									nInfo.getNodeName(),
									 nInfo
											.getUserPatternId(), nInfo
											.getScenario(),
									nInfo.getInfo(), nInfo
											.getData(),nInfo.getNodeDetailId(),nInfo.getId(),NodeType.INPUT_NODE);
						} catch (WorkflowComponentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						WorkflowNodeInfo workNodeInfo = new WorkflowNodeInfo();
						workNodeInfo.setNodeId(nodeInfoObject.getId());
						workNodeInfo.setWorkflowId(flowObject.getId());
						workNodeInfo.setStatus(status);
						if(isStart.equalsIgnoreCase("true"))
						{
							workNodeInfo.setStartNode(true);
						}
						else
							workNodeInfo.setStartNode(false);
						Workflownodeinfo workflownodeinfo=null;
						IWorkFlowNodeInfo workNodeImpl = new WorkFlowNodeInfoImpl();
						try {
							workflownodeinfo=workNodeImpl.addWorkFlowNodeInfo(workNodeInfo
									.getWorkflowId(), workNodeInfo
									.getNodeId(),workNodeInfo.getStatus(),workNodeInfo.isStartNode());
						} catch (WorkflowComponentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						IEventAttributes eventAttribute = nodeObject
								.getEventAttributeObject();
						if (eventAttribute != null && nodeInfoObject != null) {
							NodeEventsInfo nodeEventInfo = null;
							INodeEvent nodeEvent = null;
							String eventParamsOnEnter = eventAttribute
									.getOnEnter();
							if (eventParamsOnEnter != null) {
								nodeEventInfo = new NodeEventsInfo();
								nodeEventInfo.setWorkflowNodeInfoId(workflownodeinfo.getId());
								nodeEventInfo.setParams(eventParamsOnEnter);
								nodeEventInfo
										.setEventType(WorkflowConstant.ENTERNODE);
								nodeEvent = new NodeEventImpl();
								try {
									nodeEvent.addNodeEvent(nodeEventInfo
											.getWorkflowNodeInfoId(), nodeEventInfo
											.getParams(), nodeEventInfo
											.getEventType());
								} catch (WorkflowComponentException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						}
						if (eventAttribute != null && nodeInfoObject != null) {
							NodeEventsInfo nodeEventInfo = null;
							INodeEvent nodeEvent = null;
							String eventParamsOnExit = eventAttribute
									.getOnExit();
							if (eventParamsOnExit != null) {
								nodeEventInfo = new NodeEventsInfo();
								nodeEventInfo.setWorkflowNodeInfoId(workflownodeinfo.getId());
								nodeEventInfo.setParams(eventParamsOnExit);
								nodeEventInfo
										.setEventType(WorkflowConstant.EXITNODE);
								nodeEvent = new NodeEventImpl();
								try {
									nodeEvent.addNodeEvent(nodeEventInfo
											.getWorkflowNodeInfoId(), nodeEventInfo
											.getParams(), nodeEventInfo
											.getEventType());
								} catch (WorkflowComponentException e) {
									e.printStackTrace();
								}

							}
						}
						if (eventAttribute != null && nodeInfoObject != null) {
							NodeEventsInfo nodeEventInfo = null;
							INodeEvent nodeEvent = null;
							String eventParamsOnNode = eventAttribute
									.getOnNode();
							if (eventParamsOnNode != null) {
								nodeEventInfo = new NodeEventsInfo();
								nodeEventInfo.setWorkflowNodeInfoId(workflownodeinfo.getId());
								nodeEventInfo.setParams(eventParamsOnNode);
								nodeEventInfo
										.setEventType(WorkflowConstant.ONNODE);
								nodeEvent = new NodeEventImpl();
								try {
									nodeEvent.addNodeEvent(nodeEventInfo
											.getWorkflowNodeInfoId(), nodeEventInfo
											.getParams(), nodeEventInfo
											.getEventType());
								} catch (WorkflowComponentException e) {
									e.printStackTrace();
								}

							}
						}
						
							
						
					}

				}
			}

		}
	}

	public Hashtable<String, Hashtable<String, NodeObject>>  getNodeCollectionObjectFromDatabase()
	{
		Hashtable<String, Hashtable<String, NodeObject>> nodeCollection= new Hashtable<String, Hashtable<String,NodeObject>>();
		IWorkFlow workflowImpl= new WorkFlowImpl();
		List<Workflowinfo> workflowObjects=null;
	
		try {
			 workflowObjects= workflowImpl.getListOfWorkFlowList();
		} catch (WorkflowComponentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(workflowObjects!=null && workflowObjects.size()>0)
		{
			for(int i=0;i<workflowObjects.size();i++)
			{
				Workflowinfo workflowinfo= workflowObjects.get(i);
				String workFlowName= workflowinfo.getName();
				Hashtable<String,NodeObject> nodeObjectsHash=getNodeObjectTableForWorkFlow(workflowinfo);
				if(nodeObjectsHash!=null)
				nodeCollection.put(workFlowName, nodeObjectsHash);
			}
		}
		return nodeCollection;
	}
	//added new methods which return hashtable for all nodes and nodeobject.
	public 	Hashtable<String,NodeObject> getNodeObjectTableForWorkFlow(Workflowinfo workflowinfo)
	{
		Hashtable<String,NodeObject> nodeObjectsHash=null;
		String workFlowName= workflowinfo.getName();
		int workFlowId= workflowinfo.getId();
		IWorkFlowNodeInfo worknodeImpl = new WorkFlowNodeInfoImpl();
		List<Workflownodeinfo> workNodeInfoObjects=null;
		try {
			workNodeInfoObjects=worknodeImpl.getWorkflowNodesInfoListBasedOnWorkflowId(workFlowId);					
		} catch (WorkflowComponentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(workNodeInfoObjects!=null && workNodeInfoObjects.size()>0)
		{
		  NodeObject nodeObject=null;
		  nodeObjectsHash= new Hashtable<String, NodeObject>();
			for(int j=0;j<workNodeInfoObjects.size();j++)
			{
				nodeObject= new NodeObject();
				Workflownodeinfo workNodeInfo= workNodeInfoObjects.get(j);
				
				Nodeinfo nodeInfo=workNodeInfo.getNodeinfo();
				int nodeId= nodeInfo.getId();
				String nodeName=nodeInfo.getNodename();
				String formPatternId=nodeInfo.getFormpatternid();
				String dataPatternid=nodeInfo.getDatapatternid();
				String status=workNodeInfo.getStatus();
				String scenario=nodeInfo.getScenario();
				String data=nodeInfo.getData();
				String info= nodeInfo.getInfo();
				String userpatternId=nodeInfo.getUserpatternid();
				String nodeType = nodeInfo.getNodetype();
				boolean isStartNode=workNodeInfo.getIsStartNode();
				IDataObjectAttributes dataObject=new DataObjectAttributes();
			    dataObject.setData(data);
			    dataObject.setInfo(info);
			    dataObject.setStatus(status);
				NodeAttribute nodeAttributes=new NodeAttribute();
			    if(isStartNode)
			    nodeAttributes.setIsStart("true");
			    else
			    nodeAttributes.setIsStart("false");
			    nodeAttributes.setScenario(scenario);
			    nodeAttributes.setNodeId(nodeName);
			    Set<IFormPatternAttributes> formPatternObject= new HashSet<IFormPatternAttributes>();
			    IFormPatternAttributes formPatternAtt = new FormPatternAttributes();
			    formPatternAtt.setFormPatternId(formPatternId);
			    formPatternObject.add(formPatternAtt);
			    Set<IUserPatternAttributes> userPatternObject=new HashSet<IUserPatternAttributes>();
			    IUserPatternAttributes userpatternAtt= new UserPatternAttributes();
			    if(userpatternId!=null)
			    userpatternAtt.setUserPatternId(userpatternId);
			    else
			    	 userpatternAtt.setUserPatternId(null);
			    userPatternObject.add(userpatternAtt);
			    Set<IDataPatternAttributes> dataPatternObject=new HashSet<IDataPatternAttributes>();
			    IDataPatternAttributes dataPatternArr= new DataPatternAttributes();
			    if(dataPatternid!=null)
			    dataPatternArr.setDataPatternId(dataPatternid);
			    else
			    dataPatternArr.setDataPatternId(null);
			    dataPatternObject.add(dataPatternArr);
			    IWorkflowAttributes workflowAttr= new WorkflowAttributes();
			    workflowAttr.setId(workFlowName);
			    if(isStartNode)
			    	nodeAttributes.setIsStart("true");
				    else
				     nodeAttributes.setIsStart("false");
			    
			    
			    //Added Code for node type
			    if ((nodeType != null) && (nodeType.equalsIgnoreCase("A"))) 
			    	nodeObject.setNodeType(NodeType.ACTION_NODE); 
//			    nodeStatus.setScenario(scenario);
			    nodeObject.setDataObject(dataObject);
			    nodeObject.setWorkflowAttributes(workflowAttr);
			    nodeObject.setUserPatternObject(userPatternObject);
			    nodeObject.setFormPatternObject(formPatternObject);
			    nodeObject.setNodeAttributes(nodeAttributes);
			    nodeObject.setDataPatternObject(dataPatternObject);
			  
				INodeEvent nodeEventImpl= new NodeEventImpl();
				List<Nodeevent> nodeEventObjects=null;						
				try {
					nodeEventObjects=nodeEventImpl.getNodeEventBasedOnNodeId(workNodeInfo.getId());
				} catch (WorkflowComponentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(nodeEventObjects!=null && nodeEventObjects.size()>0)
				{
					IEventAttributes eventAtt= new EventAttributes();
                  for(int eventCounter=0;eventCounter<nodeEventObjects.size();eventCounter++)
                  {
                	Nodeevent nodeEvent= nodeEventObjects.get(eventCounter);
                	String paramString= nodeEvent.getParams();
                    String eventType=nodeEvent.getEventtype().getEventtype();
                    if(eventType.equalsIgnoreCase(WorkflowConstant.ONNODE))
                    {
                    
                      eventAtt.setOnNode(paramString);	
                    }
                    if(eventType.equalsIgnoreCase(WorkflowConstant.ENTERNODE))
                    {
                    	
                    	eventAtt.setOnEnter(paramString);
                    }
                    if(eventType.equalsIgnoreCase(WorkflowConstant.EXITNODE))
                    {
                    	eventAtt.setOnExit(paramString);
                    	
                    }
                  }
                  nodeObject.setEventAttributeObject(eventAtt);
				}
				nodeObjectsHash.put(nodeName, nodeObject);
			}
		}
		return nodeObjectsHash;
	}
	public Workflowinfo addWorkflowFromUI(String workflowName,String workflowId,List selectedNodesID,String isVisual,int visualizerid)
	{

		Workflowinfo flowObject = null;
		boolean booleanisVisual = false;
		if(isVisual.equals("true"))
		{
			booleanisVisual = true;
		}
		/*if(isWorkflowNameChanged!=null && isWorkflowNameChanged.equals("true"))
		{
			String workQuery=WorkflowSqlCommands.UPDATE_WORKFLOW_NAME;
			ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
			NameQueryParameter work1 = new NameQueryParameter(WorkflowSqlCommands.workflowid, Integer.parseInt(passedOnWorkflowIdFromUI));
			NameQueryParameter work2 = new NameQueryParameter(WorkflowSqlCommands.workflowname, workflowName);
			listParam.add(work1);
			listParam.add(work2);
			try 
			{
					ConnectionDatabase.GetInstanceOfDatabaseComponent().executeDMLQueryWithNameParameter(workQuery, listParam);
			} 
			catch (DBComponentException e) 
			{
						e.printStackTrace();
			}
				
		}	*/
	
		try 
		{
				List<Nodeinfo> nodeConnectorList=new LinkedList<Nodeinfo>();
				IWorkFlow wFlow = new WorkFlowImpl();
				Workflowinfo w1=null;
				if(workflowId!=null)
				{
				 w1 = wFlow.getWorkFlowBasedOnId(Integer.parseInt(workflowId));
				}
				WorkFlowInfo workInfo = new WorkFlowInfo();
				workInfo.setWorkFlowName(workflowName);
				workInfo.setVisual(booleanisVisual);
				workInfo.setCreatedFromUI(true);
				if(w1!=null)
				{
					workInfo.setId(w1.getId());
				}
				flowObject = wFlow.addWorkFlow(workInfo.getWorkFlowName(), workInfo
							.getDeleted(),workInfo.getId(),workInfo.isVisual(),workInfo.isCreatedFromUI(),visualizerid);
				workflowName=flowObject.getName();
				if(w1!=null)
				{
					int workflowID = w1.getId();
					WorkFlowNodeInfoImpl wfnii = new WorkFlowNodeInfoImpl();
					wfnii.deleteWorkflowFromWorkflowNodeInfo(workflowID);
					NodeConnectorImpl nci = new NodeConnectorImpl();
					nci.deleteWorkflowFromNodeConnector(workflowID);
				}
				
				if(flowObject!=null)
				{
					if(selectedNodesID!=null && selectedNodesID.size()>0)
					{
					for(int i=0;i<selectedNodesID.size();i++)
					{
						WorkflowComponent.logger.log(0, "workflowid = "+flowObject.getId()+"nodeID = "+selectedNodesID.get(i).toString());
						INodeDetails nodeDetailImpl= new NodeDetailsImpl();
						Nodedetail nodeDetailObj=nodeDetailImpl.getNodeDetailBasedOnNodeDetailId(Integer.parseInt(selectedNodesID.get(i).toString()));
						if(nodeDetailObj!=null)
						{
						NodeInfoImpl nodeInfoImpl = new NodeInfoImpl();
						List<Nodeinfo> nodes=nodeInfoImpl.getNodeListBasedOnNodeDetailId(nodeDetailObj.getId());
						if(nodes!=null && nodes.size()>0)
						{	
							for(int j=0;j<nodes.size();j++)
							{
								Nodeinfo nodeInfo =nodes.get(j);
								WorkflowNodeInfo workNodeInfo = new WorkflowNodeInfo();
								workNodeInfo.setNodeId(nodeInfo.getId());
								workNodeInfo.setWorkflowId(flowObject.getId());
								if(i==0 && j==0)
								{
									workNodeInfo.setStartNode(true);
								}
								nodeConnectorList.add(nodeInfo);
								String status=flowObject.getName()+"."+nodeInfo.getNodename();
								workNodeInfo.setStatus(status);
								IWorkFlowNodeInfo workNodeImpl = new WorkFlowNodeInfoImpl();
								Workflownodeinfo workflowNodeInfo=workNodeImpl.addWorkFlowNodeInfo(workNodeInfo.getWorkflowId(), workNodeInfo.getNodeId(),workNodeInfo.getStatus(),workNodeInfo.isStartNode());
								String className=nodeDetailObj.getPacakge();
								String methodName=nodeDetailObj.getAction();
								String compId=flowObject.getName()+"."+methodName+"."+className;
								String onExitParams="invokeComponentThread(\""+compId+"\",\""+methodName+"\",\""+className+"\",\"\");";
								NodeEventsInfo nodeEventInfo = null;
								INodeEvent nodeEvent = null;
								/*if(nodeInfo.getNodename().indexOf(WorkflowConstant.NODENAME_SUFFIX_FOR_INPUT)>=0)
								{*/
									String formpattern=nodeInfo.getFormpatternid();
									nodeEventInfo = new NodeEventsInfo();
									nodeEventInfo.setWorkflowNodeInfoId(workflowNodeInfo.getId());
									String onNodeParams="invokeComponent(\"id.rendering\",\"moveForm\",\"com.oxymedical.component.renderer.RendererComponent\",\""+formpattern+"\");";
									nodeEventInfo.setParams(onNodeParams);
									nodeEventInfo
											.setEventType(WorkflowConstant.ONNODE);
									nodeEvent = new NodeEventImpl();
									try {
										nodeEvent.addNodeEvent(nodeEventInfo
												.getWorkflowNodeInfoId(), nodeEventInfo
												.getParams(), nodeEventInfo
												.getEventType());
									} catch (WorkflowComponentException e) {
										e.printStackTrace();
									}
									
									nodeEventInfo = new NodeEventsInfo();
									nodeEventInfo.setWorkflowNodeInfoId(workflowNodeInfo.getId());
									String onEnterParams="invokeTool(\""+compId+"\",\""+"init"+"\",\""+className+"\",\"\");";
									nodeEventInfo.setParams(onEnterParams);
									nodeEventInfo
											.setEventType(WorkflowConstant.ENTERNODE);
									nodeEvent = new NodeEventImpl();
									try {
										nodeEvent.addNodeEvent(nodeEventInfo
												.getWorkflowNodeInfoId(), nodeEventInfo
												.getParams(), nodeEventInfo
												.getEventType());
									} catch (WorkflowComponentException e) {
										e.printStackTrace();
									}
								/*}
								else
								{*/
								nodeEventInfo = new NodeEventsInfo();
								nodeEventInfo.setWorkflowNodeInfoId(workflowNodeInfo.getId());
								nodeEventInfo.setParams(onExitParams);
								nodeEventInfo.setEventType(WorkflowConstant.EXITNODE);
									nodeEvent = new NodeEventImpl();
									try {
										nodeEvent.addNodeEvent(nodeEventInfo
												.getWorkflowNodeInfoId(), nodeEventInfo
												.getParams(), nodeEventInfo
												.getEventType());
									} catch (WorkflowComponentException e) {
										e.printStackTrace();
									}
								//}
						
						/*if(i<(selectedNodesID.size()-1))
						{
							INodeConnectorInfo nodeImpl=new NodeConnectorImpl();
							NodeConnectorInfo nodeConnectorInfo=new NodeConnectorInfo();
							nodeConnectorInfo.setFromNodeId(nodeInfo.getNodename());
							nodeInfo = nodeInfoImpl.getNodeBasedOnId(Integer.parseInt(selectedNodesID.get(i+1).toString()));
				    		nodeConnectorInfo.setToNodeId(nodeInfo.getNodename());
				    		nodeConnectorInfo.setWorkflowName(workflowName);
				    		nodeImpl= new NodeConnectorImpl();
				    		nodeImpl.addNodeConnector(nodeConnectorInfo.getConnectorName(), nodeConnectorInfo.getFromNodeId(),
										  nodeConnectorInfo.getToNodeId(),nodeConnectorInfo.getWorkflowName());
							
						}*/
						}
					 }
				   }
				}
					}	
					if(nodeConnectorList!=null && nodeConnectorList.size()>0)
					{
						for(int nodeCounter=0;nodeCounter<nodeConnectorList.size();nodeCounter++)
						{
							if(nodeCounter<(nodeConnectorList.size()-1))
							{
							INodeConnectorInfo nodeImpl=new NodeConnectorImpl();
							NodeConnectorInfo nodeConnectorInfo=new NodeConnectorInfo();
							Nodeinfo nodeInfo=nodeConnectorList.get(nodeCounter);
							nodeConnectorInfo.setFromNodeId(nodeInfo.getNodename());
							nodeInfo=nodeConnectorList.get(nodeCounter+1);
				    		nodeConnectorInfo.setToNodeId(nodeInfo.getNodename());
				    		nodeConnectorInfo.setWorkflowName(workflowName);
				    		nodeImpl= new NodeConnectorImpl();
				    		nodeImpl.addNodeConnector(nodeConnectorInfo.getConnectorName(), nodeConnectorInfo.getFromNodeId(),
										  nodeConnectorInfo.getToNodeId(),nodeConnectorInfo.getWorkflowName());
						     }
						}
						
					}
				}
		}
		catch (WorkflowComponentException e) {
			e.printStackTrace();
		}
		
		return flowObject;
	}
	public static void main(String args[])
	{
		
		NodeCollectionOperation nco = new NodeCollectionOperation();
		List l = new ArrayList();
		l.add(1);
		
		//nco.addWorkflowFromUI("test134",l );
	}
}
