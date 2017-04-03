package contactlist.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import contactlist.ContactListApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by sangeet on 4/2/2017.
 */
@RunWith(SpringRunner.class) @Configuration() @SpringBootTest(classes = {
    ContactListApplication.class },
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) @TestPropertySource(locations = {
    "classpath:test.properties" }) public class IntegrationTestConfigurer {

  final String accessToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb250YWN0bGlzdCIsInVzZXJBY2NvdW50Ijp7ImlkIjoxLCJ1c2VybmFtZSI6ImNvbnRhY3RsaXN0IiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BUFBfQURNSU4iXX0sImV4cCI6MTQ5MTk2MjU0N30.MMoEwvUfkoNXLitBTo60GIGY5kghYvyD1Upf04oLTrDP99sUKi8UKx-YvRX05RC4GL5Ct9udqRB16ru-TQU5Nw";
  @Value("${server.port}") private int port;
  private TestRestTemplate restTemplate = new TestRestTemplate();
  private String baseUrl = "http://localhost:{port}";

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public TestRestTemplate getRestTemplate() {
    return restTemplate;
  }

  public void setRestTemplate(TestRestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public String getEndPoint(final String uri) {
    return this.baseUrl.replace("{port}", String.valueOf(this.port)) + uri;
  }

  public HttpEntity<String> prepareAuthorizationHeader() {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", accessToken);
    HttpEntity<String> entity = new HttpEntity<>(headers);
    return entity;
  }

  public ResponseEntity<String> doGet(final String uri, final String... params) {
    final String endPoint = getEndPoint(uri);
    ResponseEntity<String> responseEntity = restTemplate
        .exchange(endPoint, HttpMethod.GET, prepareAuthorizationHeader(), String.class);
    return responseEntity;
  }

  public <T> T readEntity(final ResponseEntity<String> responseEntity,
      final TypeReference<T> typeReference) throws Exception {
    final ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(responseEntity.getBody(), typeReference);
  }
}
