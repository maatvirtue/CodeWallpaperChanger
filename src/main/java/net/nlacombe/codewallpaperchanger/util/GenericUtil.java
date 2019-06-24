package net.nlacombe.codewallpaperchanger.util;

import java.util.Random;

public class GenericUtil
{
	public static double rescale(double oldLowerBound, double oldUpperBound, double newLowerBound, double newUpperBound, double value)
	{
		if(value>oldUpperBound||value<oldLowerBound)
			throw new IllegalArgumentException("value out of old bounds: "+
					value+" !e ["+oldLowerBound+","+oldUpperBound+"]");

		return (((newUpperBound-newLowerBound)*(value-oldLowerBound))/(oldUpperBound-oldLowerBound))+newLowerBound;
	}

	public static void shuffleArray(int[] ar)
	{
		Random rnd=new Random();
		for(int i=ar.length-1; i>0; i--)
		{
			int index=rnd.nextInt(i+1);

			int a=ar[index];
			ar[index]=ar[i];
			ar[i]=a;
		}
	}
}
