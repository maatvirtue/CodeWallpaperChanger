package com.theprophet31337.codewallpaperchanger.config;

import java.io.File;

public class Config
{
	private File sourceCodeFolder;
	private String sourceCodeExtension;
	private int imageWidth;
	private int imageHeight;

	public File getSourceCodeFolder()
	{
		return sourceCodeFolder;
	}

	public void setSourceCodeFolder(File sourceCodeFolder)
	{
		this.sourceCodeFolder=sourceCodeFolder;
	}

	public String getSourceCodeExtension()
	{
		return sourceCodeExtension;
	}

	public void setSourceCodeExtension(String sourceCodeExtension)
	{
		this.sourceCodeExtension=sourceCodeExtension;
	}

	public int getImageWidth()
	{
		return imageWidth;
	}

	public void setImageWidth(int imageWidth)
	{
		this.imageWidth=imageWidth;
	}

	public int getImageHeight()
	{
		return imageHeight;
	}

	public void setImageHeight(int imageHeight)
	{
		this.imageHeight=imageHeight;
	}
}
