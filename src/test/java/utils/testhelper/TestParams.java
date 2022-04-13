package utils.testhelper;

public class TestParams {

  public static final boolean IMPORT_RESULT = isImportResult();
  public static final boolean UPLOAD_TEST = isUploadTest();
  public static final String ENVIRONMENT = getEnvironment();
  public static final boolean SCREEN_RECORDING = isRecording();
  public static final boolean DEBUGGING = isLogger();
  public static final boolean IS_HEADLESS = isHeadless();
  public static final boolean UPLOAD_FAILURE_ONLY = isUploadFailureOnly();
  // public static final boolean UPLOAD_S3 = isUploadS3();

  public static boolean isImportResult() {
    if (System.getProperty("importResult") != null) {
      return System.getProperty("importResult").equalsIgnoreCase("true");
    }
    return false;
  }
  public static boolean isHeadless() {
    if (System.getProperty("headless") != null) {
      if (System.getProperty("headless").equals("true")) {
        return true;
      }
    }
    return false;
  }


  public static boolean isUploadTest() {
    if (System.getProperty("uploadResult") != null) {
      return System.getProperty("uploadResult").equalsIgnoreCase("true");
    }
    return false;
  }

  public static String getEnvironment() {
    if (System.getProperty("environment") != null) {
      return System.getProperty("environment");
    }
    return "dev";
  }

  public static boolean isLogger() {
    if (System.getProperty("logging") != null) {
      return System.getProperty("logging").equalsIgnoreCase("true");
    }
    return false;
  }

  public static boolean isRecording() {
    if (System.getProperty("recording") != null) {
      return System.getProperty("recording").equalsIgnoreCase("true");
    }
    return false;
  }

  public static boolean isUploadS3() {
    if (System.getProperty("uploadS3") != null) {
      if (System.getProperty("uploadS3").equals("true")) {
        return true;
      }
    }
    return false;
  }

  public static boolean isUploadFailureOnly() {
    if (System.getProperty("uploadFailureOnly") != null) {
      if (System.getProperty("uploadFailureOnly").equals("true")) {
        return true;
      }
    }
    return false;
  }
}
