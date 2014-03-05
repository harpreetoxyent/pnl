package com.oxymedical.component.renderer.command;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import org.zkoss.zul.ext.Paginal;
import org.zkoss.zul.impl.XulElement;

import com.oxymedical.component.renderer.uiBuilder.zk.library.PagingInfo;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.core.renderdata.IDataUnit;
import com.oxymedical.core.router.IDataUnitRouter;
import com.oxymedical.core.stringutil.StringUtil;

public class ExecuteListCommand extends BaseCommand implements
		IUiLibraryCompositeCommand {

	private String listId = "";
	@Override
	public void execute() {
		IDataUnit dataUnit = createDataUnit(this.getClassname(), this.getComponentId(),
				this.getDataPatternId(), this.getFormPatternId(), this.getFormValues(),
				this.getMethodName(), this.getSession());
		dataUnit = updateDataUnit(this.getParamList(), dataUnit);
		this.setData( this.getRouter().routeToModeler(dataUnit));
	}

	@Override
	public IHICData getHICData() {
		return this.getData();
	}

	/**
	 * @param paramList
	 * @param dataUnit
	 * @return IDataUnit
	 */
	private IDataUnit updateDataUnit(String paramList, IDataUnit dataUnit) {
		String listQuery;
	

		paramList = StringUtil.reformatQueryStatement(paramList);
		String[] strValue = paramList.trim().split("_SEP_");
		if (strValue.length == 2) {
			listQuery = strValue[0];
			listId = strValue[1];
		} else {
			listQuery = strValue[0];
		}
		
		
		if (listQuery.indexOf(" limit ") <= 0 && !this.isValidListRequest()) {
			boolean valid = valideQueryForList(this.getRootFormValue(),false);
			if (valid) {
				
				String[] listInfo = new String[6];
				listInfo[0] = this.getComponentId();
				listInfo[1] = this.getMethodName();
				listInfo[2] = this.getClassname();
				listInfo[3] = listQuery;
				listInfo[4] = listId;
				//this is rquired in case of sorting to set the active page.added by pra.
				listInfo[5] = this.getPagingId();
				PagingInfo.setListInfo(listInfo);
				listQuery = createPagingQuery(listQuery);
			}
		}
		QueryData requestData = new QueryData();
		requestData.setCondition(listQuery);
		//dataUnit.setMethodName("getListData");
		dataUnit.setQueryData(requestData);
		return dataUnit;
	}

	/**
	 * @param conditonStr
	 * @return String
	 */
	public String createPagingQuery(String conditonStr) {
		Paging pagingObject = (Paging) this.getRootFormValue().getFellowIfAny(this.getPagingId(), true);
		
		String addCondition = "limit " + 0 + "," + (pagingObject.getPageSize());
		conditonStr = conditonStr + " " + addCondition;
		return conditonStr;
	}
//Method implementation changed to implement Paging. done by pra on 27_may-2009

	/**
	 * @param formObj
	 * @param valid
	 * @return boolean
	 */
	public boolean valideQueryForList(Component formObj,boolean valid) {
		//change parameter for method. done by pra on 24-june-2009
		List childElement;
		if (formObj != null) {
			childElement = formObj.getChildren();
			Iterator iter = childElement.iterator();
			while (iter.hasNext()) {
				Object value = iter.next();	
				if (value instanceof Listbox) {
					valid = valideQueryForList( (Listbox)value,valid);
					if (valid)
						return valid;
					if ((((Listbox) value).getId().indexOf("grid") >= 0)
							|| (((Listbox) value).getId().indexOf("list") >= 0)) {
						if (((Listbox) value).getPaginal()!= null) {
							Paging objectId = (Paging) ((Listbox) value)
									.getPaginal();
						
							String id = objectId.getId();
							if (id.equals(this.getPagingId())) {
								valid = true;
								listId=((Listbox)value).getId();
								return valid;
							}
						}
					}
				} else {
					//System.out.println("valideQueryForList.value: " + value);
					valid = valideQueryForList(((Component) value),valid);
				}
			}
		}
		return valid;
	}

}
