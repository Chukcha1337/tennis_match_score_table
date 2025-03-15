package com.chuckcha.service;

import com.chuckcha.entity.CurrentMatch;
import com.chuckcha.entity.Player;
import com.chuckcha.repository.PlayerRepository;
import com.chuckcha.util.HibernateUtil;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.UUID;

public class NewMatchService {

    private static final NewMatchService INSTANCE = new NewMatchService();

    @Transactional
    public UUID handleNewMatch(String player1name, String player2name) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            PlayerRepository playerRepository = new PlayerRepository(session);

            Player player1 = Player.builder()
                    .name(player1name)
                    .build();

            Player player2 = Player.builder()
                    .name(player2name)
                    .build();

            Player mergedPlayer1 = playerRepository.update(player1);
            Player mergedPlayer2 = playerRepository.update(player2);

            session.getTransaction().commit();

            return createNewMatch(mergedPlayer1, mergedPlayer2);
        }
    }


    public UUID createNewMatch(Player player1, Player player2) {
        UUID uuid = UUID.randomUUID();
        Integer player1id = player1.getId();
        Integer player2id = player2.getId();
        CurrentMatch currentMatch = new CurrentMatch(player1id, player2id);
        OngoingMatchesService.getInstance().addNewMatch(uuid, currentMatch);
        return uuid;
    }

    public static NewMatchService getInstance() {
        return INSTANCE;
    }
}
