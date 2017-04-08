package contactlist.config;

import contactlist.error.ConstraintVoilationMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sangeet on 4/7/2017.
 */
@Configuration("contactlist-config") public class Config {

  @Bean() public ConstraintVoilationMapper getMapper() {
    return new ConstraintVoilationMapper();
  }
}
