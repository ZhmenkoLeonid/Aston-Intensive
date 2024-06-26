package com.zhmenko.database.migration;

import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FlywayMigration {
    public void migrate(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("db.migration")
                .load();
        flyway.migrate();
    }

    public void migrate() {
        final Properties properties = loadProperties();
        final String url = properties.getProperty("flyway.url");
        final String user = properties.getProperty("flyway.user");
        final String password = properties.getProperty("flyway.password");
        Flyway flyway = Flyway.configure()
                .dataSource(url, user, password)
                .locations("db.migration")
                .load();
        flyway.migrate();
    }

    private Properties loadProperties() {
        final Properties properties = new Properties();
        try (InputStream input = Thread.currentThread()
                                     .getContextClassLoader().
                                     getResourceAsStream("/conf/flyway.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}
