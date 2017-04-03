package contactlist.api;

import contactlist.model.response.Contactlist;
import contactlist.service.ContactListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by sangeet on 3/6/2017.
 */
@Secured({ "ROLE_APP_ADMIN",
    "ROLE_APP_USER" }) @RestController() @RequestMapping("/contactlist") public class ContactListApi {

  @Autowired() private ContactListService contactListService;

  @RequestMapping(value = "/{id}/", method = RequestMethod.GET) public @ResponseBody() Contactlist get(
      @PathVariable Long id) {
    return contactListService.findById(id);
  }

  @RequestMapping(method = RequestMethod.GET) public @ResponseBody() List<Contactlist> get(
      final Pageable pageable) {
    return contactListService.get(pageable);
  }
}
