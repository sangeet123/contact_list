package contactlist.service.serviceimpl;

import contactlist.entity.Contacts;
import contactlist.model.response.ContactResponse;
import contactlist.repository.ContactRepository;
import contactlist.service.ContactService;
import exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sangeet on 4/9/2017.
 */
@Transactional() @Service() public class ContactServiceImpl implements ContactService {
  @Autowired() private ContactRepository contactRepository;

  private static ContactResponse mapContactToContactResponse(final Contacts contact) {
    final ContactResponse contactResponse = new ContactResponse();
    contactResponse.setId(contact.getId());
    contactResponse.setContactListId(contact.getContactListId());
    contactResponse.setEmail(contact.getEmail());
    contactResponse.setFirstName(contact.getFirstName());
    contactResponse.setLastName(contact.getLastname());
    contactResponse.setPhoneNumber(contact.getPhoneNumber());
    return contactResponse;
  }

  @Override public ContactResponse findByContactListIdAndContactId(Long contactListId,
      Long contactId) {
    final Contacts contact = contactRepository.findByIdAndContactListId(contactListId, contactId);
    if (contact == null) {
      throw new NotFoundException(
          "contact with id " + contactId + " on contact list with id " + contactListId
              + " is not present");
    }
    return mapContactToContactResponse(contact);
  }

  @Override public List<ContactResponse> get(Long contactListId, Pageable pageable) {
    final List<Contacts> contacts = contactRepository.findByContactListId(contactListId, pageable);
    if (CollectionUtils.isEmpty(contacts)) {
      return new ArrayList<>();
    }
    return contacts.stream().map(ContactServiceImpl::mapContactToContactResponse)
        .collect(Collectors.toList());
  }

  @Override public void delete(Long contactListId, Long contactId) {
    final Integer noOfRecordsDeleted = contactRepository
        .removeByIdAndContactListId(contactListId, contactId);
    if (noOfRecordsDeleted == 0) {
      throw new NotFoundException(
          "contactlist with " + contactListId + " and contact id  " + contactId
              + " does not exist");
    }
  }
}
