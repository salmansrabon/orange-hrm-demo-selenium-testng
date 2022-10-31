package testrunner;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.DashboardPage;
import pages.EmployeePage;
import pages.LoginPage;
import setup.Setup;
import utils.Utils;

import java.io.IOException;
import java.io.WriteAbortedException;
import java.util.List;

public class UserUpdateTestRunner extends Setup {
    @BeforeTest
    public void doLogin() throws IOException, ParseException {
        driver.get("https://opensource-demo.orangehrmlive.com");
        List data = Utils.readJSONArray("./src/test/resources/Users.json");
        LoginPage loginPage = new LoginPage(driver);
        JSONObject userObj = (JSONObject) data.get(data.size() - 1);
        String username = (String) userObj.get("userName");
        String password = (String) userObj.get("password");
        loginPage.doLogin(username, password);
        String urlActual = driver.getCurrentUrl();
        String urlExpected = "viewMyDetails";
        Assert.assertTrue(urlActual.contains(urlExpected));
    }

    @Test(priority = 1, description = "Upload image")
    public void uploadImage() throws InterruptedException {
        List<WebElement> images = driver.findElements(By.className("employee-image"));
        images.get(0).click();
        Thread.sleep(5000);
        driver.findElement(By.className("employee-image-action")).sendKeys("D:\\logo.png");
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("[type=submit]")).click();
        driver.navigate().back();
    }

    @Test(priority = 2, description = "Update user info")
    public void updateUserInfo() throws InterruptedException {
        List<WebElement> headerTitle = driver.findElements(By.className("orangehrm-main-title"));
        Utils.waitForElement(driver, headerTitle.get(0), 50);
        if (headerTitle.get(0).isDisplayed()) {
            EmployeePage employeePage = new EmployeePage(driver);
            employeePage.dropdownBox.get(0).click();
            employeePage.dropdownBox.get(0).sendKeys("b");
            employeePage.dropdownBox.get(0).sendKeys(Keys.ARROW_DOWN);
            employeePage.dropdownBox.get(0).sendKeys(Keys.ARROW_DOWN);
            employeePage.dropdownBox.get(0).sendKeys(Keys.ENTER);
            Utils.doScroll(driver);
            List<WebElement> buttons = driver.findElements(By.cssSelector("[type=submit]"));
            buttons.get(0).click();
            Thread.sleep(5000);
            driver.navigate().refresh();
            Thread.sleep(5000);
            List<WebElement> list = driver.findElements(By.className("oxd-select-text-input"));
            String country = list.get(0).getText();
            System.out.println(country);
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(country, "Bangladeshi");
            Assert.assertEquals(country, "Bangladeshi");
            softAssert.assertAll();
        }
    }

    //@AfterTest
    public void logout() {
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.btnProfileIcon.click();
        driver.findElement(By.partialLinkText("Logout")).click();
    }
}
//update
