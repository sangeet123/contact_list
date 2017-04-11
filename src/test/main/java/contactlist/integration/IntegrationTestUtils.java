package contactlist.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by sangeet on 4/4/2017.
 */
@Component() public class IntegrationTestUtils {
  final static String accessToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb250YWN0bGlzd"
      + "CIsInVzZXJBY2NvdW50Ijp7ImlkIjoxLCJ1c2VybmFtZSI6ImNvbnRhY3RsaXN0IiwiYXV0aG9yaXRp"
      + "ZXMiOlsiUk9MRV9BUFBfQURNSU4iXX0sImV4cCI6MTQ5MTk2MjU0N30.MMoEwvUfkoNXLitBTo60GIGY5"
      + "kghYvyD1Upf04oLTrDP99sUKi8UKx-YvRX05RC4GL5Ct9udqRB16ru-TQU5Nw";
  private static int port;
  private static String baseUrl = "http://localhost:{port}";

  public static String getAccessToken() {
    return IntegrationTestUtils.accessToken;
  }

  public static String getEndPoint(final String uri) {
    return IntegrationTestUtils.baseUrl.replace("{port}", String.valueOf(IntegrationTestUtils.port))
        + uri;
  }

  @Value("${server.port}") public void setPort(int port) {
    IntegrationTestUtils.port = port;
  }
}
