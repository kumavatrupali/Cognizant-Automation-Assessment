package com.todo.qa.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.todo.qa.base.TestBase;

public class HomePage extends TestBase {

	
	
	@FindBy(id = "todo-input")
	WebElement todoInputBox;
	
	@FindBy(xpath = "//ul[@class='todo-list']/li")
	List<WebElement> todoList;
	
	@FindBy(xpath = "//ul[@class='todo-list']/li/div/label")
	WebElement todoListLabel;
	
	@FindBy(xpath = "//a[contains(text(),'Completed')]")
	WebElement completedLink;
	
	@FindBy(xpath = "//a[contains(text(),'Active')]")
	WebElement activeLink;
	
	@FindBy(xpath = "//button[contains(text(),'Clear completed')]")
	WebElement clearCompletedLink;
	
	public HomePage(){
		PageFactory.initElements(driver, this);
	}
	
	public boolean todoboxDisplayed()
	{
	  return  todoInputBox.isDisplayed();
	
	}
	public String validateHomePageTitle() {
		return driver.getTitle();
	}
	
	public void createTodoList(List<String> taskList)
	{
		for(String task : taskList)
		{
			todoInputBox.sendKeys(task);
			todoInputBox.sendKeys(Keys.ENTER);
			System.out.println("Task Added " + task);
		}
	}
	
	public List<String> validateAddedTask()
	{
		List<String> taskList = new ArrayList<>();
         // Verify each item is displayed and print the text
         for (WebElement task : todoList) {
             if (task.isDisplayed()) {
                 System.out.println("Todo item displayed: " + task.getText());
                 taskList.add(task.getText().trim());
             } else {
                 System.out.println("Todo item NOT displayed");
             }
         }

		return taskList;		
	}
	
	public void markTaskAsCompleted(List<String> completedTaskList) {
		
		for(String taskMarkAsCompleted : completedTaskList)	{
			for (WebElement task : todoList) {
		        if (task.isDisplayed() && task.getText().equalsIgnoreCase(taskMarkAsCompleted)) {        
		            System.out.println("Todo item found: " + task.getText());	      
	
		            // Find the select button inside the task element
		            WebElement selectButton = task.findElement(By.className("toggle"));
	
		            // Scroll into view if necessary
		            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", selectButton);
	
		            // Click using JavaScript if normal click fails
	                if (!selectButton.isSelected()) { 
	                try {                         	
		            		selectButton.click();
		            	} catch (ElementClickInterceptedException e)
	                	{
		            		((JavascriptExecutor) driver).executeScript("arguments[0].click();", selectButton);
	                	}
		            Assert.assertTrue(selectButton.isSelected(),"Task is not marked as completed");
		            System.out.println("Todo item completed: " + taskMarkAsCompleted);
		            
	                } else
	                {
	                	System.out.println(taskMarkAsCompleted + "task already marked completed");
	                }
	                
		        } 
		        
		    }		
			
		}

	}


	public void editTodoTask(String taskTobeEdited, String editTaskAs) throws InterruptedException {
	    for (int i = 0; i < todoList.size(); i++) {
	        WebElement task = todoList.get(i);

	        if (task.isDisplayed() && task.getText().equalsIgnoreCase(taskTobeEdited)) {
	            System.out.println("Todo item found: " + task.getText());

	            try {
	                // Find the label inside the task
	                WebElement todoListLabel = task.findElement(By.tagName("label"));

	                // Double-click to turn it into an input field
	                Actions action = new Actions(driver);
	                action.doubleClick(todoListLabel).perform();

	                // Pause briefly to allow the input field to appear
	                Thread.sleep(1000);

	                // Re-locate the task after DOM changes
	                task = todoList.get(i);
	                WebElement taskInput = task.findElement(By.tagName("input"));

	                // Verify the input value
	                System.out.println("Task before edit: " + taskInput.getAttribute("value"));

	                // Clear and enter new text
	                taskInput.clear();
	                taskInput.sendKeys(editTaskAs);
	                taskInput.sendKeys(Keys.ENTER);

	                // Small delay to allow text change
	                Thread.sleep(1000);

	                // Re-locate again and verify
	                task = todoList.get(i);
	                System.out.println("Todo item later: " + task.getText());
	                break; // Exit loop after editing the first matching task

	            } catch (StaleElementReferenceException e) {
	                System.out.println("Element became stale.");
	                task = todoList.get(i); // Re-fetch task if stale
	            }
	    }}
	}

