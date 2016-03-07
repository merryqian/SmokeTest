package com.blemall.smokeTest.testcases;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
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

public class TestCase_02_SinglePage extends TestBase {
   String excelPath = "D:\\code\\H5\\TestCase\\H5_SingleProduct.xlsx";
   String sheetName = "SingleProduct";
   ExcelReader reader = new ExcelReader(excelPath, sheetName);
   TimeString ts = new TimeString();
   String timeString = ts.getDate()  ;
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
		
			Assert.fail("页面加载失败");
		}

	} catch (Exception e) {
		Log.logInfo("H5页面加载成功");
	}

	if (sp.getElement("SinglePageLoad").isDisplayed()) {
		Log.logInfo("\"普通商品单品页页面加载情况logo检查\"测试场景通过");

		Assert.assertTrue(true);
		testcaseStatus.put("普通商品单品页页面加载情况logo检查", "Pass");
	} else {
		Log.logError("加载失败了，请查看截图查找原因");
	
		Assert.fail("单品页加载失败");
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

			Assert.fail("页面加载失败");

		}
	} catch (Exception e) {
		Log.logInfo("H5页面加载成功");
	}

	if (sp.getElement("SinglePageLoad").isDisplayed()) {
		WebElement scrollView = sp.getElementNoWait("GoodName");
		this.getIntoView(scrollView);
		String goodName = sp.getElementNoWait("GoodName").getText()
				.substring(3);
		Log.logInfo("实际获取的商品名称= " + goodName);
		String expected = reader.getCellData(3, 6);
		Log.logInfo("预期获取的商品名称= " + expected);
		Assertion.verifyEquals(goodName, expected, "判断打开的单品页是否是预期的商品");

		Log.logInfo("\"普通商品单品页商品名称检查\"测试场景通过");
		Assert.assertTrue(true);
	} else {
		Log.logError("加载失败了，请查看截图查找原因");

		Assert.fail("加载失败");
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

			Assert.fail("页面加载失败");
		}
	} catch (Exception e) {
		Log.logInfo("H5页面加载成功");
	}

	if (sp.getElement("SinglePageLoad").isDisplayed()) {
		WebElement scrollView = sp.getElementNoWait("GoodName");
		this.getIntoView(scrollView);
		String price = sp.getElementNoWait("GoodPrice").getText();
		Log.logInfo("实际的商品价格= " + price);
		String expected = reader.getCellData(5, 6);
		Log.logInfo("预期的商品价格= " + expected);
		Assertion.verifyEquals(price, expected, "比较单品页商品价格是否相等");

		Log.logInfo("\"普通商品单品页价格显示\"测试场景通过");
		Assert.assertTrue(true);
	} else {
		Log.logError("加载失败了，请查看截图查找原因");

		Assert.fail("普通商品单品页价格显示 测试失败");
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

			Assert.fail("页面加载失败");

		}
	} catch (Exception e) {
		Log.logInfo("H5页面加载成功");
	}
	if (sp.getElement("SinglePageLoad").isDisplayed()) {
		WebElement scrollView = sp.getElementNoWait("GoodPrice");
		this.getIntoView(scrollView);
		String noEvalute = sp.getElementNoWait("GoodNoEvalute").getText();
		Log.logInfo("无评价的商品实际显示文案= " + noEvalute);
		String expected = reader.getCellData(6, 6);
		Log.logInfo("预期显示文案= " + expected);
		Assertion.verifyEquals(noEvalute, expected);

		Assert.assertTrue(true);
	} else {
		Log.logError("加载失败了，请查看截图查找原因");

	    Assert.fail("加载失败了，请查看截图查找原因");
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
		if (el.isDisplayed()) {
			Log.logError("页面加载失败：" + sp.getElement("H5Load").getText());

			Assert.fail("页面加载失败");

		}
	} catch (Exception e) {
		Log.logInfo("H5页面加载成功");
	}
	 if (sp.getElement("SinglePageLoad").isDisplayed()) {
		WebElement scrollView = sp.getElementNoWait("GoodPrice");
		this.getIntoView(scrollView);
		if(sp.getElement("GoodMark").isDisplayed())
		{
			Log.logInfo("该商品存在评价记录：");
			Log.logInfo("商品评分："+sp.getElement("GoodMark").getText());
			//Log.logInfo("商品几颗星："+sp.getElement("GoodStar").getText());
			Log.logInfo("商品评论数："+sp.getElement("GoodEvaCount").getText());

			Log.logInfo("\"普通商品单品页有评价记录显示\"测试场景通过");
			Assert.assertTrue(true);
		}
		else
		{
			Log.logInfo("\"普通商品单品页有评价记录显示\"测试场景不通过");

			Assert.fail("商品评价显示异常");
		}
		
	} else {
		Log.logError("加载失败了，请查看截图查找原因");

		Assert.fail("单品页商品有评价记录 测试场景执行失败");
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
		Assert.fail("首页打开失败");
	}
	SinglePage sp = new SinglePage(driver);
	WebElement el = sp.getElementNoWait("H5Load");
	try {
		if (el.isDisplayed()) {
			Log.logError("页面加载失败：" + sp.getElement("H5Load").getText());

			Assert.fail("页面加载失败");
		}
	} catch (Exception e) {
		Log.logInfo("H5页面加载成功");
	}
	if (sp.getElement("SinglePageLoad").isDisplayed()) {
		Log.logInfo("单品页面加载成功");
		WebElement scrollView = sp.getElementNoWait("GoodName");
		this.getIntoView(scrollView);
		if (sp.getElement("GoodPromotion").isDisplayed()) {
			List<WebElement> promotionType = sp
					.getElements("GoodPromotionType");
			for (int i = 0; i < promotionType.size(); i++) {
				Log.logInfo("促销活动类型= " + promotionType.get(i).getText());
			}
			sp.getElement("PromotionClick").click();
			List<WebElement> promotionName = sp
					.getElements("PromotionName");
			for (int i = 0; i < promotionType.size(); i++) {
				Log.logInfo("促销活动名称= " + promotionName.get(i).getText());
			}
			Log.logInfo("普通商品单品页普通商品“促销信息”显示" + "测试场景通过");
			Assert.assertTrue(true);
		} else {
			Log.logInfo("普通商品单品页普通商品“促销信息”显示：\"" + "测试场景失败");
			Assert.fail("单品页未看到促销信息");
			
		}

	} else {
		Log.logError("加载失败了，请查看截图查找原因");
		Assert.fail("单品页加载失败");
	}

}

