package com.chuckcha.mapper;

import com.chuckcha.dto.MatchDto;
import com.chuckcha.dto.PlayerDto;
import com.chuckcha.entity.Match;
import com.chuckcha.entity.Player;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DtoMapper {

    public static PlayerDto toDto(Player player) {
        return new PlayerDto(player.getId(), player.getName());
    }

    public static MatchDto toDto(Match match) {
        return new MatchDto(
                match.getFirstPlayer().getName(),
                match.getSecondPlayer().getName(),
                match.getWinner().getName());
    }
}
