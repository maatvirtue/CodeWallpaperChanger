package net.nlacombe.codewallpaperchanger.constant;

import java.awt.Color;
import java.awt.Font;

public class Constants
{
	//Text rendering
	public static Font DEFAULT_FONT=new Font("Arial", Font.PLAIN, 10);
	public static Color DEFAULT_TEXT_COLOR=new Color(54, 178, 255);
	public static int DEFAULT_NUMBER_OF_SPACES_FOR_TAB=4;
	public static int DEFAULT_PADDING=10;
	
	//Background rendering
	public static Color DEFAULT_BG_PRIMARY_COLOR=new Color(28, 101, 133);
	public static Color DEFAULT_BG_SECONDARY_COLOR=Color.BLACK;
	
	//General
	public static String CONFIG_FILE_NAME="config.json";
	public static String APPLICATION_FOLDER_NAME=".codeWallpaperChanger";
}
