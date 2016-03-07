package com.blemall.smokeTest.page;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.test.base.Page;

public class SearchPage extends Page{

	public SearchPage(WebDriver driver) throws IOException {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	@Test(dataProvider="providerMethod")
	public void searchGoods(Map<String,String> param) throws IOException
	{
		SearchPage sp=new SearchPage(driver);
		sp.getElement("inputbox").sendKeys(param.get("goodsname"));
		sp.getElement("button").click();
		sp.getElement("addshoppingcard").click();
		//sp.getElement("goodsnum")
		
	}
}
