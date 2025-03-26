package com.chuckcha.service;

import com.chuckcha.entity.Match;
import com.chuckcha.entity.MatchScore;
import com.chuckcha.entity.Player;
import com.chuckcha.exceptions.DataNotFoundException;
import com.chuckcha.exceptions.DatabaseException;
import com.chuckcha.repository.MatchRepository;
import com.chuckcha.repository.PlayerRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;

public class FinishedMatchesPersistenceService {

    private final SessionFactory sessionFactory;

    public FinishedMatchesPersistenceService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(MatchScore matchScore) throws DatabaseException {
        EntityManager entityManager = sessionFactory.getCurrentSession();
        entityManager.getTransaction().begin();
        MatchRepository matchRepository = new MatchRepository(entityManager);
        PlayerRepository playerRepository = new PlayerRepository(entityManager);
        String firstPlayerName = matchScore.getFirstPlayerScore().getPlayerName();
        String secondPlayerName = matchScore.getSecondPlayerScore().getPlayerName();
        Player firstPlayer = playerRepository.findByName(firstPlayerName)
                .orElseThrow(() -> new DatabaseException("Could not find player with name " + firstPlayerName));
        Player secondPlayer = playerRepository.findByName(secondPlayerName)
                .orElseThrow(() -> new DatabaseException("Could not find player with name " + secondPlayerName));
        Player winner;
        if (firstPlayerName.equals(matchScore.getWinnerName())) {
            winner = firstPlayer;
        } else if (secondPlayerName.equals(matchScore.getWinnerName())) {
            winner = secondPlayer;
        } else throw new DataNotFoundException("Winner wasn't set in game");
        Match match = Match.builder()
                .firstPlayer(firstPlayer)
                .secondPlayer(secondPlayer)
                .winner(winner)
                .build();
        matchRepository.save(match);
        entityManager.getTransaction().commit();
    }
}
