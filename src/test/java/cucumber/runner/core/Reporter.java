package cucumber.runner.core;

//import api.jira.Jira;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.mail.MessagingException;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.xml.XmlSuite;
import org.uncommons.reportng.HTMLReporter;
import utils.common.MailBox;
import utils.enums.Browser;
import utils.logging.Log;
import utils.settings.TestConfig;
import utils.testhelper.S3Amazon;
import utils.testhelper.TestParams;
import utils.testhelper.TestResult;

public class Reporter extends HTMLReporter {

  @Override
  public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
      String outputDirectory) {
    System.setProperty("org.uncommons.reportng.escape-output", "false");
    super.generateReport(xmlSuites, suites, outputDirectory);

    int testCount = 0;
    int passed = 0;
    int failed = 0;
    int skipped = 0;

    String suiteName = "";
    Date startDate = null;
    Date endDate = null;

    // Iterating over each suite included in the test
    for (ISuite suite : suites) {
      // Following code gets the suite name
      suiteName = suite.getName();
      // Getting the results for the said suite
      Map<String, ISuiteResult> suiteResults = suite.getResults();
      for (ISuiteResult sr : suiteResults.values()) {

        ITestContext tc = sr.getTestContext();
        passed += tc.getPassedTests().getAllResults().size();
        failed += tc.getFailedTests().getAllResults().size();
        skipped += tc.getSkippedTests().getAllResults().size();

        // Set startdate and enddate
        if (startDate == null) {
          startDate = tc.getStartDate();
        } else {
          if (startDate.getTime() <= tc.getStartDate().getTime()) {
            startDate = tc.getStartDate();
          }
        }
        if (endDate == null) {
          endDate = tc.getEndDate();
        } else {
          if (endDate.getTime() >= tc.getEndDate().getTime()) {
            endDate = tc.getEndDate();
          }
        }

      }

    }
    // count all test
    testCount = passed + failed + skipped;

    // properties
    Path propFile =
        Paths.get(Paths.get("").toAbsolutePath().toString(), "target", "env.properties");
    Properties prop = new Properties();
    // Store properties
    try {
      prop.store(new FileOutputStream(propFile.toFile()), "");
    } catch (IOException e3) {
      e3.printStackTrace();
      return;
    }
    prop.setProperty("Platform", System.getProperty("os.name"));
    prop.setProperty("Browser", Browser.getBrowser().toString());
    prop.setProperty("Test_Plan", "");
    prop.setProperty("Jenkins", "");

    // Generate cucumber html report
    TestResult.cucumberHtmlResultWeb(suiteName, propFile);

    if (TestParams.isUploadTest()) {
      // upload test result
      String htmlUrl = S3Amazon.uploadCucumberReport();
      Log.details("Cucumber report: " + htmlUrl);

      try {
        TestResult.sendWebResultToSlack(suiteName, testCount, passed, failed, skipped, htmlUrl,
            prop);
      } catch (IOException e1) {
        e1.printStackTrace();
        return;
      }

      // get start, end, duration of the test
      DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      String start = dateFormat.format(startDate);
      String end = dateFormat.format(endDate);
      long diff = endDate.getTime() - startDate.getTime();
      String duration = String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(diff),
          TimeUnit.MILLISECONDS.toSeconds(diff)
              - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(diff)));
//
//      try {
//        new MailBox().sendMailTestResult(TestConfig.svcMailbox, TestConfig.mailDest, testCount,
//            failed, 0, skipped, htmlUrl, start, end, duration);
//      } catch (MessagingException e) {
//        e.printStackTrace();
//      }
    }
//    if (TestParams.isImportResult()) {
//      try {
//        Jira.importTestResult();
//      } catch (IOException | InterruptedException e) {
//        e.printStackTrace();
//        return;
//      }
//    }
  }
}
