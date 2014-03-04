package com.oxymedical.component.UniqueIDGeneratorComponent;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.rmi.server.UID;
import java.util.Hashtable;
import java.util.Random;
import java.util.UUID;

/*
import org.safehaus.uuid.EthernetAddress;
import org.safehaus.uuid.NativeInterfaces;
import org.safehaus.uuid.UUIDGenerator;
*/
import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.maintenanceData.IMaintenanceData;

public class UniqueIdGenerator implements IComponent
{
	/*
	 * Implementation changed. JUG did not support 64 bit OS and hence the
	 * implementation now uses Java inbuilt UUID.
	 * 
	 * Changes done by HS dated 12-Jun-2009
	 */
	
//	static UUIDGenerator uniqueIdGenerator = UUIDGenerator.getInstance();
	public UniqueIdGenerator()
	{
	    //public final String id=null;
	}
	public String generatePassword(int length) {
		Random rgen = new Random();
		byte decision;
		byte numValue;
		char charValue;
		StringBuilder sb = new StringBuilder();
		while (sb.length() < length) {
			decision = (byte) rgen.nextInt(2);
			numValue = (byte) rgen.nextInt(10);
			charValue = (char) (rgen.nextInt(25) + 65);
			sb.append((decision % 2 == 0) ? (charValue + "") : (numValue + ""));
		}
		return sb.toString();
	}
/*	private static boolean noEthernetInterfaces;
	private static EthernetAddress hwAddress = null;
	static
	{
		NativeInterfaces.setUseStdLibDir(true);
	}

	public static EthernetAddress getHWAddress()
	{
		if (noEthernetInterfaces) { return null; }

		if (hwAddress != null) { return hwAddress; }

		try
		{
			hwAddress = NativeInterfaces.getPrimaryInterface();
		}
		catch (Throwable t)
		{
			hwAddress = null;
			// can't get the hardware address for some reason
			System.err.println("OTrunk: Not using native library for uuids");
			noEthernetInterfaces = true;
			// return null;
		}

		if (hwAddress == null)
		{
			System.err.println("primary interface is null");
			EthernetAddress[] addressArray = NativeInterfaces.getAllInterfaces();
			for (int i = 0; i < addressArray.length; i++)
			{
				if (addressArray[i] != null)
				{
					hwAddress = addressArray[i];
				}
			}
			if (hwAddress == null)
			{
				System.err.println("Can't find a native interface");
			}
		}

		if (hwAddress == null)
		{
			noEthernetInterfaces = true;
		}

		return hwAddress;
	}
*/
	public static String generateUniqueID(String uniqueClass)
	{
		try
		{
			// Get IPAddress Segment
			InetAddress addr = InetAddress.getLocalHost();
			UID userId = new UID();
			NetworkInterface ni = NetworkInterface.getByInetAddress(addr);
			
			String id = UUID.randomUUID().toString();
			//String id1 = UUID.nameUUIDFromBytes(ni.getHardwareAddress()).toString();
			String id1 = UUID.randomUUID().toString();
			String id2 = UUID.nameUUIDFromBytes(userId.toString().getBytes()).toString();
			return id + addr + id1 + uniqueClass + id2;

			/*EthernetAddress address = UniqueIdGenerator.getHWAddress();
            String ID = uniqueIdGenerator.generateTimeBasedUUID().toString();
            String ID1 = uniqueIdGenerator.generateRandomBasedUUID().toString();
            String ID2 = uniqueIdGenerator.generateTimeBasedUUID(address).toString();
    		return ID + addr + ID1 + uniqueClass + ID2;*/
		}
		catch(java.net.UnknownHostException ex)
		{
			ex.printStackTrace();
		}
		catch (SocketException se)
		{
			se.printStackTrace();
		}

		return UUID.randomUUID().toString() + uniqueClass + UUID.randomUUID().toString();

	}


	public static void main(String args[])
	{

		UniqueIdGenerator Id = new UniqueIdGenerator();
		String ID =Id.generateUniqueID("Input Data");
		System.out.println("[ID]" + ID);
	}

	public void destroy() {

	}


	public void run() {

	}

	public void start(Hashtable<String, org.dom4j.Document> arg0) {

	}

	public void stop() 
	{

	}

	public IHICData getHicData() {
		return null;
	}

	public void maintenance(IMaintenanceData arg0) {

	}

	public void setHicData(IHICData arg0) {

	}


}
