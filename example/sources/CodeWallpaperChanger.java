package com.theprophet31337.codewallpaperchanger.core;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.imageio.ImageIO;

import com.theprophet31337.codewallpaperchanger.constant.Constants;
import com.theprophet31337.codewallpaperchanger.tool.BackgroundGenerator;
import com.theprophet31337.codewallpaperchanger.tool.TextRenderer;
import com.theprophet31337.codewallpaperchanger.util.Filter;
import com.theprophet31337.codewallpaperchanger.util.GenericUtil;
import com.theprophet31337.codewallpaperchanger.util.WallpaperUtil;

public class CodeWallpaperChanger
{
	private File sourceCodeFolder;
	private String extension;
	private int width;
	private int height;
	
	public CodeWallpaperChanger(File sourceCodeFolder, String extension, int width, int height)
	{
		this.sourceCodeFolder=sourceCodeFolder;
		this.extension=extension;
		this.width=width;
		this.height=height;
	}
	
	public void changeWallpaper() throws IOException
	{
		File wallpaper=getWallpaperFile();
		
		generateImage(wallpaper);
		
		WallpaperUtil.setWallpaper(wallpaper.getAbsolutePath());
	}
	
	private void generateImage(File wallpaper) throws IOException
	{
		//Get List of all possible source files to use
		List<File> sourceFileSet=getSourceFileSet();
		File sourceFile;
		int textBorderX=Constants.DEFAULT_PADDING;
		int textBorderY;
		int posX;
		int posY;
		int maxX=0;
		Point imageSize=new Point();
		Random random=new Random();
		
		//Generate the background of the image
		BufferedImage image=BackgroundGenerator.generateBackground(width, height,
				Constants.DEFAULT_BG_PRIMARY_COLOR, Constants.DEFAULT_BG_SECONDARY_COLOR);
		
		/* While we haven't filled the image with code
		 * select a new source file to put on the image.
		 * 
		 * We fill the first "column" and continue to the next column
		 * until the text boundaries are bigger than the image.
		 */
		
		while(textBorderX<width)
		{
			textBorderY=Constants.DEFAULT_PADDING;
			posX=textBorderX;
			maxX=0;
			
			while(textBorderY<height)
			{
				posY=textBorderY;
				
				//Select new source file
				sourceFile=sourceFileSet.get(random.nextInt(sourceFileSet.size()));
				sourceFileSet.remove(sourceFile);
				
				//Render image
				String text=new String(Files.readAllBytes(sourceFile.toPath()));
				
				image=TextRenderer.renderText(image, posX, posY, text, Constants.DEFAULT_FONT,
						Constants.DEFAULT_TEXT_COLOR, Constants.DEFAULT_NUMBER_OF_SPACES_FOR_TAB);
				
				//Recalculate new text border
				imageSize=TextRenderer.getImageDimensions(text,
						Constants.DEFAULT_FONT, Constants.DEFAULT_NUMBER_OF_SPACES_FOR_TAB);
				
				textBorderY+=imageSize.y;
				
				if(maxX<imageSize.x)
					maxX=imageSize.x;
			}
			
			textBorderX+=maxX;
		}
		
		writeImage(wallpaper, image);
	}
	
	private void writeImage(File wallpaper, BufferedImage image) throws IOException
	{
		Files.createDirectories(wallpaper.getParentFile().toPath());
		Files.deleteIfExists(wallpaper.toPath());
		Files.createFile(wallpaper.toPath());
		
		ImageIO.write(image, "png", wallpaper);
	}
	
	private List<File> getSourceFileSet() throws IOException
	{
		List<File> files=GenericUtil.getAllFilesUnderDirectory(sourceCodeFolder, new Filter<File>()
		{
			@Override
			public boolean isPassingFilter(File file)
			{
				return file.getName().endsWith(extension);
			}
		});
		
		return files;
	}
	
	public File getWallpaperFile()
	{
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd'T'HHmm");
		String dateString=df.format(new Date());
		String fileName="wallpaper_"+dateString+".png";
		
		String userHomePath=System.getProperty("user.home");
		File userHomeFolder=new File(userHomePath);
		File applicationFolder=new File(userHomeFolder, Constants.APPLICATION_FOLDER_NAME);

		return new File(applicationFolder, fileName);
	}
}
