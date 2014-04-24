/**
 * 
 */
package com.oxymedical.component.workflowComponent.command.tool;

import com.oxymedical.component.workflowComponent.WorkflowComponent;
import com.oxymedical.component.workflowComponent.command.IWorkflowCommand;
import com.oxymedical.component.workflowComponent.hic.NOLISRuntimeHandler;
import com.oxymedical.component.workflowComponent.nodeInfo.NodeObject;
import com.oxymedical.component.workflowComponent.nodeInfo.NodeType;
import com.oxymedical.component.workflowComponent.nodemanager.NodeEvents;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.router.IRouter;
import com.oxymedical.core.stringutil.StringUtil;
import com.oxymedical.core.workflow.IWorkflowTool;
import com.oxymedical.core.workflow.WorkFlowToolException;

/**
 * @author hs
 *
 */
public abstract class BaseToolCommand implements IWorkflowCommand
{
	private IRouter _router = null;
	private IHICData _data = null;
	private NodeObject _nextNode = null;
	private NodeObject _currNode = null;
	private String _function = null;
	private NodeEvents _event = null;
	
	private IWorkflowTool wfTool = null;
	private boolean cancelled = false;
	
	
	
	public BaseToolCommand()
	{
		super();
	}
	
	public BaseToolCommand(IRouter router, IHICData data, NodeObject nextNode, String function,
			NodeObject currNode, NodeEvents event)
	{
		super();
		_router = router;
		_data = data;
		_nextNode = nextNode;
		_currNode = currNode;
		_function = function;
		_event = event;
	}
	
	
	public void setFunction(String function)			{	this._function = function;	}
	public void setHICRouter(IRouter router)			{	this._router = router;		}
	public void setHICData(IHICData data)				{	this._data = data;			}
	public void setNextNode(NodeObject node)			{	this._nextNode = node;		}
	public void setCurrentNode(NodeObject node)			{	this._currNode = node;		}
	public NodeObject getCurrentNode()					{	return this._currNode;		}
	public void setNodeEvent(NodeEvents event)			{	_event = event;				}
	public NodeEvents getNodeEvent()					{	return _event;				}
	public IHICData getHICData()						{	return this._data;			}
	public boolean isCancelled()						{	return cancelled;			}
	public void setCancelled(boolean cancelled)			{	this.cancelled = cancelled;	}
	public IWorkflowTool getWorkflowTool()				{	return wfTool;				}
	public void setWorkflowTool(IWorkflowTool wfTool)	{	this.wfTool = wfTool;		}
	
	
	public void execute()
	{
		// Perform Operation
		WorkflowComponent.log(0, "[BaseToolCommand][execute][_function]" + _function);
		
		// Get Class for Tool from this._function
		String[] params = StringUtil.splitString(_function, ",");
		if ((params != null) && (params.length >= 3))
		{
			WorkflowComponent.log(0, "[BaseToolCommand][execute][clsName]" + params[2]);
			String clsName = params[2];
			String compId = params[0];
			if (clsName != null)
			{
				clsName = clsName.trim().replaceAll("\"", "");
				WorkflowComponent.log(0, "[BaseToolCommand][execute][clsName]" + clsName);
				try
				{
					Object obj = NOLISRuntimeHandler.getComponent(compId, clsName);
					
					// Create an instance of class and store it in IWorkflowTool instance variable
					/*Class<?> cls = Class.forName(clsName);
					Object obj = cls.newInstance();*/
					WorkflowComponent.log(0, "[BaseToolCommand][execute][obj]" + obj.getClass().getName());
					if (obj instanceof IWorkflowTool)
					{
						wfTool = (IWorkflowTool) obj;
						
						// Invoke execute method of class
						WorkflowComponent.log(0, "[BaseToolCommand][execute][CALLING WORKFLOW TOOL EXECUTION METHOD][wfTool]" + wfTool);
						WorkflowComponent.log(0, "---_get NodeType----"+_currNode.getNodeType());
						if (NodeType.ACTION_NODE.equals(_currNode.getNodeType()))
						{
							WorkflowComponent.log(0, "--inside the action----");
							this._data = wfTool.execute(this.getHICData());
						}
						else if (NodeType.INPUT_NODE.equals(_currNode.getNodeType()))
						{
							if (NodeEvents.ENTER_NODE.equals(_event))
							{
								WorkflowComponent.log(0, "---inside the onEnter---");
								this._data = wfTool.init(this.getHICData());
								WorkflowComponent.log(0, "after execture on enter----");
							}
							else if (NodeEvents.EXIT_NODE.equals(_event))
							{
								this._data = wfTool.execute(this.getHICData());
							}
							else if (NodeEvents.ON_NODE.equals(_event))
							{
								//this._data = wfTool.process(this.getHICData());
							}
						}
						WorkflowComponent.log(0, "[BaseToolCommand][execute][this._data]" + this.getHICData());
					}
				}
				/*catch (ClassNotFoundException e)
				{
					getHICData().getData().setReturnMessage(e.getLocalizedMessage());
					getHICData().getData().setStatus("error");
					e.printStackTrace();
				}*/
				catch (WorkFlowToolException e)
				{
					getHICData().getData().setReturnMessage(e.getLocalizedMessage());
					getHICData().getData().setStatus("error");
					e.printStackTrace();
				}
				/*catch (InstantiationException e)
				{
					getHICData().getData().setReturnMessage(e.getLocalizedMessage());
					getHICData().getData().setStatus("error");
					e.printStackTrace();
				}*/
				/*catch (IllegalAccessException e)
				{
					getHICData().getData().setReturnMessage(e.getLocalizedMessage());
					getHICData().getData().setStatus("error");
					e.printStackTrace();
				}*/
				// Any other non-expected Exception due to cancellation of task.
				catch (Exception e)
				{
					getHICData().getData().setReturnMessage("Tool aborted manually.");
					getHICData().getData().setStatus("error");
					e.printStackTrace();
				}
			}
		}
		
		// Operation was cancelled by user
		if (cancelled)
		{
			getHICData().getData().setReturnMessage("Tool aborted manually.");
			getHICData().getData().setStatus("error");
		}
	}
	
	

}
