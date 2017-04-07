package contactlist.service;

import contactlist.model.request.ContactlistRequest;
import contactlist.model.response.ContactlistResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sangeet on 4/1/2017.
 */
public interface ContactListService {
  ContactlistResponse findByIdAndUserId(final Long id, final Long userId);

  List<ContactlistResponse> get(final Long userId, final Pageable pageable);

  ContactlistResponse create(final Long userId, final ContactlistRequest contactlistRequestRequest);

  ContactlistResponse update(final Long id,final Long userId, final ContactlistRequest contactlistRequestRequest);

  void delete(final Long id, final Long userId);
}
