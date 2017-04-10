package contactlist.service.serviceimpl;

import contactlist.entity.Contact;
import contactlist.entity.ContactList;
import contactlist.model.request.ContactRequest;
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

  private static ContactResponse mapContactToContactResponse(final Contact contact) {
    final ContactResponse contactResponse = new ContactResponse();
    contactResponse.setId(contact.getId());
    contactResponse.setContactListId(contact.getContactList().getId());
    contactResponse.setEmail(contact.getEmail());
    contactResponse.setFirstName(contact.getFirstName());
    contactResponse.setLastName(contact.getLastName());
    contactResponse.setPhoneNumber(contact.getPhoneNumber());
    return contactResponse;
  }

  @Override public ContactResponse findByContactListIdAndContactId(Long contactListId,
      Long contactId) {
    final Contact contact = contactRepository.findByIdAndContactListId(contactListId, contactId);
    if (contact == null) {
      throw new NotFoundException(
          "contact with id " + contactId + " on contact list with id " + contactListId
              + " is not present");
    }
    return mapContactToContactResponse(contact);
  }

  @Override public List<ContactResponse> get(Long contactListId, Pageable pageable) {
    final List<Contact> contacts = contactRepository.findByContactListId(contactListId, pageable);
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

  @Override public ContactResponse create(Long contactListId, ContactRequest contactRequest) {
    final Contact contact = new Contact();
    final ContactList contactList = new ContactList();
    contactList.setId(contactListId);
    contact.setContactList(contactList);
    contact.setPhoneNumber(contactRequest.getPhoneNumber());
    contact.setLastName(contactRequest.getLastName());
    contact.setFirstName(contactRequest.getFirstName());
    contact.setEmail(contactRequest.getEmail());
    return mapContactToContactResponse(contactRepository.save(contact));
  }
}
