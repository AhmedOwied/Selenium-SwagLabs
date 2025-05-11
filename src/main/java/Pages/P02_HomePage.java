package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;

public class P02_HomePage {
    static float totalPrice = 0;
    //private static List<WebElement> all_Products;
    //private static List<WebElement> selected_Products;
    //private static List<WebElement> inverntory_Items;

    String productName = "Sauce Labs Bike Light";
    //Locators
    private By addToCartButtonForAllProducts = By.xpath("//button[contains(@class,'btn_inventory')]");
    private By numbersOfProductsOnCartIcon = By.xpath("//span[contains(@class,'shopping_cart_badge')]");
    private By numberOfSelectedProducts = By.xpath("//button[text()='REMOVE']");
    private By cartIcon = By.xpath("//a[contains(@class,'shopping_cart_link')]");
    private By priceBySelectedProductsLocator = By.xpath("//button[.='REMOVE']//preceding-sibling::div[@class='inventory_item_price']");
    private By inventoryItemsLocator = By.cssSelector("div[class='inventory_item']");
    private By itemName = By.xpath("//div[@class='inventory_item_name']");
    /*Locator For ProductOfIndex_One*/
    private By productNameIndexOne = By.xpath("(//div[@class='inventory_item_name'])[1]");
    private By addToCartButtonForIndexOne = By.xpath("(//button[contains(@class,'btn_inventory')])[1]");

    /*Menu Bar*/
    private By menuButton = By.className("bm-burger-button");
    private By logOutButton = By.id("logout_sidebar_link");
    private By resetButton = By.id("reset_sidebar_link");
    private By closeMenu = By.xpath("//button[.='Close Menu']");


    private WebDriver driver;

    public P02_HomePage(WebDriver driver) {
        this.driver = driver;
    }

    //methods
    public P02_HomePage addAllProductsToCart() {
        List<WebElement> all_Products = driver.findElements(addToCartButtonForAllProducts); //1-2-3-4-5-6
        LogsUtils.info("Number of Products:" + all_Products.size());
        for (int i = 1; i <= all_Products.size(); i++) {
            By addToCartButtonForAllProducts = By.xpath("(//button[contains(@class,'btn_inventory')])[" + i + "]");
            Utility.clickOnElement(driver, addToCartButtonForAllProducts); //added From (1) To (6)
        }
        return this;
    }

    public String getProductNameForIndexOne() {
        return driver.findElement(productNameIndexOne).getText();
    }

    public P07_ProductOfDetailsPage clickOnProductNameForIndexOne() {
        Utility.clickOnElement(driver, productNameIndexOne);
        return new P07_ProductOfDetailsPage(driver);
    }

    public P02_HomePage addProductForIndexOne() {
        Utility.clickOnElement(driver, addToCartButtonForIndexOne);
        return this;
    }

    /*
    public P02_HomePage addSpecificProduct_two() {
        By addToCart = RelativeLocator.with(addToCartButton).below(itemName);
        Utility.clickOnElement(driver, addToCart);
        return this;
    }*/

    //TODO::method no work Success try later time
    /*
    public P02_HomePage addSpecificProduct() {
        List<WebElement> inventory_Items = driver.findElements(inventoryItemsLocator); //Store from 1 To 6
        LogsUtils.info("Number of Products:" + inventory_Items.size());
        //Search For specific product
        for (int i = 0; i < inventory_Items.size(); i++) { // size->6 products
            WebElement index = inventory_Items.get(i);
            String productNameII = index.findElement(itemName).getText();
            if (productNameII.equalsIgnoreCase(productName)) {
                index.findElement(addToCartButton).click();
                LogsUtils.info("Product added to cart: " + productNameII);
                break;
            }
        }
        return this;
    }
     */

    // menu bar method
    public P02_HomePage clickOnSideMenu() {
        Utility.clickOnElement(driver, menuButton);
        return this;
    }

    public P02_HomePage clickOnCloseMenu() {
        Utility.clickOnElement(driver, closeMenu);
        return this;
    }

    public P02_HomePage clickOnLogout() {
        Utility.clickOnElement(driver, logOutButton);
        return this;
    }

    public P02_HomePage clickOnResetAppState() {
        Utility.clickOnElement(driver, resetButton);
        return this;
    }

    public boolean isCartReset() {
        return driver.findElements(numbersOfProductsOnCartIcon).size() == 0;
    }

    public boolean areAllButtonResetToAddCartButton() {
        List<WebElement> buttons = driver.findElements(addToCartButtonForAllProducts);
        for (WebElement btn : buttons) {
            if (!btn.getText().equalsIgnoreCase("ADD TO CART")) {
                return false;
            }

        }
        return true;
    }

    //end to All method For menuBar

    public String getNumberOfSelectedProducts() {
        try {
            List<WebElement> selected_Products = driver.findElements(numberOfSelectedProducts);
            LogsUtils.info("Number Of Selected Products: " + (selected_Products.size()));
            return String.valueOf(selected_Products.size());
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }

    }

    public String getNumberOfProductsOnCartIcon() {
        try {
            LogsUtils.info("Number of products On Cart: " + Utility.getText(driver, numbersOfProductsOnCartIcon));
            return Utility.getText(driver, numbersOfProductsOnCartIcon);
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }
    }

    public P02_HomePage addRandomProducts(int numberOfProductsNeeded, int totalNumberOfProducts) {

        Set<Integer> randomsNumbers = Utility.generateUniqueNumber(numberOfProductsNeeded, totalNumberOfProducts);
        for (int random : randomsNumbers) {
            LogsUtils.info("randomNumber " + random);
            By addToCartButtonForAllProducts = By.xpath("(//button[contains(@class,'btn_inventory')])[" + random + "]");
            Utility.clickOnElement(driver, addToCartButtonForAllProducts);
        }

        return this;

    }

    /*Get Price by selected Products In Home*/
    public String getTotalPriceOfSelectedProducts() {
        try {
            List<WebElement> priceOfSelectedProducts = driver.findElements(priceBySelectedProductsLocator);
            for (int i = 1; i <= priceOfSelectedProducts.size(); i++) {
                By priceBySelectedProductsLocator = By.xpath("(//button[.='REMOVE']//preceding-sibling::div[@class='inventory_item_price'])[" + i + "]");
                String fullPriceText = Utility.getText(driver, priceBySelectedProductsLocator);//"$90.6"
                totalPrice += Float.parseFloat(fullPriceText.replace("$", ""));// //Float.parseFloat("90.6")  ->> 90.6 Float
            }
            LogsUtils.info("Total Price: " + totalPrice);
            return String.valueOf(totalPrice);
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }
    }


    /*Added success in Page test as Assertions */
    public boolean comparingNumberOfSelectedProductWithCart() {
        return getNumberOfProductsOnCartIcon().equals(getNumberOfSelectedProducts()); //Dynamic
        //return getNumberOfProductsOnCartIcon().equals("6"); //assume one product added in the future , equals("6") not correct succcess
    }


    public P03_CartPage clickOnCartIcon() {
        Utility.clickOnElement(driver, cartIcon);
        return new P03_CartPage(driver);
    }

    /*Assertion*/
    /*
    public boolean verifyUrl(String exceptedUrl) {
        try {
            Utility.generalWait(driver).until(ExpectedConditions.urlToBe(exceptedUrl));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    */

}
