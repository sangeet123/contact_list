package contactlist.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by sangeet on 4/4/2017.
 */
@Entity(name = "contact")
public class Contact implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id() @GeneratedValue() @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "contactlistid", nullable = false)
  private Long contactListId;

  @Column(name = "firstname")
  private String firstName;

  @Column(name = "lastname")
  private String lastname;

  @Column(name = "email")
  private String email;

  @Column(name = "phonenumber")
  private String phoneNumber;

  @ManyToOne(optional = false)
  @JoinColumn(name="contactlist",referencedColumnName="id")
  private Contactlist contactlist;

  public Contact() {
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
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

  public Contactlist getContactlist() {
    return contactlist;
  }

  public void setContactlist(Contactlist contactlist) {
    this.contactlist = contactlist;
  }

  @Override public String toString() {
    return "Contact{" +

        "id=" + id +
        ", contactListId=" + contactListId +
        ", firstName='" + firstName + '\'' +
        ", lastname='" + lastname + '\'' +
        ", email='" + email + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        '}';
  }
}
