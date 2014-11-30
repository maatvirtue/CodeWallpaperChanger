package com.theprophet31337.codewallpaperchanger.util;

public interface Filter<T>
{
	public boolean isPassingFilter(T value);
}
