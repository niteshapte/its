package com.discovery.interstellar.transport.system.configuration;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * The Class PersistenceBean.
 */
@Configuration
@EnableTransactionManagement
public class PersistenceBean {

    /**
     * Session factory.
     *
     * @param dataSource the data source
     * @param properties the properties
     * @return the local session factory bean
     */
    @Bean
    @Autowired
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource, @Qualifier("hibernateProperties") Properties properties) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("za.co.discovery.assignment.entity");
        sessionFactory.setHibernateProperties(properties);

        return sessionFactory;
    }

    /**
     * Transaction manager.
     *
     * @param sessionFactory the session factory
     * @return the hibernate transaction manager
     */
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(final SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    }

    /**
     * Exception translation.
     *
     * @return the persistence exception translation post processor
     */
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    /**
     * Properties.
     *
     * @return the properties
     */
    @Bean
    @Qualifier("hibernateProperties")
    public Properties properties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.DerbyTenSevenDialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.hbm2ddl.auto", "create");

        return properties;
    }
}

