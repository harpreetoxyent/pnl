package com.oxymedical.component.renderer.command;

import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.renderdata.IDataUnit;

public class CreateLogoutCommand extends BaseCommand implements
		IUiLibraryCompositeCommand {

	

	@Override
	public void execute() {
		IDataUnit dataUnit = createDataUnit(this.getClassname(), this.getComponentId(),
				this.getDataPatternId(), this.getFormPatternId(), this.getFormValues(),
				this.getMethodName(), this.getSession());
		dataUnit = updateDataUnit(this.getParamList(), dataUnit);
		this.setData(this.getRouter().routeToModeler(dataUnit));
	}

	@Override
	public IHICData getHICData() {
		return this.getData();
	}

	private IDataUnit updateDataUnit(String paramList, IDataUnit dataUnit) {
		
		dataUnit.setUserId(paramList);
		/*String listQuery;
		String listId = "";

		paramList = StringUtil.reformatQueryStatement(paramList);
		String[] strValue = paramList.trim().split("_SEP_");
		if (strValue.length == 2) {
			listQuery = strValue[0];
			listId = strValue[1];
		} else {
			listQuery = strValue[0];
		}
		QueryData requestData = new QueryData();
		requestData.setCondition(listQuery);
		//dataUnit.setMethodName("getListData");
		dataUnit.setQueryData(requestData);*/
		return dataUnit;
	}

	
	
}