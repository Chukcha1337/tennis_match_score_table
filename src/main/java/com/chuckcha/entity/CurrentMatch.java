package com.chuckcha.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class CurrentMatch {

    private int player1id;
    private int player2id;
    private int player1Score = 0;
    private int player2Score = 0;

    public CurrentMatch(int player1id, int player2id) {
    this.player1id = player1id;
    this.player2id = player2id;
    }

    public void incrementPlayerScore(Player player) {

    }


}
