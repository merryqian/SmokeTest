package com.test.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ho.yaml.Yaml;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.test.bean.Config;
import com.test.util.Log;

public class Locator {
	
	private String yamlFile;
	
	protected WebDriver driver;
	
    private Map<String, Map<String, String>> extendLocator;    
	 
    public void setYamlFile(String yamlFile) {
		this.yamlFile = yamlFile;
	}

	public Locator(WebDriver driver) {        
        this.driver = driver;
    }
 
    private Map<String, Map<String, String>> ml;   
 
   
    @SuppressWarnings("unchecked")
	protected void getYamlFile() throws IOException {
        File f = new File("locator/" + yamlFile + ".yaml");
        try {
            ml = Yaml.loadType(new FileInputStream(f.getAbsolutePath()),
                    HashMap.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    } 
    
    private By getBy(String type, String value) {
        By by = null;
        if (type.equals("id")) {
            by = By.id(value);
        }
        if (type.equals("name")) {
            by = By.name(value);
        }
        if (type.equals("xpath")) {
            by = By.xpath(value);
        }
        if (type.equals("className")) {
            by = By.className(value);
        }
        if (type.equals("linkText")) {
            by = By.linkText(value);
        }
        if(type.equals("css")){
        	by = By.cssSelector(value);
        }
        return by;
    }
    
    private WebElement watiForElement(final By by) {
        WebElement element = null;
        int waitTime = Config.waitTime;
        try {
            element = new WebDriverWait(driver, waitTime).until(new ExpectedCondition<WebElement>() {
                public WebElement apply(WebDriver d) {
                    return d.findElement(by);
                }
            });
        } catch (Exception e) {
            Log.logInfo(by.toString() + " is not exist until " + waitTime);
        }
        return element;
    }   
    private List<WebElement> waitForElements(final By by) {
		List<WebElement> elements = null;
		int waitTime = Config.waitTime;
		try {
			elements = new WebDriverWait(driver, waitTime)
					.until(new ExpectedCondition<List<WebElement>>() {
						public List<WebElement> apply(WebDriver d) {
							return d.findElements(by);
						}
					});
		} catch (Exception e) {
			// TODO: handle exception
		}
		return elements;
	}
    private boolean waitElementToBeDisplayed(final WebElement element) {
        boolean wait = false;
        if (element == null)
            return wait;
        try {        	
            wait = new WebDriverWait(driver, Config.waitTime).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    return element.isDisplayed();
                }
            });
        } catch (Exception e) {        	
            Log.logInfo(element.toString() + " is not displayed");
        }
        return wait;
    }
    
    public boolean waitElementToBeNonDisplayed(final WebElement element) {
        boolean wait = false;
        if (element == null)
            return wait;
        try {
            wait = new WebDriverWait(driver,Config.waitTime).until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver d) {
                            return !element.isDisplayed();
                        }
                    });
        } catch (Exception e) {
            Log.logInfo("Locator [" + element.toString() + "] is also displayed");
        }
        return wait;
    }
    
    private String getLocatorString(String locatorString, String[] ss) {
        for (String s : ss) {
            locatorString = locatorString.replaceFirst("%s", s);
        }
        return locatorString;
    }
    
    private WebElement getLocator(String key, String[] replace, boolean wait) {
        WebElement element = null;
        if (ml.containsKey(key)) {
            Map<String, String> m = ml.get(key);
            String type = m.get("type");
            String value = m.get("value");
            if (replace != null)
                value = this.getLocatorString(value, replace);
            By by = this.getBy(type, value);
            if (wait) {
                element = this.watiForElement(by);
                boolean flag = this.waitElementToBeDisplayed(element);                 
                if (!flag)
                    element = null;
            } else {
                try {
                    element = driver.findElement(by);
                } catch (Exception e) {
                    element = null;
                }
            }
        } else
            Log.logInfo("Locator " + key + " is not exist in " + yamlFile
                    + ".yaml");
        return element;
    }
    
    public WebElement getElement(String key) {
        return this.getLocator(key, null, true);
    }
     
    public WebElement getElementNoWait(String key) {
        return this.getLocator(key, null, false);
    }
     
    public WebElement getElement(String key, String[] replace) {
        return this.getLocator(key, replace, true);
    }
    public List<WebElement> getElements(String key) {
		return this.getLocators(key, null, true);
	}
    private List<WebElement> getLocators(String key, String[] replace, boolean wait) {
		List<WebElement> elements = null;

		if (ml.containsKey(key)) {
			HashMap<String, String> map = (HashMap<String, String>) ml.get(key);
			String type = map.get("type");
			String value = map.get("value");
			if (replace != null) {
				value = this.getLocatorString(value, replace);
			}
			By by = this.getBy(type, value);
			if (wait) {
				elements = this.waitForElements(by);
				boolean flag=false;
				if(elements!=null && elements.size()>0)
				{
					flag = this.waitElementToBeDisplayed(elements.get(0));
				}
				if (!flag)
					elements = null;
			} else {
				try {
					elements = driver.findElements(by);
				} catch (Exception e) {
					// TODO: handle exception
					elements = null;
				}

			}
		} else {
			System.out.println("locator " + key + " is not exist in "
					+ yamlFile + ".yaml");
		}
		return elements;
	}
    public WebElement getElementNoWait(String key, String[] replace) {
        return this.getLocator(key, replace, false);
    }
    
    public void setLocatorVariableValue(String variable, String value){
    	if(ml!=null){
    		Set<String> keys = ml.keySet();
            for(String key:keys){
                 String v = ml.get(key).get("value").replaceAll("%"+variable+"%", value);
                 ml.get(key).put("value",v);
            }
    	}        
    }    
    
    @SuppressWarnings("unchecked")
    public void loadExtendLocator(String fileName){
        File f = new File("locator/" + fileName + ".yaml");
        try {
            extendLocator = Yaml.loadType(new FileInputStream(f.getAbsolutePath()),
                    HashMap.class);
            ml.putAll(extendLocator);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }    
    
    
    public static void main(String[] args) throws IOException{
    	SeleniumDriver selenium = new SeleniumDriver();
    	Locator d = new Locator(selenium.selectBrowser());
    	d.setYamlFile("LoginPage");
    	d.getYamlFile();
        d.getElement("");
    }
	
}
