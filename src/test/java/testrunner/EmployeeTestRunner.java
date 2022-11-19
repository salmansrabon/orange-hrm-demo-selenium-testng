package testrunner;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.EmployeePage;
import pages.LoginPage;
import setup.Setup;
import utils.Utils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class EmployeeTestRunner extends Setup {
    @BeforeTest
    public void doLogin(){
        driver.get("https://opensource-demo.orangehrmlive.com");
        LoginPage loginPage=new LoginPage(driver);
        String adminUser="admin";
        String adminPass="admin123";
        loginPage.doLogin(adminUser,adminPass);
    }
    @Test(priority = 1, description = "Check if user exists", enabled = false)
    public void checkIfUserExists() throws IOException, ParseException {
        EmployeePage employeePage=new EmployeePage(driver);
//        employeePage.btnAddEmployee.get(2).click();
//        employeePage.toggleButton.click();
        List data= Utils.readJSONArray("./src/test/resources/Users.json");
        JSONObject userObj= (JSONObject) data.get(data.size()-1);
        String exsistingUserName= (String) userObj.get("userName");
        String validationMessageActual= employeePage.checkIfUserExists(exsistingUserName);
        String validationMessageExpected="Username already exists";

        Assert.assertTrue(validationMessageActual.contains(validationMessageExpected));


    }

    @Test(priority = 2, description = "Create new employee")
    public void createEmployee() throws IOException, ParseException, InterruptedException, UnsupportedFlavorException {
        EmployeePage employeePage=new EmployeePage(driver);
        driver.findElement(By.partialLinkText("PIM")).click();
        employeePage.btnAddEmployee.get(2).click();
        Thread.sleep(5000);

        List<WebElement> elements= driver.findElements(By.className("oxd-input"));
        elements.get(4).sendKeys(Keys.CONTROL+"a");
        elements.get(4).sendKeys(Keys.CONTROL+"c");
        employeePage.toggleButton.click();
        Utils utils=new Utils();
        utils.geneateRandomData();
        String firstName=utils.getFirstname();
        String lastName=utils.getLastname();
        String id= Utils.pasteValue();
        String userName=utils.getUsername();
        String password="P@ssword123";
        String confirmPassword=password;
        employeePage.txtUserCreds.get(5).clear();
        employeePage.createEmployee(firstName,lastName,userName,password,confirmPassword);

        Thread.sleep(5000);
        List<WebElement> headerTitle= driver.findElements(By.className("orangehrm-main-title"));
        Assert.assertTrue(headerTitle.get(0).isDisplayed());

        Utils.waitForElement(driver,headerTitle.get(0),50);
        if(headerTitle.get(0).isDisplayed()){
            utils.saveJsonList(id, userName,password);
        }


    }
}
