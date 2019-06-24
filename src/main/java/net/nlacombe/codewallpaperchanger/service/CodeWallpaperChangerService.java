package net.nlacombe.codewallpaperchanger.service;

import net.nlacombe.codewallpaperchanger.constant.Constants;
import net.nlacombe.codewallpaperchanger.exception.CodeWallpaperChangerException;
import net.nlacombe.codewallpaperchanger.imagegeneration.BackgroundGenerator;
import net.nlacombe.codewallpaperchanger.imagegeneration.TextRenderer;
import net.nlacombe.codewallpaperchanger.util.WallpaperService;

import javax.imageio.ImageIO;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CodeWallpaperChangerService
{
	private Path sourceCodeFolderPath;
	private String extension;
	private int width;
	private int height;
	private Random random;
	
	public CodeWallpaperChangerService(Path sourceCodeFolderPath, String extension, int width, int height)
	{
		this.sourceCodeFolderPath = sourceCodeFolderPath;
		this.extension = extension;
		this.width = width;
		this.height = height;

		random = new Random();
	}
	
	public void changeWallpaper() throws IOException
	{
		var wallpaperImageFilePath = getWallpaperFilePath();
		
		generateImage(wallpaperImageFilePath);
		
		WallpaperService.getInstance().setWallpaper(wallpaperImageFilePath);
	}
	
	private void generateImage(Path wallpaperImageFilePath) throws IOException
	{
		var sourceFilePaths = Files.walk(sourceCodeFolderPath)
				.filter(path -> path.getFileName().toString().endsWith(extension))
				.collect(Collectors.toList());
		int textBorderX=Constants.DEFAULT_PADDING;
		int textBorderY;
		int posX;
		int posY;
		int maxX;
		Point imageSize;
		
		if (sourceFilePaths.isEmpty())
			throw new CodeWallpaperChangerException("No source file found.");

		var image = BackgroundGenerator.generateBackground(width, height, Constants.DEFAULT_BG_PRIMARY_COLOR, Constants.DEFAULT_BG_SECONDARY_COLOR);
		
		/* While we haven't filled the image with code
		 * select a new source file to put on the image.
		 * 
		 * We fill the first "column" and continue to the next column
		 * until the text boundaries are bigger than the image.
		 */
		
		outerLoop:
		while(textBorderX < width)
		{
			textBorderY = Constants.DEFAULT_PADDING;
			posX = textBorderX;
			maxX = 0;
			
			while(textBorderY < height)
			{
				posY = textBorderY;

				var sourceFilePath = getNextRandomSourceFilePath(sourceFilePaths);

				if (sourceFilePath == null)
					break outerLoop;

				String sourceCodeText = new String(Files.readAllBytes(sourceFilePath));
				
				image = TextRenderer.renderText(image, posX, posY, sourceCodeText, Constants.DEFAULT_FONT,
						Constants.DEFAULT_TEXT_COLOR, Constants.DEFAULT_NUMBER_OF_SPACES_FOR_TAB);

				imageSize = TextRenderer.getImageDimensions(sourceCodeText,
						Constants.DEFAULT_FONT, Constants.DEFAULT_NUMBER_OF_SPACES_FOR_TAB);
				
				textBorderY += imageSize.y;
				
				if (maxX < imageSize.x)
					maxX = imageSize.x;
			}
			
			textBorderX += maxX;
		}
		
		writeImage(wallpaperImageFilePath, image);
	}

	private Path getNextRandomSourceFilePath(List<Path> sourceFilePaths)
	{
		if (sourceFilePaths.isEmpty())
			return null;

		var sourceFilePath = sourceFilePaths.get(random.nextInt(sourceFilePaths.size()));

		sourceFilePaths.remove(sourceFilePath);

		return sourceFilePath;
	}
	
	private void writeImage(Path wallpaperImageFilePath, BufferedImage image) throws IOException
	{
		Files.createDirectories(wallpaperImageFilePath.getParent());
		
		ImageIO.write(image, "png", wallpaperImageFilePath.toFile());
	}
	
	private Path getWallpaperFilePath()
	{
		var dateString = DateTimeFormatter.ofPattern("YYYY-MM-dd'T'HH:mm:ss").format(ZonedDateTime.now());
		var fileName = "wallpaper_"+dateString+".png";

		var userHomeFolder = Path.of(System.getProperty("user.home"));
		var applicationFolder = userHomeFolder.resolve(Constants.APPLICATION_FOLDER_NAME);

		return applicationFolder.resolve(fileName);
	}
}
