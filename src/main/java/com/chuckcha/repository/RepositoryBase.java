package com.chuckcha.repository;

import com.chuckcha.entity.BaseEntity;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class RepositoryBase<K extends Serializable, E extends BaseEntity<K>> implements Repository<K, E> {

    private final Class<E> entityClass;
    @Getter
    private final EntityManager entityManager;

    public E save(E entity) {
        entityManager.persist(entity);
        return entity;
    }

    public void delete(K id) {
        entityManager.remove(id);
        entityManager.flush();
    }

    public E update(E entity) {
        return entityManager.merge(entity);
    }

    public Optional<E> findById(K id, Map<String, Object> properties) {
        return Optional.ofNullable(entityManager.find(entityClass, id, properties));
    }

    public List<E> findAll() {
        var criteria = entityManager.getCriteriaBuilder().createQuery(entityClass);
        criteria.from(entityClass);
        return entityManager.createQuery(criteria).getResultList();
    }
}
