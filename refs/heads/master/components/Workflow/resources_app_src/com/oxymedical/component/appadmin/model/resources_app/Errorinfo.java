package com.oxymedical.component.appadmin.model.resources_app;

import java.io.Serializable;

public class Errorinfo implements Serializable
{
	private Integer id;
	private String errormessage;
	private Dataobject dataobject;

	public Integer getId()
	{
		return this.id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getErrormessage()
	{
		return this.errormessage;
	}

	public void setErrormessage(String errormessage)
	{
		this.errormessage = errormessage;
	}

	public Dataobject getDataobject()
	{
		return this.dataobject;
	}

	public void setDataobject(Dataobject dataobject)
	{
		this.dataobject = dataobject;
	}
}
