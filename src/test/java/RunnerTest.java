import com.chuckcha.dto.PlayerDto;
import com.chuckcha.entity.MatchScore;
import com.chuckcha.entity.Player;
import com.chuckcha.mapper.DtoMapper;
import com.chuckcha.service.MatchScoreCalculationService;
import com.chuckcha.service.ValidatorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class RunnerTest {

    private static final MatchScoreCalculationService MATCH_SCORE_CALCULATION_SERVICE = new MatchScoreCalculationService();
    private static final int SETS_TO_WIN = MATCH_SCORE_CALCULATION_SERVICE.getSetsToWin();
    private static final int GAMES_TO_WIN = MATCH_SCORE_CALCULATION_SERVICE.getGamesToWin();
    private static final int POINTS_TO_WIN = MATCH_SCORE_CALCULATION_SERVICE.getPointsToWin();
    private static final int TIE_BREAK_POINTS_TO_WIN = MATCH_SCORE_CALCULATION_SERVICE.getTieBreakPointsToWin();
    private static final int MIN_DIFFERENCE = MATCH_SCORE_CALCULATION_SERVICE.getMinDifference();
    private static final String FIRST_PLAYER_ID_STRING = "1";
    private static final String SECOND_PLAYER_ID_STRING = "2";
    private static MatchScore matchScore;
    private static PlayerDto firstPlayerDto;
    private static long firstPlayerId;
    private static PlayerDto secondPlayerDto;
    private static long secondPlayerId;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        firstPlayerId = Integer.parseInt(FIRST_PLAYER_ID_STRING);
        secondPlayerId = Integer.parseInt(SECOND_PLAYER_ID_STRING);
        firstPlayerDto = DtoMapper.toDto(Player.builder()
                .id(firstPlayerId)
                .name("firstPlayer")
                .build());
        secondPlayerDto = DtoMapper.toDto(Player.builder()
                .id(secondPlayerId)
                .name("secondPlayer")
                .build());

    }

    @BeforeEach
    void setUp() throws Exception {
        matchScore = new MatchScore(firstPlayerDto, secondPlayerDto);
    }

    @ParameterizedTest
    @CsvSource({FIRST_PLAYER_ID_STRING, SECOND_PLAYER_ID_STRING})
    void isGameContinueWhenNotEnoughAdvantage(String playerIdString) {
        String opponentIdString = getOpponentIdString(playerIdString);
        int id = Integer.parseInt(playerIdString);
        int minimumIterationsAmount = POINTS_TO_WIN + MIN_DIFFERENCE;
        for (int i = 0; i <= minimumIterationsAmount; i++) {
            MATCH_SCORE_CALCULATION_SERVICE.updateScore(matchScore, playerIdString);
            MATCH_SCORE_CALCULATION_SERVICE.updateScore(matchScore, opponentIdString);
        }
        assertThat(matchScore.getPlayerGames(id)).isEqualTo(0);
    }

    @ParameterizedTest
    @CsvSource({FIRST_PLAYER_ID_STRING, SECOND_PLAYER_ID_STRING})
    void isGameContinueWhenEnoughAdvantage(String playerIdString) {
        int id = Integer.parseInt(playerIdString);
        setPointsToDraw();
        incrementPlayerPointsSeveralTimes(playerIdString, MIN_DIFFERENCE);
        assertThat(matchScore.getPlayerGames(id)).isEqualTo(1);
    }

    @ParameterizedTest
    @CsvSource({FIRST_PLAYER_ID_STRING, SECOND_PLAYER_ID_STRING})
    void isGamesIncrementingWhenPointsEnoughToWin(String playerIdString) {
        int id = Integer.parseInt(playerIdString);
        incrementPlayerPointsSeveralTimes(playerIdString, POINTS_TO_WIN);
        assertThat(matchScore.getPlayerGames(id)).isEqualTo(1);

    }

    @Test
    void isSetContinueWhenBothPlayersScoreAreEqualGamesToWin() {
        setTieBreak(matchScore);
        assertThat(matchScore.getPlayerSets(firstPlayerDto.id())).isEqualTo(0);
        assertThat(matchScore.getPlayerSets(secondPlayerDto.id())).isEqualTo(0);
        assertThat(matchScore.isTieBreak()).isTrue();
    }

    @ParameterizedTest
    @CsvSource({FIRST_PLAYER_ID_STRING, SECOND_PLAYER_ID_STRING})
    void isSetEndsWhenTieBreakPointsEnoughToWin(String playerIdString) {
        int id = Integer.parseInt(playerIdString);
        setTieBreak(matchScore);
        incrementPlayerPointsSeveralTimes(playerIdString, TIE_BREAK_POINTS_TO_WIN);
        assertThat(matchScore.getPlayerSets(id)).isEqualTo(1);
    }

    @Test
    void isSetContinueWhenBothPlayersTieBreakPointsEnoughToWin() {
        setTieBreak(matchScore);
        setTieBreakPointsToDraw(matchScore);
        assertThat(matchScore.getPlayerSets(firstPlayerDto.id())).isEqualTo(0);
        assertThat(matchScore.getPlayerSets(firstPlayerDto.id())).isEqualTo(0);
    }

    @ParameterizedTest
    @CsvSource({FIRST_PLAYER_ID_STRING, SECOND_PLAYER_ID_STRING})
    void isSetEndsWhenPlayerGetsAdvantageAtTieBreak(String playerIdString) {
        int id = Integer.parseInt(playerIdString);
        setTieBreak(matchScore);
        setTieBreakPointsToDraw(matchScore);
        incrementPlayerPointsSeveralTimes(playerIdString, MIN_DIFFERENCE);
        assertThat(matchScore.getPlayerSets(id)).isEqualTo(1);
    }

    private void setPointsToDraw() {
        for (int i = 0; i < POINTS_TO_WIN; i++) {
            MATCH_SCORE_CALCULATION_SERVICE.updateScore(matchScore, FIRST_PLAYER_ID_STRING);
            MATCH_SCORE_CALCULATION_SERVICE.updateScore(matchScore, SECOND_PLAYER_ID_STRING);
        }
    }

    private void incrementPlayerPointsSeveralTimes(String playerId, int points) {
        for (int i = 0; i < points; i++) {
            MATCH_SCORE_CALCULATION_SERVICE.updateScore(matchScore, playerId);
        }
    }

    private void setTieBreakPointsToDraw(MatchScore matchScore) {
        for (int i = 0; i < TIE_BREAK_POINTS_TO_WIN; i++) {
            MATCH_SCORE_CALCULATION_SERVICE.updateScore(matchScore, FIRST_PLAYER_ID_STRING);
            MATCH_SCORE_CALCULATION_SERVICE.updateScore(matchScore, SECOND_PLAYER_ID_STRING);
        }
    }

    private void setTieBreak(MatchScore matchScore) {
        for (int i = 0; i < GAMES_TO_WIN; i++) {
            for (int j = 0; j < POINTS_TO_WIN; j++) {
                MATCH_SCORE_CALCULATION_SERVICE.updateScore(matchScore, FIRST_PLAYER_ID_STRING);
            }
            for (int j = 0; j < POINTS_TO_WIN; j++) {
                MATCH_SCORE_CALCULATION_SERVICE.updateScore(matchScore, SECOND_PLAYER_ID_STRING);
            }
        }
    }

    private String getOpponentIdString(String playerIdString) {
        if (playerIdString.equals(FIRST_PLAYER_ID_STRING)) {
            return SECOND_PLAYER_ID_STRING;
        }
        return FIRST_PLAYER_ID_STRING;
    }
}
