package contactlist.model.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by sangeet on 3/11/2017.
 */
public class ContactlistResponse {
  private Long id;
  private String name;

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
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

    ContactlistResponse that = (ContactlistResponse) o;

    return new EqualsBuilder().append(getId(), that.getId()).append(getName(), that.getName())
        .isEquals();
  }

  @Override() public int hashCode() {
    return new HashCodeBuilder(17, 37).append(getId()).append(getName())
        .toHashCode();
  }

  @Override() public String toString() {
    return "ContactlistResponse{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
