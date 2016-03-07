package com.merry;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.testng.annotations.Test;

public class CreateXml {
	 Map<String, String> result=new HashMap<String, String>();
	 Document document;
     Element root;
     
     public void load(String filePath) {
 		File file = new File(filePath);
 		if (file.exists()) {
 			SAXReader reader = new SAXReader();
 			try {
 				document = reader.read(file);
 			} catch (DocumentException e) {
 				System.out.println((filePath + " �ļ���ȡ�쳣"));
 			}
 		} else {
 			System.out.println((filePath + " �ļ�������"));
 		}
 	}
  
	public void createXmlFile()
	{
		
	    Document document = DocumentHelper.createDocument();
		Element fElement=document.addElement("TestReport");
		Element sElement=fElement.addElement("ResultInfo");
		sElement.addAttribute("FailedNum", result.get("failed") );
		sElement.addAttribute("PassedNum", result.get("passed") );
		sElement.addAttribute("TotalNum", result.get("total") );
		
		try{
			XMLWriter output=new XMLWriter(new FileWriter(new File("D:/result.xml")));
			output.write( document );
			output.flush();
			output.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
	 @Test
	public void readXml()
	{
		this.load("D:/code/xmlfile/testng-results.xml");
		
		root = document.getRootElement();
		System.out.println("skipped=" + root.attributeValue("skipped"));
		System.out.println("pass=" + root.attributeValue("passed"));
		System.out.println("failed=" + root.attributeValue("failed"));
		System.out.println("total=" + root.attributeValue("total"));
		result.put("failed", root.attributeValue("passed"));
		result.put("passed", root.attributeValue("failed"));
		result.put("total", root.attributeValue("total"));
		
		this.createXmlFile();
	}


}
