package Tests;

import Listeners.IInvokeMethodListeners;
import Listeners.ITestResultsListeners;
import Pages.P01_LoginPage;
import Utilities.DataUtil;
import Utilities.LogsUtils;
import Utilities.Utility;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

import static DriverFactory.DriverFactory.*;

@Listeners({IInvokeMethodListeners.class, ITestResultsListeners.class})
public class TC01_LoginTest {
    //Faker Data for Generate Data
    String USERNAME = new Faker().name().username();
    String PASSWORD = new Faker().number().digits(5);
    //private WebDriver driver;


    @BeforeMethod
    public void setUp() throws IOException {  //To run from Maven System.getProperty("browser")
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : DataUtil.getPropertyValue("environment", "browser");
        LogsUtils.info(System.getProperty("browser"));
        setUpDriver(browser);
        LogsUtils.info("Edge Driver is Opened");
        getDriver().get(DataUtil.getPropertyValue("environment", "LOGIN_URL"));
        LogsUtils.info("page is redirect url successfully");
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that user can be login successfully")
    @TmsLink("")//Test Management system     link test case in jira or ExcelSheet
    @Test
    public void validLoginTC() throws IOException {
        //Anonymous Object + Fluent pattern
        new P01_LoginPage(getDriver())
                .enterUserName(DataUtil.getJsonData("validLoginData", "username"))
                .enterPassword(DataUtil.getJsonData("validLoginData", "password"))
                .clickOnLoginButton();

        Assert.assertEquals(getDriver().getCurrentUrl(), DataUtil.getPropertyValue("environment", "HOME_URL"), "Assert After login redirection Failed");
    }


    @Test
    public void inValidLoginWithInCorrectUserNameTC() throws IOException {
        //Anonymous Object + Fluent pattern
        new P01_LoginPage(getDriver()).enterUserName(USERNAME)
                .enterPassword(DataUtil.getJsonData("validLoginData", "password"))
                .clickOnLoginButton();

        Utility.takeScreenShot(getDriver(), "invalid(IncorrectUserName)LoginScreen_");

        //Assert.assertNotEquals(getDriver().getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
        /*Or Message displayed success equal expected*/
        Assert.assertEquals(new P01_LoginPage(getDriver()).isDisplayedErrorMessage(), "Epic sadface: Username and password do not match any user in this service");
    }


    @Test
    public void inValidLoginWithInCorrectPasswordTC() throws IOException {
        //Anonymous Object + Fluent pattern
        new P01_LoginPage(getDriver())
                .enterUserName(DataUtil.getJsonData("validLoginData", "username"))
                .enterPassword(PASSWORD)
                .clickOnLoginButton();

        Utility.takeScreenShot(getDriver(), "invalid(IncorrectPassword)LoginScreen-");

        //Assert.assertNotEquals(getDriver().getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
        /*Or Message displayed success equal expected*/
        Assert.assertEquals(new P01_LoginPage(getDriver()).isDisplayedErrorMessage(), "Epic sadface: Username and password do not match any user in this service");
    }

    @Test
    public void inValidLoginWithEmptyFieldsTC() throws IOException {
        //Anonymous Object + Fluent pattern
        new P01_LoginPage(getDriver())
                .enterUserName("")
                .enterPassword("")
                .clickOnLoginButton();

        Utility.takeScreenShot(getDriver(), "invalid(EmptyFields)LoginScreen_");

        //Assert.assertNotEquals(getDriver().getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
        /*Or Message displayed success equal expected*/
        Assert.assertTrue(new P01_LoginPage(getDriver()).isDisplayedErrorMessageWithEmptyFields());
    }


    @AfterMethod
    public void quit() {
        quitDriver();
    }

}
