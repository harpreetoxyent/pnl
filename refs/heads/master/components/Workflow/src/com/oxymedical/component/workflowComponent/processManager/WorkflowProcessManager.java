/**
 * 
 */
package com.oxymedical.component.workflowComponent.processManager;

import java.util.Hashtable;

import com.oxymedical.component.workflowComponent.dataObjectManager.DataObjectManager;
import com.oxymedical.component.workflowComponent.nodeInfo.WorkflowNodeCollection;
import com.oxymedical.core.commonData.Application;
/**
 * @author vka
 *
 */
public class WorkflowProcessManager implements IWorkflowProcessManager
{
	
	Hashtable<String, WorkflowNodeCollection> workflowCollection ;
	DataObjectManager dataObjectManager = new DataObjectManager();
	
	
	public void createProcessDefinition(Application processDefnXMLFile)
	{
		
	}

/*	public void createProcessDefinition(IHICData hicData, WorkflowOutPut workflowOutput, Event event) throws WorkflowComponentException
	{
		
		IEventAttributes eventObject = workflowOutput.getEventObject();
		String buttonId = workflowOutput.getButtonId();
		String[] eventArray = new String[2]; 
		eventArray[0] = eventObject.getOnNode();
		eventArray[1] = eventObject.getOnExit();
		this.runEventAction(hicData,event, eventArray, buttonId);
		
		WorkflowNodeCollection workflowCollectionObject = workflowOutput.getWorkflowDataCollection();
		NodeObject nodeObj = dataObjectManager.setActiveNodeFlowObject(workflowCollectionObject);
		NodeManager nodeManager = nodeObj.getNodeManager();
		String[] enterEventArray =new String[1];
		IEventAttributes eventObj = nodeManager.getEventAttributeObject();
		enterEventArray[0] = eventObj.getOnEnter();	
		this.runEventAction(hicData,event, enterEventArray, buttonId);		
			
	}		
*/	

/*	public void runEventAction(IHICData hicData,Event event, String []eventArray,String buttonId) throws WorkflowComponentException
	{
		for( int counter=0; counter<eventArray.length;counter++)
		{
			String eventString = eventArray[counter];
			Hashtable<String, List<String>> eventSet = this.getExecuteMethod(eventString);
			List<String> methodList = eventSet.get(buttonId);
			if(methodList ==null)
			{
				throw (new WorkflowComponentException("Method list is null in class WorkflowProcessManager"));
			}
			Iterator<String> iterateMethod = methodList.iterator();
			while(iterateMethod.hasNext())
			{
				String methodName = (String) iterateMethod.next();
				this.invokeComponent(hicData, event, methodName);
				
				
			}
					
		}
	
	}*/
	
/*	public void invokeComponent(IHICData hicData, Event event, String componentMethod)
	{
		
		ISource source = hicData.getSrcHistoryList().getLast();
		source.setMethodName(componentMethod);
					
		try
		{
			
			if ( null != componentMethod)
			{
				if (componentMethod.equalsIgnoreCase("saveForm"))
				{
					event.setEventId("com.oxymedical.component.db.DBComponent");
					event.setMethod(componentMethod);
					//HICServiceUtil.informComponent(hicData, event);
						
				}
				if(componentMethod.equalsIgnoreCase("Move"))
				{
					event.setEventId("com.oxymedical.component.renderer.RendererComponent");
					//HICServiceUtil.informComponent(hicData, event);
				}
			}
		} catch (Exception e)
			{
				e.printStackTrace();
			}
	}*/
	
/*	public Hashtable<String, List<String>> getExecuteMethod(String eventString) throws WorkflowComponentException
	{
		String key = null;
		Hashtable<String, List<String>> eventSet = new Hashtable<String, List<String>>();
		List<String> actionList = null;
		if(eventString ==null)
		{
			throw (new WorkflowComponentException("Event action String is null in class WorkflowProcessManager"));
		}
		String[] actionValue = eventString.trim().split("executeOnNode");
		for (int counter = 0; counter < actionValue.length; counter++)
		{
			String splitString = actionValue[counter].trim();
			if(splitString.length()>0)
			{
    			String splitValue[] = splitString.trim().split(",");
    			actionList = new ArrayList<String>();
    			for (int innerCounter = 0; innerCounter < splitValue.length; innerCounter++)
    			{
    				String splitStr = splitValue[innerCounter];
    				if(innerCounter==0)
    				{
    					key = splitStr.substring(2, splitStr.length()-1);
    					
    					
    				}
    				else if(innerCounter >=2)
    				{
    					splitStr = splitStr.trim().substring(1,splitStr.indexOf("("));
    					actionList.add(splitStr);
    					
    				}
    			}
    		
    			eventSet.put(key, actionList);
			}
		}
			return eventSet;
	}*/
	
}
