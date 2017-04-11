package contactlist.api;

import contactlist.model.request.ContactRequest;
import contactlist.model.response.ContactResponse;
import contactlist.model.transaction.Transaction;
import contactlist.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by sangeet on 4/8/2017.
 */
@Secured({ "ROLE_APP_ADMIN",
    "ROLE_APP_USER" }) @RestController() @RequestMapping("/contact/{contactlistid}") public class ContactApi {

  @Autowired() private ContactService contactService;

  @RequestMapping(value = "/{contactId}", method = RequestMethod.GET) public @ResponseBody() ContactResponse get(
      @PathVariable Long contactlistid, @PathVariable Long contactId,
      final Transaction transaction) {
    final ContactResponse contactResponse = contactService
        .findByContactListIdAndContactId(contactlistid, contactId);
    transaction.setReturnStatus(HttpServletResponse.SC_OK);
    return contactResponse;
  }

  @RequestMapping(method = RequestMethod.GET) public @ResponseBody() List<ContactResponse> get(
      @PathVariable Long contactlistid, final Pageable pageable, final Transaction transaction) {
    List<ContactResponse> contactResponses = contactService.get(contactlistid, pageable);
    transaction.setReturnStatus(HttpServletResponse.SC_OK);
    return contactResponses;
  }

  @RequestMapping(method = RequestMethod.POST) public @ResponseBody() void create(
      @RequestBody() @Valid() final ContactRequest contactListRequest,
      @PathVariable Long contactlistid, final Transaction transaction) {
    final ContactResponse response = contactService.create(contactlistid, contactListRequest);
    transaction.setLocationHeader("/contact/" + contactlistid + "/" + response.getId());
    transaction.setReturnStatus(HttpServletResponse.SC_CREATED);
  }

  @RequestMapping(value = "/{contactId}", method = RequestMethod.PUT) public @ResponseBody() void update(
      @RequestBody() @Valid() final ContactRequest contactListRequest,
      @PathVariable Long contactlistid, @PathVariable Long contactId,
      final Transaction transaction) {
    final ContactResponse response = contactService
        .update(contactlistid, contactId, contactListRequest);
    transaction.setReturnStatus(HttpServletResponse.SC_NO_CONTENT);
  }

  @RequestMapping(value = "/{contactId}", method = RequestMethod.DELETE) public @ResponseBody() void delete(
      @PathVariable Long contactlistid, @PathVariable Long contactId,
      final Transaction transaction) {
    contactService.delete(contactlistid, contactId);
    transaction.setReturnStatus(HttpServletResponse.SC_NO_CONTENT);
  }

}
