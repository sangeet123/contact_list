package contactlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration() @EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class}) @ComponentScan(basePackages = {
    "security*",
    "contactlist*","exception*","error*"}) @EnableJpaRepositories() @EntityScan() public class ContactListApplication {
  public static void main(String[] args) {
    SpringApplication.run(ContactListApplication.class, args);
    //		String salt = BCrypt.gensalt(12);
    //		String hashed_password = BCrypt.hashpw("v3rystr0ngPassword", salt);
    //		System.out.println(hashed_password);
    //
    //		System.out.println(BCrypt.checkpw("v3rystr0ngPassword", hashed_password));
  }
}
