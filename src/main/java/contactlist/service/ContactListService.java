package contactlist.service;

import contactlist.model.response.Contactlist;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sangeet on 4/1/2017.
 */
public interface ContactListService {
  Contactlist findById(final Long id);

  List<Contactlist> get(final Pageable pageable);

}
