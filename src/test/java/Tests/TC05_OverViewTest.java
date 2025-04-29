package Tests;

import Listeners.IInvokeMethodListeners;
import Listeners.ITestResultsListeners;
import Pages.*;
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
public class TC05_OverViewTest {
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
        /* setUpDriver(String browser)*/
        setUpDriver(DataUtil.getPropertyValue("environment", "browser"));
        LogsUtils.info("Edge Driver is Opened");
        /*getDriver().get   -Ex:   driver.get(Url) */
        getDriver().get(DataUtil.getPropertyValue("environment", "LOGIN_URL"));
        LogsUtils.info("page is redirect url successfully");
    }

    @Test
    public void verifyCheckOutOverViewTC() throws IOException {
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
                .enterPostalCode(postalCode)
                .clickOnContinue();

        Assert.assertTrue(new P05_OverviewPage(getDriver()).comparingTotalPrice());

    }

    @AfterMethod
    public void quit() {
        quitDriver();
    }

}
