package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P07_ProductOfDetailsPage {
    private WebDriver driver;

    //Locator
    private By addToCartButton = By.xpath("//button[contains(@class,'btn_inventory')]");
    private By backButton = By.className("inventory_details_back_button");
    private By numbersOfProductsOnCartIcon = By.xpath("//span[contains(@class,'shopping_cart_badge')]");
    private By productName = By.xpath("//div[@class='inventory_details_name']");


    /*Constructor*/
    public P07_ProductOfDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    //methods
    public String getNumberOfProductsOnCartIcon() {
        try {
            LogsUtils.info("Number of products On Cart: " + Utility.getText(driver, numbersOfProductsOnCartIcon));
            return Utility.getText(driver, numbersOfProductsOnCartIcon);
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }
    }

    public String getNameForProductPage() {
        return Utility.getText(driver, productName);
    }

    public P07_ProductOfDetailsPage clickOnAddToCartButton() {
        Utility.clickOnElement(driver, addToCartButton);
        return this;
    }

    public P02_HomePage clickOnBackButton() {
        Utility.clickOnElement(driver, backButton);
        return new P02_HomePage(driver);
    }


}
