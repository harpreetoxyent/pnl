package com.oxymedical.core.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class HashUtil
{
	/**
	 * Remove non required entries from Hashtable
	 * @param requiredKeys
	 * @param hashtable
	 * @return
	 */
	public static Hashtable<String, Object> removeNonRequiredKeysFromHashtable(
			String[] requiredKeys, Hashtable<String, Object> hashtable)
	{
		Hashtable<String, Object> retHashtable = null;
		// Validations
		if ((requiredKeys == null) || (requiredKeys.length <= 0)) 
			return new Hashtable<String, Object>();
		if ((hashtable == null) || (hashtable.size() <= 0)) 
			return null;
		
		retHashtable = new Hashtable<String, Object>();
		
		// Iterate through all keys
/*		Enumeration<String> metaDataKeys = retHashtable.keys();
		List<String> nonRequiredKeys = new ArrayList<String>(); 
		while (metaDataKeys.hasMoreElements())
		{
			String key = metaDataKeys.nextElement();

			// Check if key is required
			for (int i = 0; i < requiredKeys.length; i++)
			{
				System.out.println("[REQUIRED KEY]" + requiredKeys[i]);
				if (key.equals(requiredKeys[i]))
				{
					System.out.println("[REQUIRED KEY]--in not" + requiredKeys[i]);
					System.out.println("[NON REQUIRED KEY]" + key);
					nonRequiredKeys.add(key);
				}
			}
		}
		*/
		// Remove keys from Hashtable
		if (requiredKeys.length > 0)
		{
			for (int i = 0; i < requiredKeys.length; i++)
			{
				if(hashtable.get(requiredKeys[i]) instanceof ArrayList)
				 continue;	
				else
				{	
				String value=(String)hashtable.get(requiredKeys[i]);
				if(value!=null)
				retHashtable.put(requiredKeys[i],value);
				}
			}
		}
		return retHashtable;
	}
}
