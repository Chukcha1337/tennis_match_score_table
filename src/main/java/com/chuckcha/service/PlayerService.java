package com.chuckcha.service;

import com.chuckcha.entity.Player;
import com.chuckcha.repository.PlayerRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class PlayerService implements Service {

    private final SessionFactory sessionFactory;

    public PlayerService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Player> checkOrCreatePlayers(String firstPlayerName, String secondPlayerName) {
        EntityManager entityManager = sessionFactory.getCurrentSession();
        entityManager.getTransaction().begin();
        PlayerRepository playerRepository = new PlayerRepository(entityManager);
        Player firstPlayer = checkOrCreateOnePlayer(firstPlayerName, playerRepository);
        Player secondPlayer = checkOrCreateOnePlayer(secondPlayerName, playerRepository);
        entityManager.getTransaction().commit();
        return List.of(firstPlayer, secondPlayer);
    }

    private Player checkOrCreateOnePlayer(String playerName, PlayerRepository playerRepository) {
        Optional<Player> maybePlayer = playerRepository.findByName(playerName);
        Player player;
        if (maybePlayer.isPresent()) {
            player = maybePlayer.get();
        } else {
            player = Player.builder().name(playerName).build();
            playerRepository.save(player);
        }
        return player;
    }

}
