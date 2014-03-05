package com.oxymedical.component.renderer.command;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Window;

import com.oxymedical.component.renderer.RendererComponent;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.commonData.ISource;
import com.oxymedical.core.commonData.Source;
import com.oxymedical.core.renderdata.DataUnit;
import com.oxymedical.core.renderdata.IDataUnit;
import com.oxymedical.core.router.IDataUnitRouter;
import com.oxymedical.core.userdata.IUserPattern;
import com.oxymedical.core.userdata.UserPattern;

public class BaseCommand {

	public IDataUnit createDataUnit(String classname, String componentId,
			String dataPatternId, String formPatternId, Hashtable formValues,
			String methodName, Session session) {
		IDataUnit dataUnit = new DataUnit();
		dataUnit.setFormId(formPatternId);
		dataUnit.setDataPatternId(dataPatternId);
		dataUnit.setMethodName(methodName);
		dataUnit.setInvokeComponentId(componentId);
		dataUnit.setInvokeComponentClass(classname);
		dataUnit.setFormValues(formValues);
		if (session!=null)dataUnit.setCurrentApplicationName((String)session.getAttribute("currentApplicationName"));
		ISource src = new Source();
		src.setMethodName("invokeComponent");
		dataUnit.setSource(src);
		if (session!=null)dataUnit.setUniqueId((String)session.getAttribute("EIBUNID"));
		if (session!=null)dataUnit.setUserId((String)session.getAttribute("userId"));
		if (session!=null)
			dataUnit.setSessionId(""+session.hashCode());
		if ((session!=null) && (session.getAttribute("userPatterns")!=null)) 
		{
			Set uspset = new HashSet();
			String [] userpatternids = ((String)session.getAttribute("userPatterns")).split("##");
			for(int i=0;i<userpatternids.length;i++)
			{
				IUserPattern usp = new UserPattern();
				usp.setUserPatternId(userpatternids[i]);
				uspset.add(usp);
			}
			dataUnit.setUserPatterns(uspset);
		}
		else
		{
			dataUnit.setUserPatterns(null);
		}
		
		
		
		return dataUnit;
	}
	
	
	public IDataUnit createDataUnit() 
	{
		return createDataUnit(this.classname, this.componentId,
				this.dataPatternId, this.formPatternId, this.formValues,
				this.methodName, this.session);
	}
	
	// All commands changed as per base command. The common variables in all
	// commands are passed to the base command so that their is no duplicate
	// code in commands.All commands are implemented and changed
	// accordingly. Change done by pra on 26-May-2006
	private IDataUnitRouter router;
	private Window rootFormValue;
	private String methodName;
	private String formPatternId;
	private String dataPatternId;
	private String componentId;
	private String classname;
	private String paramList;
	private Hashtable formValues;
	private IHICData data;
	private Session session;
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

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

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

	public IHICData getData() {
		return data;
	}

	public void setData(IHICData data) {
		this.data = data;
	}
}
