package StepDefinitions;

import Pages.P02_HomePage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.asserts.SoftAssert;

import static DriverFactory.DriverFactory.getDriver;

public class SD05_ResetApp_Steps {

    SoftAssert softAssert = new SoftAssert();

    @When("I add one product to the cart")
    public void addOneProductToCart() {
        new P02_HomePage(getDriver()).addProductForIndexOne();
    }

    @Then("the cart icon should reflect the correct number of items")
    public void verifyCartIconProductCount() {
        P02_HomePage home = new P02_HomePage(getDriver());
        softAssert.assertEquals(home.getNumberOfProductsOnCartIcon(), home.getNumberOfSelectedProducts());
    }

    @When("I perform reset app state from the side menu")
    public void performResetAppState() {
        P02_HomePage home = new P02_HomePage(getDriver());
        home.clickOnSideMenu();
        home.clickOnResetAppState();
        home.clickOnCloseMenu();
    }

    @Then("the cart should be empty")
    public void verifyCartIsEmpty() {
        softAssert.assertTrue(new P02_HomePage(getDriver()).isCartReset());
    }

    @Then("all buttons should be reset to \"ADD TO CART\"")
    public void verifyAllButtonsResetToAddToCart() {
        softAssert.assertTrue(
                new P02_HomePage(getDriver()).areAllButtonResetToAddCartButton(),
                "All buttons are NOT reset to 'ADD TO CART' state after Reset App State."
        );
    }
}
