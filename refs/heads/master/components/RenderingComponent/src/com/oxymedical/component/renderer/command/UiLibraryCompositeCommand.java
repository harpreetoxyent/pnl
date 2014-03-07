package com.oxymedical.component.renderer.command;

import java.util.Hashtable;

import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Window;

import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.router.IDataUnitRouter;

public class UiLibraryCompositeCommand implements IUiLibraryCompositeCommand {

	private IDataUnitRouter router;
	private Window rootFormValue;
	private String methodName;
	private String formPatternId;
	private String dataPatternId;
	private Session session;
	private String componentId;
	private String classname;
	private String paramList;
	private Hashtable formValues;
	private String comboSelectedValue;
	boolean validListRequest = false;
	private String pagingId;
	public String getPagingId() {
		return pagingId;
	}
	public void setPagingId(String pagingId) {
		this.pagingId = pagingId;
	}
	public boolean isValidListRequest() {
		return validListRequest;
	}
	public void setValidListRequest(boolean validListRequest) {
		this.validListRequest = validListRequest;
	}

	public String getComboSelectedValue() {
		return comboSelectedValue;
	}

	public void setComboSelectedValue(String comboSelectedValue) {
		this.comboSelectedValue = comboSelectedValue;
	}

	private IHICData data;

	public IDataUnitRouter getRouter() {
		return router;
	}

	public void setRouter(IDataUnitRouter router) {
		this.router = router;
	}

	public Window getRootFormValue() {
		return rootFormValue;
	}

	public void setRootFormValue(Window rootFormValue) {
		this.rootFormValue = rootFormValue;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getFormPatternId() {
		return formPatternId;
	}

	public void setFormPatternId(String formPatternId) {
		this.formPatternId = formPatternId;
	}

	public String getDataPatternId() {
		return dataPatternId;
	}

	public void setDataPatternId(String dataPatternId) {
		this.dataPatternId = dataPatternId;
	}

	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getParamList() {
		return paramList;
	}

	public void setParamList(String paramList) {
		this.paramList = paramList;
	}

	public Hashtable getFormValues() {
		return formValues;
	}

	public void setFormValues(Hashtable formValues) {
		this.formValues = formValues;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public void execute()
	{
		System.out.println("-------Inside Rendering Execute Method-----"+this.methodName+" router=="+this.router);
		IUiLibraryCompositeCommand command = CommandFactory
				.getUiLibraryCommand(this.methodName);
		try
		{
			command.setMethodName(this.methodName);
			command.setRouter(this.router);
			command.setClassname(this.classname);
			command.setComponentId(this.componentId);
			command.setDataPatternId(this.dataPatternId);
			command.setFormPatternId(this.formPatternId);
			command.setFormValues(this.formValues);
			command.setRootFormValue(this.rootFormValue);
			command.setParamList(this.paramList);
			command.setSession(this.session);
			command.setComboSelectedValue(this.comboSelectedValue);
			command.setValidListRequest(this.validListRequest);
			command.setPagingId(this.pagingId);
			command.execute();
		}
		catch(Exception exp)
		{
			System.out.println("-------Exception in Rendering Execute Method-----");
			exp.printStackTrace();
		}
		this.data = command.getHICData();
	}

	@Override
	public IHICData getHICData() {
		return this.data;
	}

}
