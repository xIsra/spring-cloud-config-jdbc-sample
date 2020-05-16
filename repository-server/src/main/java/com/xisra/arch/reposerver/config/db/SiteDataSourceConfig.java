package com.xisra.arch.reposerver.config.db;

import com.xisra.arch.reposerver.RepositoryServerApplication;
import com.xisra.arch.reposerver.lib.scanner.SiteRepositoryScanFilter;
import com.xisra.arch.reposerver.model.Member;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

import static com.xisra.arch.reposerver.constant.DatabaseConstant.SITE_SCHEMA;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackageClasses = RepositoryServerApplication.class,
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
                classes = SiteRepositoryScanFilter.class),
        entityManagerFactoryRef = "siteEntityManagerFactory",
        transactionManagerRef = "siteTransactionManager"
)
public class SiteDataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource." + SITE_SCHEMA)
    public DataSourceProperties siteDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource." + SITE_SCHEMA + ".configuration")
    public DataSource siteDataSource() {
        return siteDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "siteEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean siteEntityManagerFactory(EntityManagerFactoryBuilder builder, JpaProperties jpaProperties) {
        return builder
                .dataSource(siteDataSource())
//                .properties(Collections.singletonMap("hibernate.hbm2ddl.auto", "validate"))
                .properties(jpaProperties.getProperties())
                .packages(Member.class)
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager siteTransactionManager(
            final @Qualifier("siteEntityManagerFactory") LocalContainerEntityManagerFactoryBean siteEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(siteEntityManagerFactory.getObject()));
    }


    @Bean("siteMigrate")
    public Flyway siteMigrate() {
        FluentConfiguration configure = Flyway.configure();
        configure.schemas(SITE_SCHEMA);
        configure.dataSource(siteDataSource());
        configure.locations("db/migration/" + SITE_SCHEMA);
        return new Flyway(configure);
    }
}