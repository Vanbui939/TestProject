package selenium.core;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
/*import io.appium.java_client.AppiumDriver;*/
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AppiumServer {
    private static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<RemoteWebDriver>();
    public static AppiumDriverLocalService service;
    public static AppiumServiceBuilder builder;
//    public DesiredCapabilities cap;
    public static DesiredCapabilities dc;

//    AndroidDriver<MobileElement> driver;
    File root = new File(System.getProperty("user.dir"));
    File app = new File(root, "/src/test/resources/app-flock-test.apk");

    public static RemoteWebDriver setUp() {
        startServer();
        return driver.get();
    }

    public static RemoteWebDriver startServer() {
        // apk Capabilities
        dc = new DesiredCapabilities();
        dc.setCapability("BROWSER_NAME", "Android");
        dc.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
        dc.setCapability("platformName", "Android");
        dc.setCapability("udid", "127.0.0.1:62001");
//		dc.setCapability("app", app.getAbsolutePath());
        dc.setCapability("newCommandTimeout", 1000);
        dc.setCapability("automationName", "UiAutomator2");
        dc.setCapability("appPackage", "trade.flock.app");
        dc.setCapability("appActivity", "trade.flock.app.MainActivity");
        dc.setCapability("noReset", "false");

        // Build the Appium Service
        builder = new AppiumServiceBuilder();
        builder.withIPAddress("0.0.0.0");
        builder.usingPort(4723);
        builder.withCapabilities(dc);
        builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
        builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");

        // Start the server with the builder
        try {
            /* service = builder.withLogFile(testLogFile).build(); */
            service = AppiumDriverLocalService.buildService(builder);
            service.start();
            System.out.println("Start Appium server 4723");
            /* assertTrue(testLogFile.exists()); */
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

//        driver = new AndroidDriver<MobileElement>(service.getUrl(), dc);
//        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
//        return driver;
        driver.set(new RemoteWebDriver(service.getUrl(), dc));
        driver.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver.get();
    }

    public static void captureScreenshot(String ssPath, String ssName) throws IOException {
        File screenshot = ((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File(Paths.get(ssPath, ssName).toString()));
    }



    public void stopServer() {
        service.stop();
    }
    public static RemoteWebDriver getDriver() {
        return driver.get();
    }

    public boolean checkIfServerIsRunnning(int port) {

        boolean isServerRunning = false;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.close();
        } catch (IOException e) {
            // If control comes here, then it means that the port is in use
            isServerRunning = true;
        } finally {
            serverSocket = null;
        }
        return isServerRunning;
    }
}
