package utils.testhelper;

//import api.slack.IncomingWebhook;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cucumber.api.Result;
import cucumber.api.Scenario;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.reducers.ReducingMethod;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import selenium.core.DriverManager;
import utils.common.FileUtils;
import utils.logging.Log;
import utils.settings.TestConfig;

public class TestResult {

  public static void sendWebResultToSlack(String projectName, int total, int passed, int failed,
      int skipped, String html, Properties prop) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode node = mapper.createObjectNode();
    JsonNode attachmentNode = mapper.createObjectNode();
    ((ObjectNode) attachmentNode).put("fallback", "Required plain-text summary of the attachment.");
    // status color
    if (failed > 0) {
      // red for any failure or error
      ((ObjectNode) attachmentNode).put("color", "#F3441F");
    } else {
      if (skipped > 0) {
        // orange for any skipped
        ((ObjectNode) attachmentNode).put("color", "#E8AF2A");
      } else {
        // green for all passed
        ((ObjectNode) attachmentNode).put("color", "#36a64f");
      }
    }
    ((ObjectNode) attachmentNode).put("mrkdwn_in", "[\"text\", \"pretext\"]")
        .put("pretext", projectName).put("title", "Cucumber test report").put("title_link", html);

    String text = "Tests run: " + total;
    text += "\nPassed: " + passed + ", Failed: " + failed + ", Skipped: " + skipped;
    text += "\n" + "*Platform*: " + prop.getProperty("Platform") + ", *browser*: "
        + prop.getProperty("Browser");
    text += "\n" + "For more details please check <" + prop.getProperty("Test_Plan")
        + "|Jira test plan> and <" + prop.getProperty("Jenkins") + "|Jenkins job>";

    ((ObjectNode) attachmentNode).put("text", text).put("ts", TestConfig.testID);

    ArrayNode attachments = mapper.createArrayNode().add(attachmentNode);

    ((ObjectNode) node).putArray("attachments").addAll(attachments);

    Log.details(node.toString());
//    IncomingWebhook.sendMessage(node);
//    if (failed > 0 && TestParams.getEnvironment().contains("prod")) {
//      // For production using only
//      IncomingWebhook.sendMessage(node, true);
//    }
  }

  public static void smokeTestResultToSlack(String projectName, int total, int failed, int skipped,
      String html, Properties prop) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode node = mapper.createObjectNode();
    JsonNode attachmentNode = mapper.createObjectNode();
    ((ObjectNode) attachmentNode).put("fallback", "Required plain-text summary of the attachment.");
    // status color
    if (failed > 0) {
      // red for any failure or error
      ((ObjectNode) attachmentNode).put("color", "#F3441F");
    } else {
      if (skipped > 0) {
        // orange for any skipped
        ((ObjectNode) attachmentNode).put("color", "#E8AF2A");
      } else {
        // green for all passed
        ((ObjectNode) attachmentNode).put("color", "#36a64f");
      }
    }

    ((ObjectNode) attachmentNode).put("mrkdwn_in", "[\"text\", \"pretext\"]")
        .put("pretext", projectName).put("title", "Cucumber test report").put("title_link", html);

    String text = "Tests run: " + total + ", Failures: " + failed + ", Skipped: " + skipped;
    text += "\n" + "*Platform*: " + prop.getProperty("Platform") + ", *browser*: "
        + prop.getProperty("Browser");

    ((ObjectNode) attachmentNode).put("text", text).put("ts", TestConfig.testID);

    ArrayNode attachments = mapper.createArrayNode().add(attachmentNode);

    ((ObjectNode) node).putArray("attachments").addAll(attachments);

    Log.details(node.toString());
