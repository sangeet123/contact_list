package contactlist.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * Created by sangeet on 3/11/2017.
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL) public class ContactListRequest {
  private static final int NAME_MAX_SIZE = 30;
  @JsonProperty(value = "name") @NotEmpty() @Size(max = NAME_MAX_SIZE) private String name;

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }
}
