package com.theprophet31337.codewallpaperchanger.util;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;

public class WallpaperUtil
{
	private static interface User32 extends Library
	{
		User32 INSTANCE=(User32)Native.loadLibrary("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);

		boolean SystemParametersInfo(int one, int two, String s, int three);
	}

	public static void setWallpaper(String path)
	{
		User32.INSTANCE.SystemParametersInfo(0x0014, 0, path, 1);
	}
}
