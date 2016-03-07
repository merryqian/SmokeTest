package com.blemall.smokeTest.testcases;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.blemall.smokeTest.page.ToPayOrderPage;
import com.test.base.TestBase;
import com.test.bean.Config;
import com.test.bean.DeliveryFeeRule;
import com.test.util.Log;

public class TestCase_05_ChargeFee extends TestBase {

	// 生鲜收取运费
	@Test(dataProvider = "providerMethod")
	public void freshGoodsDeliveryFee(Map<String, String> param)
			throws IOException, InterruptedException {
		ToPayOrderPage tp = new ToPayOrderPage(driver);
		// 调用打开首页方法
		this.openURL();
		// 调用登录方法
		this.login(tp, param.get("username"), param.get("password"));
		// 调用打开购物车方法,并进行数据初始化
		this.initData(tp, param);
		// 调用进入结算页面方法
		this.gotoShoppingCartSP(tp);
		this.checkFreshGoodsDeliveryFee(tp, param);
	}

	// 生鲜不收取运费
	@Test(dataProvider = "providerMethod")
	public void freshGoodsNoDeliveryFee(Map<String, String> param)
			throws IOException, InterruptedException {
		ToPayOrderPage tp = new ToPayOrderPage(driver);
		// 调用打开首页方法
		this.openURL();
		// 调用登录方法
		this.login(tp, param.get("username"), param.get("password"));
		// 调用打开购物车方法,并进行数据初始化
		this.initData(tp, param);
		// 调用进入结算页面方法
		this.gotoShoppingCartSP(tp);
		this.checkFreshGoodsNoDeliveryFee(tp, param);
	}

	// 小于48元重量小于等于5kg常温商品-自营
	@Test(dataProvider = "providerMethod")
	public void normalGoodsScenario1(Map<String, String> param)
			throws IOException, InterruptedException {
		ToPayOrderPage tp = new ToPayOrderPage(driver);
		// 调用打开首页方法
		this.openURL();
		// 调用登录方法
		this.login(tp, param.get("username"), param.get("password"));
		// 调用打开购物车方法,并进行数据初始化
		this.initData(tp, param);
		// 调用进入结算页面方法
		this.gotoShoppingCartSP(tp);
		this.checkFee(tp, param);
	}

	// 小于48元重量大于5kg常温商品-自营
	@Test(dataProvider = "providerMethod")
	public void normalGoodsScenario2(Map<String, String> param)
			throws IOException, InterruptedException {
		ToPayOrderPage tp = new ToPayOrderPage(driver);
		// 调用打开首页方法
		this.openURL();
		// 调用登录方法
		this.login(tp, param.get("username"), param.get("password"));
		// 调用打开购物车方法,并进行数据初始化
		this.initData(tp, param);
		// 调用进入结算页面方法
		this.gotoShoppingCartSP(tp);
		this.checkFee(tp, param);
	}

	// 大于等于48元重量小于5kg常温商品
	@Test(dataProvider = "providerMethod")
	public void normalGoodsScenario3(Map<String, String> param)
			throws IOException, InterruptedException {
		ToPayOrderPage tp = new ToPayOrderPage(driver);
		// 调用打开首页方法
		this.openURL();
		// 调用登录方法
		this.login(tp, param.get("username"), param.get("password"));
		// 调用打开购物车方法,并进行数据初始化
		this.initData(tp, param);
		// 调用进入结算页面方法
		this.gotoShoppingCartSP(tp);
		this.checkFee(tp, param);
	}

	// 大于等于48元重量大于5kg常温商品
	@Test(dataProvider = "providerMethod")
	public void normalGoodsScenario4(Map<String, String> param)
			throws IOException, InterruptedException {
		ToPayOrderPage tp = new ToPayOrderPage(driver);
		// 调用打开首页方法
		this.openURL();
		// 调用登录方法
		this.login(tp, param.get("username"), param.get("password"));
		// 调用打开购物车方法,并进行数据初始化
		this.initData(tp, param);
		// 调用进入结算页面方法
		this.gotoShoppingCartSP(tp);
		this.checkFee(tp, param);
	}

