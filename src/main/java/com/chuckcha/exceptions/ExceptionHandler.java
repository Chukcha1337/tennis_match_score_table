package com.chuckcha.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;

public final class ExceptionHandler {

    private ExceptionHandler() {
    }

    public static void handleExceptions(HttpServletRequest req, Throwable throwable) {
        int status = getStatusCode(throwable);
        req.setAttribute("errorName", throwable.getClass().getSimpleName());
        req.setAttribute("status", status);
        req.setAttribute("message", throwable.getMessage());
        req.setAttribute("stackTrace", Arrays.stream(throwable.getStackTrace()).toList());
    }

    private static int getStatusCode(Throwable throwable) {
        return switch (throwable.getClass().getSimpleName()) {
            case "BadRequestException" -> HttpServletResponse.SC_BAD_REQUEST;
            case "DataNotFoundException" -> HttpServletResponse.SC_NOT_FOUND;
            default -> HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        };
    }
}
