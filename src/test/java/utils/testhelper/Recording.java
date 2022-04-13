package utils.testhelper;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import utils.common.ProcessUtils;
import utils.settings.TestConfig;

public class Recording {
  public static String recordingPID = ""; // for windows only
  static String getPIDPath = Paths.get(TestConfig.TOOL_PATH, "ffmpeg.bat").toString();
  static String sendSignalPath = Paths.get(TestConfig.TOOL_PATH, "SendSignal.exe").toString();
  static String startRecordingMacPath =
      Paths.get(TestConfig.TOOL_PATH, "startRecordingMac.sh").toString();
  public static String recordingName = "screenRecord_" + TestConfig.testID + ".mkv";

  /**
   * return path to recording file
   * 
   * @return
   */
  public static String getRecordingPath() {
    return Paths.get(TestConfig.TARGET_PATH.toString(), recordingName).toString();
  }

  /**
   * Start recording
   * 
   * @throws IOException
   * @throws InterruptedException
   */
  public static void start() throws IOException, InterruptedException {
    ProcessUtils.startRecording(startRecordingMacPath, getRecordingPath());
  }

  /**
   * Stop recording
   * 
   * @throws IOException
   * @throws InterruptedException
   */
  public static void stop() throws IOException, InterruptedException {
    List<String> taskList = new ArrayList<String>();

    if (System.getProperty("os.name").startsWith("Mac")) {
      taskList = ProcessUtils.startProcess("ps -A | grep startRecordingMac.sh");
      for (String task : taskList) {
        if (task.contains("startRecordingMac.sh")) {
          recordingPID = task.trim().replaceAll(" +", " ").split(" ")[0];
        }
      }
    } else {
      taskList = ProcessUtils.startProcess(getPIDPath);
      for (String task : taskList) {
        if (task.contains("ffmpeg.exe") && !task.contains("cmd.exe")) {
          recordingPID = task.trim().replaceAll(" +", " ").split(" ")[1];
        }
      }
    }

    ProcessUtils.stopRecording(sendSignalPath, recordingPID);
  }
}
