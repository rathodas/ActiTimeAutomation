package com.technocrats.Pages;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.codoid.products.fillo.Connection;
import com.technocrats.TestRunner.TestEngine;
import com.technocrats.core.CustomAssert;
import com.technocrats.core.FrameworkServices;
import com.technocrats.utils.ExcelRead;
import com.technocrats.utils.FrameworkUtils;
import com.technocrats.utils.GenericMethods;
import com.technocrats.utils.WaitTime;

public class ActiTimeHomePage extends GenericMethods{

	WebDriverWait wait;
	public ActiTimeHomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		wait=new WebDriverWait(driver, Duration.ofSeconds(20));
	}
	
	String sheetName="ActiTimeHomePage"; 
	public void navigateToActitimeURL(WebDriver driver,String product, String lob,String testScenarioID,String regressionScenarioID,XSSFWorkbook workbook,Connection conn,  String stepGroup,CustomAssert customAssert) throws Exception {
		String url = FrameworkServices.getConfigProperties().getProperty("URL");
		driver.navigate().to(url);
		Reporter.log("Navigated to "+url);
	}
	
	public void verifyTitle(WebDriver driver,String product, String lob,String testScenarioID,String regressionScenarioID,XSSFWorkbook workbook,Connection conn,  String stepGroup,CustomAssert customAssert) throws Exception {
		Properties dataRow = ExcelRead.readRowDataInProperties(workbook, sheetName, testScenarioID, stepGroup);
		
		customAssert.verifyAssert(dataRow.getProperty("Title"),driver.getTitle(), "Verify Title");
		FrameworkUtils.captureScreenShot(driver, TestEngine.excutionFolder+FrameworkServices.configProp.getProperty("ScreenShotFolder"), new SimpleDateFormat("dd-MM-yyyy HH-mm-ss").format(new Date()) );
		Thread.sleep(WaitTime.low);
	}
	
}
