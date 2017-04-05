package contactlist.api;

import contactlist.model.request.ContactlistRequest;
import contactlist.model.response.ContactlistResponse;
import contactlist.service.ContactListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

  @RequestMapping(method = RequestMethod.GET) public @ResponseBody() List<ContactlistResponse> get(
      final Pageable pageable) {
    return contactListService.get(pageable);
  }

  @RequestMapping(method = RequestMethod.POST) public @ResponseBody() ContactlistResponse create(
      final ContactlistRequest contactlistRequest) {
    return contactListService.create(contactlistRequest);
  }

  @RequestMapping(method = RequestMethod.PUT) public @ResponseBody() ContactlistResponse update(
      final ContactlistRequest contactlistRequest) {
    return contactListService.create(contactlistRequest);
  }
}
