package com.chuckcha.service;

import com.chuckcha.entity.Match;
import com.chuckcha.entity.MatchScore;
import com.chuckcha.repository.MatchRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;

public class FinishedMatchesPersistenceService implements Service{

    private final SessionFactory sessionFactory;

    public FinishedMatchesPersistenceService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(MatchScore matchScore) {
        EntityManager entityManager = sessionFactory.getCurrentSession();
        entityManager.getTransaction().begin();
        MatchRepository matchRepository = new MatchRepository(entityManager);
        Match match = Match.builder()
                .firstPlayer(matchScore.getFirstPlayer())
                .secondPlayer(matchScore.getSecondPlayer())
                .winner(matchScore.getWinner())
                .build();
        matchRepository.save(match);
        entityManager.getTransaction().commit();
    }
}
