package contactlist.integration;

import contactlist.ContactListApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by sangeet on 4/2/2017.
 */
@RunWith(SpringRunner.class) @Configuration() @SpringBootTest(classes = {
    ContactListApplication.class },
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) @TestPropertySource(locations = {
    "classpath:test.properties" }) @ContextConfiguration(classes = IntegrationTestUtils.class) public abstract class IntegrationTestConfigurer {
}
