package cucumber.runner.core;

import factory.DataContainer;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.Properties;
import org.apache.log4j.PropertyConfigurator;

public class Test {

  public static void main(String[] args) throws Exception {

    // setup logger
    Properties prop = new Properties();
    prop.load(new FileInputStream(Paths.get(Paths.get("").toAbsolutePath().toString(), "src",
        "test", "resources", "configs", "log4j.properties").toString()));
    PropertyConfigurator.configure(prop);

    DataContainer data = new DataContainer();
//    data.environment = data.testDataProvider.getEnvirontment();
    //System.out.println(System.getProperty("os.name"));


  }



}
