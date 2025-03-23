package com.chuckcha.service;

import com.chuckcha.entity.MatchScore;
import com.chuckcha.exceptions.BadRequestException;
import com.chuckcha.exceptions.DataNotFoundException;

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

    public int validateId(String id) {
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new BadRequestException("Invalid id: " + id);
        }
    }
}

