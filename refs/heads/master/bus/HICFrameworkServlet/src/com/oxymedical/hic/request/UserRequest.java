package com.oxymedical.hic.request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.oxymedical.core.commonData.Data;
import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.hic.application.NOLISRuntime;
import com.oxymedical.hic.application.eventmanagement.PublicationScope;

public class UserRequest implements Runnable
{
	private String applicationName;
	private String authenticate;
	private String sourceDir;

	public String getApplicationName()
	{
		return applicationName;
	}

	public void setApplicationName(String applicationName)
	{
		this.applicationName = applicationName;
	}

	public String getAuthenticate()
	{
		return authenticate;
	}

	public void setAuthenticate(String authenticate)
	{
		this.authenticate = authenticate;
	}

	public String getSourceDir()
	{
		return sourceDir;
	}

	public void setSourceDir(String sourceDir)
	{
		this.sourceDir = sourceDir;
	}

	public void handleUserRequest() throws IOException 
	{
		String userXML = null;
		String rightsXML = null;
		String rolesXML = null;
		String userPatternsXML = null;
		String groupXML = null;
		String orgXML = null;

//		IUserAdminComponent userAdminComponent 
//		= (IUserAdminComponent)NOLISRuntime.getComponent(IComponentIdConstants.USERADMIN_COMP_ID);
		System.out.println("------Inside handleUserRequest of Thread----------");

		if(authenticate == null)
		{
			System.out.println("------authenticate ----------");
			
			userXML 		= sourceDir+(IRequestConstants.users + "/" + IRequestConstants.USERS_XML);
			rightsXML 		= sourceDir+(IRequestConstants.users + "/" + IRequestConstants.RIGHTS_XML);
			rolesXML 		= sourceDir+(IRequestConstants.users + "/" + IRequestConstants.ROLES_XML);
			userPatternsXML = sourceDir+(IRequestConstants.users + "/" + IRequestConstants.USERPATTERNS_XML);
			groupXML 		= sourceDir+(IRequestConstants.users + "/" + IRequestConstants.GROUPS_XML);
			orgXML 			= sourceDir+(IRequestConstants.users + "/" + IRequestConstants.ORGS_XML);

//			if (userAdminComponent!= null)
//			{
				System.out.println("------userAdminComponent not null ----------"+applicationName);				
				try
				{
					IHICData hicData = new HICData();
					IData data = new Data();
					List<String> parameters = new ArrayList<String>();
					parameters.add(applicationName);
					parameters.add(orgXML);
					data.setList(parameters);
					hicData.setData(data);
					NOLISRuntime.FireEvent("registerNewApplication", new Object[]{hicData}, PublicationScope.Global);
//					userAdminComponent.registerNewApplication(orgXML,applicationName);
					parameters.remove(1);
					parameters.add(groupXML);
					NOLISRuntime.FireEvent("registerNewApplication", new Object[]{hicData}, PublicationScope.Global);
//					userAdminComponent.registerNewApplication(groupXML,applicationName);
					parameters.remove(1);
					parameters.add(rightsXML);
					NOLISRuntime.FireEvent("registerNewApplication", new Object[]{hicData}, PublicationScope.Global);
//					userAdminComponent.registerNewApplication(rightsXML,applicationName);
					parameters.remove(1);
					parameters.add(rolesXML);
					NOLISRuntime.FireEvent("registerNewApplication", new Object[]{hicData}, PublicationScope.Global);
//					userAdminComponent.registerNewApplication(rolesXML,applicationName);
					parameters.remove(1);
					parameters.add(userXML);
					NOLISRuntime.FireEvent("registerNewApplication", new Object[]{hicData}, PublicationScope.Global);
//					userAdminComponent.registerNewApplication(userXML,applicationName);
					parameters.remove(1);
					parameters.add(userPatternsXML);
					NOLISRuntime.FireEvent("registerNewApplication", new Object[]{hicData}, PublicationScope.Global);
//					userAdminComponent.registerNewApplication(userPatternsXML,applicationName);
				}
				catch (Exception e) {
					e.printStackTrace();
				}

//			}
		}	
	}

	@Override
	public void run() 
	{
		try
		{
			System.out.println("------Inside UserRequest Thread----------");
			handleUserRequest();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
