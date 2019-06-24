package net.nlacombe.codewallpaperchanger.domain;

public enum OsType
{
	WINDOWS, MAC, LINUX, OTHER;

	public static OsType getHostOsType()
	{
		String osName = System.getProperty("os.name").toLowerCase();

		if (osName.startsWith("windows"))
			return WINDOWS;
		else
			return OTHER;
	}
}
