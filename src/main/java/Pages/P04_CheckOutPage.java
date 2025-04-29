package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class P04_CheckOutPage {
    WebDriver driver;
    //locators
    private By firstNameInput = By.id("first-name");
    private By lastNameInput = By.id("last-name");
    private By postalCode = By.id("postal-code");
    private By continueButton = By.cssSelector("input[type='submit']");
    private By cancelButton = By.xpath("//a[text()='CANCEL']");
    private By errorValidationMessage = By.cssSelector("h3[data-test='error']");

    public P04_CheckOutPage(WebDriver driver) {
        this.driver = driver;
    }

    //methods

    public P04_CheckOutPage enterFirstName(String firstName) {
        Utility.sendData(driver, firstNameInput, firstName);
        return this;
    }

    public P04_CheckOutPage enterLastName(String LastName) {
        driver.findElement(lastNameInput).sendKeys(LastName);
        return this;
    }

    public P04_CheckOutPage enterPostalCode(String pCode) {
        driver.findElement(postalCode).sendKeys(pCode);
        return this;
    }

    //TODO:: data should be data Driven

   /* public P04_CheckOutPage fillingInfoForm(String fname, String lname, String zcode) {
        driver.findElement(firstNameInput).sendKeys(fname);
        driver.findElement(lastNameInput).sendKeys(lname);
        driver.findElement(postalCode).sendKeys(zcode);
        return this;
    }*/

    public P05_OverviewPage clickOnContinue() {
        Utility.clickOnElement(driver, continueButton);
        LogsUtils.info("Redirect To OverViewPage Successfully");
        return new P05_OverviewPage(driver);
    }

    public boolean isDisplayedErrorMessage() {
        LogsUtils.info("Error Message is: " + driver.findElement(errorValidationMessage).getText());
        Utility.generalWait(driver).until(ExpectedConditions.visibilityOfElementLocated(errorValidationMessage));
        return driver.findElement(errorValidationMessage).isDisplayed();
    }

    /*Assertion*/
    public boolean verifyUrl(String exceptedUrl) {
        try {
            Utility.generalWait(driver).until(ExpectedConditions.urlToBe(exceptedUrl));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
