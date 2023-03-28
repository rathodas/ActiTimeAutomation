package com.technocrats.Pages;

import java.time.Duration;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.codoid.products.fillo.Connection;
import com.technocrats.core.CustomAssert;
import com.technocrats.utils.ExcelRead;
import com.technocrats.utils.WaitTime;

public class ActiTimeTasks extends ActiTimeHomePage{

	@FindBy(linkText = "Tasks")
	private WebElement taskTab;
	
	@FindBy(id = "ext-comp-1016")
	private WebElement createTaskLink;
	
	//Anil added 28/03/2023
	@FindBy(name = "customerId")
	private WebElement Customer;

	@FindBy(name = "projectId")
	private WebElement Project;
	
	@FindBy(name = "task[0].name")
	private WebElement firstTaskName;
	
	@FindBy(xpath = "//*[@id='container']/form[1]/table/tbody/tr/td/table/tbody/tr[5]/td/table/tbody/tr/td/input[1]")
	private WebElement createTaskBtn;

	String sheetName="ActiTimeTasks"; 
	WebDriverWait wait;
	public ActiTimeTasks(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		wait=new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	public void createTask(WebDriver driver,String product, String lob,String testScenarioID,String regressionScenarioID,XSSFWorkbook workbook,Connection conn,  String stepGroup,CustomAssert customAssert) throws Exception {

		Properties dataRow = ExcelRead.readRowDataInProperties(workbook, sheetName, testScenarioID, stepGroup);

		click(driver, taskTab, "Tasks");
		
		Thread.sleep(WaitTime.medium);
		
		click(driver, createTaskLink, "Create Task");
		Thread.sleep(WaitTime.medium);
		
		selectFromDropdownByVisibleText(driver, Customer, dataRow.getProperty("Customer"), "Customer");
		selectFromDropdownByVisibleText(driver, Project, dataRow.getProperty("Project"), "Project");
		clearAndSenKeys(driver, firstTaskName, dataRow.getProperty("FirstTaskName"), "First Task Name ");
		click(driver, createTaskBtn, "Create Tasks");
		Thread.sleep(WaitTime.medium);
	}

	
}
