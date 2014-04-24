package com.oxymedical.component.appadmin.model.resources_app;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.sql.Date;
import java.sql.Time;

public class Dataobject implements Serializable
{
	private Integer id;
	private String status;
	private String formpattern;
	private String datapattern;
	private String userpatterns;
	private String userid;
	private String uniqueid;
	private String nodeexecutionstatus;
	private Workflownodeinfo workflownodeinfo;
	private Date toolstartdate;
	private Time toolstarttime;
	private Set<Dataobjectmetadata> dataobjectmetadatas = new HashSet(0);
    private Set<Errorinfo> errorinfos = new HashSet(0);


	public Integer getId()
	{
		return this.id;
	}


	public void setId(Integer id)
	{
		this.id = id;
	}


	public String getStatus()
	{
		return this.status;
	}


	public void setStatus(String status)
	{
		this.status = status;
	}


	public String getFormpattern()
	{
		return this.formpattern;
	}


	public void setFormpattern(String formpattern)
	{
		this.formpattern = formpattern;
	}


	public String getDatapattern()
	{
		return this.datapattern;
	}


	public void setDatapattern(String datapattern)
	{
		this.datapattern = datapattern;
	}


	public String getUserpatterns()
	{
		return this.userpatterns;
	}


	public void setUserpatterns(String userpatterns)
	{
		this.userpatterns = userpatterns;
	}


	public String getUserid()
	{
		return this.userid;
	}


	public void setUserid(String userid)
	{
		this.userid = userid;
	}


	public String getUniqueid()
	{
		return this.uniqueid;
	}


	public void setUniqueid(String uniqueid)
	{
		this.uniqueid = uniqueid;
	}


	public String getNodeexecutionstatus()
	{
		return this.nodeexecutionstatus;
	}
	

	 public Date getToolstartdate() {
	    return this.toolstartdate;
	  }

	  public void setToolstartdate(Date toolstartdate) {
	    this.toolstartdate = toolstartdate; }

	  public Time getToolstarttime() {
	    return this.toolstarttime;
	  }

	  public void setToolstarttime(Time toolstarttime) {
	    this.toolstarttime = toolstarttime; }


	public void setNodeexecutionstatus(String nodeexecutionstatus)
	{
		this.nodeexecutionstatus = nodeexecutionstatus;
	}


	public Workflownodeinfo getWorkflownodeinfo()
	{
		return this.workflownodeinfo;
	}


	public void setWorkflownodeinfo(Workflownodeinfo workflownodeinfo)
	{
		this.workflownodeinfo = workflownodeinfo;
	}


	public Set<Dataobjectmetadata> getDataobjectmetadatas()
	{
		return this.dataobjectmetadatas;
	}


	public void setDataobjectmetadatas(Set<Dataobjectmetadata> dataobjectmetadatas)
	{
		this.dataobjectmetadatas = dataobjectmetadatas;
	}
    public Set<Errorinfo> getErrorinfos() 
    {
		    return this.errorinfos;
	 }

    public void setErrorinfos(Set<Errorinfo> errorinfos)
    {
		    this.errorinfos = errorinfos;
	}
}