package com.oxymedical.component.workflowComponent.operation.db;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import com.oxymedical.component.appadmin.model.resources_app.Nodeconnector;
import com.oxymedical.component.appadmin.model.resources_app.Nodeinfo;
import com.oxymedical.component.appadmin.model.resources_app.Workflowinfo;
import com.oxymedical.component.workflowComponent.db.impl.INodeConnectorInfo;
import com.oxymedical.component.workflowComponent.db.impl.IWorkFlow;
import com.oxymedical.component.workflowComponent.db.impl.IWorkFlowNodeInfo;
import com.oxymedical.component.workflowComponent.db.impl.NodeConnectorImpl;
import com.oxymedical.component.workflowComponent.db.impl.NodeInfoImpl;
import com.oxymedical.component.workflowComponent.db.impl.WorkFlowImpl;
import com.oxymedical.component.workflowComponent.db.impl.WorkFlowNodeInfoImpl;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.nodeConnectorBuilder.INodeConnector;
import com.oxymedical.component.workflowComponent.nodeConnectorBuilder.NodeConnector;
import com.oxymedical.component.workflowComponent.nodeInfo.NodeObject;
import com.oxymedical.component.workflowComponent.util.db.NodeConnectorInfo;
import com.oxymedical.component.workflowComponent.util.db.WorkFlowInfo;
import com.oxymedical.component.workflowComponent.util.db.WorkflowNodeInfo;

public class NodeConnectorCollectionOperation {
	
	public void addNodeCollectionObject(Hashtable<String, List<INodeConnector>> connectorCollection)
	{
		Enumeration nodeConnectorEnum = connectorCollection.keys();
	      while(nodeConnectorEnum.hasMoreElements())
	      {
	    	  String workflowName=(String)nodeConnectorEnum.nextElement();
	    	  IWorkFlow workFlowImpl= new WorkFlowImpl();
				/*try {
					Workflowinfo workflowdb=workFlowImpl.getWorkFlowBasedOnName(workflowName);
					if(workflowdb!=null)
					{			
						int id=workflowdb.getId();
						INodeConnectorInfo nodeConnectorImpl= new NodeConnectorImpl();
				    	List<Nodeconnector> nodeConnectors=nodeConnectorImpl.getNodeConnectorsBasedOnWorkFlow(id);
				    	if(nodeConnectors!=null && nodeConnectors.size()>0)
				    	{
				    		continue;
				    	}
					}
				} catch (WorkflowComponentException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}*/
	    	  List<INodeConnector> nodeConnectors= connectorCollection.get(workflowName);
	    	  NodeConnectorInfo nodeConnectorInfo=null;
	    	  if(nodeConnectors!=null && nodeConnectors.size()>0)
	    	  {	  
	    		  INodeConnectorInfo nodeImpl=null;
	    		  
		    	  for(int i=0;i<nodeConnectors.size();i++)
		    	  {
		    		  INodeConnector nodeConnector=nodeConnectors.get(i);
		    		  nodeConnectorInfo=new NodeConnectorInfo();
		    		  nodeConnectorInfo.setFromNodeId(nodeConnector.getFromNodeId());
		    		  nodeConnectorInfo.setToNodeId(nodeConnector.getToNodeId());
		    		  nodeConnectorInfo.setWorkflowName(workflowName);
		    		  nodeImpl= new NodeConnectorImpl();
		    		  try {
						nodeImpl.addNodeConnector(nodeConnectorInfo.getConnectorName(), nodeConnectorInfo.getFromNodeId(),
								  nodeConnectorInfo.getToNodeId(),nodeConnectorInfo.getWorkflowName());
					} catch (WorkflowComponentException e) {
						e.printStackTrace();
					}
		    		  
		    		 
		    	  }
	    	  }
	      }
	    	  
	}
	
	public Hashtable<String, List<INodeConnector>> getNodeConnectorCollectionObject()
	{
		Hashtable<String, List<INodeConnector>> connectorCollection= new Hashtable<String, List<INodeConnector>>();
		IWorkFlow workflowImpl= new WorkFlowImpl();
		List<Workflowinfo> workflowObjects=null;
	
		Hashtable<String,NodeObject> nodeObjectsHash=null;
		try {
			 workflowObjects= workflowImpl.getListOfWorkFlowList();
		} catch (WorkflowComponentException e) {
			e.printStackTrace();
		}
		if(workflowObjects!=null && workflowObjects.size()>0)
		{
	       for(int workflowCounter=0;workflowCounter<workflowObjects.size();workflowCounter++)
	       {
	    	   Workflowinfo workflowInfo= workflowObjects.get(workflowCounter);
	    	  
	    	   String workflowName= workflowInfo.getName();
	    	   List<INodeConnector> nodeConnectorList=getConnectorListForWorkflow(workflowInfo);
	    	   connectorCollection.put(workflowName, nodeConnectorList);
	       }
		}
		return connectorCollection;
	}
	//added new method which returns list of inodeconnector object based on the workflow.
	public  List<INodeConnector>  getConnectorListForWorkflow(Workflowinfo workflowInfo)
	{
	   int workflowId= workflowInfo.getId();
 	   List<INodeConnector> nodeConnectorList=new ArrayList<INodeConnector>();
 	   INodeConnectorInfo nodeConnectorImpl= new NodeConnectorImpl();
 	   List<Nodeconnector> nodeConnectors=nodeConnectorImpl.getNodeConnectorsBasedOnWorkFlow(workflowId);
 	   if(nodeConnectors!=null && nodeConnectors.size()>0)
 	   {
 		   for(int i= 0;i<nodeConnectors.size();i++)
 		   {
 		   Nodeconnector nodeconnector=nodeConnectors.get(i);
 		   String fromNodeName=nodeconnector.getNodeinfoByFromNodeId().getNodename();
 		   String toNodeName=nodeconnector.getNodeinfoByToNodeId().getNodename();
 		   INodeConnector nodeConnectorWorkFlow= new NodeConnector();
 		   nodeConnectorWorkFlow.setFromNodeId(fromNodeName);
 		   nodeConnectorWorkFlow.setToNodeId(toNodeName);
 		   nodeConnectorList.add(nodeConnectorWorkFlow);
 		   }
 	   }
 	   return nodeConnectorList;
	}
}