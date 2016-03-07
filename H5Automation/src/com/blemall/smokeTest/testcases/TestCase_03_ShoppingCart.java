package com.blemall.smokeTest.testcases;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.blemall.smokeTest.page.ShoppingCartPage;
import com.blemall.smokeTest.page.ToPayOrderPage;
import com.test.base.TestBase;
import com.test.bean.Config;
import com.test.util.Log;

public class TestCase_03_ShoppingCart extends TestBase {
	// 将商品从“搜索列表页”加入购物车
	@Test(dataProvider = "providerMethod")
	public void A1_addToCartFromSearch(Map<String, String> param)
			throws IOException {
		ShoppingCartPage sp = new ShoppingCartPage(driver);
		// 打开H5首页
		openURL();
		//登录
		this.login(sp, param.get("username"), param.get("password"));
		// 调用搜索方法,并判断返回的结果是否为空
		searchKey(param.get("searchKey"));
		// 查找搜索结果，直到找到有库存的商品
		String buttonXpath = findStockGoods();
		// 调用加入购物车按钮
		addToShoppingCartFromList(sp, buttonXpath);
	}

	// 将商品从详情页加入购物车
	@Test(dataProvider = "providerMethod")
	public void A2_addToShopCart(Map<String, String> param) {
		// 打开H5首页
		openURL();
		// 调用搜索方法
		ShoppingCartPage sp = searchKey(param.get("searchKey"));
		// 查找有加入购物车按钮的商品
		goToSinglePage(sp);
		// 调用加入购物车的方法
		addToShoppingCartFromSingle(sp);

	}


	// 购物车中编辑单个商品-增加商品数量
	@Test(dataProvider = "providerMethod")
	public void A3_oneGoodsEditAddNum(Map<String, String> param)
			throws IOException, InterruptedException {
		ShoppingCartPage sp = new ShoppingCartPage(driver);
		// 打开H5首页
		openURL();
		// 登录
		this.login(sp, param.get("username"), param.get("password"));
		// 进入购物车页面
		this.gotoShoppingCart(sp,param);
		this.goodsEditAddNum(sp);
	}

	// 购物车中编辑单个商品-减少商品数量
	@Test(dataProvider = "providerMethod")
	public void A4_oneGoodsEditDeleteNum(Map<String, String> param)
			throws IOException, InterruptedException {
		ShoppingCartPage sp = new ShoppingCartPage(driver);
		// 打开H5首页
		openURL();
		// 登录
		this.login(sp, param.get("username"), param.get("password"));
		// 进入购物车页面
		this.gotoShoppingCart(sp,param);
		this.goodsEditDeleteNum(sp);
	}

	// 购物车中增加商品库存校验功能
	@Test(dataProvider = "providerMethod")
	public void A5_addGoodNumCheckStock(Map<String, String> param)
			throws IOException, InterruptedException {
		ShoppingCartPage sp = new ShoppingCartPage(driver);
		// 打开H5首页
		openURL();
		// 登录
		this.login(sp, param.get("username"), param.get("password"));
		// 进入购物车页面
		this.gotoShoppingCart(sp,param);
		this.goodNumCheckStock(sp);
	}

	// 选择商品进入结算页面
	@Test(dataProvider = "providerMethod")
	public void A6_oneGoodToSubmit(Map<String, String> param) throws IOException,
			InterruptedException {
		ShoppingCartPage sp = new ShoppingCartPage(driver);
		// 打开H5首页
		openURL();
		// 登录
		this.login(sp, param.get("username"), param.get("password"));
		// 进入购物车页面
		this.gotoShoppingCart(sp,param);
		this.checkOneGoodToSubmit(sp);
	}

