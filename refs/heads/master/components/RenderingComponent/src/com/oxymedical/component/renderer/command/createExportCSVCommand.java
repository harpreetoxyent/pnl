/**
 * 
 */
package com.oxymedical.component.renderer.command;

import java.util.Hashtable;
import java.util.List;

import org.zkoss.zk.ui.Session;

import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.core.renderdata.IDataUnit;
import com.oxymedical.core.stringutil.StringUtil;

/**
 * @author wkh
 *
 */
public class createExportCSVCommand extends BaseCommand implements
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
		QueryData queryData = new QueryData();
		List<Object> listData = (List<Object>) session.getAttribute("dbListValue");
		
		queryData.setListData(listData);
		queryData.setIdField(paramList);
		dataUnit.setQueryData(queryData);
		
		
	return dataUnit;
	}
}