// 普通商品单品页是否存在“加入购物车”按钮
@Test(dataProvider = "providerMethod")
public void addToCartButton(Map<String, String> param) throws IOException {
	try {
		this.goTo(param.get("url") + param.get("productID"));
		Log.logInfo("打开连接=" + param.get("url") + param.get("productID"));
	} catch (Exception e) {
		Log.logInfo("打开失败");
		Assert.fail("页面打开失败");
	}
	SinglePage sp = new SinglePage(driver);
	WebElement el = sp.getElement("H5Load");
	try {
		if (el.isDisplayed()) {
			Log.logError("页面加载失败：" + sp.getElement("H5Load").getText());
			Assert.fail("页面加载失败");
		}
	} catch (Exception e) {
		Log.logInfo("H5页面加载成功");
	}

	if (sp.getElement("SinglePageLoad").isDisplayed()) {
		WebElement button = sp.getElement("AddShoppingCartButton");
		if (button.isDisplayed()) {
			Log.logInfo("\"普通商品单品页是否存在“加入购物车”按钮：\"" + "测试场景通过");
			Assert.assertTrue(true);
		} else {
			Log.logInfo("\"普通商品单品页是否存在“加入购物车”按钮：\"" + "测试场景失败");
			Assert.fail("单品页未看到加入购物车按钮");
			
		}
	} else {
		Log.logError("加载失败了，请查看截图查找原因");
		Assert.fail("加载失败了，请查看截图查找原因");

	}

}

// 查看售后服务选择检查
@Test(dataProvider = "providerMethod")
public void customerService(Map<String, String> param) throws IOException {
	try {
		this.goTo(param.get("url") + param.get("productID"));
		Log.logInfo("打开连接=" + param.get("url") + param.get("productID"));
	} catch (Exception e) {
		Log.logInfo("打开失败");
		Assert.fail("页面打开失败");
	}
	SinglePage sp = new SinglePage(driver);
	WebElement el = sp.getElement("H5Load");
	try {
		if (el.isDisplayed()) {
			Log.logError("页面加载失败：" + sp.getElement("H5Load").getText());
			Assert.fail("页面加载失败");
		}
	} catch (Exception e) {
		Log.logInfo("H5页面加载成功");
	}
	if (sp.getElement("SinglePageLoad").isDisplayed()) {
		WebElement scrollView = sp.getElementNoWait("GoodName");
		this.getIntoView(scrollView);
		WebElement service = sp.getElement("CustomerService");
		if (service.isDisplayed()) {
			Log.logInfo(service.getText() + ": "
					+ sp.getElement("ServiceContent").getText());
			Log.logInfo("\"查看售后服务选择检查:\"" + "测试场景通过");
			Assert.assertTrue(true);
		} else {
			Log.logInfo("\"查看售后服务选择检查:\"" + "测试场景失败");
			Assert.fail("单品页未看到售后服务内容");
		}
	} else {
		Log.logError("加载失败了，请查看截图查找原因");
		Assert.fail("加载失败了，请查看截图查找原因");

	}
}

