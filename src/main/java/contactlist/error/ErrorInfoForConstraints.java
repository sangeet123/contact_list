package contactlist.error;

import error.ValidationErrorInfo;

import java.util.HashMap;

/**
 * Created by sangeet on 4/6/2017.
 */
public class ErrorInfoForConstraints {
  final static String UNIQUE_KEY_CONSTRAINT_ON_CONTACTLIST_NAME = "UK_contactlistname";
  final static HashMap<String, ValidationErrorInfo> constraintToValidationErrorMapper;

  static {
    constraintToValidationErrorMapper = new HashMap<>();

    //UNIQUE_KEY_CONSTRAINT_ON_CONTACTLIST_NAME
    final ValidationErrorInfo forUK_contactlist = new ValidationErrorInfo();
    forUK_contactlist.addFieldError("name", "ContactList with name already exists.");
    constraintToValidationErrorMapper
        .put(UNIQUE_KEY_CONSTRAINT_ON_CONTACTLIST_NAME, forUK_contactlist);
  }

  final static ValidationErrorInfo getValidationErrorInfo(final String constraintName) {
    return constraintToValidationErrorMapper.get(constraintName);
  }
}
