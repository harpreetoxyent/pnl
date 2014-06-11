package com.oxymedical.component.renderer.uiBuilder.zk.library;

import java.util.Hashtable;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Window;
import org.zkoss.zul.Combobox;

import com.oxymedical.component.renderer.RendererComponent;
import com.oxymedical.component.renderer.command.UiLibraryCompositeCommand;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.router.IDataUnitRouter;
import com.oxymedical.core.router.IRouter;

public class ComboEventListener {

	String method ="executeList";
	String componentId = "dbComponent";
	String classname = "com.oxymedical.component.db.DBComponent";
	IDataUnitRouter router = (IDataUnitRouter)RendererComponent.dataUnitRouter;
	private Hashtable formValues = new Hashtable();
	UiLibraryUtil uiLibraryUtil=new UiLibraryUtil();
	
	public void getComboData(String dataPatternId, String formPatternId, Window rootFormValue, String paramList, Combobox comboObj)
	{
	    UiLibraryCompositeCommand command=new UiLibraryCompositeCommand();
		command.setMethodName(method);
		command.setRouter(router);
	    command.setClassname(classname);
		command.setComponentId(componentId);
	    command.setDataPatternId(dataPatternId);
	    command.setFormPatternId(formPatternId);
	    command.setFormValues(formValues);
		command.setRootFormValue(rootFormValue);
		command.setParamList(paramList);
		command.setSession(null);
		command.setComboSelectedValue(null);
		command.setValidListRequest(false);
		command.setPagingId("");
		command.execute();
		IHICData hicData = command.getHICData();
		List listValue = null;
		IData dataUnit = hicData.getData();
		listValue = dataUnit.getQueryData().getListData();
		String[][] allValues = dataUnit.getQueryData().iterateListData(listValue);
		uiLibraryUtil.showComboData(allValues,comboObj);
	}
	public void onEvent(Combobox target) {
			if (target == GridRowCustomRenderer.methodCombobox)
				getComboData("Fortis" , "nursing_progress_notes" , GridRowCustomRenderer.window ,"get MasterMethod.methodId,MasterMethod.methodName from FORTIS.MASTERMETHOD",GridRowCustomRenderer.methodCombobox);
	
	}
}