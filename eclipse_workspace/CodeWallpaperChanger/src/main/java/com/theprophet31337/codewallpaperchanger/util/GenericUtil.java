package com.theprophet31337.codewallpaperchanger.util;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

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
			// Simple swap
			int a=ar[index];
			ar[index]=ar[i];
			ar[i]=a;
		}
	}

	public static List<File> getAllFilesUnderDirectory(File directory, final Filter<File> fileFilter) throws IOException
	{
		final List<File> files=new LinkedList<File>();

		Files.walkFileTree(directory.toPath(), new SimpleFileVisitor<Path>()
		{
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
			{
				if(fileFilter.isPassingFilter(dir.toFile()))
					files.add(dir.toFile());

				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
			{
				if(fileFilter.isPassingFilter(file.toFile()))
					files.add(file.toFile());

				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException
			{
				return FileVisitResult.CONTINUE;
			}
		});

		return files;
	}
}
