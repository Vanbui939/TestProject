//package api.login;
//import api.core.BaseAPI;
//import com.fasterxml.jackson.databind.JsonNode;
//import org.apache.http.entity.ContentType;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import utils.encode.Base64Encoder;
//import utils.encode.Encode;
//import utils.testhelper.TestDataProvider;
//
//import java.io.IOException;
//import java.net.URISyntaxException;
//import java.security.KeyManagementException;
//import java.security.KeyStoreException;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//
//import static utils.encode.Encode.toHash512Base64;
//public class LoginApi extends BaseAPI {
//    public LoginApi() throws IOException {
//        super();
//        this.uriBuilder.setPath(TestDataProvider.getEnvirontment().api_path + "/auth/login");
//    }
//
//    public String getLoginToken(String email, String password)
//            throws NoSuchAlgorithmException, IOException, KeyManagementException, KeyStoreException,
//            URISyntaxException {
//        System.out.println("uri: " + uriBuilder);
//
//        System.out.println("email: " + email);
//        System.out.println("password: " + password);
//        JsonNode node =
//                objectMapper
//                        .createObjectNode()
//                        .put("email", email)
//                        .put("password", password);
//
//        HttpHeaders header = new HttpHeaders();
//        header.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
//
//        JsonNode resultAsJson = BaseAPI.doPost(this.uriBuilder.build(), header, node);
////        System.out.println(resultAsJson.get("data").asText());
//        return resultAsJson.get("data").get("token").asText();
//    }
//    public String getLoginBU(String email, String password)
//            throws NoSuchAlgorithmException, IOException, KeyManagementException, KeyStoreException,
//            URISyntaxException {
////        System.out.println("uri: " + uriBuilder);
////
////        System.out.println("email: " + email);
////        System.out.println("password: " + password);
//        JsonNode node =
//                objectMapper
//                        .createObjectNode()
//                        .put("email", email)
//                        .put("password", password);
//
//        HttpHeaders header = new HttpHeaders();
//        header.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
//
//        JsonNode resultAsJson = BaseAPI.doPost(this.uriBuilder.build(), header, node);
////        System.out.println(resultAsJson.get("data").asText());
//        return resultAsJson.get("data").get("business_unit").get("business_unit_name").asText();
//    }
//}
