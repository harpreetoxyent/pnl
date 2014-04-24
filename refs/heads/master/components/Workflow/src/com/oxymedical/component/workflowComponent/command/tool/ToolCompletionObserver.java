package com.oxymedical.component.workflowComponent.command.tool;

import java.util.List;

import com.oxymedical.component.appadmin.model.resources_app.Dataobject;
import com.oxymedical.component.workflowComponent.IWorkflowComponent;
import com.oxymedical.component.workflowComponent.WorkflowComponent;
import com.oxymedical.component.workflowComponent.constants.WorkflowConstant;
import com.oxymedical.component.workflowComponent.dataobject.util.DataObjectListUtil;
import com.oxymedical.component.workflowComponent.db.impl.DataObjectQueueImpl;
import com.oxymedical.component.workflowComponent.db.impl.ErrorDBImpl;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.nodeInfo.NodeObject;
import com.oxymedical.component.workflowComponent.nodeInfo.NodeType;
import com.oxymedical.component.workflowComponent.nodemanager.NodeEventInfo;
import com.oxymedical.component.workflowComponent.nodemanager.NodeEvents;
import com.oxymedical.component.workflowComponent.workflowManager.WorkflowManager;
import com.oxymedical.core.commonData.Data;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.commonData.IWorkflowPattern;
import com.oxymedical.core.commonData.WorkflowPattern;
import com.oxymedical.core.constants.CoreConstants;

public class ToolCompletionObserver implements IToolCompletionObserver
{
	IWorkflowComponent _workflowComponent;
	WorkflowManager _wm;
	NodeEvents _currEvent;
	NodeObject _currNode;
	
	public ToolCompletionObserver(IWorkflowComponent workflowComponent)
	{
		this._workflowComponent = workflowComponent;
		_wm = ((WorkflowComponent)_workflowComponent).getWorkflowManager();
	}
	
