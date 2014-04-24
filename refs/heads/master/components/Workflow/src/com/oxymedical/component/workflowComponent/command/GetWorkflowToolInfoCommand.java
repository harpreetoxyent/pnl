package com.oxymedical.component.workflowComponent.command;

import com.oxymedical.component.workflowComponent.command.tool.NodeDOQueue;

public class GetWorkflowToolInfoCommand extends BaseWorkflowToolCommand/* implements IWorkflowToolCommand*/
{
	WorkflowToolInfo _info = null;
	Object output = null;
	
	public GetWorkflowToolInfoCommand(WorkflowToolInfo info) 
	{ 
		setInfo(info);
	}

	public synchronized void execute()
	{
		// No info to fetch; return
		if (_info == null) return;
		
		if (queues.containsKey(_currNode))
		{
			NodeDOQueue queue = queues.get(_currNode);
			
			// No queue present for current node; return
			if (queue == null) return;
			
//			if (_info.equals(WorkflowToolInfo.PERCENT_COMPLETE))
			output = queue.getInfo(_info, this._data, this._currNode, this._function);
			
			getHICData().getData().setReturnedData(output);
		}
	}


	public void setInfo(WorkflowToolInfo info)
	{
		this._info = info;
	}
	
	public Object getOutput()
	{
		return output;
	}

}
