package com.oxymedical.component.renderer.command;

import java.util.Hashtable;

import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Window;



public interface IUiLibraryCompositeCommand extends ICommand {

	
	public void setMethodName(String method);
	public void setFormValues(Hashtable formValues);
	public void setRootFormValue(Window rootFormValue);
	public void setFormPatternId(String formPatternId);
	public void setDataPatternId(String dataPatternId);
	public void setSession(Session session);
	public void setClassname(String classname);
	public void setParamList(String paramList);
	public void setComponentId(String componentId);
	public void setComboSelectedValue(String comboSelectedValue);
	public void setValidListRequest(boolean validListRequest);
	public void setPagingId(String pagingId);
	
}
