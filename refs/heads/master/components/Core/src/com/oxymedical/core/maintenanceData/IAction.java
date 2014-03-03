package com.oxymedical.core.maintenanceData;

import java.sql.Timestamp;
import java.util.List;

//import com.oxymedical.hic.IData;

public interface IAction
{
	public String getAction_id();
	public void setAction_id(String action_id);
	public String getAction_type();
	public void setAction_type(String action_type);
	public List<String> getDefect_id();
	public void setDefect_id(List<String> defect_id);
	public Timestamp getAction_found();
	public void setAction_found(Timestamp action_found);
}
