package com.technocrats.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;

import com.technocrats.core.FrameworkServices;


public class FrameworkUtils extends FrameworkServices {

	public FrameworkUtils() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public WebDriver launchBrowser(WebDriver driver, String browserName) throws Exception {

		if(browserName.equalsIgnoreCase("chrome")) {
		/*	String projectPath = System.getProperty("user.dir"); 
			System.setProperty("webdriver.chrome.driver", projectPath+"\\drivers\\chromedriver_win32\\chromedriver.exe");
			ChromeOptions option = new ChromeOptions();
			option.addArguments("--remote-allow-origins=*");
			driver =  new ChromeDriver(option); */
			
		//	driver =  new ChromeDriver();
			
			ChromeOptions chromeOptions = new ChromeOptions();
			Thread.sleep(WaitTime.low);
			driver=new RemoteWebDriver(new URL(FrameworkServices.configProp.getProperty("GridURL")), chromeOptions);

		}else if(browserName.equalsIgnoreCase("firefox")) {
			String projectPath = System.getProperty("user.dir"); 
			System.setProperty("webdriver.gecko.driver", projectPath+"\\drivers\\geckodriver-v0.31.0-win64\\geckodriver.exe");
			driver =  new FirefoxDriver();

		}else if(browserName.equalsIgnoreCase("ie")) {
			String projectPath = System.getProperty("user.dir"); 
			System.setProperty("webdriver.ie.driver", projectPath+"\\drivers\\IEDriverServer_Win32_4.3.0\\IEDriverServer.exe");
			driver =  new InternetExplorerDriver();

		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver;

	}
	
/*	public static String captureScreenShot(WebDriver driver, String snapshotFolder, String destinationFilePathLocation) throws IOException{
		String fileName = new String();
		//RemoteWebDriver augmentedDriver = (RemoteWebDriver) driver;
		//File screenshot = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		
		File  screenshot = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
	//	FileHandler.copy(srcFile, destFile);
		
		//Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
		fileName = destinationFilePathLocation  + new SimpleDateFormat("dd-MM-yyyy HH-mm-ss").format(new Date())+ ".png";
		File destinationFilePath = new File(snapshotFolder + File.separator + fileName);
		//String destinationFilePath = snapshotFolder + File.separator + fileName;
		FileUtils.copyFile(screenshot, destinationFilePath);
		//ImageIO.write(screenshot.getImage(), "png", new File(destinationFilePath));
		Reporter.log("<br> <a target = \"_blank\" href=\"" + ".."+FrameworkServices.configProp.getProperty("ScreenShotFolder")+"/"+fileName +"\">"+
				"<img src=\""
				+ ".." +FrameworkServices.configProp.getProperty("ScreenShotFolder")+"/"+fileName 
				+ "\" alt=\"ScreenShot Not Available\" height=\"500\" width=\"600\"> </a>");
		System.out.println(destinationFilePath.getAbsolutePath());
		return destinationFilePath.getAbsolutePath();
	} */
	

	public static String captureScreenShot(WebDriver driver, String snapshotFolder, String destinationFilePathLocation) throws Exception{
		String fileName = new String();
		RemoteWebDriver augmentedDriver = (RemoteWebDriver) driver;
		File screenshot = augmentedDriver.getScreenshotAs(OutputType.FILE);
		
		//TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		//File  screenshot = takesScreenshot.getScreenshotAs(OutputType.FILE);
			
		Thread.sleep(WaitTime.low);
		//Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
		fileName = destinationFilePathLocation  + new SimpleDateFormat("dd-MM-yyyy HH-mm-ss").format(new Date())+ ".png";
		File destinationFilePath = new File(snapshotFolder + File.separator + fileName);
		//String destinationFilePath = snapshotFolder + File.separator + fileName;
		FileUtils.copyFile(screenshot, destinationFilePath);
		//ImageIO.write(screenshot.getImage(), "png", new File(destinationFilePath));
		Reporter.log("<br> <a target = \"_blank\" href=\"" + ".."+FrameworkServices.configProp.getProperty("ScreenShotFolder")+"/"+fileName +"\">"+
				"<img src=\""
				+ ".." +FrameworkServices.configProp.getProperty("ScreenShotFolder")+"/"+fileName 
				+ "\" alt=\"ScreenShot Not Available\" height=\"100\" width=\"250\"> </a>");
		//System.out.println(destinationFilePath.getAbsolutePath());
		return destinationFilePath.getAbsolutePath();
	}
	
	
}
