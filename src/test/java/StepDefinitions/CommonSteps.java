package StepDefinitions;

import Pages.P01_LoginPage;
import Pages.P02_HomePage;
import Utilities.DataUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.asserts.SoftAssert;

import static DriverFactory.DriverFactory.getDriver;

public class CommonSteps {

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        getDriver().get(DataUtil.getPropertyValue("environment", "LOGIN_URL"));
    }

    @When("I login with valid credentials")
    public void loginWithValidCredentials() {
        new P01_LoginPage(getDriver())
                .enterUserName("standard_user")
                .enterPassword("secret_sauce")
                .clickOnLoginButton();
    }


    @Then("the cart icon should reflect the correct number of selected products")
    public void verifyCartIconMatchesSelectedProducts() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(
                new P02_HomePage(getDriver()).getNumberOfProductsOnCartIcon(),
                new P02_HomePage(getDriver()).getNumberOfSelectedProducts()
        );
    }
}
