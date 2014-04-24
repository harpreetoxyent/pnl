package com.oxymedical.component.workflowComponent.command;

import com.oxymedical.component.workflowComponent.command.tool.InvokeToolCommand;



public class CommandFactory
{
	public static IWorkflowCommand getWorkflowCommand(String function)
	{
		IWorkflowCommand com = null;
		String funcDet = function.trim().toLowerCase();
		if (funcDet.indexOf("move") == 0)
		{
			com = new MoveCommand();
		}
		else if (funcDet.indexOf("invokecomponentthread")==0)
		{
			com = new InvokeWorkflowToolCommand(); /*InvokeWorkflowToolCommand.getInstance();*/
		}
		else if (funcDet.indexOf("invokecomponent")==0)
		{
			com = new InvokeComponentCommand();
		}
		else if ((funcDet.indexOf("changedostatus") == 0) || (funcDet.indexOf("setdatastatus") == 0))
		{
			com = new ChangeStatusCommand();
		}
		else if (funcDet.indexOf("getworkflowtoolstatus")==0)
		{
			com = new GetWorkflowToolInfoCommand(WorkflowToolInfo.PERCENT_COMPLETE);
		}
		else if (funcDet.indexOf("stopworkflowtool")==0)
		{
			com = new PerformWorkflowToolOperationCommand(WorkflowToolOperation.STOP_OPERATION);
		}
		else if (funcDet.indexOf("invoketool")==0)
		{
			com = new InvokeToolCommand();
		}
		return com;
	}
}
