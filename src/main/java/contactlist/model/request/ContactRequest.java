package contactlist.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by sangeet on 4/4/2017.
 */
public class ContactRequest {
  private static final int MAX_SIZE = 30;

  private Long contactListId;

  @JsonProperty(value = "firstName") @NotNull() @Size(max = MAX_SIZE)
  private String firstName;

  @JsonProperty(value = "lastName") @NotNull() @Size(max = MAX_SIZE)
  private String lastname;

  @JsonProperty(value = "email")
  @NotNull()
  private String email;

  @JsonProperty(value = "phoneNumber")
  private String phoneNumber;

  public ContactRequest() {
  }

  public Long getContactListId() {
    return contactListId;
  }

  public void setContactListId(final Long contactListId) {
    this.contactListId = contactListId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(final String lastname) {
    this.lastname = lastname;
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
        ", contactListId=" + contactListId +
        ", firstName='" + firstName + '\'' +
        ", lastname='" + lastname + '\'' +
        ", email='" + email + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        '}';
  }
}