	// 大于48重量大于5KG的常温商品-自营
	@Test(dataProvider = "providerMethod")
	public void normalGoodsScenario5(Map<String, String> param)
			throws IOException, InterruptedException {
		ToPayOrderPage tp = new ToPayOrderPage(driver);
		// 调用打开首页方法
		this.openURL();
		// 调用登录方法
		this.login(tp, param.get("username"), param.get("password"));
		// 调用打开购物车方法,并进行数据初始化
		this.initData(tp, param);
		// 调用进入结算页面方法
		this.gotoShoppingCartSP(tp);
		this.checkFee(tp, param);
	}

	// 打开首页
	public void openURL() {
		try {
			this.goTo(Config.path);
		} catch (Exception e) {
			Log.logInfo("[购物车]  打开页面失败");
			Assert.fail("[购物车]  打开页面失败");
		}
	}

	public void login(ToPayOrderPage tp, String username, String password) {
		// 定位到首页的登录按钮位置
		WebElement scrollView = tp.getElement("location");
		this.getIntoView(scrollView);
		Log.logInfo("定位到首页的登录按钮位置");
		WebElement loginEntrance = tp.getElementNoWait("loginEntrance");
		loginEntrance.click();
		Log.logInfo("点击首页的登录按钮入口");
		tp.getElement("userName").sendKeys(username);
		Log.logInfo("用户名=" + username);
		tp.getElement("password").sendKeys(password);
		Log.logInfo("密码=" + password);
		tp.getElementNoWait("loginButton").click();
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

	private void initData(ToPayOrderPage tp, Map<String, String> param)
			throws InterruptedException {
		// 点击首页的购物车按钮
		tp.getElement("shoppingCart").click();
		Log.logInfo("点击首页的购物车按钮");
		if (!driver.getPageSource().contains("去结算")) {
			Log.logInfo("购物车无商品");
			driver.get(param.get("link") + param.get("productID"));
			tp.getElement("addButton").click();
			tp.getElement("addButton").click();
			String success = tp.getElement("AddSuccess").getText();
			if (success.equals("加入购物车成功")) {
				Log.logInfo("成功提示信息： " + success);
				tp.getElement("toShoppingCart").click();

			} else {
				Log.logInfo("失败提示信息： " + success);
				Assert.fail("单品页商品加入购物车失败");
			}
		} else {
			// 清空购物车
			Log.logInfo("购物车有商品");
			Log.logInfo("清空购物车");
			tp.getElement("editAll").click();
			tp.getElement("deleteBtn").click();
			tp.getElement("Completed").click();
			driver.get(param.get("link") + param.get("productID"));
			tp.getElement("addButton").click();
			tp.getElement("addButton").click();
			String success = tp.getElement("AddSuccess").getText();
			if (success.equals("加入购物车成功")) {
				Log.logInfo("成功提示信息： " + success);
				tp.getElement("toShoppingCart").click();

			} else {
				Log.logInfo("失败提示信息： " + success);
				Assert.fail("单品页商品加入购物车失败");
			}
		}

	}

	public int gotoShoppingCartSP(ToPayOrderPage tp) {

		// 进入购物车后，点击去结算按钮
		String numString = tp.getElement("goodsNumInShoppingCart").getText();
		Log.logInfo("该商品在购物车中的数量= " + numString.substring(2, 3));
		tp.getElement("submitBtn").click();
		Log.logInfo("进入购物车后，点击去结算按钮");
		return Integer.parseInt(numString.substring(2, 3));
	}

	// 检查生鲜运费
	private void checkFreshGoodsDeliveryFee(ToPayOrderPage tp,
			Map<String, String> param) {
		Double totalAmount = Double.parseDouble(param.get("Level1Amount"));
		Double expectedFee = Double.parseDouble(param.get("deliveryFee"));
		String totalMoneyDesc = tp.getElement("totalOrderAmount").getText()
				.substring(1);
		double totalMoney = Double.parseDouble(totalMoneyDesc);
		String deliveryFeeDesc = tp.getElement("totalDeliveryCost").getText()
				.substring(1);
		double deliveryFee = Double.parseDouble(deliveryFeeDesc);
		Log.logInfo("商品总额= " + totalMoney);
		Log.logInfo("运费= " + deliveryFee);
		if (totalMoney < totalAmount) {
			if (deliveryFee == expectedFee) {
				Log.logInfo("生鲜商品运费校验 测试场景通过");
				Assert.assertTrue(true);
			} else {
				Log.logInfo("生鲜商品运费校验 测试场景不通过");
				Assert.fail("生鲜商品运费校验 测试场景不通过");
			}
		}

	}

	// 检查生鲜运费
	private void checkFreshGoodsNoDeliveryFee(ToPayOrderPage tp,
			Map<String, String> param) {
		Double expectedFee = Double.parseDouble(param.get("deliveryFee"));
		String totalMoneyDesc = tp.getElement("totalOrderAmount").getText()
				.substring(1);
		double totalMoney = Double.parseDouble(totalMoneyDesc);
		String deliveryFeeDesc = tp.getElement("totalDeliveryCost").getText()
				.substring(1);
		double deliveryFee = Double.parseDouble(deliveryFeeDesc);
		Log.logInfo("商品总额= " + totalMoney);
		Log.logInfo("运费= " + deliveryFee);
			if (deliveryFee == expectedFee) {
				Log.logInfo("生鲜商品运费校验 测试场景通过");
				Assert.assertTrue(true);
			} else {
				Log.logInfo("生鲜商品运费校验 测试场景不通过");
				Assert.fail("生鲜商品运费校验 测试场景不通过");
			}
	}

	// 检查生鲜运费
	private void checkFreshGoodsForLv3DeliveryFee(ToPayOrderPage tp,
			Map<String, String> param) {
		Double totalAmount = Double.parseDouble(param.get("Level3Amount"));
		Double expectedFee = Double.parseDouble(param.get("deliveryFee"));

		String totalMoneyDesc = tp.getElement("totalOrderAmount").getText()
				.substring(1);
		double totalMoney = Double.parseDouble(totalMoneyDesc);
		String deliveryFeeDesc = tp.getElement("totalDeliveryCost").getText()
				.substring(1);
		double deliveryFee = Double.parseDouble(deliveryFeeDesc);
		Log.logInfo("商品总额= " + totalMoney);
		Log.logInfo("运费= " + deliveryFee);
		if (totalMoney > totalAmount) {
			if (deliveryFee == expectedFee) {
				Log.logInfo("大于128元生鲜商品运费校验测试场景通过");
				Assert.assertTrue(true);
			} else {
				Log.logInfo("大于128元生鲜商品运费校验 测试场景不通过");
				Assert.fail("大于128元生鲜商品运费校验 测试场景不通过");
			}
		}
	}

	// 检查运费
	private void checkFee(ToPayOrderPage tp, Map<String, String> param)
			throws InterruptedException {
		DeliveryFeeRule rule = new DeliveryFeeRule();
		// 获取商品重量
		double getTotalWeight = Double.parseDouble(param.get("deliveryWeight"));
		// 获取订单金额
		String totalMoneyDesc = tp.getElement("totalOrderAmount").getText()
				.substring(1);
		double totalMoney = Double.parseDouble(totalMoneyDesc);
		// 获取测试场景名称
		String testCase = param.get("scenario");
		this.waitimplicitly(3);
		// 获取实际运费金额
		String deliveryFeeDesc = tp.getElement("totalDeliveryCost").getText()
				.substring(1);
		double deliveryFeeActual = Double.parseDouble(deliveryFeeDesc);
		// 调用运费方法，算出预期运费金额
		double getExpectedFee = rule.getDeliveryFee(getTotalWeight, totalMoney);
		Log.logInfo("商品总额= " + totalMoney);
		Log.logInfo("商品总重量= " + getTotalWeight);
		Log.logInfo("配送方式=物流配送");
		Log.logInfo("实际显示的运费= " + deliveryFeeActual);
		if (deliveryFeeActual == getExpectedFee) {

			Log.logInfo(testCase + " 测试场景通过");
			Assert.assertTrue(true);
		} else {
			Log.logInfo(testCase + " 测试场景不通过");
			Assert.fail(testCase + " 测试场景不通过");
		}

	}
}
