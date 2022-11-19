package testrunner;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import setup.Setup;
import utils.Utils;

import java.util.List;

public class LoginTestRunner extends Setup {
    DashboardPage dashboardPage;
    LoginPage loginPage;

    @Test(priority = 1, description = "User can not login with wrong creds")
    public void doLoginWithWrongCreds(){
        driver.get("https://opensource-demo.orangehrmlive.com");
        loginPage=new LoginPage(driver);
        loginPage.doLogin("wronguser","password");
        String validationErrorActual= driver.findElement(By.className("oxd-alert-content-text")).getText();
        String validationErrorExpected="Invalid credentials";

        Assert.assertEquals(validationErrorActual,validationErrorExpected);
        Allure.description("User can not login with wrong creds");

    }
    @Test(priority = 2, description = "Admin log in successfully")
    public void doLogin(){
        loginPage=new LoginPage(driver);
        loginPage.doLogin("admin","admin123");
        String urlActual=driver.getCurrentUrl();
        String urlExpected="dashboard";
        Assert.assertTrue(urlActual.contains(urlExpected));
    }
    @Test(priority = 2, description = "Admin profile image showing")
    public void isProfileImageExists(){
        dashboardPage=new DashboardPage(driver);
        driver.findElement(By.partialLinkText("PIM")).click();
//        WebElement imgProfile= driver.findElement(By.className("oxd-userdropdown-img"));
        Assert.assertTrue(dashboardPage.imgProfile.isDisplayed());
    }
    @Test(priority = 3, description = "Select employee status")
    public void selectEmploymentStatus() throws InterruptedException {
        Thread.sleep(2000);
        dashboardPage.dropdowns.get(0).click();
        dashboardPage.dropdowns.get(0).sendKeys(Keys.ARROW_DOWN);
        dashboardPage.dropdowns.get(0).sendKeys(Keys.ARROW_DOWN);
        dashboardPage.dropdowns.get(0).sendKeys(Keys.ENTER);
        dashboardPage.btnSubmit.click();
        Thread.sleep(3000);
        List<WebElement> txtData= driver.findElements(By.tagName("span"));
        String dataActual= txtData.get(14).getText();
        String dataExpected="Records Found";
        Assert.assertTrue(dataActual.contains(dataExpected));
    }
    @Test(priority = 4, description = "Showing employee list")
    public void listEmployee(){
        Utils.doScroll(driver);
        WebElement table= driver.findElement(By.className("oxd-table-body"));
        List<WebElement> allRows= table.findElements(By.cssSelector("[role=row]"));
        for (WebElement row:allRows) {
            List<WebElement> cells= row.findElements(By.cssSelector("[role=cell]"));
            System.out.println(cells.get(5).getText());
            Assert.assertTrue(cells.get(5).getText().contains("Full-Time Contract"));

        }
    }
    //@Test(priority = 5, description = "Showing no employee data if not in database", enabled = false)
    public void noEmployeeData() throws InterruptedException {
        Thread.sleep(2000);
        dashboardPage=new DashboardPage(driver);
        dashboardPage.dropdowns.get(0).click();
        dashboardPage.dropdowns.get(0).sendKeys(Keys.ARROW_DOWN);
        dashboardPage.dropdowns.get(0).sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        dashboardPage.btnSubmit.click();
        Thread.sleep(2000);

        String dataStatusActual= driver.findElement(By.xpath("//span[contains(text(),'No Records Found')]")).getText();
        String dataStatusExpected="No Records Found";
        Assert.assertEquals(dataStatusActual,dataStatusExpected);
    }
}
