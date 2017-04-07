package contactlist.error;

import error.ValidationErrorInfo;
import exception.mapper.DataIntegrityViolationMapper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

/**
 * Created by sangeet on 4/6/2017.
 */
@Component("contactlistconstraints") public class ConstraintVoilationMapper
    implements DataIntegrityViolationMapper {

  @Override public ValidationErrorInfo mapExceptoin(final DataIntegrityViolationException e) {
    if (e.getCause() instanceof ConstraintViolationException) {
      final ConstraintViolationException constraintViolationException = (ConstraintViolationException) e
          .getCause();
      return ErrorInfoForConstraints.getValidationErrorInfo(constraintViolationException.getConstraintName());
    }
    return null;
  }
}
