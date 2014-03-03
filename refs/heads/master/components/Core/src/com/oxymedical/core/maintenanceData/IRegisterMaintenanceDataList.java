package com.oxymedical.core.maintenanceData;

import java.util.List;

public interface IRegisterMaintenanceDataList 
{
	public List<IRegisterMaintenanceData> getListRegisterMaintenanceData();
	public void setListRegisterMaintenanceData(List<IRegisterMaintenanceData> listRegisterMaintenanceData);
	public void addRegisterMaintenanceData(IRegisterMaintenanceData RegisterMaintenanceData);
}
