package config;

import java.awt.GraphicsEnvironment;

import toolset.ViewStile;

public final class Configs{
	public static String Language = ViewStile.EN_US;
	public static String SystemWeight = ViewStile.DEFAULT_WEIGHT;
	public static String ColorSchema = ViewStile.DEFAULT_SCHEMA;
	public static GraphicsEnvironment ge = GraphicsEnvironment
	.getLocalGraphicsEnvironment();
	public static int Width = ge.getMaximumWindowBounds().width;
	public static int Height = ge.getMaximumWindowBounds().height;
}
