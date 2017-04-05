package contactlist.integration.get;

import com.fasterxml.jackson.core.type.TypeReference;
import contactlist.integration.IntegrationTestUtils;
import contactlist.model.response.ContactlistResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by sangeet on 4/4/2017.
 */
public class TestEndPointsForContactList {
  public void testGetContactList() throws Exception {

    final ResponseEntity<String> responseEntity = IntegrationTestUtils.doGet("/contactlist");
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    List<ContactlistResponse> ctlist = IntegrationTestUtils
        .readEntity(responseEntity, new TypeReference<List<ContactlistResponse>>() {
        });
    assertTrue(ctlist.size() == 3);
    assertEquals(ctlist.get(0).getName(), "Friends");
    assertEquals(ctlist.get(1).getName(), "Family");
    assertEquals(ctlist.get(2).getName(), "Others");

    assertEquals(ctlist.get(0).getId(), Long.valueOf(1));
    assertEquals(ctlist.get(1).getId(), Long.valueOf(2));
    assertEquals(ctlist.get(2).getId(), Long.valueOf(3));
  }

  public void testGetContactListPaginationWithSorting() throws Exception {
    final ResponseEntity<String> responseEntity = IntegrationTestUtils
        .doGet("contactlist?page=0&size=2&sort=name,asc");
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    List<ContactlistResponse> ctlist = IntegrationTestUtils
        .readEntity(responseEntity, new TypeReference<List<ContactlistResponse>>() {
        });
    assertTrue(ctlist.size() == 2);
    assertEquals(ctlist.get(0).getName(), "Family");
    assertEquals(ctlist.get(1).getName(), "Friends");

    assertEquals(ctlist.get(0).getId(), Long.valueOf(2));
    assertEquals(ctlist.get(1).getId(), Long.valueOf(1));
  }

  public void testGetById() throws Exception {
    final ResponseEntity<String> responseEntity = IntegrationTestUtils.doGet("/contactlist/1");
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    final ContactlistResponse response = IntegrationTestUtils
        .readEntity(responseEntity, new TypeReference<ContactlistResponse>() {
        });
    assertEquals(response.getName(), "Friends");
    assertEquals(response.getId(), Long.valueOf(1));
  }

  public void testGetNotFoundById() throws Exception {
    final ResponseEntity<String> responseEntity = IntegrationTestUtils.doGet("/contactlist/5");
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertFalse(responseEntity.hasBody());
  }

  public void testSuccessFullDeleteById() throws Exception{
    ResponseEntity<String> responseEntity = IntegrationTestUtils.doDelete("/contactlist/2");
    assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
    assertFalse(responseEntity.hasBody());

    //Again deleting same resources should result in 404
    responseEntity = IntegrationTestUtils.doDelete("/contactlist/2");
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertFalse(responseEntity.hasBody());
  }

  public void testDeleteByIdNotFound() throws Exception{
    ResponseEntity<String> responseEntity = IntegrationTestUtils.doDelete("/contactlist/2");
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertFalse(responseEntity.hasBody());
  }

}
