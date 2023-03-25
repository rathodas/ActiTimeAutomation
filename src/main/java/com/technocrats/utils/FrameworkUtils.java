package com.technocrats.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
/*import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;*/
import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Reporter;

import com.technocrats.core.FrameworkServices;
import com.google.common.io.Files;


public class FrameworkUtils extends FrameworkServices {

	public FrameworkUtils() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public WebDriver launchBrowser(WebDriver driver, String browserName) {

		if(browserName.equalsIgnoreCase("chrome")) {
			String projectPath = System.getProperty("user.dir"); 
			System.setProperty("webdriver.chrome.driver", projectPath+"\\drivers\\chromedriver_win32\\chromedriver.exe");
			ChromeOptions option = new ChromeOptions();
			option.addArguments("--remote-allow-origins=*");
			driver =  new ChromeDriver(option);

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
	
	public static String captureScreenShot(WebDriver driver, String snapshotFolder, String destinationFilePathLocation) throws IOException{
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
	}
	

}
