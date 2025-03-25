package com.chuckcha.service;

import com.chuckcha.entity.MatchScore;
import com.chuckcha.entity.Player;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor
public class OngoingMatchesService implements Service{

    private final Map<String, MatchScore> currentMatches = new ConcurrentHashMap<>();

    public String createNewMatch(Player firstPlayer, Player secondPlayer) {
        String uuid = UUID.randomUUID().toString();
        MatchScore matchScore = new MatchScore(firstPlayer, secondPlayer);
        currentMatches.put(uuid, matchScore);
        return uuid;
    }

    public MatchScore getCurrentMatch(String uuid) {
        return currentMatches.get(uuid);
    }

    public void removeCurrentMatch(String uuid) {
        currentMatches.remove(uuid);
    }
}
