package com.oxymedical.component.appadmin.model.resources_app;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Nodedetail implements Serializable
{
	private Integer id;
	private String action;
	private String formpattern;
	private String pacakge;
	private String deleted;
	private String nodeDescription;
	private Boolean isVisualizer;
	private String inputxml;
	
	private Set<Nodeinfo> nodeinfos = new HashSet(0);
	private Set<Workflowinfo> workflowinfos = new HashSet(0);
	private Toolcateogry toolcateogry;


	public Integer getId()
	{
		return this.id;
	}


	public void setId(Integer id)
	{
		this.id = id;
	}


	public String getAction()
	{
		return this.action;
	}


	public void setAction(String action)
	{
		this.action = action;
	}


	public String getFormpattern()
	{
		return this.formpattern;
	}


	public void setFormpattern(String formpattern)
	{
		this.formpattern = formpattern;
	}


	public String getPacakge()
	{
		return this.pacakge;
	}


	public void setPacakge(String pacakge)
	{
		this.pacakge = pacakge;
	}


	public String getDeleted()
	{
		return this.deleted;
	}


	public void setDeleted(String deleted)
	{
		this.deleted = deleted;
	}


	public String getNodeDescription()
	{
		return this.nodeDescription;
	}


	public void setNodeDescription(String nodeDescription)
	{
		this.nodeDescription = nodeDescription;
	}


	public Set<Nodeinfo> getNodeinfos()
	{
		return this.nodeinfos;
	}


	public void setNodeinfos(Set<Nodeinfo> nodeinfos)
	{
		this.nodeinfos = nodeinfos;
	}
	public Toolcateogry getToolcateogry() {
	        return this.toolcateogry;
	    }
	    
	public void setToolcateogry(Toolcateogry toolcateogry) {
	        this.toolcateogry = toolcateogry;
	    }


	public Boolean getIsVisualizer() {
		return isVisualizer;
	}


	public void setIsVisualizer(Boolean isVisualizer) {
		this.isVisualizer = isVisualizer;
	}


	public String getInputxml() {
		return inputxml;
	}


	public void setInputxml(String inputxml) {
		this.inputxml = inputxml;
	}
	
    public Set<Workflowinfo> getWorkflowinfos() {
		    return this.workflowinfos;
    }

    public void setWorkflowinfos(Set<Workflowinfo> workflowinfos) {
		    this.workflowinfos = workflowinfos; 
	}
	
	
}