package Tests;

import Listeners.IInvokeMethodListeners;
import Listeners.ITestResultsListeners;
import Pages.*;
import Utilities.DataUtil;
import Utilities.LogsUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

import static DriverFactory.DriverFactory.*;

@Listeners({IInvokeMethodListeners.class, ITestResultsListeners.class})
public class TC06_FinishingOrderTest {


    @BeforeMethod
    public void setUp() throws IOException {
        setUpDriver(DataUtil.getPropertyValue("environment", "browser"));
        LogsUtils.info("Edge Driver is Opened");
        getDriver().get(DataUtil.getPropertyValue("environment", "LOGIN_URL"));
        LogsUtils.info("page is redirect url successfully");
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    /*Verify displayed message when Finishing Order*/
    @Test
    public void verifyFinishingOrderTC() throws IOException {
        //TODO::LoginSteps
        new P01_LoginPage(getDriver()).enterUserName("standard_user")
                .enterPassword("secret_sauce")
                .clickOnLoginButton();
        //TODO::Adding Products Steps
        new P02_HomePage(getDriver()).addRandomProducts(2, 6)
                .clickOnCartIcon();
        //TODO::GoTo CheckOut Page
        new P03_CartPage(getDriver()).clickOnCheckOutButton();
        //TODO:: Filling Info Steps
        new P04_CheckOutPage(getDriver())
                .enterFirstName("")
                .enterLastName("")
                .enterPostalCode("");
        new P05_OverviewPage(getDriver()).clickOnFinishButton();

        Assert.assertTrue(new P06_FinishPage(getDriver()).isVisibilityThanksMessage());

    }

    @AfterMethod
    public void quit() {
        quitDriver();
    }

}

