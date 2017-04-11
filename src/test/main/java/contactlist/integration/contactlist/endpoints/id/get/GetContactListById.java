package contactlist.integration.contactlist.endpoints.id.get;

import contactlist.integration.IntegrationTestConfigurer;
import contactlist.integration.IntegrationTestUtils;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyOrNullString;

/**
 * Created by sangeet on 4/8/2017.
 */
@SqlGroup({ @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
    "classpath:sql/create-schema.sql", "classpath:sql/create-user.sql",
    "classpath:sql/create-contactlist.sql" }),
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {
        "classpath:sql/drop-schema.sql" }) }) public class GetContactListById
    extends IntegrationTestConfigurer {
  private final static String CONTACTLIST_SPECIFIC_RESOURCE_ENDPOINT = "/contactlist/{id}";

  @Test() public void test_get_on_contactlist_with_id_1_that_exists() throws Exception {
    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        pathParam("id", 1).
        when().
        get(IntegrationTestUtils.getEndPoint(CONTACTLIST_SPECIFIC_RESOURCE_ENDPOINT)).
        then().
        statusCode(HttpStatus.OK.value()).
        contentType(JSON).body("name", equalTo("Friends")).
        body("id", equalTo(1));
  }

  @Test() public void test_get_on_contactlist_with_id_5_that_does_not_exist() throws Exception {
    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        pathParam("id", 5).
        when().
        get(IntegrationTestUtils.getEndPoint(CONTACTLIST_SPECIFIC_RESOURCE_ENDPOINT)).
        then().
        statusCode(HttpStatus.NOT_FOUND.value()).
        contentType(isEmptyOrNullString()).
        body(isEmptyOrNullString());
  }

}
