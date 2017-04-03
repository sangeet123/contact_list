package contactlist.repository;

import contactlist.entity.Contactlist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by sangeet on 4/1/2017.
 */
public interface ContactListRepository extends PagingAndSortingRepository<Contactlist, Long> {
  @Query("select count(c)>0 from contactlist c WHERE c.userid= :userId AND c.id= :contactListId")
  boolean exist(@Param("userId") final Long userId, @Param("contactListId") final Long contactListId);
}
