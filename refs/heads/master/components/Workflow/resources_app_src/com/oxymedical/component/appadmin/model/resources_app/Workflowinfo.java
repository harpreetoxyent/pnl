package com.oxymedical.component.appadmin.model.resources_app;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.oxymedical.component.workflowComponent.constants.WorkflowConstant;

public class Workflowinfo implements Serializable
{
	private Integer id;
	private String name;
	private String displayname;
	private String deleted;
	private Boolean isVisual;
	private Boolean isCreatedFromUi;
	private Nodedetail nodedetail;
	private Set<Workflownodeinfo> workflownodeinfos = new HashSet(0);
	private Set<Nodeconnector> nodeconnectors = new HashSet(0);


	public Integer getId()
	{
		return this.id;
	}


	public void setId(Integer id)
	{
		this.id = id;
	}


	public String getName()
	{
		return this.name;
	}


	public void setName(String name)
	{
		name=name.replaceAll("[\\\\/!.:\\s'_$%&*@#\"]","");
		this.name = name;
	}


	public String getDeleted()
	{
		return this.deleted;
	}


	public void setDeleted(String deleted)
	{
		this.deleted = deleted;
	}


	public Boolean getIsVisual()
	{
		return this.isVisual;
	}


	public void setIsVisual(Boolean isVisual)
	{
		this.isVisual = isVisual;
	}


	public Boolean getIsCreatedFromUi()
	{
		return this.isCreatedFromUi;
	}


	public void setIsCreatedFromUi(Boolean isCreatedFromUi)
	{
		this.isCreatedFromUi = isCreatedFromUi;
	}


	public Set<Workflownodeinfo> getWorkflownodeinfos()
	{
		return this.workflownodeinfos;
	}


	public void setWorkflownodeinfos(Set<Workflownodeinfo> workflownodeinfos)
	{
		this.workflownodeinfos = workflownodeinfos;
	}


	public Set<Nodeconnector> getNodeconnectors()
	{
		return this.nodeconnectors;
	}


	public void setNodeconnectors(Set<Nodeconnector> nodeconnectors)
	{
		this.nodeconnectors = nodeconnectors;
	}


	public String getDisplayname() {
		return displayname;
	}


	public void setDisplayname(String displayname) {
		this.displayname = displayname;
		this.setName(displayname);
	}


   public Nodedetail getNodedetail() {
		    return this.nodedetail;
		  }

   public void setNodedetail(Nodedetail nodedetail) {
		    this.nodedetail = nodedetail; }
}