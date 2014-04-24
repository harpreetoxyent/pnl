/**
 * 
 */
package com.oxymedical.component.workflowComponent.parser;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.dom4j.Document;

import com.oxymedical.component.workflowComponent.WorkflowComponent;
import com.oxymedical.component.workflowComponent.constants.WorkflowConstant;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.core.commonData.Application;
import com.oxymedical.core.xmlutil.XmlReader;

/**
 * @author vka
 * 
 */
public class ParserUtility
{

	private XmlReader xmlReader = null;

	public ParserUtility()
	{
		xmlReader = new XmlReader();
	}

	/**
	 * Gets the workflow definition xml document
	 * 
	 * @param Application
	 *            application
	 * @param String
	 *            workflowformId
	 * @return workflowDoc
	 * @throws WorkflowComponentException
	 */
	public Document getWorkflowPatternXmlDoc(Application application,
			String formPatternId) throws WorkflowComponentException
	{
		Document workflowDoc = null;
		try
		{
			String formPatternXmlFileSrc = application
					.getApplicationFolderPath()
					+ WorkflowConstant.WORKFLOW_FOLDER_NAME
					+ "/"
					+ formPatternId + WorkflowConstant.WORKFLOW_EXTN;
			// 	WorkflowComponent.logger.log(0," formPatternXmlFileSrc = "
			// +formPatternXmlFileSrc);
			workflowDoc = xmlReader.getDocumentFromPath(formPatternXmlFileSrc);
		}
		catch (Exception fileExp)
		{
			throw (new WorkflowComponentException(fileExp.getMessage()));
		}
		return workflowDoc;
	}

	/**
	 * Gets the event action as string and split it. 
	 * 
	 * @param String
	 *            actionString
	 * @return Hashtable<String, List<String>>
	 * @throws WorkflowComponentException
	 */
	public Hashtable<String, List<String>> setWorkflowAction(String actionString)throws WorkflowComponentException
	{
		if(actionString ==null)
		{
			throw (new WorkflowComponentException("Event action string is null"));
		}
		Hashtable<String, List<String>> eventSet = new Hashtable<String, List<String>>();
		List<String> actionList = new ArrayList<String>();
		String key = null;
		int index = actionString.indexOf("executeOnNode(");
		String[] actionValue = actionString.trim().split(";");
		for (int counter = 0; counter < actionValue.length; counter++)
		{
			String splitString = actionValue[counter];
			if (splitString.length() > 2)
			{
				String splitValue[] = splitString.substring(index + 14,
						splitString.length()).toString().split(",");
				for (int innerCounter = 0; innerCounter < splitValue.length; innerCounter++)
				{
					String splStr = splitValue[innerCounter];
					if (splStr.lastIndexOf(")") > 0)
						splStr = splStr + "\"";
					if (innerCounter == 0)
						key = splStr;
					else
					{
						actionList.add(splStr);
					}
				}
				eventSet.put(key, actionList);
			}
		}
		return eventSet;
	}

}
