package utils.testhelper;

import java.io.IOException;
import java.util.List;
import utils.common.ProcessUtils;

public class GenymotionControl {
  /**
   * Get device id by name
   */
  public static String getDeviceID(String name) throws IOException, InterruptedException {
    String[] command = {"/usr/local/bin/vboxmanage", "list", "vms"};

    String id = "";

    for (String info : ProcessUtils.startProcess(command)) {
      if (info.split("\"")[1].equals(name)) {
        id = info.substring(info.lastIndexOf(" ") + 2, info.length() - 1);
        break;
      }
    }

    return id;

  }

  public static boolean isDeviceRunning(String id) throws IOException, InterruptedException {
    // Return 1 if its running, else 0
    String[] command = {"/usr/local/bin/vboxmanage", "showvminfo", id};

    List<String> result = ProcessUtils.startProcess(command);

    Thread.sleep(3000);

    for (String line : result) {
      if (line.contains("running (")) {
        return true;
      }
    }

    return false;
  }

  /**
   * Start emulator by id or name
   * 
   * @param id
   * @throws IOException
   * @throws InterruptedException
   */
  public static void startDevice(String id) throws IOException, InterruptedException {
    String[] command = {"open", "-a", "/Applications/Genymotion.app/Contents/MacOS/player.app",
        "--args", "--vm-name", id};

    ProcessUtils.startProcess(command);
  }

  /**
   * Stop emulator by id or name
   * 
   * @param id
   * @throws IOException
   * @throws InterruptedException
   */
  public static void stopDevice(String id) throws IOException, InterruptedException {
    // power off vbox to prevent issue
    String[] powerOffCmd = {"/usr/local/bin/vboxmanage", "controlvm", id, "poweroff", "||", "true"};
    ProcessUtils.startProcess(powerOffCmd);

    // get PID of started device using genymotion player
    String[] getPID = {"ps", "-A"};
    List<String> result = ProcessUtils.startProcess(getPID);
    Thread.sleep(3000);
    String pid = "";
    for (String task : result) {
      if (task.contains(id) && task.contains("player")) {
        pid = task.trim().split(" ")[0];
        break;
      }
    }

    // Kill genymotion device
    String[] killPID = {"kill", pid};
    ProcessUtils.startProcess(killPID);
  }

}
