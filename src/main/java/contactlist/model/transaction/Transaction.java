package contactlist.model.transaction;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by sangeet on 4/10/2017.
 */
@Scope("request") @Component() public class Transaction {
  private HttpServletResponse httpServletResponse;
  private HttpServletRequest httpServletRequest;

  public Transaction() {
    this.httpServletRequest = ((ServletRequestAttributes) RequestContextHolder
        .currentRequestAttributes()).getRequest();
    this.httpServletResponse = ((ServletRequestAttributes) RequestContextHolder
        .currentRequestAttributes()).getResponse();
  }

  public void setLocationHeader(final String location) {
    httpServletResponse.setHeader("location", location);
  }

  public void setReturnStatus(final int statusCode) {
    httpServletResponse.setStatus(statusCode);
  }

  public Long getUserIdFromHeader() {
    final Long userId = (Long) httpServletRequest.getAttribute("userId");
    return userId;
  }
}
