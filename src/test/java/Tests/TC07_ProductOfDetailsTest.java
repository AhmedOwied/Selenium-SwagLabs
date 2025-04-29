package Tests;

import Pages.P01_LoginPage;
import Pages.P02_HomePage;
import Pages.P07_ProductOfDetailsPage;
import Utilities.DataUtil;
import Utilities.LogsUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static DriverFactory.DriverFactory.*;

public class TC07_ProductOfDetailsTest {

    private String Username;
    private String Password;


    {
        try {
            Username = DataUtil.getJsonData("validLoginData", "username");
            Password = DataUtil.getJsonData("validLoginData", "password");
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

    /*Verify adding product from ProductOfDetailsPage*/
    @Test
    public void verifyAddingProductToCartTC() {
        //TODO::LoginSteps
        new P01_LoginPage(getDriver()).enterUserName(Username)
                .enterPassword(Password)
                .clickOnLoginButton();
        P02_HomePage home = new P02_HomePage(getDriver());
        String ProductNameForHome = home.getProductNameForIndexOne();
        LogsUtils.info("ProductName in HomePage: " + ProductNameForHome);
        String ProductNameForProductPage = home.clickOnProductNameForIndexOne().getNameForProductPage();
        LogsUtils.info("ProductName in ProductDetails : " + ProductNameForProductPage);

        Assert.assertEquals(ProductNameForProductPage, ProductNameForHome);

        P07_ProductOfDetailsPage P_Details = new P07_ProductOfDetailsPage(getDriver());
        P_Details.clickOnAddToCartButton();
        Assert.assertEquals(P_Details.getNumberOfProductsOnCartIcon(), "1");

        P_Details.clickOnBackButton();
        Assert.assertEquals(home.getNumberOfProductsOnCartIcon(), 1);


    }

    @AfterMethod
    public void quit() {
        quitDriver();
    }

}
