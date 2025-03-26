package com.chuckcha.entity;

import lombok.Getter;
import java.util.HashMap;
import java.util.Map;

@Getter
public class PlayerScore {

    private final long playerId;
    private final String playerName;
    private int points = 0;
    private int setPoints = 0;
    private int gamePoints = 0;
    private final Map<Integer, String> gamesHistory = new HashMap<>();
    private boolean isWinner = false;

    public PlayerScore(long id, String name) {
        this.playerId = id;
        this.playerName = name;
    }

    public void incrementPoints() {
        points++;
    }

    public void decrementPoints() {
        points--;
    }

    public void incrementSets() {
        setPoints++;
    }

    public void saveSetResults(int indexOfSet, int opponentGamePoints) {
        String result = "(%s - %s)".formatted(gamePoints, opponentGamePoints);
        gamesHistory.put(indexOfSet, result);
    }

    public String getSetResult(int indexOfSet) {
        return gamesHistory.get(indexOfSet);
    }

    public void incrementGames() {
        gamePoints++;
    }

    public void setWinner() {
        isWinner = true;
    }

    public void resetPoints() {
        points = 0;
    }

    public void resetGames() {
        gamePoints = 0;
    }

}
