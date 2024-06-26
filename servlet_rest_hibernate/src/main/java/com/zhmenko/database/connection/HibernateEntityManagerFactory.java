package com.zhmenko.database.connection;

import jakarta.persistence.EntityManager;

public class HibernateEntityManagerFactory implements EntityManagerFactory {
    @Override
    public EntityManager getEntityManager() {
        return null;
    }
/*    private static final Logger log = LogManager.getLogger(HibernateEntityManagerFactory.class);
    private SessionFactory sessionFactory;

    @Override
    public EntityManager getEntityManager() {
        if (sessionFactory == null) initSessionFactory();
        return sessionFactory.createEntityManager();
    }

    private void initSessionFactory() {
        StandardServiceRegistry standardRegistry
                = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        Metadata metadata = new MetadataSources(standardRegistry)
                .getMetadataBuilder()
                .build();

        sessionFactory = metadata.getSessionFactoryBuilder().build();
        log.debug("Hibernate session factory initialized successfully");
    }*/
}
