package StepDefinitions;

import Pages.P02_HomePage;
import Pages.P07_ProductOfDetailsPage;
import Utilities.LogsUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static DriverFactory.DriverFactory.getDriver;
import static StepDefinitions.Hooks.softAssert;

public class SD07_E2E_AddProductFromDetailsSteps {

    String productNameFromHome;
    String productNameFromDetails;
    P02_HomePage home;
    P07_ProductOfDetailsPage detailsPage;

    //Given I am on the login page
    //When  I login with valid credentials
    //TODO:: This Steps Written in CommonSteps

    @And("I view the first product details")
    public void i_view_the_first_product_details() {
        home = new P02_HomePage(getDriver());
        productNameFromHome = home.getProductNameForIndexOne();
        LogsUtils.info("ProductName in HomePage: " + productNameFromHome);
        productNameFromDetails = home.clickOnProductNameForIndexOne().getNameForProductPage();
        LogsUtils.info("ProductName in ProductDetails: " + productNameFromDetails);
    }

    @Then("the product name on the product details page should match the name on the home page")
    public void verify_product_name_matches() {
        softAssert.assertEquals(productNameFromDetails, productNameFromHome);
    }

    @When("I add the product to the cart from the product details page")
    public void add_product_from_details_page() {
        new P07_ProductOfDetailsPage(getDriver()).clickOnAddToCartButton();

    }

    @Then("the cart icon on the product details page should show {int} item")
    public void verify_cart_on_details_page(int count) {
        softAssert.assertEquals(new P07_ProductOfDetailsPage(getDriver()).getNumberOfProductsOnCartIcon(), String.valueOf(count));
    }

    @When("I go back to the home page")
    public void go_back_to_home_page() {
        new P07_ProductOfDetailsPage(getDriver()).clickOnBackButton();
    }

    @Then("the cart icon on the home page should show {int} item")
    public void verify_cart_on_home_page(int count) {
        softAssert.assertEquals(new P02_HomePage(getDriver()).getNumberOfProductsOnCartIcon(), String.valueOf(count));
    }
}
