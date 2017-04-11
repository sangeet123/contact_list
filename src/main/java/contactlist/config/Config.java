package contactlist.config;

import contactlist.error.ConstraintVoilationMapper;
import contactlist.interceptors.ContactVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by sangeet on 4/7/2017.
 */
@Configuration("contactlist-config") public class Config extends WebMvcConfigurerAdapter {

  @Bean() public ConstraintVoilationMapper getMapper() {
    return new ConstraintVoilationMapper();
  }

  @Override() public void addInterceptors(final InterceptorRegistry registry) {
    registry.addInterceptor(contactVerifier()).addPathPatterns("/contact/**");
    ;
  }

  @Bean() public ContactVerifier contactVerifier() {
    return new ContactVerifier();
  }
}