// 单品页供货方显示检查-自营
@Test(dataProvider = "providerMethod")
public void supplierCheckBySelf(Map<String, String> param)
		throws IOException {
	try {
		this.goTo(param.get("url") + param.get("productID"));
		Log.logInfo("打开连接=" + param.get("url") + param.get("productID"));
	} catch (Exception e) {
		Log.logInfo("打开失败");
		Assert.fail("页面打开失败");
	}
	SinglePage sp = new SinglePage(driver);
	WebElement el = sp.getElement("H5Load");
	try {
		if (el.isDisplayed()) {
			Log.logError("页面加载失败：" + sp.getElement("H5Load").getText());
			Assert.fail("页面加载失败");
		}
	} catch (Exception e) {
		Log.logInfo("H5页面加载成功");
	}
	if (sp.getElement("SinglePageLoad").isDisplayed()) {
		WebElement scrollView = sp.getElementNoWait("GoodName");
		this.getIntoView(scrollView);
		WebElement suplier = sp.getElement("SupplierCheckBySelf");
		if (suplier.isDisplayed()) {
			Log.logInfo("单品页商品供应商= " + suplier.getText());
			Assert.assertEquals(suplier.getText(), "自营");
			Log.logInfo("\"单品页供货方显示检查-自营:\"" + "测试场景通过");
			Assert.assertTrue(true);
		} else {
			Log.logInfo("\"查看售后服务选择检查:\"" + "测试场景失败");
			Assert.fail("单品页未看到供货方");
		}
	} else {
		Log.logError("加载失败了，请查看截图查找原因");
		Assert.fail("加载失败了，请查看截图查找原因");

	}
}

// 单品页供货方显示检查-联营
@Test(dataProvider = "providerMethod")
public void supplierCheckByUnion(Map<String, String> param)
		throws IOException {
	try {
		this.goTo(param.get("url") + param.get("productID"));
		Log.logInfo("打开连接=" + param.get("url") + param.get("productID"));
	} catch (Exception e) {
		Log.logInfo("打开失败");
		Assert.fail("页面打开失败");
	}
	SinglePage sp = new SinglePage(driver);
	WebElement el = sp.getElement("H5Load");
	try {
		if (el.isDisplayed()) {
			Log.logError("页面加载失败：" + sp.getElement("H5Load").getText());
			Assert.fail("页面加载失败");
		}
	} catch (Exception e) {
		Log.logInfo("H5页面加载成功");
	}
	if (sp.getElement("SinglePageLoad").isDisplayed()) {
		WebElement scrollView = sp.getElementNoWait("GoodName");
		this.getIntoView(scrollView);
		WebElement suplier = sp.getElement("SupplierCheckBySelf");
		if (suplier.isDisplayed()) {
			Log.logInfo("单品页商品供应商= " + suplier.getText());
			Assert.assertEquals(suplier.getText(), "联营");
			Log.logInfo("\"单品页供货方显示检查-联营:\"" + "测试场景通过");
			Assert.assertTrue(true);
		} else {
			Log.logInfo("\"查看售后服务选择检查:\"" + "测试场景失败");
			Assert.fail("单品页未看到供货方");
		}
	} else {
		Log.logError("加载失败了，请查看截图查找原因");
		Assert.fail("加载失败了，请查看截图查找原因");

	}
}

// 单品页支持自提显示检查-支持
@Test(dataProvider = "providerMethod")
public void takeBySelf(Map<String, String> param) throws IOException {
	try {
		this.goTo(param.get("url") + param.get("productID"));
		Log.logInfo("打开连接=" + param.get("url") + param.get("productID"));
	} catch (Exception e) {
		Log.logInfo("打开失败");
		Assert.fail("页面打开失败");
	}
	SinglePage sp = new SinglePage(driver);
	WebElement el = sp.getElement("H5Load");
	try {
		if (el.isDisplayed()) {
			Log.logError("页面加载失败：" + sp.getElement("H5Load").getText());
			Assert.fail("页面加载失败");
		}
	} catch (Exception e) {
		Log.logInfo("H5页面加载成功");
	}
	if (sp.getElement("SinglePageLoad").isDisplayed()) {
		WebElement scrollView = sp.getElementNoWait("GoodName");
		this.getIntoView(scrollView);
		WebElement suplier = sp.getElement("TakeBySelf");
		if (suplier.isDisplayed()) {
			Log.logInfo("是否支持自提= " + suplier.getText());
			Assert.assertEquals(suplier.getText(), "支持自提");
			Log.logInfo("\"单品页支持自提显示检查-支持:\"" + "测试场景通过");
			Assert.assertTrue(true);
		} else {
			Log.logInfo("\"单品页支持自提显示检查-支持:\"" + "测试场景失败");
			Assert.fail("单品页未展示支持自提标示");
		}
	} else {
		Log.logError("加载失败了，请查看截图查找原因");
		Assert.fail("加载失败了，请查看截图查找原因");

	}
}

