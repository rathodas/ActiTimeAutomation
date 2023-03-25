package com.technocrats.core;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.technocrats.core.Entity.TestScenarioGenerator;
import com.technocrats.core.Entity.TestScriptStepGenerator;
import com.technocrats.core.Entity.TestSuiteGenerator;

public class FrameworkServices {
	public 	static Properties configProp;
	static FileInputStream configInput;
	static Properties masterConfigProp;
	static FileInputStream masterConfigInput;
	static Properties testScenarioConfigProp;
	static FileInputStream testConfigInput;
	public static XSSFWorkbook masterWorkbook;
	static FileInputStream DataRepositoryConfig;
	public FrameworkServices() throws Exception {
		getConfigProperties();
		getMasterConfigProperties();
		getTestConfigProperties();
	}
	public static Properties getConfigProperties() throws Exception {
		if(configProp==null) {
			configProp=new Properties();
			configInput=new FileInputStream("./config.properties");
			configProp.load(configInput);
		}
		return configProp;
	}
	public static Properties getMasterConfigProperties() throws Exception {
		if(masterConfigProp==null) {
			masterConfigProp=new Properties();
			masterConfigInput=new FileInputStream("./MasterConfig.properties");
			masterConfigProp.load(masterConfigInput);
		}
		return masterConfigProp;
	}
	public static Properties getTestConfigProperties() throws Exception {
		if(testScenarioConfigProp==null) {
			testScenarioConfigProp=new Properties();
			testConfigInput=new FileInputStream("./TestScenarioConfig.properties");
			testScenarioConfigProp.load(testConfigInput);
		}
		return testScenarioConfigProp;
	}
	
	public List<TestSuiteGenerator> getTestSuiteForExecution() throws Exception{
		FileInputStream fileInputStream=new FileInputStream(new File(getConfigProperties().getProperty("TestDataFolder")+getConfigProperties().getProperty("MasterTestSuitePath")));
		masterWorkbook=new XSSFWorkbook(fileInputStream);
		XSSFSheet masterSuiteSheet=masterWorkbook.getSheet("MasterTestSuite");
		int numberOfRows=masterSuiteSheet.getLastRowNum();
		int ReferenceIndexForTestSuite=Integer.parseInt(masterConfigProp.getProperty("ReferenceIndex"));
		int LOBNameIndex=Integer.parseInt(masterConfigProp.getProperty("LOBNameIndex"));
		int ProductCodeIndex=Integer.parseInt(masterConfigProp.getProperty("ProductCodeIndex"));
		int TestData_RepositoryFileIndex=Integer.parseInt(masterConfigProp.getProperty("TestData_RepositoryFileIndex"));
		int BrowserNameIndex=Integer.parseInt(masterConfigProp.getProperty("BrowserNameIndex"));
		int ExecuteFlagIndex=Integer.parseInt(masterConfigProp.getProperty("ExecuteFlagIndex"));
		int TestScenario_RepositoryFileIndex=Integer.parseInt(masterConfigProp.getProperty("TestScenario_RepositoryFileIndex"));
		int scenarioIDIndex=Integer.parseInt(masterConfigProp.getProperty("ScenarioIDIndex"));

		List<TestSuiteGenerator> testSuiteGeneratorList=new ArrayList<>();
		for(int i=1;i<=numberOfRows;i++) {
			Row rowData=masterSuiteSheet.getRow(i);
			if(rowData.getCell(ExecuteFlagIndex).getStringCellValue().equalsIgnoreCase("yes")) {
				TestSuiteGenerator testSuiteGenerator=new TestSuiteGenerator();
				testSuiteGenerator.setReference(rowData.getCell(ReferenceIndexForTestSuite).getStringCellValue());
				testSuiteGenerator.setLOBName(rowData.getCell(LOBNameIndex).getStringCellValue());
				testSuiteGenerator.setProductCode(rowData.getCell(ProductCodeIndex).getStringCellValue());
				testSuiteGenerator.setTestData_RepositoryFile(rowData.getCell(TestData_RepositoryFileIndex).getStringCellValue());
				testSuiteGenerator.setBrowserName(rowData.getCell(BrowserNameIndex).getStringCellValue());
				testSuiteGenerator.setExecuteFlag(rowData.getCell(ExecuteFlagIndex).getStringCellValue());
				testSuiteGenerator.setTestScenario_RepositoryFile(rowData.getCell(TestScenario_RepositoryFileIndex).getStringCellValue());
				testSuiteGenerator.setScenarioID(rowData.getCell(scenarioIDIndex).getStringCellValue());
				testSuiteGeneratorList.add(testSuiteGenerator);
			}
		}
		return testSuiteGeneratorList;
	}

