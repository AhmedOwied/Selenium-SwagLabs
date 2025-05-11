package StepDefinitions;

import Pages.P02_HomePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static DriverFactory.DriverFactory.getDriver;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SD05_E2E_ResetAppStateSteps {


    @When("I add one product to the cart")
    public void addOneProductToCart() {
        new P02_HomePage(getDriver()).addProductForIndexOne();
    }

    @Then("the cart icon should reflect the correct number of items")
    public void verifyCartIconProductCount() {
        P02_HomePage home = new P02_HomePage(getDriver());
        assertEquals(home.getNumberOfProductsOnCartIcon(), home.getNumberOfSelectedProducts());
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
        assertTrue(new P02_HomePage(getDriver()).isCartReset());

    }

    @And("all buttons should be reset to \"ADD TO CART\"")
    public void verifyAllButtonsResetToAddToCart() {
        assertTrue(new P02_HomePage(getDriver()).areAllButtonResetToAddCartButton(),
                "All buttons are NOT reset to 'ADD TO CART' state after Reset App State."
        );
        //System.out.println("Result: " + new P02_HomePage(getDriver()).areAllButtonResetToAddCartButton());


    }
}
