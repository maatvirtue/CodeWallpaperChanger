package net.nlacombe.codewallpaperchanger.config;

import java.nio.file.Path;

public class Config
{
	private Path sourceCodeFolder;
	private String sourceCodeExtension;
	private int imageWidth;
	private int imageHeight;

	public Path getSourceCodeFolder() {
		return sourceCodeFolder;
	}

	public void setSourceCodeFolder(Path sourceCodeFolder) {
		this.sourceCodeFolder = sourceCodeFolder;
	}

	public String getSourceCodeExtension() {
		return sourceCodeExtension;
	}

	public void setSourceCodeExtension(String sourceCodeExtension) {
		this.sourceCodeExtension = sourceCodeExtension;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}
}
