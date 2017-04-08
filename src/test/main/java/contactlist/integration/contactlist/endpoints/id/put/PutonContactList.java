package contactlist.integration.contactlist.endpoints.id.put;

import contactlist.integration.IntegrationTestConfigurer;
import contactlist.integration.IntegrationTestUtils;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

/**
 * Created by sangeet on 4/8/2017.
 */
@SqlGroup({ @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
    "classpath:sql/create-schema.sql", "classpath:sql/create-user.sql",
    "classpath:sql/create-contactlist.sql" }),
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {
        "classpath:sql/drop-schema.sql" }) })
public class PutOnContactList extends IntegrationTestConfigurer {
  private final static String CONTACTLIST_SPECIFIC_RESOURCE_ENDPOINT = "/contactlist/{id}";

  @Test()
  public void test_put_on_contactlist_with_id_1_that_exists() throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("name", "Collegues");

    given().
        pathParam("id", 1).
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        contentType("application/json").
        body(map).
        when().
        put(IntegrationTestUtils.getEndPoint(CONTACTLIST_SPECIFIC_RESOURCE_ENDPOINT)).
        then().
        statusCode(HttpStatus.NO_CONTENT.value()).
        contentType(isEmptyOrNullString()).
        body(isEmptyOrNullString());

    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        pathParam("id", 1).
        when().
        get(IntegrationTestUtils.getEndPoint(CONTACTLIST_SPECIFIC_RESOURCE_ENDPOINT)).
        then().
        statusCode(HttpStatus.OK.value()).
        contentType(JSON).body("name", equalTo("Collegues")).
        body("id", equalTo(1));

  }


  @Test()
  public void test_contact_list_with_empty_contactlist_name() throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("name", "");

    given().
        pathParam("id", 2).
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        contentType("application/json").
        body(map).
        when().
        put(IntegrationTestUtils.getEndPoint(CONTACTLIST_SPECIFIC_RESOURCE_ENDPOINT)).
        then().
        statusCode(HttpStatus.BAD_REQUEST.value()).
        contentType(JSON).
        body("context",equalTo("contactlist")).
        body("fieldErrors.field",contains("name")).
        body("fieldErrors.message",contains("Value is required."));
  }

  @Test()
  public void test_contact_list_with_no_contactlist_name() throws Exception {
    Map<String, String> map = new HashMap<>();
    given().
        pathParam("id", 2).
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        contentType("application/json").
        body(map).
        when().
        put(IntegrationTestUtils.getEndPoint(CONTACTLIST_SPECIFIC_RESOURCE_ENDPOINT)).
        then().
        statusCode(HttpStatus.BAD_REQUEST.value()).
        contentType(JSON).
        body("context",equalTo("contactlist")).
        body("fieldErrors.field",contains("name")).
        body("fieldErrors.message",contains("Value is required."));
  }


  @Test()
  public void test_contact_list_with_name_Others_to_be_changed_to_name_Family_that_exists() throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("name", "Family");
    given().
        pathParam("id", 3).
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        contentType("application/json").
        body(map).
        when().
        put(IntegrationTestUtils.getEndPoint(CONTACTLIST_SPECIFIC_RESOURCE_ENDPOINT)).
        then().
        contentType(JSON).
        statusCode(HttpStatus.CONFLICT.value()).
        body("context",equalTo("contactlist")).
        body("fieldErrors.field",contains("name")).
        body("fieldErrors.message",contains("ContactList with name already exists."));
  }
}
