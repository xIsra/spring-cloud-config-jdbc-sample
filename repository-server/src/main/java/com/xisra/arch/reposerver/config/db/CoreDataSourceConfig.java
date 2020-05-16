package com.xisra.arch.reposerver.config.db;

import com.xisra.arch.reposerver.RepositoryServerApplication;
import com.xisra.arch.reposerver.lib.scanner.CoreRepositoryScanFilter;
import com.xisra.arch.reposerver.model.Card;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;

import static com.xisra.arch.reposerver.constant.DatabaseConstant.CORE_SCHEMA;

@Configuration
@EnableJpaRepositories(
        basePackageClasses = RepositoryServerApplication.class,
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
                classes = CoreRepositoryScanFilter.class),
        entityManagerFactoryRef = "coreEntityManagerFactory",
        transactionManagerRef = "coreTransactionManager"
)
public class CoreDataSourceConfig {
    @Bean
    @ConfigurationProperties("app.datasource." + CORE_SCHEMA)
    public DataSourceProperties coreDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("app.datasource." + CORE_SCHEMA + ".configuration")
    public DataSource coreDataSource() {
        return coreDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "coreEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean coreEntityManagerFactory(
            EntityManagerFactoryBuilder builder, JpaProperties jpaProperties) {
        return builder
                .dataSource(coreDataSource())
                .packages(Card.class)
//                .properties(Collections.singletonMap("hibernate.hbm2ddl.auto", "validate"))
                .properties(jpaProperties.getProperties())
                .build();
    }

    @Bean
    public PlatformTransactionManager coreTransactionManager(
            final @Qualifier("coreEntityManagerFactory") LocalContainerEntityManagerFactoryBean coreEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(coreEntityManagerFactory.getObject()));
    }

    @Bean("coreMigrate")
    public Flyway coreMigrate() {
        FluentConfiguration configure = Flyway.configure();
        configure.schemas(CORE_SCHEMA);
        configure.dataSource(coreDataSource());
        configure.locations("db/migration/" + CORE_SCHEMA);
        return new Flyway(configure);
    }
}
