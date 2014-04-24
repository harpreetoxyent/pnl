package com.oxymedical.component.workflowComponent.command;

import com.oxymedical.component.workflowComponent.command.tool.NodeDOQueue;

public class PerformWorkflowToolOperationCommand extends BaseWorkflowToolCommand/* implements IWorkflowToolCommand*/
{
	WorkflowToolOperation _operation = null;
	
	public PerformWorkflowToolOperationCommand(WorkflowToolOperation operation) 
	{ 
		setOperation(operation);
	}


	public synchronized void execute()
	{
		// No operation to perform; return
		if (_operation == null) return;
		
		if (queues.containsKey(_currNode))
		{
			NodeDOQueue queue = queues.get(_currNode);
			
			// No queue present for current node; return
			if (queue == null) return;
			
			boolean result = queue.performAction(_operation, this._data, this._currNode, this._function);
			getHICData().getData().setReturnedData(result);
		}
	}


	public void setOperation(WorkflowToolOperation operation)
	{
		this._operation = operation;
	}
}
