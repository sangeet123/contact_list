package contactlist.service.serviceimpl;

import contactlist.entity.Contactlist;
import contactlist.model.request.ContactlistRequest;
import contactlist.model.response.ContactlistResponse;
import contactlist.repository.ContactListRepository;
import contactlist.service.ContactListService;
import exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  private static final Logger LOGGER = LoggerFactory.getLogger(ContactListServiceImpl.class);
  @Autowired() private ContactListRepository contactListRepository;

  private Contactlist getContactList(final Long id, final Long userId) {
    Contactlist contactList = contactListRepository.findByIdAndUserid(id, userId);
    if (contactList == null) {
      throw new NotFoundException("contact list with id " + id + " not found");
    }
    return contactList;
  }

  private ContactlistResponse save(final Contactlist contactlist){
    contactListRepository.save(contactlist);
    final ContactlistResponse contactlistResponse = new ContactlistResponse();
    contactlistResponse.setId(contactlist.getId());
    contactlistResponse.setName(contactlist.getName());
    return contactlistResponse;
  }

  @Override() public ContactlistResponse findByIdAndUserId(final Long id, final Long userId) {
    final Contactlist contactList = getContactList(id, userId);
    final ContactlistResponse contactListResponse = new ContactlistResponse();
    contactListResponse.setName(contactList.getName());
    contactListResponse.setId(contactList.getId());
    return contactListResponse;
  }

  @Override public List<ContactlistResponse> get(final Long userId, final Pageable pageable) {
    Iterable<contactlist.entity.Contactlist> contactLists = contactListRepository
        .findByUserid(userId, pageable);
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

  @Override public ContactlistResponse create(final Long userId,
      ContactlistRequest contactlistRequestRequest) {
    Contactlist contactlist = new Contactlist();
    contactlist.setUserid(userId);
    contactlist.setName(contactlistRequestRequest.getName());
    return save(contactlist);
  }

  @Override public ContactlistResponse update(final Long id, final Long userid,
      ContactlistRequest contactlistRequestRequest) {
    Contactlist contactList = getContactList(id, userid);
    contactList.setName(contactlistRequestRequest.getName());
    return save(contactList);
  }

  @Override public void delete(Long id, Long userId) {
    final Integer noOfRecordsDeleted = contactListRepository.removeByIdAndUserid(id, userId);
    if (noOfRecordsDeleted == 0) {
      throw new NotFoundException(
          "contactlist with " + id + " and userid " + userId + " does not exist");
    }
  }
}
