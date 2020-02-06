package com.spring.data.jpa.inMemoryTesting.config;

import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;


/**
 * Configuration class to load in-memory (H2) database to unit test repository logic
 * This class will external it's configurations through property file loaded via propertySource
 */

@Configuration
@PropertySource("application.properties")
@EnableJpaRepositories(basePackages = "com.spring.data.jpa.inMemoryTesting.dao")
public class SpringDataSourceConfiguration {

  @Autowired
  private Environment env;

  /**
   * Defines dataSource bean by reading configurations from properties file.
   * @return Datasource object for jdbc enabled database; H2 datasource in this case
   */
  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
    dataSource.setUrl(env.getProperty("jdbc.url"));
    dataSource.setUsername(env.getProperty("jdbc.user"));
    dataSource.setPassword(env.getProperty("jdbc.pass"));

    return dataSource;
  }

  /**
   * During creation of EmployeeRepository an instance of EntityManagerFactory is required to fetch entityManager(while setting bean's property)
   * @return Instance of EntityManagerFactory with all required properties
   */
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource());
    em.setPackagesToScan(new String[] { "com.spring.data.jpa.inMemoryTesting.model" });
    em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    em.setJpaProperties(additionalProperties());
    return em;
  }

  /**
   * Creation of transactionManager bean to perform CRUD operations via JPA
   * @param entityManagerFactory entityManagerFactory instance for defined datasource
   * @return
   */
  @Bean
  JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory);
    return transactionManager;
  }

  final Properties additionalProperties() {
    final Properties hibernateProperties = new Properties();

    hibernateProperties.setProperty("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
    hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("spring.jpa.show-sql"));
    // to be set for running DB tests with in-memory(H2) database and comment otherwise
    hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));

    /*hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", env.getProperty("hibernate.cache.use_second_level_cache"));
    hibernateProperties.setProperty("hibernate.cache.use_query_cache", env.getProperty("hibernate.cache.use_query_cache"));*/
    return hibernateProperties;
  }


}
