package contactlist.integration.contactlist.endpoints.get;

import contactlist.integration.IntegrationTestConfigurer;
import contactlist.integration.IntegrationTestUtils;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;

/**
 * Created by sangeet on 4/8/2017.
 */
@SqlGroup({ @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
    "classpath:sql/create-schema.sql", "classpath:sql/create-user.sql",
    "classpath:sql/create-contactlist.sql" }),
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {
        "classpath:sql/drop-schema.sql" }) }) public class GetContactLists
    extends IntegrationTestConfigurer {
  private final static String CONTACTLIST_ENTRY_ENDPOINT = "/contactlist";
  @Test()
  public void test_get_all_contactlists() throws Exception {
    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        when().
        get(IntegrationTestUtils.getEndPoint(CONTACTLIST_ENTRY_ENDPOINT)).
        then().
        statusCode(HttpStatus.OK.value()).
        contentType(JSON).body("name", containsInAnyOrder("Friends", "Family", "Others")).
        body("id", containsInAnyOrder(1, 2, 3));
  }

  @Test()
  public void test_get_all_contactlists_on_page_0_with_size_2() throws Exception{
    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        queryParam("page", 0).
        queryParam("size", 2).
        queryParam("sort", "name,asc").
        when().
        get(IntegrationTestUtils.getEndPoint(CONTACTLIST_ENTRY_ENDPOINT)).
        then().
        statusCode(HttpStatus.OK.value()).
        contentType(JSON).body("name", contains("Family", "Friends")).
        body("id", contains(2, 1));
  }
}