// 单品页退换货显示检查-不支持
@Test(dataProvider = "providerMethod")
public void returnOrChangeGoodsNotSupported(Map<String, String> param)
		throws IOException {
	try {
		this.goTo(param.get("url") + param.get("productID"));
		Log.logInfo("打开连接=" + param.get("url") + param.get("productID"));
	} catch (Exception e) {
		Log.logInfo("打开失败");
		Assert.fail("页面打开失败");
	}
	SinglePage sp = new SinglePage(driver);
	WebElement el = sp.getElement("H5Load");
	try {
		if (el.isDisplayed()) {
			Log.logError("页面加载失败：" + sp.getElement("H5Load").getText());
			Assert.fail("页面加载失败");
		}
	} catch (Exception e) {
		Log.logInfo("H5页面加载成功");
	}
	if (sp.getElement("SinglePageLoad").isDisplayed()) {
		WebElement scrollView = sp.getElementNoWait("GoodName");
		this.getIntoView(scrollView);
		WebElement suplier = sp.getElement("ReturnOrChangeGoodsNotSupported");
		if (suplier.isDisplayed()) {
			Log.logInfo("是否支持7天无理由退换货= " + suplier.getText());
			Assert.assertEquals(suplier.getText(), "不支持7天无理由退换货");
			Log.logInfo("\"单品页退换货显示检查-不支持:\"" + "测试场景通过");
			Assert.assertTrue(true);
		} else {
			Log.logInfo("\"单品页退换货显示检查-不支持:\"" + "测试场景失败");
			Assert.fail("单品页未展示不支持7天无理由退换货标示");
		}
	} else {
		Log.logError("加载失败了，请查看截图查找原因");
		Assert.fail("加载失败了，请查看截图查找原因");

	}
}

// 单品页退换货显示检查-支持
@Test(dataProvider = "providerMethod")
public void returnOrChangeGoods(Map<String, String> param)
		throws IOException {
	try {
		this.goTo(param.get("url") + param.get("productID"));
		Log.logInfo("打开连接=" + param.get("url") + param.get("productID"));
	} catch (Exception e) {
		Log.logInfo("打开失败");
		Assert.fail("页面打开失败");
	}
	SinglePage sp = new SinglePage(driver);
	WebElement el = sp.getElement("H5Load");
	try {
		if (el.isDisplayed()) {
			Log.logError("页面加载失败：" + sp.getElement("H5Load").getText());
			Assert.fail("页面加载失败");
		}
	} catch (Exception e) {
		Log.logInfo("H5页面加载成功");
	}
	if (sp.getElement("SinglePageLoad").isDisplayed()) {
		WebElement scrollView = sp.getElementNoWait("GoodName");
		this.getIntoView(scrollView);
		WebElement suplier = sp.getElement("ReturnOrChangeGoodsSupported");
		if (suplier.isDisplayed()) {
			Log.logInfo("是否支持7天无理由退换货= " + suplier.getText());
			Assert.assertEquals(suplier.getText(), "7天无理由退换货");
			Log.logInfo("\"单品页退换货显示检查-支持:\"" + "测试场景通过");
			Assert.assertTrue(true);
		} else {
			Log.logInfo("\"单品页退换货显示检查-支持:\"" + "测试场景失败");
			Assert.fail("单品页未展示支持7天无理由退换货标示");
		}
	} else {
		Log.logError("加载失败了，请查看截图查找原因");
		Assert.fail("加载失败了，请查看截图查找原因");

	}
}

// 单品页货到付款显示检查-支持
@Test(dataProvider = "providerMethod")
public void CODSupport(Map<String, String> param)
		throws IOException {
	try {
		this.goTo(param.get("url") + param.get("productID"));
		Log.logInfo("打开连接=" + param.get("url") + param.get("productID"));
	} catch (Exception e) {
		Log.logInfo("打开失败");
		Assert.fail("页面打开失败");
	}
	SinglePage sp = new SinglePage(driver);
	WebElement el = sp.getElement("H5Load");
	try {
		if (el.isDisplayed()) {
			Log.logError("页面加载失败：" + sp.getElement("H5Load").getText());
			Assert.fail("页面加载失败");
		}
	} catch (Exception e) {
		Log.logInfo("H5页面加载成功");
	}
	if (sp.getElement("SinglePageLoad").isDisplayed()) {
		WebElement scrollView = sp.getElementNoWait("GoodName");
		this.getIntoView(scrollView);
		WebElement suplier = sp.getElement("CODSupport");
		if (suplier.isDisplayed()) {
			Log.logInfo("是否支持货到付款= " + suplier.getText());
			Assert.assertEquals(suplier.getText(), "支持货到付款");
			Log.logInfo("\"单品页货到付款显示检查-支持:\"" + "测试场景通过");
			Assert.assertTrue(true);
		} else {
			Log.logInfo("\"单品页货到付款显示检查-支持:\"" + "测试场景失败");
			Assert.fail("单品页未展示支持货到付款");
		}
	} else {
		Log.logError("加载失败了，请查看截图查找原因");
		Assert.fail("加载失败了，请查看截图查找原因");

	}

}

