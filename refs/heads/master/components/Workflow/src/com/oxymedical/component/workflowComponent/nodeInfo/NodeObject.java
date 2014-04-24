/**
 * 
 */
package com.oxymedical.component.workflowComponent.nodeInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.oxymedical.component.workflowComponent.WorkflowComponent;
import com.oxymedical.component.workflowComponent.nodeBuilder.FormPatternAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.IDataObjectAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.IDataPatternAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.IEventAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.IFormPatternAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.INodeAttribute;
import com.oxymedical.component.workflowComponent.nodeBuilder.INodeAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.IUserPatternAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.IWorkflowAttributes;
import com.oxymedical.component.workflowComponent.nodeBuilder.IWorkflowNodeStatus;
import com.oxymedical.component.workflowComponent.nodeBuilder.NodeAttribute;
import com.oxymedical.component.workflowComponent.nodeBuilder.UserPatternAttributes;
import com.oxymedical.component.workflowComponent.nodeConnectorBuilder.INodeConnector;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.userdata.IUserPattern;

/**
 * @author vka
 *
 */
public class NodeObject 
{
	private IDataObjectAttributes dataObject = null;
	private Set<IDataPatternAttributes> dataPatternObject = null;
	private IEventAttributes eventAttributeObject = null;
	private Set<IFormPatternAttributes> formPatternObject = null;
	private INodeAttribute nodeAttr = null;
	private Set<IUserPatternAttributes> userPatternObject = null;
	private IWorkflowAttributes workflowAttr = null;
	private Set<INodeConnector> nodeConnector = null;
	private NodeType nodeType = NodeType.INPUT_NODE;
	
	Logger logger = Logger.getLogger(NodeObject.class);
	
	/**
	 * @return the nodeConnectorObject
	 */
	public Set<INodeConnector> getNodeConnectors()
	{
		return nodeConnector;
	}
	/**
	 * @param nodeConnectorObject the nodeConnectorObject to set
	 */
	public void setNodeConnectors(Set<INodeConnector> nodeConnectors)
	{
		this.nodeConnector = nodeConnectors;
	}


	public void addNodeConnector(INodeConnector nodeConnector)
	{
		if (this.nodeConnector == null)
			this.nodeConnector = new HashSet<INodeConnector>();
		this.nodeConnector.add(nodeConnector);
	}

	/**
	 * @return the dataObject
	 */
	public IDataObjectAttributes getDataObject()
	{
		return dataObject;
	}
	/**
	 * @param dataObject the dataObject to set
	 */
	public void setDataObject(IDataObjectAttributes dataObject)
	{
		this.dataObject = dataObject;
	}
	/**
	 * @return the dataPatternObject
	 */
	public Set<IDataPatternAttributes> getDataPatternObject()
	{
		return dataPatternObject;
	}
	/**
	 * @param dataPatternObject the dataPatternObject to set
	 */
	public void setDataPatternObject(Set<IDataPatternAttributes> dataPatternObject)
	{
		this.dataPatternObject = dataPatternObject;
	}


	public void addDataPatternObject(IDataPatternAttributes dataPatternObject)
	{
		if (this.dataPatternObject == null)
			this.dataPatternObject = new HashSet<IDataPatternAttributes>();
		this.dataPatternObject.add(dataPatternObject);
	}

	public String getDataPatternString()
	{
		if (dataPatternObject == null)
			return null;

		final String sep = ",";
		String retVal = "";
		
		Iterator<IDataPatternAttributes> dataPatternAttrib = dataPatternObject.iterator();
		while (dataPatternAttrib.hasNext())
		{
			IDataPatternAttributes dataPatternAtt = (IDataPatternAttributes) dataPatternAttrib.next();
			if(dataPatternAtt.getDataPatternId()!=null)
			{
			retVal = retVal + (retVal.equals("") ? "" : sep) + dataPatternAtt.getDataPatternId();
			}
			else
				return null;
		}
		return retVal;
	}
	