	@Override
	public void update(IObservable observable, Object obj)
	{
		if (observable instanceof InvokeComponentThreadCommand)
		{
			if (obj instanceof IHICData)
			{
				IHICData hicData = (IHICData) obj;
				
				// Get current workflow id and node id
				String workflowId = hicData.getData().getWorkflowPattern().getWorkflowPattern();
				String workflowNodeId = hicData.getData().getWorkflowPattern().getWorkflowNode();

				/*
				 * &&&&&&&&&&&&&&&&&&&&&& PRINTING &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
				 */
				DataObjectListUtil.printDataObject(hicData, "THIS IS WHAT IS RECEIVED AFTER TOOL COMPLETION", "*");
				WorkflowComponent.log(0, "[workflowId]" + workflowId 
							+ "\n[nodeId]" + workflowNodeId
							+ "\n[NODE_EXECUTION_STATUS]" + ((workflowNodeId.indexOf(WorkflowConstant.NODENAME_SUFFIX_FOR_INPUT) > 0) 
						? WorkflowConstant.NODE_EXECUTION_STATUS_WAITING 
								: WorkflowConstant.NODE_EXECUTION_STATUS_PROGRESS));
				/*
				 * &&&&&&&&&&&&&&&&&&&&&& PRINTING &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
				 */
				
				
				
				/*
				 * Update meta data information to database after completion of
				 * tool execution
				 */
				hicData.getData().getWorkflowPattern().setWorkflowNode(workflowNodeId);
				hicData.getData().getWorkflowPattern().setWorkflowPattern(workflowId);
				
				// this is added to handle the exception for the tool
				boolean isError = false;
				if(hicData.getData().getStatus()!=null)
				{
					String errorString=hicData.getData().getStatus();
					if(errorString.equalsIgnoreCase(CoreConstants.DATAOBJECT_ERROR_STATUS))
					{
						WorkflowComponent.log(0, "AN ERROR HAS OCCURED IN TOOL EXECUTION. THE ERROR IS"
								+ "\n********************************************************\n"
								+ hicData.getData().getReturnMessage()
								+ "\n********************************************************\n");
						isError = true;
						hicData.getData().setStatus(null);
					}
				}
				
				try
				{
					int maxTrials = 5, currTrial = 0; 
					Dataobject doDB = null;
					
					while (currTrial < maxTrials)
					{
						currTrial = currTrial + 1;
						
						List<Object[]> output = DataObjectQueueImpl.checkDataObjectForUser(hicData, null);

						if ((output != null) && (output.size() > 0))
							doDB = (Dataobject) output.get(0)[0];

						WorkflowComponent.log(0, "[***************************************************************]"
							+ "\n[***** THIS IS WHAT IS RETRIEVED FROM DATABASE MATCHING DO *****]"
							+ "\n[***************************************************************]");

						if (doDB != null)
						{
							WorkflowComponent.log(0, "[getDatapattern]" 
									+ "\n[getFormpattern]" + doDB.getFormpattern()
									+ "\n[getNodeexecutionstatus]" + doDB.getNodeexecutionstatus()
									+ "\n[getStatus]" + doDB.getStatus()
									+ "\n[getUniqueid]" + doDB.getUniqueid()
									+ "\n[getUserid]" + doDB.getUserid()
									+ "\n[getUserpatterns]" + doDB.getUserpatterns()
									+ "\n[getId]" + doDB.getId()
									+ "\n[getWorkflowinfo]" + doDB.getWorkflownodeinfo().getWorkflowinfo().getName()
									+ "\n[getNodeinfo]" + doDB.getWorkflownodeinfo().getNodeinfo().getNodename());
						}
						else
						{
							WorkflowComponent.log(0, "[RETRIEVED DATA OBJECT FROM DATABASE IS ---NULL---]");
						}

						WorkflowComponent.log(0, "[***************]"
							+ "\n[***** END *****]"
							+ "\n[***************]");
						
						if (doDB != null) break;
						
						try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
					}
					
					if (doDB != null)
					{  
						//change done for the error.
						if(isError)
						{
							WorkflowComponent.log(0, "[THERE IS AN ERROR - UPDATING STATUS OF DO]");
							this._wm.getDoManager()
							.updateDataObjectFromNodeOnGivenStatus(workflowId, workflowNodeId, hicData,WorkflowConstant.NODE_EXECUTION_STATUS_ERROR);
							WorkflowComponent.log(0, "[THERE IS AN ERROR - STORING ERROR DATA IN ERROR TABLE]");
							ErrorDBImpl.addErrorInfoForDataObject(doDB, hicData.getData().getReturnMessage());
							hicData.getData().setFormPattern(null);
							
						}
						else
						{
							WorkflowComponent.log(0, "[********** Going for appending data object meta data to database]");
							// Update meta data information to database
							this._wm.getDoManager().appendToDataObjectMetaData(
									workflowId, 
									workflowNodeId, 
									hicData,
									doDB);
						}
					}

				}
				catch (WorkflowComponentException e)
				{
					WorkflowComponent.log(3, "[!!!!!ERROR!!!!! WHILE STORING DATA OBJECT META DATA TO DATABASE]");
					e.printStackTrace();
				}
				
			
				
				//changes done for the error
				if(!isError)
				{
					markCommandExecutionCompletion(hicData, _currNode, _currEvent);
					
					boolean executionCompleted = true;
					
					WorkflowComponent.log(0, "[TOOL COMPLETION OBSERVER][UPDATE][$$$$$ STARTING EXECUTION OF NEW IMPLEMENTATION]");
					WorkflowComponent.log(0, "[_currEvent]" + _currEvent);
					
					// Call Node Manager - Perform Post Execution
					executionCompleted = 
						_wm.getNodeManager().postEventExecution(
							hicData, _currNode, _currEvent, _wm, 
							_currNode.getWorkflowAttributes().getId(), 
							_currNode.getNodeAttributes().getNodeId());
					WorkflowComponent.log(0, "[executionCompleted AFTER postEventExecution]" + executionCompleted);
					if (!executionCompleted) return;

					// Check current event
					List<NodeEvents> list = NodeEventInfo.getSuccessorExecutionEvents(_currEvent);
					
					// Complete other events of current node
					if ((list != null) && (list.size() > 1))
					{
						WorkflowComponent.log(0, "[getSuccessorExecutionEvents list.size() for '" + _currEvent + "' ]" + list.size());
						try
						{
							WorkflowComponent.log(0, "[STARTING EXECUTION OF REMAINING EVENTS OF CURRENT NODE STARTING WITH EVENT - ]" + list.get(1));
							executionCompleted = _wm.getNodeManager().startNodeExecution(hicData, _currNode, list.get(1), _wm);
						}
						catch (WorkflowComponentException e)
						{
							WorkflowComponent.log(3, "[!!!!!ERROR!!!!! WHILE PERFORMING EXECUTION OF REMAINING TASKS.][_wm.getNodeManager().startNodeExecution(hicData, _currNode, '" + list.get(0) + "' , _wm);]");
							e.printStackTrace();
						}
					}
					WorkflowComponent.log(0, "[executionCompleted AFTER NodeManager.startNodeExecution]" + executionCompleted);
					if (!executionCompleted) return;

					
					
					
					
					
					
//					NodeObject newNode = null;
//					try
//					{
//						newNode = _wm.getWorkflowCollection(hicData.getApplication()).getNextNodes(workflowId, workflowNodeId).get(0);
//					}
//					catch (NullPointerException e1)
//					{
////						e1.printStackTrace();
//					}
//					/*catch (WorkflowComponentException e1)
//					{
//						e1.printStackTrace();
//					}*/
//					
//					if ((newNode != null) && (newNode.getNodeType().equals(NodeType.ACTION_NODE)))
					{
						// Make sure whether _currEvent is of destinationNode or of sourceNode
						boolean isSourceNode = true;
						NodeEvents stopEvent = NodeEventInfo.getExecutionStopEvent();
						if (_currEvent.equals(stopEvent))
						{
							isSourceNode = false;
						}
						else
						{
							if ((list != null) && (list.contains(stopEvent)))
							{
								isSourceNode = false;
							}
							else
							{
								isSourceNode = true;
							}
						}
						WorkflowComponent.log(0, "[isSourceNode]" + isSourceNode);
	
						// Call Workflow Manager for executing Next Node
						if (isSourceNode)
						{
							try
							{
								WorkflowComponent.log(0, "[STARTING EXECUTION OF NEXT NODE][GOING FOR _wm.executeNextNode]");
								hicData = _wm.updateDOForNextNode(hicData);
								
//								NodeObject newNode = _wm.getNextNode(hicData, null);
								NodeObject newNode = getNextNodeFromWorkflowCollection(hicData, _currNode, null, null);
								
								// For case where _currNode is lastNode
//								if (!_currNode.equals(newNode))
								if (newNode != null)
								{
									if (NodeType.ACTION_NODE.equals(newNode.getNodeType()))
									{
										_currNode = newNode;
										executionCompleted = _wm.executeNextNode(hicData, null);
									}
									else
									{
										// Just set the status. Don't execute the event.
										executionCompleted = _wm.executeNextNode(hicData, null, false);
									}
								}
								else
								{
									executionCompleted = false;
								}
							}
							catch (WorkflowComponentException e)
							{
								WorkflowComponent.log(3, "[!!!!!ERROR!!!!! WHILE EXECUTING NEXT NODE][executeNextNode(hicData, null)]");
								e.printStackTrace();
							}
						}
						WorkflowComponent.log(0, "[executionCompleted AFTER executeNextNode]" + executionCompleted);
						if (!executionCompleted) return;
						
						
						// If current node is last node, then fire Exit method here itself.
						NodeObject newNode = getNextNodeFromWorkflowCollection(hicData, _currNode, null, null);
						
						if (newNode == null) // It means _currNode is last node in workflow
						{
							try
							{
								_wm.startNodeExecution(hicData, null, false);
							}
							catch (WorkflowComponentException e)
							{
								e.printStackTrace();
							}
						}
						// Otherwise
						else if (_currNode.getNodeType().equals(NodeType.ACTION_NODE))
						{
							// Invoke Workflow if current node is Action Node
							try
							{
								_wm.invokeWorkflowForNextNode(hicData, false);
//								_wm.invokeWorkflow(hicData);
							}
							catch (WorkflowComponentException e)
							{
								e.printStackTrace();
							}
						}
					}
					
					
//					
//					// Remove DO from current node
//					this._wm.getDoManager()
//						.removeDataObjectFromNode(workflowId, workflowNodeId, hicData);
//					
//					
//					/*
//					 * Execute the next Node in the Workflow
//					 */
//					// Fetch next node from current node.
//					NodeObject nextNO = null;
//					try
//					{
//						nextNO = this._wm.getNextNode(hicData, null);
//					}
//					catch (WorkflowComponentException e)
//					{
//						e.printStackTrace();
//					}
//
//
//					if (nextNO != null)
//					{
//						// Set status
//						hicData.getData().setStatus(nextNO.getDataObject().getStatus());
//
//						// Set Form Pattern
//						String nextForm = nextNO.getFormPatternString();
//
//						if (nextForm != null)
//						{
//							if (nextForm.indexOf(",") >= 0) nextForm = nextForm.substring(0, nextForm.indexOf(","));
//						}
//						WorkflowComponent.log(0, "[nextForm]" + nextForm);
//						hicData.getData().getFormPattern().setFormId(nextForm);
//
//						WorkflowComponent.log(0, "[nextNO.getDataObject().getStatus()]" + nextNO.getDataObject().getStatus());
//						if (nextNO.getDataObject().getStatus().indexOf(WorkflowConstant.NODENAME_SUFFIX_FOR_INPUT) < 0)
//						{
//							/*
//							 * For Batch mode where there is no input screen.
//							 * Invoke workflow
//							 */
//							this._workflowComponent.invokeWorkflow(hicData);
//						}
//						else
//						{
//							String nextWorkflowNode = nextNO.getNodeAttributes().getNodeId();
//							WorkflowComponent.log(0, "[nextWorkflowNode]" + nextWorkflowNode);
//							hicData.getData().getWorkflowPattern().setWorkflowNode(nextWorkflowNode);
//							/*
//							 * Do not invoke workflow. Just store the DO on next
//							 * node of workflow. <br />
//							 * 
//							 * Reason: Next node contains code for moving to
//							 * next screen; invoking code for next node will
//							 * throw an exception in Executions class since this
//							 * thread being a separate thread from main thread.
//							 */
//							/*
//							 * &&&&&&&&&&&&&&&&&&&&&& PRINTING &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
//							 */
//							DataObjectListUtil.printDataObject(hicData, "THIS IS WHAT IS BEING STORED INTO DATABASE FOR NEXT NODE", "*");
//							WorkflowComponent.log(0, "[workflowId]" + workflowId);
//							WorkflowComponent.log(0, "[NEXT WorkflowNode]" + nextWorkflowNode);
//							/*
//							 * &&&&&&&&&&&&&&&&&&&&&& PRINTING &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
//							 */
//							this._workflowComponent.getDataObjectManager()
//							.storeDataObjectOnNode(workflowId, nextWorkflowNode, hicData);
//						}
//					}
				}
			}
		}
	}

	
	public IWorkflowComponent getWorkflowComponent()
	{
		return this._workflowComponent;
	}

