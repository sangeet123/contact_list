package contactlist.repository;

import contactlist.entity.ContactList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sangeet on 4/1/2017.
 */
@Repository("contactListRepository") public interface ContactListRepository
    extends PagingAndSortingRepository<ContactList, Long> {
  ContactList findByIdAndUserid(final Long id, final Long userid);

  List<ContactList> findByUserid(final Long userid, final Pageable pageable);

  @Modifying() @Query("delete from contactlist where id= :id and userid= :userid") Integer removeByIdAndUserid(
      @Param("id") final Long id, @Param("userid") final Long userid);

  @Query("select count(c)>0 from contactlist c WHERE c.userid= :userId AND c.id= :contactListId") boolean exist(
      @Param("userId") final Long userId, @Param("contactListId") final Long contactListId);

}
