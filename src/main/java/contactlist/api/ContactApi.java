package contactlist.api;

import contactlist.model.response.ContactResponse;
import contactlist.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by sangeet on 4/8/2017.
 */
@Secured({ "ROLE_APP_ADMIN",
    "ROLE_APP_USER" }) @RestController() @RequestMapping("/contact/{contactlistid}") public class ContactApi {

  @Autowired() private ContactService contactService;

  @RequestMapping(value = "/{id}", method = RequestMethod.GET) public @ResponseBody() ContactResponse get(
      @PathVariable Long contactlistid, @PathVariable Long id, final HttpServletRequest request) {
    return contactService.findByContactListIdAndContactId(id, contactlistid);
  }

  @RequestMapping(method = RequestMethod.GET) public @ResponseBody() List<ContactResponse> get(
      @PathVariable Long contactlistid, final HttpServletRequest request, final Pageable pageable) {
    return contactService.get(contactlistid, pageable);
  }

  //  @RequestMapping(method = RequestMethod.POST) public @ResponseBody() void create(
  //      @RequestBody() @Valid() final ContactListRequest contactListRequest,
  //      final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
  //    final Long userId = (Long) httpServletRequest.getAttribute("userId");
  //    final ContactListResponse response = contactListService.create(userId, contactListRequest);
  //    httpServletResponse.setHeader("location", "/contactlist/" + response.getId());
  //    httpServletResponse.setStatus(HttpStatus.CREATED.value());
  //  }
  //
  //  @RequestMapping(value = "/{id}", method = RequestMethod.PUT) public void update(
  //      @PathVariable Long id, @RequestBody() @Valid() final ContactListRequest contactListRequest,
  //      final HttpServletRequest request, final HttpServletResponse httpServletResponse) {
  //    final Long userId = (Long) request.getAttribute("userId");
  //    contactListService.update(id, userId, contactListRequest);
  //    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
  //  }
  //
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE) public @ResponseBody() void delete(
      @PathVariable Long contactlistid, @PathVariable Long id, final HttpServletRequest request,
      final HttpServletResponse httpServletResponse) {
    contactService.delete(contactlistid, id);
    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
  }

}
