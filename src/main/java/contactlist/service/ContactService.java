package contactlist.service;

import contactlist.model.response.ContactResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sangeet on 4/8/2017.
 */
public interface ContactService {
  ContactResponse findByContactListIdAndContactId(final Long contactListId,final Long contactId);

  List<ContactResponse> get(final Long contactListId, final Pageable pageable);

  public void delete(final Long contactListId, final Long contactId);
}
