package contactlist.model.response;

/**
 * Created by sangeet on 4/4/2017.
 */
public class ContactResponse {
  private Long contactListId;

  private Long id;

  private String firstName;

  private String lastName;

  private String email;

  private String phoneNumber;

  public ContactResponse() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
    return "ContactResponse{" +
        ", id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", email='" + email + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        '}';
  }
}
