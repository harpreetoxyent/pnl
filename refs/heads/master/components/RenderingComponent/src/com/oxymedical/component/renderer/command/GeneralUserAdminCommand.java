package com.oxymedical.component.renderer.command;

import java.util.Hashtable;

import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.oxymedical.component.renderer.RendererComponent;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.core.renderdata.IDataUnit;
import com.oxymedical.core.router.IDataUnitRouter;
import com.oxymedical.core.stringutil.StringUtil;

public class GeneralUserAdminCommand extends BaseCommand implements
		IUiLibraryCompositeCommand {


	@Override
	public void execute() {
		IDataUnit dataUnit = createDataUnit(this.getClassname(), this.getComponentId(),
				this.getDataPatternId(), this.getFormPatternId(), this.getFormValues(),
				this.getMethodName(), this.getSession());
		dataUnit = updateDataUnit(dataUnit, this.getParamList(), this.getSession(),
				this.getMethodName());
		this.setData(this.getRouter().routeToModeler(dataUnit));

	}

	@Override
	public IHICData getHICData() {
		return this.getData();
	}

	private IDataUnit updateDataUnit(IDataUnit dataUnit, String paramList,
			Session session, String method) {
		RendererComponent.logger.log(0," in deleteUserFromApplication getComboDataFromUserAdmin-in zs--Check param-- "
						+ paramList);
		QueryData requestData = new QueryData();
		String listQuery = listQuery(paramList, method);
		requestData.setCondition(listQuery);
		dataUnit.setQueryData(requestData);
		return dataUnit;
	}

	// method implementation changed as previously it checks only the index
	// _SEP_ now it will take care of condition without _SEP_ 
	//added by pra on 26-may-2009
//Update code to show message by pra on 30may-2009
	private String listQuery(String paramList, String method) {
		String listQuery = "";
		String listId = "";
		String[] strValue = paramList.trim().split("_SEP_");
		if (strValue.length == 2) {
			listQuery = strValue[0];
			RendererComponent.logger.log(0," in deleteUserFromApplication-listQuery "
					+ listQuery);
			listId = strValue[1];
			RendererComponent.logger.log(0," in deleteUserFromApplication-listId "
					+ listId);

		} else 
		{
			listQuery = strValue[0];
			
		}
		//removed condition for delete to display message done by pra on 4-june-2009
		if (method.equalsIgnoreCase("deleteUserFromApplication")|| method.equalsIgnoreCase("SoftDeleteUser")) {
		//This code is commented as no this message will be displyed from application.Done by pra on june-29-2009
				/*try {
					if (!(Messagebox.show("Do you want to delete this record?",
							"Question", Messagebox.YES | Messagebox.NO,
							"Messagebox.QUESTION") == Messagebox.YES)) {
						return "Error";
						
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}*/
			

			listQuery = listQuery.replaceAll("rowId", (String) this.getSession()
					.getAttribute("rowId"));
			RendererComponent.logger.log(0," in deleteUserFromApplication-after replace listQuery "
							+ listQuery);
		}
		RendererComponent.logger.log(0," in replace listQuery " + listQuery);
		return listQuery;
	}

}
