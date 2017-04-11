package contactlist.interceptors;

import contactlist.repository.ContactListRepository;
import exceptions.NotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by sangeet on 4/9/2017.
 */
public class ContactVerifier extends HandlerInterceptorAdapter {
  @Autowired() private ContactListRepository contactListRepository;

  @Override public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler) throws Exception {
    final Map pathVariables = (Map) request
        .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    final String stringId = (String) pathVariables.get("contactlistid");
    final Long userId = (Long) request.getAttribute("userId");

    if (StringUtils.isEmpty(stringId)) {
      return true;
    }
    try {
      final Long id = Long.parseLong(stringId);
      if (contactListRepository.exist(userId, id)) {
        return true;
      }
    } catch (final NumberFormatException ex) {
    }
    throw new NotFoundException();
  }

}
