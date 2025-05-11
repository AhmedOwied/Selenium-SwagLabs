package StepDefinitions;

import Pages.P02_HomePage;
import Utilities.DataUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.asserts.SoftAssert;

import static DriverFactory.DriverFactory.getDriver;

public class SD04_E2E_LogoutSteps {

    SoftAssert softAssert = new SoftAssert();

    //ToDo::These common steps are defined in CommonSteps.java:
    //@Given("I am on the login page")
    //Navigate to login page
    //@When("I login with valid credentials")
    //Login with valid credentials


    @And("I add the first product to the cart")
    public void iAddFirstProductToCart() {
        new P02_HomePage(getDriver()).addProductForIndexOne();
    }

    @And("I open the side menu")
    public void iOpenSideMenu() {
        new P02_HomePage(getDriver()).clickOnSideMenu();
    }

    @And("I click on the logout button")
    public void iClickOnLogoutButton() {
        new P02_HomePage(getDriver()).clickOnLogout();
    }

    @Then("I should be redirected back to the login page")
    public void iShouldBeRedirectedBackToLoginPage() {
        String actualUrl = getDriver().getCurrentUrl();
        String expectedUrl = DataUtil.getPropertyValue("environment", "LOGIN_URL");
        softAssert.assertEquals(actualUrl, expectedUrl, "Redirection to login page after logout failed");
    }
}
