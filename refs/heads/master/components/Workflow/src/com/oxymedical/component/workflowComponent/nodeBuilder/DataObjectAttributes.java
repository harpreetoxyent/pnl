package com.oxymedical.component.workflowComponent.nodeBuilder;

public class DataObjectAttributes implements IDataObjectAttributes
{
	private String data;
	private String info;
	private String status;
	
	public String getData()
	{
		return this.data;
	}
	public String getInfo()
	{
		return this.info;
	}
	public String getStatus()
	{
		return this.status;
	}

	public void setData(String data)
	{
		this.data = data;
	}
	public void setInfo(String info)
	{
		this.info = info;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		else if (obj == null) return false;
		else if (this.getClass() != obj.getClass()) return false;
		
		DataObjectAttributes doa = (DataObjectAttributes)obj;
		return (data == doa.data || (data != null && data.equals(doa.data))) 
			&& (info == doa.info || (info != null && info.equals(doa.info))) 
			&& (status == doa.status || (status != null && status.equals(doa.status)));
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 31 * hash + (null == data ? 0 : data.hashCode());
		hash = 31 * hash + (null == info ? 0 : info.hashCode());
		hash = 31 * hash + (null == status ? 0 : status.hashCode());
		return hash;
	}
}
