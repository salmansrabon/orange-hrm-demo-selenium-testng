package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import java.util.List;

public class EmployeePage {
    @FindBy(className = "oxd-button")
    public List<WebElement> btnAddEmployee;
    @FindBy(css = "[type=submit]")
    public WebElement btnSubmit;
    @FindBy(name = "firstName")
    WebElement txtFirstName;
    @FindBy(name="lastName")
    WebElement txtLastName;
    @FindBy(className = "oxd-switch-input")
    public WebElement toggleButton;

    @FindBy(className = "oxd-input")
    public List<WebElement> txtUserCreds;

    @FindBy(className = "oxd-select-text-input")
    public List<WebElement> dropdownBox;

    @FindBy(className = "oxd-input-field-error-message")
    public WebElement lblValidationError;

    public EmployeePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
    public String checkIfUserExists(String username){
        txtUserCreds.get(5).sendKeys(username);
        return lblValidationError.getText();
    }
    public void createEmployee(String firstName, String lastName, String userName, String password, String confirmPassword){
        txtFirstName.sendKeys(firstName);
        txtLastName.sendKeys(lastName);

        txtUserCreds.get(5).sendKeys(userName); //username
        txtUserCreds.get(6).sendKeys(password); //password
        txtUserCreds.get(7).sendKeys(confirmPassword); //confirm password
        btnSubmit.click();
    }
}
