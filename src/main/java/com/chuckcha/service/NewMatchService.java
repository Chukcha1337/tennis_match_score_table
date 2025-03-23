package com.chuckcha.service;

import com.chuckcha.entity.MatchScore;
import com.chuckcha.entity.Player;
import com.chuckcha.exceptions.ValidationException;
import com.chuckcha.util.DataValidator;

import java.util.List;


public class NewMatchService implements Service {

    private final OngoingMatchesService ongoingMatchesService;
    private final PlayerService playerService;

    public NewMatchService(OngoingMatchesService ongoingMatchesService, PlayerService playerService) {
        this.ongoingMatchesService = ongoingMatchesService;
        this.playerService = playerService;
    }

    public String createNewMatch(String firstPlayerName, String secondPlayerName) throws ValidationException {
        DataValidator.validatePlayersNames(List.of(firstPlayerName, secondPlayerName));
        List<Player> players = playerService.checkOrCreatePlayers(firstPlayerName, secondPlayerName);
        Player playerOne = players.getFirst();
        Player playerTwo = players.getLast();
        MatchScore matchScore = new MatchScore(playerOne, playerTwo);
        return ongoingMatchesService.addNewMatch(matchScore);
    }
}
