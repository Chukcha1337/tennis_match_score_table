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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"firstPlayer", "secondPlayer", "winner"})
@Builder
@Table(name = "matches", schema = "public")
@Entity
public class Match implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Player1")
    private Player firstPlayer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Player2")
    private Player secondPlayer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Winner")
    private Player winner;
}
