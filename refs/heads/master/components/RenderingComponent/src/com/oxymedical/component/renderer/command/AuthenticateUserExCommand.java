package com.oxymedical.component.renderer.command;

import java.util.Hashtable;
import java.util.Iterator;

import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.commonData.IUserInfo;
import com.oxymedical.core.renderdata.IDataUnit;
import com.oxymedical.core.router.IDataUnitRouter;
import com.oxymedical.core.userdata.UserInfo;

public class AuthenticateUserExCommand extends BaseCommand implements IUiLibraryCompositeCommand {

	
	@Override
	public void execute() 
	{
		Hashtable userFormValues = createUserFormValues(this.getParamList());
		//if(userFormValues.size() > 0)
			//this.setFormValues(userFormValues);
		Iterator itr = userFormValues.keySet().iterator();
		while(itr.hasNext()){
			String key = itr.next().toString();
			this.getFormValues().put(key, userFormValues.get(key));
		}
	    IDataUnit dataUnit = createDataUnit(this.getClassname(), this.getComponentId(),
				this.getDataPatternId(), this.getFormPatternId(), this.getFormValues(),
				this.getMethodName(), this.getSession());
		dataUnit = updateDataUnit(dataUnit, this.getSession());
		System.out.println("-----------------Inside------"+dataUnit);
		System.out.println("-----------------this.getRouter()------"+this.getRouter());
		System.out.println("-----------------Inside this.getClassname()------"+this.getClassname()
							+ "getDataPatternId()"+this.getDataPatternId()
							+ "this.getComponentId()"+this.getComponentId()
							+ "this.getMethodName()"+this.getMethodName()
							+ "this.getFormValues()"+this.getFormValues());
		this.setData(this.getRouter().routeToModeler(dataUnit));		
	}
	@Override
	public IHICData getHICData()
	{
		return this.getData();
	}
	private Hashtable createUserFormValues(String paramList)
	{
		
		String fieldName ;
		String fieldId;
		Hashtable userauth = new Hashtable();
		if(paramList.indexOf("_FIELDSEP_")>=0)
		{
			String[] strValue = paramList.trim().split("_FIELDSEP_");
			if(strValue.length ==2)
			{
				for(int i=0;i<strValue.length;i++)
				{
					String fieldExp = strValue[i];
					if(fieldExp.indexOf("_SEP_")>=0)
					{
						String[] fexp = fieldExp.trim().split("_SEP_");
						fieldName = fexp[0];
						fieldId = fexp[1];
						String fieldVal = (String)this.getFormValues().get(fieldId);
						if(fieldName!=null && fieldVal!=null)
						{
							userauth.put(fieldName, fieldVal);
						}
					}
					else
					{
						try
						{
							Messagebox.show("Invalid arguments");
						} catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}
					}
					
				}
	
			}
		return userauth;
	}
		
	private IDataUnit updateDataUnit(IDataUnit dataUnit,Session session)
	{
		
		IUserInfo userInfo = new UserInfo();
		userInfo.setHostAddress((session != null) ? session.getRemoteHost() : "localhost");
		dataUnit.setUserInfo(userInfo);
		return dataUnit;
	}

}
