package DriverFactory;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    //SetDriver
    public static void setUpDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions option1 = new ChromeOptions();
                option1.addArguments("--start-maximized");
                option1.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                option1.addArguments("--incognito");
                driverThreadLocal.set(new ChromeDriver(option1));
                break;
            case "firefox":
                driverThreadLocal.set(new FirefoxDriver());
                break;
            default:
                EdgeOptions option2 = new EdgeOptions();
                option2.addArguments("--start-maximized");
                option2.setPageLoadStrategy(PageLoadStrategy.NORMAL); //Search? in Document
                driverThreadLocal.set(new EdgeDriver(option2));
        }
    }


    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void quitDriver() {

        getDriver().quit();
        //driverThreadLocal.remove();


    }

}

/**/
