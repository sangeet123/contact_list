package contactlist.filter;

import contactlist.repository.ContactListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.NotFoundException;
import java.util.Map;

/**
 * Created by sangeet on 4/2/2017.
 */
public class ContactListVerifier extends HandlerInterceptorAdapter {
  private static final Logger LOGGER = LoggerFactory.getLogger(HandlerInterceptorAdapter.class);
  @Autowired() private ContactListRepository contactListRepository;

  @Override public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler) throws Exception {
    final Map pathVariables = (Map) request
        .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    final String stringId = (String) pathVariables.get("id");
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
      LOGGER.debug("{Not valid user id}",ex);
    }
    throw new NotFoundException();
  }
}
