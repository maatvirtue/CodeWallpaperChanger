package net.nlacombe.codewallpaperchanger.imagegeneration;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import net.nlacombe.codewallpaperchanger.constant.Constants;

public class TextRenderer
{
	public static BufferedImage renderText(String text, Font font, Color fontColor, int numberOfSpacesForTab)
	{
		String originalText=text;
		text=replaceTabWithSpaces(text, numberOfSpacesForTab);
		
		String[] lines=text.split("\r?\n");
		Point imageSize=getImageDimensions(lines, font);
		
		BufferedImage image=new BufferedImage(imageSize.x, imageSize.y, BufferedImage.TYPE_INT_ARGB);
		
		return renderText(image, originalText, font, fontColor, numberOfSpacesForTab);
	}
	
	public static Point getImageDimensions(String text, Font font, int numberOfSpacesForTab)
	{
		text=replaceTabWithSpaces(text, numberOfSpacesForTab);
		String[] lines=text.split("\r?\n");
		
		return getImageDimensions(lines, font);
	}
	
	public static BufferedImage renderText(BufferedImage image, int posX, int posY, String text, Font font, Color fontColor, int numberOfSpacesForTab)
	{
		text=replaceTabWithSpaces(text, numberOfSpacesForTab);
		String[] lines=text.split("\r?\n");
		
		Graphics2D g2d=image.createGraphics();
		setBaseConfig(g2d, font, fontColor);
		FontMetrics fm=g2d.getFontMetrics();
		
		posY+=fm.getAscent();
		
		for(String line: lines)
		{
			g2d.drawString(line, posX, posY);
			
			posY+=fm.getHeight();
		}
		
		g2d.dispose();
		
		return image;
	}
	
	public static BufferedImage renderText(BufferedImage image, String text, Font font, Color fontColor, int numberOfSpacesForTab)
	{
		text=replaceTabWithSpaces(text, numberOfSpacesForTab);
		String[] lines=text.split("\r?\n");
		
		Graphics2D g2d=image.createGraphics();
		setBaseConfig(g2d, font, fontColor);
		FontMetrics fm=g2d.getFontMetrics();
		
		int posX=Constants.DEFAULT_PADDING;
		int posY=fm.getAscent()+Constants.DEFAULT_PADDING;
		
		for(String line: lines)
		{
			g2d.drawString(line, posX, posY);
			
			posY+=fm.getHeight();
		}
		
		g2d.dispose();
		
		return image;
	}
	
	private static String replaceTabWithSpaces(String text, int numberOfSpacesForTab)
	{
		StringBuilder sb=new StringBuilder();
		
		for(int i=0; i<numberOfSpacesForTab; i++)
			sb.append(' ');
		
		String spaces=sb.toString();
		
		return text.replace("\t", spaces);
	}
	
	private static Point getImageDimensions(String[] lines, Font font)
	{
		/*
		 * Because font metrics is based on a graphics context, we need to
		 * create a small, temporary image so we can ascertain the width and
		 * height of the final image
		 */
		BufferedImage img=new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d=img.createGraphics();
		g2d.setFont(font);
		FontMetrics fm=g2d.getFontMetrics();
		
		int longestLineWidth=0;
		int lineWidth;
		
		for(int i=0; i<lines.length; i++)
		{
			//lineWidth=(int)fm.getStringBounds(lines[i], g2d).getWidth();
			lineWidth=fm.stringWidth(lines[i]);
			
			if(longestLineWidth<lineWidth)
				longestLineWidth=lineWidth;
		}
		
		int width=longestLineWidth+2*Constants.DEFAULT_PADDING;
		int height=fm.getHeight()*lines.length+2*Constants.DEFAULT_PADDING;
		Point imageSize=new Point(width, height);
		
		g2d.dispose();
		
		return imageSize;
	}
	
	private static void setBaseConfig(Graphics2D g2d, Font font, Color fontColor)
	{
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g2d.setFont(font);
		g2d.setColor(fontColor);
	}
}
