package com.test.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.test.bean.Config;
public class SeleniumDriver {
	WebDriver driver;
	public WebDriver selectBrowser()
	{
		if("firefox".equals(Config.browser)){
			driver=new FirefoxDriver();
			return driver;
		}
		else if("ie".equals(Config.browser))
		{
			System.setProperty("webdriver.ie.driver", "file/IEDriverServer.exe");
			DesiredCapabilities capabilities=DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability("ignoreProtectedModeSettings", true);
			driver=new InternetExplorerDriver(capabilities);
			return driver;
		}
		else if("chrome".equals(Config.browser))
		{
			System.setProperty("webdriver.chrome.driver", "file/chromedriver.exe");
		    ChromeOptions options=new ChromeOptions();
			options.addArguments("--test type","--ignore-certificate-errors");
			options.addArguments("--test type","--start-maximized");
			driver=new ChromeDriver(options);
			return driver;
		}
		else{
			System.out.println(Config.browser);
			return null;
		}
	}

}
