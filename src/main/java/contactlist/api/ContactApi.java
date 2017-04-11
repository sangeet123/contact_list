package contactlist.api;

import contactlist.model.request.ContactRequest;
import contactlist.model.response.ContactResponse;
import contactlist.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
      final HttpServletRequest request) {
    return contactService.findByContactListIdAndContactId(contactlistid, contactId);
  }

  @RequestMapping(method = RequestMethod.GET) public @ResponseBody() List<ContactResponse> get(
      @PathVariable Long contactlistid, final HttpServletRequest request, final Pageable pageable) {
    return contactService.get(contactlistid, pageable);
  }

  @RequestMapping(method = RequestMethod.POST) public @ResponseBody() void create(
      @RequestBody() @Valid() final ContactRequest contactListRequest,
      @PathVariable Long contactlistid, final HttpServletRequest httpServletRequest,
      final HttpServletResponse httpServletResponse) {
    final ContactResponse response = contactService.create(contactlistid, contactListRequest);
    httpServletResponse.setHeader("location", "/contact/" + contactlistid + "/" + response.getId());
    httpServletResponse.setStatus(HttpStatus.CREATED.value());
  }

  @RequestMapping(value = "/{contactId}", method = RequestMethod.PUT) public @ResponseBody() void update(
      @RequestBody() @Valid() final ContactRequest contactListRequest,
      @PathVariable Long contactlistid, @PathVariable Long contactId,
      final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
    final ContactResponse response = contactService
        .update(contactlistid, contactId, contactListRequest);
    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
  }

  @RequestMapping(value = "/{contactId}", method = RequestMethod.DELETE) public @ResponseBody() void delete(
      @PathVariable Long contactlistid, @PathVariable Long contactId, final HttpServletRequest request,
      final HttpServletResponse httpServletResponse) {
    contactService.delete(contactlistid, contactId);
    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
  }

}
