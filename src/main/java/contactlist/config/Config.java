package contactlist.config;

import contactlist.filter.ContactListVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by sangeet on 4/2/2017.
 */
@Configuration("contactlistconfig")
public class Config extends WebMvcConfigurerAdapter {

    @Override()
    public void addInterceptors(final InterceptorRegistry registry) {
      registry.addInterceptor(contactListVerifier());
    }

    @Bean()
    public ContactListVerifier contactListVerifier() {
      return new ContactListVerifier();
    }
}
