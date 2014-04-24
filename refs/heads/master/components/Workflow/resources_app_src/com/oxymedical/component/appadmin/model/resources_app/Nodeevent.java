package com.oxymedical.component.appadmin.model.resources_app;

import java.io.Serializable;

public class Nodeevent implements Serializable
{
	private Integer id;
	private String params;
	private Workflownodeinfo workflownodeinfo;
	private Eventtype eventtype;


	public Integer getId()
	{
		return this.id;
	}


	public void setId(Integer id)
	{
		this.id = id;
	}


	public String getParams()
	{
		return this.params;
	}


	public void setParams(String params)
	{
		this.params = params;
	}


	public Workflownodeinfo getWorkflownodeinfo()
	{
		return this.workflownodeinfo;
	}


	public void setWorkflownodeinfo(Workflownodeinfo workflownodeinfo)
	{
		this.workflownodeinfo = workflownodeinfo;
	}


	public Eventtype getEventtype()
	{
		return this.eventtype;
	}


	public void setEventtype(Eventtype eventtype)
	{
		this.eventtype = eventtype;
	}
}