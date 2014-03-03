// Source File Name:   URLConstants.java

package com.oxymedical.servlet.HICServlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import com.oxymedical.core.propertyUtil.PropertyUtil;


public class URLConstants
{

    public URLConstants()
    {
    }
    private static String portAddress = "8080";
    public static final String sourceURL = "sourceURL";
    public static final String addUserURL = "addUserURL";
    public static final String appConfigInfo = PropertyUtil.setUpProperties("GLASSFISH_DOMAIN_HOME") + "/config/applicationInfomation.xml";
    public static final String appInfo = PropertyUtil.setUpProperties("GLASSFISH_DOMAIN_HOME") + "/applications/j2ee-modules/";
    public static final String applicationName = "ApplicationName";
    public static final String appFolder = "applicationInfo";
    public static final String sourcedir = "sourcedir";
    public static final String serverIPAddress = "serverIPAddress";
    public static final String serverAddressAppended = "http://"+ getCurrentMachineIPAddress() +"/c";
    public static final String serverAddress = "http://"+ getCurrentMachineIPAddress() +"/";
    public static final String serverDirectory = "serverDirectory";
    public static final String serverLibDirectory = "serverLibDirectory";
    public static final String deploy = "deploy";
    public static final String controllerServlet = "ControllerServlet";
    public static final String isRenderingForward = "renderForward";
    public static final String isBillingTracking = "billingTracking";
    public static final String billingServlet = "billingServlet";
    public static final String isImport = "import";
    public static final String importServlet = "ImportServlet";
    public static final String UserAdminServlet = "UserAdminServlet";
    public static final String UAServlet = "UAServlet";
    public static final String isUserAdmin = "UserAdmin";
    public static final String users = "users";
    public static final String authenticate = "authenticate";
    public static final String usersXML = "users.xml";
    public static final String renderOption = "renderOption";
    public static final String zk = "ZK";
    public static final String lazlo = "OpenLaszlo";
    public static final String DATABASESETTING = "/data/datasettings.xml";
    public static final String APP_EXT = ".esp";
    public static final String BASE_APPLICATION_NAME = "BaseAppName";
    
    public static final String LOGIN_DONE = "LoginDone";
    public static final String USER_ADMIN_APP = "UserAdminApp";
    //if want to get application name from session use this attribute
    public static final String CUURENT_APP = "currentApp";
    public static final String APPLICATION_INFO_FILENAME = "applicationinformation.xml";

    private static String hostIp = null;


    private static String getCurrentMachineIPAddress()
	{
		return getCurrentMachineIPAddress(URLConstants.portAddress);
	}

	private static String getCurrentMachineIPAddress(String portNumber)
	{
		if (hostIp == null)
		{
			String ipAddrStr = "";
			try
			{
				InetAddress addr = InetAddress.getLocalHost();

				// Get IP Address
				byte[] ipAddr = addr.getAddress();
				for (int i=0; i<ipAddr.length; i++)
				{
					if (i > 0)
					{
						ipAddrStr = ipAddrStr + ".";
					}
					ipAddrStr = ipAddrStr + (ipAddr[i]&0xFF);
				}

				// Get hostname
				//			String hostname = addr.getHostName();
			}
			catch (UnknownHostException e)
			{
				ipAddrStr = "127.0.0.1";
			}

			hostIp = ipAddrStr;
			System.out.println("[getCurrentMachineIPAddress][hostIp]" + hostIp);
		}
		return hostIp.concat(":").concat(URLConstants.portAddress);
	}
}
