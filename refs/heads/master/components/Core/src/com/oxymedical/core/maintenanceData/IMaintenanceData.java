package com.oxymedical.core.maintenanceData;

import com.oxymedical.core.commonData.IData;


public interface IMaintenanceData extends IData
{
	public IAction getAction();
	public void setAction(IAction action);
	public String getComponent_id();
	public void setComponent_id(String component_id);
	public IDefect getDefect();
	public void setDefect(IDefect defect);
}
