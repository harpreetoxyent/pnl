package com.oxymedical.component.workflowComponent.command;

import com.oxymedical.component.workflowComponent.nodeInfo.NodeObject;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.router.IRouter;

public interface ICommand
{
	public void setHICData(IHICData data);
	public void setHICRouter(IRouter router);
	public void setNextNode(NodeObject node);
	public void execute();
	public IHICData getHICData();
}
