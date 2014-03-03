package com.oxymedical.core.maintenanceData;

import java.sql.Timestamp;
import java.util.Date;


public class Defect implements IDefect 
{
	String defect_id;
	String defect_type;
	Integer severity;
	Timestamp defect_occured;
	
	public Defect()
	{
		
	}
	
	public Defect(String defect_id, String defect_type, Integer severity, Timestamp defect_occured) {
		super();
		this.defect_id = defect_id;
		this.defect_type = defect_type;
		this.severity = severity;
		this.defect_occured = defect_occured;
	}
	
	public String getDefect_id() {
		return defect_id;
	}
	public void setDefect_id(String defect_id) {
		this.defect_id = defect_id;
	}
	public String getDefect_type() {
		return defect_type;
	}
	public void setDefect_type(String defect_type) {
		this.defect_type = defect_type;
	}
	public Integer getSeverity() {
		return severity;
	}
	public void setSeverity(Integer severity) {
		this.severity = severity;
	}
	public Timestamp getDefect_occured() {
		return defect_occured;
	}
	public void setDefect_occured(Timestamp defect_occured) {
		this.defect_occured = defect_occured;
	}
	
}
