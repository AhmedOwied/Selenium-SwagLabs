package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class P03_CartPage {
    static float totalPrice = 0;
    WebDriver driver;
    //locators
    private By checkOutButton = By.linkText("CHECKOUT");
    private By continueButton = By.xpath("//*[@id=\"cart_contents_container\"]/div/div[2]/a[1]");
    private By totalPriceInCartLocator = By.xpath("(//div[@class='inventory_item_price'])");
    private By shoppingCartIcon = By.id("shopping_cart_container");

    private By numbersOfProductsOnCartIcon = By.xpath("//span[contains(@class,'shopping_cart_badge')]");

    private By cartItemNames = By.className("inventory_item_name");
    private By productNamesInCartPage = By.xpath("//div[@class='inventory_item_name']");

    private By cancelButtonForIndexOne = By.xpath("(//button[contains(@class,'btn_secondary')])[1]");

    //Constructor
    public P03_CartPage(WebDriver driver) {
        this.driver = driver;
    }

    //methods

    public List<String> getProductNames() {
        List<WebElement> nameElements = driver.findElements(productNamesInCartPage);
        List<String> productNames = new ArrayList<>();

        for (WebElement element : nameElements) {
            productNames.add(element.getText());
        }
        return productNames;
    }

    public P03_CartPage clickOnCancelButtonForIndexOne() {
        Utility.clickOnElement(driver, cancelButtonForIndexOne);
        return this;
    }

    public String getNumberOfProductsOnCartIconInCartPage() {
        try {
            Utility.getText(driver, numbersOfProductsOnCartIcon);
            LogsUtils.info("Number of products On Cart: " + Utility.getText(driver, numbersOfProductsOnCartIcon));
            return Utility.getText(driver, numbersOfProductsOnCartIcon);
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }
    }

    /*Assert*/
    public boolean isProductForIndexOneDisplayedSuccess(String Pname) {
        List<String> productNamesII = getProductNames();
        return productNamesII.contains(Pname);
    }

    /*Get Price by selected Products In Cart*/
    public String getTotalPriceOfSelectedProducts() {
        try {
            List<WebElement> allPriceProductsInTheCart = driver.findElements(totalPriceInCartLocator); //6
            for (int i = 1; i <= allPriceProductsInTheCart.size(); i++) {
                By priceBySelectedProductsLocator = By.xpath("((//div[@class='inventory_item_price']))[" + i + "]");
                String fullPriceText = Utility.getText(driver, priceBySelectedProductsLocator);//"90.6"
                totalPrice += Float.parseFloat(fullPriceText);// //Float.parseFloat("90.6")  ->> 90.6 Integer
            }
            LogsUtils.info("TotalPrice: " + totalPrice);
            return String.valueOf(totalPrice);
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }
    }

    //TODO:: Complete This Method as logic
    public boolean isProductInCart(String expectedProductName) {
        List<WebElement> products = driver.findElements(cartItemNames);
        for (WebElement product : products) {
            String actualName = product.getText().trim();
            if (actualName.equalsIgnoreCase(expectedProductName)) {
                return true; // المنتج موجود
            }
        }
        return false; // المنتج مش موجود
    }

    public P04_CheckOutPage clickOnCheckOutButton() {
        Utility.clickOnElement(driver, checkOutButton);
        LogsUtils.info("Redirect To CheckOutPage Your Information");
        return new P04_CheckOutPage(driver);
    }

    public P02_HomePage clickOnContinueShoppingButton() {
        Utility.clickOnElement(driver, continueButton);
        LogsUtils.info("Redirect To CheckOutPage Your Information");
        return new P02_HomePage(driver);
    }

    /*public P02_HomePage clickOnContinueShopping(){
        driver.findElement(continueButton).click();
        return new P02_HomePage(driver);
    }*/
}
