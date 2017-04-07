package contactlist.integration.contactlist.endpoint;

import com.fasterxml.jackson.core.type.TypeReference;
import contactlist.integration.IntegrationTestUtils;
import contactlist.model.request.ContactlistRequest;
import contactlist.model.response.ContactlistResponse;
import error.ValidationErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Created by sangeet on 4/4/2017.
 */
public class TestEndPointsForContactList {
  public void testGetContactList() throws Exception {

    final ResponseEntity<String> responseEntity = IntegrationTestUtils.doGet("/contactlist");
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    List<ContactlistResponse> ctlist = IntegrationTestUtils
        .readEntity(responseEntity.getBody(), new TypeReference<List<ContactlistResponse>>() {
        });
    assertTrue(ctlist.size() == 3);
    Map<Long, String> ctmap = ctlist.stream().collect(Collectors.toMap(ContactlistResponse::getId, ContactlistResponse::getName));
    assertEquals(ctmap.get(1l), "Friends");
    assertEquals(ctmap.get(2l), "Family");
    assertEquals(ctmap.get(3l), "Others");
  }

  public void testGetContactListPaginationWithSorting() throws Exception {
    final ResponseEntity<String> responseEntity = IntegrationTestUtils
        .doGet("contactlist?page=0&size=2&sort=name,asc");
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    List<ContactlistResponse> ctlist = IntegrationTestUtils
        .readEntity(responseEntity.getBody(), new TypeReference<List<ContactlistResponse>>() {
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
        .readEntity(responseEntity.getBody(), new TypeReference<ContactlistResponse>() {
        });
    assertEquals(response.getName(), "Friends");
    assertEquals(response.getId(), Long.valueOf(1));
  }

  public void testGetNotFoundById() throws Exception {
    final ResponseEntity<String> responseEntity = IntegrationTestUtils.doGet("/contactlist/5");
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertFalse(responseEntity.hasBody());
  }

  ///Danger do not user contact list with id 2 for any purpose
  public void testSuccessFullDeleteById() throws Exception{
    ResponseEntity<String> responseEntity = IntegrationTestUtils.doDelete("/contactlist/2");
    assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    assertFalse(responseEntity.hasBody());

    //Again deleting same resources should result in 404
    responseEntity = IntegrationTestUtils.doDelete("/contactlist/2");
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertFalse(responseEntity.hasBody());
  }

  public void testDeleteByIdNotFound() throws Exception{
    ResponseEntity<String> responseEntity = IntegrationTestUtils.doDelete("/contactlist/12");
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertFalse(responseEntity.hasBody());
  }

  public void testSuccessFullCreate() throws Exception{
    final ContactlistRequest request = new ContactlistRequest();
    request.setName("tobecreated");
    ResponseEntity<String> responseEntity = IntegrationTestUtils.doPost("/contactlist", request);
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertFalse(responseEntity.hasBody());

    responseEntity = IntegrationTestUtils.doGet(responseEntity.getHeaders().getFirst("location"));
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    final ContactlistResponse response = IntegrationTestUtils
        .readEntity(responseEntity.getBody(), new TypeReference<ContactlistResponse>() {
        });
    assertEquals(response.getName(), "tobecreated");
  }

  public void testBadContactlistPost() throws Exception{
    final ContactlistRequest request = new ContactlistRequest();
    request.setName("");
    ResponseEntity<String> responseEntity = IntegrationTestUtils.doPost("/contactlist", request);
    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    final ValidationErrorInfo errorInfo = IntegrationTestUtils.readEntity(responseEntity.getBody(), new TypeReference<ValidationErrorInfo>() {
    });
    assertTrue(errorInfo.getFieldErrors().size()==1);
    assertEquals("name", errorInfo.getFieldErrors().get(0).getField());
    assertEquals("Value is required.",errorInfo.getFieldErrors().get(0).getMessage());
  }

  public void testContactlistPostContactlistAlreadyExists() throws Exception{

    final ContactlistRequest request = new ContactlistRequest();
    request.setName("conflict");
    ResponseEntity<String> responseEntity = IntegrationTestUtils.doPost("/contactlist", request);
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertFalse(responseEntity.hasBody());

    request.setName("conflict");
    responseEntity = IntegrationTestUtils.doPost("/contactlist", request);
    assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    final ValidationErrorInfo errorInfo = IntegrationTestUtils.readEntity(responseEntity.getBody(), new TypeReference<ValidationErrorInfo>() {
    });
    assertTrue(errorInfo.getFieldErrors().size()==1);
    assertEquals("name", errorInfo.getFieldErrors().get(0).getField());
    assertEquals("Contactlist with name already exists.",errorInfo.getFieldErrors().get(0).getMessage());
  }

  public void testSuccessContactlistPutContactlistExists() throws Exception{

    //First create
    final ContactlistRequest request = new ContactlistRequest();
    request.setName("testPut");
    ResponseEntity<String> responseEntity = IntegrationTestUtils.doPost("/contactlist", request);
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertFalse(responseEntity.hasBody());

    //Then do get
    final String location = responseEntity.getHeaders().getFirst("location");
    responseEntity = IntegrationTestUtils.doGet(location);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    ContactlistResponse response = IntegrationTestUtils
        .readEntity(responseEntity.getBody(), new TypeReference<ContactlistResponse>() {
        });
    assertEquals(response.getName(), "testPut");

    //Then update
    request.setName("testPutUpdate");
    ResponseEntity<String> responseEntityForPut = IntegrationTestUtils.doPut(location, request);
    assertEquals(HttpStatus.NO_CONTENT, responseEntityForPut.getStatusCode());

    //Then do get
    responseEntity = IntegrationTestUtils.doGet(location);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    response = IntegrationTestUtils
        .readEntity(responseEntity.getBody(), new TypeReference<ContactlistResponse>() {
        });
    assertEquals(response.getName(), "testPutUpdate");
  }

  public void testBadContactlistPut() throws Exception{
    final ContactlistRequest request = new ContactlistRequest();
    request.setName("");
    ResponseEntity<String> responseEntity = IntegrationTestUtils.doPut("/contactlist/1", request);
    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    final ValidationErrorInfo errorInfo = IntegrationTestUtils.readEntity(responseEntity.getBody(), new TypeReference<ValidationErrorInfo>() {
    });
    assertTrue(errorInfo.getFieldErrors().size()==1);
    assertEquals("name", errorInfo.getFieldErrors().get(0).getField());
    assertEquals("Value is required.",errorInfo.getFieldErrors().get(0).getMessage());
  }

  public void testConflictContactlistPut() throws Exception{
    final ContactlistRequest request = new ContactlistRequest();
    request.setName("Others");
    ResponseEntity<String> responseEntity = IntegrationTestUtils.doPut("/contactlist/1", request);
    assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    final ValidationErrorInfo errorInfo = IntegrationTestUtils.readEntity(responseEntity.getBody(), new TypeReference<ValidationErrorInfo>() {
    });
    assertTrue(errorInfo.getFieldErrors().size()==1);
    assertEquals("name", errorInfo.getFieldErrors().get(0).getField());
    assertEquals("Contactlist with name already exists.",errorInfo.getFieldErrors().get(0).getMessage());
  }
}
