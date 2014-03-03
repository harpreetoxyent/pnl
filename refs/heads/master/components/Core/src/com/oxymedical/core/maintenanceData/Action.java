package com.oxymedical.core.maintenanceData;

import java.sql.Timestamp;
import java.util.List;

public class Action implements IAction 
{
	String action_id;
	String action_type;
	List<String> defect_id;
	Timestamp action_found;
		
	public Action()
	{
		
	}
	
	public Action(String action_id, String action_type, List<String> defect_id, Timestamp action_found) {
		super();
		this.action_id = action_id;
		this.action_type = action_type;
		this.defect_id = defect_id;
		this.action_found = action_found;
	}
	public String getAction_id() {
		return action_id;
	}
	public void setAction_id(String action_id) {
		this.action_id = action_id;
	}
	public String getAction_type() {
		return action_type;
	}
	public void setAction_type(String action_type) {
		this.action_type = action_type;
	}
	public List<String> getDefect_id() {
		return defect_id;
	}
	public void setDefect_id(List<String> defect_id) {
		this.defect_id = defect_id;
	}
	public Timestamp getAction_found() {
		return action_found;
	}
	public void setAction_found(Timestamp action_found) {
		this.action_found = action_found;
	}
	
	
}
