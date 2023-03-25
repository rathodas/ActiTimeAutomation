package com.technocrats.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Reporter;

public class ExcelRead {

	public static Properties readRowDataInProperties(XSSFWorkbook workbook, String sheetName, String testScenarioID,String stepGroup) {
		Properties prop = new Properties();
		try{
			XSSFSheet sheet=workbook.getSheet(sheetName);
			DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale

			Row titleRow = sheet.getRow(0);

			for (int i = 1; i<sheet.getPhysicalNumberOfRows(); i++) {
				Row dataRow = sheet.getRow(i);
				String testCaseID = formatter.formatCellValue(dataRow.getCell(1));
				String executionType=formatter.formatCellValue(dataRow.getCell(2));
				String groupName=formatter.formatCellValue(dataRow.getCell(3));
				if (testCaseID.equalsIgnoreCase(testScenarioID) && stepGroup.equalsIgnoreCase(groupName)) {
					if(stepGroup.equalsIgnoreCase(groupName)){		
						for (int j = 0; j<dataRow.getPhysicalNumberOfCells(); j++) {

							Cell cell =  dataRow.getCell(j); //new code added By Anil on 01/08/2018 to get data from formula based cell in excel
							try {

								switch (cell.getCellType()) {
								case STRING:
								case FORMULA:	
								case BOOLEAN:
									cell.getRichStringCellValue().toString();
									prop.put(formatter.formatCellValue(titleRow.getCell(j)), cell.getStringCellValue());
									break;
								case NUMERIC:
								case BLANK:
									prop.put(formatter.formatCellValue(titleRow.getCell(j)), formatter.formatCellValue(dataRow.getCell(j)));
									break;

								default:
									break;
								}

							}
							catch(NullPointerException e) {
								prop.put(formatter.formatCellValue(titleRow.getCell(j)), "");
							}
						}
					}
				}
			}
		}catch (Exception e){
			Reporter.log(e.toString());
			return null;
		}

		return prop;
	}

	public static void updateValueInExcel(XSSFWorkbook workbook, String sheetName, String testScenarioID,int columnNumnber,String value) {
		XSSFSheet sheet=workbook.getSheet(sheetName);
		DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale
		for (int i = 1; i<sheet.getPhysicalNumberOfRows(); i++) {
			Row dataRow = sheet.getRow(i);
			String testCaseID = formatter.formatCellValue(dataRow.getCell(1));

			if(testCaseID.equalsIgnoreCase(testScenarioID)) {
				XSSFCell cell = sheet.getRow(i).getCell(columnNumnber);
				cell.setCellValue(value);
			}
		}
	}
	public static Properties verifyRowDataInProperties(XSSFWorkbook workbook, String sheetName, String testScenarioID,String stepGroup) {
		Properties prop = new Properties();
		try{
			XSSFSheet sheet=workbook.getSheet(sheetName);
			DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale

			Row titleRow = sheet.getRow(0);
			;
			for (int i = 1; i<sheet.getPhysicalNumberOfRows(); i++) {
				Row dataRow = sheet.getRow(i);
				String testCaseID = formatter.formatCellValue(dataRow.getCell(1));
				String executionType=formatter.formatCellValue(dataRow.getCell(2));
				String groupName=formatter.formatCellValue(dataRow.getCell(3));
				if (testCaseID.equalsIgnoreCase(testScenarioID)) {
					if(executionType.equalsIgnoreCase("Verify")&&groupName.equalsIgnoreCase(stepGroup)){
						for (int j = 0; j<dataRow.getPhysicalNumberOfCells(); j++) {
							prop.put(formatter.formatCellValue(titleRow.getCell(j)), formatter.formatCellValue(dataRow.getCell(j)));
						}
					}
				}
			}
		}catch (Exception e){
			return null;
		}

		return prop;
	}
	public static Properties readRowDataForDatabaseAndPuttyConnection(XSSFWorkbook workbook, String sheetName, String databaseName) {
		Properties prop = new Properties();
		try{
			XSSFSheet sheet=workbook.getSheet(sheetName);
			DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale

			Row titleRow = sheet.getRow(0);

			for (int i = 1; i<sheet.getPhysicalNumberOfRows(); i++) {
				Row dataRow = sheet.getRow(i);
				String testCaseID = formatter.formatCellValue(dataRow.getCell(1));

				if (testCaseID.equalsIgnoreCase(databaseName)) {

					for (int j = 0; j<dataRow.getPhysicalNumberOfCells(); j++) {

						Cell cell =  dataRow.getCell(j); //new code added By Anil on 01/08/2018 to get data from formula based cell in excel
						try {


							switch (cell.getCellType()) {
							case STRING:
							case FORMULA:	
							case BOOLEAN:
								cell.getRichStringCellValue().toString();
								prop.put(formatter.formatCellValue(titleRow.getCell(j)), cell.getStringCellValue());
								break;
							case NUMERIC:
							case BLANK:
								prop.put(formatter.formatCellValue(titleRow.getCell(j)), formatter.formatCellValue(dataRow.getCell(j)));
								break;

							default:
								break;
							}

						}				
						catch(NullPointerException e) {
							prop.put(formatter.formatCellValue(titleRow.getCell(j)), "");
						}
					}

				}
			}
		}catch (Exception e){
			return null;
		}

		return prop;
	}

	public static List<Properties> readRowDataInPropertiesInList(XSSFWorkbook workbook, String sheetName, String testScenarioID,String stepGroup) {
		List<Properties> propList=new ArrayList<>();
		try{
			XSSFSheet sheet=workbook.getSheet(sheetName);
			DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale

			Row titleRow = sheet.getRow(0);

			for (int i = 1; i<sheet.getPhysicalNumberOfRows(); i++) {
				Row dataRow = sheet.getRow(i);
				String testCaseID = formatter.formatCellValue(dataRow.getCell(1));
				//System.out.println("TCID: "+testCaseID);
				String executionType=formatter.formatCellValue(dataRow.getCell(2));
				String groupName=formatter.formatCellValue(dataRow.getCell(3));
				if (testCaseID.equalsIgnoreCase(testScenarioID)&&stepGroup.equalsIgnoreCase(groupName)) {


					Properties prop = new Properties();
					for (int j = 0; j<dataRow.getPhysicalNumberOfCells(); j++) {

						Cell cell =  dataRow.getCell(j); //new code added By Anil on 01/08/2018 to get data from formula based cell in excel
						try {

							switch (cell.getCellType()) {
							case STRING:
							case FORMULA:	
							case BOOLEAN:
								cell.getRichStringCellValue().toString();
								prop.put(formatter.formatCellValue(titleRow.getCell(j)), cell.getStringCellValue());
								break;
							case NUMERIC:
							case BLANK:
								prop.put(formatter.formatCellValue(titleRow.getCell(j)), formatter.formatCellValue(dataRow.getCell(j)));
								break;

							default:
								break;
							}

						}				
						catch(NullPointerException e) {
							prop.put(formatter.formatCellValue(titleRow.getCell(j)), "");
						}
					}

					propList.add(prop);
				}
			}
		}catch (Exception e){
			return null;
		}

		return propList;
	}


}
