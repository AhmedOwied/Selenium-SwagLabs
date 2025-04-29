package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class P06_FinishPage {
    private final WebDriver driver;

    private By thankMessage = By.className("complete-header");

    public P06_FinishPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isVisibilityThanksMessage() {
        LogsUtils.info("Thanks Message is: " + driver.findElement(thankMessage).getText());
        return Utility.generalWait(driver).until(ExpectedConditions.visibilityOfElementLocated(thankMessage))
                .isDisplayed();
    }

}
