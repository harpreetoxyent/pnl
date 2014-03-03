package com.oxymedical.core.maintenanceData;

import java.util.List;


public class RegisterMaintenanceData implements IRegisterMaintenanceData
{
	List<IDefect> defect;
	List<IAction> action;
	//IDefect defect[];
	public List<IAction> getAction() {
		return action;
	}
	public void setAction(List<IAction> action) {
		this.action = action;
	}
	public List<IDefect> getDefect() {
		return defect;
	}
	public void setDefect(List<IDefect> defect) {
		this.defect = defect;
	}
	
	
}
