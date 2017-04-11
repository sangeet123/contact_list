package contactlist.api;

import contactlist.model.request.ContactListRequest;
import contactlist.model.response.ContactListResponse;
import contactlist.model.transaction.Transaction;
import contactlist.service.ContactListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by sangeet on 3/6/2017.
 */
@Secured({ "ROLE_APP_ADMIN",
    "ROLE_APP_USER" }) @RestController() @RequestMapping("/contactlist") public class ContactListApi {

  @Autowired() private ContactListService contactListService;

  @RequestMapping(value = "/{id}", method = RequestMethod.GET) public @ResponseBody() ContactListResponse get(
      @PathVariable Long id, final Transaction transaction) {
    final ContactListResponse contactListResponse = contactListService
        .findByIdAndUserId(id, transaction.getUserIdFromHeader());
    transaction.setReturnStatus(HttpServletResponse.SC_OK);
    return contactListResponse;
  }

  @RequestMapping(method = RequestMethod.GET) public @ResponseBody() List<ContactListResponse> get(
      final Pageable pageable, final Transaction transaction) {
    final List<ContactListResponse> contactListResponses = contactListService
        .get(transaction.getUserIdFromHeader(), pageable);
    transaction.setReturnStatus(HttpServletResponse.SC_OK);
    return contactListResponses;
  }

  @RequestMapping(method = RequestMethod.POST) public @ResponseBody() void create(
      @RequestBody() @Valid() final ContactListRequest contactListRequest,
      final Transaction transaction) {
    final ContactListResponse response = contactListService
        .create(transaction.getUserIdFromHeader(), contactListRequest);
    transaction.setLocationHeader("/contactlist/" + response.getId());
    transaction.setReturnStatus(HttpServletResponse.SC_CREATED);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT) public void update(
      @PathVariable Long id, @RequestBody() @Valid() final ContactListRequest contactListRequest,
      final Transaction transaction) {
    contactListService.update(id, transaction.getUserIdFromHeader(), contactListRequest);
    transaction.setReturnStatus(HttpServletResponse.SC_NO_CONTENT);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE) public @ResponseBody() void delete(
      @PathVariable Long id, final Transaction transaction) {
    contactListService.delete(id, transaction.getUserIdFromHeader());
    transaction.setReturnStatus(HttpServletResponse.SC_NO_CONTENT);
  }

}
