package com.chuckcha.service;

import com.chuckcha.entity.MatchScore;

public class MatchScoreCalculationService implements Service {

    private static final int SETS_TO_WIN = 2;
    private static final int GAMES_TO_WIN = 6;
    private static final int POINTS_TO_WIN = 4;
    private static final int TIE_BREAK_POINTS_TO_WIN = 7;
    private static final int MIN_DIFFERENCE = 2;
    private final ValidatorService validatorService;

    public MatchScoreCalculationService(ValidatorService validatorService) {
        this.validatorService = validatorService;
    }

    public void updateScore(MatchScore matchScore, String pointWinnerIdString) {
        int pointWinnerId = validatorService.validateId(pointWinnerIdString);
        int pointLoserId = matchScore.getOpponentId(pointWinnerId);
        if (matchScore.isTieBreak()) {
            calculateIfTieBrake(matchScore, pointWinnerId, pointLoserId);
        } else calculateIfNoTieBrake(matchScore, pointWinnerId, pointLoserId);
    }

    private void calculateIfTieBrake(MatchScore matchScore, int pointWinnerId, int pointLoserId) {
        matchScore.incrementPoints(pointWinnerId);
        int winnerPoints = matchScore.getPlayerPoints(pointWinnerId);
        int loserPoints = matchScore.getPlayerPoints(pointLoserId);
        if (winnerPoints >= TIE_BREAK_POINTS_TO_WIN) {
            if (winnerPoints - MIN_DIFFERENCE >= loserPoints) {
                matchScore.incrementGames(pointWinnerId);
                calculateSets(matchScore, pointWinnerId);
                matchScore.setTieBrakeFalse();
            }
        }
    }

    private void calculateSets(MatchScore matchScore, int pointWinnerId) {
        matchScore.incrementSets(pointWinnerId);
        int winnerSets = matchScore.getPlayerSets(pointWinnerId);
        if (winnerSets == SETS_TO_WIN) {
            matchScore.setGameFinished();
            matchScore.setWinner(pointWinnerId);
        }
    }

    private void calculateIfNoTieBrake(MatchScore matchScore, int pointWinnerId, int pointLoserId) {
        matchScore.incrementPoints(pointWinnerId);
        int winnerPoints = matchScore.getPlayerPoints(pointWinnerId);
        int loserPoints = matchScore.getPlayerPoints(pointLoserId);
        if (isPointsEnoughToWin(winnerPoints, loserPoints)) {
            matchScore.incrementGames(pointWinnerId);
            int winnerGames = matchScore.getPlayerGames(pointWinnerId);
            int loserGames = matchScore.getPlayerGames(pointLoserId);
            if (isGamesEnoughToWin(winnerGames, loserGames)) {
                calculateSets(matchScore, pointWinnerId);
            } else if (isTieBreakCondition(winnerGames, loserGames)) {
                matchScore.setTieBrakeTrue();
            }
        } else if (isItAdvantageDraw(winnerPoints, loserPoints)) {
            matchScore.decrementPoints(pointWinnerId);
            matchScore.decrementPoints(pointLoserId);
        }
    }

    private boolean isPointsEnoughToWin(int winnerPoints, int loserPoints) {
        return (winnerPoints >= POINTS_TO_WIN && loserPoints <= winnerPoints - MIN_DIFFERENCE);
    }

    private boolean isGamesEnoughToWin(int winnerGames, int loserGames) {
        return (winnerGames >= GAMES_TO_WIN && loserGames <= winnerGames - MIN_DIFFERENCE);
    }

    private boolean isItAdvantageDraw(int winnerPoints, int loserPoints) {
        return winnerPoints == POINTS_TO_WIN && loserPoints == POINTS_TO_WIN;
    }

    private boolean isTieBreakCondition(int winnerGames, int loserGames) {
        return winnerGames == GAMES_TO_WIN && loserGames == GAMES_TO_WIN;
    }

    public int getSetsToWin() {
        return SETS_TO_WIN;
    }
    public int getTieBreakPointsToWin() {
        return TIE_BREAK_POINTS_TO_WIN;
    }
    public int getGamesToWin() {
        return GAMES_TO_WIN;
    }
    public int getPointsToWin() {
        return POINTS_TO_WIN;
    }
    public int getMinDifference() {
        return MIN_DIFFERENCE;
    }

}
