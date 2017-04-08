package contactlist.service;

import contactlist.model.request.ContactListRequest;
import contactlist.model.response.ContactListResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sangeet on 4/1/2017.
 */
public interface ContactListService {
  ContactListResponse findByIdAndUserId(final Long id, final Long userId);

  List<ContactListResponse> get(final Long userId, final Pageable pageable);

  ContactListResponse create(final Long userId, final ContactListRequest contactListRequestRequest);

  ContactListResponse update(final Long id, final Long userId,
      final ContactListRequest contactListRequestRequest);

  void delete(final Long id, final Long userId);
}
