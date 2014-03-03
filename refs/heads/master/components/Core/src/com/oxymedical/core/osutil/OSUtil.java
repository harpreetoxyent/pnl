package com.oxymedical.core.osutil;

public class OSUtil
{

    public static OSName getOperatingSystem()
	{
		String os = System.getProperty("os.name").trim().toLowerCase();
		OSName realOS = null;
		
		if (os.indexOf("windows") >= 0)
		{
			realOS = OSName.WINDOWS;
		}
		else if (os.indexOf("linux") >= 0)
		{
			realOS = OSName.LINUX;
		}
		else if (os.indexOf("solaris") >= 0 || os.indexOf("sunos") >= 0)
		{
			realOS = OSName.SOLARIS;
		}
		else if (os.indexOf("mac os x") >= 0 || os.indexOf("macosx") >= 0)
		{
			realOS = OSName.MACOSX;
		}
		else if (os.indexOf("bsd") >= 0)
		{
			if (os.indexOf("freebsd") >= 0)
			{
				realOS = OSName.FREEBSD;
			}
			else if (os.indexOf("netbsd") >= 0)
			{
				realOS = OSName.NETBSD;
			}
			else if (os.indexOf("openbsd") >= 0)
			{
				realOS = OSName.OPENBSD;
			}
			else
			{ // default
				realOS = OSName.BSD;
			}
		}
		else if (os.indexOf("aix") >= 0)
		{
			realOS = OSName.AIX;
		}
		else if (os.indexOf("hp ux") >= 0)
		{
			realOS = OSName.HPUX;
		}
		return realOS;
	}
    

    public static OSType getOperatingSystemArch()
	{
		String arch = System.getProperty("os.arch").trim().toLowerCase();
		OSType realArch = null;

		if (arch.indexOf("64") >= 0)
		{
			realArch = OSType.BIT64;
		}
		else if  (arch.indexOf("ppc") >= 0)
		{
			realArch = OSType.PPC;
		}
		else if (arch.indexOf("86") >= 0)
		{
			realArch = OSType.BIT32;
		}
		else if (arch.indexOf("amd") >= 0)
		{
			realArch = OSType.AMD;
		}
		return realArch;
	}
    
    
    public static void main(String[] args)
    {
    	System.out.println(getOperatingSystem());
    	System.out.println(getOperatingSystemArch());
    }
}
