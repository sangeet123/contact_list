package contactlist.repository;

import contactlist.entity.Contactlist;
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
@Transactional()
@Repository()
public interface ContactListRepository extends PagingAndSortingRepository<Contactlist, Long> {
  Contactlist findByIdAndUserid(final Long id, final Long userid);

  List<Contactlist> findByUserid(final Long userid, final Pageable pageable);

  @Modifying()
  @Query("delete from contactlist where id= :id and userid= :userid")
  Integer removeByIdAndUserid(@Param("id") final Long id, @Param("userid")final Long userid);

}
