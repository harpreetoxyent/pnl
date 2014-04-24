/**
 * 
 */
package com.oxymedical.component.workflowComponent.nodeBuilder;

/**
 * @author vka
 * 
 */
public class NodeAttribute implements INodeAttribute
{
	private String nodeId = null;
	private String isStart = null;
	private String scenario = null;

	/*
	 * Getters
	 */
	public String getIsStart()	{	return isStart;		}
	public String getScenario()	{	return scenario;	}
	public String getNodeId()	{	return nodeId;		}

	/*
	 * Setters
	 */
	public void setIsStart(String isStart)		{	this.isStart = isStart;		}
	public void setScenario(String scenario)	{	this.scenario = scenario;	}
	public void setNodeId(String nodeId)		{	this.nodeId = nodeId;		}


	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		else if (obj == null)
			return false;
		else if (this.getClass() != obj.getClass()) // This ensures that sub class != main class
			return false;

		NodeAttribute na = (NodeAttribute) obj;
		return (nodeId == na.nodeId || (nodeId != null && nodeId.equals(na.nodeId)))
				&& (isStart == na.isStart || (isStart != null && isStart.equals(na.isStart)))
				&& (scenario == na.scenario || (scenario != null && scenario.equals(na.scenario)));
	}


	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 31 * hash + (null == nodeId ? 0 : nodeId.hashCode());
		hash = 31 * hash + (null == isStart ? 0 : isStart.hashCode());
		hash = 31 * hash + (null == scenario ? 0 : scenario.hashCode());
		return hash;
	}
}
