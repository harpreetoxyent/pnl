package com.oxymedical.component.workflowComponent.command;

import java.util.Hashtable;

import com.oxymedical.component.workflowComponent.nodeInfo.NodeObject;
import com.oxymedical.component.workflowComponent.nodemanager.NodeEvents;
import com.oxymedical.core.commonData.Data;
import com.oxymedical.core.commonData.FormPattern;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IFormPattern;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.commonData.IWorkflowPattern;
import com.oxymedical.core.commonData.WorkflowPattern;
import com.oxymedical.core.router.IRouter;

public class BaseCommand
{
	IHICData initializeHICData(IHICData hicData)
	{
    	IData data = hicData.getData();
    	IFormPattern fp;
    	Hashtable<String, Object> formVals;
    	if (data == null) 
    	{
    		data = new Data();
    		fp = new FormPattern();
    		formVals = new Hashtable<String, Object>();
    		fp.setFormValues(formVals);
    		data.setFormPattern(fp);
    		hicData.setData(data);
    	}
    	
    	fp = data.getFormPattern();
    	if (fp == null) 
    	{
    		fp = new FormPattern();
    		formVals = new Hashtable<String, Object>();
    		fp.setFormValues(formVals);
    		data.setFormPattern(fp);
    	}
    	
    	formVals = fp.getFormValues();
    	if (formVals == null) 
    	{
    		formVals = new Hashtable<String, Object>();
    		fp.setFormValues(formVals);
    	}
    	return hicData;
	}
	
	void executeEachFunction(String functionName, IHICData hicData, IRouter router)
	{
//		Object[] objectArray = {hicData};
//		NOLISRuntime.FireEvent(functionName, objectArray, PublicationScope.Global);
		router.routeToComponentEx(hicData,functionName);
	}

	IHICData updateHICData(IHICData data, String componentId, String methodName, String componentClass, String rawData)
	{
		data = updateHICData(data, componentId, methodName, componentClass);
		data.getData().setRawData(rawData);
		return data;
	}

	IHICData updateHICData(IHICData data, String componentId, String methodName, String componentClass)
	{
		data.getData().setMethodName(methodName);
		data.getData().setInvokeComponentId(componentId);
		data.getData().setInvokeComponentClass(componentClass);
		return data;
	}
	
	IHICData updateHICData(IHICData data, String functionParams)
	{
		functionParams = functionParams.trim();
		functionParams = functionParams.substring(1, functionParams.length()-1);
		String[] functionParam = functionParams.split(",");


		data = updateHICData(data, functionParam[0].trim().replaceAll("\"", ""),
				functionParam[1].trim().replaceAll("\"", ""),
				functionParam[2].trim().replaceAll("\"", ""),
				functionParam[3].trim().replaceAll("\"", ""));

		return data;
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
