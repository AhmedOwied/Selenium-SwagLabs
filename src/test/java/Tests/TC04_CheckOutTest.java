package Tests;

import Listeners.IInvokeMethodListeners;
import Listeners.ITestResultsListeners;
import Pages.P01_LoginPage;
import Pages.P02_HomePage;
import Pages.P03_CartPage;
import Pages.P04_CheckOutPage;
import Utilities.DataUtil;
import Utilities.LogsUtils;
import Utilities.Utility;
import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static DriverFactory.DriverFactory.*;

@Listeners({IInvokeMethodListeners.class, ITestResultsListeners.class})
public class TC04_CheckOutTest {

    private String Username;
    private String Password;
    private String firstName;
    private String lastName;
    private String postalCode;

    {
        try {
            Username = DataUtil.getJsonData("validLoginData", "username");
            Password = DataUtil.getJsonData("validLoginData", "password");
            firstName = DataUtil.getJsonData("information", "fName") + "-" + Utility.getTimeStamp();
            lastName = DataUtil.getJsonData("information", "lName") + "-" + Utility.getTimeStamp();
            postalCode = new Faker().number().digits(5);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @BeforeMethod
    public void setUp() throws IOException {
        setUpDriver(DataUtil.getPropertyValue("environment", "browser"));
        LogsUtils.info("Edge Driver is Opened");
        getDriver().get(DataUtil.getPropertyValue("environment", "LOGIN_URL"));
        LogsUtils.info("page is redirect url successfully");
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @Test
    public void verifyCheckOutInformationTC() throws IOException {
        //TODO:: LoginSteps
        new P01_LoginPage(getDriver()).enterUserName(Username)
                .enterPassword(Password)
                .clickOnLoginButton();
        //TODO:: Adding Products Steps
        new P02_HomePage(getDriver()).addRandomProducts(2, 6)
                .clickOnCartIcon();
        //TODO:: GoTo CheckOut Page
        new P03_CartPage(getDriver()).clickOnCheckOutButton();
        //TODO:: Filling Info Steps
        new P04_CheckOutPage(getDriver())
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .enterPostalCode(postalCode)
                .clickOnContinue();

        Assert.assertEquals(getDriver().getCurrentUrl(), DataUtil.getPropertyValue("environment", "CheckOutOverView_URL"));
    }

    @Test
    public void invalidCheckOutInformation_WithEmptyFNameTC() throws IOException {
        //TODO::LoginSteps
        new P01_LoginPage(getDriver()).enterUserName(Username)
                .enterPassword(Password)
                .clickOnLoginButton();
        //TODO::Adding Products Steps
        new P02_HomePage(getDriver()).addRandomProducts(2, 6)
                .clickOnCartIcon();
        //TODO::GoTo CheckOut Page
        new P03_CartPage(getDriver()).clickOnCheckOutButton();
        //TODO:: Filling Info Steps
        new P04_CheckOutPage(getDriver())
                .enterFirstName("")
                .enterLastName(lastName)
                .enterPostalCode(postalCode)
                .clickOnContinue();

        Utility.takeScreenShot(getDriver(), "invalidCheckOutScreen-");

        Assert.assertTrue(new P04_CheckOutPage(getDriver()).isDisplayedErrorMessage());
    }

    @Test
    public void invalidCheckOutInformation_WithEmptyLNameTC() throws IOException {
        //TODO::LoginSteps
        new P01_LoginPage(getDriver()).enterUserName(Username)
                .enterPassword(Password)
                .clickOnLoginButton();
        //TODO::Adding Products Steps
        new P02_HomePage(getDriver()).addRandomProducts(2, 6)
                .clickOnCartIcon();
        //TODO::GoTo CheckOut Page
        new P03_CartPage(getDriver()).clickOnCheckOutButton();
        //TODO:: Filling Info Steps
        new P04_CheckOutPage(getDriver())
                .enterFirstName(firstName)
                .enterLastName("")
                .enterPostalCode(postalCode)
                .clickOnContinue();

        Utility.takeScreenShot(getDriver(), "invalidCheckOutScreen-");

        Assert.assertTrue(new P04_CheckOutPage(getDriver()).isDisplayedErrorMessage());
    }

    @Test
    public void invalidCheckOutInformation_WithEmptyPosalCodeTC() throws IOException {
        //TODO::LoginSteps
        new P01_LoginPage(getDriver()).enterUserName(Username)
                .enterPassword(Password)
                .clickOnLoginButton();
        //TODO::Adding Products Steps
        new P02_HomePage(getDriver()).addRandomProducts(2, 6)
                .clickOnCartIcon();
        //TODO::GoTo CheckOut Page
        new P03_CartPage(getDriver()).clickOnCheckOutButton();
        //TODO:: Filling Info Steps
        new P04_CheckOutPage(getDriver())
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .enterPostalCode("")
                .clickOnContinue();

        Utility.takeScreenShot(getDriver(), "invalidCheckOutScreen-");

        Assert.assertTrue(new P04_CheckOutPage(getDriver()).isDisplayedErrorMessage());
    }

    @AfterMethod
    public void quit() {
        quitDriver();
    }
}
