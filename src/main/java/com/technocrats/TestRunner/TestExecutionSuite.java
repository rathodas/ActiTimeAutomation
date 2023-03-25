package com.technocrats.TestRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.technocrats.core.CustomAssert;
import com.technocrats.core.ExcelDatabase;
import com.technocrats.core.FrameworkServices;
import com.technocrats.core.Entity.TestScriptStepGenerator;
import com.technocrats.stepDefination.StepKeyword;
import com.technocrats.utils.FrameworkUtils;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class TestExecutionSuite{

	public XSSFWorkbook workbook = null;
	public Connection connOfTestData = null;
	public String ScenarioID = "";
	public String executionStatus = "";
	String filePath="";
	String regressionFilePath="";
	String CalculationFilePath="";
	String SheetName="";

	public static WebDriver driver = null;
	public static int ingeniumLoginCounter=0;
	@Parameters({"ScenarioID", "Module", "LoBName","Product","Description", "ScriptReference", "BrowserName","regressionScenarioID", "TestScenario_RepositoryFileIndex", "TestDataRepository" })
	@Test(testName = "regressionScenarioID")

	public void regressionSuite(String testScenario_Id, String module, String lob, String product, String description,String scriptReference, String BrowserName, String regressionScenarioID,String TestScenario_RepositoryFileIndex,String testDataRepository)throws Exception{
		FrameworkServices frameworkServices=new FrameworkServices();

		filePath=FrameworkServices.getConfigProperties().getProperty("TestDataFolder").concat(testDataRepository);

		FileInputStream fileInputMasterStream = new FileInputStream(new File(FrameworkServices.getConfigProperties().getProperty("TestDataFolder")+ FrameworkServices.getConfigProperties().getProperty("MasterTestSuitePath")));
		FrameworkServices.masterWorkbook = new XSSFWorkbook(fileInputMasterStream);

		FrameworkUtils frameworkUtils = new FrameworkUtils();
		Fillo fillo = new Fillo();
		connOfTestData = fillo.getConnection(filePath);
		regressionFilePath = FrameworkServices.getConfigProperties().getProperty("TestDataFolder").concat(TestScenario_RepositoryFileIndex);
	//	updateRegressionDataToMasterData(connOfTestData, regressionFilePath, testScenario_Id, regressionScenarioID);
		Thread.sleep(2000);


		FileInputStream fileInputStream = new FileInputStream(new File(filePath));
		workbook = new XSSFWorkbook(fileInputStream);

		try {

			driver = frameworkUtils.launchBrowser(driver, BrowserName);

			CustomAssert customAssert = new CustomAssert(driver);
			CustomAssert.executionFlag = true;

			ingeniumLoginCounter=1;
			StepKeyword keyword = new StepKeyword(driver);
			for (TestScriptStepGenerator testScriptStepGenerator : FrameworkServices.getScriptStepFromScriptName(scriptReference)) {
				Reporter.log("<B><I><Font color=\"BLUE\"><U> Step   ==>     "+ testScriptStepGenerator.getStepKeyword() + " </U> ===></Font></I></B>");
				keyword.executeTestStep(driver, product, lob, testScenario_Id, regressionScenarioID,testScriptStepGenerator.getStepKeyword(),testScriptStepGenerator.getStepGroup(), workbook,connOfTestData,customAssert);
			}

			if (CustomAssert.executionFlag) {
				Reporter.log("Test Scenario has been passed");

				updateExecutionStatus(regressionFilePath, testScenario_Id, regressionScenarioID, "Passed");


			} else {
				updateExecutionStatus(regressionFilePath, testScenario_Id, regressionScenarioID, "Failed");
				throw new AssertionError();
			}
		} catch (Exception e) {


			updateExecutionStatus( regressionFilePath, testScenario_Id, regressionScenarioID, "Failed");

			FrameworkUtils.captureScreenShot(driver, TestEngine.excutionFolder+FrameworkServices.configProp.getProperty("ScreenShotFolder"),testScenario_Id);
			e.printStackTrace();
			Reporter.log(e.toString());
			if(e.getMessage().equals(FrameworkServices.configProp.getProperty("CustomExceptionMessage")) && CustomAssert.executionFlag){
				Assert.assertEquals(true, true);
			}
			else{
				Reporter.log(e.getCause().getMessage());
				Assert.assertEquals(true,false);
			}
			driver.quit();
			workbook.close();
			connOfTestData.close();

		} finally {
			if(driver!=null) {
				driver.quit();	
			}
			if(workbook!=null) {
				workbook.close();
			}
			if(connOfTestData!=null) {
				connOfTestData.close();
			}
			/*
			 * 
			 * driver=null; workbook=null; connOfTestData=null;
			 */


		}
	}

	@AfterTest
	public void tearDown() {
		ExcelDatabase.updateBorders(regressionFilePath);
		ExcelDatabase.updateBorders(filePath);
		System.gc();
		//Mimimum acceptable free memory you think your app needs
		long minRunningMemory = (1024*1024);

		Runtime runtime = Runtime.getRuntime();

		if(runtime.freeMemory()<minRunningMemory)
			System.gc();
	}
	synchronized void updateExecutionStatus(String regressionTestData, String scenarioId,String regressionScenarioID, String Status) throws FilloException, IOException {
		try {
			Fillo fillo = new Fillo();
			ExcelDatabase excelDatabase = new ExcelDatabase();
			Connection connOfregressionTestData = fillo.getConnection(regressionTestData);
			String updateMigrationIntoTestData = "Update TestScenaios Set Status='"+ Status+"' Where RegressionScenarioID='"+regressionScenarioID+"'";
			excelDatabase.sheetsname.add("regressionTestData");
			connOfregressionTestData.executeUpdate(updateMigrationIntoTestData);

			connOfregressionTestData.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	synchronized  void updateRegressionDataToMasterData(Connection connForTestData, String regressionTestData, String scenarioId,String regressionScenarioID) throws FilloException {
		Connection connOfregressionTestData = null;
		try{
			Fillo fillo = new Fillo();
			if(connOfregressionTestData==null) {
				connOfregressionTestData = fillo.getConnection(regressionTestData);
			}


			String userName = "";
			String password = "";


			String fetchRegresstionData = "select * from TestScenaios where RegressionScenarioID='"+regressionScenarioID+"'";
			Recordset recordsetRegression = connOfregressionTestData.executeQuery(fetchRegresstionData);
			while (recordsetRegression.next()) {

				userName = recordsetRegression.getField("UserName");
				password = recordsetRegression.getField("Password");

			}
			String updateMigrationIntoTestData = "Update MasterTestData set UserName='"+userName+"',Password='"+password+"' where TCID='"+scenarioId +"'";
			connForTestData.executeUpdate(updateMigrationIntoTestData);
			connOfregressionTestData.close();
			recordsetRegression.close();
			ExcelDatabase excelDatabase = new ExcelDatabase();
			excelDatabase.sheetsname.add("MASTERTESTDATA");
		}catch(Exception e){
			System.out.println(e);
			Reporter.log(e.getMessage());
			if(connOfregressionTestData!=null) {
				connOfregressionTestData.close();
			}
		}
		finally {
			if(connOfregressionTestData!=null) {
				connOfregressionTestData.close();
			}
			connOfregressionTestData=null;
		}

	}

}
