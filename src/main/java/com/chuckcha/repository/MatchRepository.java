package com.chuckcha.repository;

import com.chuckcha.entity.Match;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Map;

public class MatchRepository extends BaseRepository<Match> {

    private static final String FIND_PAGINATED_HQL = "select m from Match m order by m.id desc";
    private static final String FIND_BY_NAME_PAGINATED_HQL = """
            select m from Match m 
            where lower(m.firstPlayer.name) like lower(:name)
            or lower(m.secondPlayer.name) like lower(:name)
            order by m.id desc
            """;
    private static final String GET_ALL_MATCHES_AMOUNT_HQL = "select count(m) from Match m";
    private static final String GET_MATCHES_AMOUNT_BY_NAME_HQL = """
            select count(m) from Match m 
            where lower(m.firstPlayer.name) like lower(:name)
            or lower(m.secondPlayer.name) like lower(:name)
            """;

    public MatchRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public List<Match> findPaginated(int offset, int pageSize) {
        return findResultList(QueryBuilder.<Match>builder()
                .query(FIND_PAGINATED_HQL)
                .clazz(Match.class)
                .offset(offset)
                .pageSize(pageSize)
                .graphName("WithPlayers")
                .build());
    }

    public List<Match> findByNamePaginated(String name, int offset, int pageSize) {
        return findResultList(QueryBuilder.<Match>builder()
                .query(FIND_BY_NAME_PAGINATED_HQL)
                .clazz(Match.class)
                .parameters(Map.of("name", "%" + name + "%"))
                .offset(offset)
                .pageSize(pageSize)
                .graphName("WithPlayers")
                .build());
    }

    public Long getRowsAmount() {
        return getCount(QueryBuilder.<Long>builder()
                .query(GET_ALL_MATCHES_AMOUNT_HQL)
                .build()
        );
    }

    public Long getRowsAmount(String name) {
        return getCount(QueryBuilder.<Long>builder()
                .query(GET_MATCHES_AMOUNT_BY_NAME_HQL)
                .parameters(Map.of("name", "%" + name + "%"))
                .build());
    }
}

