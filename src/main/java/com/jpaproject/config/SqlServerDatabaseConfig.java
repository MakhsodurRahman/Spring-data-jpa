package com.jpaproject.config;


import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
public class SqlServerDatabaseConfig {

    @Bean
    @ConfigurationProperties(prefix = "sqlserver.datasource")
    public DataSource sqlServerDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean sqlServerEntityManagerFactory(){
        var fectory = new LocalContainerEntityManagerFactoryBean();
        fectory.setPersistenceUnitName("sql-server-unit");
        fectory.setDataSource(sqlServerDataSource());
        fectory.setPackagesToScan("com.jpaproject.entity");

        var adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);

        fectory.setJpaVendorAdapter(adapter);
        Properties props = new Properties();
        props.setProperty("hibernate.hbm2ddl.auto","update");
        props.setProperty("hibernate.format_sql","true");
        props.setProperty("hibernate.show_sql","true");

        fectory.setJpaProperties(props);
        return fectory;
    }


    @Bean
    public PlatformTransactionManager sqlServerTransactionManager(EntityManagerFactory sqlServerEntityManagerFactory){
        var manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(sqlServerEntityManagerFactory);
        return manager;
    }
}
