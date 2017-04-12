package contactlist.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by sangeet on 4/4/2017.
 */
@Entity(name = "contacts") @Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = { "email", "contactlistid" }) }) public class Contact
    implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id() @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id", nullable = false) private Long id;

  @Column(name = "firstname") private String firstName;

  @Column(name = "lastname") private String lastname;

  @Column(name = "email") private String email;

  @Column(name = "phonenumber") private String phoneNumber;

  @ManyToOne(optional = false) @JoinColumn(name = "contactlistid", referencedColumnName = "id") private ContactList contactList;

  public Contact() {
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastname;
  }

  public void setLastName(final String lastname) {
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

  public ContactList getContactList() {
    return contactList;
  }

  public void setContactList(final ContactList contactList) {
    this.contactList = contactList;
  }

  @Override public String toString() {
    return "Contact{" +

        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastname='" + lastname + '\'' +
        ", email='" + email + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        '}';
  }
}
