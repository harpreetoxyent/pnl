/**
 * 
 */
package com.oxymedical.component.renderer.command;

import com.oxymedical.component.db.query.QueryCreator;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.core.renderdata.IDataUnit;

/**
 * @author vka
 *
 */
public class CreateQueryCommand extends BaseCommand implements
IUiLibraryCompositeCommand {
	
	
	@Override
	public void execute() {
		IDataUnit dataUnit = createDataUnit(this.getClassname(), this.getComponentId(),
				this.getDataPatternId(), this.getFormPatternId(), this.getFormValues(),
				this.getMethodName(), this.getSession());
		dataUnit=updateDataUnit(dataUnit, this.getParamList());
		this.setData(this.getRouter().routeToModeler(dataUnit));
		
	}

	@Override
	public IHICData getHICData() {
		return this.getData();
	}

	private IDataUnit updateDataUnit(IDataUnit dataUnit, String paramList)
	{
		String[] listArg = paramList.trim().split("_SEP_");
		String tableName=null;
		String columnName = null;
		String textId = null;
		if(listArg.length==3)
		{
			tableName= listArg[0];
			columnName= listArg[1];
			textId= listArg[2];
		}
		textId.toLowerCase();
		String colValue=null;
		colValue = (String) this.getFormValues().get(textId);
		
		// Temporary commented by HS as asked by Wasim
		/*QueryCreator queryCreator = new QueryCreator();
		String sqlResult = queryCreator.buildSqlQuery(tableName, columnName, colValue);		
		QueryData queryD = new QueryData();
		queryD.setCondition(sqlResult);
		dataUnit.setQueryData(queryD);*/
		
		
		return dataUnit;
	}
}
