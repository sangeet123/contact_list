package contactlist.service.serviceimpl;

import contactlist.exceptions.NotFoundException;
import contactlist.model.request.ContactlistRequest;
import contactlist.model.response.ContactlistResponse;
import contactlist.repository.ContactListRepository;
import contactlist.service.ContactListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sangeet on 4/1/2017.
 */
@Transactional() @Service() public class ContactListServiceImpl implements ContactListService {
  @Autowired() private ContactListRepository contactListRepository;

  @Override() public ContactlistResponse findByIdAndUserId(final Long id, final Long userId) {
    contactlist.entity.Contactlist contactList = contactListRepository.findByIdAndUserid(id, userId);
    if (contactList == null) {
      throw new NotFoundException("contact list with id " + id + " not found");
    }
    final ContactlistResponse contactListResponse = new ContactlistResponse();
    contactListResponse.setName(contactList.getName());
    contactListResponse.setId(contactList.getId());
    return contactListResponse;
  }

  @Override public List<ContactlistResponse> get(final Long userId, final Pageable pageable) {
    Iterable<contactlist.entity.Contactlist> contactLists = contactListRepository.findByUserid(userId,pageable);
    final List<ContactlistResponse> contactListsResponse = new ArrayList<>();
    if (contactLists == null) {
      return contactListsResponse;
    }
    contactLists.iterator().forEachRemaining(contactlist -> {
      final ContactlistResponse ctlist = new ContactlistResponse();
      ctlist.setId(contactlist.getId());
      ctlist.setName(contactlist.getName());
      contactListsResponse.add(ctlist);
    });
    return contactListsResponse;
  }

  @Override public ContactlistResponse create(ContactlistRequest contactlistRequestRequest) {
    return null;
  }

  @Override public ContactlistResponse update(ContactlistRequest contactlistRequestRequest) {
    return null;
  }

  @Override public void delete(Long id, Long userId) {
    final Integer noOfRecordsDeleted = contactListRepository.removeByIdAndUserid(id, userId);
    if(noOfRecordsDeleted == 0){
      throw new NotFoundException("contactlist with " + id + " and userid " + userId + " does not exist");
    }
  }
}
