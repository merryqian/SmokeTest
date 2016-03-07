package com.test.bean;

import com.test.util.ParseXml;


public class Config {
	public static String browser;
	public static int waitTime;
	public static String path;
	static {
		ParseXml px = new ParseXml();
		px.load("Config/config.xml");
		browser = px.getElementText("/config/browser");
		waitTime = Integer.valueOf(px.getElementText("/config/waitTime"));
		path = px.getElementText("/config/pathURL");
	}

}
