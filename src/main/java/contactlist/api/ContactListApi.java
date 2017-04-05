package contactlist.api;

import contactlist.model.request.ContactlistRequest;
import contactlist.model.response.ContactlistResponse;
import contactlist.service.ContactListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by sangeet on 3/6/2017.
 */
@Secured({ "ROLE_APP_ADMIN",
    "ROLE_APP_USER" }) @RestController() @RequestMapping("/contactlist") public class ContactListApi {

  @Autowired() private ContactListService contactListService;

  @RequestMapping(value = "/{id}", method = RequestMethod.GET) public @ResponseBody() ContactlistResponse get(
      @PathVariable Long id, final HttpServletRequest request) {
    final Long userId = (Long) request.getAttribute("userId");
    return contactListService.findByIdAndUserId(id, userId);
  }

  @RequestMapping(method = RequestMethod.GET) public @ResponseBody() List<ContactlistResponse> get(final HttpServletRequest request,
      final Pageable pageable) {
    final Long userId = (Long) request.getAttribute("userId");
    return contactListService.get(userId, pageable);
  }

  @RequestMapping(method = RequestMethod.POST) public @ResponseBody() ContactlistResponse create(
      final ContactlistRequest contactlistRequest) {
    return contactListService.create(contactlistRequest);
  }

  @RequestMapping(method = RequestMethod.PUT) public @ResponseBody() ContactlistResponse update(
      final ContactlistRequest contactlistRequest) {
    return contactListService.create(contactlistRequest);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE) public @ResponseBody() void delete(
      @PathVariable Long id, final HttpServletRequest request, final HttpServletResponse httpServletResponse) {
    final Long userId = (Long) request.getAttribute("userId");
    contactListService.delete(id, userId);
    httpServletResponse.setStatus(HttpStatus.ACCEPTED.value());
  }

}
