package com.theprophet31337.codewallpaperchanger.config;

import java.io.*;

import com.google.gson.*;

public class ConfigFileReader
{
	private File configFile;
	
	public ConfigFileReader(File configFile)
	{
		this.configFile=configFile;
	}
	
	/**
	 * Reads the JSON configuration file.
	 * 
	 * @return a Config obejct.
	 */
	public Config parse() throws IOException
	{
		try
		(
				FileReader reader=new FileReader(configFile);
		)
		{
			Config config=new Config();
			
			JsonElement rootElement=new JsonParser().parse(reader);
			
			if(!validate(rootElement))
				throw new IOException("Invalid configuration file");
			
			JsonObject root=(JsonObject)rootElement;
			
			config.setSourceCodeFolder(new File(root.get("sourceCodeFolder").getAsString()));
			config.setSourceCodeExtension(root.get("sourceCodeExtension").getAsString());
			config.setImageWidth(root.get("imageWidth").getAsInt());
			config.setImageHeight(root.get("imageHeight").getAsInt());
			
			return config;
		}
	}
	
	/**
	 * Validate root properties in the configuration file.
	 */
	private boolean validate(JsonElement rootElement)
	{
		if(!(rootElement instanceof JsonObject))
			return false;
		
		JsonObject root=(JsonObject)rootElement;
		
		if(!root.has("sourceCodeFolder")||!root.has("sourceCodeExtension")||
				!root.has("imageWidth")||!root.has("imageHeight"))
			return false;
		
		return true;
	}
}
