package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class P01_LoginPage {
    WebDriver driver;
    //Locators
    private By userNameField = By.id("user-name");
    private By passwordField = By.cssSelector("input[name='password']");
    private By loginButton = By.className("btn_action");

    private By errorMessageValidation = By.cssSelector("h3[data-test='error']");
    private By errorMessageWithEmptyFields = By.cssSelector("h3[data-test='error']");

    public P01_LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    //Methods
    public P01_LoginPage enterUserName(String name) {
        Utility.sendData(driver, userNameField, name);
        //driver.findElement(userNameField).sendKeys(name);
        return new P01_LoginPage(driver);
        // return this;  //the same
    }

    public P01_LoginPage enterPassword(String password) {
        Utility.sendData(driver, passwordField, password);
        //driver.findElement(passwordField).sendKeys(password);
        return this;
    }

    public P02_HomePage clickOnLoginButton() {
        Utility.clickOnElement(driver, loginButton);  // Wait+ clickElement
        return new P02_HomePage(driver);
    }

    /*For Negative Sc*/
    public String isDisplayedErrorMessage() {
        Utility.generalWait(driver).until(ExpectedConditions.visibilityOfElementLocated(errorMessageValidation));
        return driver.findElement(errorMessageValidation).getText();
    }

    public boolean isDisplayedErrorMessageWithEmptyFields() {
        Utility.generalWait(driver).until(ExpectedConditions.visibilityOfElementLocated(errorMessageWithEmptyFields));
        return driver.findElement(errorMessageWithEmptyFields).isDisplayed();
    }


}