	public static List<TestScriptStepGenerator> getScriptStepFromScriptName( String scriptName) throws Exception {
		FileInputStream fileInputStream=new FileInputStream(new File(getConfigProperties().getProperty("TestDataFolder")+getConfigProperties().getProperty("MasterTestSuitePath")));
		masterWorkbook=new XSSFWorkbook(fileInputStream);
		XSSFSheet masterTestScriptStepsheet=masterWorkbook.getSheet("MasterTestScriptStep");
		int numberOfRows=masterTestScriptStepsheet.getLastRowNum();
		int ReferenceIndexForTestStep=Integer.parseInt(masterConfigProp.getProperty("ReferenceIndexForTestStep"));
		int automationScriptName_MasterTestScriptStep_Index=Integer.parseInt(masterConfigProp.getProperty("AutomationScriptName_MasterTestScriptStep_Index"));
		int stepKeywordIndex=Integer.parseInt(masterConfigProp.getProperty("StepKeywordIndex"));
		int stepGroupIndex=Integer.parseInt(masterConfigProp.getProperty("StepGroupIndex"));
		int skipStepIndex=Integer.parseInt(masterConfigProp.getProperty("SkipStepIndex"));
		List<TestScriptStepGenerator> testScriptStepGenerators=new ArrayList<>();
		for(int i=1;i<=numberOfRows;i++) {
			Row rowData=masterTestScriptStepsheet.getRow(i);
			if(rowData.getCell(automationScriptName_MasterTestScriptStep_Index).getStringCellValue().equalsIgnoreCase(scriptName)&&rowData.getCell(skipStepIndex).getStringCellValue().equalsIgnoreCase("No")) {
				TestScriptStepGenerator testScriptStepGenerator=new TestScriptStepGenerator();
				testScriptStepGenerator.setReference(rowData.getCell(ReferenceIndexForTestStep).getStringCellValue());
				testScriptStepGenerator.setAutomationScriptName(rowData.getCell(automationScriptName_MasterTestScriptStep_Index).getStringCellValue());
				testScriptStepGenerator.setStepKeyword(rowData.getCell(stepKeywordIndex).getStringCellValue());
				testScriptStepGenerator.setStepGroup(rowData.getCell(stepGroupIndex).getStringCellValue());
				testScriptStepGenerator.setSkipStep(rowData.getCell(skipStepIndex).getStringCellValue());
				testScriptStepGenerators.add(testScriptStepGenerator);
			}
		}
		return testScriptStepGenerators;
	}



