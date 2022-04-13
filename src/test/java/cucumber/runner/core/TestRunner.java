package cucumber.runner.core;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Properties;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.PickleEventWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import selenium.core.DriverManager;
import utils.logging.Log;
import utils.settings.TestConfig;

public class TestRunner {
  protected TestNGCucumberRunner testNGCucumberRunner;

  protected Path propFile =
      Paths.get(Paths.get("").toAbsolutePath().toString(), "target", "env.properties");
  protected Properties prop = new Properties();

  protected String projectName;

  protected Date startTime = null;
  protected Date endTime = null;
  public static int gatewayErrorCount = 0;
  public static int loadingTimeOut = 0;
  public static int appCrashesCount = 0;

  /**
   * Before all, do setup stuffs for the test to run
   * 
   * @throws Exception
   */
  @BeforeClass(alwaysRun = true)
  public void setUpClass() throws Exception {
    // setup logger
    Properties prop = new Properties();
    prop.load(new FileInputStream(
        Paths.get(TestConfig.RESOURCE_PATH, "configs", "log4j.properties").toString()));
    PropertyConfigurator.configure(prop);

    // Set test ID for the test
    // For uploading purpose
    TestConfig.setTestID();

    testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
  }

  @BeforeSuite
  public void beforeSuite() {

  }

  @AfterSuite
  public void afterSuite() {

  }

  @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
  public void scenario(PickleEventWrapper eventWrapper, CucumberFeatureWrapper featureWrapper)
      throws Throwable {

    Log.details("Feature: " + featureWrapper.toString());
    testNGCucumberRunner.runScenario(eventWrapper.getPickleEvent());
  }

  @DataProvider
  public Object[][] scenarios() {
    return testNGCucumberRunner.provideScenarios();
  }

  @AfterClass(alwaysRun = true)
  public void tearDownClass() throws Exception {
    // <---- After feature ----->
    testNGCucumberRunner.finish();
    DriverManager.quitDriver();
  }

}
