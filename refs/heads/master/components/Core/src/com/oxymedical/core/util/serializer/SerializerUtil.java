package com.oxymedical.core.util.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;

public class SerializerUtil
{
	public static String serialize(Object obj)
	{
		String serializedObject = null;
		
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oout = new ObjectOutputStream(baos);
			oout.writeObject(obj);
			oout.close();
			byte[] buf = baos.toByteArray();

			serializedObject = new sun.misc.BASE64Encoder().encode(buf);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return serializedObject;
	}

	public static Object deserialize(String str)
	{
		Object deserializedObject = null;
		
		try
		{
			byte[] buf = new sun.misc.BASE64Decoder().decodeBuffer(str);
			ByteArrayInputStream bais = new ByteArrayInputStream(buf);
			ObjectInputStream oin = new ObjectInputStream(bais);
			deserializedObject = oin.readObject();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return deserializedObject;
	}
	
	
	public static void main(String[] args)
	{
		Hashtable<String, Object> ht = new Hashtable<String, Object>();
		ht.put("PatientId", "234");
		ht.put("PatientMRN", "54345545");
		ht.put("InputScreen", "ToolDownsamplingInput");
		
		String serStr = serialize(ht);
		System.out.println("[serStr]" + serStr);
		
		Object ret = deserialize(serStr);
		if (ret.getClass() == Hashtable.class)
		{
			Hashtable<String, Object> htRet = (Hashtable<String, Object>)ret;
			Enumeration<String> keys = htRet.keys();
			while (keys.hasMoreElements())
			{
				String key = keys.nextElement();
				System.out.println("[key]" + key + "\t[value]" + htRet.get(key));
			}
		}
		
		String str = "Harpreet Singh";
		serStr = serialize(str);
		System.out.println("[serStr]" + serStr);
		ret = deserialize(serStr);
		System.out.println("[ret]" + ret);
	}
}
