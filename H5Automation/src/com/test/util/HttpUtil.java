package com.test.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class HttpUtil {
	static String result="";
	public  String doPost(String url,String params){
		URL urlPost = null;
		HttpURLConnection http = null;
		 
		try {
			urlPost = new URL(url);
			http = (HttpURLConnection)urlPost.openConnection();
			http.setDoInput(true);
			http.setDoOutput(true);
			http.setRequestMethod("POST");
			http.setRequestProperty("Content-Type", "application/json");
			http.setRequestProperty("Charset", "UTF-8");
			http.connect();
			
			OutputStreamWriter out = new OutputStreamWriter(http.getOutputStream(),"utf-8");
			out.write(params);
			out.flush();
			out.close();
			if(http.getResponseCode()==200){
				BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream(),"utf-8"));
				String inputLine = "";
				while((inputLine=in.readLine())!=null){
					result += inputLine;
				}
				in.close();
			}
			
			System.out.println("result="+result);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return result;
	}
	public  boolean verifyResult(String url,String param)
	{
		boolean flag=false;
		HttpUtil http=new HttpUtil();
		String result=http.doPost(url,param);
		String getMessage=http.getResultMessage(result);
		if(getMessage.equals("查询购物车失败"))
			{
			Log.logInfo("购物车为空");
			flag=true;
			}
		else
			{
			 Log.logInfo("购物车不为空");
			 flag=false;
			}
		return flag;
	}
	private  String getResultMessage(String result)
	{
		String jsonMessage = result;
		  String value1 = null;
		  try
		  {
		   //将字符串转换成jsonObject对象
		   JSONObject myJsonObject = new JSONObject(jsonMessage);
		   //获取对应的值
		   value1 = myJsonObject.getString("resultMsg");
		  }
		  catch (JSONException e)
		  {
			  Log.logInfo("获取出错了");
		  }
		  return value1;
	}
	public static void main(String[] args) {
		HttpUtil http=new HttpUtil();
		String url="http://10.201.128.190:9081/iokblShoppingCartApi/cart/queryShoppingCart.htm";
		String param="{\"memberId\":\"100000000011758\",\"member_token\":\"ABe3O8nwSWyzimeg-Ed13Q\",\"shoppingCartId\":\"43545454\",\"orderSourceCode\":\"1\"}";
		String result=http.doPost(url,param);
		Log.logInfo(http.getResultMessage(result));
		Log.logInfo(http.verifyResult(url, param));
	}
}
