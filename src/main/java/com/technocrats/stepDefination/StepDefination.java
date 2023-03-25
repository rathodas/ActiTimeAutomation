package com.technocrats.stepDefination;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import com.codoid.products.fillo.Connection;
import com.technocrats.Pages.ActiTimeHomePage;
import com.technocrats.Pages.ActiTimeLoginPage;
import com.technocrats.core.CustomAssert;
import com.technocrats.core.ExcelDatabase;

public class StepDefination extends ActiTimeHomePage {


	ExcelDatabase excelDatabase=new ExcelDatabase();
	public StepDefination(WebDriver driver) {
		super(driver);
	}
	
	public void NavigateToActiTime(WebDriver driver,String product, String lob,String testScenarioID,String regressionScenarioID,XSSFWorkbook workbook,Connection conn,  String stepGroup,CustomAssert customAssert) throws Exception {
		ActiTimeHomePage actiTimeHomePage = new ActiTimeHomePage(driver);
		actiTimeHomePage.navigateToActitimeURL(driver, product, lob, testScenarioID, regressionScenarioID, workbook, conn, stepGroup, customAssert);
	}
	
	public void Login(WebDriver driver,String product, String lob,String testScenarioID,String regressionScenarioID,XSSFWorkbook workbook,Connection conn,  String stepGroup,CustomAssert customAssert) throws Exception {
		ActiTimeLoginPage actiTimeLoginPage = new ActiTimeLoginPage(driver);
		actiTimeLoginPage.loginToActiTime(driver, product, lob, testScenarioID, regressionScenarioID, workbook, conn, stepGroup, customAssert);
	}
	
	public void VerifyTitle(WebDriver driver,String product, String lob,String testScenarioID,String regressionScenarioID,XSSFWorkbook workbook,Connection conn,  String stepGroup,CustomAssert customAssert) throws Exception {
		ActiTimeHomePage actiTimeHomePage = new ActiTimeHomePage(driver);
		actiTimeHomePage.verifyTitle(driver, product, lob, testScenarioID, regressionScenarioID, workbook, conn, stepGroup, customAssert);
	}
}

