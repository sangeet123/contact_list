package contactlist.integration.contactlist.endpoints.id.contact.id.put;

import contactlist.integration.IntegrationTestConfigurer;
import contactlist.integration.IntegrationTestUtils;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.isEmptyOrNullString;

/**
 * Created by sangeet on 4/10/2017.
 */
@SqlGroup({ @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
    "classpath:sql/create-schema.sql", "classpath:sql/create-user.sql",
    "classpath:sql/create-contactlist.sql", "classpath:sql/create-contacts.sql" }),
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {
        "classpath:sql/drop-schema.sql" }) }) public class PutOnContact
    extends IntegrationTestConfigurer {
  private final static String CONTACT_SPECIFIC_RESOURCE_ENDPOINT = "/contact/{contactlistid}/{id}";

  @Test() public void test_put_a_valid_contact() throws Exception {
    final Map<String, String> map = new HashMap<>();
    map.put("firstName", "Ramesh");
    map.put("lastName", "Awasti");
    map.put("email", "Ramesh_Awasti@gmail.com");
    map.put("phoneNumber", "2563487777");

    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        pathParam("contactlistid", 1).
        pathParam("id", 2).
        contentType("application/json").
        body(map).
        when().
        put(IntegrationTestUtils.getEndPoint(CONTACT_SPECIFIC_RESOURCE_ENDPOINT)).
        then().
        statusCode(HttpStatus.NO_CONTENT.value()).
        contentType(isEmptyOrNullString()).
        body(isEmptyOrNullString());

    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        pathParam("contactlistid", 1).
        pathParam("id", 2).
        when().
        get(IntegrationTestUtils.getEndPoint(CONTACT_SPECIFIC_RESOURCE_ENDPOINT)).
        then().
        statusCode(HttpStatus.OK.value()).
        contentType(JSON).
        body("contactListId", Matchers.equalTo(1)).
        body("firstName", equalTo("Ramesh")).
        body("lastName", equalTo("Awasti")).
        body("email", equalTo("Ramesh_Awasti@gmail.com")).
        body("phoneNumber", equalTo("2563487777"));
  }

  @Test() public void test_put_empty_contact_email() throws Exception {
    final Map<String, String> map = new HashMap<>();
    map.put("firstName", "Ramesh");
    map.put("lastName", "Awasti");
    map.put("email", "");
    map.put("phoneNumber", "2563487777");

    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        pathParam("contactlistid", 1).
        pathParam("id", 2).
        contentType("application/json").
        body(map).
        when().
        put(IntegrationTestUtils.getEndPoint(CONTACT_SPECIFIC_RESOURCE_ENDPOINT)).
        then().
        contentType(JSON).
        statusCode(HttpStatus.BAD_REQUEST.value()).
        body("context", Matchers.equalTo("contactlist")).
        body("fieldErrors.field", contains("email")).
        body("fieldErrors.message", contains("Value is required."));
  }

  @Test() public void test_put_invalid_contact_email() throws Exception {
    final Map<String, String> map = new HashMap<>();
    map.put("firstName", "Ramesh");
    map.put("lastName", "Awasti");
    map.put("email", "fkdjkl");
    map.put("phoneNumber", "2563487777");

    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        pathParam("contactlistid", 1).
        pathParam("id", 2).
        contentType("application/json").
        body(map).
        when().
        put(IntegrationTestUtils.getEndPoint(CONTACT_SPECIFIC_RESOURCE_ENDPOINT)).
        then().
        contentType(JSON).
        statusCode(HttpStatus.BAD_REQUEST.value()).
        body("context", Matchers.equalTo("contactlist")).
        body("fieldErrors.field", contains("email")).
        body("fieldErrors.message", contains("Invalid email."));
  }

  @Test() public void test_put_invalid_phone_number() throws Exception {
    final Map<String, String> map = new HashMap<>();
    map.put("firstName", "Ramesh");
    map.put("lastName", "Awasti");
    map.put("email", "Ramesh_Awasti@gmail.com");
    map.put("phoneNumber", "abc");

    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        pathParam("contactlistid", 1).
        pathParam("id", 2).
        contentType("application/json").
        body(map).
        when().
        put(IntegrationTestUtils.getEndPoint(CONTACT_SPECIFIC_RESOURCE_ENDPOINT)).
        then().
        contentType(JSON).
        statusCode(HttpStatus.BAD_REQUEST.value()).
        body("context", Matchers.equalTo("contactlist")).
        body("fieldErrors.field", contains("phoneNumber")).
        body("fieldErrors.message", contains("String must follow the specified pattern."));
  }

  @Test() public void test_put_invalid_first_name_size_larger_than_30() throws Exception {
    final Map<String, String> map = new HashMap<>();
    map.put("firstName",
        "This is a very large first name and it should not be allowed to be entered into db.");
    map.put("lastName", "Awasti");
    map.put("email", "Ramesh_Awasti@gmail.com");
    map.put("phoneNumber", "2563487777");

    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        pathParam("contactlistid", 1).
        pathParam("id", 2).
        contentType("application/json").
        body(map).
        when().
        put(IntegrationTestUtils.getEndPoint(CONTACT_SPECIFIC_RESOURCE_ENDPOINT)).
        then().
        contentType(JSON).
        statusCode(HttpStatus.BAD_REQUEST.value()).
        body("context", Matchers.equalTo("contactlist")).
        body("fieldErrors.field", contains("firstName")).
        body("fieldErrors.message", contains("String length must be within boundry."));
  }

  @Test() public void test_put_invalid_last_name_size_larger_than_30() throws Exception {
    final Map<String, String> map = new HashMap<>();
    map.put("firstName", "Ramesh");
    map.put("lastName",
        "This is a very large last name and it should not be allowed to be entered into db.");
    map.put("email", "Ramesh_Awasti@gmail.com");
    map.put("phoneNumber", "2563487777");

    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        pathParam("contactlistid", 1).
        pathParam("id", 2).
        contentType("application/json").
        body(map).
        when().
        put(IntegrationTestUtils.getEndPoint(CONTACT_SPECIFIC_RESOURCE_ENDPOINT)).
        then().
        contentType(JSON).
        statusCode(HttpStatus.BAD_REQUEST.value()).
        body("context", Matchers.equalTo("contactlist")).
        body("fieldErrors.field", contains("lastName")).
        body("fieldErrors.message", contains("String length must be within boundry."));
  }

  @Test() public void test_put_email_that_already_exists() throws Exception {
    final Map<String, String> map = new HashMap<>();
    map.put("firstName", "Ramesh");
    map.put("lastName", "Awasti");
    map.put("email", "abc4_last@gmail.com");
    map.put("phoneNumber", "2563487777");

    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        pathParam("contactlistid", 1).
        pathParam("id", 2).
        contentType("application/json").
        body(map).
        when().
        put(IntegrationTestUtils.getEndPoint(CONTACT_SPECIFIC_RESOURCE_ENDPOINT)).
        then().
        contentType(JSON).
        statusCode(HttpStatus.CONFLICT.value()).
        body("context", Matchers.equalTo("contactlist")).
        body("fieldErrors.field", contains("email")).
        body("fieldErrors.message", contains("Contacts with email exists."));
  }
}
