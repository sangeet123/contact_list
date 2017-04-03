package contactlist.service.serviceimpl;

import contactlist.model.response.Contactlist;
import contactlist.repository.ContactListRepository;
import contactlist.service.ContactListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sangeet on 4/1/2017.
 */
@Transactional() @Service() public class ContactListServiceImpl implements ContactListService {
  @Autowired() private ContactListRepository contactListRepository;

  @Override() public Contactlist findById(final Long id) {
    contactlist.entity.Contactlist contactList = contactListRepository.findOne(id);
    if (contactList == null) {
      throw new NotFoundException("contact list with id " + id + " not found");
    }
    final Contactlist contactListResponse = new Contactlist();
    contactListResponse.setName(contactList.getName());
    contactListResponse.setId(contactList.getId());
    contactListResponse.setUserid(contactList.getuserid());
    return contactListResponse;
  }

  @Override public List<Contactlist> get(final Pageable pageable) {
    Iterable<contactlist.entity.Contactlist> contactLists = contactListRepository.findAll(pageable);
    final List<Contactlist> contactListsResponse = new ArrayList<>();
    if (contactLists == null) {
      return contactListsResponse;
    }
    contactLists.iterator().forEachRemaining(contactlist -> {
      final Contactlist ctlist = new Contactlist();
      ctlist.setId(contactlist.getId());
      ctlist.setName(contactlist.getName());
      ctlist.setUserid(contactlist.getuserid());
      contactListsResponse.add(ctlist);
    });
    return contactListsResponse;
  }
}
