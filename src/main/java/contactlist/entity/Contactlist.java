package contactlist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by sangeet on 3/11/2017.
 */
@Entity(name = "contactlist") public class Contactlist implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id() @GeneratedValue() @Column(name = "id", nullable = false) private Long id;
  @Column(name="userid", nullable = false) private Long userid;
  @Column(name = "name", nullable = false) private String name;

  public Contactlist() {
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

  @Override public String toString() {
    return "Contactlist{" +
        "id=" + id +
        ", userid=" + userid +
        ", name='" + name + '\'' +
        '}';
  }
}
