package com.test.util;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * Created by snail on 2016/1/18.
 * Description: class for get config value
 */
public class PropertiesUtils {
    private static Map<String, Properties> propertiesMap = new HashMap<String, Properties>();

    public static Properties loadConfig(String fileKey){
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        Properties properties = null;

        try{
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            String path = fileKey;
            if(!path.endsWith(".properties")){
                path +=".properties";
            }

            inputStream = loader.getResourceAsStream(path);
            inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
            properties = new Properties();
            properties.load(inputStreamReader);
            propertiesMap.put(fileKey,properties);
        }catch (Exception e){
        	 Log.logError("[配置文件]  关闭文件流发送异常，异常信息为："+e.getMessage()+"fileKey={}"+fileKey);
        }finally {
            try{
                if(inputStreamReader!=null){
                    inputStreamReader.close();
                }

                if(inputStream!=null){
                    inputStream.close();
                }
            }catch (IOException e){
                Log.logError("[配置文件]  关闭文件流发送异常，异常信息为："+e.getMessage()+"fileKey={}"+fileKey);
            }
        }

        return properties;
    }

    public static String getProperty(String fileKey,String configKey){
        Properties config = propertiesMap.get(fileKey);
        if(config==null){
            config = loadConfig(fileKey);
        }

        return (config==null?null:config.getProperty(configKey));
    }

    public static String getProperty(String fileKey,String configKey,String defaultValue){
        String value = getProperty(fileKey,configKey);
        return (value==null?defaultValue:value);
    }
}