	public void setCurrentEvent(NodeEvents currEvent)
	{
		this._currEvent = currEvent;
	}
	
	public void setCurrentNode(NodeObject currNode)
	{
		this._currNode = currNode;
	}

	private IHICData markCommandExecutionCompletion(IHICData hicData, NodeObject currNode, NodeEvents event)
	{
		if (hicData.getData() == null)
		{
			IData data = new Data();
			hicData.setData(data);
		}
		if (hicData.getData().getWorkflowPattern() == null)
		{
			IWorkflowPattern wPatt = new WorkflowPattern();
			hicData.getData().setWorkflowPattern(wPatt);
		}
		
		hicData.getData().getWorkflowPattern().addWorkflowNodeEventStatus(currNode.getNodeAttributes().getNodeId() + "_" + event, true);
		return hicData;
	}
	
	
	private NodeObject getNextNodeFromWorkflowCollection(IHICData hicData, NodeObject currNode,
			String currWorkflowName, String currNodeName)
	{
		NodeObject nextNode = null;
		try
		{
			nextNode = _wm.getWorkflowCollection(hicData.getApplication()).getNextNodes(_currNode.getWorkflowAttributes().getId(), _currNode.getNodeAttributes().getNodeId()).get(0);
		}
		catch (NullPointerException e1) { }
		return nextNode;
	}
	
	public static void main(String[] args)
	{
		WorkflowComponent.log(0, "[Inside main]");
		int maxTrials = 5, currTrial = 0;
		
		WorkflowComponent.log(0, "[Starting While loop]");
		while (currTrial < maxTrials)
		{
			WorkflowComponent.log(0, "[Inside While] Current Trial: " + currTrial);
			currTrial = currTrial + 1;
		}
	}

	
}
