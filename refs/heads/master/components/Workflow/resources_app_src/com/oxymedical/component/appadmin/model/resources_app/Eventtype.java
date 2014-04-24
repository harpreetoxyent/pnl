package com.oxymedical.component.appadmin.model.resources_app;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Eventtype implements Serializable
{
	private Integer id;
	private String eventtype;
	private Set<Nodeevent> nodeevents = new HashSet(0);


	public Integer getId()
	{
		return this.id;
	}


	public void setId(Integer id)
	{
		this.id = id;
	}


	public String getEventtype()
	{
		return this.eventtype;
	}


	public void setEventtype(String eventtype)
	{
		this.eventtype = eventtype;
	}


	public Set<Nodeevent> getNodeevents()
	{
		return this.nodeevents;
	}


	public void setNodeevents(Set<Nodeevent> nodeevents)
	{
		this.nodeevents = nodeevents;
	}
}