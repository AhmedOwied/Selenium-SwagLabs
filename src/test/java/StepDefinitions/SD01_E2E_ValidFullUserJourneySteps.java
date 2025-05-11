package StepDefinitions;

import Pages.*;
import Utilities.DataUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import static DriverFactory.DriverFactory.getDriver;

public class SD01_E2E_ValidFullUserJourneySteps {


    //ToDo::These common steps are defined in CommonSteps.java:
    //@Given("I am on the login page")
    //Navigate to login page
    //@When("I login with valid credentials")
    //Login with valid credentials

    @Then("I should be redirected to the home page")
    public void verifyHomePageRedirection() {
        String actualUrl = getDriver().getCurrentUrl();
        String expectedUrl = DataUtil.getPropertyValue("environment", "HOME_URL");
        Assert.assertEquals(actualUrl, expectedUrl, "After login redirection failed");
    }

    @When("I add All products to the cart")
    public void addRandomProducts() {
        new P02_HomePage(getDriver()).addAllProductsToCart();
    }

    /*@Then("the cart icon should reflect the correct number of selected products")
    public void verifyCartCount() {
        String actualCount = new P02_HomePage(getDriver()).getNumberOfProductsOnCartIcon();
        String expectedCount = new P02_HomePage(getDriver()).getNumberOfSelectedProducts();
        Assert.assertEquals(actualCount, expectedCount);
    }*/   //TODO::Written in CommandSteps is Duplicated in some Classes

    @When("I proceed to checkout")
    public void proceedToCheckout() {
        new P02_HomePage(getDriver()).clickOnCartIcon();
        new P03_CartPage(getDriver()).clickOnCheckOutButton();
    }

    @When("I fill in my shipping information:")
    public void fillShippingInfo() {
        new P04_CheckOutPage(getDriver())
                .enterFirstName("Ahmed")
                .enterLastName("Owied")
                .enterPostalCode("12352")
                .clickOnContinue();
    }

    @Then("the total price should be calculated correctly")
    public void verifyTotalPrice() {
        Assert.assertTrue(new P05_OverviewPage(getDriver()).comparingTotalPrice());
    }

    @When("I complete the purchase")
    public void completePurchase() {
        new P05_OverviewPage(getDriver()).clickOnFinishButton();
    }

    @Then("I should see the order confirmation message")
    public void verifyConfirmationMessage() {
        Assert.assertTrue(new P06_FinishPage(getDriver()).isVisibilityThanksMessage());
    }


}


