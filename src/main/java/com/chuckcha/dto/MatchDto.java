package com.chuckcha.dto;

import com.chuckcha.entity.Player;
import lombok.Builder;

@Builder
public record MatchDto(int id,
                       Player firstPlayer,
                       Player secondPlayer,
                       Player winner) {
}
