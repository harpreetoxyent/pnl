package com.oxymedical.component.appadmin.model.resources_app;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Nodeinfo implements Serializable
{
	private Integer id;
	private String formpatternid;
	private String datapatternid;
	private String userpatternid;
	private String scenario;
	private String info;
	private String data;
	private String nodename;
	private String nodetype;
	private Nodedetail nodedetail;
	private Set<Workflownodeinfo> workflownodeinfos = new HashSet(0);
	private Set<Nodeconnector> nodeconnectorsByToNodeId = new HashSet(0);
	private Set<Nodeconnector> nodeconnectorsByFromNodeId = new HashSet(0);


	public Integer getId()
	{
		return this.id;
	}


	public void setId(Integer id)
	{
		this.id = id;
	}


	public String getFormpatternid()
	{
		return this.formpatternid;
	}


	public void setFormpatternid(String formpatternid)
	{
		this.formpatternid = formpatternid;
	}


	public String getDatapatternid()
	{
		return this.datapatternid;
	}


	public void setDatapatternid(String datapatternid)
	{
		this.datapatternid = datapatternid;
	}


	public String getUserpatternid()
	{
		return this.userpatternid;
	}


	public void setUserpatternid(String userpatternid)
	{
		this.userpatternid = userpatternid;
	}


	public String getScenario()
	{
		return this.scenario;
	}


	public void setScenario(String scenario)
	{
		this.scenario = scenario;
	}


	public String getInfo()
	{
		return this.info;
	}


	public void setInfo(String info)
	{
		this.info = info;
	}


	public String getData()
	{
		return this.data;
	}


	public void setData(String data)
	{
		this.data = data;
	}


	public String getNodename()
	{
		return this.nodename;
	}


	public void setNodename(String nodename)
	{
		this.nodename = nodename;
	}


	public Nodedetail getNodedetail()
	{
		return this.nodedetail;
	}


	public void setNodedetail(Nodedetail nodedetail)
	{
		this.nodedetail = nodedetail;
	}


	public Set<Workflownodeinfo> getWorkflownodeinfos()
	{
		return this.workflownodeinfos;
	}


	public void setWorkflownodeinfos(Set<Workflownodeinfo> workflownodeinfos)
	{
		this.workflownodeinfos = workflownodeinfos;
	}


	public Set<Nodeconnector> getNodeconnectorsByToNodeId()
	{
		return this.nodeconnectorsByToNodeId;
	}


	public void setNodeconnectorsByToNodeId(Set<Nodeconnector> nodeconnectorsByToNodeId)
	{
		this.nodeconnectorsByToNodeId = nodeconnectorsByToNodeId;
	}


	public Set<Nodeconnector> getNodeconnectorsByFromNodeId()
	{
		return this.nodeconnectorsByFromNodeId;
	}


	public void setNodeconnectorsByFromNodeId(Set<Nodeconnector> nodeconnectorsByFromNodeId)
	{
		this.nodeconnectorsByFromNodeId = nodeconnectorsByFromNodeId;
	}
	public String getNodetype()
	{
		return nodetype;
	}


	public void setNodetype(String nodetype)
	{
		this.nodetype = nodetype;
	}
}