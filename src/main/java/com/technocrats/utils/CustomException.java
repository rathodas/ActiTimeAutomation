package com.technocrats.utils;


import org.testng.Reporter;

import com.technocrats.core.CustomAssert;

@SuppressWarnings("serial")
public class CustomException extends Exception{
	public CustomException(String message) {
		super(message);
		Reporter.log(message);
		CustomAssert.executionFlag=false;

	}

	public CustomException(String message,Exception err) {
		super(message,err);
		//Reporter.log(message);
	}
}
