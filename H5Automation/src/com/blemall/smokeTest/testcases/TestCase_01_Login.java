package com.blemall.smokeTest.testcases;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.blemall.smokeTest.page.LoginPage;
import com.blemall.smokeTest.page.ShoppingCartPage;
import com.test.base.TestBase;
import com.test.bean.Config;
import com.test.bean.TestExpect;
import com.test.bean.TestngListener;
import com.test.util.ExcelReader;
import com.test.util.Log;
import com.test.util.ScreenShot;
import com.test.util.TimeString;

//@Listeners(TestngListener.class)
public class TestCase_01_Login extends TestBase {
	TimeString ts = new TimeString();
	String timeString = ts.getDate();
	ScreenShot screen = new ScreenShot(driver);

	// 登录名不存在-错误提示
	@Test(dataProvider = "providerMethod")
	public void usernameNotExist(Map<String, String> param) throws IOException,
			InterruptedException {
		LoginPage lp = new LoginPage(driver);
		this.openURL();
		// 进入登录页面
		this.gotoLoginPage(lp);
		// 检查登录名不存在
		this.usernameNotExistCheck(lp, param);
	}

	// 登录页面登录 - 用户名和密码正确
	@Test(dataProvider = "providerMethod")
	public void loginSuccess(Map<String, String> param) throws IOException,
			InterruptedException {
		LoginPage lp = new LoginPage(driver);
		this.openURL();
		// 进入登录页面
		this.gotoLoginPage(lp);
		// 检查登录成功
		this.loginSuccessCheck(lp, param);

	}

	// 登录页面登录 - 密码错误提示
	@Test(dataProvider = "providerMethod")
	public void wrongPasswordAlert(Map<String, String> param)
			throws IOException, InterruptedException {
		LoginPage lp = new LoginPage(driver);
		this.openURL();
		// 进入登录页面
		this.gotoLoginPage(lp);
		// 检查密码错误提示
		this.wrongPasswordAlertCheck(lp, param);
	}

	// 打开H5首页
	public void openURL() {
		try {
			this.goTo(Config.path);
			Log.logInfo("打开首页连接=" + Config.path);
			//wait(3000);
			String log = ".//*[@id='main']/div/header/a[3]/img";
			if (isElementDisplayed(log)) {
				Log.logInfo("首页打开成功");
			} else {
				Log.logInfo("首页打开失败");
				return;
			}
		} catch (Exception e) {
			Log.logInfo("[首页]  打开页面失败");
			Assert.fail("[首页]  打开页面失败");
		}
	}

	// 判断某个元素是否出现
	@SuppressWarnings("unused")
	private boolean isElementDisplayed(String path) {
		boolean result = false;
		try {
			result = driver.findElement(By.xpath(path)).isDisplayed();
			Log.logInfo("你要找的元素出现了");
		} catch (Exception e) {
			Log.logInfo("你要找的元素未出现");
			Assert.fail();
		}

		return result;
	}

	// 判断某个元素是否存在
	@SuppressWarnings("unused")
	private boolean isElementExists(String path) {
		boolean result = false;
		try {
			driver.findElement(By.xpath(path));
			result = true;
		} catch (Exception e) {
			Log.logInfo("元素不存在,path=" + path);
		}

		return result;
	}

	// 登录
	public void login(LoginPage lp, String username, String password) {
		// 定位到首页的登录按钮位置
		WebElement scrollView = lp.getElement("location");
		this.getIntoView(scrollView);
		Log.logInfo("定位到首页的登录按钮位置");
		WebElement loginEntrance = lp.getElementNoWait("loginEntrance");
		loginEntrance.click();
		Log.logInfo("点击首页的登录按钮入口");
		lp.getElement("userName").sendKeys(username);
		Log.logInfo("用户名=" + username);
		lp.getElement("password").sendKeys(password);
		Log.logInfo("密码=" + password);
		lp.getElementNoWait("loginButton").click();
		Log.logInfo("点击登录按钮");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		try {
			driver.findElement(By.linkText("登录"));
			Log.logInfo("登录失败");
			Assert.fail("登录失败");
		} catch (Exception e) {
			Log.logInfo("登录成功后跳转回首页");
			Assert.assertTrue(true);
		}
	}

	private void gotoLoginPage(LoginPage lp) {
		// 因为首页的登录按钮首屏未显示，故使用JS方法先定位登录的按钮
		// 选择的定位点为登录上面的一个资源位
		WebElement scrollView = lp.getElement("location");
		this.getIntoView(scrollView);
		WebElement loginEntrance = lp.getElementNoWait("loginEntrance");
		loginEntrance.click();
		Log.logInfo("点击首页的登录按钮");
	}

	// 密码错误提示
	private void wrongPasswordAlertCheck(LoginPage lp, Map<String, String> param) {
		lp.getElement("userName").sendKeys(param.get("username"));
		lp.getElement("password").sendKeys(param.get("password"));
		lp.getElement("loginButton").click();
		String expectedAlert = param.get("expectedAlert");
		WebElement alert = lp.getElement("wrongAlert");
		Log.logInfo("预期提示=" + expectedAlert);
		Log.logInfo("实际提示=" + alert.getText());
		if (alert.getText().contains(expectedAlert)) {
			Log.logInfo("密码错误提示= " + alert.getText());
			Log.logInfo("登录页面登录 - 密码错误提示 测试场景通过");
			Assert.assertTrue(true);
		} else {
			Log.logInfo("登录页面登录 - 密码错误提示 测试场景不通过");
			Assert.fail("登录页面登录 - 密码错误提示");
		}

	}

	// 用户名不存在
	private void usernameNotExistCheck(LoginPage lp, Map<String, String> param) {
		lp.getElement("userName").sendKeys(param.get("username"));
		lp.getElement("password").sendKeys(param.get("password"));
		lp.getElement("loginButton").click();
		String expectedAlert = param.get("expectedAlert");
		WebElement alert = lp.getElement("wrongAlert");
		Log.logInfo("用户名不存在预期提示信息= " + expectedAlert);
		Log.logInfo("用户名不存在实际提示信息= " + alert.getText());
		if (alert.isDisplayed()) {

			if (alert.getText().contains(expectedAlert)) {
				;
				Log.logInfo("\"登录名不存在-错误提示\" 测试场景通过");
			} else {
				Log.logInfo("\"登录名不存在-错误提示\"测试场景不通过");
				Assert.fail("登录页面登录 - 登录名不存在提示");
			}
			Assert.assertTrue(true);
		} else {
			Log.logInfo("\"登录名不存在-错误提示\"测试场景不通过");

			Assert.fail("登录页面登录 - 登录名不存在");
		}
	}

	// 登录成功
	private void loginSuccessCheck(LoginPage lp, Map<String, String> param)
			throws InterruptedException {
		lp.getElement("userName").sendKeys(param.get("username"));
		lp.getElement("password").sendKeys(param.get("password"));

		lp.getElementNoWait("loginButton").click();
		this.waitimplicitly(3);
		try {
			driver.findElement(By.linkText("登录"));
			Log.logInfo("登录失败");
			Assert.fail("登录失败");
		} catch (Exception e) {
			Log.logInfo("登录成功");
			Assert.assertTrue(true);
		}
	}

}
