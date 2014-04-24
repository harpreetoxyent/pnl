package com.oxymedical.component.workflowComponent.command;

import java.util.regex.Pattern;

import com.oxymedical.component.workflowComponent.WorkflowComponent;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.nodeInfo.NodeObject;
import com.oxymedical.component.workflowComponent.nodemanager.NodeEvents;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.router.IRouter;

public class MoveCommand extends BaseCommand implements IWorkflowCommand
{
	private IRouter router;
	private IHICData data;
	private NodeObject nextNode;
	private String function;
	
	private NodeObject _currentNode;
	private NodeEvents _event;

	public void setFunction(String function)
	{
		this.function = function;
	}

	public void setHICRouter(IRouter router)
	{
		this.router = router;
	}

	public void setHICData(IHICData data)
	{
		this.data = data;
	}

	public IHICData getHICData()
	{
		return this.data;
	}

	public void setNextNode(NodeObject node)
	{
		this.nextNode = node;
	}

	public void execute()
	{
		try
		{
			String event = getEventName();
			this.data = updateHIC(this.data);
			if (event != null)
				executeEachFunction(event, this.data, this.router);
		}
		catch (WorkflowComponentException e)
		{
			e.printStackTrace();
		}
		markCommandExecutionCompletion(this.data, this._currentNode, this._event);
	}

	private String getEventName()
	{
		return "moveForm";
	}
	
	private IHICData updateHIC(IHICData hicData) throws WorkflowComponentException
	{
		hicData = initializeHICData(hicData);
		hicData.getData().getFormPattern().getFormValues().put("destinationForm", getDestinationFormName());		
		return hicData;
	}
	
	private String getDestinationFormName() throws WorkflowComponentException
	{
		// Check if invokeComponent command contains form name to move to
		if ((function.toLowerCase().indexOf("invokecomponent") >= 0) && (function.indexOf("(") > 0))
		{
			String params = function.trim().substring("invokecomponent".length());
			params = params.trim();
			params = params.substring(1, params.length()-1);
			String[] param = params.split(",");
			String destForm = param[3].trim().replaceAll("\"", "");
			if ((destForm != null) && (destForm.length() > 0) && (!destForm.equals("")))
			{
				WorkflowComponent.log(0, "[DESTINATION FORM FROM INVOKE COMPONENT]" + destForm);
				return destForm;
			}
		}
		
		// Otherwise select the form pattern from next node.
		String nextFormAtt = (this.nextNode != null) ? this.nextNode.getFormPatternString() : _currentNode.getFormPatternString();
		String currForm = this.data.getData().getFormPattern().getFormId();
    	Pattern p = Pattern.compile(",", Pattern.LITERAL);
    	String[] nextFormAttArr = p.split(nextFormAtt);
    	String destForm = "";
    	for (int i=0; i<nextFormAttArr.length; i++)
    	{
    		destForm = nextFormAttArr[i];
    		if (currForm.indexOf(destForm) < 0) break;
    	}
		WorkflowComponent.log(0, "[DESTINATION FORM FROM NEXT NODE]" + destForm);
    	if (destForm.equals("")) throw new WorkflowComponentException("Cannot perform Move action; Destination Form is not available.");
    	return destForm;
	}
	
	@Override
	public void setCurrentNode(NodeObject currentNode)
	{
		this._currentNode = currentNode;
	}

	@Override
	public void setNodeEvent(NodeEvents event)
	{
		this._event = event;
	}

}
