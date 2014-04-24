package com.oxymedical.component.appadmin.model.resources_app;

import java.io.Serializable;

public class Nodeconnector implements Serializable
{
	private Integer id;
	private String connectorname;
	private Workflowinfo workflowinfo;
	private Nodeinfo nodeinfoByToNodeId;
	private Nodeinfo nodeinfoByFromNodeId;


	public Integer getId()
	{
		return this.id;
	}


	public void setId(Integer id)
	{
		this.id = id;
	}


	public String getConnectorname()
	{
		return this.connectorname;
	}


	public void setConnectorname(String connectorname)
	{
		this.connectorname = connectorname;
	}


	public Workflowinfo getWorkflowinfo()
	{
		return this.workflowinfo;
	}


	public void setWorkflowinfo(Workflowinfo workflowinfo)
	{
		this.workflowinfo = workflowinfo;
	}


	public Nodeinfo getNodeinfoByToNodeId()
	{
		return this.nodeinfoByToNodeId;
	}


	public void setNodeinfoByToNodeId(Nodeinfo nodeinfoByToNodeId)
	{
		this.nodeinfoByToNodeId = nodeinfoByToNodeId;
	}


	public Nodeinfo getNodeinfoByFromNodeId()
	{
		return this.nodeinfoByFromNodeId;
	}


	public void setNodeinfoByFromNodeId(Nodeinfo nodeinfoByFromNodeId)
	{
		this.nodeinfoByFromNodeId = nodeinfoByFromNodeId;
	}
}