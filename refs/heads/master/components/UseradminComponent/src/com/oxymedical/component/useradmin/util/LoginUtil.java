/**
 * 
 */
package com.oxymedical.component.useradmin.util;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.useradmin.UserAdminComponent;
import com.oxymedical.component.useradmin.constants.Constants;
import com.oxymedical.component.useradmin.constants.SQLCommands;
import com.oxymedical.component.useradmin.exception.NoSuchUserException;
import com.oxymedical.component.useradmin.impl.UserImpl;
import com.oxymedical.component.useradmin.model.Logindetail;
import com.oxymedical.component.useradmin.model.User;
import com.oxymedical.component.useradmin.operations.ConnectionDatabase;
import com.oxymedical.core.dateutil.DateUtil;

/**
 * @author vka
 *
 */

public class LoginUtil {

	
	
	/**
	 * this method save the login date ,time and ipAddress when valid user comes.
	 * @param userId
	 */
	static public Logindetail saveLoginDetails(String userId,String hostAddress)
	{
		Logindetail loginDetails = new Logindetail();
		try{
			String loginDate = null;
			String loginTime = null;
			String ipAddress = null;
			String da=DateUtil.formatDate(new Date(), "yyyy-MM-dd,HH:mm:ss");		
			String[] dateArray = da.trim().split(",");
			if(dateArray.length ==2)
			{
				loginDate = dateArray[0];
				loginTime = dateArray[1];
			}

			
			if(hostAddress ==null)
			{
				InetAddress ownIP=InetAddress.getLocalHost();
				ipAddress = ownIP.getHostAddress();
			}
			else
			{
				ipAddress = hostAddress;
			}

			
			userId = userId.trim().toLowerCase();
			
			loginDetails.setUserId(userId);
			loginDetails.setLoginDate(loginDate);
			loginDetails.setIpAddress(ipAddress);
			loginDetails.setLoginTime(loginTime);
			loginDetails.setLogout("0");
			loginDetails= (Logindetail) ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(loginDetails);
			
			
		}catch (Exception exp) {
			exp.printStackTrace();


		}	

		return loginDetails;

	}

	public static void saveLogOutDetail(String userId) 
	{
		
		String logoutTime=null;
		String logoutDate = null;
		String da=DateUtil.formatDate(new Date(), "yyyy-MM-dd,HH:mm:ss");		
		String[] dateArray = da.trim().split(",");
		if(dateArray.length ==2)
		{
			logoutDate = dateArray[0];
			logoutTime = dateArray[1];
			
		}
		String query = "update useradmin.loginDetails set logoutTime:="+logoutTime +",logout:=1 conditions loginDetails.userId:=["+
						userId+"] and loginDetails.loginDate:=["+logoutDate+"] and loginDetails.logout:=0";

		try {
			ConnectionDatabase.GetInstanceOfDatabaseComponent().stringQueryList(query);
		} catch (DBComponentException e) {
			e.printStackTrace();
		}
	}

}
