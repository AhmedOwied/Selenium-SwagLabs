package StepDefinitions;

import Utilities.DataUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.testng.asserts.SoftAssert;

import static DriverFactory.DriverFactory.quitDriver;
import static DriverFactory.DriverFactory.setUpDriver;

public class Hooks {

    public static SoftAssert softAssert;

    @Before
    public void setUp() {
        String browser = System.getProperty("browser") != null
                ? System.getProperty("browser")
                : DataUtil.getPropertyValue("environment", "browser");
        setUpDriver(browser);

        // نبدأ SoftAssert جديد لكل سيناريو
        softAssert = new SoftAssert();
    }

    @After
    public void tearDown() {
        try {
            // تحقق من جميع الـ assertions في نهاية السيناريو
            if (softAssert != null) {
                softAssert.assertAll();
            }
        } catch (AssertionError e) {
            throw e; // يخلي السيناريو يفشل
        } finally {
            softAssert = null; // تنظيف
            quitDriver();
        }
    }
}
