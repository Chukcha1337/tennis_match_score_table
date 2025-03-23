package com.chuckcha.service;

import com.chuckcha.entity.MatchScore;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor
public class OngoingMatchesService implements Service{

    private final Map<String, MatchScore> currentMatches = new ConcurrentHashMap<>();

    public String addNewMatch(MatchScore matchScore) {
        String uuid = UUID.randomUUID().toString();
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
