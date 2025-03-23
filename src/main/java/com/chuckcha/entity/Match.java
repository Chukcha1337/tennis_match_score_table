package com.chuckcha.entity;

import jakarta.persistence.*;
import lombok.*;

@NamedEntityGraph(name = "WithPlayers",
        attributeNodes = {
        @NamedAttributeNode("firstPlayer"),
        @NamedAttributeNode("secondPlayer"),
        @NamedAttributeNode("winner")
        }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Table(name = "matches", schema = "public")
@Entity
public class Match implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Player1")
    private Player firstPlayer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Player2")
    private Player secondPlayer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Winner")
    private Player winner;

    public Match(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }
}
