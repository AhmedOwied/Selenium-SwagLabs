package Tests.E2E;

import Listeners.IInvokeMethodListeners;
import Listeners.ITestResultsListeners;
import Pages.*;
import Utilities.DataUtil;
import Utilities.LogsUtils;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.FileNotFoundException;
import java.io.IOException;

import static DriverFactory.DriverFactory.*;

;


@Listeners({IInvokeMethodListeners.class, ITestResultsListeners.class})
public class E2e_Sc {


    @BeforeMethod
    public void setUp() throws IOException {
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : DataUtil.getPropertyValue("environment", "browser");
        LogsUtils.info(System.getProperty("browser"));
        setUpDriver(browser);
        getDriver().get(DataUtil.getPropertyValue("environment", "LOGIN_URL"));
        LogsUtils.info("page is redirect url successfully");
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @Description("Valid E2e Scenario for User Journey")
    @Test
    public void mvn() throws IOException {
        //TODO::Use SoftAssert
        SoftAssert softAssert = new SoftAssert();
        new P01_LoginPage(getDriver())
                .enterUserName(DataUtil.getJsonData("validLoginData", "username"))
                .enterPassword(DataUtil.getJsonData("validLoginData", "password"))
                .clickOnLoginButton();
        softAssert.assertEquals(getDriver().getCurrentUrl(), DataUtil.getPropertyValue("environment", "HOME_URL"), "Assert After login redirection Failed");
        //TODO::adding product
        new P02_HomePage(getDriver())
                .addRandomProducts(3, 6);
        softAssert.assertEquals(new P02_HomePage(getDriver()).getNumberOfProductsOnCartIcon(), new P02_HomePage(getDriver()).getNumberOfSelectedProducts());
        new P02_HomePage(getDriver()).clickOnCartIcon();
        //TODO::Filling info CheckOut
        new P03_CartPage(getDriver()).clickOnCheckOutButton()
                .enterFirstName(DataUtil.getJsonData("information", "fName"))
                .enterLastName(DataUtil.getJsonData("information", "lName"))
                .enterPostalCode("123")
                .clickOnContinue();

        softAssert.assertEquals(new P05_OverviewPage(getDriver()).getTotal(), new P05_OverviewPage(getDriver()).calculateTotalPrice());

        new P05_OverviewPage(getDriver()).clickOnFinishButton();
        softAssert.assertTrue(new P06_FinishPage(getDriver()).isVisibilityThanksMessage());

        softAssert.assertAll();

    }

    //new P05_OverviewPage(getDriver()).comparingTotalPrice()
    @Description("Verify CheckOut Order WithOut Product In Cart")
    @Test
    public void E2eScenario_Invalid_CheckOrderWithEmptyCart() throws IOException {
        //TODO::Use SoftAssert
        SoftAssert softAssert = new SoftAssert();
        new P01_LoginPage(getDriver())
                .enterUserName(DataUtil.getJsonData("validLoginData", "username"))
                .enterPassword(DataUtil.getJsonData("validLoginData", "password"))
                .clickOnLoginButton();
        softAssert.assertEquals(getDriver().getCurrentUrl(), DataUtil.getPropertyValue("environment", "HOME_URL"), "Assert After login redirection Failed");
        /*ممككن في سناريو تاني مستخدمش addproduct واخليه يكمل */
        new P02_HomePage(getDriver())
                .clickOnCartIcon();

        new P03_CartPage(getDriver()).clickOnCheckOutButton()
                .enterFirstName(DataUtil.getJsonData("information", "fName"))
                .enterLastName(DataUtil.getJsonData("information", "lName"))
                .enterPostalCode("123")
                .clickOnContinue();

        softAssert.assertEquals(new P05_OverviewPage(getDriver()).getTotalPriceWithZero(), "0.00");
        //TODO::Complete this--->
        softAssert.assertAll();

    }

    /*Test When Remove item from cart --> countOfCartIcon decrease 1 */
    @Description("Verify When Remove item From Cart count Of Cart Icon decrease")
    @Test
    public void E2eScenario_Valid_RemoveProductFromCartSc() throws IOException {
        //TODO::Use SoftAssert
        SoftAssert softAssert = new SoftAssert();
        new P01_LoginPage(getDriver())
                .enterUserName(DataUtil.getJsonData("validLoginData", "username"))
                .enterPassword(DataUtil.getJsonData("validLoginData", "password"))
                .clickOnLoginButton();
        softAssert.assertEquals(getDriver().getCurrentUrl(), DataUtil.getPropertyValue("environment", "HOME_URL"), "Assert After login redirection Failed");
        /*ممككن في سناريو تاني مستخدمش addproduct واخليه يكمل */
        new P02_HomePage(getDriver()).addRandomProducts(2, 5)
                .clickOnCartIcon();

        int beforeClickOnRemove = Integer.parseInt(new P03_CartPage(getDriver()).getNumberOfProductsOnCartIconInCartPage());
        LogsUtils.info("numberOfCartIconBeforeClickOnRemoveItem: " + beforeClickOnRemove);

        new P03_CartPage(getDriver()).clickOnCancelButtonForIndexOne();

        int afterClickOnRemove = Integer.parseInt(new P03_CartPage(getDriver()).getNumberOfProductsOnCartIconInCartPage());
        LogsUtils.info("numberOfCartIconAfterClickOnRemoveItem: " + afterClickOnRemove);

        softAssert.assertEquals(afterClickOnRemove, beforeClickOnRemove - 1, "Cart icon number did not decrease after removing product");
        softAssert.assertAll();

    }

    @Description("Verify When User Click On LogOut Redirect to Login Page")
    @Test
    public void E2eScenario_valid_LogoutSc() throws IOException {
        //TODO::Use SoftAssert
        SoftAssert softAssert = new SoftAssert();
        new P01_LoginPage(getDriver())
                .enterUserName(DataUtil.getJsonData("validLoginData", "username"))
                .enterPassword(DataUtil.getJsonData("validLoginData", "password"))
                .clickOnLoginButton();
        softAssert.assertEquals(getDriver().getCurrentUrl(), DataUtil.getPropertyValue("environment", "HOME_URL"), "Assert After login redirection Failed");
        /*ممككن في سناريو تاني مستخدمش addproduct واخليه يكمل */
        new P02_HomePage(getDriver()).addProductForIndexOne()
                .clickOnCartIcon();


        new P02_HomePage(getDriver()).clickOnSideMenu()
                .clickOnLogout();
        softAssert.assertEquals(getDriver().getCurrentUrl(), DataUtil.getPropertyValue("environment", "LOGIN_URL"));
        softAssert.assertAll();
    }

    @Description("Verify All buttons are Reset to 'ADD TO CART' state after Clicking On Reset App State")
    @Test
    public void E2eScenario_inValidResetAppState() throws IOException {
        //TODO::Use SoftAssert
        SoftAssert softAssert = new SoftAssert();
        new P01_LoginPage(getDriver())
                .enterUserName(DataUtil.getJsonData("validLoginData", "username"))
                .enterPassword(DataUtil.getJsonData("validLoginData", "password"))
                .clickOnLoginButton()
                .addProductForIndexOne();
        Assert.assertEquals(new P02_HomePage(getDriver()).getNumberOfProductsOnCartIcon(), new P02_HomePage(getDriver()).getNumberOfSelectedProducts());

        new P02_HomePage(getDriver())
                .clickOnSideMenu()
                .clickOnResetAppState()
                .clickOnCloseMenu();
        Assert.assertTrue(new P02_HomePage(getDriver()).isCartReset()); //true
        Assert.assertTrue(new P02_HomePage(getDriver()).areAllButtonResetToAddCartButton(), "All buttons are NOT reset to 'ADD TO CART' state after Reset App State.");//failed
        softAssert.assertAll();
    }

    @Description("Verify When User Add product From ProductPageDetails")
    @Test
    public void E2eScenario_invalid_AddingProductFromProductPageDetails() throws FileNotFoundException {
        //TODO::LoginSteps
        new P01_LoginPage(getDriver())
                .enterUserName(DataUtil.getJsonData("validLoginData", "username"))
                .enterPassword(DataUtil.getJsonData("validLoginData", "password"))
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

    @Description("Verify that User can Continue Shopping After Add Products in the Cart")
    @Test
    public void E2eScenario_VerifyContinueShopping() throws IOException {
        //TODO::Use SoftAssert
        SoftAssert softAssert = new SoftAssert();
        new P01_LoginPage(getDriver())
                .enterUserName(DataUtil.getJsonData("validLoginData", "username"))
                .enterPassword(DataUtil.getJsonData("validLoginData", "password"))
                .clickOnLoginButton()
                .addProductForIndexOne();
        Assert.assertEquals(new P02_HomePage(getDriver()).getNumberOfProductsOnCartIcon(), new P02_HomePage(getDriver()).getNumberOfSelectedProducts());

        new P02_HomePage(getDriver()).clickOnCartIcon();
        new P03_CartPage(getDriver()).clickOnContinueShoppingButton()
                .addRandomProducts(1, 2);

        softAssert.assertEquals(new P02_HomePage(getDriver()).getNumberOfProductsOnCartIcon(), new P02_HomePage(getDriver()).getNumberOfSelectedProducts());
        new P02_HomePage(getDriver()).clickOnCartIcon();
        //TODO::Filling info CheckOut
        new P03_CartPage(getDriver()).clickOnCheckOutButton()
                .enterFirstName(DataUtil.getJsonData("information", "fName"))
                .enterLastName(DataUtil.getJsonData("information", "lName"))
                .enterPostalCode("123")
                .clickOnContinue();

        softAssert.assertEquals(new P05_OverviewPage(getDriver()).getTotal(), new P05_OverviewPage(getDriver()).calculateTotalPrice());

        new P05_OverviewPage(getDriver()).clickOnFinishButton();
        softAssert.assertTrue(new P06_FinishPage(getDriver()).isVisibilityThanksMessage());
        softAssert.assertAll();
    }


    @AfterMethod
    public void quit() {
        quitDriver();
    }
}
