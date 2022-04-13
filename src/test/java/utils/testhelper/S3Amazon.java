package utils.testhelper;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import utils.common.FileUtils;
import utils.logging.Log;
import utils.settings.TestConfig;

public class S3Amazon {
  static String bucketName = "example-automation-evidence";
  static AWSCredentials credentials =
      new BasicAWSCredentials("AKIAXUAG74D7IYTKLDFXABC", "noaqF9esatNy5IjYAKd5A5PD5rbPm1UzacmCOajABC");
  static AmazonS3 s3client = AmazonS3ClientBuilder.standard()
      .withCredentials(new AWSStaticCredentialsProvider(credentials))
      .withRegion(Regions.AP_SOUTHEAST_1).build();
  static String s3Dest = TestConfig.s3amazon + TestConfig.testID;

  public static String uploadCucumberReport() {
    String htmlFolder = Paths.get(TestConfig.HTML_REPORT_PATH.toString()).toString();
    List<File> fileList = FileUtils.listFilesFolder(htmlFolder, new ArrayList<File>());
    Log.details("Bucket: " + bucketName);

    Log.details("Destination: " + s3Dest);

    String htmlPath = "";

    for (File file : fileList) {
      if (!file.getName().contains(".mp4")) {
        Log.details("Upload file: " + file.getAbsolutePath());
        s3client.putObject(new PutObjectRequest(bucketName,
            s3Dest + file.getAbsolutePath().replace(htmlFolder, "")
                .replace(System.getProperty("file.separator"), "/"),
            file).withCannedAcl(CannedAccessControlList.PublicRead));
        if (file.getName().equals("overview-features.html")) {
          htmlPath = s3client.getUrl(bucketName, s3Dest + "/" + file.getName()).toString();
        }
      }
    }

    return htmlPath;

  }

  public static String uploadScreenRecording(String folder, String fileName) {
    try {

      Log.details("Upload file: " + Paths.get(folder, fileName).toString());
      s3client.putObject(new PutObjectRequest(bucketName, s3Dest + "/" + fileName,
          new File(Paths.get(folder, fileName).toString()))
              .withCannedAcl(CannedAccessControlList.PublicRead));
      return s3client.getUrl(bucketName, s3Dest + "/" + fileName).toString();
    } catch (Exception e) {
      Log.details("Couldn't upload video");
      Log.details(e.getMessage());
      return "";
    }
  }

  public static String uploadScreenCapture(String folder, String fileName) {
    File file = Paths.get(folder, fileName).toFile();
    String ssUrl = "";
    Log.details("Upload screen capture: " + file.getAbsolutePath());
    s3client.putObject(new PutObjectRequest(bucketName,
        s3Dest + file.getAbsolutePath().replace(folder, "")
            .replace(System.getProperty("file.separator"), "/"),
        file).withCannedAcl(CannedAccessControlList.PublicRead));
    ssUrl = s3client.getUrl(bucketName, s3Dest + "/" + file.getName()).toString();

    return ssUrl;
  }

  public static String uploadTestNGEmailableReport() {
    String htmlFolder = Paths.get(TestConfig.SUREFIRE_PATH.toString(), "html").toString();
    List<File> fileList = FileUtils.listFilesFolder(htmlFolder, new ArrayList<File>());

    Log.details("Bucket: " + bucketName);

    Log.details("Destination: " + s3Dest);

    String htmlUrl = "";

    for (File file : fileList) {
      Log.details("Upload file: " + file.getAbsolutePath());
      s3client.putObject(new PutObjectRequest(bucketName,
          s3Dest + file.getAbsolutePath().replace(htmlFolder, "")
              .replace(System.getProperty("file.separator"), "/"),
          file).withCannedAcl(CannedAccessControlList.PublicRead));
      if (file.getName().equals("index.html")) {
        htmlUrl = s3client.getUrl(bucketName, s3Dest + "/" + file.getName()).toString();
      }
    }

    return htmlUrl;
  }

}
