package com.chuckcha.repository;

import com.chuckcha.entity.Match;
import jakarta.persistence.EntityManager;

public class MatchRepository extends RepositoryBase<Integer, Match> {

    public MatchRepository(EntityManager entityManager) {
        super(Match.class, entityManager);
    }
}
