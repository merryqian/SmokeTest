package com.blemall.smokeTest.testcases;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.blemall.smokeTest.page.SinglePage;
import com.test.base.TestBase;
import com.test.util.Assertion;
import com.test.util.ExcelReader;
import com.test.util.Log;
import com.test.util.ScreenShot;
import com.test.util.TimeString;

public class TestCase_SinglePage extends TestBase {
	String excelPath = "D:\\code\\H5\\TestCase\\H5_SingleProduct.xlsx";
	String sheetName = "SingleProduct";
	ExcelReader reader = new ExcelReader(excelPath, sheetName);
	TimeString ts = new TimeString();
	String timeString = ts.getDate() + "-" + ts.getTime();
	ScreenShot screen = new ScreenShot(driver);
	Map<String, String> testcaseStatus = new HashMap<String, String>();

	// 单品页加载
	@Test(dataProvider = "providerMethod")
	public void singlePageLoad(Map<String, String> param) throws IOException {
		try {
			this.goTo(param.get("url") + param.get("productID"));
			Log.logInfo("打开连接=" + param.get("url") + param.get("productID"));

		} catch (Exception e) {
			Log.logInfo("打开失败");
			Assert.fail("页面打开失败");
		}
		SinglePage sp = new SinglePage(driver);
		try {
			if (sp.getElementNoWait("H5Load").isDisplayed()) {
				Log.logError("页面加载失败：" + sp.getElement("H5Load").getText());
				screen.snapshot((TakesScreenshot) driver, timeString, "Single",
						"单品页加载");
				Assert.fail("页面加载失败");
			}

		} catch (Exception e) {
			Log.logInfo("H5页面加载成功");
		}

		if (sp.getElement("SinglePageLoad").isDisplayed()) {
			Log.logInfo("单品页面加载成功");
			screen.snapshot((TakesScreenshot) driver, timeString, "Single",
					"单品页加载");
			testcaseStatus.put("普通商品单品页页面加载情况logo检查", "Pass");
		} else {
			Log.logError("加载失败了，请查看截图查找原因");
			screen.snapshot((TakesScreenshot) driver, timeString, "Single",
					"单品页加载");
		}

	}

	// 商品名称检查
	@Test(dataProvider = "providerMethod")
	public void goodName(Map<String, String> param) throws IOException {
		try {
			this.goTo(param.get("url") + param.get("productID"));
			Log.logInfo("打开连接=" + param.get("url") + param.get("productID"));
		} catch (Exception e) {
			Log.logInfo("打开失败");
			Assert.fail("页面打开失败");
		}
		SinglePage sp = new SinglePage(driver);
		WebElement el = sp.getElementNoWait("H5Load");
		try {
			if (el.isDisplayed()) {
				Log.logError("页面加载失败：" + sp.getElement("H5Load").getText());
				screen.snapshot((TakesScreenshot) driver, timeString, "Single",
						"单品页加载");
				Assert.fail("页面加载失败");

			}
		} catch (Exception e) {
			Log.logInfo("H5页面加载成功");
		}

		if (sp.getElement("SinglePageLoad").isDisplayed()) {
			WebElement scrollView = sp.getElementNoWait("ScrollView");
			this.getIntoView(scrollView);
			String goodName = sp.getElementNoWait("GoodName").getText()
					.substring(3);
			Log.logInfo("实际获取的商品名称= " + goodName);
			String expected = reader.getCellData(3, 5);
			Log.logInfo("预期获取的商品名称= " + expected);
			Assertion.verifyEquals(goodName, expected, "判断打开的单品页是否是预期的商品");
			screen.snapshot((TakesScreenshot) driver, timeString, "Single",
					"商品名称检查");
		} else {
			Log.logError("加载失败了，请查看截图查找原因");
			screen.snapshot((TakesScreenshot) driver, timeString, "Single",
					"单品页加载");
		}

	}

