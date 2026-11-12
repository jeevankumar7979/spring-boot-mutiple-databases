package com.jeevankumar.app.mysql.config;

import com.jeevankumar.app.mysql.model.Customer;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.jeevankumar.app.mysql.repository",
        entityManagerFactoryRef = "mysqlEntityManagerFactory",
        transactionManagerRef = "mysqlTransactionManager"
)
public class MysqlDatabaseConfig {

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.mysql")
    DataSourceProperties mysqlDatasourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    DataSource mysqlDatasource (
            @Qualifier("mysqlDatasourceProperties")
            DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean
    @Primary
    LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("mysqlDatasource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages(Customer.class)
                .persistenceUnit("mysql")
                .build();
    }

    @Bean
    @Primary
    JpaTransactionManager mysqlTransactionManager(
            @Qualifier("mysqlEntityManagerFactory")
            EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
