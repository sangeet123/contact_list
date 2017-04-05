package contactlist.repository;

import contactlist.entity.Contactlist;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by sangeet on 4/1/2017.
 */
public interface ContactListRepository extends PagingAndSortingRepository<Contactlist, Long> {
  Contactlist findByIdAndUserid(final Long id, final Long userid);

}
