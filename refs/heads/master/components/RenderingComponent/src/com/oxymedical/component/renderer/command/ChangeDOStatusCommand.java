package com.oxymedical.component.renderer.command;

import java.util.Hashtable;

import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Window;

import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.renderdata.IDataUnit;
import com.oxymedical.core.router.IDataUnitRouter;

public class ChangeDOStatusCommand extends BaseCommand implements
		IUiLibraryCompositeCommand {



	@Override
	public void execute() {
		IDataUnit dataUnit = createDataUnit(this.getClassname(), this.getComponentId(),
				this.getDataPatternId(), this.getFormPatternId(), this.getFormValues(),
				this.getMethodName(), this.getSession());
		dataUnit = updateDataUnit(dataUnit, this.getParamList(), this.getSession());
		this.setData(this.getRouter().routeToModeler(dataUnit));
	}

	@Override
	public IHICData getHICData() {
		return this.getData();
	}


	protected IDataUnit updateDataUnit(IDataUnit dataUnit, String paramList, Session session)
	{
		return updateDataUnit(dataUnit, paramList, session, true);
	}
	
	
	protected IDataUnit updateDataUnit(IDataUnit dataUnit, String paramList, Session session,
			boolean setCompIdToNull)
	{
		String[] strValue = null;
		strValue = paramList.trim().split("_SEP_");

		if (strValue.length >= 1)
		{
			if (!strValue[0].equals("_NULL_")) dataUnit.setStatus(strValue[0]);
			else dataUnit.setStatus(null);
		}
		if (strValue.length >= 2)
		{
			/*if (!strValue[0].equals("_NULL_")) dataUnit.setStatus(strValue[0]);*/
			if (!strValue[1].equals("_NULL_")) dataUnit.setFormId(strValue[1]);
			else dataUnit.setFormId(null);
		}
		if (strValue.length >= 3)
		{
			/*if (!strValue[0].equals("_NULL_")) dataUnit.setStatus(strValue[0]);
			if (!strValue[1].equals("_NULL_")) dataUnit.setFormId(strValue[1]);*/
			if (!strValue[2].equals("_NULL_")) dataUnit.setDataPatternId(strValue[2]);
			else dataUnit.setDataPatternId(null);
			if (setCompIdToNull) session.getWebApp().setAttribute("currentPage", strValue[1]);
		}
		if (setCompIdToNull) dataUnit.setInvokeComponentId(null);
		return dataUnit;
	}

}
