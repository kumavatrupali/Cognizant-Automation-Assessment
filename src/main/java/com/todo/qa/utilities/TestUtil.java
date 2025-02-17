package com.todo.qa.utilities;

import com.todo.qa.base.TestBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class TestUtil extends TestBase{
	

	public static long PAGE_LOAD_TIMEOUT = 20;
	public static long IMPLICIT_WAIT = 20;

	public static String TESTDATA_SHEET_PATH = System.getProperty("user.dir")+ "/src/main/java/com/todo/qa/config/TasklistData.xlsx";

	static Workbook book;
	static Sheet sheet;
	
	
	public static List<String> getTaskDataFromExcel(String sheetName) throws InvalidFormatException {
		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
			//System.out.println("test1");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			//System.out.println("test11");
		}
		try {
			book = WorkbookFactory.create(file);
			//System.out.println("test2");

		} catch (IOException e) {
			e.printStackTrace();
		//	System.out.println("test22");

		}
		sheet = book.getSheet(sheetName);
		List<String> taskData = new ArrayList<>();
		//System.out.println("test3");

		for(Row row :sheet)
		{		//	System.out.println("test4");

			Cell cell = row.getCell(0);
			System.out.println(cell.getStringCellValue());

			if(cell != null)
			{
				//System.out.println("test6");

				taskData.add(cell.getStringCellValue().trim());
				//System.out.println(taskData);
				//System.out.println("test7");

			}
			
		}
		return taskData;
	}

	public static void takeScreenshotAtEndOfTest() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
	}
}
