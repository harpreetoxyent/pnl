package com.oxymedical.core.workflow;

import com.oxymedical.core.commonData.IHICData;

public interface IWorkflowTool {

	public IHICData execute(IHICData inputDataObject) throws WorkFlowToolException;
	
	public int getPercentComplete();
	
	public IHICData init(IHICData inputDataObject) throws WorkFlowToolException;
}
