package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P05_OverviewPage {
    private final WebDriver driver;
    //Locators
    private By subTotal = By.className("summary_subtotal_label");
    private By tax = By.className("summary_tax_label");
    private By totalPrice = By.className("summary_total_label");
    private By finishButton = By.xpath("//a[.='FINISH']");

    public P05_OverviewPage(WebDriver driver) {
        this.driver = driver;
    }

    public Float getSubTotal() {
        String subtotal = Utility.getText(driver, subTotal).replace("Item total: $", "");
        return Float.parseFloat(subtotal);
    }

    public Float getTax() {
        String Tax = Utility.getText(driver, tax).replace("Tax: $", "");
        return Float.parseFloat(Tax);
    }

    public Float getTotal() {
        String TotalNumber = Utility.getText(driver, totalPrice).replace("Total: $", "");
        LogsUtils.info("Actual Total Price: " + TotalNumber);
        return Float.parseFloat(TotalNumber);

    }

    public Float calculateTotalPrice() {  //getSubTotal+getTax
        float total = getSubTotal() + getTax();
        LogsUtils.info("calculated Total Price: " + (getSubTotal() + getTax()));
        return total;
    }

    public boolean comparingTotalPrice() {  //calculateTotalPrice & getTotal
        return calculateTotalPrice().equals(getTotal());
    }

    public P06_FinishPage clickOnFinishButton() {
        Utility.clickOnElement(driver, finishButton);
        return new P06_FinishPage(driver);
    }

    public String getTotalPriceWithZero() {
        String tPrice = Utility.getText(driver, totalPrice).replace("Total: $", "");
        LogsUtils.info("Actual Total Price: " + tPrice);
        return tPrice;
    }


}
