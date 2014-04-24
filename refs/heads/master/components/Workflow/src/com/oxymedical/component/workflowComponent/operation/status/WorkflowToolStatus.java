package com.oxymedical.component.workflowComponent.operation.status;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import com.oxymedical.component.appadmin.model.resources_app.Dataobject;
import com.oxymedical.component.appadmin.model.resources_app.Dataobjectmetadata;
import com.oxymedical.component.appadmin.model.resources_app.Nodedetail;
import com.oxymedical.component.appadmin.model.resources_app.Nodeinfo;
import com.oxymedical.component.appadmin.model.resources_app.Workflowinfo;
import com.oxymedical.component.workflowComponent.constants.WorkflowConstant;
import com.oxymedical.component.workflowComponent.db.impl.DataObjectQueueImpl;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.commonData.IWorkflowPattern;
import com.oxymedical.core.commonData.WorkflowPattern;
import com.oxymedical.core.commonData.app.ApplicationConstant;
import com.oxymedical.core.stringutil.StringUtil;

public class WorkflowToolStatus {

	
	public List<Object> getListOfDataObjectsForStatus(String queryString, String userId,
			String uniqueID, String nodeExecutionStatus)
	{
		List<Object> returnList=new LinkedList<Object>();
		List<Object> objects=null;
		try {
			if(uniqueID != null)
			{
				objects = DataObjectQueueImpl.getDataObjectsForStatusCondition(userId,uniqueID,nodeExecutionStatus);
			}
			else{
			objects=DataObjectQueueImpl.getDataObjectsForStatus(userId,nodeExecutionStatus);
			}
			List<Object> formValuesList= new LinkedList<Object>();
			if(objects!=null && objects.size()>0)
			{
				List<Object> allValues=createAllValues(objects, queryString);
				returnList.add(allValues);
				for(int i=0;i<objects.size();i++)
				{
					Object[] retObjs = (Object[])objects.get(i);
					Hashtable  values= new Hashtable();
					for (int j = 0; j < retObjs.length; j++)
					{
						Object obj=retObjs[j];
						if(obj instanceof Dataobject)
						{
							 Dataobject data= (Dataobject)obj;
							 String uniqueId=data.getUniqueid();
							 String[] pInfo=null;
							 if(uniqueId.indexOf("_")>=0)
							 {
								 pInfo=uniqueId.split("_");
							 }
							 String patientId=pInfo[0];
							 String patientMRN=pInfo[1];
							 String scheduleId=pInfo[2];
							 String status=data.getStatus();
							 String nodeExecutionstatus=data.getNodeexecutionstatus();
							 values.put(ApplicationConstant.KEY_PATIENT_ID, patientId);
							 values.put(ApplicationConstant.KEY_PATIENT_MRN, patientMRN);
							 values.put(ApplicationConstant.KEY_SCHEDULE_ID, scheduleId);
							 values.put(WorkflowConstant.INPUT_NODESTATUS,status);
							 values.put(WorkflowConstant.INPUT_NODEEXECUTIONSTATUS,nodeExecutionstatus);
							 values.put(WorkflowConstant.INPUT_UNIQUEID,uniqueId);
						}
						else if(obj instanceof Workflowinfo)
						{
							Workflowinfo workFlow= (Workflowinfo)obj;
							String workFlowName=workFlow.getName();
							 values.put(WorkflowConstant.INPUT_WORKFLOWNAME, workFlowName);
						}
						else if(obj instanceof Nodeinfo)
						{
							Nodeinfo node= (Nodeinfo)obj;
							String nodeName=node.getNodename();
							values.put(WorkflowConstant.INPUT_NODENAME, nodeName);
						}
						else if(obj instanceof Dataobjectmetadata)
						{
							Dataobjectmetadata dataobjectmetadata= (Dataobjectmetadata)obj;
							String workingArea=(String)dataobjectmetadata.getDatavalue();
							values.put(ApplicationConstant.KEY_SCHEDULE_WORK_AREA, workingArea);
						}
						else if(obj instanceof Nodedetail)
						{
							Nodedetail nodedetail= (Nodedetail)obj;
							String nodeDescription=nodedetail.getNodeDescription();
						}
						
					}
					formValuesList.add(values);
				}
				returnList.add(formValuesList);	
			}
			return returnList;
		} catch (WorkflowComponentException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
public List<Object> createAllValues(List<Object> objects,String queryString)
{
	Object[] allValues=null;
	String[] queryFields=null;
    List<Object> returnObjects=new LinkedList<Object>();
	if(queryString!=null)
	queryFields=queryString.split(",");
	if(queryFields!=null && queryFields.length>0)
	{
		for(int i=0;i<objects.size();i++)
		{
			allValues=new Object[queryFields.length+1];
			for(int j=0;j<queryFields.length;j++)
			{
				String queryField=queryFields[j];
				String[] query=StringUtil.splitString(queryField.trim(),".");
				if(query!=null && query.length>0)
				{	
					String tableName=query[0];
					String fieldName=query[1];
					Object[] retObjs = (Object[])objects.get(i);
					for (int k= 0; k < retObjs.length; k++)
					{
						Object obj=retObjs[k];
						if(obj instanceof Dataobject && tableName.equalsIgnoreCase("Dataobject"))
						{
							Dataobject data= (Dataobject)obj;
							String methodName=StringUtil.createGetterSetterMethod(fieldName.toLowerCase(),true,data.getClass());
							try {
								
								Object returnObj=evaluateMethod(methodName,data);
								allValues[j]=returnObj;
							} catch (Exception e) {
								
								e.printStackTrace();
							} 
							 break;
							
						}
						else if(obj instanceof Workflowinfo && tableName.equalsIgnoreCase("Workflowinfo"))
						{
							Workflowinfo workFlow= (Workflowinfo)obj;
							String methodName=StringUtil.createGetterSetterMethod(fieldName.toLowerCase(),true,workFlow.getClass());
							try {
								
								Object returnObj=evaluateMethod(methodName,workFlow);
								allValues[j]=returnObj;
							} catch (Exception e) {
								
								e.printStackTrace();
							} 
							 break;
							
						}
						else if(obj instanceof Nodeinfo && tableName.equalsIgnoreCase("Nodeinfo"))
						{
							Nodeinfo nodeObj= (Nodeinfo)obj;
							String methodName=StringUtil.createGetterSetterMethod(fieldName.toLowerCase(),true,nodeObj.getClass());
							try {
								
								Object returnObj=evaluateMethod(methodName,nodeObj);
								allValues[j]=returnObj;
							} catch (Exception e) {
								
								e.printStackTrace();
							} 
							 break;
						}
						else if(obj instanceof Dataobjectmetadata && tableName.equalsIgnoreCase("Dataobjectmetadata"))
						{
							Dataobjectmetadata dataobjectmetadata= (Dataobjectmetadata)obj;
							String methodName=StringUtil.createGetterSetterMethod(fieldName.toLowerCase(),true,dataobjectmetadata.getClass());
							try {
								
								Object returnObj=evaluateMethod(methodName,dataobjectmetadata);
								allValues[j]=returnObj;
							} catch (Exception e) {
								
								e.printStackTrace();
							} 
							 break;
						}
						else if(obj instanceof Nodedetail && (tableName.equalsIgnoreCase("Nodedetails")))
						{
							Nodedetail nodedetail= (Nodedetail)obj;
							String methodName=StringUtil.createGetterSetterMethod(fieldName.toLowerCase(),true,nodedetail.getClass());
							try {
								
								Object returnObj=evaluateMethod(methodName,nodedetail);
								allValues[j]=returnObj;
							} catch (Exception e) {
								
								e.printStackTrace();
							} 
							 break;
						}
						else if(obj instanceof Dataobject && (fieldName.equalsIgnoreCase(ApplicationConstant.KEY_PATIENT_ID) || fieldName.equalsIgnoreCase(ApplicationConstant.KEY_PATIENT_MRN)
								|| fieldName.equalsIgnoreCase(ApplicationConstant.KEY_SCHEDULE_ID)))
						{
							Dataobject data= (Dataobject)obj;
							 String uniqueId=data.getUniqueid();
							 String[] pInfo=null;
							 if(uniqueId.indexOf("_")>=0)
							 {
								 pInfo=uniqueId.split("_");
							 }
							 String patientId=pInfo[0];
							 String patientMRN=pInfo[1];
							 String scheduleId=pInfo[2];
							 if(fieldName.equalsIgnoreCase(ApplicationConstant.KEY_PATIENT_ID))
							 allValues[j]=patientId;
							 if(fieldName.equalsIgnoreCase(ApplicationConstant.KEY_PATIENT_MRN))
								 allValues[j]=patientMRN;
							 if(fieldName.equalsIgnoreCase(ApplicationConstant.KEY_SCHEDULE_ID))
								 allValues[j]=scheduleId;
							 break;
							 
						}
					}
				}	
			}
			returnObjects.add(allValues);
		}
	}
	return returnObjects;
}

private Object evaluateMethod(String methodName, Object ob)
throws IllegalArgumentException, SecurityException,
IllegalAccessException, InvocationTargetException,
NoSuchMethodException 
{
	  Object returnValue=null;
		try 
		{
			Object value = ob;
			Class clazz = ob.getClass();
			Method[] classMethods = clazz.getMethods();
			
			
			returnValue = (Object) clazz.getMethod(methodName ,null)
						.invoke(ob,null);
			return returnValue;
		} 
		catch (NoSuchMethodException nsme) 
		{
			return null;
		}
	}

public IHICData createHICDataForStatus(IHICData hicData,Hashtable formValues)
{
	hicData.getData().setDataPattern(null);
	hicData.getData().getFormPattern().setFormId(null);
	String status=(String)formValues.get(WorkflowConstant.INPUT_NODESTATUS)!=null?(String)formValues.get(WorkflowConstant.INPUT_NODESTATUS):null;
	String unquieId=(String)formValues.get(WorkflowConstant.INPUT_UNIQUEID)!=null ?(String)formValues.get(WorkflowConstant.INPUT_UNIQUEID):null;
	String nodeName=(String)formValues.get(WorkflowConstant.INPUT_NODENAME)!=null?(String)formValues.get(WorkflowConstant.INPUT_NODENAME):null;
	String workflowName=(String)formValues.get(WorkflowConstant.INPUT_WORKFLOWNAME)!=null ?(String)formValues.get(WorkflowConstant.INPUT_WORKFLOWNAME):null;
	IWorkflowPattern workflowPatten= new WorkflowPattern();
	if(nodeName!=null)
	workflowPatten.setWorkflowNode(nodeName);
	if(workflowName!=null)
	workflowPatten.setWorkflowPattern(workflowName);
	if(status!=null)
	workflowPatten.setWorkflowNodeStatus(status);
	if(workflowPatten!=null)
	hicData.getData().setWorkflowPattern(workflowPatten);
	if(unquieId!=null)
	hicData.setUniqueID(unquieId);
	if(status!=null)
	hicData.getData().setStatus(status);
	return hicData;
}
}
