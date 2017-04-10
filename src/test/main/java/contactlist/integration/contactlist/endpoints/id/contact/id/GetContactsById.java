package contactlist.integration.contactlist.endpoints.id.contact.id;

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
 * Created by sangeet on 4/9/2017.
 */
@SqlGroup({ @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
    "classpath:sql/create-schema.sql", "classpath:sql/create-user.sql",
    "classpath:sql/create-contactlist.sql", "classpath:sql/create-contacts.sql" }),
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {
        "classpath:sql/drop-schema.sql" }) }) public class GetContactsById
    extends IntegrationTestConfigurer {
  private final static String CONTACT_SPECIFIC_RESOURCE_ENDPOINT = "/contact/{contactlistid}/{id}";

  @Test() public void test_get_on_contactlist_with_id_1_that_exists() throws Exception {
    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        pathParam("contactlistid", 1).
        pathParam("id", 1).
        when().
        get(IntegrationTestUtils.getEndPoint(CONTACT_SPECIFIC_RESOURCE_ENDPOINT)).
        then().
        statusCode(HttpStatus.OK.value()).
        contentType(JSON).body("id", equalTo(1)).
        body("contactListId", equalTo(1)).
        body("firstName", equalTo("abc1")).
        body("lastName", equalTo("last")).
        body("phoneNumber", equalTo("(245)-312-2221")).
        body("email", equalTo("abc1_last@gmail.com"));
  }

  @Test() public void test_get_on_contactlist_with_id_5_and_contact_id_15_for_another_user_that_exist()
      throws Exception {
    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        pathParam("contactlistid", 5).
        pathParam("id", 15).
        when().
        get(IntegrationTestUtils.getEndPoint(CONTACT_SPECIFIC_RESOURCE_ENDPOINT)).
        then().
        statusCode(HttpStatus.NOT_FOUND.value()).
        contentType(isEmptyOrNullString()).
        body(isEmptyOrNullString());
  }

  @Test() public void test_get_on_contactlist_with_id_1_exists_and_contact_with_id_13_that_does_not_exist()
      throws Exception {
    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        pathParam("contactlistid", 1).
        pathParam("id", 13).
        when().
        get(IntegrationTestUtils.getEndPoint(CONTACT_SPECIFIC_RESOURCE_ENDPOINT)).
        then().
        statusCode(HttpStatus.NOT_FOUND.value()).
        contentType(isEmptyOrNullString()).
        body(isEmptyOrNullString());
  }

}
