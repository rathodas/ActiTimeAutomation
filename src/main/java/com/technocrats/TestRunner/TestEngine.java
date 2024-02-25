package com.technocrats.TestRunner;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import com.technocrats.core.FrameworkServices;
import com.technocrats.core.TestSuiteEngine;
import com.technocrats.utils.WaitTime;
/*
 * 
 * Author
 * Anil S. Rathod
 * 
 */

public class TestEngine {
	public static String excutionFolder="";
	public static Process process=null;
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		try {
			
			FrameworkServices frameworkServices=new FrameworkServices();
			process=Runtime.getRuntime().exec("cmd /c Grid_chrome.bat",null, new File(System.getProperty("user.dir")+FrameworkServices.configProp.getProperty("GridPath")));
			TestNG testNG=new TestNG();
			java.util.Date date=new java.util.Date();
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy__hh-mm-ss");
			String folderDate=simpleDateFormat.format(date);
			excutionFolder="D:\\Actitime_Execution_Repo\\"+folderDate;
			testNG.setOutputDirectory(excutionFolder);
			List<XmlSuite> suiteList=new ArrayList<>();
			suiteList=	new TestSuiteEngine().executeTestSuiteGenerator(frameworkServices, suiteList);
			testNG.setXmlSuites(suiteList);
			testNG.run();
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}

		finally{
			Runtime rt=Runtime.getRuntime();
			rt.exec("taskkill /f /im cmd.exe") ;	
			System.gc();
			System.out.println("GC Called");
		}
	}
}