package api.core;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.runner.core.TestRunner;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import utils.logging.Log;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class BaseAPI {

  public static String url;
  public static ThreadLocal<String> token = new ThreadLocal<String>();
  protected final static ObjectMapper objectMapper = new ObjectMapper();
  public final static String API_VERSION = "20200423";
  protected String path;
  protected URIBuilder uriBuilder;

  public BaseAPI() {
    uriBuilder = new URIBuilder();
    uriBuilder.setPath(BaseAPI.url + this.path);
  }

  public static JsonNode doPost(URI uri, HttpHeaders header, JsonNode jsonNode)
      throws IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
    HttpComponentsClientHttpRequestFactory requestFactory = getRequestFactory();
    RestTemplate restTemplate = new RestTemplate(requestFactory);

    HttpEntity<String> request = new HttpEntity<String>(jsonNode.toString(), header);

    Log.details("Post with API url: " + uri);
    Log.details("Request header: " + request.getHeaders().toString());
    Log.details("Request body: " + request.getBody().toString());

    ResponseEntity<String> result = null;

    try {
      result = restTemplate.exchange(uri, HttpMethod.POST, request, String.class);
    } catch (HttpStatusCodeException e) {
      if (e.getResponseBodyAsString().contains("502")) {
        TestRunner.gatewayErrorCount += 1;
      }
      Log.details(e.getResponseBodyAsString());
      throw e;
    }

    JsonNode resultAsJson = objectMapper.readTree(result.getBody());

    return resultAsJson;
  }

  public static JsonNode doPut(URI uri, HttpHeaders header, JsonNode jsonNode)
      throws IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
    HttpComponentsClientHttpRequestFactory requestFactory = getRequestFactory();
    RestTemplate restTemplate = new RestTemplate(requestFactory);

    HttpEntity<String> request = new HttpEntity<String>(jsonNode.toString(), header);

    Log.details("Put with API url: " + uri);
    Log.details("Request header: " + request.getHeaders().toString());
    Log.details("Request body: " + request.getBody().toString());

    ResponseEntity<String> result = null;

    try {
      result = restTemplate.exchange(uri, HttpMethod.PUT, request, String.class);
    } catch (HttpStatusCodeException e) {
      if (e.getResponseBodyAsString().contains("502")) {
        TestRunner.gatewayErrorCount += 1;
      }
      Log.details(e.getResponseBodyAsString());
      throw e;
    }

    JsonNode resultAsJson = objectMapper.readTree(result.getBody());

    return resultAsJson;
  }

  public static JsonNode doGet(URI uri, HttpHeaders header, JsonNode node)
      throws IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
    HttpComponentsClientHttpRequestFactory requestFactory = getRequestFactory();
    RestTemplate restTemplate = new RestTemplate(requestFactory);

    HttpEntity<String> request = new HttpEntity<String>(node.toString(), header);

    Log.details("Get with API url: " + uri);
    Log.details("Request header: " + request.getHeaders().toString());
    Log.details("Request body: " + request.getBody().toString());

    ResponseEntity<String> result = null;

    try {
      result = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
    } catch (HttpStatusCodeException e) {
      if (e.getResponseBodyAsString().contains("502")) {
        TestRunner.gatewayErrorCount += 1;
      }
      Log.details(e.getResponseBodyAsString());
      throw e;
    }

    JsonNode resultAsJson = objectMapper.readTree(result.getBody());

    return resultAsJson;
  }

  public static JsonNode doGet(URI uri, HttpHeaders header)
      throws IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
    HttpComponentsClientHttpRequestFactory requestFactory = getRequestFactory();
    RestTemplate restTemplate = new RestTemplate(requestFactory);

    HttpEntity<String> request = new HttpEntity<String>(null, header);

    Log.details("Get with API url: " + uri);
    Log.details("Request header: " + request.getHeaders().toString());

    ResponseEntity<String> result = null;

    try {
      result = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
    } catch (HttpStatusCodeException e) {
      if (e.getResponseBodyAsString().contains("502")) {
        TestRunner.gatewayErrorCount += 1;
      }
      Log.details(e.getResponseBodyAsString());
      throw e;
    }

    JsonNode resultAsJson = objectMapper.readTree(result.getBody());

    return resultAsJson;
  }

  public static JsonNode doDelete(URI uri, HttpHeaders header)
      throws IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
    HttpComponentsClientHttpRequestFactory requestFactory = getRequestFactory();
    RestTemplate restTemplate = new RestTemplate(requestFactory);

    HttpEntity<String> request = new HttpEntity<String>(null, header);

    Log.details("Delete with API url: " + uri);
    Log.details("Request header: " + request.getHeaders().toString());

    ResponseEntity<String> result = null;

    try {
      result = restTemplate.exchange(uri, HttpMethod.DELETE, request, String.class);
    } catch (HttpStatusCodeException e) {
      if (e.getResponseBodyAsString().contains("502")) {
        TestRunner.gatewayErrorCount += 1;
      }
      Log.details(e.getResponseBodyAsString());
      throw e;
    }

    JsonNode resultAsJson = objectMapper.readTree(result.getBody());

    return resultAsJson;
  }


  private static HttpComponentsClientHttpRequestFactory getRequestFactory()
      throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
    TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

    SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
        .loadTrustMaterial(null, acceptingTrustStrategy).build();

    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

    CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf)
        .setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
        .build();

    HttpComponentsClientHttpRequestFactory requestFactory =
        new HttpComponentsClientHttpRequestFactory();

    requestFactory.setHttpClient(httpClient);
    requestFactory.setConnectTimeout(10000);
    requestFactory.setReadTimeout(60000);
    requestFactory.setConnectionRequestTimeout(10000);
    return requestFactory;
  }
}