	// 商品价格检查
	@Test(dataProvider = "providerMethod")
	public void goodPrice(Map<String, String> param) throws IOException {
		try {
			this.goTo(param.get("url") + param.get("productID"));
			Log.logInfo("打开连接=" + param.get("url") + param.get("productID"));
		} catch (Exception e) {
			Log.logInfo("打开失败");
			Assert.fail("页面打开失败");
		}
		SinglePage sp = new SinglePage(driver);
		WebElement el = sp.getElementNoWait("H5Load");
		try {
			if (el.isDisplayed()) {
				Log.logError("页面加载失败：" + sp.getElement("H5Load").getText());
				screen.snapshot((TakesScreenshot) driver, timeString, "Single",
						"单品页加载");
				Assert.fail("页面加载失败");

			}
		} catch (Exception e) {
			Log.logInfo("H5页面加载成功");
		}

		if (sp.getElement("SinglePageLoad").isDisplayed()) {
			WebElement scrollView = sp.getElementNoWait("ScrollView");
			this.getIntoView(scrollView);
			String price = sp.getElementNoWait("GoodPrice").getText();
			Log.logInfo("实际的商品价格= " + price);
			String expected = reader.getCellData(5, 5);
			Log.logInfo("预期的商品价格= " + expected);
			Assertion.verifyEquals(price, expected, "比较单品页商品价格是否相等");
			screen.snapshot((TakesScreenshot) driver, timeString, "Single",
					"单品页商品价格检查");
		} else {
			Log.logError("加载失败了，请查看截图查找原因");
			screen.snapshot((TakesScreenshot) driver, timeString, "Single",
					"单品页加载");
		}

	}

	// 单品页商品无评价
	@Test(dataProvider = "providerMethod")
	public void goodNoEvaluate(Map<String, String> param) throws IOException {
		try {
			this.goTo(param.get("url") + param.get("productID"));
			Log.logInfo("打开连接=" + param.get("url") + param.get("productID"));
		} catch (Exception e) {
			Log.logInfo("打开失败");
			Assert.fail("页面打开失败");
		}
		SinglePage sp = new SinglePage(driver);
		WebElement el = sp.getElementNoWait("H5Load");
		try {
			if (el.isDisplayed()) {
				Log.logError("页面加载失败：" + sp.getElement("H5Load").getText());
				screen.snapshot((TakesScreenshot) driver, timeString, "Single",
						"单品页加载");
				Assert.fail("页面加载失败");

			}
		} catch (Exception e) {
			Log.logInfo("H5页面加载成功");
		}
		if (sp.getElement("SinglePageLoad").isDisplayed()) {
			WebElement scrollView = sp.getElementNoWait("ScrollView");
			this.getIntoView(scrollView);
			String noEvalute = sp.getElementNoWait("GoodNoEvalute").getText();
			Log.logInfo("无评价的商品实际显示文案= " + noEvalute);
			String expected = reader.getCellData(6, 5);
			Log.logInfo("预期显示文案= " + expected);
			Assertion.verifyEquals(noEvalute, expected);
			new ScreenShot(driver).snapshot((TakesScreenshot) driver,
					timeString, "Single", "单品页商品无评价检查");
		} else {
			Log.logError("加载失败了，请查看截图查找原因");
			screen.snapshot((TakesScreenshot) driver, timeString, "Single",
					"单品页加载");
		}

	}

