package com.test.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShot {
    public WebDriver driver;
    TimeString ts=new TimeString();
    public ScreenShot(WebDriver driver) {
        this.driver = driver;
    }
 
    private void takeScreenshot(String screenPath) {
        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(screenPath));
        } catch (IOException e) {
            System.out.println("Screen shot error: " + screenPath);
        }
    }
 
    public void takeScreenshot() {
        String screenName = String.valueOf(ts.getDate("-") + ".jpg");
        File dir = new File("/TestScreenShot");
        if (!dir.exists())
            dir.mkdirs();
        String screenPath = dir.getAbsolutePath() + "/" + screenName;
        this.takeScreenshot(screenPath);       
    }
    public  void snapshot(TakesScreenshot drivername, String snapshotTime,String foldername,String filename) {
		// this method will take screen shot ,require two parameters ,one is
		// driver name, another is file name
       
		String currentPath = System.getProperty("user.dir");
		// get current workfolder
		File scrFile = drivername.getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy
		// somewhere
		try {
			System.out.println("save snapshot path is:" + currentPath + "/"
					+ filename + ".jpg");
			FileUtils.copyFile(scrFile, new File(currentPath +"\\"+ "screenshot"+"\\"+snapshotTime+"\\" +foldername+ "\\"+filename
					+ ".jpg"));
		} catch (IOException e) {
			System.out.println("Can't save screenshot");
			e.printStackTrace();
		} finally {
			System.out.println("screen shot finished, it's in " + currentPath
					+ " folder");
		}
	}
}