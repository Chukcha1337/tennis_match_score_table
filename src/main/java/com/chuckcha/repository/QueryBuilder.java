package com.chuckcha.repository;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class QueryBuilder<E> {
    private final String query;
    private final Class<E> clazz;
    @Builder.Default
    private final Map<String, Object> parameters = Map.of();
    @Builder.Default
    private final int offset = 0;
    @Builder.Default
    private final int pageSize = 5;
    private final String graphName;
}