	/**
	 * @return the eventAttributeObject
	 */
	public IEventAttributes getEventAttributeObject() {
		return eventAttributeObject;
	}
	/**
	 * @param eventAttributeObject the eventAttributeObject to set
	 */
	public void setEventAttributeObject(IEventAttributes eventAttributeObject) {
		this.eventAttributeObject = eventAttributeObject;
	}
	/**
	 * @return the formPatternObject
	 */
	public Set<IFormPatternAttributes> getFormPatternObject() {
		return formPatternObject;
	}
	/**
	 * @param formPatternObject the formPatternObject to set
	 */
	public void setFormPatternObject(Set<IFormPatternAttributes> formPatternObject) {
		this.formPatternObject = formPatternObject;
	}
	
	public void addFormPatternObject(IFormPatternAttributes formPatternObject) 
	{
		if (this.formPatternObject == null) this.formPatternObject = new HashSet<IFormPatternAttributes>();
		this.formPatternObject.add(formPatternObject);
	}

	public String getFormPatternString()
	{
		if (formPatternObject == null) return null;

		final String sep = ",";
		String retVal = "";
		
		Iterator<IFormPatternAttributes> formPatternAttrib = formPatternObject.iterator();
		while (formPatternAttrib.hasNext())
		{
			IFormPatternAttributes formPatternAtt = (IFormPatternAttributes) formPatternAttrib.next();
			if( formPatternAtt.getFormPatternId()!=null)
			retVal = retVal + (retVal.equals("") ? "" : sep) + formPatternAtt.getFormPatternId();
			else
				return null;
		}
		return retVal;
	}

	/**
	 * @return the nodeObject
	 */
	public INodeAttribute getNodeAttributes() {
		return nodeAttr;
	}
	/**
	 * @param nodeObject the nodeObject to set
	 */
	public void setNodeAttributes(NodeAttribute nodeAttributes) {
		this.nodeAttr = nodeAttributes;
	}
	/**
	 * @return the userPatternObject
	 */
	public Set<IUserPatternAttributes> getUserPatternObject() {
		return userPatternObject;
	}
	/**
	 * @param userPatternObject the userPatternObject to set
	 */
	public void setUserPatternObject(Set<IUserPatternAttributes> userPatternObject) {
		this.userPatternObject = userPatternObject;
	}
	
	public void addUserPatternObject(IUserPatternAttributes userPatternObject)
	{
		if (this.userPatternObject == null) this.userPatternObject = new HashSet<IUserPatternAttributes>();
		this.userPatternObject.add(userPatternObject);
	}
	
	public String getUserPatternString()
	{
		if (userPatternObject == null) return null;

		final String sep = ",";
		String retVal = "";
		List<String> upList = new ArrayList<String>();
		
		Iterator<IUserPatternAttributes> userPatternAttrib = userPatternObject.iterator();
		while (userPatternAttrib.hasNext())
		{
			IUserPatternAttributes userPatternAtt = (IUserPatternAttributes) userPatternAttrib.next();
			upList.add(userPatternAtt.getUserPatternId());
//			retVal = retVal + (retVal.equals("") ? "" : ",") + userPatternAtt.getUserPatternId();
		}
		
		Collections.sort(upList);
		for (int i=0; i<upList.size(); i++)
		{
			if(upList.get(i)!=null)
			retVal = retVal + (retVal.equals("") ? "" : sep) + upList.get(i);
			else
				return null;
		}

		return retVal;
	}
	
	public IWorkflowAttributes getWorkflowAttributes()
	{
		return workflowAttr;
	}

