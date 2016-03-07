package com.test.util;

import org.testng.Assert;

public class Assertion {
	
	public static boolean flag = true;
	
	public static void verifyEquals(Object actual, Object expected){
		try{
			Assert.assertEquals(actual, expected);
		}catch(Error e){
			flag = false;
		}
	}
	
	public static void verifyEquals(Object actual, Object expected, String message){
		try{
			Assert.assertEquals(actual, expected, message);
			Log.logInfo("与预期结果相相等");
		}catch(Error e){
			flag = false;
			Log.logInfo("与预期结果不相等");
		}
	}

}
