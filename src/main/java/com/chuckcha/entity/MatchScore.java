package com.chuckcha.entity;

import com.chuckcha.dto.PlayerDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class MatchScore {

    private final PlayerScore firstPlayerScore;
    private final PlayerScore secondPlayerScore;
    private final long firstPlayerId;
    private final long secondPlayerId;
    private boolean isTieBreak;
    private boolean isGameFinished;
    private String winnerName;

    public MatchScore(PlayerDto firstPlayerDto, PlayerDto secondPlayerDto) {
        firstPlayerScore = new PlayerScore(firstPlayerDto.id(), firstPlayerDto.name());
        secondPlayerScore = new PlayerScore(secondPlayerDto.id(), secondPlayerDto.name());
        firstPlayerId = firstPlayerDto.id();
        secondPlayerId = secondPlayerDto.id();
        isTieBreak = false;
        isGameFinished = false;
    }

    public long getOpponentId(long id) {
        return id == firstPlayerId ? secondPlayerId : firstPlayerId;
    }

    public PlayerScore getPlayerScoreById(long id) {
        return id == firstPlayerId ? firstPlayerScore : secondPlayerScore;
    }

    public void incrementPoints(long id) {
        getPlayerScoreById(id).incrementPoints();
    }

    public void decrementPoints(long id) {
        getPlayerScoreById(id).decrementPoints();
    }

    public int getPlayerPoints(long id) {
        return getPlayerScoreById(id).getPoints();
    }

    public void incrementSets(long id) {
        PlayerScore playerScore = getPlayerScoreById(id);
        PlayerScore opponentScore = getPlayerScoreById(getOpponentId(id));
        playerScore.incrementSets();
        saveSetResults(playerScore, opponentScore);
        resetPoints();
        resetGames();
    }

    public int getSetsNumber() {
        return firstPlayerScore.getSetPoints() + secondPlayerScore.getSetPoints();
    }

    private void saveSetResults(PlayerScore playerScore, PlayerScore opponentScore) {
        int indexOfSet = playerScore.getSetPoints() + opponentScore.getSetPoints();
        playerScore.saveSetResults(indexOfSet, opponentScore.getGamePoints());
        opponentScore.saveSetResults(indexOfSet, playerScore.getGamePoints());
    }

    public String getSetResults(int indexOfSet, long playerId) {
        return getPlayerScoreById(playerId).getSetResult(indexOfSet);
    }

    public int getPlayerSets(long id) {
        return getPlayerScoreById(id).getSetPoints();
    }

    public void incrementGames(long id) {
        getPlayerScoreById(id).incrementGames();
        resetPoints();
    }

    public int getPlayerGames(long id) {
        return getPlayerScoreById(id).getGamePoints();
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

    public void setWinner(long id) {
        PlayerScore playerScore = getPlayerScoreById(id);
        playerScore.setWinner();
        winnerName = playerScore.getPlayerName();
    }

    private void resetPoints() {
        firstPlayerScore.resetPoints();
        secondPlayerScore.resetPoints();
    }

    private void resetGames() {
        firstPlayerScore.resetGames();
        secondPlayerScore.resetGames();
    }

    public String getNormalPoints(long id) {
        int index = getPlayerPoints(id);
        Points[] points = Points.values();
        if (index >= 0 && index < points.length) {
            return points[index].getRepresentation();
        }
        throw new IllegalArgumentException("Invalid point index: " + index);
    }
}