	public void deleteTodoTask(String taskTobeDeleted) {
	    for (WebElement task : todoList) {
	        if (task.isDisplayed() && task.getText().equalsIgnoreCase(taskTobeDeleted)) {        
	            System.out.println("Todo item found: " + task.getText());

	            // Hover over the task to make the delete button visible
	            Actions actions = new Actions(driver);
	            actions.moveToElement(task).perform();

	            // Adding a small sleep to allow UI updates (avoid if possible)
	            try {
	                Thread.sleep(1000);  // Wait 1 second for the UI to update
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }

	            // Find the delete button inside the task element
	            WebElement deleteButton = task.findElement(By.className("destroy"));

	            // Scroll into view if necessary
	            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", deleteButton);

	            // Click using JavaScript if normal click fails
	            try {
	                deleteButton.click();
	            } catch (ElementClickInterceptedException e) {
	                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteButton);
	            }

	            System.out.println("Todo item deleted: " + taskTobeDeleted);
	            break; // Exit loop after deleting the task
	        }
	    }
	}

    public boolean verifyTaskMarkedAsCompleted(List<String> completedTaskList) {
		boolean flag = false;
		for(String taskMarkAsCompleted : completedTaskList)	{
			for (WebElement task : todoList) {
		        if (task.isDisplayed() && task.getText().equalsIgnoreCase(taskMarkAsCompleted)) {        
		            System.out.println("Todo item found: " + task.getText());	      
	
		            // Find the select button inside the task element
		            WebElement selectButton = task.findElement(By.className("toggle"));
	
		            // Scroll into view if necessary
		            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", selectButton);
	
		            // Click using JavaScript if normal click fails
	                if (selectButton.isSelected()) { 
	                	
	                	flag = true;
		          
		            System.out.println("Todo item completed: " + taskMarkAsCompleted);
		            
	                } else
	                {
	                	flag = false;
	                	System.out.println("task is not marked as completed " + "task already marked completed");
	                }      
		        
		        }	
			}		
	} 
	
		return flag;
	}


    public boolean verifyCompletedTab(List<String> completedTaskList) {
		boolean flag = false;
		completedLink.click();
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); // Use implicit wait
	    for(String taskMarkedAsCompleted : completedTaskList)	{
			for (WebElement task : todoList) {
		        if (task.isDisplayed() && task.getText().equalsIgnoreCase(taskMarkedAsCompleted)) {        
		            WebElement selectButton = task.findElement(By.className("toggle"));
		            if(selectButton.isSelected())
		            	flag=true;
		            else
		            	flag=false;
		            
		        	}
		        else flag = false;
		       }
		}
	    return flag;
	}

	public boolean verifyActiveTab(List<String> taskList, List<String> completedTaskList) {
		activeLink.click();
		boolean flag = false;
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); // Use implicit wait
			for (WebElement task : todoList)
			    for(String allTask :taskList )
					for(String taskMarkedAsCompleted : completedTaskList)
					{
		        if (task.isDisplayed() && task.getText().equalsIgnoreCase(allTask) && !(task.getText().equalsIgnoreCase(taskMarkedAsCompleted)))
		        {  	           
		          	WebElement selectButton = task.findElement(By.className("toggle"));
		            if(!selectButton.isSelected())
		            	flag=true;
		            else
		            	flag=false;
		            
		        	}
		        else flag = false;
		       }
			 return flag;
		}

	public boolean verifyClearCompleted(List<String> completedTaskList) {
		clearCompletedLink.click();	
		boolean flag = false;
		{
			for (WebElement task : todoList) 
				for(String completedTask : completedTaskList)	{
		        if (task.isDisplayed() && !(task.getText().equalsIgnoreCase(completedTask)))       
		            flag=true;
		        else
		            flag=false;		
		        }
		}
		completedLink.click();
		for (WebElement task : todoList) 
		{	if (task.isDisplayed())
			flag = false;
		}
		return flag;
	} 
	
		
	
}

