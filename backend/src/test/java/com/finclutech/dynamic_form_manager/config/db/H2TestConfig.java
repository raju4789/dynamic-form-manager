package com.finclutech.dynamic_form_manager.config.db;

import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@Profile("test") // This configuration is only active when the "test" profile is active
@EnableJpaRepositories(
        basePackages = "com.finclutech.dynamic_form_manager.repositories.sql", // Replace with your repository package
        entityManagerFactoryRef = "h2EntityManagerFactory",
        transactionManagerRef = "h2TransactionManager"
)
public class H2TestConfig {

    @Bean
    public DataSource h2DataSource() {
        return org.springframework.boot.jdbc.DataSourceBuilder.create()
                .url("jdbc:h2:mem:testdb") // In-memory H2 database
                .driverClassName("org.h2.Driver")
                .username("sa")
                .password("")
                .build();
    }

    @Bean(name = "h2EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean h2EntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(h2DataSource())
                .packages("com.finclutech.dynamic_form_manager.entities.sql") // Replace with your entity package
                .persistenceUnit("h2")
                .build();
    }

    @Bean(name = "h2TransactionManager")
    public PlatformTransactionManager h2TransactionManager(LocalContainerEntityManagerFactoryBean h2EntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(h2EntityManagerFactory.getObject()));
    }
}