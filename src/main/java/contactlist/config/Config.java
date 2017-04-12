package contactlist.config;

import contactlist.error.ConstraintVoilationMapper;
import contactlist.interceptors.ContactVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sangeet on 4/7/2017.
 */
@EnableJpaRepositories(basePackages = "contactlist.repository",
    entityManagerFactoryRef = "contactListEntityManager",
    transactionManagerRef = "contactListTransactionManager") @EntityScan("contactlist.entity") @Configuration("contactlist-config") public class Config
    extends WebMvcConfigurerAdapter {

  @Autowired() private Environment environment;

  @Bean() public ConstraintVoilationMapper getMapper() {
    return new ConstraintVoilationMapper();
  }

  @Override() public void addInterceptors(final InterceptorRegistry registry) {
    registry.addInterceptor(contactVerifier()).addPathPatterns("/contact/**");
  }

  @Bean() public ContactVerifier contactVerifier() {
    return new ContactVerifier();
  }

  @Primary() @Bean("contactListEntityManager") public LocalContainerEntityManagerFactoryBean contactListEntityManager() {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(contactListDataSource());
    em.setPackagesToScan(new String[] { "contactlist.entity" });

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);
    Map<String, Object> properties = new HashMap<>();
    properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
    properties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
    properties.put("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
    em.setJpaPropertyMap(additionalProperties());
    return em;
  }

  @Primary() @Bean("contactListDataSource") public DataSource contactListDataSource() {

    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource
        .setDriverClassName(environment.getProperty("contactlist.datasource.driverClassName"));
    dataSource.setUrl(environment.getProperty("contactlist.datasource.url"));
    dataSource.setUsername(environment.getProperty("contactlist.datasource.username"));
    dataSource.setPassword(environment.getProperty("contactlist.datasource.password"));

    return dataSource;
  }

  @Primary() @Bean("contactListTransactionManager") public PlatformTransactionManager contactListTransactionManager() {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(contactListEntityManager().getObject());
    return transactionManager;
  }

  final Map<String, Object> additionalProperties() {
    Map<String, Object> properties = new HashMap<>();
    properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
    properties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
    properties.put("hibernate.cache.use_second_level_cache",
        environment.getProperty("hibernate.cache.use_second_level_cache"));
    properties.put("hibernate.cache.use_query_cache",
        environment.getProperty("hibernate.cache.use_query_cache"));
    properties.put("hibernate.id.new_generator_mappings",
        environment.getProperty("hibernate.id.new_generator_mappings=false"));
    return properties;
  }
}
