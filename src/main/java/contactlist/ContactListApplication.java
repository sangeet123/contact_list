package contactlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration() @EnableAutoConfiguration(exclude = {
    SecurityAutoConfiguration.class }) @ComponentScan(basePackages = { "security*", "contactlist*",
    "exception*", "error*" }) public class ContactListApplication {
  public static void main(String[] args) {
    SpringApplication.run(ContactListApplication.class, args);
  }
}
