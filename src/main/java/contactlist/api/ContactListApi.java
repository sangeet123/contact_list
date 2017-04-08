package contactlist.api;

import contactlist.model.request.ContactListRequest;
import contactlist.model.response.ContactListResponse;
import contactlist.service.ContactListService;
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
 * Created by sangeet on 3/6/2017.
 */
@Secured({ "ROLE_APP_ADMIN",
    "ROLE_APP_USER" }) @RestController() @RequestMapping("/contactlist") public class ContactListApi {

  @Autowired() private ContactListService contactListService;

  @RequestMapping(value = "/{id}", method = RequestMethod.GET) public @ResponseBody() ContactListResponse get(
      @PathVariable Long id, final HttpServletRequest request) {
    final Long userId = (Long) request.getAttribute("userId");
    return contactListService.findByIdAndUserId(id, userId);
  }

  @RequestMapping(method = RequestMethod.GET) public @ResponseBody() List<ContactListResponse> get(
      final HttpServletRequest request, final Pageable pageable) {
    final Long userId = (Long) request.getAttribute("userId");
    return contactListService.get(userId, pageable);
  }

  @RequestMapping(method = RequestMethod.POST) public @ResponseBody() void create(
      @RequestBody() @Valid() final ContactListRequest contactListRequest,
      final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
    final Long userId = (Long) httpServletRequest.getAttribute("userId");
    final ContactListResponse response = contactListService.create(userId, contactListRequest);
    httpServletResponse.setHeader("location", "/contactlist/" + response.getId());
    httpServletResponse.setStatus(HttpStatus.CREATED.value());
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT) public void update(
      @PathVariable Long id, @RequestBody() @Valid() final ContactListRequest contactListRequest,
      final HttpServletRequest request, final HttpServletResponse httpServletResponse) {
    final Long userId = (Long) request.getAttribute("userId");
    contactListService.update(id, userId, contactListRequest);
    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE) public @ResponseBody() void delete(
      @PathVariable Long id, final HttpServletRequest request,
      final HttpServletResponse httpServletResponse) {
    final Long userId = (Long) request.getAttribute("userId");
    contactListService.delete(id, userId);
    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
  }

}
