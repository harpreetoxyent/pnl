package com.oxymedical.component.workflowComponent.command.tool;

import java.util.Vector;

import com.oxymedical.component.workflowComponent.command.WorkflowToolInfo;
import com.oxymedical.component.workflowComponent.command.WorkflowToolOperation;
import com.oxymedical.component.workflowComponent.dataobject.util.DataObjectListUtil;
import com.oxymedical.component.workflowComponent.nodeInfo.NodeObject;
import com.oxymedical.component.workflowComponent.nodemanager.NodeEvents;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.router.IRouter;

public class InvokeComponentThreadCommand  extends BaseToolCommand implements IObservable, /*IWorkflowCommand,*/ Runnable
{
	private boolean changed = false;
	private Vector<IObserver> obs;
	/*private IObserver _observer;
	private NodeObject _currNode;*/
	
/*	private IRouter _router = null;
	private IHICData _data = null;
	private NodeObject _nextNode = null;
	private NodeObject _currNode = null;*/
	private IToolCompletionObserver _observer = null;
	/*private String _function = null;
	private NodeEvents _event = null;
	
	private IWorkflowTool wfTool = null;
	private boolean cancelled = false;*/
	

	public InvokeComponentThreadCommand(IRouter router, IHICData data, NodeObject nextNode, String function,
			NodeObject currNode, IObserver observer, NodeEvents event)
	{
		super(router, data, nextNode, function,	currNode, event);
		/*setFunction(function);
		setHICData(data);
		setHICRouter(router);
		setNextNode(nextNode);
		setCurrentNode(currNode);*/
		setObserver(observer);

		DataObjectListUtil.printDataObject(data, "THIS IS WHAT IS SENT TO COMPONENT FOR " + function + "EXECUTION", null);
		
		// Each thread should have its own Observer, so that observer
		// data is not overwritten with each thread.
		// Take WorkflowComponent instance from current Observer and set it in
		// new Observer instance.
		_observer = new ToolCompletionObserver(_observer.getWorkflowComponent());
		_observer.setCurrentEvent(getNodeEvent());
		_observer.setCurrentNode(getCurrentNode());
		
		obs = new Vector<IObserver>();
		this.addObserver(_observer);
		
		
		
	
		/*this._currNode = currNode;*/
	}

	
	public void execute()
	{
		// Perform Operation
		/*WorkflowComponent.log(0, "[InvokeComponentThreadCommand][execute][_function]" + _function);
		
		// Get Class for Tool from this._function
		String[] params = StringUtil.splitString(_function, ",");
		if ((params != null) && (params.length >= 3))
		{
			WorkflowComponent.log(0, "[InvokeComponentThreadCommand][execute][clsName]" + params[2]);
			String clsName = params[2];
			String compId = params[0];
			if (clsName != null)
			{
				clsName = clsName.trim().replaceAll("\"", "");
				WorkflowComponent.log(0, "[InvokeComponentThreadCommand][execute][clsName]" + clsName);
				try
				{
					Object obj = NOLISRuntimeHandler.getComponent(compId, clsName);
					
					// Create an instance of class and store it in IWorkflowTool instance variable
					Class<?> cls = Class.forName(clsName);
					Object obj = cls.newInstance();
					WorkflowComponent.log(0, "[InvokeComponentThreadCommand][execute][obj]" + obj.getClass().getName());
					if (obj instanceof IWorkflowTool)
					{
						wfTool = (IWorkflowTool) obj;
						
						// Invoke execute method of class
						WorkflowComponent.log(0, "[InvokeComponentThreadCommand][execute][CALLING WORKFLOW TOOL EXECUTION METHOD][wfTool]" + wfTool);
						this._data = wfTool.execute(this.getHICData());
						WorkflowComponent.log(0, "---this._data-----"+this._data);
						WorkflowComponent.log(0, "[InvokeComponentThreadCommand][execute][this._data]" + this.getHICData());
					}
				}
				catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				}
				catch (WorkFlowToolException e)
				{
					e.printStackTrace();
					WorkflowComponent.log(0, "---this._data---WorkFlowToolException--"+this._data);
					getHICData().getData().setReturnMessage(e.getLocalizedMessage());
					getHICData().getData().setStatus("error");
				}
				catch (InstantiationException e)
				{
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
				// Any other non-expected Exception due to cancellation of task.
				catch (Exception e)
				{
					e.printStackTrace();
					WorkflowComponent.log(0, "---this._data--Exception-"+this._data);
					getHICData().getData().setReturnMessage(e.getLocalizedMessage());
					getHICData().getData().setStatus("error");
				}
			}
		}
		
		// Operation was cancelled by user
		if (cancelled)
		{
			getHICData().getData().setReturnMessage("Tool aborted manually.");
			getHICData().getData().setStatus("error");
		}
	*/	
		super.execute();
		// After execution is complete,
		// Inform Observers about completion
		setChanged();
		notifyObservers(getHICData());
	}
	
	public void run()
	{
		execute();
	}
	
	public void performAction(WorkflowToolOperation operation)
	{
		if (operation.equals(WorkflowToolOperation.STOP_OPERATION))
		{
			this.setCancelled(true);
		}
	}
	
	
	public Object getInfo(WorkflowToolInfo info)
	{
		if (getWorkflowTool() != null)
		{
			if (info.equals(WorkflowToolInfo.PERCENT_COMPLETE))
			{
				return getWorkflowTool().getPercentComplete();
			}
		}
		
		return null;
	}
	

	/*public void setFunction(String function)	{	this._function = function;	}
	public void setHICRouter(IRouter router)	{	this._router = router;		}
	public void setHICData(IHICData data)		{	this._data = data;			}
	public void setNextNode(NodeObject node)	{	this._nextNode = node;		}
	public void setCurrentNode(NodeObject node)	{	this._currNode = node;		}*/
	public void setObserver(IObserver observer)	{	this._observer = (IToolCompletionObserver)observer;	}
	/*public void setNodeEvent(NodeEvents event)	{	_event = event;				}
	public IHICData getHICData()				{	return this._data;			}*/

	public synchronized void addObserver(IObserver o)
	{
		if (o == null)
			throw new NullPointerException();
		if (!obs.contains(o))
		{
			obs.addElement(o);
		}
	}

	public synchronized void deleteObserver(IObserver o)
	{
		obs.removeElement(o);
	}

	public void notifyObservers()
	{
		notifyObservers(null);
	}

	public void notifyObservers(Object obj)
	{
		/*
		 * a temporary array buffer, used as a snapshot of the state of current
		 * Observers.
		 */
		Object[] arrLocal;

		synchronized (this)
		{
			/*
			 * We don't want the Observer doing callbacks into arbitrary code
			 * while holding its own Monitor. The code where we extract each
			 * Observable from the Vector and store the state of the Observer
			 * needs synchronization, but notifying observers does not (should
			 * not). The worst result of any potential race-condition here is
			 * that: 1) a newly-added Observer will miss a notification in
			 * progress 2) a recently unregistered Observer will be wrongly
			 * notified when it doesn't care
			 */
			if (!changed)
				return;
			arrLocal = obs.toArray();
			clearChanged();
		}

		for (int i = arrLocal.length - 1; i >= 0; i--)
			((IObserver) arrLocal[i]).update(this, obj);
	}

	public synchronized void deleteObservers()
	{
		obs.removeAllElements();
	}

	protected synchronized void setChanged()
	{
		changed = true;
	}

	protected synchronized void clearChanged()
	{
		changed = false;
	}

	public synchronized boolean hasChanged()
	{
		return changed;
	}

	public synchronized int countObservers()
	{
		return obs.size();
	}

}
