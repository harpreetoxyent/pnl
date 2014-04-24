package com.oxymedical.component.workflowComponent.operation.db;

import java.util.Hashtable;
import java.util.List;

import com.oxymedical.component.appadmin.model.resources_app.Nodedetail;
import com.oxymedical.component.appadmin.model.resources_app.Nodeinfo;
import com.oxymedical.component.workflowComponent.constants.WorkflowConstant;
import com.oxymedical.component.workflowComponent.db.impl.INodeDetails;
import com.oxymedical.component.workflowComponent.db.impl.INodeInfo;
import com.oxymedical.component.workflowComponent.db.impl.NodeDetailsImpl;
import com.oxymedical.component.workflowComponent.db.impl.NodeInfoImpl;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.nodeInfo.NodeType;
import com.oxymedical.component.workflowComponent.util.db.NodeDetails;
import com.oxymedical.component.workflowComponent.util.db.NodeInfo;

public class NodeOperation
{
	public void addNewNode(Hashtable fieldsValue,String applicationName)
	{
		
		String formPatternId= (String)fieldsValue.get(WorkflowConstant.FORMPATTERN_ID);
		String params=(String)fieldsValue.get(WorkflowConstant.PARAMS);
		String nodeName=(String)fieldsValue.get(WorkflowConstant.NODENAME);
		String nodeDescription=(String)fieldsValue.get(WorkflowConstant.NODEDESCRIPTION);;
		String nodeId=(String)fieldsValue.get(WorkflowConstant.NODEID);
		String toolId=(String)fieldsValue.get(WorkflowConstant.INPUT_TOOLCATEGORY);
		String isVisualizer=(String)fieldsValue.get(WorkflowConstant.INPUT_ISVISUALZIER);
		String inputXML=(String)fieldsValue.get(WorkflowConstant.INPUT_XML);
		int toolCategoryId=0;
		if(toolId!=null)
			toolCategoryId=Integer.parseInt(toolId);
			
		NodeDetails nodeDetails= new NodeDetails();
		nodeDetails.setAction(nodeName);
		nodeDetails.setPacakge(params);
		if(nodeId!=null)
		{
			nodeDetails.setId(Integer.parseInt(nodeId));
		}
		nodeName=nodeName.replaceAll(WorkflowConstant.NODE_PATTERN_NAME,"");
		nodeDetails.setNodeDescription(nodeDescription);
		nodeDetails.setFormPattern(formPatternId);
		if( isVisualizer!=null && isVisualizer.equalsIgnoreCase("true"))
		nodeDetails.setIsVisulaizer(true);
		nodeDetails.setInputxml(inputXML);
		INodeDetails nodeDetailsImpl = new NodeDetailsImpl();
		Nodedetail nodeDetail=null;
		try 
		{
		nodeDetail=nodeDetailsImpl.addNodeDetail(nodeDetails.getId(), nodeDetails.getAction(), nodeDetails.getPacakge(), nodeDetails.getFormPattern()
				,nodeDetails.getDeleted(),nodeDetails.getNodeDescription(),toolCategoryId,nodeDetails.getIsVisulaizer(),nodeDetails.getInputxml());
			} catch (WorkflowComponentException e) {
				e.printStackTrace();
		}
			boolean visualizer=nodeDetail.getIsVisualizer();
		if(nodeDetail!=null && !visualizer)
		{
		INodeInfo nodeInfoImpl = new NodeInfoImpl();
		List<Nodeinfo> nodeInfoList=nodeInfoImpl.getNodeListBasedOnNodeDetailId(nodeDetail.getId());
		int nodeInputScreenId=0;
		int nodeExectionId=0;
		if(nodeInfoList!=null && nodeInfoList.size()>0)
		{
			for(int i=0;i<nodeInfoList.size();i++)
			{
				Nodeinfo nodeinfo= nodeInfoList.get(i);
				nodeInputScreenId=nodeInfoList.get(i).getId();
			/*	if(nodeinfo.getNodename().indexOf(WorkflowConstant.NODENAME_SUFFIX_FOR_INPUT)>=0)
				{
					nodeInputScreenId=nodeInfoList.get(i).getId();
				}
				else
				{
					nodeExectionId=nodeInfoList.get(i).getId();
				}*/
			}
		}
		else
		{	
	    NodeInfo nInfo= new NodeInfo();
	    //String inputNodeName=nodeName+WorkflowConstant.NODENAME_SUFFIX_FOR_INPUT;
	    String inputNodeName=nodeName;
	
	    nInfo.setFormpattern(formPatternId);
	    nInfo.setNodeName(inputNodeName);
		nInfo.setScenario(applicationName);
		nInfo.setNodeDetailId(nodeDetail.getId());
		nInfo.setId(nodeInputScreenId);
		Nodeinfo nodeInfoObject = null;
		try 
		{
		nodeInfoObject = nodeInfoImpl.addNodeInfo(nInfo
							.getFormpattern(), nInfo.getDatapattern(),
							 nInfo.getNodeName(),
							nInfo
									.getUserPatternId(), nInfo
									.getScenario(),
							 nInfo.getInfo(), nInfo
									.getData(),nInfo.getNodeDetailId(),nInfo.getId(),NodeType.INPUT_NODE);
				} catch (WorkflowComponentException e) {
					e.printStackTrace();
				}
/*	This code is commented as now we will work on single node.	  
 * nInfo= new NodeInfo();
		  nInfo.setNodeName(nodeName);
		  nInfo.setScenario(applicationName);
		  nInfo.setNodeDetailId(nodeDetail.getId());
		  nInfo.setId(nodeExectionId);
		  nodeInfoObject = null;
					try 
					{
					nodeInfoObject = nodeInfoImpl.addNodeInfo(nInfo
										.getFormpattern(), nInfo.getDatapattern(),
										 nInfo.getNodeName(),
										 nInfo
												.getUserPatternId(), nInfo
												.getScenario(),
										 nInfo.getInfo(), nInfo
												.getData(),nInfo.getNodeDetailId(),nInfo.getId(),NodeType.ACTION_NODE);
							} catch (WorkflowComponentException e) {
								e.printStackTrace();
							}*/
					}
		}
	}	   
}