// 单品页商品规格检查
@Test(dataProvider = "providerMethod")
public void productSpecification(Map<String, String> param) throws IOException {
	try {
		this.goTo(param.get("url") + param.get("productID"));
		Log.logInfo("打开连接=" + param.get("url") + param.get("productID"));
	} catch (Exception e) {
		Log.logInfo("打开失败");
		Assert.fail("页面打开失败");
	}
	SinglePage sp = new SinglePage(driver);
	WebElement el = sp.getElement("H5Load");
	try {
		if (el.isDisplayed()) {
			Log.logError("页面加载失败：" + sp.getElement("H5Load").getText());
			Assert.fail("页面加载失败");
		}
	} catch (Exception e) {
		Log.logInfo("H5页面加载成功");
	}
	if (sp.getElement("SinglePageLoad").isDisplayed()) {
		WebElement scrollView = sp.getElementNoWait("GoodName");
		this.getIntoView(scrollView);
		WebElement specification1 = sp.getElement("Chooice");
		WebElement specification2 = sp.getElement("Specification");
		if (specification1.isDisplayed()) {
			Log.logInfo(specification1.getText()+specification2.getText());
			Assert.assertEquals(specification1.getText(), "选择");
			specification1.click();
			//规格选择红色
			sp.getElement("Red").click();
			//尺寸选择50
			sp.getElement("SKUAttributeBtn").click();
			Log.logInfo("\"单品页商品规格选择检查:\"" + "测试场景通过");
			Assert.assertTrue(true);
		} else {
			Log.logInfo("\"单品页商品规格选择检查:\"" + "测试场景失败");
			Assert.fail("单品页未展示选择规格选项");
		}
	} else {
	Log.logError("加载失败了，请查看截图查找原因");
		Assert.fail("加载失败了，请查看截图查找原因");

		}

	}
//单品页商品加入购物车检查
 @Test(dataProvider = "providerMethod")
public void addToShoppingCart(Map<String, String> param) throws IOException {
	try {
		this.goTo(param.get("url") + param.get("productID"));
		Log.logInfo("打开连接=" + param.get("url") + param.get("productID"));
	} catch (Exception e) 
	{
		Log.logInfo("打开失败");
		Assert.fail("页面打开失败");
	}
	SinglePage sp = new SinglePage(driver);
	WebElement el = sp.getElement("H5Load");
	try {
		if (el.isDisplayed()) {
			Log.logError("页面加载失败：" + sp.getElement("H5Load").getText());
			Assert.fail("页面加载失败");
		}
	} catch (Exception e) {
		Log.logInfo("H5页面加载成功");
	}
	if (sp.getElement("SinglePageLoad").isDisplayed()) {
		WebElement scrollView = sp.getElementNoWait("GoodName");
		Log.logInfo("单品页加载成功");
		this.getIntoView(scrollView);
		WebElement addbutton = sp.getElement("AddShoppingCartButton");
		if (addbutton.isDisplayed()) {
			Log.logInfo(addbutton.getText());
			String goodNumBefore=sp.getElement("GoodNum").getText();
			Log.logInfo("当前购物车中有商品数量= "+goodNumBefore);
			addbutton.click();
			//规格选择红色
			sp.getElement("Red").click();
			//尺寸选择50
			sp.getElement("SKUAttributeBtn").click();
			//点击确定
			sp.getElement("AddButton").click();
			//捕捉加入购物车成功弹出框提示信息
			String success=sp.getElement("AddSuccess").getText();
			if(success.equals("加入购物车成功"))
			{
				Log.logInfo("成功提示信息： "+success);
				Assert.assertEquals(success,"加入购物车成功");
				String goodNumAfter=sp.getElement("NumAfter").getText();
				Log.logInfo("加入之后购物车中有商品数量= "+goodNumAfter);
				//因未登录，所以每次购物车图标应只有一件商品
				Assert.assertEquals(Integer.parseInt(goodNumAfter), 1);
				Log.logInfo("\"单品页商品加入购物车检查:\"" + "测试场景通过");
				Assert.assertTrue(true);
			}else{
				Log.logInfo("失败提示信息： "+success);
				Assert.fail("单品页商品加入购物车失败");
			}
			
		} else {
			Log.logInfo("\"单品页商品加入购物车检查:\"" + "测试场景失败");
		}
	} else {
	Log.logError("加载失败了，请查看截图查找原因");
		Assert.fail("加载失败了，请查看截图查找原因");

		}

	}
