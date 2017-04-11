package contactlist.integration.contactlist.endpoints.id.contact.id.delete;

import contactlist.integration.IntegrationTestConfigurer;
import contactlist.integration.IntegrationTestUtils;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.isEmptyOrNullString;

/**
 * Created by sangeet on 4/10/2017.
 */
@SqlGroup({ @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
    "classpath:sql/create-schema.sql", "classpath:sql/create-user.sql",
    "classpath:sql/create-contactlist.sql", "classpath:sql/create-contacts.sql" }),
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {
        "classpath:sql/drop-schema.sql" }) })
public class DeleteContactById extends IntegrationTestConfigurer {
  private final static String CONTACT_SPECIFIC_RESOURCE_ENDPOINT = "/contact/{contactlistid}/{id}";

  @Test()
  public void test_delete_by_id_12_that_does_not_exist() throws Exception {
    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        pathParam("contactlistid",1).
        pathParam("id", 14).
        when().
        delete(IntegrationTestUtils.getEndPoint(CONTACT_SPECIFIC_RESOURCE_ENDPOINT)).
        then().
        statusCode(HttpStatus.NOT_FOUND.value()).
        contentType(isEmptyOrNullString()).
        body(isEmptyOrNullString());
  }

  @Test()
  public void test_delete_by_id_2_that_exists() throws Exception {
    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        pathParam("contactlistid",1).
        pathParam("id", 2).
        when().
        delete(IntegrationTestUtils.getEndPoint(CONTACT_SPECIFIC_RESOURCE_ENDPOINT)).
        then().
        statusCode(HttpStatus.NO_CONTENT.value()).
        contentType(isEmptyOrNullString()).
        body(isEmptyOrNullString());

    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        pathParam("contactlistid",1).
        pathParam("id", 2).
        when().
        delete(IntegrationTestUtils.getEndPoint(CONTACT_SPECIFIC_RESOURCE_ENDPOINT)).
        then().
        statusCode(HttpStatus.NOT_FOUND.value()).
        contentType(isEmptyOrNullString()).
        body(isEmptyOrNullString());
  }
}