	// 单品页商品有评价记录
	@Test(dataProvider = "providerMethod")
	public void goodEvaluate(Map<String, String> param) throws IOException {
		try {
			this.goTo(param.get("url") + param.get("productID"));
			Log.logInfo("打开连接=" + param.get("url") + param.get("productID"));
		} catch (Exception e) {
			Log.logInfo("打开失败");
			Assert.fail("页面打开失败");
		}
		SinglePage sp = new SinglePage(driver);
		WebElement el = sp.getElementNoWait("H5Load");
		try {

		} catch (Exception e) {
			Log.logInfo("H5页面加载成功");
		}
		if (el.isDisplayed()) {
			Log.logError("页面加载失败：" + sp.getElement("H5Load").getText());
			screen.snapshot((TakesScreenshot) driver, timeString, "Single",
					"单品页加载");
			Assert.fail("页面加载失败");

		} else if (sp.getElement("SinglePageLoad").isDisplayed()) {
			WebElement scrollView = sp.getElementNoWait("ScrollView");
			this.getIntoView(scrollView);
			Log.logInfo("该商品存在评价记录：" + sp.getElement("GoodEvaluate").getText());
			screen.snapshot((TakesScreenshot) driver, timeString, "Single",
					"单品页商品有评价记录");
		} else {
			Log.logError("加载失败了，请查看截图查找原因");
			screen.snapshot((TakesScreenshot) driver, timeString, "Single",
					"单品页加载");
		}

	}

	// 促销活动展示
	@Test(dataProvider = "providerMethod")
	public void goodPromotion(Map<String, String> param) throws IOException {
		try {
			this.goTo(param.get("url") + param.get("productID"));
			Log.logInfo("打开连接=" + param.get("url") + param.get("productID"));
		} catch (Exception e) {
			Log.logInfo("打开失败");
			Assert.fail("页面打开失败");
		}
		SinglePage sp = new SinglePage(driver);
		WebElement el = sp.getElementNoWait("H5Load");
		try {
			if (el.isDisplayed()) {
				Log.logError("页面加载失败：" + sp.getElement("H5Load").getText());
				screen.snapshot((TakesScreenshot) driver, timeString, "Single",
						"单品页加载");
				Assert.fail("页面加载失败");

			}
		} catch (Exception e) {
			Log.logInfo("H5页面加载成功");
		}
		if (sp.getElement("SinglePageLoad").isDisplayed()) {
			WebElement scrollView = sp.getElementNoWait("ScrollView");
			this.getIntoView(scrollView);
			Log.logInfo("该商品存在促销活动：" + sp.getElement("GoodPromotion").getText());
			Log.logInfo("促销活动=" + sp.getElement("PromotionType").getText());
			sp.getElement("PromotionClick").click();
			Log.logInfo("活动名称= " + sp.getElement("PromotionName").getText());
			screen.snapshot((TakesScreenshot) driver, timeString, "Single",
					"单品页商品促销活动展示");
		} else {
			Log.logError("加载失败了，请查看截图查找原因");
			screen.snapshot((TakesScreenshot) driver, timeString, "Single",
					"单品页加载");
		}

	}

	// 普通商品单品页是否存在“加入购物车”按钮
	// @Test(dataProvider = "providerMethod")
	public void addToCartButton(Map<String, String> param) throws IOException {
		try {
			this.goTo(param.get("url") + param.get("productID"));

		} catch (Exception e) {
			Log.logInfo("打开失败");
			Assert.fail("页面打开失败");
		}
		Log.logInfo("url= " + param.get("url") + param.get("productID"));
		SinglePage sp = new SinglePage(driver);
		try {

		} catch (Exception e) {
			Log.logInfo("H5页面加载成功");
		}
		if (sp.getElement("H5Load").isDisplayed()) {
			Log.logError("页面加载失败：" + sp.getElement("H5Load").getText());
			Assert.fail("页面加载失败");
		} else {
			Log.logInfo("页面加载成功");
		}
	}

	// 查看售后服务选择检查
	// @Test(dataProvider = "providerMethod")
	public void customerService(Map<String, String> param) throws IOException {
		this.goTo(param.get("url") + param.get("productID"));
		Log.logInfo("url= " + param.get("url") + param.get("productID"));
		SinglePage sp = new SinglePage(driver);
	}

	// 单品页供货方显示检查-自营
	// @Test(dataProvider = "providerMethod")
	public void supplierCheck(Map<String, String> param) throws IOException {
		this.goTo(param.get("url") + param.get("productID"));
		Log.logInfo("url= " + param.get("url") + param.get("productID"));
		SinglePage sp = new SinglePage(driver);
	}

}
