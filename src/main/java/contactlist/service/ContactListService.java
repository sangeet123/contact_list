package contactlist.service;

import contactlist.model.request.ContactlistRequest;
import contactlist.model.response.ContactlistResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sangeet on 4/1/2017.
 */
public interface ContactListService {
  ContactlistResponse findById(final Long id);

  List<ContactlistResponse> get(final Pageable pageable);

  ContactlistResponse create(final ContactlistRequest contactlistRequestRequest);

  ContactlistResponse update(final ContactlistRequest contactlistRequestRequest);

}
