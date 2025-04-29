package Utilities;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Utility {
    private static final String SCREEN_PATH = "test-outputs/ScreenShots/";
    /*Documentation for Each Function */

    /**
     * Function for finding and Click on Element After waiting to be Clickable
     *
     * @param driver
     * @param locator
     */

    //TODO:: clicking ON Element
    public static void clickOnElement(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(locator));
        driver.findElement(locator).click();
    }

    /**
     * Function for finding and sendData to locator After waiting to be Locator visible
     *
     * @param driver
     * @param locator
     * @param text
     */

    //TODO:: Sending Data to Element
    public static void sendData(WebDriver driver, By locator, String text) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).sendKeys(text);
    }

    //TODO:: GetText for Locator

    public static String getText(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator).getText();
    }

    //TODO:: General Wait
    public static WebDriverWait generalWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(5));

    }

    //ToDo:: Convert to WebElement
    public static WebElement byToWebElement(WebDriver driver, By locator) {
        return driver.findElement(locator);
    }

    //ToDo:: Selecting From DropDown
    public static void selectingFromDropDown(WebDriver driver, By locator, String option) {
        // Select select = new Select(byToWebElement(driver,locator)).selectByVisibleText(option);
        new Select(byToWebElement(driver, locator)).selectByVisibleText(option);
    }


    //TODO:: Taking ScreenShots
    public static void takeScreenShot(WebDriver driver, String screenName) {
        try {
            File screen_src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File screen_Des = new File(SCREEN_PATH + screenName + "-" + getTimeStamp() + ".png");

            FileUtils.copyFile(screen_src, screen_Des);
            //Attach ScreenShot To Allure
            Allure.addAttachment(screenName, Files.newInputStream(Path.of(screen_Des.getPath())));
            //Files.newInputStream   يمعني ان ال الملف ده عندي في البروجكت مش برا

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
/*
    //TODO:: Taking FUllScreenShots
    public static void takeFullScreen(WebDriver driver, By locator, String testName) {
        try {
            // إنشاء مجلد Screenshots إذا غير موجود
            String screenshotDir = "test-outputs/ScreenShots";
            new File(screenshotDir).mkdirs();

            // اسم الصورة بالتاريخ والتست نيم
            String fileName = testName + "_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            // التقاط سكرين شوت كاملة مع تظليل العنصر
            Shutterbug.shootPage(driver, Capture.FULL_SCROLL)
                    .highlight(byToWebElement(driver, locator))
                    .save(screenshotDir + "/" + fileName);

            LogsUtils.info(" Screenshot saved: " + fileName + ".png");

        } catch (Exception e) {
            LogsUtils.error(" Screenshot failed: " + e.getMessage());
        }
    }
*/

    //TODO:: Scrolling To Element
    public static void scrollingByElement(WebDriver driver, By Locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", byToWebElement(driver, Locator));
    }

    //TODO:: Get TimeStamp
    public static String getTimeStamp() {
        Date currentDate = new Date();
        return new SimpleDateFormat("yyyy-MM-dd-h-m-ss a").format(currentDate);
    }

    //TODO:: Uploading files using ROBOT


    //TODO::Create Random products
    public static int generateRandomNumber(int upperBound) {  //method to generate number
        return new Random().nextInt(upperBound) + 1; //Upperbound inclusive without (+1) upperbound is Exclusive
    }

    public static Set<Integer> generateUniqueNumber(int numberOfProductsNeeded, int totalNumberOfProducts) { //5-->50
        Set<Integer> generateNumbers = new HashSet<>(); //Using Set To Ensure Unique Number
        while (generateNumbers.size() < numberOfProductsNeeded) {
            int randomNumber = generateRandomNumber(totalNumberOfProducts);
            generateNumbers.add(randomNumber);
        }
        return generateNumbers;

    }


    public static Set<Cookie> getAllCookies(WebDriver driver) {
        return driver.manage().getCookies();
    }

    public static void restoreSession(WebDriver driver, Set<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            driver.manage().addCookie(cookie);
        }
    }
}
