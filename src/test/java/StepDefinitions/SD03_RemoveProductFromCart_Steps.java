package StepDefinitions;

import Pages.P01_LoginPage;
import Pages.P02_HomePage;
import Pages.P03_CartPage;
import Utilities.DataUtil;
import Utilities.LogsUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.asserts.SoftAssert;

import java.io.FileNotFoundException;

import static DriverFactory.DriverFactory.getDriver;

public class SD03_RemoveProductFromCart_Steps {
    SoftAssert softAssert = new SoftAssert();

    int beforeClickOnRemove;
    int afterClickOnRemove;


    @Given("I am logged in with valid credentials")
    public void login_with_valid_credentials() throws FileNotFoundException {
        getDriver().get(DataUtil.getPropertyValue("environment", "LOGIN_URL"));
        new P01_LoginPage(getDriver())
                .enterUserName(DataUtil.getJsonData("validLoginData", "username"))
                .enterPassword(DataUtil.getJsonData("validLoginData", "password"))
                .clickOnLoginButton();
        softAssert.assertEquals(getDriver().getCurrentUrl(), DataUtil.getPropertyValue("environment", "HOME_URL"), "Assert After login redirection Failed");
    }

    @And("I add between {} and {} random products to the cart")
    public void addingRandomProduct(int min, int max) {
        new P02_HomePage(getDriver()).addRandomProducts(min, max)
                .clickOnCartIcon();
    }

    @And("I navigate to the cart page")
    public void iNavigateToCartPage() { //TODO::
        beforeClickOnRemove = Integer.parseInt(new P03_CartPage(getDriver()).getNumberOfProductsOnCartIconInCartPage());
        LogsUtils.info("numberOfCartIconBeforeClickOnRemoveItem: " + beforeClickOnRemove);
    }

    @And("I remove one product from the cart")
    public void iRemoveProductFromTheCart() {
        new P03_CartPage(getDriver()).clickOnCancelButtonForIndexOne();
        afterClickOnRemove = Integer.parseInt(new P03_CartPage(getDriver()).getNumberOfProductsOnCartIconInCartPage());
        LogsUtils.info("numberOfCartIconAfterClickOnRemoveItem: " + afterClickOnRemove);
    }

    @Then("The cart count should decrease by one")
    public void theCartCountShouldDecreaseByOne() {
        softAssert.assertEquals(afterClickOnRemove, beforeClickOnRemove - 1, "Cart icon number did not decrease after removing product");
        softAssert.assertAll();
    }


}
