package com.technocrats.utils;

import java.time.Duration;
import java.util.Random;
import java.util.Set;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;


public class GenericMethods {

	protected WebDriver driver;
	WebDriverWait wait;
	public GenericMethods() {

	}
	public GenericMethods(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait=new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	public void clickWithJavaScript(WebDriver driver, WebElement webElement, String webElementName) {
		highlighter( webElement,driver);
		((JavascriptExecutor)driver).executeScript("arguments[0].click()",webElement);
		Reporter.log("Clicked on <B>"+ webElementName +"</B> ");
	}

	public void click(WebDriver driver, WebElement webElement, String webElementName) throws InterruptedException {
		highlighter(webElement, driver);	
		webElement.click();
		Reporter.log("Clicked on <B>"+ webElementName +"</B> ");
	}

	

	public void clearAndSenKeys(WebDriver driver, WebElement webElement,String data, String fieldName) {
		highlighter(webElement, driver);
		webElement.clear();
		webElement.sendKeys(data);
		Reporter.log("<B>"+ data +"</B> is entered in  "+fieldName+" text field");
	}

	
	public void selectFromDropdownByVisibleText(WebDriver driver, WebElement webElement,String visibleText, String fieldname) {
		highlighter(webElement, driver);
		Select selectVisibleText = new Select(webElement);
		selectVisibleText.selectByVisibleText(visibleText);
		Reporter.log("<B>"+ visibleText +"</B> is selected from "+ fieldname);
	}

	public void selectFromDropdownById(WebDriver driver, WebElement webElement, int index, String fieldname) {
		highlighter(webElement, driver);
		Select selectindex = new Select(webElement);
		selectindex.selectByIndex(index);
		Reporter.log("index <B>"+ index +"</B> is selected from "+ fieldname);
	}

	public void selectFromDropdownByValue(WebDriver driver, WebElement webElement, String value, String fieldname) {
		highlighter(webElement, driver);
		Select selectvalue = new Select(webElement);
		selectvalue.selectByValue(value);
		Reporter.log("<B>"+ value +"</B> is selected from "+ fieldname);
	}

	public void selectCheckBox(WebDriver driver, WebElement webElement, String checkBoxName) {
		highlighter(webElement, driver);
		if(!webElement.isSelected()) {
			webElement.click();
			Reporter.log("<B>"+ checkBoxName +"</B> is checked");
		}
	}

	public void switchToWindow(WebDriver driver) {
		String parentWindow = driver.getWindowHandle();
		Set<String> multiWindows = driver.getWindowHandles();
		for(String winHandles : multiWindows) {
			if(!winHandles.equalsIgnoreCase(parentWindow)){
				driver.switchTo().window(winHandles);
				driver.manage().window().maximize();
				Reporter.log("Switched to <B>"+driver.getTitle()+"</B> window");
				//System.out.println(driver.getCurrentUrl());
			}
		}
	}

	public void selectRadioButton(WebDriver driver, WebElement webElement, String radioButtonName) {
		highlighter(webElement, driver);
		if(!webElement.isSelected()) {
			webElement.click();
			Reporter.log("<B>"+radioButtonName+"</B> is selected");
		}
	}

	public void highlighter(WebElement webElement, WebDriver driver) {
		((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid red'",webElement);
	}


	


	public String fetchTextFromApplication(WebDriver driver, WebElement element, String fieldName) {

		highlighter(element, driver);
		String data = element.getText().trim();
		Reporter.log(fieldName+": <B>"+data+" </B> fetched from "+fieldName);
		return data;
	}

	
	public void switchtoframe(WebDriver driver,Integer index) {
		driver.switchTo().frame(index);
		Reporter.log("Switch to frame()");
	}

	public void switchtoframe(WebDriver driver,String name) {
		driver.switchTo().frame(name);
		Reporter.log("Switch to frame("+name+")");
	}


	public void switchtodefaultframe(WebDriver driver) {
		driver.switchTo().defaultContent();
	}

	
	public void switchtoframe(WebDriver driver, WebElement iFrame, String Framename) {
		driver.switchTo().frame(iFrame);
		Reporter.log("switchtoframe("+Framename+")");
	}

	
	public String fetchTextFromEditBox(WebDriver driver, WebElement element, String fieldName) {
		highlighter(element, driver);
		String data = element.getAttribute("value").trim();
		Reporter.log(fieldName+": <B>"+data+" </B> fetched from "+fieldName);
		return data;
	}

	
	public String randomNumberGenerator() {
		/*String randomNumber = "";
		Random random = new Random();
		int num = 100000 + (int) (random.nextFloat() * 89990000);
		randomNumber = randomNumber + String.valueOf(num);
		return randomNumber;*/
		//ABCDEFGHIJKLMNPQRSTUVWXYZ123456789123456789     123456789123456789
		String str = "1234567892345678912345678923456789";
		StringBuilder stringBuilder = new StringBuilder();
		Random random = new Random();
		while(stringBuilder.length()<8) {
			int index = (int) (random.nextFloat() * str.length());
			stringBuilder.append(str.charAt(index));
		}
		String randomNo = stringBuilder.toString();
		return randomNo;
	}
	
	public void selectCheckBoxWithJavaScript(WebDriver driver, WebElement webElement, String checkBoxName) {
		highlighter(webElement, driver);
		if(!webElement.isSelected()) {
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", webElement);
			Reporter.log("<B>"+ checkBoxName +"</B> is checked");
		}
	}
	
	public void quitBrowser(WebDriver driver) throws InterruptedException {
		driver.quit();
		Thread.sleep(WaitTime.veryLow);
	}
	
	public String randomNameGenerator() {
		int rsnum = 5; 
		String str = "ABCDEFGHIJKLMNPQRSTUVWXYZ";
		int loopCount = rsnum + (int)(Math.random() * ((10 - rsnum) + 1));
		StringBuilder stringBuilder = new StringBuilder();
		Random random = new Random();
		while(stringBuilder.length()<loopCount) {
			int index = (int) (random.nextFloat() * str.length());
			stringBuilder.append(str.charAt(index));
		}
		String randomName = stringBuilder.toString();
		return randomName;
	}	

	
}