	/**
	 * @param workflowAttributes
	 *            the workflowNodeStatus to set
	 */
	public void setWorkflowAttributes(IWorkflowAttributes workflowAttributes)
	{
		this.workflowAttr = workflowAttributes;
	}

	
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof IHICData)
		{
			IHICData hicData = (IHICData)obj;
			return (isDataPatternEqual(hicData) && isFormPatternEqual(hicData) && isUserPatternEqual(hicData));
		}
		else if (obj.getClass() == this.getClass())
		{
			NodeObject no = (NodeObject) obj;
			return (nodeAttr.equals(no.nodeAttr)) 
				&& (workflowAttr.equals(no.workflowAttr));
		}
		return false;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 31 * hash + nodeAttr.hashCode();
		hash = 31 * hash + workflowAttr.hashCode();
		return hash;
	}
	
	private boolean isDataPatternEqual(IHICData hicData)
	{
		logger.debug("[IHICData][DataPattern]".concat(hicData.getData().getDataPattern().getDataPatternId()));
		
		Iterator<IDataPatternAttributes> dataPatterns = dataPatternObject.iterator();
		while (dataPatterns.hasNext())
		{
			IDataPatternAttributes dataPatternAtt = (IDataPatternAttributes) dataPatterns.next();
			logger.debug("[NodeManager][DataPattern]".concat(dataPatternAtt.getDataPatternId()));
			if (dataPatternAtt.getDataPatternId().equals(hicData.getData().getDataPattern().getDataPatternId())) return true;
		}

		return false;
	}

	private boolean isFormPatternEqual(IHICData hicData)
	{
		logger.debug("[IHICData][FormPattern]".concat(hicData.getData().getFormPattern().getFormId()));
		logger.debug("");

		Iterator<IFormPatternAttributes> formPatterns = formPatternObject.iterator();
		while (formPatterns.hasNext())
		{
			FormPatternAttributes formPatternAtt = (FormPatternAttributes) formPatterns.next();
			logger.debug("[NodeManager][UserPattern]".concat(formPatternAtt.getFormPatternId()));
			if (formPatternAtt.getFormPatternId().equals(hicData.getData().getFormPattern().getFormId())) return true;
		}

		return false;
	}

	private boolean isUserPatternEqual(IHICData hicData)
	{
		logger.debug("[IHICData][UserPattern]".concat(hicData.getData().getUserId()));
		logger.debug("");
		
		Iterator<IUserPatternAttributes> userPatternAttrib = userPatternObject.iterator();
		while (userPatternAttrib.hasNext())
		{
			UserPatternAttributes userPatternAtt = (UserPatternAttributes) userPatternAttrib.next();
			logger.debug("[NodeManager][UserPattern]".concat(userPatternAtt.getUserPatternId()));

			Iterator<IUserPattern> userPatterns = hicData.getData().getUserPatterns().iterator();
			while (userPatterns.hasNext())
			{
				String userPatternId = userPatterns.next().getUserPatternId();
				if (userPatternAtt.getUserPatternId().equals(userPatternId)) return true;
			}
		}
		return false;
	}


	/**
	 * Dummy method called from Rule Component when a working copy matches
	 * production copy.
	 */
	public void fireOnNode()
	{
//		WorkflowComponent.log(0, "[NodeObject][fireOnNode][Inside function. Workflow object called successfully.]");
//		WorkflowComponent.log(0, "[NodeObject][fireOnNode][Inside function. Workflow object called successfully.]");
//		WorkflowComponent.log(0, "[*****************************************************************************]");
	}


	public NodeType getNodeType()
	{
		return nodeType;
	}


	public void setNodeType(NodeType nodeType)
	{
		this.nodeType = nodeType;
	}
	
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("****** NODE OBJECT ****** \n").append("[NODE ID]").append(this.nodeAttr.getNodeId());
		sb.append("\n").append("[WORKFLOW ID]").append(this.workflowAttr.getId());
		sb.append("\n").append("[FORM PATTERN]").append(this.getFormPatternString());
		sb.append("\n").append("[DATA PATTERN]").append(this.getDataPatternString());
		if (this.eventAttributeObject != null)
		{
			sb.append("\n").append("[EVENT ON ENTER]").append(this.eventAttributeObject.getOnEnter());
			sb.append("\n").append("[EVENT ON NODE]").append(this.eventAttributeObject.getOnNode());
			sb.append("\n").append("[EVENT ON EXIT]").append(this.eventAttributeObject.getOnExit());
		}
		sb.append("\n").append("[NODE TYPE]").append(this.nodeType);
		sb.append("\n").append("[DATA OBJECT STATUS]").append(this.dataObject.getStatus());
		
		return sb.toString();
	}
	
}
