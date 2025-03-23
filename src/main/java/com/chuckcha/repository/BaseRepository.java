package com.chuckcha.repository;

import com.chuckcha.exceptions.DatabaseException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.hibernate.HibernateException;
import org.hibernate.graph.GraphSemantic;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BaseRepository<E> {

    EntityManager entityManager;

    public BaseRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(E entity) {
        try {
            entityManager.persist(entity);
        } catch (HibernateException e) {
            throw new DatabaseException("Database error!");
        }
    }

    public Optional<E> findSingleResult(String query, Class<E> clazz, Map<String, Object> parameters) {
        try {
            TypedQuery<E> typedQuery = entityManager.createQuery(query, clazz);
            parameters.forEach(typedQuery::setParameter);
            return Optional.ofNullable(typedQuery.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<E> findResultList(QueryBuilder<E> params) {
        try {
            TypedQuery<E> query = entityManager.createQuery(params.getQuery(), params.getClazz());
            params.getParameters().forEach(query::setParameter);
            query.setFirstResult(params.getOffset()).setMaxResults(params.getPageSize());

            if (params.getGraphName() != null) {
                query.setHint(GraphSemantic.LOAD.getJakartaHintName(), entityManager.getEntityGraph(params.getGraphName()));
            }

            return query.getResultList();
        } catch (HibernateException e) {
            throw new DatabaseException("Database error!");
        }
    }

    public Long getCount(QueryBuilder<Long> params) {
        try {
            TypedQuery<Long> query = entityManager.createQuery(params.getQuery(), Long.class);
            params.getParameters().forEach(query::setParameter);
            return query.getSingleResult();
        } catch (HibernateException e) {
            throw new DatabaseException("Database error!");
        }
    }

}
