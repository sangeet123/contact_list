package contactlist.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;

/**
 * Created by sangeet on 4/4/2017.
 */
@Component() public class IntegrationTestUtils {
  final static String accessToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb250YWN0bGlzdCIsInVzZXJBY2NvdW50Ijp7ImlkIjoxLCJ1c2VybmFtZSI6ImNvbnRhY3RsaXN0IiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BUFBfQURNSU4iXX0sImV4cCI6MTQ5MTk2MjU0N30.MMoEwvUfkoNXLitBTo60GIGY5kghYvyD1Upf04oLTrDP99sUKi8UKx-YvRX05RC4GL5Ct9udqRB16ru-TQU5Nw";
  private static int port;
  private static TestRestTemplate restTemplate = new TestRestTemplate();
  private static String baseUrl = "http://localhost:{port}";

  public static int getPort() {
    return port;
  }

  @Value("${server.port}") public void setPort(int port) {
    IntegrationTestUtils.port = port;
  }

  public static TestRestTemplate getRestTemplate() {
    return restTemplate;
  }

  public static void setRestTemplate(TestRestTemplate restTemplate) {
    IntegrationTestUtils.restTemplate = restTemplate;
  }

  public static String getBaseUrl() {
    return baseUrl;
  }

  public static void setBaseUrl(String baseUrl) {
    IntegrationTestUtils.baseUrl = baseUrl;
  }

  public static String getEndPoint(final String uri) {
    return IntegrationTestUtils.baseUrl.replace("{port}", String.valueOf(IntegrationTestUtils.port))
        + uri;
  }

  public static HttpEntity<String> prepareHttpEntity() {
    MultiValueMap<String, String> headers = new HttpHeaders();
    headers.put("Authorization", Arrays.asList(accessToken));
    HttpEntity<String> entity = new HttpEntity<>(headers);
    return entity;
  }

  public static HttpEntity<String> prepareHttpEntityForPutAndPost(final String body) {
    MultiValueMap<String, String> headers = new HttpHeaders();
    headers.put("Authorization", Arrays.asList(accessToken));
    headers.put("Content-Type", Arrays.asList("application/json"));
    HttpEntity<String> entity = new HttpEntity<>(body, headers);
    return entity;
  }

  public static ResponseEntity<String> doGet(final String uri, final String... params) {
    final String endPoint = getEndPoint(uri);
    ResponseEntity<String> responseEntity = restTemplate
        .exchange(endPoint, HttpMethod.GET, prepareHttpEntity(), String.class);
    return responseEntity;
  }

  public static ResponseEntity<String> doDelete(final String uri, final String... params) {
    final String endPoint = getEndPoint(uri);
    ResponseEntity<String> responseEntity = restTemplate
        .exchange(endPoint, HttpMethod.DELETE, prepareHttpEntity(), String.class);
    return responseEntity;
  }

  public static ResponseEntity<String> doPost(final String uri, final Object body,
      final String... params) {
    final String endPoint = getEndPoint(uri);
    ResponseEntity<String> responseEntity = null;
    try {
      responseEntity = restTemplate
          .exchange(endPoint, HttpMethod.POST, prepareHttpEntityForPutAndPost(jsonSerilize(body)),
              String.class,params);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return responseEntity;
  }

  public static ResponseEntity<String> doPut(final String uri, final Object body,
      final String... params) {
    final String endPoint = getEndPoint(uri);
    ResponseEntity<String> responseEntity = null;
    try {
      responseEntity = restTemplate
          .exchange(endPoint, HttpMethod.PUT, prepareHttpEntityForPutAndPost(jsonSerilize(body)),String.class,params);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return responseEntity;
  }

  public static <T> T readEntity(final String body,
      final TypeReference<T> typeReference) throws Exception {
    final ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(body, typeReference);
  }

  public static String jsonSerilize(final Object obj) throws Exception {
    final ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(obj);
  }

}
