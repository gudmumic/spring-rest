package com.gudmumic.spring.rest.bootstrap;

import jakarta.persistence.PreUpdate;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
//@Component
public class FlywayMigrationRunner implements CommandLineRunner {
    @PreUpdate
    public void migrate() {
        Flyway.configure()
                .dataSource("jdbc:mysql://172.172.0.3:3306/restdb", "root", "password")
                .locations("classpath:db/migration")
                .load()
                .migrate();
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("***** Starting Flyway migration *****");
        migrate();
    }
}
