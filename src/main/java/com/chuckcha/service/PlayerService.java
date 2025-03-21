package com.chuckcha.service;

import com.chuckcha.entity.Player;
import com.chuckcha.repository.PlayerRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class PlayerService implements Service {

    private final SessionFactory sessionFactory;

    public PlayerService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Player> checkOrCreatePlayers(String playerOneName, String playerTwoName) {
        Session session = sessionFactory.getCurrentSession();

        Player currentPlayerOne;
        Player currentPlayerTwo;

        session.beginTransaction();
        PlayerRepository playerRepository = new PlayerRepository(session);

        Optional<Player> maybePlayer1 = playerRepository.findByName(playerOneName);
        Optional<Player> maybePlayer2 = playerRepository.findByName(playerTwoName);

        if (maybePlayer1.isPresent()) {
            currentPlayerOne = maybePlayer1.get();
        } else {
            currentPlayerOne = Player.builder().name(playerOneName).build();
            session.persist(currentPlayerOne);
        }

        if (maybePlayer2.isPresent()) {
            currentPlayerTwo = maybePlayer2.get();
        } else {
            currentPlayerTwo = Player.builder().name(playerTwoName).build();
            session.persist(currentPlayerTwo);
        }
        session.getTransaction().commit();

        return List.of(currentPlayerOne, currentPlayerTwo);
    }
}
