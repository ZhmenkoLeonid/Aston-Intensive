package com.zhmenko.author.data.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import com.zhmenko.author.data.model.AuthorEntity;
import com.zhmenko.exception.AuthorNotFoundException;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jpa.SpecHints;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Singleton
public class AuthorDaoImpl implements AuthorDao {
    private static final Logger log = LogManager.getLogger(AuthorDaoImpl.class);

    private final Provider<EntityManager> entityManagerProvider;

    @Inject
    public AuthorDaoImpl(final Provider<EntityManager> entityManager) {
        this.entityManagerProvider = entityManager;
    }


    @Transactional
    public AuthorEntity insertAuthor(final AuthorEntity author) {
        final EntityManager entityManager = entityManagerProvider.get();
        entityManager.persist(author);
        log.info("saving author using id - {}", author.getId());
        return author;
    }


    /**
     * Checks if an author with the given id exists in the database.
     *
     * @param id the id of the author to check
     * @return true if the author exists, false otherwise
     */
    public boolean isExistById(final Long id) {
        final EntityManager entityManager = entityManagerProvider.get();
        final long cnt = (long) entityManager
                .createQuery("SELECT COUNT(b) " +
                             "FROM AuthorEntity b " +
                             "WHERE b.id=:id")
                .setParameter("id", id)
                .getSingleResult();
        return cnt == 1;
    }

    @Override
    @Transactional
    public Optional<AuthorEntity> selectAuthorById(final Long id) {
        final EntityManager entityManager = entityManagerProvider.get();
        EntityGraph entityGraph = entityManager.getEntityGraph("graph.Authors.books");
        Map<String, Object> properties = new HashMap<>();
        properties.put("jakarta.persistence.fetchgraph", entityGraph);
        AuthorEntity authorEntity = entityManager.find(AuthorEntity.class, id, properties);
        log.info("finding author with id - {}", id);
        return Optional.ofNullable(authorEntity);
    }

    @Override
    @Transactional
    public AuthorEntity updateAuthor(final AuthorEntity authorEntity) {
        final AuthorEntity updatedAuthor = selectAuthorById(authorEntity.getId())
                .orElseThrow(() -> new AuthorNotFoundException(authorEntity.getId()));
        updatedAuthor.setFirstName(authorEntity.getFirstName());
        updatedAuthor.setSecondName(authorEntity.getSecondName());
        updatedAuthor.setThirdName(authorEntity.getThirdName());
        log.info("updating author with id - {}", authorEntity.getId());
        return updatedAuthor;
    }

    @Override
    @Transactional
    public AuthorEntity deleteAuthorById(final Long id) {
        final EntityManager entityManager = entityManagerProvider.get();
        final Optional<AuthorEntity> authorEntityOpt = selectAuthorById(id);
        final AuthorEntity authorEntity = authorEntityOpt.orElseThrow(() -> new AuthorNotFoundException(id));
        entityManager.remove(authorEntity);
        log.info("removing author with id - {}", id);
        return authorEntity;
    }

    @Override
    @SuppressWarnings("unckecked")
    @Transactional
    public List<AuthorEntity> selectAll() {
        final EntityManager entityManager = entityManagerProvider.get();
        EntityGraph entityGraph = entityManager.getEntityGraph("graph.Authors.books");
        final Query<AuthorEntity> fromAuthorEntity = entityManager.unwrap(Session.class)
                .createQuery("from AuthorEntity", AuthorEntity.class)
                .setHint(SpecHints.HINT_SPEC_FETCH_GRAPH, entityGraph);
        log.info("find all authors");
        return fromAuthorEntity.getResultList();
    }
}
