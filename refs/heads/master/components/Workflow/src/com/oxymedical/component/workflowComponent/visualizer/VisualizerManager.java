package com.oxymedical.component.workflowComponent.visualizer;

import java.util.Hashtable;

import com.oxymedical.component.appadmin.model.resources_app.Workflowinfo;
import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.workflowComponent.WorkflowComponent;
import com.oxymedical.component.workflowComponent.dataobject.util.DataObjectListUtil;
import com.oxymedical.component.workflowComponent.db.impl.WorkFlowImpl;
import com.oxymedical.component.workflowComponent.hic.NOLISRuntimeHandler;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.commonData.app.ApplicationConstant;
import com.oxymedical.core.constants.CoreConstants;

public class VisualizerManager
{
	public static void callVisualizer(IHICData dataObject)
	{
		IComponent visTool = null;
		String workflownodestatus = dataObject.getData().getStatus();
		
		//System.out.println("dataObject=="+dataObject.getData().getWorkflowPattern().getWorkflowNodeStatus());
		try
		{
			
		Hashtable<String, Object> formValues = dataObject.getData().getFormPattern().getFormValues();
		String workflowName = (String)formValues.get(ApplicationConstant.KEY_WORKFLOW_NAME);
		String nodeName = (String)formValues.get(ApplicationConstant.KEY_NODE_NAME);
		DataObjectListUtil.updateFormValuesWithDBData(dataObject, workflowName, nodeName);
		Workflowinfo workflowinfo=WorkFlowImpl.getWorkflowBasedOnStatus(workflownodestatus);
		if(workflowinfo!=null)
		{
		// TODO - Complete implementation once the database structure is completed.
		String visualToolClass = workflowinfo.getNodedetail().getPacakge().trim(); // WorkFlowImpl.getWorkflowBasedOnWorkflowName(workflow).getVisualTool().getPackage;
		String visualCompId =visualToolClass.trim();
		Object obj = NOLISRuntimeHandler.getComponent(visualCompId, visualToolClass, true);
			
			// Create an instance of class and store it in IWorkflowTool instance variable
			/*Class<?> cls = Class.forName(visualToolClass);
			Object obj = cls.newInstance();*/
			
			WorkflowComponent.log(0, "[VisualizerManager][openVisualizer][obj]" + obj.getClass().getName());
			if (obj instanceof IComponent)
			{
				visTool = (IComponent) obj;

				// Invoke execute method of class
				WorkflowComponent.log(0, "[VisualizerManager][openVisualizer][CALLING RUN METHOD][visTool]" + visTool);

				visTool.setHicData(dataObject);
				visTool.run();
				WorkflowComponent.log(0, "[VisualizerManager][openVisualizer][this._data]" + dataObject);
			}
		}	
		}
		/*catch (ClassNotFoundException cnfe) { updateDOForError(dataObject, cnfe); }
		catch (IllegalAccessException iae) { updateDOForError(dataObject, iae); }
		catch (InstantiationException ie) { updateDOForError(dataObject, ie); }*/
		catch (Exception e) { updateDOForError(dataObject, e); }
	}
	
	
	private static void updateDOForError(IHICData dataObject, Exception e)
	{
		dataObject.getData().setStatus(CoreConstants.DATAOBJECT_ERROR_STATUS);
		dataObject.getData().setReturnMessage(e.getMessage());
	}
}
