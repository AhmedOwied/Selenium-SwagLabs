package Listeners;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

import static DriverFactory.DriverFactory.getDriver;

public class IInvokeMethodListeners implements IInvokedMethodListener {

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {

    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        /*Not Working Success*/
        //Utilities.Utility.takeFullScreen(getDriver(), new P02_HomePage(getDriver()).getNumberOfSelectedProductsOnCart(), "FullScreen");
        if (testResult.getStatus() == ITestResult.FAILURE) {
            LogsUtils.info("Test Case :" + testResult.getName() + " Failed ");
            Utility.takeScreenShot(getDriver(), testResult.getName());
        }
    }
}