//    IncomingWebhook.sendMessage(node);
  }

  public static void cucumberHtmlResultWeb(String projectName, Path propPath) {
    File reportOutputDirectory = new File("target");
    List<String> jsonFiles = new ArrayList<>();

    // Find all Cucumber.json files then add into report
    List<File> files = FileUtils.listFilesFolder(TestConfig.CUCUMBER_REPORT_PATH.toString(),
        new ArrayList<File>());
    for (File file : files) {
      if (file.getName().equals("Cucumber.json")) {
        jsonFiles.add(file.getAbsolutePath());
      }
    }

    // String buildNumber = "1";

    Configuration configuration = new Configuration(reportOutputDirectory, projectName);
    // optional configuration - check javadoc for details
    // configuration.addPresentationModes(PresentationMode.RUN_WITH_JENKINS);
    // configuration.setBuildNumber(buildNumber);
    // addidtional metadata presented on main page

    // optionally add metadata presented on main page via properties file
    List<String> classificationFiles = new ArrayList<>();
    classificationFiles.add(propPath.toString());
    // classificationFiles.add("properties-2.properties");
    configuration.addClassificationFiles(classificationFiles);
    configuration.addReducingMethod(ReducingMethod.MERGE_FEATURES_BY_ID);
    ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);

    reportBuilder.generateReports();
  }

  public static void embedScreenCapture(Scenario scenario, String ssName) throws IOException {

    byte[] screenshot = new byte[0];
    if (TestParams.UPLOAD_TEST) {
      String folder = TestConfig.SCREENCAPTURE_PATH.toString();
      // Capture screenshot
      if (DriverManager.getDriver() != null) {
        DriverManager.captureScreenshot(folder, ssName);
      }
//      if (AndroidManager.getDriver() != null) {
//        AndroidManager.captureScreenshot(folder, ssName);
//      }
//      if (IOsManager.getDriver() != null) {
//        IOsManager.captureScreenshot(folder, ssName);
//      }

      // Upload into S3amazon
      String ssUrl = S3Amazon.uploadScreenCapture(folder, ssName);
      String html = "<img src=\"" + ssUrl + "\" alt=\"Screenshot\">";
      scenario.embed(html.getBytes(), "text/html");
    } else {
      if (DriverManager.getDriver() != null) {
        screenshot =
            ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
      }
      scenario.embed(screenshot, "image/png"); // Stick it in the report
    }
  }

  public static void embedScreenRecording(Scenario scenario, File record, String type) {
    if (record == null) {
      return;
    }
    String folder = TestConfig.SCREEN_RECORDING_PATH.toString();
    String html;
//     don't upload or embed if passed
    if (TestParams.UPLOAD_FAILURE_ONLY && scenario.getStatus() == Result.Type.PASSED) {
      return;
    }
    if (TestParams.UPLOAD_TEST) {
      /* Upload into S3amazon */
      String recordUrl = S3Amazon.uploadScreenRecording(folder, record.getName());
      html = "<video width=\"640\" height=\"480\" controls>\n" + "  <source src=\"" + recordUrl
          + "\" type=\"video/mp4\">\n" + "</video>";
    } else {
      html = "<video width=\"640\" height=\"480\" controls>\n" + "  <source src=\""
          + Paths.get(folder, record.getName()).toString() + "\" type=\"video/mp4\">\n"
          + "</video>";
    }
    scenario.embed(html.getBytes(), type);
  }

  public static void testngScreenshot(String ssName) throws IOException {
    String folder = TestConfig.SCREENCAPTURE_PATH.toString();
    // Capture screenshot
    DriverManager.captureScreenshot(folder, ssName);

    if (TestParams.UPLOAD_TEST) {
      // Upload into S3amazon
      String ssUrl = S3Amazon.uploadScreenCapture(folder, ssName);
      String html = "<img src=\"" + ssUrl + "\" alt=\"Screenshot\">";
      Reporter.log(html);
    } else {
      // Local img
      String path = Paths.get(folder, ssName).toString();
      String html = "<img src=\"" + path + "\" alt=\"Screenshot\">";
      Reporter.log(html);
    }
  }
}
