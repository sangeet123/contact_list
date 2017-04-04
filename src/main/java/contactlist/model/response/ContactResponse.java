package contactlist.model.response;

/**
 * Created by sangeet on 4/4/2017.
 */
public class ContactResponse {
  private Long id;

  private String firstName;

  private String lastname;

  private String email;

  private String phoneNumber;

  public ContactResponse() {
  }

  public Long getContactListId() {
    return id;
  }

  public void setContactListId(final Long id) {
    this.id = id;
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
    return "ContactResponse{" +
        ", id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastname='" + lastname + '\'' +
        ", email='" + email + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        '}';
  }
}
