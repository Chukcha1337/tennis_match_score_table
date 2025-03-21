package com.chuckcha.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@EqualsAndHashCode
@ToString
public class MatchScore extends Match {

    private Map<Integer, Integer> points;
    private Map<Integer, Integer> sets;
    private Map<Integer, Integer> games;
    private Map<Integer, Map<Integer, Integer>> gamesHistory;
    private final int firstPlayerId;
    private final int secondPlayerId;
    private boolean isTieBreak;
    private boolean isGameFinished;

    public MatchScore(Player firstPlayer, Player secondPlayer) {
        super(firstPlayer, secondPlayer);
        firstPlayerId = firstPlayer.getId();
        secondPlayerId = secondPlayer.getId();
        isTieBreak = false;
        isGameFinished = false;
        initializeScore();
    }

    private void initializeScore() {
        points = new HashMap<>();
        sets = new HashMap<>();
        games = new HashMap<>();
        gamesHistory = new LinkedHashMap<>();
        points.put(firstPlayerId, 0);
        sets.put(firstPlayerId, 0);
        games.put(firstPlayerId, 0);
        points.put(secondPlayerId, 0);
        sets.put(secondPlayerId, 0);
        games.put(secondPlayerId, 0);
    }

    public int getOpponentId(int id) {
        return id == firstPlayerId ? secondPlayerId : firstPlayerId;
    }

    public void incrementPoints(int id) {
        points.replace(id, points.get(id) + 1);
    }

    public void decrementPoints(int id) {
        points.replace(id, points.get(id) - 1);
    }

    public int getPlayerPoints(int id) {
        return points.get(id);
    }

    public void incrementSets(int id) {
        sets.replace(id, sets.get(id) + 1);
        saveSetResults(id);
        resetPoints();
        resetGames();
    }

    public int getSetsNumber() {
        return sets.values().stream().mapToInt(Integer::intValue).sum();
    }

    private void saveSetResults(int id) {
        int playerGames = getPlayerGames(id);
        int opponentId = getOpponentId(id);
        int opponentGames = getPlayerGames(opponentId);
        Map<Integer, Integer> setResults = new HashMap<>();
        setResults.put(id, playerGames);
        setResults.put(opponentId, opponentGames);
        gamesHistory.put(getPlayerSets(id) + getPlayerSets(opponentId), setResults);
    }

    public String getSetResults(int setId, int playerId) {
        int opponentId = getOpponentId(playerId);
        Map<Integer, Integer> integerIntegerMap = gamesHistory.get(setId);
        Integer playerGames = integerIntegerMap.get(playerId);
        Integer opponentGames = integerIntegerMap.get(opponentId);
        return "(%d - %d)".formatted(playerGames, opponentGames);
    }

    public int getPlayerSets(int id) {
        return sets.get(id);
    }

    public void incrementGames(int id) {
        resetPoints();
        games.replace(id, games.get(id) + 1);
    }

    public int getPlayerGames(int id) {
        return games.get(id);
    }

    public void setTieBrakeTrue() {
        isTieBreak = true;
    }

    public void setTieBrakeFalse() {
        isTieBreak = false;
    }

    public void setGameFinished() {
        isGameFinished = true;
    }

    public boolean isTieBreak() {
        return isTieBreak;
    }

    public boolean isGameFinished() {
        return isGameFinished;
    }

    public void setWinner(int id) {
        if (id == firstPlayerId) {
            super.setWinner(super.getFirstPlayer());
        } else super.setWinner(super.getSecondPlayer());
    }

    private void resetPoints() {
        points.clear();
        points.put(firstPlayerId, 0);
        points.put(secondPlayerId, 0);
    }

    private void resetGames() {
        games.clear();
        games.put(firstPlayerId, 0);
        games.put(secondPlayerId, 0);
    }

    public String getNormalPoints(int id) {
        int index = getPlayerPoints(id);
        Points[] points = Points.values();
        if (index >= 0 && index < points.length) {
            return points[index].getRepresentation();
        }
        throw new IllegalArgumentException("Invalid point index: " + index);
    }
}

