package utils.testhelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import api.core.BaseAPI;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import utils.settings.TestConfig;

public class TestDataProvider {
  private final static Path ENVIRONMENT =
      Paths.get(TestConfig.RESOURCE_PATH, "data", TestParams.ENVIRONMENT, "environment.json");
  private final static Path ACCOUNT =
          Paths.get(TestConfig.RESOURCE_PATH, "data", TestParams.ENVIRONMENT, "account.json");




//  public static Environment getEnvirontment() throws IOException {
//      // read json
//      File jsonFile = new File(ENVIRONMENT.toString());
//      JsonNode envNode = new ObjectMapper().readTree(jsonFile);
//
//      Environment env = new Environment();
//      env.site = envNode.get("site").asText();
//      env.api_path = envNode.get("api_path").asText();
//      BaseAPI.url = env.api_path;
//      return env;
//    }
//
//
//  public Account getAdminAccount() throws IOException, IOException {
//    // read json
//    File jsonFile = new File(ACCOUNT.toString());
//    JsonNode envNode = new ObjectMapper().readTree(jsonFile);
//
//    Account adAcc = new Account();
//    adAcc.userName = envNode.get("userName").asText();
//    adAcc.passWord = envNode.get("passWord").asText();
//
//    return adAcc;
//  }
//  public Account getUserByRole(String role) throws Exception {
//    File jsonFile = new File(ACCOUNT.toString());
//    JsonNode envNode = new ObjectMapper().readTree(jsonFile).get("users");
//    System.out.println("test " + envNode);
//    Account acc = new Account();
//    for (JsonNode account : envNode) {
//
//      if (account.get("role").asText().equals(role)) {
//        acc.userName = account.get("userName").asText();
//        acc.passWord = account.get("passWord").asText();
//        System.out.println("test role" + acc.userName + acc.passWord);
//        break;
//      }
//    }
//    return acc;
//  }



}
