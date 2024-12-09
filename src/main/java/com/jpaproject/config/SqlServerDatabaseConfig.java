package com.jpaproject.config;


import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.jca.support.LocalConnectionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.jpaproject.repository",
        entityManagerFactoryRef = "sqlServerEntityManagerFactory",
        transactionManagerRef = "sqlServerTransactionManager",
        queryLookupStrategy = QueryLookupStrategy.Key.CREATE
)
public class SqlServerDatabaseConfig {



    @Bean
    @ConfigurationProperties(prefix = "sqlserver.datasource")
    public DataSource sqlServerDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "sqlServerEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean sqlServerEntityManagerFactory(){
        var factory = new LocalContainerEntityManagerFactoryBean();
        factory.setPersistenceUnitName("sql-server-unit");
        factory.setDataSource(sqlServerDataSource());
        factory.setPackagesToScan("com.jpaproject.entity");

        var adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);

        factory.setJpaVendorAdapter(adapter);
        Properties props = new Properties();
        props.setProperty("hibernate.hbm2ddl.auto","update");
        props.setProperty("hibernate.format_sql","true");
        props.setProperty("hibernate.show_sql","true");

        factory.setJpaProperties(props);
        return factory;
    }


    @Bean(name = "sqlServerTransactionManager")
    public PlatformTransactionManager sqlServerTransactionManager(EntityManagerFactory sqlServerEntityManagerFactory){
        var manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(sqlServerEntityManagerFactory);
        return manager;
    }
}
