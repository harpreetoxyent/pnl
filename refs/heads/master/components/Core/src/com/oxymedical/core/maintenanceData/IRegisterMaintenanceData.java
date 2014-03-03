package com.oxymedical.core.maintenanceData;

import java.util.List;

public interface IRegisterMaintenanceData 
{
	public List<IAction> getAction();
	public void setAction(List<IAction> action);
	public List<IDefect> getDefect();
	public void setDefect(List<IDefect> defect);
}
