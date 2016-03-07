package com.blemall.smokeTest.page;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.test.base.Page;

public class AddShoppingCard extends Page {

	public AddShoppingCard(WebDriver driver) throws IOException {
		super(driver);
		
	}
	@Test(dataProvider="providerMethod")
	public void addShoppingCard(Map<String,String> param) throws IOException
	{
		AddShoppingCard sp=new AddShoppingCard(driver);
		sp.getElement("addshoppingcard").click();
		String num=sp.getElement("goodsnum").getText();
		System.out.println("num= "+num);
		
	}
	

}