//改变商品数量按钮检查
@Test(dataProvider = "providerMethod")
public void changeGoodNum(Map<String, String> param) throws IOException {
	try {
		this.goTo(param.get("url") + param.get("productID"));
		Log.logInfo("打开连接=" + param.get("url") + param.get("productID"));
	} catch (Exception e) {
		Log.logInfo("打开失败");
		Assert.fail("页面打开失败");
	}
	SinglePage sp = new SinglePage(driver);
	WebElement el = sp.getElement("H5Load");
	try {
		if (el.isDisplayed()) {
			Log.logError("页面加载失败：" + sp.getElement("H5Load").getText());
			Assert.fail("页面加载失败");
		}
	} catch (Exception e) {
		Log.logInfo("H5页面加载成功");
	}
	if (sp.getElement("SinglePageLoad").isDisplayed()) {
		WebElement scrollView = sp.getElementNoWait("GoodName");
		Log.logInfo("单品页加载成功");
		this.getIntoView(scrollView);
		WebElement inputNum = sp.getElement("InputNum");
		int num1=Integer.parseInt(inputNum.getAttribute("value"));
		Log.logInfo("商品数量="+inputNum.getAttribute("value"));
		if (inputNum.isDisplayed()) {
			
			if(inputNum.getAttribute("value").equals("1"))
			{
			  Log.logInfo("输入框中的商品数量为1,不可再点击“-”");
			  sp.getElement("IncreaseGoodNum").click();
			  WebElement inputNum2=sp.getElement("InputNum");
			  Log.logInfo("点击“+”号后输入框商品数量= "+inputNum2.getAttribute("value"));
			  int num2=Integer.parseInt(inputNum2.getAttribute("value"));
			  Log.logInfo("num1= "+num1);
			  Log.logInfo("num2= "+num2);
			  //点击一次增加按钮，数量增加1
			  Assert.assertEquals((num2-num1),1);
			  Log.logInfo("\"改变商品数量按钮检查\"测试场景通过");
			  Assert.assertTrue(true);
			}
			else{
				Log.logInfo("输入框中的商品数量= "+sp.getElement("InputNum").getAttribute("value"));
				sp.getElement("DecreaseGoodNum").click();
				Log.logInfo("点击“-”号后输入框中的商品数量= "+sp.getElement("InputNum").getAttribute("value"));
			}
		  
		}else{
			Log.logInfo("输入框商品数量框显示异常，请查看");
			Assert.fail("改变商品数量按钮异常");
		}
		
	}
	
	 else {
	Log.logError("加载失败了，请查看截图查找原因");
		Assert.fail("加载失败了，请查看截图查找原因");

		}
	}
//单品页多件商品加入购物车检查
@Test(dataProvider = "providerMethod")
public void mutiGoodsToShoppingCart(Map<String, String> param) throws IOException {
	try {
		this.goTo(param.get("url") + param.get("productID"));
		Log.logInfo("打开连接=" + param.get("url") + param.get("productID"));
	} catch (Exception e) {
		Log.logInfo("打开失败");
		Assert.fail("页面打开失败");
	}
	SinglePage sp = new SinglePage(driver);
	WebElement el = sp.getElement("H5Load");
	try {
		if (el.isDisplayed()) {
			Log.logError("页面加载失败：" + sp.getElement("H5Load").getText());
			Assert.fail("页面加载失败");
		}
	} catch (Exception e) {
		Log.logInfo("H5页面加载成功");
	}
	if (sp.getElement("SinglePageLoad").isDisplayed()) {
		WebElement scrollView = sp.getElementNoWait("GoodName");
		Log.logInfo("单品页加载成功");
		this.getIntoView(scrollView);
		WebElement inputNum = sp.getElement("InputNum");
		int num1=Integer.parseInt(inputNum.getAttribute("value"));
		Log.logInfo("商品数量="+num1);
		String expectedNum=param.get("goodNum");
		if (inputNum.isDisplayed()) {
			int forNum=Integer.parseInt(param.get("goodNum"));
			Log.logInfo("预期加入购物车的数量= "+forNum);
			inputNum.clear();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			inputNum.sendKeys(expectedNum);
			sp.getElementNoWait("AddShoppingCartButton").click();
			sp.getElementNoWait("AddButton").click();
			String success=sp.getElement("AddSuccess").getText();
			if(success.equals("加入购物车成功"))
			{
				Log.logInfo("成功提示信息： "+success);
				Assert.assertEquals(success,"加入购物车成功");
				String goodNumAfter=sp.getElement("NumAfter").getText();
				Log.logInfo("加入之后购物车中有商品数量= "+goodNumAfter);
				Assert.assertEquals(goodNumAfter,expectedNum );
				Log.logInfo("\"单品页商品加入购物车检查:\"" + "测试场景通过");
				Assert.assertTrue(true);
			}else{
				Log.logInfo("失败提示信息： "+success);
				Assert.fail("单品页商品加入购物车失败");
			}
		  
		}else{
			Log.logInfo("输入框商品数量框显示异常，请查看");
			Assert.fail("输入框商品数量框显示异常，请查看");
		}
		
	}
	 else {
	Log.logError("加载失败了，请查看截图查找原因");
		Assert.fail("加载失败了，请查看截图查找原因");
		}
	}
