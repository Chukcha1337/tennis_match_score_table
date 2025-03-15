package com.chuckcha.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public final class ExceptionHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String NOT_NULL_VIOLATION = "23502";
    private static final String UNIQUE_VIOLATION = "23505";
    private static final String STRING_DATA_RIGHT_TRUNCATION = "22001";
    private static final String NUMERIC_VALUE_OUT_OF_RANGE = "22003";

    private ExceptionHandler() {}

    public static void handleExceptions(HttpServletResponse resp, Throwable throwable) throws IOException {
        int status = getStatusCode(throwable);
//        ErrorResponseDto errorResponseDto = new ErrorResponseDto(throwable.getMessage());
//        ResponseCreator.createResponse(resp, status, errorResponseDto, objectMapper);
    }

    private static int getStatusCode(Throwable throwable) {
        return switch (throwable.getClass().getSimpleName()) {
            case "IllegalArgumentException" -> HttpServletResponse.SC_BAD_REQUEST;
            case "DataAlreadyExistsException" -> HttpServletResponse.SC_CONFLICT;
            case "DataNotFoundException" -> HttpServletResponse.SC_NOT_FOUND;
            default -> HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        };
    }

    public static RuntimeException handleSQLException(SQLException exception) {
        String sqlState = exception.getSQLState();
        switch (sqlState) {
            case NOT_NULL_VIOLATION -> throw new DataNotFoundException("One or more currencies are not exist in the database");
            case UNIQUE_VIOLATION -> throw new DataAlreadyExistsException("Such currency pair exchange rate already exists");
            case NUMERIC_VALUE_OUT_OF_RANGE -> throw new IllegalArgumentException("Numeric value out of range");
            case STRING_DATA_RIGHT_TRUNCATION -> throw new IllegalArgumentException("String data is too long for the column");
            default -> throw new CurrencyExchangeAppRuntimeException("Database Error: " + exception.getMessage());
        }
    }
}
