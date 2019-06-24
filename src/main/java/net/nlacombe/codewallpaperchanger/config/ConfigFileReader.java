package net.nlacombe.codewallpaperchanger.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.nlacombe.codewallpaperchanger.exception.CodeWallpaperChangerException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class ConfigFileReader
{
	public static Config parse(Path configFilePath)
	{
		try (FileReader reader=new FileReader(configFilePath.toFile()))
		{
			Config config=new Config();
			
			JsonElement rootElement=new JsonParser().parse(reader);
			
			if(!validate(rootElement))
				throw new CodeWallpaperChangerException("Invalid configuration file");
			
			JsonObject root=(JsonObject)rootElement;
			
			config.setSourceCodeFolder(Path.of(root.get("sourceCodeFolder").getAsString()));
			config.setSourceCodeExtension(root.get("sourceCodeExtension").getAsString());
			config.setImageWidth(root.get("imageWidth").getAsInt());
			config.setImageHeight(root.get("imageHeight").getAsInt());
			
			return config;
		}
		catch(FileNotFoundException e)
		{
			throw new CodeWallpaperChangerException("Error: configuration file not found: " + configFilePath.toAbsolutePath());
		}
		catch(IOException e)
		{
			throw new CodeWallpaperChangerException("Error: error reading configuration file: " + configFilePath.toAbsolutePath());
		}
	}

	private static boolean validate(JsonElement rootElement)
	{
		if(!(rootElement instanceof JsonObject))
			return false;
		
		JsonObject root=(JsonObject)rootElement;

		return root.has("sourceCodeFolder") &&
				root.has("sourceCodeExtension") &&
				root.has("imageWidth") &&
				root.has("imageHeight");
	}
}
