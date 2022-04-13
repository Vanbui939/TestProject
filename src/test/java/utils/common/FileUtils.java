package utils.common;

import java.io.File;
import java.util.List;

public class FileUtils {
  public static List<File> listFilesFolder(String folderPath, List<File> files) {
    File folder = new File(folderPath);

    // Get all files from a directory.
    File[] fList = folder.listFiles();
    if (fList != null)
      for (File file : fList) {
        if (file.isFile()) {
          files.add(file);
        } else if (file.isDirectory()) {
          listFilesFolder(file.getAbsolutePath(), files);
        }
      }
    return files;
  }

}
