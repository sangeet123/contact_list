package contactlist.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by sangeet on 3/11/2017.
 */
@Entity(name = "contactlist") @Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = { "name", "userid" }) }) public class ContactList
    implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id() @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id", nullable = false) private Long id;
  @Column(name = "userid", nullable = false) private Long userid;
  @Column(name = "name", nullable = false) private String name;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "contactList", targetEntity = Contact.class,
      fetch = FetchType.LAZY) private Collection contacts;

  public ContactList() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getuserid() {
    return userid;
  }

  public void setUserid(final Long userid) {
    this.userid = userid;
  }

  public Collection getContacts() {
    return contacts;
  }

  public void setContacts(Collection contacts) {
    this.contacts = contacts;
  }

  @Override public String toString() {
    return "ContactList{" +
        "id=" + id +
        ", userid=" + userid +
        ", name='" + name + '\'' +
        '}';
  }
}
