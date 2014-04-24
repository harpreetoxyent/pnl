package com.oxymedical.component.workflowComponent.nodemanager;

import java.util.List;

import com.oxymedical.component.workflowComponent.WorkflowComponent;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.nodeInfo.NodeObject;
import com.oxymedical.component.workflowComponent.nodeInfo.NodeType;
import com.oxymedical.component.workflowComponent.workflowManager.IWorkflowManager;
import com.oxymedical.component.workflowComponent.workflowManager.WorkflowManager;
import com.oxymedical.core.commonData.Data;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.commonData.IWorkflowPattern;
import com.oxymedical.core.commonData.WorkflowPattern;
import com.oxymedical.core.constants.CoreConstants;

public class NodeManager implements INodeManager
{
	NodeEventManager nEvMgr = new NodeEventManager();
	boolean stopExecution = false;

	public boolean startNodeExecution(IHICData dataObject, NodeObject node, NodeEvents event, 
			IWorkflowManager workflowMgr) throws WorkflowComponentException
	{
		return startNodeExecution(dataObject, node, event, workflowMgr, false);
	}
	
	public boolean startNodeExecution(IHICData dataObject, NodeObject node, NodeEvents event, 
			IWorkflowManager workflowMgr, boolean dummyExecution) throws WorkflowComponentException
	{
		boolean completedGracefully = true;
		WorkflowComponent.log(0, "[node.getNodeType()]" + node.getNodeType());
		
//		WorkflowComponent.log(0, "[INSIDE startNodeExecution FUNCTION]");

		WorkflowManager wfMgr = (WorkflowManager)workflowMgr;
		
		setStopExecution(false);
		
		// These two are used to remove DO from DOList on Node
		// These two are stored even before execution so that even if the event execution changes this value, we still have the original values
		String storedWorkflowId = null;
		String storedNodeId = null;
		
		try
		{
			storedWorkflowId = dataObject.getData().getWorkflowPattern().getWorkflowPattern();
			storedNodeId = dataObject.getData().getWorkflowPattern().getWorkflowNode();
		}
		catch (NullPointerException npe) { }
		
		if (event == null) event = NodeEventInfo.getDefaultExecutionStartEvent();
		
		
		// Get all event types from NodeEvent with their execution order information. - NODE MANAGER
		// For current node, Loop each node event according to execution order - NODE MANAGER
		// {
		List<NodeEvents> nEventList = NodeEventInfo.getSuccessorExecutionEvents(event);
		for (int i = 0; i < nEventList.size(); i++)
		{
			NodeEvents currEvent = nEventList.get(i);
			
			// Check if event is already under processing
			Boolean alreadyProcessingBool = null;
			if ((dataObject != null) 
					&& (dataObject.getData() != null) 
					&& (dataObject.getData().getWorkflowPattern() != null) 
					&& (dataObject.getData().getWorkflowPattern().getWorkflowNodeEventStatus() != null))
				alreadyProcessingBool = dataObject.getData().getWorkflowPattern().getWorkflowNodeEventStatus().get(node.getNodeAttributes().getNodeId() + "_" + currEvent);

			if (alreadyProcessingBool == null)
			{
				// Store / Remove data object from DO queue
				preEventExecution(dataObject, node, currEvent, workflowMgr, storedWorkflowId, storedNodeId);

				// Update Form Values based on data from previous nodes
				dataObject = wfMgr.getDoManager().updateFormValuesWithDBData(dataObject);

				WorkflowComponent.log(0, "[Firing Execution of event][" + currEvent + "] \n on " + node + "\nDATA OBJECT \n" + dataObject);
				WorkflowComponent.log(0, "[dummyExecution]" + dummyExecution);
				WorkflowComponent.log(0, "[node.getNodeType()]" + node.getNodeType());

				if ((dummyExecution) && (NodeType.INPUT_NODE.equals(node.getNodeType())))
				{
					completedGracefully = false;
					setStopExecution(true);
				}
				else
				{
					// Fire node event - NODE EVENT MANAGER
					nEvMgr.executeNodeFunctions(
							node, currEvent, 
							wfMgr.getRouter(), 
							dataObject, null, node, 
							wfMgr.getToolCompletionObserver(), 
							wfMgr);

					// WorkflowComponent.log(0, "[AFTER EXECUTION OF EVENT; DATA OBJECT IS]\n" + dataObject);
					// After completing execution of event, set event execution status appropriately. For thread commands, this status is set by ToolCompletionObserver. - NODE EVENT MANAGER / TOOL COMPLETION OBSERVER

					// Post execution steps
					completedGracefully = postEventExecution(dataObject, node, currEvent, workflowMgr, storedWorkflowId, storedNodeId);
					if (!completedGracefully) break;
				}
			}
			else
			{
				completedGracefully = false;
				setStopExecution(true);
			}
		}
		// }
		
		return completedGracefully;
	}

	
	public boolean postEventExecution(IHICData dataObject, NodeObject node, NodeEvents currEvent, IWorkflowManager workflowMgr, String storedWorkflowId, String storedNodeId)
	{
		boolean completedGracefully = true;
		WorkflowManager wfMgr = (WorkflowManager)workflowMgr;
		
		try
		{
			if (storedWorkflowId == null)
				storedWorkflowId = dataObject.getData().getWorkflowPattern().getWorkflowPattern();
			if (storedNodeId == null)
				storedNodeId = dataObject.getData().getWorkflowPattern().getWorkflowNode();
		}
		catch (NullPointerException npe)
		{
//			npe.printStackTrace();
		}
		
		// Check event execution status of DO - NODE MANAGER / TOOL COMPLETION OBSERVER
		Boolean completed = dataObject.getData().getWorkflowPattern().getWorkflowNodeEventStatus().get(node.getNodeAttributes().getNodeId() + "_" + currEvent);
	
//		WorkflowComponent.log(0, "[completed]" + completed);
		
		// If event execution status is set to "complete", check error - NODE MANAGER / TOOL COMPLETION OBSERVER
		if ((completed != null) && (completed.booleanValue()))
		{
			// If event returns an error, send it to HangingObject List for that workflow - NODE MANAGER / TOOL COMPLETION OBSERVER, WORKFLOW MANAGER
			if (dataObject.getData().getStatus().equals(CoreConstants.DATAOBJECT_ERROR_STATUS))
			{
				// TODO - Add to Hanging Objects
			}
			// If not - NODE MANAGER / TOOL COMPLETION OBSERVER
			else
			{
				// If it is LAST event in Execution List - NODE MANAGER / TOOL COMPLETION OBSERVER
				if (currEvent.equals(NodeEventInfo.getLastEvent()))
				{
//					WorkflowComponent.log(0, "[REMOVING DO FROM NODE]" + storedNodeId);
					
					if ((storedWorkflowId != null) && (storedNodeId != null))
					{
						// Remove DO from DOList from Node
						wfMgr.getDoManager().removeDataObjectFromNode(storedWorkflowId, storedNodeId, dataObject);
					}
					
					// Get out of the loop & Return to WorkflowManager for deciding next node and firing it. - NODE MANAGER / TOOL COMPLETION OBSERVER, WORKFLOW MANAGER
				}
				
				// If it is FIRST event in Execution List
				if (currEvent.equals(NodeEventInfo.getStartEvent()))
				{
					
				}
				
				// If it is STOP_AT event in Execution List
				if (currEvent.equals(NodeEventInfo.getExecutionStopEvent()))
				{
					// If Node Type is Input
					if (node.getNodeType().equals(NodeType.INPUT_NODE))
					{
						// Get out of the loop - NODE MANAGER
						setStopExecution(true);
						completedGracefully = false;
					}
				}
			}
		}
		else
		{
			// Do not go to next event until execution of current event is completed.
			setStopExecution(true);
			completedGracefully = false;
		}
		
		return completedGracefully;
	}
	
	
	public void preEventExecution(IHICData dataObject, NodeObject node, NodeEvents currEvent,
			IWorkflowManager workflowMgr, String storedWorkflowId, String storedNodeId)
	{
		WorkflowManager wfMgr = (WorkflowManager)workflowMgr;
		
		// Create WorkflowPattern for storing data
		prepareDOForWorkflowData(dataObject);
		
		dataObject.getData().getWorkflowPattern().getWorkflowNodeEventStatus().put(node.getNodeAttributes().getNodeId() + "_" + currEvent, false);
		
		try
		{
			if (storedWorkflowId == null)
				storedWorkflowId = dataObject.getData().getWorkflowPattern().getWorkflowPattern();
			if (storedNodeId == null)
				storedNodeId = dataObject.getData().getWorkflowPattern().getWorkflowNode();
		}
		catch (NullPointerException npe)
		{
		}
		
		// If it is LAST event in Execution List - NODE MANAGER / TOOL COMPLETION OBSERVER
		if (currEvent.equals(NodeEventInfo.getLastEvent()))
		{
			/*WorkflowComponent.log(0, "[REMOVING DO FROM NODE]" + storedNodeId);
			
			if ((storedWorkflowId != null) && (storedNodeId != null))
			{
				// Remove DO from DOList from Node
				wfMgr.getDoManager().removeDataObjectFromNode(storedWorkflowId, storedNodeId, dataObject);
			}*/
			
			
			// Get out of the loop & Return to WorkflowManager for deciding next node and firing it. - NODE MANAGER / TOOL COMPLETION OBSERVER, WORKFLOW MANAGER
		}
		
		// If it is FIRST event in Execution List
		else if (currEvent.equals(NodeEventInfo.getStartEvent()))
		{
			// Update workflow / node information in DataObject - NODE MANAGER
			dataObject.getData().getWorkflowPattern().setWorkflowPattern(node.getWorkflowAttributes().getId());
			dataObject.getData().getWorkflowPattern().setWorkflowNode(node.getNodeAttributes().getNodeId());
			
			// Get new WorkflowId and WorkflowNodeId to store DO on this node
			String newWorkflowId = dataObject.getData().getWorkflowPattern().getWorkflowPattern();
			String newNodeId = dataObject.getData().getWorkflowPattern().getWorkflowNode();
			
			WorkflowComponent.log(0, "[STORING DO ON NODE]" + newNodeId);

			// Store DO to DOList on Node
			wfMgr.getDoManager().storeDataObjectOnNode(newWorkflowId, newNodeId, dataObject);
			
			// Update status of data object according to existing node
			dataObject.getData().setStatus(node.getDataObject().getStatus());
		}
		
		// Set status for each event in data object
		setStatusForEvent(dataObject, wfMgr, currEvent);
		/*if ((dataObject.getData() != null) && (dataObject.getData().getWorkflowPattern() != null)
				&& (dataObject.getData().getWorkflowPattern().getWorkflowPattern() != null)
				&& (dataObject.getData().getWorkflowPattern().getWorkflowNode() != null))
		{
			wfMgr.getDoManager().updateDataObjectStatus(
					dataObject.getData().getWorkflowPattern().getWorkflowPattern(), 
					dataObject.getData().getWorkflowPattern().getWorkflowNode(), 
					dataObject, 
					""+currEvent.ordinal());
		}*/
	}
	
	
	public IHICData setStatusForEvent(IHICData dataObject, IWorkflowManager workflowManager,
			NodeEvents currEvent)
	{
		WorkflowManager wfMgr = (WorkflowManager) workflowManager;
		
		// Set status for each event in data object
		if ((dataObject.getData() != null) && (dataObject.getData().getWorkflowPattern() != null)
				&& (dataObject.getData().getWorkflowPattern().getWorkflowPattern() != null)
				&& (dataObject.getData().getWorkflowPattern().getWorkflowNode() != null))
		{
			wfMgr.getDoManager().updateDataObjectStatus(
					dataObject.getData().getWorkflowPattern().getWorkflowPattern(), 
					dataObject.getData().getWorkflowPattern().getWorkflowNode(), 
					dataObject, 
					""+currEvent.ordinal());
		}
		
		return dataObject;
	}
	
	
	public boolean isStopExecution()
	{
		return stopExecution;
	}

	private void setStopExecution(boolean stopExecution)
	{
		this.stopExecution = stopExecution;
	}
	
	
	private void prepareDOForWorkflowData(IHICData dataObject)
	{
		if (dataObject.getData() == null)
		{
			IData data = new Data();
			dataObject.setData(data);
		}
		if (dataObject.getData().getWorkflowPattern() == null)
		{
			IWorkflowPattern wPatt = new WorkflowPattern();
			dataObject.getData().setWorkflowPattern(wPatt);
		}
	}


	public IHICData performActionGetInfoFromNode(NodeObject currNode, IHICData dataObject, List<String> functions,
			IWorkflowManager workflowMgr, NodeEvents event) throws WorkflowComponentException
	{
		WorkflowManager wfMgr = (WorkflowManager) workflowMgr;
		
		nEvMgr.executeFunctions(
				wfMgr.getRouter(), dataObject, currNode, functions, currNode,
				wfMgr.getToolCompletionObserver(), event);
		
		return dataObject;
	}
	
}
