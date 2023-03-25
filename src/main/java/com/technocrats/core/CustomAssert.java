package com.technocrats.core;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;

import com.technocrats.TestRunner.TestEngine;
import com.technocrats.utils.FrameworkUtils;
import com.technocrats.utils.GenericMethods;

public class CustomAssert extends GenericMethods {
	WebDriver driver=null;
	public static boolean executionFlag=true;
	public CustomAssert(WebDriver driver) {
		super(driver);
		this.driver=driver;
	}
	public  int counter=1;
	public void verifyAssert(String expected,String actual, String message) throws IOException {
		try {
			Assert.assertEquals(expected, actual);
			Reporter.log("<B><Font color=\"DarkOrange\">"+counter+".   "+message+"       => PASSED</Font></B>");
			Reporter.log("<B>Expected =  "+expected+"</Font></B>");
			Reporter.log("<B>Actual =  "+actual+"</Font></B>");
		}catch(AssertionError assertionError){
			executionFlag=false;
			Reporter.log("<B><Font color=\"DarkOrange\">"+counter+".   "+message+"       => FAILED</Font></B>");
			Reporter.log("<B>Expected =  "+expected+"</Font></B>");
			Reporter.log("<B>Actual =  "+actual+"</Font></B>");
			FrameworkUtils.captureScreenShot(driver, TestEngine.excutionFolder+FrameworkServices.configProp.getProperty("ScreenShotFolder"), new SimpleDateFormat("dd-MM-yyyy HH-mm-ss").format(new Date()) );
		}finally {
			counter++;
		}
	}
	public void verifyAssert(Boolean expected, Boolean actual, String message) throws IOException {
		try {
			Assert.assertEquals(expected, actual);
			Reporter.log("<B><Font color=\"DarkOrange\">"+counter+".   "+message+"       => PASSED</Font></B>");
			Reporter.log("<B>Expected =  "+expected+"</Font></B>");
			Reporter.log("<B>Actual =  "+actual+"</Font></B>");
		}catch(AssertionError assertionError){
			executionFlag=false;
			Reporter.log("<B><Font color=\"DarkOrange\">"+counter+".   "+message+"       => FAILED</Font></B>");
			Reporter.log("<B>Expected =  "+expected+"</Font></B>");
			Reporter.log("<B>Actual =  "+actual+"</Font></B>");
			FrameworkUtils.captureScreenShot(driver, TestEngine.excutionFolder+FrameworkServices.configProp.getProperty("ScreenShotFolder"), new SimpleDateFormat("dd-MM-yyyy HH-mm-ss").format(new Date()));
		}finally {
			counter++;
		}
	}
	
	
}
