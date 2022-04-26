package cucumber.runner.mobile;

//import cucumber.api.CucumberOptions;
//import cucumber.api.junit.Cucumber;
//import org.junit.runner.RunWith;

//@RunWith(Cucumber.class)
//@CucumberOptions(format = { "pretty", "html:/home/ninad/eclipse-workspace/Reports/html/",
//        "json:/home/ninad/eclipse-workspace/Reports/cucumber-report.json" }, features = {
//        "src/test/resource" }, glue = "stepDefinitions", tags = { "@runfile" })
//public class MobileRunner {
//}

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import java.net.URL;

import cucumber.api.testng.PickleEventWrapper;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import selenium.core.DriverManager;
import selenium.core.MobileBasePage;
import utils.logging.Log;

@CucumberOptions(
        features = "src/test/resources/features/flockLogin.feature",
        plugin = {"pretty", "html:target/cucumber-reports/web/openPage",
                "json:target/cucumber-reports/web/openPage/Cucumber.json"},
        glue={"selenium/steps"},
        tags={}, //",@regression"
        strict = true,
        monochrome = true
        )

public class MobileRunner { //extends MobileBasePage

    private TestNGCucumberRunner testNGCucumberRunner;

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        System.out.println("Setup classssssssssssss");
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());

    }

    @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void scenario(PickleEventWrapper eventWrapper, CucumberFeatureWrapper featureWrapper)
            throws Throwable {
        System.out.println("Runner: Scenarios");
        testNGCucumberRunner.runScenario(eventWrapper.getPickleEvent());
    }
    @DataProvider
    public Object[][] scenarios() {
        return testNGCucumberRunner.provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        System.out.println("Teardown clasllllllllllllll");
        testNGCucumberRunner.finish();
    }
}