//数量大于实际库存加入购物车检查
@Test(dataProvider = "providerMethod")
public void moreThanKuCun(Map<String, String> param) throws IOException {
	try {
		this.goTo(param.get("url") + param.get("productID"));
		Log.logInfo("打开连接=" + param.get("url") + param.get("productID"));
	} catch (Exception e) {
		Log.logInfo("打开失败");
		Assert.fail("页面打开失败");
	}
	SinglePage sp = new SinglePage(driver);
	WebElement el = sp.getElement("H5Load");
	try {
		if (el.isDisplayed()) {
			Log.logError("页面加载失败：" + sp.getElement("H5Load").getText());
			Assert.fail("页面加载失败");
		}
	} catch (Exception e) {
		Log.logInfo("H5页面加载成功");
	}
	if (sp.getElement("SinglePageLoad").isDisplayed()) {
		WebElement scrollView = sp.getElementNoWait("GoodName");
		Log.logInfo("单品页加载成功");
		this.getIntoView(scrollView);
		WebElement inputNum = sp.getElement("InputNum");
		int num1=Integer.parseInt(inputNum.getAttribute("value"));
		Log.logInfo("商品数量="+num1);
		String expectedNum=param.get("goodNum");
		if (inputNum.isDisplayed()) {
			int forNum=Integer.parseInt(param.get("goodNum"));
			Log.logInfo("预期加入购物车的数量= "+forNum);
			inputNum.clear();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			inputNum.sendKeys(expectedNum);
			sp.getElementNoWait("AddShoppingCartButton").click();
			sp.getElementNoWait("AddButton").click();
			String fail=sp.getElement("KuCunBuZu").getText();
			if(fail.equals("库存不足"))
			{
				Log.logInfo("提示信息： "+fail);
				Assert.assertEquals(fail,"库存不足");
				Log.logInfo("\"数量大于实际库存加入购物车检查:\"" + "测试场景通过");
				Assert.assertTrue(true);
			}else{
				Log.logInfo("失败提示信息： "+fail);
				Assert.fail("单品页商品加入购物车超过库存失败");
				
			}
		   
		}else{
			Log.logInfo("输入框商品数量框显示异常，请查看");
			Assert.fail("输入框商品数量框显示异常，请查看");		
			}
		
	}
	
	 else {
	Log.logError("加载失败了，请查看截图查找原因");
		Assert.fail("加载失败了，请查看截图查找原因");

		}
	}
//数量大于实际库存加入购物车检查
@Test(dataProvider = "providerMethod")
public void outOfStock(Map<String, String> param) throws IOException {
	try {
		this.goTo(param.get("url") + param.get("productID"));
		Log.logInfo("打开连接=" + param.get("url") + param.get("productID"));
	} catch (Exception e) {
		Log.logInfo("打开失败");
		Assert.fail("页面打开失败");
	}
	SinglePage sp = new SinglePage(driver);
	WebElement el = sp.getElement("H5Load");
	try {
		if (el.isDisplayed()) {
			Log.logError("页面加载失败：" + sp.getElement("H5Load").getText());
			Assert.fail("页面加载失败");
		}
	} catch (Exception e) {
		Log.logInfo("H5页面加载成功");
	}
	if (sp.getElement("SinglePageLoad").isDisplayed()) {
		WebElement scrollView = sp.getElementNoWait("GoodName");
		Log.logInfo("单品页加载成功");
		this.getIntoView(scrollView);
		WebElement addButton = sp.getElement("AddShoppingCartButton");
		if (addButton.isDisplayed()) {
			
			sp.getElementNoWait("AddShoppingCartButton").click();
			sp.getElementNoWait("AddButton").click();
			Log.logInfo("库存不足标志： "+sp.getElement("OutOfStock").getText());
			String fail=sp.getElement("KuCunBuZu").getText();
			if(fail.equals("库存不足"))
			{
				Log.logInfo("提示信息： "+fail);
				Assert.assertEquals(fail,"库存不足");
				Log.logInfo("\"无库存商品加入购物车按钮检查:\"" + "测试场景通过");
				Assert.assertTrue(true);
			}else{
				Log.logInfo("失败提示信息： "+fail);
				Assert.fail("单品页检查无库存商品加入购物车失败");
				
			}
		   
		}else{
			Log.logInfo("加入购物车的按钮显示异常，请查看");
			Assert.fail("加入购物车的按钮显示异常，请查看");
		}
		
	}
	
	 else {
	Log.logError("加载失败了，请查看截图查找原因");
		Assert.fail("加载失败了，请查看截图查找原因");

		}
	}
