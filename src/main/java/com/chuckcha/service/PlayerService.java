package com.chuckcha.service;

import com.chuckcha.dto.PlayerDto;
import com.chuckcha.entity.Player;
import com.chuckcha.mapper.DtoMapper;
import com.chuckcha.repository.PlayerRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class PlayerService {

    private final SessionFactory sessionFactory;

    public PlayerService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<PlayerDto> checkOrCreatePlayers(String firstPlayerName, String secondPlayerName) {
        EntityManager entityManager = sessionFactory.getCurrentSession();
        entityManager.getTransaction().begin();
        PlayerRepository playerRepository = new PlayerRepository(entityManager);
        DtoMapper.toDto(checkOrCreateOnePlayer(firstPlayerName, playerRepository));
        PlayerDto firstPlayerDto = DtoMapper.toDto(checkOrCreateOnePlayer(firstPlayerName, playerRepository));
        PlayerDto secondPlayerDto = DtoMapper.toDto(checkOrCreateOnePlayer(secondPlayerName, playerRepository));
        entityManager.getTransaction().commit();
        return List.of(firstPlayerDto, secondPlayerDto);
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
