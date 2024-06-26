package com.zhmenko.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.zhmenko.database.migration.FlywayMigration;
import jakarta.servlet.ServletContextEvent;

public class ServletConfig extends GuiceServletContextListener {

    @Override
    public void contextInitialized(final ServletContextEvent servletContextEvent) {
        super.contextInitialized(servletContextEvent);
        new FlywayMigration().migrate();
    }

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new DependencyInjectionConfig());
    }
}