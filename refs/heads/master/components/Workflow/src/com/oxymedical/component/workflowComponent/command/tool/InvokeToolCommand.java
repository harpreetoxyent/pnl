/**
 * 
 */
package com.oxymedical.component.workflowComponent.command.tool;

import com.oxymedical.component.workflowComponent.nodeInfo.NodeObject;
import com.oxymedical.component.workflowComponent.nodemanager.NodeEvents;
import com.oxymedical.core.commonData.Data;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.commonData.IWorkflowPattern;
import com.oxymedical.core.commonData.WorkflowPattern;

/**
 * @author hs
 *
 */
public class InvokeToolCommand extends BaseToolCommand
{
	public void execute()
	{
		super.execute();
		markCommandExecutionCompletion(getHICData(), getCurrentNode(), getNodeEvent());
	}
	
	
	IHICData markCommandExecutionCompletion(IHICData hicData, NodeObject currNode, NodeEvents event)
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
}
