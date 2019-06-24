package net.nlacombe.codewallpaperchanger.util;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;
import net.nlacombe.codewallpaperchanger.domain.OsType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class WallpaperService
{
	private static Logger logger = LoggerFactory.getLogger(WallpaperService.class);

	private interface User32 extends Library
	{
		boolean SystemParametersInfo(int one, int two, String s, int three);
	}

	private static WallpaperService instance;

	private OsType osType;
	private User32 windowsUser32NativeLibrary;

	private WallpaperService()
	{
		osType = OsType.getHostOsType();

		if (OsType.WINDOWS.equals(osType))
			windowsUser32NativeLibrary = (User32)Native.loadLibrary("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);
	}

	public static WallpaperService getInstance()
	{
		if (instance == null)
			instance = new WallpaperService();

		return instance;
	}

	public void setWallpaper(Path wallpaperImageFilePath)
	{
		String wallpaperImageAbsolutePath = wallpaperImageFilePath.toAbsolutePath().toString();

		if (OsType.WINDOWS.equals(osType))
			windowsUser32NativeLibrary.SystemParametersInfo(0x0014, 0, wallpaperImageAbsolutePath, 1);
		else
			logger.warn("Warning: OS not supported. Wallpaper will not be set. Wallpaper image was generated at: " + wallpaperImageAbsolutePath);
	}
}
