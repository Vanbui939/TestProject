package utils.settings;

import java.nio.file.Path;
import java.nio.file.Paths;

public class SeleniumConfig {
  private final static String SELENIUM_CONFIG_PATH =
      Paths.get(Paths.get("").toAbsolutePath().toString(), "src", "test", "resources", "configs",
          "Selenium.properties").toString();
  private final static ConfigReader CONFIG = new ConfigReader(SELENIUM_CONFIG_PATH);
  private final static String CHROME_DRIVER_MAC = CONFIG.getValue("chrome.driver.macos");
  private final static String CHROME_DRIVER_WINDOWS = CONFIG.getValue("chrome.driver.windows");
  private final static String CHROME_DRIVER_LINUX = CONFIG.getValue("chrome.driver.linux");
  private final static String FIREFOX_DRIVER_MAC = CONFIG.getValue("firefox.driver.macos");
  private final static String FIREFOX_DRIVER_WINDOWS = CONFIG.getValue("firefox.driver.windows");
  private final static String FIREFOX_DRIVER_LINUX = CONFIG.getValue("firefox.driver.linux");
  private final static Path DRIVER_PATH =
      Paths.get(Paths.get("").toAbsolutePath().toString(), "src", "test", "resources", "drivers");

  // Property
  public static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
  public static final String FIREFOX_DRIVER_PROPERTY = "webdriver.gecko.driver";
  public static final String IE_DRIVER_PROPERTY = "webdriver.ie.driver";
  public static final String EDGE_DRIVER_PROPERTY = "webdriver.edge.driver";

  public SeleniumConfig() {

  }

  public static String getChromeBinary() {
    if (System.getProperty("os.name").startsWith("Mac")) {
      return Paths.get(DRIVER_PATH.toString(), CHROME_DRIVER_MAC).toString();
    }
    else if (System.getProperty("os.name").startsWith("Ubuntu")){
      return Paths.get(DRIVER_PATH.toString(), CHROME_DRIVER_LINUX).toString();
    }
    else {
      return Paths.get(DRIVER_PATH.toString(), CHROME_DRIVER_WINDOWS).toString();

    }
  }

  public static String getFirefoxBinary() {
    if (System.getProperty("os.name").startsWith("Mac")) {
      return Paths.get(DRIVER_PATH.toString(), FIREFOX_DRIVER_MAC).toString();
    }
    else if (System.getProperty("os.name").startsWith("Ubuntu")){
      return Paths.get(DRIVER_PATH.toString(), FIREFOX_DRIVER_LINUX).toString();
    }
    else {
      return Paths.get(DRIVER_PATH.toString(), FIREFOX_DRIVER_WINDOWS).toString();
    }
  }

  public class Browser {
    public String driverName;
    public Path driverDir;

    public Browser(String browserName, String driverName, Path driverDir) {
      this.driverName = driverName;
      this.driverDir = driverDir;
    }

    public String getDriverPath() {
      return Paths.get(driverDir.toString(), this.driverName).toString();
    }

  }
}
