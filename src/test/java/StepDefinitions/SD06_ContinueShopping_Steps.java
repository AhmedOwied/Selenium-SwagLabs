package StepDefinitions;

import Pages.*;
import Utilities.DataUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.asserts.SoftAssert;

import java.io.FileNotFoundException;

import static DriverFactory.DriverFactory.getDriver;

public class SD06_ContinueShopping_Steps {
    SoftAssert softAssert = new SoftAssert();
    P02_HomePage home = new P02_HomePage(getDriver());

    @And("the user adds the first product to the cart")
    public void addFirstProductToCart() {
        home.addProductForIndexOne();
    }

    /*@Then("the cart icon count should match the number of selected items")
    public void verifyCartIconMatchesSelectedProducts() {
        softAssert.assertEquals(
                home.getNumberOfProductsOnCartIcon(),
                home.getNumberOfSelectedProducts()
        );
    }*/  //TODO::Written in CommandSteps is Duplicated in some Classes

    @When("the user clicks on the cart icon")
    public void clickOnCartIcon() {
        home.clickOnCartIcon();
    }

    @And("clicks on the Continue Shopping button")
    public void clickContinueShopping() {
        new P03_CartPage(getDriver()).clickOnContinueShoppingButton();
    }

    @And("adds more products to the cart")
    public void addMoreProducts() {
        home.addRandomProducts(1, 2);
    }

    @Then("the cart icon should reflect the updated number of selected products")
    public void verifyUpdatedCartIcon() {
        softAssert.assertEquals(
                home.getNumberOfProductsOnCartIcon(),
                home.getNumberOfSelectedProducts()
        );
    }

    @When("the user proceeds to checkout")
    public void proceedToCheckout() {
        new P02_HomePage(getDriver()).clickOnCartIcon();
        new P03_CartPage(getDriver()).clickOnCheckOutButton();
    }

    @And("enters valid checkout information")
    public void enterCheckoutInformation() throws FileNotFoundException {
        new P04_CheckOutPage(getDriver())
                .enterFirstName(DataUtil.getJsonData("information", "fName"))
                .enterLastName(DataUtil.getJsonData("information", "lName"))
                .enterPostalCode("123")
                .clickOnContinue();
    }

    @Then("the total price should be correctly calculated")
    public void verifyTotalPrice() {
        softAssert.assertEquals(
                new P05_OverviewPage(getDriver()).getTotal(),
                new P05_OverviewPage(getDriver()).calculateTotalPrice()
        );
    }

    @And("the user finishes the purchase")
    public void finishPurchase() {
        new P05_OverviewPage(getDriver()).clickOnFinishButton();
    }

    @Then("a thank you message should be displayed")
    public void verifyThankYouMessage() {
        softAssert.assertTrue(new P06_FinishPage(getDriver()).isVisibilityThanksMessage());
        softAssert.assertAll();
    }
}
