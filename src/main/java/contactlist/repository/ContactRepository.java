package contactlist.repository;

import contactlist.entity.Contact;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sangeet on 4/1/2017.
 */
@Transactional() @Repository() public interface ContactRepository
    extends PagingAndSortingRepository<Contact, Long> {
  Contact findByIdAndContactListId(final Long id, final Long contactListId);

  List<Contact> findByContactListId(final Long contactListId, final Pageable pageable);

  @Modifying() @Query("delete from contacts where id= :id and contactlistid= :contactlistid") Integer removeByIdAndContactListId(
      @Param("id") final Long id, @Param("contactlistid") final Long contactlistid);
}
