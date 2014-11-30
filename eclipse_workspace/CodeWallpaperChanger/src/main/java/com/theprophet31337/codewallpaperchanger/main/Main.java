package com.theprophet31337.codewallpaperchanger.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.theprophet31337.codewallpaperchanger.config.Config;
import com.theprophet31337.codewallpaperchanger.config.ConfigFileReader;
import com.theprophet31337.codewallpaperchanger.constant.Constants;
import com.theprophet31337.codewallpaperchanger.core.CodeWallpaperChanger;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		Config config;
		
		try
		{
			ConfigFileReader configReader=new ConfigFileReader(new File(Constants.CONFIG_FILE_NAME));
			config=configReader.parse();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Error: configuration file \""+Constants.CONFIG_FILE_NAME+"\" not found.");
			return;
		}
		catch(IOException e)
		{
			System.out.println("Error: error reading configuration file.");
			return;
		}
		
		CodeWallpaperChanger codeWallpaperChanger=new CodeWallpaperChanger(config.getSourceCodeFolder(),
				config.getSourceCodeExtension(), config.getImageWidth(), config.getImageHeight());
		codeWallpaperChanger.changeWallpaper();
	}
}
