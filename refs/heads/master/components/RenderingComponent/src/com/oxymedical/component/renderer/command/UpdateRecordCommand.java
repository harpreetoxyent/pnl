package com.oxymedical.component.renderer.command;

import java.util.Hashtable;

import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.core.renderdata.IDataUnit;
import com.oxymedical.core.router.IDataUnitRouter;
import com.oxymedical.core.stringutil.StringUtil;

public class UpdateRecordCommand extends BaseCommand implements
		IUiLibraryCompositeCommand {



	@Override
	public void execute() {
		IDataUnit dataUnit = createDataUnit(this.getClassname(), this.getComponentId(),
				this.getDataPatternId(), this.getFormPatternId(), this.getFormValues(),
				this.getMethodName(), this.getSession());
		dataUnit = updateDataUnit(dataUnit, this.getParamList(), this.getSession());
		if(dataUnit != null)
		{
			this.setData(this.getRouter().routeToModeler(dataUnit));
		}
	}

	@Override
	public IHICData getHICData() {
		return this.getData();
	}

	private IDataUnit updateDataUnit(IDataUnit dataUnit, String paramList,
			Session session) {
		QueryData requestData = new QueryData();
		String listQuery = "";
	
			listQuery = listQuery(paramList);
		if(listQuery.equalsIgnoreCase("Error"))
		{
			return null;
		}
		
		listQuery = listQuery.replaceAll("rowId", (String) session
				.getAttribute("rowId"));
		requestData.setCondition(listQuery);
		dataUnit.setQueryData(requestData);
		return dataUnit;
	}

	private String listQuery(String paramList)  {
		paramList = StringUtil.reformatQueryStatement(paramList);
		String listQuery = "";
		String listId = "";
		String[] strValue = paramList.trim().split("_SEP_");
		if (strValue.length == 2) {
			listQuery = strValue[0];
			listId = strValue[1];
		} else 
		{
				listQuery = strValue[0];
		
		}
		return listQuery;
	}
}
