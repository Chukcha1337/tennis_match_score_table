package com.chuckcha.service;

import com.chuckcha.entity.Match;
import com.chuckcha.entity.MatchScore;
import com.chuckcha.entity.Player;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.UUID;


public class NewMatchService {

    private final SessionFactory sessionFactory;
    private final OngoingMatchesService ongoingMatchesService;
    private final PlayerService playerService;

    public NewMatchService(SessionFactory sessionFactory, OngoingMatchesService ongoingMatchesService, PlayerService playerService) {
        this.sessionFactory = sessionFactory;
        this.ongoingMatchesService = ongoingMatchesService;
        this.playerService = playerService;
    }

    public String createNewMatch(String player1name, String player2name) {
        List<Player> players = playerService.checkOrCreatePlayers(player1name, player2name);
        Player playerOne = players.getFirst();
        Player playerTwo = players.getLast();
        MatchScore matchScore = new MatchScore(playerOne, playerTwo);
        return ongoingMatchesService.addNewMatch(matchScore);
    }
}
