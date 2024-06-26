package com.zhmenko;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.zhmenko.connection.DependencyInjectionConfigTest;
import com.zhmenko.utils.SqlUtils;
import jakarta.persistence.EntityManager;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;

public class AbstractDaoTest {
    private static final String DB_SCRIPT_PATH = "aston_db_script.sql";

    @ClassRule
    protected static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest");

    protected static Injector injector;
    private Connection connection;

    @BeforeAll
    public static void setUp() {
        postgresContainer.start();
        System.setProperty("db.url", postgresContainer.getJdbcUrl().toString());
        System.setProperty("db.username", postgresContainer.getUsername());
        System.setProperty("db.password", postgresContainer.getPassword());
        injector = Guice.createInjector(
                new DependencyInjectionConfigTest(postgresContainer.getJdbcUrl(),
                        postgresContainer.getUsername(),
                        postgresContainer.getPassword())
        );
    }

    @BeforeEach
    public void connectToDatabase() throws Exception {
        String jdbcUrl = postgresContainer.getJdbcUrl();
        String username = postgresContainer.getUsername();
        String password = postgresContainer.getPassword();

        connection = DriverManager.getConnection(jdbcUrl, username, password);
        SqlUtils.executeSql(DB_SCRIPT_PATH, connection);
    }

    @AfterEach
    public void closeConnection() throws Exception {
        connection.close();
        injector.getInstance(EntityManager.class).clear();
    }
}
