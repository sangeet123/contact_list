package contactlist.service.serviceimpl;

import contactlist.entity.ContactList;
import contactlist.model.request.ContactListRequest;
import contactlist.model.response.ContactListResponse;
import contactlist.repository.ContactListRepository;
import contactlist.service.ContactListService;
import exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sangeet on 4/1/2017.
 */
@Transactional("contactListTransactionManager") @Service() public class ContactListServiceImpl
    implements ContactListService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ContactListServiceImpl.class);
  @Autowired() @Qualifier("contactListRepository") private ContactListRepository contactListRepository;

  private ContactList getContactList(final Long id, final Long userId) {
    ContactList contactList = contactListRepository.findByIdAndUserid(id, userId);
    if (contactList == null) {
      throw new NotFoundException("contact list with id " + id + " not found");
    }
    return contactList;
  }

  private ContactListResponse save(final ContactList contactList) {
    contactListRepository.save(contactList);
    final ContactListResponse contactListResponse = new ContactListResponse();
    contactListResponse.setId(contactList.getId());
    contactListResponse.setName(contactList.getName());
    return contactListResponse;
  }

  @Override() public ContactListResponse findByIdAndUserId(final Long id, final Long userId) {
    final ContactList contactList = getContactList(id, userId);
    final ContactListResponse contactListResponse = new ContactListResponse();
    contactListResponse.setName(contactList.getName());
    contactListResponse.setId(contactList.getId());
    return contactListResponse;
  }

  @Override public List<ContactListResponse> get(final Long userId, final Pageable pageable) {
    Iterable<ContactList> contactLists = contactListRepository.findByUserid(userId, pageable);
    final List<ContactListResponse> contactListsResponse = new ArrayList<>();
    if (contactLists == null) {
      return contactListsResponse;
    }
    contactLists.iterator().forEachRemaining(contactlist -> {
      final ContactListResponse ctlist = new ContactListResponse();
      ctlist.setId(contactlist.getId());
      ctlist.setName(contactlist.getName());
      contactListsResponse.add(ctlist);
    });
    return contactListsResponse;
  }

  @Override public ContactListResponse create(final Long userId,
      ContactListRequest contactListRequestRequest) {
    ContactList contactList = new ContactList();
    contactList.setUserid(userId);
    contactList.setName(contactListRequestRequest.getName());
    return save(contactList);
  }

  @Override public ContactListResponse update(final Long id, final Long userid,
      ContactListRequest contactListRequestRequest) {
    ContactList contactList = getContactList(id, userid);
    contactList.setName(contactListRequestRequest.getName());
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
