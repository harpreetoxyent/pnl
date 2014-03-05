package com.oxymedical.component.renderer.command;

import org.zkoss.zk.ui.Session;

import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.commonData.app.ApplicationConstant;
import com.oxymedical.core.renderdata.IDataUnit;

public class ChangeDOStatusDynamicWorkflowCommand extends ChangeDOStatusCommand
{
	@Override
	public void execute()
	{
		IDataUnit dataUnit = createDataUnit();
		dataUnit = updateDataUnit(dataUnit, this.getParamList(), this.getSession());
		this.setData(this.getRouter().routeToModeler(dataUnit));
	}

	protected IDataUnit updateDataUnit(IDataUnit dataUnit, String paramList, Session session)
	{
		if (this.getMethodName().equalsIgnoreCase("processNextWorkflowTool"))
			dataUnit = super.updateDataUnit(dataUnit, paramList, session, false);
		else
			dataUnit = super.updateDataUnit(dataUnit, paramList, session);
		
		String[] strValue = null;
		strValue = paramList.trim().split("_SEP_");
		if (strValue.length >= 4)
		{
			if (!strValue[3].equals("_NULL_")) 
				dataUnit.getFormValues().put(ApplicationConstant.KEY_PATIENT_ID, strValue[3]);
		}
		if (strValue.length >= 5)
		{
			if (!strValue[4].equals("_NULL_")) 
				dataUnit.getFormValues().put(ApplicationConstant.KEY_PATIENT_MRN, strValue[4]);
		}
		if (strValue.length >= 6)
		{
			if (!strValue[5].equals("_NULL_")) 
				dataUnit.getFormValues().put(ApplicationConstant.KEY_SCHEDULE_ID, strValue[5]);
		}
		if (strValue.length >= 7)
		{
			if (!strValue[6].equals("_NULL_")) 
				dataUnit.getFormValues().put(ApplicationConstant.KEY_SCHEDULE_WORK_AREA, strValue[6]);
		}

		return dataUnit;
	}
}
