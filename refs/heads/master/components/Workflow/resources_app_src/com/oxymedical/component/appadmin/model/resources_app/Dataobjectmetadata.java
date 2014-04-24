package com.oxymedical.component.appadmin.model.resources_app;

import java.io.Serializable;
import java.sql.Blob;

import com.oxymedical.core.util.serializer.SerializerUtil;

public class Dataobjectmetadata implements Serializable
{
	private Integer id;
	private String datakey;
	private String datavalue;
	private String datavalueblob;
	private Dataobject dataobject;


	public Integer getId()
	{
		return this.id;
	}


	public void setId(Integer id)
	{
		this.id = id;
	}


	public String getDatakey()
	{
		return this.datakey;
	}


	public void setDatakey(String datakey)
	{
		this.datakey = datakey;
	}

	/** It is advised to use getDataValue function instead of getDatavalue or getDatavalueblob */
	public Object getDatavalue()
	{
		return this.datavalue;
	}

	/** DO NOT USE setDatavalue OR setDatavalueblob FUNCTION. USE setDataValue FUNCTION INSTEAD */
	public void setDatavalue(String datavalue)
	{
		this.datavalue = datavalue;
	}

	public Dataobject getDataobject()
	{
		return this.dataobject;
	}

	public void setDataobject(Dataobject dataobject)
	{
		this.dataobject = dataobject;
	}

	/** It is advised to use getDataValue function instead of getDatavalue or getDatavalueblob */
	public Object getDatavalueblob()
	{
		return this.datavalueblob;
	}

	/** DO NOT USE setDatavalue OR setDatavalueblob FUNCTION. USE setDataValue FUNCTION INSTEAD */
	public void setDatavalueblob(String datavalueblob)
	{
		this.datavalueblob = datavalueblob;
	}

	/** It is advised to use getDataValue function instead of getDatavalue or getDatavalueblob */
	public Object getDataValue()
	{
		if (this.datavalue.equals("[ O B J E C T ]"))
			return SerializerUtil.deserialize((String)this.datavalueblob);
		else
			return this.datavalue;
	}

	/** DO NOT USE setDatavalue OR setDatavalueblob FUNCTION. USE setDataValue FUNCTION INSTEAD */
	public void setDataValue(Object dataValue)
	{
		if ((dataValue.getClass() == String.class)
				|| (dataValue.getClass() == int.class)
				|| (dataValue.getClass() == Integer.class)
				|| (dataValue.getClass() == long.class)
				|| (dataValue.getClass() == Long.class)
				|| (dataValue.getClass() == float.class)
				|| (dataValue.getClass() == Float.class)
				|| (dataValue.getClass() == double.class)
				|| (dataValue.getClass() == Double.class)
			)
		{
			this.datavalue = (String)dataValue;
		}
		else
		{
			this.datavalue = "[ O B J E C T ]";
			this.datavalueblob = SerializerUtil.serialize(dataValue);
		}
	}
}