	// 购物车中编辑单个商品-删除商品
	@Test(dataProvider = "providerMethod")
	public void A7_oneGoodsEditDelete(Map<String, String> param)
			throws IOException, InterruptedException {
		ShoppingCartPage sp = new ShoppingCartPage(driver);
		// 打开H5首页
		openURL();
		// 登录
		this.login(sp, param.get("username"), param.get("password"));
		// 进入购物车页面
		this.gotoShoppingCart(sp,param);
		this.deleteOneGoods(sp);
	}

	// 判断某个元素是否存在
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

	// 判断某个元素是否存在
	private boolean isElementExists(String path, WebElement scrollPath) {
		boolean result = false;

		try {
			this.getIntoView(scrollPath);
			driver.findElement(By.xpath(path));
			result = true;
		} catch (Exception e) {
			Log.logInfo("元素不存在,path=" + path);
		}

		return result;
	}

	// 打开H5首页
	public void openURL() {
		try {
			this.goTo(Config.path);
		} catch (Exception e) {
			Log.logInfo("[购物车]  打开页面失败");
			Assert.fail("[购物车]  打开页面失败");
		}
	}

	// 登录
	public void login(ShoppingCartPage sp, String username, String password) {
		// 定位到首页的登录按钮位置
		WebElement scrollView = sp.getElement("location");
		this.getIntoView(scrollView);
		Log.logInfo("定位到首页的登录按钮位置");
		WebElement loginEntrance = sp.getElementNoWait("loginEntrance");
		loginEntrance.click();
		Log.logInfo("点击首页的登录按钮入口");
		sp.getElement("userName").sendKeys(username);
		Log.logInfo("用户名=" + username);
		sp.getElement("password").sendKeys(password);
		Log.logInfo("密码=" + password);
		sp.getElementNoWait("loginButton").click();
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

	// 查找商品
	private ShoppingCartPage searchKey(String key) {
		ShoppingCartPage sp = null;
		try {
			sp = new ShoppingCartPage(driver);
			sp.getElement("search").click();
			sp.getElement("keySearch").sendKeys(key);
			sp.getElement("keySearch").sendKeys(Keys.ENTER);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} catch (IOException e) {
			e.printStackTrace();
			Log.logError("[搜索] 搜索发生异常，异常信息为：" + e.getMessage() + " key=" + key);
		}
		// 判断搜索出来的结果是否为空
		String noGoodsPath = ".//*[@id='goodslist']/div[2]";
		if (isElementExists(noGoodsPath)) {
			Assert.fail("未搜索到任何商品");
		}
		return sp;
	}

	// 检查购物车中闪购商品的展示
	private void checkShanGouInShoppingCart(ShoppingCartPage sp) {
		String shanGouNum = "//i[contains(text(),'闪购')]";
		// 查找闪购商品，购物车中没有找到闪购的，则判断用例失败
		if (isElementExists(shanGouNum)) {
			int canNotBuyNum = sp.getElements("shanGouNum").size();
			Log.logInfo("闪购商品的数量=" + canNotBuyNum);
			Log.logInfo("购物车中有闪购活动的商品");
			Log.logInfo("购物车闪购商品标签展示 测试场景通过");
			Assert.assertTrue(true);

		} else {
			Log.logInfo("购物车闪购商品标签展示 测试场景不通过");
			Assert.fail("购物车没有闪购活动的商品，请重新检查");
		}
	}

	// 进入购物车方法-登录

	public int gotoShoppingCart(ShoppingCartPage sp,Map<String,String> param) {
		String numString = null;
		// 点击首页的购物车按钮
		// 进入购物车
		sp.getElement("shoppingCart").click();
		Log.logInfo("点击首页的购物车按钮");
		// 判断购物车是否为空
		if (this.checkIfShoppingCartNull(sp)) {

			numString = sp.getElement("goodsNumInShoppingCart").getText();
		} else {
			driver.get(param.get("link") + param.get("productID"));
			sp.getElement("addButton").click();
			sp.getElement("addButton").click();
			String success = sp.getElement("AddSuccessInSinglePage").getText();
			if (success.equals("加入购物车成功")) {
				Log.logInfo("成功提示信息： " + success);
				sp.getElement("toShoppingCart").click();

			} else {
				Log.logInfo("失败提示信息： " + success);
				Assert.fail("单品页商品加入购物车失败");
			}
		}
		return Integer.parseInt(numString.substring(2, 3));

	}

	// 进入购物车方法-未登录
	public void gotoShoppingCartNotLogin(ShoppingCartPage sp) {
		// 点击首页的购物车按钮
		// 进入购物车
		sp.getElement("shoppingCart").click();
		Log.logInfo("点击首页的购物车按钮");
	}

	private void checkShoppingCartNull(ShoppingCartPage sp) {
		String noGoodsText = sp.getElement("noGoodsInShoppingCart").getText();
		if (noGoodsText.contains("购物车已经饿瘪了")) {
			Log.logInfo(noGoodsText);
			Log.logInfo("购物车无商品样式展示  测试场景通过");
			Assert.assertTrue(true);
		} else {
			Log.logInfo("购物车无商品样式展示  测试场景不通过");
			Assert.fail("购物车仍存在商品或者其他原因");
		}
	}

	private boolean checkIfShoppingCartNull(ShoppingCartPage sp) {
		boolean result = false;
		String noGoods = ".//*[@id='submitBtn']";
		if (isElementExists(noGoods)) {
			Log.logInfo("购物车不为空");
			result = true;

		} else {
			Log.logInfo("购物车为空啊啊啊啊啊");
			result = false;
		}
		return result;
	}

	// 进入订单结算页面
	@SuppressWarnings("unused")
	private void gotoOrderPage(ShoppingCartPage sp) {
		String numString = sp.getElement("goodsNumInShoppingCart").getText();
		Log.logInfo("该商品在购物车中的数量= " + numString.substring(2, 3));
		sp.getElement("submitBtn").click();
	}

	// 查找有库存商品方法
	private String findStockGoods() {

		int i = 1;
		while (true) {
			String cartItemPath = ".//*[@id='goodslist']/li[" + i + "]/div/a";
			String goodsXpath = ".//*[@id='goodslist']/li[" + i
					+ "]/a/span[2]/span[1]";
			WebElement goodsName = driver.findElement(By.xpath(goodsXpath));
			if (!isElementExists(cartItemPath, goodsName)) {
				i++;
				Log.logInfo("已比较的商品数 = " + i + " ");
				continue;
			} else {
				String buttonXpath = ".//*[@id='goodslist']/li[" + i
						+ "]/div/a";
				return buttonXpath;
			}
		}
	}

	// 从搜索列表页中调用加入购物车按钮，并比较实际加入的是否与预期的相等
	public void addToShoppingCartFromList(ShoppingCartPage sp, String xpath) {
		WebElement el = driver.findElement(By.xpath(xpath));
		el.click();
		WebElement success = sp.getElement("addSuccess");
		// 点击购物车后，应提示，加入购物车成功
		if (success.getText().contains("加入购物车成功")) {
			// 输出加入购物车的商品名称
			// 点击快速导航栏
//			sp.getElementNoWait("fastBar").click();
//			// 点击购物车按钮
//			sp.getElement("shoppingcart").click();
//			String result = sp.getElement("submitBtn").getText();
//			String num = result.substring(4, 5);
//			Log.logInfo("购物车中的商品数量= " + num);
//			if (num.equals("1")) {
//				Log.logInfo("加入购物车的商品数量与预期的相等");
//				Assert.assertTrue(true);
//				return;
//			} else {
			Log.logInfo("是否加入成功="+success.getText());
				Log.logInfo("加入购物车成功");
				Log.logInfo("商品列表页加入购物车 测试场景通过");
				Assert.assertTrue(true);
//			}

		} else {
			// 可能是加入失败，也有可能是弹出的提示信息修改
			Log.logInfo("加入失败");
			Assert.fail("是否加入成功="+success.getText());
		}

	}

	// 查找有加入购物车按钮的商品，并跳转到商品详情页
	private void goToSinglePage(ShoppingCartPage sp) {
		int i = 1;
		while (true) {
			String cartItemPath = ".//*[@id='goodslist']/li[" + i + "]/div/a";
			String goodsXpath = ".//*[@id='goodslist']/li[" + i
					+ "]/a/span[2]/span[1]";
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebElement goodsName = driver.findElement(By.xpath(goodsXpath));
			if (!isElementExists(cartItemPath, goodsName)) {
				i++;
				Log.logInfo("已比较的商品数 = " + i + " ");
				continue;
			} else {
				Log.logInfo("比较了" + i + " 次，找到了有库存的商品");
				String goodsPrice = ".//*[@id='goodslist']/li[" + i
						+ "]/a/span[2]/span[3]/span/span";
				driver.findElement(By.xpath(goodsPrice)).click();
				break;
			}
		}
	}

	// 进入单品页，加入购物车并判断是否加入成功
	private void addToShoppingCartFromSingle(ShoppingCartPage sp) {

		sp.getElement("addButton").click();
		sp.getElementNoWait("addButton").click();
		WebElement success = sp.getElement("singleAddSuccess");
		Log.logInfo("点击了加入购物车按钮后: " + success.getText());
		// 点击购物车后，应提示，加入购物车成功
		if (success.getText().contains("加入购物车成功")) {
			sp.getElementNoWait("singleFastBar").click();
			sp.getElementNoWait("shoppingcartSingle").click();
			String result = sp.getElement("submitBtn").getText();
			String num = result.substring(4, 5);
			Log.logInfo("购物车中的商品数量= " + num);
			if (num.equals("1")) {
				Log.logInfo("加入购物车的商品数量与预期的相等");
				Assert.assertTrue(true);
				return;
			} else {
				Log.logInfo("加入购物车的商品数量与预期的不等");
				Assert.fail("加入购物车的商品数量与预期的不等");
			}

		} else {
			// 可能是加入失败，也有可能是弹出的提示信息修改
			Log.logInfo("未弹出加入购物车按钮");
			Assert.fail();
		}
	}

	// 无货检查
	public void noStockCheck(ShoppingCartPage sp) {
		sp.getElement("addButton").click();

		String noGoods = ".//*[@id='productStor']";
		if (!isElementExists(noGoods)) {
			Assert.fail("打开的商品未显示“已售罄”");
		} else {
			Log.logInfo(sp.getElement("noStock").getText());
			sp.getElementNoWait("addButton").click();
			WebElement fail = sp.getElement("noGoods");
			Log.logInfo("点击确定后: " + fail.getText());
			if (fail.getText().contains("库存不足")) {
				Log.logInfo("单品页验证商品“库存不足” 测试用例通过");
				Assert.assertTrue(true);
			} else {
				Log.logInfo("单品页验证商品“库存不足” 测试用例通过");
				Assert.fail("提示信息不对或者其他原因导致失败");
			}

		}
	}

	// 正则方法取购物车中可结算的商品数量
	public int getCartNum(ShoppingCartPage sp) {
		String cartNum = null;
		// 提取数字
		WebElement submitBtn = sp.getElement("toOrderPaySubmit");
		cartNum = submitBtn.getText();
		int num = Integer.parseInt(Pattern.compile("[^0-9]").matcher(cartNum)
				.replaceAll(""));
		return num;
	}

	// 取购物车中真正的的商品数量，包括已下架，无货等
	public List<WebElement> getActualCartNum(ShoppingCartPage sp) {
		List<WebElement> actualGoodsNum = sp.getElements("actualGoodsNum");
		return actualGoodsNum;
	}

	// 获取所有的checkbox
	public List<WebElement> getAllCheckBox(ShoppingCartPage sp) {
		List<WebElement> actualGoodsNum = sp.getElements("goodCheckBox");
		return actualGoodsNum;
	}

	private boolean isExistEditGoods(ShoppingCartPage sp) {
		int cartGoodsNum = this.getCartNum(sp);
		Log.logInfo("当前购物车中有商品数量= " + cartGoodsNum);
		if (cartGoodsNum <= 0) {
			Log.logError("购物车中没有可编辑的商品");
			Assert.fail("购物车中没有可编辑的商品");
			return false;
		} else {
			return true;
		}
	}

	// 检查删除商品
	private void deleteOneGoods(ShoppingCartPage sp)
			throws InterruptedException {
		// 点击购物车中某个商品的编辑按钮
		String canNotBuy = "//div[@class='cart-show-list']/div/label/img[@name='canNotBuyImg']";
		if (!isElementExists(canNotBuy)) {
			// 获取实际的商品数量
			List<WebElement> actualGoodsNum = this.getActualCartNum(sp);
			// 获取checkbox数量，即为可编辑
			List<WebElement> checkbox = this.getAllCheckBox(sp);
			for (int i = 0; i < actualGoodsNum.size(); i++) {
				// 如果当前购物车中仅有一件商品，点击删除后购物车将显示无商品
				if (actualGoodsNum.size() == 1) {
					if (checkbox.get(i).getAttribute("canbuy").equals("yes")) {
						Log.logInfo("当前找到的商品正常的商品，可以编辑");
						sp.getElement("GoodsEdit").click();
						actualGoodsNum
								.get(i)
								.findElement(
										By.xpath("//div/div[2]/div[4]/div/div[1]/div[1]"))
								.click();
						actualGoodsNum
								.get(i)
								.findElement(
										By.xpath("//div/div[2]/div[4]/div/div[1]/div[2]"))
								.click();
						if (this.checkIfShoppingCartNull(sp)) {
							Log.logInfo("删除购物车中仅有的一件商品后，购物车显示空标志");
						} else {
							Log.logInfo("删除购物车中仅有的一件商品后，购物车未显示空标志");
						}
						return;
					} else {
						continue;
					}

				} else {
					if (checkbox.get(i).getAttribute("canbuy").equals("yes")) {
						Log.logInfo("当前找到的商品正常的商品，可以编辑");
						// 获取购物车中当前的商品数量
						int cartGoodNumBefore = this.getCartNum(sp);
						Log.logInfo("当前商品数量= " + cartGoodNumBefore);
						sp.getElement("GoodsEdit").click();
						
						// 预期要删除的商品数量
						String deleteNumString = driver.findElement(By.xpath("//div[@class='cart-show-list']["+ (i + 1)
												+ "]//div[@class='f-img-w'][1]/div[2]/div[4]/div[1]/div[2]/div[1]/div[2]/input"))
								.getAttribute("value");
						Log.logInfo("要删除的商品数量= " + deleteNumString);
						int deleteNum = Integer.parseInt(deleteNumString);
						actualGoodsNum
								.get(i)
								.findElement(
										By.xpath("//div/div[2]/div[4]/div/div[1]/div[1]"))
								.click();
						actualGoodsNum
								.get(i)
								.findElement(
										By.xpath("//div/div[2]/div[4]/div/div[1]/div[2]"))
								.click();
						int cartGoodNumAfter = this.getCartNum(sp);
						Log.logInfo("当前商品数量= " + cartGoodNumAfter);
						if ((cartGoodNumBefore - deleteNum) == cartGoodNumAfter) {
							Log.logInfo("删除成功,购物车剩余商品数量与预期相等");
						} else {
							Log.logInfo(",购物车剩余商品数量与预期相等");
						}
						return;
					}
				}

			}
		}

	}

	// 检查增加商品数量
	private void goodsEditAddNum(ShoppingCartPage sp)
			throws InterruptedException {
		int cartGoodsNum = this.getCartNum(sp);
		Log.logInfo("当前购物车中有商品数量= " + cartGoodsNum);
		if (cartGoodsNum <= 0) {
			Log.logError("购物车中没有可编辑的商品");
			Assert.fail("购物车中没有可编辑的商品");
			return;
		} else {
			// 点击购物车中某个商品的编辑按钮
			String canNotBuy = "//div[@class='cart-show-list']/div/label/img[@name='canNotBuyImg']";
			if (!isElementExists(canNotBuy)) {
				List<WebElement> actualGoodsNum = this.getActualCartNum(sp);
				List<WebElement> checkbox = this.getAllCheckBox(sp);
				for (int i = 0; i < actualGoodsNum.size(); i++) {
					if (checkbox.get(i).getAttribute("canbuy").equals("yes")) {
						Log.logInfo("当前找到的商品正常的商品，可以编辑");
						sp.getElement("GoodsEdit").click();
						actualGoodsNum
								.get(i)
								.findElement(
										By.xpath("//div/div[2]/div[4]/div/div[2]/div[1]/div[3]/img"))
								.click();
						actualGoodsNum
								.get(i)
								.findElement(
										By.xpath("//div/div[2]/div[4]/div/div[1]/div[2]"))
								.click();
						
						int cartGoodNumNow = this.getCartNum(sp);
						Log.logInfo("当前购物车中有商品数量= " + cartGoodNumNow);
						if ((cartGoodNumNow - cartGoodsNum) == 1) {

							Log.logInfo("购物车中编辑单个商品-增加商品数量  测试场景通过");
							Assert.assertTrue(true);
						} else {
							Log.logInfo("购物车中编辑单个商品-增加商品数量  测试场景不通过");
							Log.logInfo("当前购物车中有商品数量= " + cartGoodNumNow);
							Assert.fail();

						}
						break;
					} else {
						continue;
					}
				}
			}

		}
	}

	// 检查删除商品数量
	private void goodsEditDeleteNum(ShoppingCartPage sp)
			throws InterruptedException {
		int cartGoodsNum = this.getCartNum(sp);
		Log.logInfo("当前购物车中有商品数量= " + cartGoodsNum);
		if (cartGoodsNum <= 0) {
			Log.logError("购物车中没有可编辑的商品");
			Assert.fail("购物车中没有可编辑的商品");
			return;
		} else {
			// 点击购物车中某个商品的编辑按钮
			String canNotBuy = "//div[@class='cart-show-list']/div/label/img[@name='canNotBuyImg']";
			if (!isElementExists(canNotBuy)) {
				List<WebElement> actualGoodsNum = this.getActualCartNum(sp);
				List<WebElement> checkbox = this.getAllCheckBox(sp);
				for (int i = 0; i < actualGoodsNum.size(); i++) {
					if (checkbox.get(i).getAttribute("canbuy").equals("yes")) {
						Log.logInfo("当前找到的商品正常的商品，可以编辑");
						sp.getElement("GoodsEdit").click();
						actualGoodsNum
								.get(i)
								.findElement(
										By.xpath("//div/div[2]/div[4]/div/div[2]/div[1]/div[1]/img"))
								.click();
						actualGoodsNum
								.get(i)
								.findElement(
										By.xpath("//div/div[2]/div[4]/div/div[1]/div[2]"))
								.click();
						this.waitimplicitly(3);
						int cartGoodNumNow = this.getCartNum(sp);
						Log.logInfo("当前购物车中有商品数量= " + cartGoodNumNow);
						if ((cartGoodsNum - cartGoodNumNow) == 1) {
							Log.logInfo("购物车中编辑单个商品-减少商品数量  测试场景通过");
							Assert.assertTrue(true);
						} else {
							Log.logInfo("购物车中编辑单个商品-减少商品数量  测试场景不通过");
							Log.logInfo("当前购物车中有商品数量= " + cartGoodNumNow);
							Assert.fail();

						}
						break;
					} else {
						continue;
					}
				}
			}

		}
	}

	// 检查加入购物车库存校验
	private void goodNumCheckStock(ShoppingCartPage sp) {
		WebElement submitBtn = sp.getElement("toOrderPaySubmit");
		int submitNum = this.getCartNum(sp);
		Log.logInfo("当前购物车中有商品数量= " + submitNum);
		if (submitNum <= 0) {
			Log.logError("购物车中没有可下单的商品");
			Assert.fail();
			return;
		} else {
			// 点击购物车中某个商品的编辑按钮
			sp.getElement("GoodsEdit").click();
			sp.getElement("buyNumber").sendKeys("999");
			Log.logInfo("库存校验提示： " + sp.getElement("StockCheckAlert").getText());
			int submitNum2 = Integer.parseInt(submitBtn.getText().substring(4,
					5));
			if (submitNum2 != 999) {
				Log.logInfo("购物车中增加商品库存校验功能  测试场景通过");
				Assert.assertTrue(true);
			} else {
				Log.logInfo("购物车中增加商品库存校验功能  测试场景不通过");
				Assert.fail();
			}
		}
	}

	private void checkShoppingCartNoLogin(ShoppingCartPage sp) {
		// 判断未登录的购物车的样式展示
		String notLoginText = sp.getElement("loginRightNow").getText();
		String notLoginURL = sp.getElement("loginNow").getAttribute("href");
		Log.logInfo("未登录= " + notLoginText);
		Log.logInfo("未登录= " + notLoginURL);
		Log.logInfo(driver.findElement(By.xpath("html/body/div[2]/div[2]"))
				.getText());
		Log.logInfo(driver.findElement(By.xpath("html/body/div[2]/div[3]/a"))
				.getText());
		if (notLoginText.contains("立即登录")) {
			Log.logInfo("购物车未登录样式展示  测试场景通过");
			Assert.assertTrue(true);
		} else {
			Log.logInfo("购物车未登录样式展示  测试场景不通过");
			Assert.fail();
		}

	}

	// 购物车选择商品进入结算页
	private void checkOneGoodToSubmit(ShoppingCartPage sp)
			throws InterruptedException {
		WebElement submitBtn = sp.getElement("toOrderPaySubmit");
		int submitNum = Integer.parseInt(submitBtn.getText().substring(4, 5));
		Log.logInfo("当前购物车中有商品数量= " + submitNum);
		if (submitNum <= 0) {
			Log.logError("购物车中没有可下单的商品");
			Assert.fail();
		} else {
			// 预期的跳转链接地址
			String expectURL = driver.findElement(By.xpath("html/body/form"))
					.getAttribute("action");
			Log.logInfo("预期跳转的链接= " + expectURL);
			submitBtn.click();
			// 以出现实付款、立即支付按钮出现，当前页面当然URL为标准，判断订单确认页面加载成功
			// 实际的跳转链接地址
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			String actualURL = this.getCurrentURL();
			Log.logInfo("实际的链接跳转" + driver.getCurrentUrl());
			Boolean flag = isElementEqual(expectURL, actualURL);
			String actualOrderPay = ".//*[@id='paymentOrderPanel']/div[2]/div[1]/div[1]";// 实付款
			String toPayButton = sp.getElement("toPayOrder").getText();// 支付按钮

			if (isElementExists(actualOrderPay) && toPayButton.contains("立即支付")
					&& flag) {

				Log.logInfo(sp.getElement("actualOrderPay").getText() + " 显示");
				Log.logInfo(sp.getElement("toPayOrder").getText() + " 按钮显示");

				Log.logInfo("进入订单确认页成功\n 选择商品进入结算页面 测试场景通过");
				Assert.assertTrue(true);
			} else {
				Log.logInfo("进入订单确认页失败\n 选择商品进入结算页面 测试场景不通过");
				Assert.fail();
			}

		}
	}

}
