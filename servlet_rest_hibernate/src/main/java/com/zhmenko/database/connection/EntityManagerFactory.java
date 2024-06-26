package com.zhmenko.database.connection;


import jakarta.persistence.EntityManager;

public interface EntityManagerFactory {
    EntityManager getEntityManager();
}
