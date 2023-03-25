package com.technocrats.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;
import com.technocrats.core.Entity.TestScenarioGenerator;
import com.technocrats.core.Entity.TestSuiteGenerator;


public class TestSuiteEngine {
	@SuppressWarnings("static-access")
	public List<XmlSuite> executeTestSuiteGenerator(FrameworkServices frameworkServices,List<XmlSuite> suiteList) throws Exception {

		for(TestSuiteGenerator testSuiteGenerator:frameworkServices.getTestSuiteForExecution()) {
			XmlSuite suite=new XmlSuite();	
			suite.setName(testSuiteGenerator.getLOBName()+"_"+testSuiteGenerator.getProductCode());
			suite.setThreadCount(3);
			//suite.setParallel(ParallelMode.TESTS);
			Map<String, String> testSuiteParams=new HashMap<>();
			testSuiteParams.put("LoBName",testSuiteGenerator.getLOBName());
			testSuiteParams.put("Product", testSuiteGenerator.getProductCode());
			testSuiteParams.put("DataRepo", testSuiteGenerator.getTestData_RepositoryFile());
			testSuiteParams.put("BrowserName", testSuiteGenerator.getBrowserName());
			testSuiteParams.put("TestScenario_RepositoryFileIndex", testSuiteGenerator.getTestScenario_RepositoryFile());
			testSuiteParams.put("TestDataRepository", testSuiteGenerator.getTestData_RepositoryFile());
			
			suite.setParameters(testSuiteParams);
			for(TestScenarioGenerator migrationScenarioGenerator:frameworkServices.getRegressionSuiteGenerator(testSuiteGenerator)) {
					XmlTest test=new XmlTest(suite);
					test.setName(migrationScenarioGenerator.getModule()+"_"+migrationScenarioGenerator.getRegressionScenarioID());
					test.setVerbose(2);
					Map<String, String> testCaseParams=new HashMap<>();
					testCaseParams.put("ScenarioID", migrationScenarioGenerator.getScenarioID());
					testCaseParams.put("Module", migrationScenarioGenerator.getModule());
					testCaseParams.put("Description", migrationScenarioGenerator.getDesription());
					testCaseParams.put("Product",migrationScenarioGenerator.getProduct());
					testCaseParams.put("ScriptReference", migrationScenarioGenerator.getScriptName());
					
					testCaseParams.put("regressionScenarioID",migrationScenarioGenerator.getRegressionScenarioID());
					test.setParameters(testCaseParams);
					List<XmlClass> classes=new ArrayList<>();
					classes.add(new XmlClass("com.technocrats.TestRunner.TestExecutionSuite"));
					test.setClasses(classes);
				}
			
			suiteList.add(suite);
		}
		return suiteList;
	}
}




