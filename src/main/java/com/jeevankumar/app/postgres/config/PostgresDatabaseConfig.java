package com.jeevankumar.app.postgres.config;

import com.jeevankumar.app.postgres.model.PurchaseOrder;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties
@EnableJpaRepositories(
        basePackages = "com.jeevankumar.app.postgres.repository",
        entityManagerFactoryRef = "postgresEntityManagerFactory",
        transactionManagerRef = "postgresTransactionManager"
)
public class PostgresDatabaseConfig {

    @Bean
    @ConfigurationProperties("app.datasource.postgres")
    DataSourceProperties postgresDatasourceProperties () {
        return new DataSourceProperties();
    }

    @Bean
    DataSource postgresDatasource (
            @Qualifier("postgresDatasourceProperties")
            DataSourceProperties properties
    ) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean
    LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory (
            EntityManagerFactoryBuilder builder,
            @Qualifier("postgresDatasource")
            DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages(PurchaseOrder.class)
                .persistenceUnit("postgres")
                .build();
    }

    @Bean
    PlatformTransactionManager postgresTransactionManager (
            @Qualifier("postgresEntityManagerFactory")
            EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
