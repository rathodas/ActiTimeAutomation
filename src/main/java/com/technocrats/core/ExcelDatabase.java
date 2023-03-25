package com.technocrats.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Reporter;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ExcelDatabase {

	public static HashSet<String> sheetsname=new HashSet<String>();
	static Fillo fillo=new Fillo();
	public static Connection conn;
	public static Connection Connection_excel(String workbookname) throws FilloException {
		conn=fillo.getConnection(workbookname);
		return conn;
	}

	public String selectQueryForData(Connection conn, String sheetName, String columnname, String testScenarioID,String childkey ) throws FilloException {

		String result=null;
		String strQuery="Select "+columnname+" from "+sheetName+" where TCID='"+testScenarioID+"' and ReferenceKey='"+childkey+"'";
		Recordset record=conn.executeQuery(strQuery);		
		while(record.next()) {
			result=record.getField(columnname);
		}
		record.close();
		return result;
	}

	public void updateQueryForData( Connection conn, String sheetName, String columnname, String testScenarioID,String stepGroup, String value) throws FilloException {

		String strQuery="Update "+sheetName+" set "+columnname+"='"+value+"'  where TCID='"+testScenarioID+"' and GroupName='"+stepGroup+"'";
		conn.executeUpdate(strQuery);
		sheetsname.add(sheetName);
	}

	public void insert(String sheetName,String columnname,String value) throws FilloException {

		String strQuery="Insert into "+sheetName+"("+columnname+") values('"+value+"');";
		conn.executeQuery(strQuery);
	}

	public static void close_connection(Connection conn) {
		conn.close();
	}

	public void updateStatus( Connection conn, String sheetName, String columnname, String testScenarioID) throws FilloException {
		String strQuery="Update "+sheetName+" set "+columnname+"='Failed'  where TCID='"+testScenarioID;
		conn.executeUpdate(strQuery);
	}

	public static void updateBorders(String filepath) {	
		try{	
			FileInputStream readFile=new FileInputStream(new File(filepath));
			@SuppressWarnings("resource")
			XSSFWorkbook xssfWorkbook=new XSSFWorkbook(readFile);
			if(xssfWorkbook.getNumberOfSheets()!=0){
				for(int i=0;i<xssfWorkbook.getNumberOfSheets();i++){		
					for(String sheetName: sheetsname){
						if(xssfWorkbook.getSheetName(i).equals(sheetName)){
							XSSFSheet xssfSheet=xssfWorkbook.getSheet(sheetName);
							int numberOfRows=xssfSheet.getLastRowNum();
							for(int j=1;j<=numberOfRows;j++) {
								Row rowData=xssfSheet.getRow(j);
								for (Cell cell : rowData) {				
									XSSFCellStyle style=xssfWorkbook.createCellStyle();
									style.setBorderTop(BorderStyle.THIN);
									style.setBorderBottom(BorderStyle.THIN);
									style.setBorderLeft(BorderStyle.THIN);
									style.setBorderRight(BorderStyle.THIN);
									cell.setCellStyle(style);
								}
							}
						}

					}
				}
			}else{
				Reporter.log("Sheet not Found");

			}
			FileOutputStream fileOutputStream=new FileOutputStream(new File(filepath));
			xssfWorkbook.write(fileOutputStream);
			fileOutputStream.close();
		}
		catch(Exception e){
			Reporter.log(e.toString());
		}
	}
	

	public void updateQueryForCalculationData( Connection conn, String sheetName, String columnname, String testScenarioID,String ProposalNumber, String value) throws FilloException {

		String strQuery="Update "+sheetName+" set "+columnname+"='"+value+"'  where ScenarioID='"+testScenarioID+"' and ProposalNumber='"+ProposalNumber+"'";
		conn.executeUpdate(strQuery);
		sheetsname.add(sheetName);
	}

	public String selectQueryForCalculationData(Connection conn, String sheetName, String columnname, String testScenarioID,String ProposalNumber ) throws FilloException {

		String result=null;
		String strQuery="Select "+columnname+" from "+sheetName+" where ScenarioID='"+testScenarioID+"' and ProposalNumber='"+ProposalNumber+"'";
		Recordset record=conn.executeQuery(strQuery);		
		while(record.next()) {
			result=record.getField(columnname);
		}
		record.close();
		return result;
	}

}