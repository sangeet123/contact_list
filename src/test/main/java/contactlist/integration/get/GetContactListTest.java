package contactlist.integration.get;

import com.fasterxml.jackson.core.type.TypeReference;
import contactlist.integration.IntegrationTestConfigurer;
import contactlist.model.response.Contactlist;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SqlGroup({ @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
    "classpath:sql/create-schema.sql", "classpath:sql/create-user.sql",
    "classpath:sql/create-contactlist.sql" }),
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {
        "classpath:sql/drop-user.sql", "classpath:sql/drop-contactlist.sql",
        "classpath:sql/drop-schema.sql" }) }) public class GetContactListTest
    extends IntegrationTestConfigurer {
  @Test public void testContactListGet() throws Exception {
    final ResponseEntity<String> responseEntity = this.doGet("/contactlist");
    assertEquals(200, responseEntity.getStatusCode().value());
    List<Contactlist> ctlist = readEntity(responseEntity, new TypeReference<List<Contactlist>>() {
    });
    assertTrue(ctlist.size() == 3);
    assertEquals(ctlist.get(0).getName(), "Friends");
    assertEquals(ctlist.get(1).getName(), "Family");
    assertEquals(ctlist.get(2).getName(), "Others");

    assertEquals(ctlist.get(0).getId(), Long.valueOf(1));
    assertEquals(ctlist.get(1).getId(), Long.valueOf(2));
    assertEquals(ctlist.get(2).getId(), Long.valueOf(3));
  }
}
