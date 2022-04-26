package selenium.steps;

import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import selenium.core.AppiumServer;
import selenium.pageobject.FlockLogin;
import org.junit.Assert;
import cucumber.runner.mobile.MobileRunner;
import cucumber.api.java.Before;

import java.net.MalformedURLException;

public class FlockLoginSteps {

//    AndroidDriver<MobileElement> driver;
//    AppiumServer appiumServer = new AppiumServer();

//    @Before("@setup")
//    public void setUp() {
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
//
//    }
//    @After("@destroy")
//    public void tearDown() throws Throwable {
//        driver.close();
//        appiumServer.stopServer();
//    }

    FlockLogin flockLogin;
    public FlockLoginSteps() {
        flockLogin = new FlockLogin();
    }
    @Given("^I click Sign In button$")
    public void iClickSignInButton() throws InterruptedException {
        System.out.println("11111");
        Thread.sleep(1000);
        flockLogin.clickSignInBtn();
    }

    @When("^I input username as \"(.*)\"$")
    public void iInputUsernameAs(String arg0) {
        flockLogin.inputUsername(arg0);
    }

    @And("^I input password as \"(.*)\"$")
    public void iInputPasswordAs(String arg0) {
        flockLogin.inputPassword(arg0);
    }

    @And("^I click Login button$")
    public void iClickLoginButton() {
        flockLogin.clickLoginBtn();
    }

    @Then("^Show error message \"(.*)\"$")
    public void showErrorMessage(String expected) {
        String actual = flockLogin.verifyErrorMsg();
        Assert.assertEquals(actual, expected);
    }

}
