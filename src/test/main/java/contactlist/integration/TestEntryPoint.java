package contactlist.integration;

import contactlist.integration.contactlist.endpoint.TestEndPointsForContactList;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

@SqlGroup({ @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
    "classpath:sql/create-schema.sql", "classpath:sql/create-user.sql",
    "classpath:sql/create-contactlist.sql" }),
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {
        "classpath:sql/drop-schema.sql" }) }) public class TestEntryPoint
    extends IntegrationTestConfigurer {
  @Test public void testGetContactList() throws Exception {
    final TestEndPointsForContactList testEndPointsForContactList = new TestEndPointsForContactList();
    testEndPointsForContactList.testGetById();
    testEndPointsForContactList.testGetContactList();
    testEndPointsForContactList.testGetContactListPaginationWithSorting();
    testEndPointsForContactList.testGetNotFoundById();
    testEndPointsForContactList.testSuccessFullDeleteById();
    testEndPointsForContactList.testDeleteByIdNotFound();
    testEndPointsForContactList.testSuccessFullCreate();
    testEndPointsForContactList.testBadContactlistPost();
    testEndPointsForContactList.testContactlistPostContactlistAlreadyExists();
    testEndPointsForContactList.testSuccessContactlistPutContactlistExists();
    testEndPointsForContactList.testBadContactlistPut();
    testEndPointsForContactList.testConflictContactlistPut();
  }
}
