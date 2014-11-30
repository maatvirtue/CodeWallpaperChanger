package com.theprophet31337.codewallpaperchanger.tool;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.theprophet31337.codewallpaperchanger.externaltools.PerlinNoise;
import com.theprophet31337.codewallpaperchanger.util.GenericUtil;

public class BackgroundGenerator
{
	public static BufferedImage generateBackground(int width, int height, Color primary, Color secondary)
	{
		PerlinNoise perlinNoise=new PerlinNoise();
		float[][] noiseArray=perlinNoise.generateOctavedSimplexNoise(width, height, 10, 0.4f, 0.005f);
		BufferedImage image=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Color color;

		for(int y=0; y<height; y++)
			for(int x=0; x<width; x++)
			{
				color=interpolate(primary, secondary, (float)GenericUtil.rescale(-1.05, 1.05, 0, 1, noiseArray[x][y]));
				
				image.setRGB(x, y, color.getRGB());
			}

		return image;
	}

	private static Color interpolate(Color x, Color y, float blending)
	{
		float inverse_blending = 1 - blending;

		float red =   x.getRed()   * blending   +   y.getRed()   * inverse_blending;
		float green = x.getGreen() * blending   +   y.getGreen() * inverse_blending;
		float blue =  x.getBlue()  * blending   +   y.getBlue()  * inverse_blending;

		//note that if i pass float values they have to be in the range of 0.0-1.0 
		//and not in 0-255 like the ones i get returned by the getters.
		return new Color (red / 255, green / 255, blue / 255);
	}
}
