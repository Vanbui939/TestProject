package factory;

import java.io.IOException;
import cucumber.runtime.java.picocontainer.PicoFactory;
import utils.testhelper.TestDataProvider;

public class DataContainer extends PicoFactory {

  public String api_token;
  public TestDataProvider testDataProvider;

  public DataContainer() throws IOException {
    testDataProvider = new TestDataProvider();

  }
}
