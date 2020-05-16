package com.xisra.arch.reposerver.config.db;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfiguration {

    @Autowired
    @Qualifier("coreMigrate")
    Flyway coreMigrate;

    @Autowired
    @Qualifier("siteMigrate")
    Flyway siteMigrate;

    @Bean(initMethod = "migrate")
    public Flyway migrate() {

        coreMigrate.clean();
        coreMigrate.migrate();
        siteMigrate.clean();
        siteMigrate.migrate();
        return coreMigrate;
    }

}
