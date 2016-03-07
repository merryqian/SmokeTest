package com.test.util;

public class TestMD5 {
	 public static void main( String xu[] )
	 { // 计算 "a" �? MD5 代码，应该为�?0cc175b9c0f1b6a831c399e269772661
	  System.out.println( MD5Util.getMD5(MD5Util.getMD5("!QAZ2wsx".getBytes()).getBytes()) );
	 }

}


