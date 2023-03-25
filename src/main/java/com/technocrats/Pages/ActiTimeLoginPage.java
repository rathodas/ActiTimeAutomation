package com.technocrats.Pages;

import java.time.Duration;
import java.util.Properties;
import java.util.Scanner;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.codoid.products.fillo.Connection;
import com.technocrats.core.CustomAssert;
import com.technocrats.utils.ExcelRead;
import com.technocrats.utils.GenericMethods;
import com.technocrats.utils.WaitTime;

public class ActiTimeLoginPage extends GenericMethods{

	@FindBy(name="username")
	private WebElement userName;

	@FindBy(name="pwd")
	private WebElement password;

	@FindBy(id="loginButton")
	private WebElement loginButton;

	WebDriverWait wait;

	public ActiTimeLoginPage(WebDriver driver) {
		super();
		PageFactory.initElements(driver, this);
		wait=new WebDriverWait(driver, Duration.ofSeconds(20));
	}
	
	String sheetName="ActiTimeLoginPage";

	public void loginToActiTime(WebDriver driver,String product, String lob,String testScenarioID,String regressionScenarioID,XSSFWorkbook workbook,Connection conn,  String stepGroup,CustomAssert customAssert) throws Exception {

		Properties dataRow = ExcelRead.readRowDataInProperties(workbook, sheetName, testScenarioID, stepGroup);

		clearAndSenKeys(driver, userName, dataRow.getProperty("UserName"), "User Name");
		clearAndSenKeys(driver, password, dataRow.getProperty("Password"), "Password");
		click(driver, loginButton, "Login");
		
		Thread.sleep(WaitTime.low);
	}
	
	


}
