package contactlist.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by sangeet on 4/4/2017.
 */
public class ContactRequest {
  private static final int MAX_SIZE = 30;

  @JsonProperty(value = "firstName") @Size(max = MAX_SIZE) private String firstName;

  @JsonProperty(value = "lastName") @Size(max = MAX_SIZE) private String lastName;

  @JsonProperty(value = "email") @NotEmpty() @Email() private String email;

  @JsonProperty(value = "phoneNumber") @Pattern(regexp = "^[0-9]{7,15}") private String phoneNumber;

  public ContactRequest() {
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(final String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Override public String toString() {
    return "ContactRequest{" +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", email='" + email + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        '}';
  }
}
