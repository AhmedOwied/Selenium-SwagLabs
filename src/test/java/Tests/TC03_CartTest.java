package Tests;

import Pages.P01_LoginPage;
import Pages.P02_HomePage;
import Pages.P03_CartPage;
import Utilities.DataUtil;
import Utilities.LogsUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

import static DriverFactory.DriverFactory.*;

public class TC03_CartTest {
    String expectedName = "Sauce Labs Bike Light";
    //WebDriver driver;

    @BeforeMethod
    public void setUp() throws IOException {
        setUpDriver(DataUtil.getPropertyValue("environment", "browser"));
        LogsUtils.info("Edge Driver is Opened");
        getDriver().get(DataUtil.getPropertyValue("environment", "LOGIN_URL"));
        LogsUtils.info("page is redirect url successfully");
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    /*Verify that selectOfProducts Added success To The Cart*/
    @Test
    public void validProductAddedToCartTC() {
        //Anonymous Object + Fluent pattern
        String totalPriceInHome = new P01_LoginPage(getDriver()).enterUserName("standard_user")
                .enterPassword("secret_sauce")
                .clickOnLoginButton()
                .addRandomProducts(3, 6)
                .getTotalPriceOfSelectedProducts();

        new P02_HomePage(getDriver()).clickOnCartIcon();

        Assert.assertEquals(new P03_CartPage(getDriver()).getTotalPriceOfSelectedProducts(), totalPriceInHome);
    }

    @Test
    public void validCancelProductFromTheCartTC() {
        SoftAssert softAssert = new SoftAssert();
        //Anonymous Object + Fluent pattern
        new P01_LoginPage(getDriver()).enterUserName("standard_user")
                .enterPassword("secret_sauce")
                .clickOnLoginButton();
        new P02_HomePage(getDriver()).addProductForIndexOne()
                .clickOnCartIcon();

        int beforeClickOnRemove = Integer.parseInt(new P03_CartPage(getDriver()).getNumberOfProductsOnCartIconInCartPage());
        LogsUtils.info("numberOfCartIconBeforeClickOnRemoveItem: " + beforeClickOnRemove);

        new P03_CartPage(getDriver()).clickOnCancelButtonForIndexOne();

        int afterClickOnRemove = Integer.parseInt(new P03_CartPage(getDriver()).getNumberOfProductsOnCartIconInCartPage());
        LogsUtils.info("numberOfCartIconAfterClickOnRemoveItem: " + afterClickOnRemove);

        softAssert.assertEquals(afterClickOnRemove, beforeClickOnRemove - 1, "Cart icon number did not decrease after removing product");
        softAssert.assertAll();
    }


    @AfterMethod
    public void quit() {
        quitDriver();
    }
}
