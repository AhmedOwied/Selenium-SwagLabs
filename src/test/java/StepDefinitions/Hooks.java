package StepDefinitions;

import Utilities.DataUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import static DriverFactory.DriverFactory.quitDriver;
import static DriverFactory.DriverFactory.setUpDriver;

public class Hooks {

    @Before
    public void setUp() {
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : DataUtil.getPropertyValue("environment", "browser");
        setUpDriver(browser);
    }

    @After
    public void tearDown() {
        quitDriver();
    }
}
