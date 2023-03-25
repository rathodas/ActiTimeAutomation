package com.technocrats.stepDefination;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import com.technocrats.core.CustomAssert;
import com.codoid.products.fillo.Connection;

public class StepKeyword extends StepDefination {
	public StepKeyword(WebDriver driver) {
		super(driver);
	}

	public void executeTestStep(WebDriver driver,String product,String lob, String testScenarioID, String regressionScenarioID, String step,String stepGroup,XSSFWorkbook workbook,Connection conn,CustomAssert customAssert) throws Exception {
		switch (step){
		case "NavigateToActiTime":
			NavigateToActiTime(driver, product,lob, testScenarioID, regressionScenarioID, workbook, conn,  stepGroup,customAssert);
			break;
		case "Login":
			Login(driver, product,lob, testScenarioID, regressionScenarioID, workbook, conn,  stepGroup,customAssert);
			break;
			
		case "VerifyTitle":
			VerifyTitle(driver, product,lob, testScenarioID, regressionScenarioID, workbook, conn,  stepGroup,customAssert);
			break;

		}
	}
}