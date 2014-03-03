package com.oxymedical.core.maintenanceData;

import java.sql.Timestamp;

public interface IDefect 
{
	public String getDefect_id();
	public void setDefect_id(String defect_id);
	public String getDefect_type();
	public void setDefect_type(String defect_type);
	public Integer getSeverity();
	public void setSeverity(Integer severity);
	public Timestamp getDefect_occured();
	public void setDefect_occured(Timestamp defect_occured);

}
