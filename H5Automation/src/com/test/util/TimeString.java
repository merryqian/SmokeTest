package com.test.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeString {
	Calendar calendar = new GregorianCalendar();
	private String valueOfString(String str, int len) {
		String string = "";
		for (int i = 0; i < len - str.length(); i++) {
			string = string + "0";
		}
		return (str.length() == len) ? (str) : (string + str);
	}
	/**
	 * 返回当前时间，格式为�?2014-12-18 15:11:50
	 * @return
	 */
	public String getSimpleDateFormat(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date()); 		 
	}	
	
	/**
	 * 返回当前时间�?
	 * @return
	 */
//	public String getTime(){
//		return String.valueOf(new Date().getTime());
//	}
	
	/**
	 * 生成�?个长度为17的时间字符串，精确到毫秒
	 * @return
	 */
	
	public String getDate() {
		
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = this.valueOfString(String.valueOf(calendar.get(Calendar.MONTH) + 1),2);	
		String day = this.valueOfString(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)),2);
		return year+month+day;
	}	
	public String getDate(String split) {
		Calendar cl = new GregorianCalendar();
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = this.valueOfString(String.valueOf(calendar.get(Calendar.MONTH) + 1),2);	
		String day = this.valueOfString(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)),2);
		System.out.println("getDate="+year+split+month+split+day);
		return year+split+month+split+day;
	}
	public String getTime()
	{
		String hour = this.valueOfString(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)),2);
		String minute = this.valueOfString(String.valueOf(calendar.get(Calendar.MINUTE)),2);
		String second = this.valueOfString(String.valueOf(calendar.get(Calendar.SECOND)),2);
		//String millisecond = this.valueOfString(String.valueOf(calendar.get(Calendar.MILLISECOND)),3);
		return hour+minute+second;
	}
	public String getTime(String split)
	{
		Calendar cl = new GregorianCalendar();
		String hour = this.valueOfString(String.valueOf(cl.get(Calendar.HOUR_OF_DAY)),2);
		String minute = this.valueOfString(String.valueOf(cl.get(Calendar.MINUTE)),2);
		String second = this.valueOfString(String.valueOf(cl.get(Calendar.SECOND)),2);
		//String millisecond = this.valueOfString(String.valueOf(calendar.get(Calendar.MILLISECOND)),3);
		System.out.println("getTime="+hour+split+minute+split+second);
		return hour+split+minute+split+second;
	}

}
