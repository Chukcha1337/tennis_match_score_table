package com.chuckcha.repository;

import com.chuckcha.entity.Player;
import jakarta.persistence.EntityManager;

public class PlayerRepository extends RepositoryBase<Integer, Player> {

    public PlayerRepository(EntityManager entityManager) {
        super(Player.class, entityManager);
    }
}
