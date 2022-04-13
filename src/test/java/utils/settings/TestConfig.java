package utils.settings;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.TimeZone;
import cucumber.api.Scenario;

public class TestConfig {
  public final static String TOOL_PATH =
      Paths.get(Paths.get("").toAbsolutePath().toString(), "src", "test", "resources", "tools")
          .toString();
  public final static String RESOURCE_PATH =
      Paths.get(Paths.get("").toString(), "src", "test", "resources").toString();
  // Executing scenario
  public static ThreadLocal<Scenario> scenario = new ThreadLocal<Scenario>();
  public final static String s3amazon = "Test/Web-Test/Test-Result/";
  public static String testID;
  public final static Path TARGET_PATH =
      Paths.get(Paths.get("").toAbsolutePath().toString(), "target");
  public final static Path CUCUMBER_REPORT_PATH =
      Paths.get(TARGET_PATH.toString(), "cucumber-reports");
  public final static Path HTML_REPORT_PATH =
      Paths.get(TARGET_PATH.toString(), "cucumber-html-reports");
  public final static Path SCREENCAPTURE_PATH = Paths.get(TARGET_PATH.toString(), "screenshot");
  public final static Path SUREFIRE_PATH = Paths.get(TARGET_PATH.toString(), "surefire-reports");
  public final static Path SCREEN_RECORDING_PATH =
      Paths.get(TARGET_PATH.toString(), "screenrecording");
  public final static String DB_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

  public static void setTestID() {
    if (testID == null) {
      testID = String
          .valueOf(Calendar.getInstance(TimeZone.getTimeZone("GMT-7")).getTime().getTime() / 1000);
    }
  }

}
