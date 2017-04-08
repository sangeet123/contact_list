package contactlist.integration.contactlist.endpoints.post;

import contactlist.integration.IntegrationTestConfigurer;
import contactlist.integration.IntegrationTestUtils;
import io.restassured.response.Response;
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
        "classpath:sql/drop-schema.sql" }) }) public class PostOnContactList extends
    IntegrationTestConfigurer {

  private final static String CONTACTLIST_ENTRY_ENDPOINT = "/contactlist";
  private final static String CONTACTLIST_SPECIFIC_RESOURCE_ENDPOINT = "/contactlist/{id}";

  @Test()
  public void test_create_contactlist_with_name_tobecreated() throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("name", "tobecreated");

    final Response response = given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        contentType("application/json").
        body(map).
        when().
        post(IntegrationTestUtils.getEndPoint(CONTACTLIST_ENTRY_ENDPOINT));

    response.
        then().
        statusCode(HttpStatus.CREATED.value()).
        contentType(isEmptyOrNullString()).
        body(isEmptyOrNullString());

    final String location = response.getHeader("location");
    final String id = location.substring(location.lastIndexOf("/") + 1);

    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        pathParam("id", id).
        when().
        get(IntegrationTestUtils.getEndPoint(CONTACTLIST_SPECIFIC_RESOURCE_ENDPOINT)).
        then().
        statusCode(HttpStatus.OK.value()).
        contentType(JSON).
        body("name", equalTo("tobecreated")).
        body("id", equalTo(Integer.parseInt(id)));
  }

  @Test()
  public void test_contact_list_with_empty_contactlist_name() throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("name", "");

    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        contentType("application/json").
        body(map).
        when().
        post(IntegrationTestUtils.getEndPoint(CONTACTLIST_ENTRY_ENDPOINT)).
        then().
        contentType(JSON).
        statusCode(HttpStatus.BAD_REQUEST.value()).
        body("context",equalTo("contactlist")).
        body("fieldErrors.field",contains("name")).
        body("fieldErrors.message",contains("Value is required."));
  }

  @Test()
  public void test_contact_list_with_no_contactlist_name() throws Exception {
    Map<String, String> map = new HashMap<>();
    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        contentType("application/json").
        body(map).
        when().
        post(IntegrationTestUtils.getEndPoint(CONTACTLIST_ENTRY_ENDPOINT)).
        then().
        contentType(JSON).
        statusCode(HttpStatus.BAD_REQUEST.value()).
        body("context",equalTo("contactlist")).
        body("fieldErrors.field",contains("name")).
        body("fieldErrors.message",contains("Value is required."));
  }


  @Test()
  public void test_contact_list_with_name_Friends_that_exist() throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("name", "Friends");
    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        contentType("application/json").
        body(map).
        when().
        post(IntegrationTestUtils.getEndPoint(CONTACTLIST_ENTRY_ENDPOINT)).
        then().
        contentType(JSON).
        statusCode(HttpStatus.CONFLICT.value()).
        body("context",equalTo("contactlist")).
        body("fieldErrors.field",contains("name")).
        body("fieldErrors.message",contains("ContactList with name already exists."));
  }
}
