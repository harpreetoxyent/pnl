package com.oxymedical.core.maintenanceData;

import java.util.ArrayList;
import java.util.List;

public class RegisterMaintenanceDataList implements
		IRegisterMaintenanceDataList
{
	List<IRegisterMaintenanceData> listRegisterMaintenanceData;

	public RegisterMaintenanceDataList()
	{
		this.listRegisterMaintenanceData = new ArrayList<IRegisterMaintenanceData>();
	}
	public List<IRegisterMaintenanceData> getListRegisterMaintenanceData() {
		return listRegisterMaintenanceData;
	}

	public void setListRegisterMaintenanceData(
			List<IRegisterMaintenanceData> listRegisterMaintenanceData) {
		this.listRegisterMaintenanceData = listRegisterMaintenanceData;
	}
	public void addRegisterMaintenanceData(IRegisterMaintenanceData RegisterMaintenanceData) 
	{
		this.listRegisterMaintenanceData.add(RegisterMaintenanceData);
	}
}
