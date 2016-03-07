package com.test.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.testng.annotations.Test;

public class ParseXml {
	Document document;

	public void load(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			SAXReader reader = new SAXReader();
			try {
				document = reader.read(file);
			} catch (DocumentException e) {
				Log.logInfo(filePath + " 文件读取异常");
			}
		} else {
			Log.logInfo(filePath + " 文件不存�?");
		}
	}

	public Element getElementObject(String elementPath) {
		return (Element) document.selectSingleNode(elementPath);
	}

	public boolean isExist(String elementPath) {
		boolean flag = false;
		Element element = this.getElementObject(elementPath);
		if (element != null)
			flag = true;
		return flag;
	}

	public String getElementText(String elementPath) {
		Element element = this.getElementObject(elementPath);
		if (element != null) {
			return element.getText().trim();
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Element> getElementObjects(String elementPath) {
		return document.selectNodes(elementPath);
	}

	public Map<String, String> getChildrenInfoByElement(Element element) {
		Map<String, String> map = new HashMap<String, String>();
		@SuppressWarnings("unchecked")
		List<Element> children=element.elements();
		for (Element e:children) {
			map.put(e.getName(), e.getText());
		}
		return map;
	}

	@Test
	public void test1()
	{
		this.load("Config/config.xml");
		System.out.println(this.getElementText("//browser"));
	}

}
