package com.chuckcha.service;

import com.chuckcha.dto.PlayerDto;
import com.chuckcha.entity.MatchScore;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor
public class OngoingMatchesService {

    private final Map<String, MatchScore> currentMatches = new ConcurrentHashMap<>();

    public String createNewMatch(PlayerDto firstPlayerDto, PlayerDto secondPlayerDto) {
        String uuid = UUID.randomUUID().toString();

        MatchScore matchScore = new MatchScore(firstPlayerDto, secondPlayerDto);
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