	public static List<TestScenarioGenerator> getRegressionSuiteGenerator(TestSuiteGenerator testSuiteGenerator) throws Exception{
		
		FileInputStream fileInputStream=new FileInputStream(new File(getConfigProperties().getProperty("TestDataFolder")+testSuiteGenerator.getTestScenario_RepositoryFile()));

		masterWorkbook=new XSSFWorkbook(fileInputStream);
		XSSFSheet regressionTestData=masterWorkbook.getSheet("TestScenaios");
		int numberOfRows=regressionTestData.getLastRowNum();
		int SrNo=Integer.parseInt(testScenarioConfigProp.getProperty("SrNoIndex"));
		int ScenarioID=Integer.parseInt(testScenarioConfigProp.getProperty("ScenarioIDIndex"));
		int Module=Integer.parseInt(testScenarioConfigProp.getProperty("ModuleIndex"));
		int ScriptName=Integer.parseInt(testScenarioConfigProp.getProperty("ScriptNameIndex"));
		int Desription=Integer.parseInt(testScenarioConfigProp.getProperty("DesriptionIndex"));
		int Count=Integer.parseInt(testScenarioConfigProp.getProperty("CountIndex"));
		int regressionScenarioID=Integer.parseInt(testScenarioConfigProp.getProperty("RegressionScenarioIDIndex"));
		int Product=Integer.parseInt(testScenarioConfigProp.getProperty("ProductIndex"));
		int ExecuteFlag=Integer.parseInt(testScenarioConfigProp.getProperty("ExecuteFlagIndex"));
		//int userName=Integer.parseInt(testScenarioConfigProp.getProperty("UserNameIndex"));
	//	int password=Integer.parseInt(testScenarioConfigProp.getProperty("PasswordIndex"));
		int Status=Integer.parseInt(testScenarioConfigProp.getProperty("StatusIndex"));
		
		
		String testScenarioIDs = testSuiteGenerator.getScenarioID();
		List<TestScenarioGenerator> regressionTestDataList=new ArrayList<>();
		List tcList=null;
		if(testScenarioIDs!="") {
			try {
				tcList = getTestCasesList(testScenarioIDs);	
			}catch (Exception e) {
				e.printStackTrace();
			}

			for(int i=1;i<=numberOfRows;i++) {
				Row rowData=regressionTestData.getRow(i);
				if(getTestCase(tcList,rowData.getCell(regressionScenarioID).getStringCellValue())) {
					TestScenarioGenerator regressionTestDataGenerator=new TestScenarioGenerator();
					regressionTestDataGenerator.setSrNo((rowData.getCell(SrNo).getStringCellValue()));
					regressionTestDataGenerator.setScenarioID((rowData.getCell(ScenarioID).getStringCellValue()));
					regressionTestDataGenerator.setModule((rowData.getCell(Module).getStringCellValue()));
					regressionTestDataGenerator.setScriptName((rowData.getCell(ScriptName).getStringCellValue()));
					regressionTestDataGenerator.setDesription((rowData.getCell(Desription).getStringCellValue()));
					regressionTestDataGenerator.setCount((rowData.getCell(Count).getStringCellValue()));
					regressionTestDataGenerator.setRegressionScenarioID((rowData.getCell(regressionScenarioID).getStringCellValue()));
					regressionTestDataGenerator.setProduct((rowData.getCell(Product).getStringCellValue()));
					//regressionTestDataGenerator.setUserName((rowData.getCell(userName).getStringCellValue()));
					//regressionTestDataGenerator.setPassword((rowData.getCell(password).getStringCellValue()));
					regressionTestDataGenerator.setStatus((rowData.getCell(Status).getStringCellValue()));
					regressionTestDataList.add(regressionTestDataGenerator);
				}
			}
		}else {
			for(int i=1;i<=numberOfRows;i++) {
				Row rowData=regressionTestData.getRow(i);
				if(rowData.getCell(ExecuteFlag).getStringCellValue().equalsIgnoreCase("yes")) {
					TestScenarioGenerator regressionTestDataGenerator=new TestScenarioGenerator();
					regressionTestDataGenerator.setSrNo((rowData.getCell(SrNo).getStringCellValue()));
					regressionTestDataGenerator.setScenarioID((rowData.getCell(ScenarioID).getStringCellValue()));
					regressionTestDataGenerator.setModule((rowData.getCell(Module).getStringCellValue()));
					regressionTestDataGenerator.setScriptName((rowData.getCell(ScriptName).getStringCellValue()));
					regressionTestDataGenerator.setDesription((rowData.getCell(Desription).getStringCellValue()));
					regressionTestDataGenerator.setCount((rowData.getCell(Count).getStringCellValue()));
					regressionTestDataGenerator.setRegressionScenarioID((rowData.getCell(regressionScenarioID).getStringCellValue()));
					regressionTestDataGenerator.setProduct((rowData.getCell(Product).getStringCellValue()));
					//regressionTestDataGenerator.setUserName((rowData.getCell(userName).getStringCellValue()));
					//regressionTestDataGenerator.setPassword((rowData.getCell(password).getStringCellValue()));
					regressionTestDataGenerator.setStatus((rowData.getCell(Status).getStringCellValue()));
					regressionTestDataList.add(regressionTestDataGenerator);
				}
			}	
		}


		return regressionTestDataList;
	}

	public static List<String> getTestCasesList(String str) throws Exception {
		List<String> lstTest = new ArrayList<String>();

		// to get List of single TestCaseData number
		String str1 [] = str.split(",");
		for(int i = 0; i <str1.length;i++){
			lstTest.add(str1[i]);
			//System.out.println(str1[i]);

			// separating by '-' for range of TestCaseData number
			if(str1[i].contains("-")){

				// removing range data from list ex. TCD_2-TCD_9
				lstTest.remove(str1[i]);

				// Splitting range 
				String str2[]=str1[i].split("-");
				String str3[]=str2[0].split("_");
				String str4[]=str2[1].split("_");

				// first Number of range
				int firstNumber = Integer.parseInt(str3[1]);
				//System.out.println(firstNumber);

				// second Number of range
				int lastNumber = Integer.parseInt(str4[1]);
				//System.out.println(lastNumber);

				// adding TestCaseData number to list
				for(int l = firstNumber; l<=lastNumber;l++){
					if(l<=9) {
						lstTest.add("TC_0"+l);
					}else {
						lstTest.add("TC_"+l);
					}

				}
			}
		}

		return lstTest;
	}
	public static boolean getTestCase(List tcList, String scenarioID) {
		boolean status = false;
		for(int i=0;i<tcList.size();i++) {
			if(tcList.get(i).equals(scenarioID)) {
				status=true;
				break;
			}
		}
		return status;
	}

}