//单品页商品详情选择检查
 @Test(dataProvider = "providerMethod")
public void goodDetails(Map<String, String> param) throws IOException {
	try {
		this.goTo(param.get("url") + param.get("productID"));
		Log.logInfo("打开连接=" + param.get("url") + param.get("productID"));
	} catch (Exception e) {
		Log.logInfo("打开失败");
		Assert.fail("页面打开失败");
	}
	SinglePage sp = new SinglePage(driver);
	WebElement el = sp.getElement("H5Load");
	try {
		if (el.isDisplayed()) {
			Log.logError("页面加载失败：" + sp.getElement("H5Load").getText());
			Assert.fail("页面加载失败");
		}
	} catch (Exception e) {
		Log.logInfo("H5页面加载成功");
	}
	if (sp.getElement("SinglePageLoad").isDisplayed()) {
		WebElement scrollView = sp.getElementNoWait("GoodName");
		Log.logInfo("单品页加载成功");
		this.getIntoView(scrollView);
		WebElement goodDetails = sp.getElement("GoodDetails");
		if (goodDetails.isDisplayed()) {
			
			goodDetails.click();
			List<WebElement> list=sp.getElements("Details");
			for(int i=0;i<list.size();i++)
			{
			   Log.logInfo("单品页商品详情内容： "+list.get(i).getText());	
			}
			Assert.assertTrue(true);   
		}else{
			Log.logInfo("单品页的商品详情按钮显示异常，请查看");
		}
		
	}
	
	 else {
	Log.logError("加载失败了，请查看截图查找原因");
		Assert.fail("加载失败了，请查看截图查找原因");

		}
	}
//单品页商品推荐检查
@Test(dataProvider = "providerMethod")
public void goodRecommend(Map<String, String> param) throws IOException {
	try {
		this.goTo(param.get("url") + param.get("productID"));
		Log.logInfo("打开连接=" + param.get("url") + param.get("productID"));
	} catch (Exception e) {
		Log.logInfo("打开失败");
		Assert.fail("页面打开失败");
	}
	SinglePage sp = new SinglePage(driver);
	WebElement el = sp.getElement("H5Load");
	try {
		if (el.isDisplayed()) {
			Log.logError("页面加载失败：" + sp.getElement("H5Load").getText());
			Assert.fail("页面加载失败");
		}
	} catch (Exception e) {
		Log.logInfo("H5页面加载成功");
	}
	if (sp.getElement("SinglePageLoad").isDisplayed()) {
		WebElement scrollView = sp.getElementNoWait("GoodRecommend");
		Log.logInfo("单品页加载成功");
		this.getIntoView(scrollView);
		WebElement goodRecommend = sp.getElement("GoodRecommend");
		if (goodRecommend.isDisplayed()) {
			 Log.logInfo(goodRecommend.getText()+" 加载成功");
			 List<WebElement> list=sp.getElements("RecommendGoodsList");
			 Log.logInfo("单品页推荐商品列表个数= "+list.size());
			 WebElement goods=null;
				for(int i=1;i<=list.size();i++)
				{
			 goods=driver.findElement(By.xpath(".//*[@id='scroller']/div[10]/div/ul/li["+i+"]/div/div[2]/a"));
				    Log.logInfo("热门推荐商品= "+goods.getText());
				    Log.logInfo(goods);
				}
				Assert.assertTrue(true);
		}else{
			Log.logInfo("单品页的商品详情按钮显示异常，请查看");
			Assert.fail("单品页的商品详情按钮显示异常，请查看");
		}
		
	}
	
	 else {
	Log.logError("加载失败了，请查看截图查找原因");
		Assert.fail("加载失败了，请查看截图查找原因");

		}
	}

 


}
