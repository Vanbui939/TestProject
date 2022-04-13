package utils.settings;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
  private Properties properties;

  public ConfigReader(String configPath) {
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(configPath));
      properties = new Properties();
      try {
        properties.load(reader);
        reader.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      throw new RuntimeException("File not found " + configPath);
    }
  }

  public boolean getBooleanValue(String key) {
    return Boolean.valueOf((properties.getProperty(key)));
  }

  public String getValue(String key) {
    return properties.getProperty(key);
  }

  public int getIntValue(String key) {
    return Integer.parseInt(properties.getProperty(key));

  }

}
