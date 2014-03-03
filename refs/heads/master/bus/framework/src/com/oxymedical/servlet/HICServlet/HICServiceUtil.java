package com.oxymedical.servlet.HICServlet;

import java.util.Iterator;
import java.util.List;

import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.oxymedical.hic.application.NOLISRuntime;
import com.oxymedical.hic.servlet.NOLISStartupUtility;

public class HICServiceUtil
{
	public static NOLISStartupUtility NOLISStartupUtility = null;
	public static Object application = null;

	public static void startHIC(String appConfFile)
	{
		if (application == null)
		{
			NOLISStartupUtility = new NOLISStartupUtility(appConfFile);
			// application = new HICRuntime(NOLISStartupUtility.ConfigData);
			
			NOLISRuntime.startApplication();
			application = new Object();
		}
		else
		{
			System.out.println("Bus is already Running");
		}
	}

	

	public void getUsersList()
	{
		long COMPANY_ID = 1234;
		List userList;
		try
		{
			userList = UserLocalServiceUtil.search(COMPANY_ID, "", "", "", "",
					"%@%", true, null, false, 0, Integer.MAX_VALUE, null);

			Iterator iterator = userList.iterator();
			while (iterator.hasNext())
			{
				User communityUser = (User) iterator.next();
			}

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
