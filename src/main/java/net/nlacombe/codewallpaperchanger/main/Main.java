package net.nlacombe.codewallpaperchanger.main;

import net.nlacombe.codewallpaperchanger.config.ConfigFileReader;
import net.nlacombe.codewallpaperchanger.constant.Constants;
import net.nlacombe.codewallpaperchanger.service.CodeWallpaperChangerService;
import net.nlacombe.codewallpaperchanger.exception.CodeWallpaperChangerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;

public class Main
{
	private static Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws IOException
	{
		try
		{
			var config = ConfigFileReader.parse(Path.of(Constants.CONFIG_FILE_NAME));
			var codeWallpaperChanger=new CodeWallpaperChangerService(config.getSourceCodeFolder(),
					config.getSourceCodeExtension(), config.getImageWidth(), config.getImageHeight());

			codeWallpaperChanger.changeWallpaper();
		}
		catch (CodeWallpaperChangerException e)
		{
			logger.error("Error: " + e.getMessage());
		}
	}
}
