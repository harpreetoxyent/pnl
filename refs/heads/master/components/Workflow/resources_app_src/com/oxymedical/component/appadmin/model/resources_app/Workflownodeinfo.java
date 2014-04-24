package com.oxymedical.component.appadmin.model.resources_app;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Workflownodeinfo implements Serializable
{
	private Integer id;
	private Boolean isStartNode;
	private String status;
	private Set<Nodeevent> nodeevents = new HashSet(0);
	private Set<Dataobject> dataobjects = new HashSet(0);
	private Workflowinfo workflowinfo;
	private Nodeinfo nodeinfo;


	public Integer getId()
	{
		return this.id;
	}


	public void setId(Integer id)
	{
		this.id = id;
	}


	public Boolean getIsStartNode()
	{
		return this.isStartNode;
	}


	public void setIsStartNode(Boolean isStartNode)
	{
		this.isStartNode = isStartNode;
	}


	public String getStatus()
	{
		return this.status;
	}


	public void setStatus(String status)
	{
		this.status = status;
	}


	public Set<Nodeevent> getNodeevents()
	{
		return this.nodeevents;
	}


	public void setNodeevents(Set<Nodeevent> nodeevents)
	{
		this.nodeevents = nodeevents;
	}


	public Set<Dataobject> getDataobjects()
	{
		return this.dataobjects;
	}


	public void setDataobjects(Set<Dataobject> dataobjects)
	{
		this.dataobjects = dataobjects;
	}


	public Workflowinfo getWorkflowinfo()
	{
		return this.workflowinfo;
	}


	public void setWorkflowinfo(Workflowinfo workflowinfo)
	{
		this.workflowinfo = workflowinfo;
	}


	public Nodeinfo getNodeinfo()
	{
		return this.nodeinfo;
	}


	public void setNodeinfo(Nodeinfo nodeinfo)
	{
		this.nodeinfo = nodeinfo;
	}
}