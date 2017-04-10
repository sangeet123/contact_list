package contactlist.integration.contactlist.endpoints.contact.get;

import contactlist.integration.IntegrationTestConfigurer;
import contactlist.integration.IntegrationTestUtils;
import contactlist.model.response.ContactResponse;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonPathConfig;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.io.File;
import java.net.URI;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertEquals;

/**
 * Created by sangeet on 4/8/2017.
 */
@SqlGroup({ @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
    "classpath:sql/create-schema.sql", "classpath:sql/create-user.sql",
    "classpath:sql/create-contactlist.sql", "classpath:sql/create-contacts.sql" }),
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {
        "classpath:sql/drop-schema.sql" }) }) public class GetContacts
    extends IntegrationTestConfigurer {
  private final static String CONTACT_ENTRY_ENDPOINT = "/contact/{contactlistid}";

  @Test() public void test_get_all_contacts_for_contactlist_with_id_1() throws Exception {
    final URI uri = getClass().getClassLoader().getResource("response/get-contacts.json").toURI();
    final JsonPath jsonPath = new JsonPath(new File(uri)).
        using(new JsonPathConfig("ISO-8859-1"));
    final List<ContactResponse> expected = jsonPath.get();
    final List<ContactResponse> received = given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        pathParam("contactlistid", 1).
        when().
        get(IntegrationTestUtils.getEndPoint(CONTACT_ENTRY_ENDPOINT)).
        then().
        statusCode(HttpStatus.OK.value()).
        contentType(JSON).extract().body().as(List.class);

    assertEquals(expected, received);
  }

  @Test() public void test_get_all_contacts_for_contactlist_with_id_5_which_does_not_exist()
      throws Exception {
    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        pathParam("contactlistid", 5).
        when().
        get(IntegrationTestUtils.getEndPoint(CONTACT_ENTRY_ENDPOINT)).
        then().
        statusCode(HttpStatus.NOT_FOUND.value()).
        contentType(isEmptyOrNullString()).body(isEmptyOrNullString());
  }

  @Test() public void test_get_all_contactlists_on_page_0_with_size_() throws Exception {
    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        pathParam("contactlistid", 1).
        queryParam("page", 1).
        queryParam("size", 5).
        queryParam("sort", "email,asc").
        when().
        get(IntegrationTestUtils.getEndPoint(CONTACT_ENTRY_ENDPOINT)).
        then().
        statusCode(HttpStatus.OK.value()).body("size()", equalTo(5)).
        body("email", contains("abc3_last@gmail.com", "abc4_last@gmail.com", "abc5_last@gmail.com",
            "abc6_last@gmail.com", "abc7_last@gmail.com"));
  }
}
