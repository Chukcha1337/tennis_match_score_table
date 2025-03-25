package com.chuckcha.service;

import com.chuckcha.entity.MatchScore;
import com.chuckcha.exceptions.BadRequestException;
import com.chuckcha.exceptions.DataNotFoundException;
import com.chuckcha.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class ValidatorService {

    public void validateUUID(String uuid) {
        if (uuid == null) {
            throw new BadRequestException("UUID is empty");
        }
    }

    public void validateMatchScore(MatchScore matchScore, String uuid) {
        if (matchScore == null) {
            throw new DataNotFoundException("Match with uuid " + uuid + " not found");
        }
    }

    public int validatePageParam(String pageParam) {
        if (pageParam != null) {
            try {
                return Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                throw new BadRequestException("Invalid page parameter: " + pageParam);
            }
        }
        return 1;
    }

    public void validatePlayersNames(List<String> names) throws ValidationException {
        List<String> errors = new ArrayList<>();

        for (int i = 0; i < names.size(); i++) {
            String currentName = names.get(i);
            int playerIndex = i + 1;

            if (currentName.isEmpty()) {
                errors.add("Player %d name is empty".formatted(playerIndex));
            }
            if (!currentName.matches("[а-яА-ЯёЁa-zA-Z\\s-]+")) {
                errors.add("Player %d name contains forbidden characters, please, use only letters or (-)".formatted(playerIndex));
            }
            if (currentName.charAt(0) == ' ' || currentName.charAt(0) == '-') {
                errors.add("Player %d name must begin with letter".formatted(playerIndex));
            }
            if (currentName.contains("--") || currentName.contains("  ")) {
                errors.add("Player %d name contains forbidden amount of sequential characters, please, use only letters or (-)".formatted(playerIndex));
            }
            if (currentName.length() > 30) {
                errors.add("Player %d name is too long".formatted(playerIndex));
            }
            if (currentName.length() < 4) {
                errors.add("Player %d name is too short".formatted(playerIndex));
            }
        }

        if (names.getFirst().equalsIgnoreCase(names.getLast())) {
            errors.add("Players names should be unique");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}

