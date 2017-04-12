package contactlist.integration;

import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Created by sangeet on 4/4/2017.
 */
@Component() public class IntegrationTestUtils {
  private static int port;
  private static String baseUrl = "http://localhost:{port}";

  private static Boolean isAuthorized = false;
  private static String accessToken;

  public static String getAccessToken() {
    if (!isAuthorized) {
      final Map<String, String> map = new HashMap<>();
      map.put("username", "contactlist");
      map.put("password", "v3rystr0ngPassword");
      final String url = IntegrationTestUtils.getEndPoint("/login");
      final Response response = given().
          body(map).
          when().
          post(url);
      response.then().statusCode(HttpStatus.OK.value());
      accessToken = response.getHeader("Authorization").replace("Bearer", "");
      isAuthorized = true;
    }
    return accessToken;
  }

  public static String getEndPoint(final String uri) {
    return IntegrationTestUtils.baseUrl.replace("{port}", String.valueOf(IntegrationTestUtils.port))
        + uri;
  }

  @Value("${server.port}") public void setPort(int port) {
    IntegrationTestUtils.port = port;
  }
}
