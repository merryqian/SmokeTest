package com.test.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.testng.IResultMap;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import com.test.util.TimeString;
import com.test.util.WriteResultUtils;

public class TestngListener extends TestListenerAdapter {
	private static Logger logger = Logger.getLogger(TestngListener.class);
	private TestResult testResult = new TestResult();
	public static List<TestExpect> expectedList = new ArrayList<TestExpect>();
	TimeString ts=new TimeString();

	@Override
	public void onTestStart(ITestResult tr) {
		super.onTestStart(tr);
		logger.info(tr.getName() + " Start");
		testResult.setDate(ts.getDate("-"));
		testResult.setStartTime(ts.getTime(":"));
	}

	@Override
	public void onFinish(ITestContext testContext) {
		super.onFinish(testContext);
		IResultMap passedMap = testContext.getPassedTests();
		IResultMap failedMap = testContext.getFailedTests();
		
		testResult.setEndTime(ts.getTime(":"));
		testResult.setDuration("");
		testResult.setFailCount(String.valueOf(failedMap.size()));
		testResult.setPassCount(String.valueOf(passedMap.size()));
		testResult.setTotalCount(String.valueOf(failedMap.size()+passedMap.size()));
		logger.info("testContext.getName()="+testContext.getName());
		initTestCaseInfo();
		
 		ITestClass className = testContext.getAllTestMethods()[0].getTestClass();
		WriteResultUtils utils = new WriteResultUtils();
		utils.writeResultSummary(testResult, getTemplatePath(className.getName()));
	}
	
	public void initTestCaseInfo(){
		for(TestExpect ep:expectedList){
			TestCase testCase = new TestCase();
			testCase.setBussinessDesp(ep.getBussinessDesp());
			testCase.setResult(ep.getResult());
			testCase.setRemark(ep.getExpect()+"\n"+ep.getActual());
			testResult.getItem().add(testCase);
		}
	}
	
	public String getTemplatePath(String className){
		String filePath = "D://excel//";
		String[] packageArray = className.split("\\.");
		String simpleName = packageArray[packageArray.length-1];
		if("com.blemall.smokeTest.testcases.TestCase_01_Login".equalsIgnoreCase(className)){
			filePath += simpleName;
		}
		filePath +="_2015_11_22.xls";
		logger.info("filePath="+filePath);
		return filePath;
	}

	private void takeScreenShot(ITestResult tr) {
		String screenName = String.valueOf(new Date().getTime()) + ".jpg";
		File dir = new File("test-output/snapshot");
		if (!dir.exists())
			dir.mkdirs();
		String screenPath = dir.getAbsolutePath() + "/" + screenName;
		Reporter.setCurrentTestResult(tr);
		Reporter.log(screenPath);
		// 这里实现把图片链接直接输出到结果文件中，通过邮件发�?�结果则可以直接显示图片
		Reporter.log("<img src=\"../" + screenPath + "\"/>");

	}
}