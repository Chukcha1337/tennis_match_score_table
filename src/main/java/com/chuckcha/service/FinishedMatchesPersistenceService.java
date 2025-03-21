package com.chuckcha.service;

import com.chuckcha.entity.Match;
import com.chuckcha.entity.MatchScore;
import com.chuckcha.repository.MatchRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class FinishedMatchesPersistenceService implements Service{

    private final SessionFactory sessionFactory;

    public FinishedMatchesPersistenceService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(MatchScore matchScore) {
        Session session = sessionFactory.getCurrentSession();
        MatchRepository matchRepository = new MatchRepository(session);
        session.beginTransaction();
        Match match = Match.builder()
                .firstPlayer(matchScore.getFirstPlayer())
                .secondPlayer(matchScore.getSecondPlayer())
                .winner(matchScore.getWinner())
                .build();
        matchRepository.save(match);
        session.getTransaction().commit();
    }
}
