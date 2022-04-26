package selenium.core;

import cucumber.api.testng.TestNGCucumberRunner;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;

public class MobileBasePage {
//    AndroidDriver<MobileElement> driver;
    private RemoteWebDriver driver;
//    AppiumServer appiumServer = new AppiumServer();

    public MobileBasePage(){
//        int port = 4723;
//        try {
//            if (!appiumServer.checkIfServerIsRunnning(port)) {
//                appiumServer.startServer();
//                appiumServer.stopServer();
//            } else {
//                System.out.println("Appium Server already running on Port - " + port);
//            }
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//        driver = appiumServer.startServer();
        if (AppiumServer.getDriver() == null) {
            driver = AppiumServer.setUp();
        } else {
            driver = AppiumServer.getDriver();
        }
    }
    public String getUrl() {
        return driver.getCurrentUrl();
    }


//    @BeforeClass(alwaysRun = true)
//    public void setUpClass() {
//        System.out.println("test Before classsadsdjasd");
//    }

}
