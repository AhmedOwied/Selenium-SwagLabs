package StepDefinitions;

import Pages.P01_LoginPage;
import Pages.P02_HomePage;
import Pages.P03_CartPage;
import Pages.P05_OverviewPage;
import Utilities.DataUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

import static DriverFactory.DriverFactory.getDriver;

public class SD02_EmptyCartCheckout_Steps {

    SoftAssert softAssert = new SoftAssert();

    @Given("The user is logged in with valid credentials")
    public void login_with_valid_credentials() throws IOException {
        getDriver().get(DataUtil.getPropertyValue("environment", "LOGIN_URL"));
        new P01_LoginPage(getDriver())
                .enterUserName(DataUtil.getJsonData("validLoginData", "username"))
                .enterPassword(DataUtil.getJsonData("validLoginData", "password"))
                .clickOnLoginButton();
        softAssert.assertEquals(getDriver().getCurrentUrl(), DataUtil.getPropertyValue("environment", "HOME_URL"), "Assert After login redirection Failed");
    }

    @And("The user navigates to the cart without adding products")
    public void navigate_to_cart_without_adding_products() {
        new P02_HomePage(getDriver()).clickOnCartIcon();
    }

    @When("The user proceeds to checkout and enters valid personal information")
    public void fill_checkout_information() throws IOException {
        new P03_CartPage(getDriver()).clickOnCheckOutButton()
                .enterFirstName(DataUtil.getJsonData("information", "fName"))
                .enterLastName(DataUtil.getJsonData("information", "lName"))
                .enterPostalCode("123")
                .clickOnContinue();
    }

    @Then("The total price on the overview page should be {string}")
    public void verify_total_price(String expectedPrice) {
        softAssert.assertEquals(new P05_OverviewPage(getDriver()).getTotalPriceWithZero(), expectedPrice);
        softAssert.assertAll();
    }

    /*@After
    public void tearDown() {
        quitDriver();
    }*/
}
