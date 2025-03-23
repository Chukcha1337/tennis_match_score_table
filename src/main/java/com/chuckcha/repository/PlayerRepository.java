package com.chuckcha.repository;

import com.chuckcha.entity.Player;
import jakarta.persistence.EntityManager;

import java.util.Map;
import java.util.Optional;

public class PlayerRepository extends BaseRepository<Player>{

    private static final String HQL_QUERY = "select p from Player p where p.name = :name";

    public PlayerRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public Optional<Player> findByName(String name) {
        Map<String, Object> params = Map.of("name", name);
        return findSingleResult(HQL_QUERY, Player.class, params);
    }
}
