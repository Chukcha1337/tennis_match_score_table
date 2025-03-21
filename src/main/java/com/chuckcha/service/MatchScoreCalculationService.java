package com.chuckcha.service;

import com.chuckcha.entity.MatchScore;

public class MatchScoreCalculationService implements Service {

    private static final int SETS_TO_WIN = 2;
    private static final int GAMES_TO_WIN = 6;
    private static final int POINTS_TO_WIN = 4;
    private static final int TIE_BREAK_POINTS_TO_WIN = 7;
    private static final int MIN_DIFFERENCE = 2;

    public void updateScore(MatchScore matchScore, String pointWinnerIdString) {
        int pointWinnerId = Integer.parseInt(pointWinnerIdString);
        int pointLoserId = matchScore.getOpponentId(pointWinnerId);
        if (matchScore.isTieBreak()) {
            calculateIfTieBrake(matchScore, pointWinnerId, pointLoserId);
        } else calculateIfNoTieBrakes(matchScore, pointWinnerId, pointLoserId);
    }

    private void calculateIfTieBrake(MatchScore matchScore, int pointWinnerId, int pointLoserId) {
        matchScore.incrementPoints(pointWinnerId);
        int winnerPoints = matchScore.getPlayerPoints(pointWinnerId);
        int loserPoints = matchScore.getPlayerPoints(pointLoserId);
        if (winnerPoints >= TIE_BREAK_POINTS_TO_WIN) {
            if (winnerPoints - MIN_DIFFERENCE >= loserPoints) {
                calculateSets(matchScore, pointWinnerId);
                matchScore.setTieBrakeFalse();
            }
        }
    }

    private void calculateIfNoTieBrake(MatchScore matchScore, int pointWinnerId, int pointLoserId) {
        int winnerPoints = matchScore.getPlayerPoints(pointWinnerId);
        int loserPoints = matchScore.getPlayerPoints(pointLoserId);
        if (isPointsEnoughToWin(winnerPoints, loserPoints)) {
            int winnerGames = matchScore.getPlayerGames(pointWinnerId);
            int loserGames = matchScore.getPlayerGames(pointLoserId);
            if (isGamesEnoughToWin(winnerGames, loserGames)) {
                calculateSets(matchScore, pointWinnerId);
            } else if (isTieBreakCondition(winnerGames, loserGames)) {
                matchScore.incrementGames(pointWinnerId);
                matchScore.setTieBrakeTrue();
            } else matchScore.incrementGames(pointWinnerId);
        } else if (willBeAdvantageDraw(winnerPoints, loserPoints)) {
            matchScore.decrementPoints(pointLoserId);
        } else matchScore.incrementPoints(pointWinnerId);
    }

    private void calculateSets(MatchScore matchScore, int pointWinnerId) {
        matchScore.incrementSets(pointWinnerId);
        int winnerSets = matchScore.getPlayerSets(pointWinnerId);
        if (winnerSets == SETS_TO_WIN) {
            matchScore.setGameFinished();
            matchScore.setWinner(pointWinnerId);
        }
    }

    private boolean isTieBreakCondition(int winnerGames, int loserGames) {
        return winnerGames == GAMES_TO_WIN - 1 && loserGames == GAMES_TO_WIN;
    }

    private boolean isGamesEnoughToWin(int winnerGames, int loserGames) {
        return (winnerGames == GAMES_TO_WIN - 1 && loserGames <= GAMES_TO_WIN - MIN_DIFFERENCE)
               || (winnerGames == GAMES_TO_WIN && loserGames == GAMES_TO_WIN - 1);
    }

    private boolean willBeAdvantageDraw(int winnerPoints, int loserPoints) {
        return winnerPoints == POINTS_TO_WIN - 1 && loserPoints == POINTS_TO_WIN;
    }

    private boolean isPointsEnoughToWin(int winnerPoints, int loserPoints) {
        return (winnerPoints == POINTS_TO_WIN - 1 && loserPoints <= POINTS_TO_WIN - MIN_DIFFERENCE)
               || (winnerPoints == POINTS_TO_WIN && loserPoints == POINTS_TO_WIN - 1);
    }

    private boolean isGameFinished(int winnerPoints, int loserPoints) {
        return winnerPoints >= POINTS_TO_WIN && loserPoints <= POINTS_TO_WIN - MIN_DIFFERENCE;
    }

    private void calculateIfNoTieBrakes(MatchScore matchScore, int pointWinnerId, int pointLoserId) {
        matchScore.incrementPoints(pointWinnerId);
        int winnerPoints = matchScore.getPlayerPoints(pointWinnerId);
        int loserPoints = matchScore.getPlayerPoints(pointLoserId);
        if (isPointsEnoughToWins(winnerPoints, loserPoints)) {
            matchScore.incrementGames(pointWinnerId);
            int winnerGames = matchScore.getPlayerGames(pointWinnerId);
            int loserGames = matchScore.getPlayerGames(pointLoserId);
            if (isGamesEnoughToWins(winnerGames, loserGames)) {
                calculateSets(matchScore, pointWinnerId);
            } else if (isTieBreakConditions(winnerGames, loserGames)) {
                matchScore.setTieBrakeTrue();
            }
        } else if (isItAdvantageDraw(winnerPoints, loserPoints)) {
            matchScore.decrementPoints(pointWinnerId);
            matchScore.decrementPoints(pointLoserId);
        }
    }

    private boolean isPointsEnoughToWins(int winnerPoints, int loserPoints) {
        return (winnerPoints >= POINTS_TO_WIN && loserPoints <= winnerPoints - MIN_DIFFERENCE);
    }

    private boolean isGamesEnoughToWins(int winnerGames, int loserGames) {
        return (winnerGames >= GAMES_TO_WIN && loserGames <= winnerGames - MIN_DIFFERENCE);
    }

    private boolean isItAdvantageDraw(int winnerPoints, int loserPoints) {
        return winnerPoints == POINTS_TO_WIN && loserPoints == POINTS_TO_WIN;
    }

    private boolean isTieBreakConditions(int winnerGames, int loserGames) {
        return winnerGames == GAMES_TO_WIN && loserGames == GAMES_TO_WIN;
    }
}
