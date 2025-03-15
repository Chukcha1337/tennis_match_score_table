package com.chuckcha.service;

import com.chuckcha.entity.CurrentMatch;
import com.chuckcha.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OngoingMatchesService {

    private static final OngoingMatchesService INSTANCE = new OngoingMatchesService();
    private final Map<UUID, CurrentMatch> currentMatches = new HashMap<>();

    public void addNewMatch(UUID uuid, CurrentMatch currentMatch) {
        currentMatches.put(uuid, currentMatch);
    }

    public CurrentMatch getCurrentMatch(UUID uuid) {
        return currentMatches.get(uuid);
    }

    public void removeCurrentMatch(UUID uuid) {
        currentMatches.remove(uuid);
    }

    public static OngoingMatchesService getInstance() {
        return INSTANCE;
    }
}
