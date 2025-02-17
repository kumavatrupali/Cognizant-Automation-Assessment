package com.todo.qa.testcases;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.todo.qa.base.TestBase;
import com.todo.qa.pages.HomePage;
import com.todo.qa.utilities.TestUtil;

public class HomePageTest extends TestBase {
	
	HomePage homePage;
	String sheetName = "TaskList";
	String sheetNameCompleted = "Completed";
	String taskTobeEdited = "Go to Office";
	String editTaskAs = "Meeting with Product Owner";
	String taskTobeDeleted = "Meeting with Client";
	String markTaskAsCompleted = "Make Report11";

	
	public HomePageTest(){
		super();
	}

	@BeforeMethod
	public void setUp(){
		initialization();
		homePage = new HomePage();	
	}
	
	@Test(priority=1)
	public void HomePageTitleTest(){
		String title = homePage.validateHomePageTitle();
		System.out.println("pagename is" +title);
		Assert.assertEquals("TodoMVC: React", title,"Invalid Title, Homepage Title is not correct");
		}
	
	@Test(priority=1)
	public void todoboxDisplayedTest(){
		Assert.assertTrue(homePage.todoboxDisplayed());
	
	}
	
		
	@Test(priority=1)
	public void createToDoListTest() throws InvalidFormatException{
		List<String> taskList = TestUtil.getTaskDataFromExcel(sheetName);
		
		homePage.createTodoList(taskList);
	}
	@Test(priority=1)
	public void verifyToDoListTest() throws InvalidFormatException{
		List<String> taskList = TestUtil.getTaskDataFromExcel(sheetName);
		
		homePage.createTodoList(taskList);
		List <String> taskAddedToList = homePage.validateAddedTask();
		Assert.assertEquals(taskList,taskAddedToList,"Task list is not correct");
	}
	
	@Test(priority=1)
	public void editToDoListTest() throws InvalidFormatException, InterruptedException{
		List<String> taskList = TestUtil.getTaskDataFromExcel(sheetName);
		homePage.createTodoList(taskList);
		List <String> taskAddedToList = homePage.validateAddedTask();
		homePage.editTodoTask(taskTobeEdited,editTaskAs);
	}
	
	@Test(priority=1)
	public void deleteToDotaskTest() throws InvalidFormatException, InterruptedException{
		List<String> taskList = TestUtil.getTaskDataFromExcel(sheetName);
		homePage.createTodoList(taskList);
		List <String> taskAddedToList = homePage.validateAddedTask();
		homePage.deleteTodoTask(taskTobeDeleted);
	}
	
	@Test(priority=1)
	public void markTaskAsCompletedTest() throws InvalidFormatException, InterruptedException{
		List<String> taskList = TestUtil.getTaskDataFromExcel(sheetName);
		homePage.createTodoList(taskList);
		List<String> completedTaskList = TestUtil.getTaskDataFromExcel(sheetNameCompleted);
		homePage.markTaskAsCompleted(completedTaskList);
	}
	
	@Test(priority=1)
	public void verifyTaskMarkedAsCompletedTest() throws InvalidFormatException, InterruptedException{
		List<String> taskList = TestUtil.getTaskDataFromExcel(sheetName);
		homePage.createTodoList(taskList);
		List<String> completedTaskList = TestUtil.getTaskDataFromExcel(sheetNameCompleted);
		homePage.markTaskAsCompleted(completedTaskList);
		boolean tasksMarkedAsCompleted = homePage.verifyTaskMarkedAsCompleted(completedTaskList);
		Assert.assertTrue(tasksMarkedAsCompleted,"Task Not found or not marked as completed");

	}
	
	@Test(priority=1)
	public void verifyActiveTabTest() throws InvalidFormatException, InterruptedException{
		List<String> taskList = TestUtil.getTaskDataFromExcel(sheetName);
		homePage.createTodoList(taskList);
		List<String> completedTaskList = TestUtil.getTaskDataFromExcel(sheetNameCompleted);
		homePage.markTaskAsCompleted(completedTaskList);
		boolean activeTaskVerify = homePage.verifyActiveTab(taskList,completedTaskList);
		Assert.assertTrue(activeTaskVerify,"Task Not found or not active list is not valid");
	}
	
	@Test(priority=1)
	public void verifyCompletedTabTest() throws InvalidFormatException, InterruptedException{
		 List<String> taskList = TestUtil.getTaskDataFromExcel(sheetName);
			homePage.createTodoList(taskList);
			List<String> completedTaskList = TestUtil.getTaskDataFromExcel(sheetNameCompleted);
			homePage.markTaskAsCompleted(completedTaskList);
			boolean completedPassed =homePage.verifyCompletedTab(completedTaskList);
			Assert.assertTrue(completedPassed,"Task Not found in completed section");

	}
	@Test(priority=1)
	public void verifyClearCompletedTest() throws InvalidFormatException, InterruptedException{
		 List<String> taskList = TestUtil.getTaskDataFromExcel(sheetName);
			homePage.createTodoList(taskList);
			List<String> completedTaskList = TestUtil.getTaskDataFromExcel(sheetNameCompleted);
			homePage.markTaskAsCompleted(completedTaskList);
			boolean clearcompleted = homePage.verifyClearCompleted(completedTaskList);
			Assert.assertTrue(clearcompleted,"Task not completed or invalid task");


	}
	@AfterMethod
	public void tearDown(){
		driver.quit();
	}
	
	
	
}
