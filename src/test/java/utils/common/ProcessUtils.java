package utils.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import utils.settings.TestConfig;

public class ProcessUtils {
  public static class ReadStream implements Runnable {
    String name;
    InputStream is;
    Thread thread;
    public List<String> returnList = new ArrayList<String>();

    public ReadStream(String name, InputStream is) {
      this.name = name;
      this.is = is;
    }

    public void start() {
      thread = new Thread(this);
      thread.start();
    }

    @Override
    public void run() {
      try {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        while (true) {
          String s = br.readLine();
          if (s == null)
            break;
          System.out.println("[" + name + "] " + s);
          returnList.add(s);
        }
        is.close();
      } catch (Exception ex) {
        System.out.println("Problem reading stream " + name + "... :" + ex);
        ex.printStackTrace();
      }
    }
  }

  public static List<String> startProcess(String command) throws IOException, InterruptedException {
    List<String> returnList = new ArrayList<String>();
    // getRuntime: Returns the runtime object associated with the current Java
    // application.
    // exec: Executes the specified string command in a separate process.
    System.out.println(command);
    Process p = Runtime.getRuntime().exec(command);

    ReadStream s1 = new ReadStream("stdin", p.getInputStream());
    ReadStream s2 = new ReadStream("stderr", p.getErrorStream());
    s1.start();
    s2.start();
    p.waitFor();

    returnList = s1.returnList;

    return returnList;
  }

  public static List<String> startProcess(String[] command)
      throws IOException, InterruptedException {
    List<String> returnList = new ArrayList<String>();

    ProcessBuilder processBuilder = new ProcessBuilder();

    processBuilder.command(command);
    System.out.println(String.join(" ", command));

    Process p = processBuilder.start();

    ReadStream s1 = new ReadStream("stdin", p.getInputStream());
    ReadStream s2 = new ReadStream("stderr", p.getErrorStream());
    s1.start();
    s2.start();
    p.waitFor();

    returnList = s1.returnList;
    while (!p.isAlive()) {
      break;
    }
    return returnList;

  }

  public static void createFolder(String folderPath, String folderName)
      throws IOException, InterruptedException {
    String folder = Paths.get(folderPath, folderName).toString();
    String command = "";
    if (System.getProperty("os.name").startsWith("Mac")) {
      command = "mkdir -p " + folder;
    } else {
      command = "cmd.exe /c mkdir " + folder;
    }

    startProcess(command);
  }

  public static void startRecording(String bashScript, String filePath)
      throws IOException, InterruptedException {
    String command = "";
    if (System.getProperty("os.name").startsWith("Mac")) {
      command = "/usr/local/bin/ffmpeg -f avfoundation -i 0 -pix_fmt yuv420p -vcodec mpeg4 -r 3 "
          + filePath;

      // Delete old bash script
      File file = new File(bashScript);
      file.delete();

      // Generate new bash scripts
      PrintWriter writer = new PrintWriter(bashScript, "UTF-8");
      writer.println(command + ";");
      writer.close();

      Thread.sleep(1000);

      // Set permission
      Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
      perms.add(PosixFilePermission.OWNER_READ);
      perms.add(PosixFilePermission.OWNER_WRITE);
      perms.add(PosixFilePermission.OWNER_EXECUTE);
      // add group permissions
      perms.add(PosixFilePermission.GROUP_READ);
      perms.add(PosixFilePermission.GROUP_WRITE);
      perms.add(PosixFilePermission.GROUP_EXECUTE);
      Files.setPosixFilePermissions(Paths.get(bashScript), perms);

      Thread.sleep(1000);

      // Run bash script
      command = "/usr/bin/open -a Terminal " + bashScript;
    } else {
      command =
          "cmd.exe /c start C:\\ffmpeg\\bin\\ffmpeg.exe -f gdigrab -framerate 3 -i desktop -vcodec libx264 -pix_fmt yuv420p "
              + filePath;

      // Generate new batch scripts
      PrintWriter writer = new PrintWriter(
          Paths.get(TestConfig.TOOL_PATH.toString(), "startRecording.bat").toString(), "UTF-8");
      writer.println(command);
      writer.close();

      // Add some wait time here
      Thread.sleep(2000);

      command = Paths.get(TestConfig.TOOL_PATH.toString(), "startRecording.bat").toString();
    }
    startProcess(command);
  }

  public static void stopRecording(String sendSignalPath, String PID)
      throws IOException, InterruptedException {
    String command = "";
    if (System.getProperty("os.name").startsWith("Mac")) {
      command = "pkill ffmpeg";
    } else {
      command = sendSignalPath + " " + PID;
    }
    System.out.println(command);
    startProcess(command);
  }

}
