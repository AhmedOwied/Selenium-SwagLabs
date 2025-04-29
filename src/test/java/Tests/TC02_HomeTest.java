package Tests;

import Pages.P01_LoginPage;
import Pages.P02_HomePage;
import Pages.P03_CartPage;
import Utilities.DataUtil;
import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

import static DriverFactory.DriverFactory.*;

public class TC02_HomeTest {
    public Set<Cookie> cookies;

    //TODO:: When Login by User ,Store allCookies in this method
    @BeforeClass
    public void Login() throws FileNotFoundException {
        setUpDriver(DataUtil.getPropertyValue("environment", "browser"));
        LogsUtils.info("Edge Driver is Opened");
        getDriver().get(DataUtil.getPropertyValue("environment", "LOGIN_URL"));
        LogsUtils.info("page is redirect url successfully");

        new P01_LoginPage(getDriver())
                .enterUserName(DataUtil.getJsonData("validLoginData", "username"))
                .enterPassword(DataUtil.getJsonData("validLoginData", "password"))
                .clickOnLoginButton();

        cookies = Utility.getAllCookies(getDriver());
        quitDriver();
    }

    @BeforeMethod
    public void setUp() throws IOException {
        setUpDriver(DataUtil.getPropertyValue("environment", "browser"));
        LogsUtils.info("Edge Driver is Opened");
        getDriver().get(DataUtil.getPropertyValue("environment", "LOGIN_URL"));
        LogsUtils.info("page is redirect to HOME_PAGE successfully");

        Utility.restoreSession(getDriver(), cookies); //adding cookie before Each test
        getDriver().navigate().refresh();
        getDriver().get(DataUtil.getPropertyValue("environment", "HOME_URL"));


    }


    /*Verify When Click on ADD To Cart For all products == numberOfCartIcon */
    @Test
    public void VerifyAddingAllProductsInCart() {
        new P02_HomePage(getDriver()).addAllProductsToCart();

        /*Assert--->  NumberOfProductsOnCartIcon() == NumberOfSelectedProducts() */
        //new P02_HomePage(getDriver()).getNumberOfProductsOnCartIcon(), new P02_HomePage(getDriver()).getNumberOfSelectedProducts()
        Assert.assertEquals(new P02_HomePage(getDriver()).getNumberOfProductsOnCartIcon(), new P02_HomePage(getDriver()).getNumberOfSelectedProducts());
    }

    /*Verify When Click on ADD To Cart For random products == numberOfCartIcon */
    //TODO:: Remove this and Create method Click on specific product with productName for example
    @Test
    public void AddRandomProducts() {
        new P02_HomePage(getDriver()).addRandomProducts(2, 5)
                .getTotalPriceOfSelectedProducts(); //return total price of selected


        /*Assert--->  NumberOfProductsOnCartIcon() == NumberOfSelectedProducts() */
        Assert.assertEquals(new P02_HomePage(getDriver()).getNumberOfProductsOnCartIcon(), new P02_HomePage(getDriver()).getNumberOfSelectedProducts());

    }

    /*Verify When click onCartButton redirection success*/
    @Test
    public void clickOnCartIcon() {

        new P02_HomePage(getDriver()).clickOnCartIcon();
        Assert.assertEquals(getDriver().getCurrentUrl(), DataUtil.getPropertyValue("environment", "CART_URL"));
        /*OR in case of When Click on Cart button reload a few time and then display success cart page*/
        //Assert.assertTrue(new P02_HomePage(driver).verifyUrl(DataUtil.getPropertyValue("environment", "CART_URL")));

    }


    @Test
    public void verifyAddingProductForIndexOne() {

        P02_HomePage home = new P02_HomePage(getDriver());
        String pName = home.getProductNameForIndexOne();
        LogsUtils.info("This is Product name" + pName);
        home.addProductForIndexOne()
                .clickOnCartIcon();
        Assert.assertTrue(new P03_CartPage(getDriver()).isProductForIndexOneDisplayedSuccess(pName), "Product is Not Displayed Success");


    }

    @Test
    public void VerifyResetAppStateFunctionality() { //invalid testcase

        new P02_HomePage(getDriver()).addProductForIndexOne();
        Assert.assertEquals(new P02_HomePage(getDriver()).getNumberOfProductsOnCartIcon(), new P02_HomePage(getDriver()).getNumberOfSelectedProducts());

        new P02_HomePage(getDriver())
                .clickOnSideMenu()
                .clickOnResetAppState()
                .clickOnCloseMenu();
        Assert.assertTrue(new P02_HomePage(getDriver()).isCartReset());
        Assert.assertTrue(new P02_HomePage(getDriver()).areAllButtonResetToAddCartButton(), "All buttons are NOT reset to 'ADD TO CART' state after Reset App State.");

    }

    //TODO:Search How To implement this method:

    @Test
   /*
   public void AddSpecificProductToCart() throws IOException {

        new P01_LoginPage(getDriver())
                .enterUserName(DataUtil.getJsonData("validLoginData", "username"))
                .enterPassword(DataUtil.getJsonData("validLoginData", "password"))
                .clickOnLoginButton()
                .addSpecificProduct()
                .clickOnCartIcon();

        //Assert.assertEquals(getDriver().getCurrentUrl(), DataUtil.getPropertyValue("environment", "CART_URL"));
        //OR in case of When Click on Cart button reload a few time and then display success cart page
        Assert.assertTrue(new P02_HomePage(driver).verifyUrl(DataUtil.getPropertyValue("environment", "CART_URL")));

    }
    */


    @AfterMethod
    public void quit() {
        quitDriver();
    }

    @AfterClass
    public void deleteSession() {
        cookies.clear();
    }


}

