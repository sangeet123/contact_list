package contactlist.model.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by sangeet on 3/11/2017.
 */
public class Contactlist {
  private Long id;
  private String name;
  private Long userid;

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public Long getUserid() {
    return userid;
  }

  public void setUserid(final Long userid) {
    this.userid = userid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override() public boolean equals(Object o) {
    if (this == o)
      return true;

    if (o == null || getClass() != o.getClass())
      return false;

    Contactlist that = (Contactlist) o;

    return new EqualsBuilder().append(getId(), that.getId()).append(getName(), that.getName())
        .append(userid, that.userid).isEquals();
  }

  @Override() public int hashCode() {
    return new HashCodeBuilder(17, 37).append(getId()).append(getName()).append(userid)
        .toHashCode();
  }

  @Override() public String toString() {
    return "Contactlist{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", userid=" + userid +
        '}';
  }
}
